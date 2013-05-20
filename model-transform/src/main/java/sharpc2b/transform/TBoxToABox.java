package sharpc2b.transform;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * User: rk Date: 5/8/13
 */
public class TBoxToABox
{

    /**
     * Base namespace IRI for the Domain meta-Model entities.
     */
    final static IRI aboxDomainMetaModelIRI = IRI
            .create( "http://asu.edu/sharpc2b" + "/" + "SharpOwlABoxDomainMetaModel" );

    final static String resourceName = "/DomainMetaModelABoxEntities.properties";

    Map<String, IRI> mmDomainObject;

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

    private DefaultPrefixManager pm;

    /**
     * The source T-Box Domain Model Ontology.
     */
    private OWLOntology tboxModel;

    /**
     * The destination A-Box version of the Domain Model Ontology.
     */
    private OWLOntology aboxModel;

    /**
     * Set of Ontologies to search in case want OWLOntologyManager to search multiple Ontologies.
     */
    private Set<OWLOntology> onts;

    /*
     *  A-Box Domain Meta-Model objects.
     */

//    private OWLClass mm_Class;
//
//    private OWLClass mm_Individual;
//
//    private OWLClass mm_Property;
//
//    private OWLObjectProperty mm_type;
//
//    private OWLObjectProperty mm_subClassOf;
//
//    private OWLObjectProperty mm_subPropertyOf;
//
//    private OWLObjectProperty mm_domain;
//
//    private OWLObjectProperty mm_range;

    //==================================================================================

    public TBoxToABox ()
    {
        super();

//        initDomainModelABoxSubstitution();
        try
        {
            initDomainModelABoxSubstitutionsFromPropertiesFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //==================================================================================

    public void populateABox (OWLOntology tboxDomainModel,
                              OWLOntology aboxDomainModel)
    {
        this.tboxModel = tboxDomainModel;
        this.aboxModel = aboxDomainModel;

        oom = aboxDomainModel.getOWLOntologyManager();
        if (oom == null)
        {
            oom = OWLManager.createOWLOntologyManager();
        }
        odf = oom.getOWLDataFactory();

        onts = new TreeSet<OWLOntology>();

        onts.add( tboxModel );
        onts.add( aboxModel );

        initNamespaces();
//        initDomainMetaModelEntities();
//        initDomainModelABoxSubstitution();
        addImports();

        for (OWLClass e : this.tboxModel.getClassesInSignature( false ))
        {
            transform_Class( e );
        }

        for (OWLObjectProperty e : this.tboxModel.getObjectPropertiesInSignature( false ))
        {
            transform_ObjectProperty( e );
        }

        for (OWLDataProperty e : this.tboxModel.getDataPropertiesInSignature( false ))
        {
            transform_DataProperty( e );
        }
    }

    public void setDomainModelABoxSubstitution (IRI rdfOwlEntityIRI,
                                                IRI domainModelEntityIRI)
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
        return getPrefixManager().getIRI( "a:" + getLocalName( tIRI ) );
    }

    private PrefixManager getPrefixManager ()
    {
        if (pm == null)
        {
            pm = new DefaultPrefixManager();

            pm.setPrefix( "mm:", aboxDomainMetaModelIRI.toString() + "#" );
            pm.setPrefix( "skos:", Namespaces.SKOS.toString() );
        }
        return pm;
    }

    private void initNamespaces ()
    {
        getPrefixManager();  // Make sure initialized.

        pm.setDefaultPrefix( this.aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "a:", this.aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "t:", this.tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "mm:", aboxDomainMetaModelIRI.toString() + "#" );
        pm.setPrefix( "skos:", Namespaces.SKOS.toString() );
    }

    /**
     * Initialize the configuration for what kind of A-Box entity is created for each kind of builtin RDF &
     * OWL entity to be transformed.  This is initialized with default concept IRIs.  The defaults defined
     * here can be replaced by calling the method setDomainModelABoxSubstitution() with a preferred
     * replacement IRI for each RDF/OWL concept.
     */
    private void initDomainModelABoxSubstitution ()
    {
        mmDomainObject = new TreeMap<String, IRI>();

        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_CLASS.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Class" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_DATATYPE.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Datatype" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_INDIVIDUAL.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Individual" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_NAMED_INDIVIDUAL.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Individual" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_OBJECT_PROPERTY.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Property" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_DATA_PROPERTY.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Property" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.OWL_ANNOTATION_PROPERTY.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_Property" ) );

        setDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_SUBCLASS_OF.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_subClassOf" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_subPropertyOf" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_DOMAIN.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_domain" ) );
        setDomainModelABoxSubstitution( OWLRDFVocabulary.RDFS_RANGE.getIRI(),
                                        getPrefixManager().getIRI( "mm:a_range" ) );
    }

    private void initDomainModelABoxSubstitutionsFromPropertiesFile ()
            throws IOException
    {
        this.mmDomainObject = new TreeMap<String, IRI>();

//        final ClassLoader classLoader;
//        classLoader = this.getClass().getClassLoader();
        InputStream propertiesStream;
        propertiesStream = System.class.getResourceAsStream( resourceName );

        Properties properties = new Properties();
        properties.load( propertiesStream );

        for (String propName : properties.stringPropertyNames())
        {
            setDomainModelABoxSubstitution( IRI.create( propName ),
                                            IRI.create( properties.getProperty( propName ) ) );
        }
    }

    private IRI getDomainModelABoxSubstitution (IRI rdfOwlEntityIRI)
    {
        return mmDomainObject.get( rdfOwlEntityIRI.toString() );
    }

    /*
     * The following dozen or so methods are convenience methods for obtaining the A-Box version of core
     * OWL concepts.
     */

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

//    private OWLClass mm_Individual;
//
//    private OWLClass mm_Property;
//
//    private OWLObjectProperty mm_type;
//
//    private OWLObjectProperty mm_subClassOf;
//
//    private OWLObjectProperty mm_subPropertyOf;
//
//    private OWLObjectProperty mm_domain;
//
//    private OWLObjectProperty mm_range;

    private void addImports ()
    {
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

    private OWLNamedIndividual getABoxIndividual (OWLEntity owlEntity)
    {

        IRI tIRI = owlEntity.getIRI();
        IRI aIRI = getABoxIRI( tIRI );
        return odf.getOWLNamedIndividual( aIRI );
    }

    private void transform_Class (final OWLClass owlClass)
    {

        OWLNamedIndividual aEntity = getABoxIndividual( owlClass );

        oom.addAxiom( aboxModel, odf.getOWLClassAssertionAxiom( mm_Class(), aEntity ) );

        for (OWLClassExpression tSuper : owlClass.getSuperClasses( tboxModel ))
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

        oom.addAxiom( aboxModel, odf.getOWLClassAssertionAxiom( mm_Property(), aEntity ) );

        for (P tSuper : owlProperty.getSuperProperties( tboxModel ))
        {
            transform_subPropertyOf( owlProperty, tSuper );
        }

        for (OWLClassExpression tSuper : owlProperty.getDomains( tboxModel ))
        {
            transform_domain( owlProperty, tSuper );
        }

        for (OWLPropertyRange tSuper : owlProperty.getRanges( tboxModel ))
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
            oom.addAxiom( aboxModel, axiom );
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
            oom.addAxiom( aboxModel, axiom );
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
            oom.addAxiom( aboxModel, axiom );
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
            oom.addAxiom( aboxModel, axiom );
        }

    }

}
