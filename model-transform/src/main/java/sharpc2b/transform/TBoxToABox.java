package sharpc2b.transform;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.Set;
import java.util.TreeSet;

/**
 * User: rk Date: 5/8/13
 */
public class TBoxToABox
{

    public TBoxToABox ()
    {
        super();
    }

    public void populateABox (OWLOntology tboxDomainModel,
                              OWLOntology aboxDomainModel)
    {
        this.tboxModel = tboxDomainModel;
        this.aboxModel = aboxDomainModel;

//        oom = OWLManager.createOWLOntologyManager();
        oom = aboxDomainModel.getOWLOntologyManager();
        odf = oom.getOWLDataFactory();

        onts = new TreeSet<OWLOntology>();

        onts.add( tboxModel );
        onts.add( aboxModel );

        initNamespaces();
        initDomainMetaModelEntities();
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
        return pm;
    }

    private void initNamespaces ()
    {
        pm = new DefaultPrefixManager();

        pm.setDefaultPrefix( this.aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "a:", this.aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "t:", this.tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        pm.setPrefix( "mm:", aboxDomainMetaModelIRI.toString() + "#" );
    }

    private void initDomainMetaModelEntities ()
    {
        mm_Class = odf.getOWLClass( "mm:a_Class", getPrefixManager() );
        mm_Individual = odf.getOWLClass( "mm:a_Individual", getPrefixManager() );
        mm_Property = odf.getOWLClass( "mm:a_Property", getPrefixManager() );
        mm_type = odf.getOWLObjectProperty( "mm:a_type", getPrefixManager() );
        mm_subClassOf = odf.getOWLObjectProperty( "mm:a_subClassOf", getPrefixManager() );
        mm_subPropertyOf = odf.getOWLObjectProperty( "mm:a_subPropertyOf", getPrefixManager() );
        mm_domain = odf.getOWLObjectProperty( "mm:a_domain", getPrefixManager() );
        mm_range = odf.getOWLObjectProperty( "mm:a_range", getPrefixManager() );
    }

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

        oom.addAxiom( aboxModel, odf.getOWLClassAssertionAxiom( mm_Class, aEntity ) );

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

        oom.addAxiom( aboxModel, odf.getOWLClassAssertionAxiom( mm_Property, aEntity ) );

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
            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_subClassOf, x, y );
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
            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_subPropertyOf, x, y );
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
            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_domain, x, y );
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
            OWLAxiom axiom = odf.getOWLObjectPropertyAssertionAxiom( mm_range, x, y );
            oom.addAxiom( aboxModel, axiom );
        }

    }

//    public static IRI getAboxDomainMetaModelIRI ()
//    {
//        return aboxDomainMetaModelIRI;
//    }
//
//    public static void setAboxDomainMetaModelIRI (IRI aboxDomainMetaModelIRI)
//    {
//        TBoxToABox.aboxDomainMetaModelIRI = aboxDomainMetaModelIRI;
//    }
//
//    public OWLOntologyManager getOom ()
//    {
//        return oom;
//    }
//
//    public void setOom (OWLOntologyManager oom)
//    {
//        this.oom = oom;
//    }
//
//    public OWLDataFactory getOdf ()
//    {
//        return odf;
//    }
//
//    public void setOdf (OWLDataFactory odf)
//    {
//        this.odf = odf;
//    }
//
//    public PrefixManager getPm ()
//    {
//        return pm;
//    }
//
//    public void setPm (PrefixManager pm)
//    {
//        this.pm = pm;
//    }
//
//    public OWLOntology getTboxModel ()
//    {
//        return tboxModel;
//    }
//
//    public void setTboxModel (OWLOntology tboxModel)
//    {
//        this.tboxModel = tboxModel;
//    }
//
//    public OWLOntology getAboxModel ()
//    {
//        return aboxModel;
//    }
//
//    public void setAboxModel (OWLOntology aboxModel)
//    {
//        this.aboxModel = aboxModel;
//    }
//
//    public Set<OWLOntology> getOnts ()
//    {
//        return onts;
//    }
//
//    public void setOnts (Set<OWLOntology> onts)
//    {
//        this.onts = onts;
//    }
//
//    public OWLClass getMm_Class ()
//    {
//        return mm_Class;
//    }
//
//    public void setMm_Class (OWLClass mm_Class)
//    {
//        this.mm_Class = mm_Class;
//    }
//
//    public OWLClass getMm_Individual ()
//    {
//        return mm_Individual;
//    }
//
//    public void setMm_Individual (OWLClass mm_Individual)
//    {
//        this.mm_Individual = mm_Individual;
//    }
//
//    public OWLClass getMm_Property ()
//    {
//        return mm_Property;
//    }
//
//    public void setMm_Property (OWLClass mm_Property)
//    {
//        this.mm_Property = mm_Property;
//    }
//
//    public OWLObjectProperty getMm_type ()
//    {
//        return mm_type;
//    }
//
//    public void setMm_type (OWLObjectProperty mm_type)
//    {
//        this.mm_type = mm_type;
//    }
//
//    public OWLObjectProperty getMm_subClassOf ()
//    {
//        return mm_subClassOf;
//    }
//
//    public void setMm_subClassOf (OWLObjectProperty mm_subClassOf)
//    {
//        this.mm_subClassOf = mm_subClassOf;
//    }
//
//    public OWLObjectProperty getMm_subPropertyOf ()
//    {
//        return mm_subPropertyOf;
//    }
//
//    public void setMm_subPropertyOf (OWLObjectProperty mm_subPropertyOf)
//    {
//        this.mm_subPropertyOf = mm_subPropertyOf;
//    }
//
//    public OWLObjectProperty getMm_domain ()
//    {
//        return mm_domain;
//    }
//
//    public void setMm_domain (OWLObjectProperty mm_domain)
//    {
//        this.mm_domain = mm_domain;
//    }
//
//    public OWLObjectProperty getMm_range ()
//    {
//        return mm_range;
//    }
//
//    public void setMm_range (OWLObjectProperty mm_range)
//    {
//        this.mm_range = mm_range;
//    }

    /**
     * Base namespace IRI for the Domain meta-Model entities.
     */
    private static IRI aboxDomainMetaModelIRI = IRI
            .create( "http://" + "asu.edu/sharpc2b/rk/" + "SharpOwlABoxDomainMetaModel" );

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

    private OWLClass mm_Class;

    private OWLClass mm_Individual;

    private OWLClass mm_Property;

    private OWLObjectProperty mm_type;

    private OWLObjectProperty mm_subClassOf;

    private OWLObjectProperty mm_subPropertyOf;

    private OWLObjectProperty mm_domain;

    private OWLObjectProperty mm_range;
}
