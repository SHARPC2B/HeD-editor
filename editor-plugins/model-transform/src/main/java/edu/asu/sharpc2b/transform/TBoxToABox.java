package edu.asu.sharpc2b.transform;

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Convert a standard OWL2 T-Box-based domain model Ontology into an A-Box version.
 *
 * What is meant by "standard OWL2 T-Box-based domain model Ontology" is classes and properties defined with
 * owl:Class, owl:ObjectProperty, owl:DatatypeProperty, rdfs:subClassOf, rdfs:subPropertyOf, plus
 * rdfs:domain and rdfs:range to specify what properties a class has, and what their type is.
 *
 * In the A-Box version of the domain model, all OWL classes and properties will become OWL Individuals,
 * whose type will be an owl:Class whose IRI is specified in a config file that maps the OWL+RDF builtin
 * concept IRIs to a replacement IRI.
 *
 * The default properties file will be searched for in the classpath at the resource path of:
 * "/DomainMetaModelABoxEntities.properties".  An alternate resource path can be passed in as the argument
 * to the constructor that takes a String argument.  In the properties file, a line like the following would
 * result in all OWL classes in the input Ontology to be transformed into OWL Individuals whose rdf:type
 * would be skos:Concept (":" character must be quoted with '\' in Java *.properties file).
 *
 * http\://www.w3.org/2002/07/owl#Class = http\://www.w3.org/2004/02/skos/core#Concept
 *
 * User: rk Date: 5/8/13
 */
public class TBoxToABox
{

    /**
     * Default file to load to obtain the A-Box IRIs to use as substitutes for OWL Class, Property,
     * subClassOf, etc.
     */
    private static String defaultResourcePathForSharpABoxConcepts
            = "/OWL-to-Sharp-ABox-Concepts.properties";

    //==================================================================================

    private Map<String, IRI> mmDomainObject;

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

    private PrefixOWLOntologyFormat pm;

    /**
     * The input T-Box Domain Model Ontology.
     */
    private OWLOntology ontT;

    /**
     * The output A-Box version of the Domain Model Ontology.
     */
    private OWLOntology ontA;

    //==================================================================================

    public TBoxToABox ()
    {
        super();

        try
        {
            initDomainModelABoxSubstitutionsFromPropertiesFile( defaultResourcePathForSharpABoxConcepts );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public TBoxToABox (final String resourceName)
            throws IOException
    {
        super();

        try
        {
            initDomainModelABoxSubstitutionsFromPropertiesFile( resourceName );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    //==================================================================================

    public void addABoxAxioms (final OWLOntology tboxDomainModel,
                               final OWLOntology aboxDomainModel)
    {
        this.ontT = tboxDomainModel;
        this.ontA = aboxDomainModel;

        oom = aboxDomainModel.getOWLOntologyManager();
        if (oom == null)
        {
//            oom = OWLManager.createOWLOntologyManager();
            oom = OwlUtil.createSharpOWLOntologyManager();
        }
        odf = oom.getOWLDataFactory();

        initNamespaces();

        addImports();

        for (OWLClass e : this.ontT.getClassesInSignature( false ))
        {
            transform_Class( e );
        }

        for (OWLObjectProperty e : this.ontT.getObjectPropertiesInSignature( false ))
        {
            transform_ObjectProperty( e );
        }

        for (OWLDataProperty e : this.ontT.getDataPropertiesInSignature( false ))
        {
            transform_DataProperty( e );
        }
    }

    /**
     * To supply additional mappings beyond what is specified in the config properties file.
     */
    public void setDomainModelABoxSubstitution (final IRI rdfOwlEntityIRI,
                                                final IRI domainModelEntityIRI)
    {
        mmDomainObject.put( rdfOwlEntityIRI.toString(), domainModelEntityIRI );
    }

    private String getLocalName (IRI iri)
    {
        String iriString = iri.toString();
        int pos = iriString.lastIndexOf( "#" );
        if (pos < 0)
        {
            pos = iriString.lastIndexOf( "/" );
        }

        return iriString.substring( pos + 1 );
    }

    private IRI getABoxIRI (IRI tIRI)
    {
        return getOntologyFormat().getIRI( "a:" + getLocalName( tIRI ) );
    }

    private PrefixOWLOntologyFormat getOntologyFormat ()
    {
        if (pm == null)
        {
            OWLOntologyFormat ooFormat = oom.getOntologyFormat( ontA );
            if (ooFormat != null && ooFormat.isPrefixOWLOntologyFormat())
            {
                pm = (PrefixOWLOntologyFormat) ooFormat;
            }
            else
            {
                pm = IriUtil.getDefaultSharpOntologyFormat();
            }
            pm.setPrefix( "skos:", Namespaces.SKOS.toString() );
        }
        return pm;
    }

    private void initNamespaces ()
    {
        getOntologyFormat();  // Make sure initialized.

        pm.setDefaultPrefix( this.ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "a:", this.ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "t:", this.ontT.getOntologyID().getOntologyIRI().toString() + "#" );
        IriUtil.addSharpPrefixes( pm );

        // ToDo: Add prefixes for ops:, skos-ext:, etc.?
    }

    /**
     * Read the properties file containing mappings from standard OWL T-box concepts to replacement entities
     * in the A-Box ontology.  Maps an IRI to an IRI.  An example mapping would be to map rdfs:subClassOf in
     * the input ontology to skos:subConceptOf in the output ontology.
     */
    private void initDomainModelABoxSubstitutionsFromPropertiesFile (final String resourceName)
            throws IOException
    {
        this.mmDomainObject = new TreeMap<String, IRI>();

//        final ClassLoader classLoader;
//        classLoader = this.getClass().getClassLoader();
        InputStream propertiesStream;
        propertiesStream = System.class.getResourceAsStream( resourceName );

        if (propertiesStream == null)
        {
            File f = new File( resourceName );
            propertiesStream = new FileInputStream( f );
            if (propertiesStream == null)
            {
                throw new IOException( "Cannot create InputStream for resource or File, " +
                                               "possibly File does not exist for resource = '" +
                                               resourceName + "' or File = '" + f + "'" );
            }
            else if (propertiesStream.available() <= 0)
            {
                throw new IOException( "Able to create InputStream for resource or File, " +
                                               "but stream appears to be empty or unreadable" +
                                               " for resource = " +
                                               resourceName + "' or File = '" + f + "'" );
            }
        }

//        System.out.println( System.class.getResource( resourceName ) );
//        System.out.println( propertiesStream );
        System.out.println( "propertiesStream.available() = " + propertiesStream.available() );

        Properties properties = new Properties();
        properties.load( propertiesStream );

        for (String propName : properties.stringPropertyNames())
        {
//            System.out.println("|"+propName +"|");

            setDomainModelABoxSubstitution( IRI.create( propName ),
                                            IRI.create( properties.getProperty( propName ) ) );
        }
    }

    /**
     * Get the IRI to use in the output A-Box ontology that corresponds to the input OWL concept.
     *
     * @param rdfOwlEntityIRI the IRI of an OWL or RDF builtin concept.
     * @return IRI to use in the output A-Box Ontology
     */
    private IRI getDomainModelABoxSubstitution (IRI rdfOwlEntityIRI)
    {
        return mmDomainObject.get( rdfOwlEntityIRI.toString() );
    }

    //===========================================================================================
    // The following dozen or so methods are convenience methods for obtaining the A-Box version of core
    // OWL concepts.
    //===========================================================================================

    private OWLClass mm_Class ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_CLASS.getIRI() );
        return odf.getOWLClass( iri );
    }

    private OWLClass mm_Individual ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getIRI() );
        return odf.getOWLClass( iri );
    }

    private OWLClass mm_Property ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI() );
        return odf.getOWLClass( iri );
    }

    private OWLObjectProperty mm_type ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.RDF_TYPE.getIRI() );
        return odf.getOWLObjectProperty( iri );
    }

    private OWLObjectProperty mm_subClassOf ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_SUBCLASS_OF.getIRI() );
        return odf.getOWLObjectProperty( iri );
    }

    private OWLObjectProperty mm_subPropertyOf ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF.getIRI() );
        return odf.getOWLObjectProperty( iri );
    }

    private OWLObjectProperty mm_domain ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_DOMAIN.getIRI() );
        return odf.getOWLObjectProperty( iri );
    }

    private OWLObjectProperty mm_range ()
    {
        IRI iri = getDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_RANGE.getIRI() );
        return odf.getOWLObjectProperty( iri );
    }

    private void addImports ()
    {
        OWLImportsDeclaration importOps = odf.getOWLImportsDeclaration( IriUtil.sharpEditorIRI( "ops" ) );

        System.out.println( "add owl:imports: " + importOps );

        oom.applyChange( new AddImport( ontA, importOps ) );
    }

//    private OWLNamedIndividual getA_Class (OWLClassExpression owlClassExpression)
//    {
//        if (owlClassExpression instanceof OWLClass)
//        {
//            IRI tIRI = ((OWLNamedObject) owlClassExpression).getIRI();
//            IRI aIRI = getABoxIRI( tIRI );
//            return odf.getOWLNamedIndividual( aIRI );
//        }
//
//        return null;
//    }

    /**
     * Take the input OWLEntity, create a new IRI from its IRI, return the OWL Individual with that IRI.
     */
    private OWLNamedIndividual getABoxIndividual (OWLEntity owlEntity)
    {

        IRI tIRI = owlEntity.getIRI();
        IRI aIRI = getABoxIRI( tIRI );
        return odf.getOWLNamedIndividual( aIRI );
    }

    //===========================================================================================
    // Methods that add axioms to output ontology given a domain concept from the input ontology
    //===========================================================================================

    private void transform_Class (final OWLClass owlClass)
    {
        OWLNamedIndividual aEntity = getABoxIndividual( owlClass );

        oom.addAxiom( ontA, odf.getOWLClassAssertionAxiom( mm_Class(), aEntity ) );

        for (OWLClassExpression tSuper : owlClass.getSuperClasses( ontT ))
        {
            transform_subClassOf( owlClass, tSuper );
        }

    }

    private void transform_ObjectProperty (final OWLObjectProperty owlProperty)
    {
        transform_Property( owlProperty );
    }

    private void transform_DataProperty (final OWLDataProperty owlProperty)
    {
        transform_Property( owlProperty );
    }

    private <R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>> void transform_Property (final OWLProperty<R, P> owlProperty)
    {
        OWLNamedIndividual aEntity = getABoxIndividual( owlProperty );

        oom.addAxiom( ontA, odf.getOWLClassAssertionAxiom( mm_Property(), aEntity ) );

        for (P tSuper : owlProperty.getSuperProperties( ontT ))
        {
            transform_subPropertyOf( owlProperty, tSuper );
        }

        for (OWLClassExpression tSuper : owlProperty.getDomains( ontT ))
        {
            transform_domain( owlProperty, tSuper );
        }

        for (OWLPropertyRange tSuper : owlProperty.getRanges( ontT ))
        {
            transform_range( owlProperty, tSuper );
        }
    }

    private void transform_subClassOf (final OWLClassExpression owlSubClass,
                                       final OWLClassExpression owlSuperClass)
    {
        if (owlSubClass instanceof OWLEntity && owlSuperClass instanceof OWLEntity)
        {
            OWLNamedIndividual x = getABoxIndividual( (OWLEntity) owlSubClass );
            OWLNamedIndividual y = getABoxIndividual( (OWLEntity) owlSuperClass );

            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_subClassOf(), x, y );

            oom.addAxiom( ontA, axiom );
        }

    }

    private void transform_subPropertyOf (final OWLPropertyExpression owlSubProperty,
                                          final OWLPropertyExpression owlSuperProperty)
    {

        if (owlSubProperty instanceof OWLEntity && owlSuperProperty instanceof OWLEntity)
        {
            OWLNamedIndividual x = getABoxIndividual( (OWLEntity) owlSubProperty );
            OWLNamedIndividual y = getABoxIndividual( (OWLEntity) owlSuperProperty );

            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_subPropertyOf(), x, y );

            oom.addAxiom( ontA, axiom );
        }

    }

    private void transform_domain (final OWLPropertyExpression owlProperty,
                                   final OWLClassExpression owlDomain)
    {

        if (owlProperty instanceof OWLEntity && owlDomain instanceof OWLEntity)
        {
            OWLNamedIndividual x = getABoxIndividual( (OWLEntity) owlProperty );
            OWLNamedIndividual y = getABoxIndividual( (OWLEntity) owlDomain );

            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_domain(), x, y );

            oom.addAxiom( ontA, axiom );
        }

    }

    private void transform_range (final OWLPropertyExpression owlProperty,
                                  final OWLPropertyRange owlRange)
    {

        if (owlProperty instanceof OWLEntity && owlRange instanceof OWLEntity)
        {
            OWLNamedIndividual x = getABoxIndividual( (OWLEntity) owlProperty );
            OWLNamedIndividual y = getABoxIndividual( (OWLEntity) owlRange );

            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_range(), x, y );

            oom.addAxiom( ontA, axiom );
        }

    }

}
