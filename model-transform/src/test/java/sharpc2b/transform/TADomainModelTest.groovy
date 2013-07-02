package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAxiom
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLDataProperty
import org.semanticweb.owlapi.model.OWLDataRange
import org.semanticweb.owlapi.model.OWLImportsDeclaration
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLNamedObject
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.model.OWLProperty
import org.semanticweb.owlapi.model.OWLPropertyExpression
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil
import sharpc2b.transform.test.TestUtil

/**
 * User: rk
 * Date: 5/7/13
 *
 * The initial step-by-step development version of class TBoxToABox.  Take the domain model in
 * inputOntFile, represented with OWL Classes and Properties, and convert to an Individual-based (A-Box)
 * version.
 */

class TADomainModelTest extends GroovyTestCase {

    static File inputOntFile = TestFileUtil.getResourceAsFile( "/ontologies/in/ClinicalDomainT.ofn" );

    /**
     * Meta-model.  A-box versions of Class, Property, etc.  Perhaps don't really need to load.
     */
    static File mmaFile = TestFileUtil.getResourceAsFile( "/ontologies/in/ABoxMetaModel.ofn" );
    static File mmaOutFile = TestFileUtil.getResourceAsFile( "/ontologies/out/SharpOwlABoxMetaModel.ofn" );

    static IRI outputOntIRI = TestUtil.testIRI( "ClinicalDomainInsts" );
    static File outputOntFile = TestFileUtil.getResourceAsFile( "/ontologies/out/ClinicalDomainA.ofn" );


    OWLOntologyManager oom;
    OWLDataFactory odf;

    PrefixOWLOntologyFormat pm;

    OWLOntology mma;
    OWLOntology ontA;
    OWLOntology ontT;
    Set<OWLOntology> onts;
    IRI inputOntIRI
    IRI mmaIRI

//    OWLClass topCodeClass;
//    OWLObjectProperty refinesProp;
//    OWLObjectProperty skosBroaderTransitive;
//    OWLAnnotationProperty prefLabelProp;
//    OWLDataProperty icd9Prop;

    OWLClass aClass;
    OWLClass aIndividual;
    OWLClass aProperty;
    OWLObjectProperty aType;
    OWLObjectProperty aSubClassOf;
    OWLObjectProperty aSubPropertyOf;
    OWLObjectProperty aDomain;
    OWLObjectProperty aRange;

    @BeforeClass
    static void setUpOnce () {

    }

    @AfterClass
    static void tearDownOnce () {

    }

    @Before
    void setUp () {

        oom = OWLManager.createOWLOntologyManager();
        odf = oom.getOWLDataFactory();

//        skos = oom.loadOntologyFromOntologyDocument( new File( skosRootPath + ".rdf" ) );
//        ontA = oom.loadOntologyFromOntologyDocument( new File(
//                ontologiesHttpFileRoot + aOntRelPath + ".ofn" ) );

        ontT = oom.loadOntologyFromOntologyDocument( inputOntFile );
        mma = oom.loadOntologyFromOntologyDocument( mmaFile );
        mmaIRI = mma.getOntologyID().getOntologyIRI()
//        ontA = oom.createOntology( outputOntIRI );
        assert mma;

        onts = new HashSet<OWLOntology>();
        onts.add( mma );
        onts.add( ontT );
        onts.add( ontA );
    }

    @After
    void tearDown () {

    }

    void testRunIt () {

        assert ontT;

        inputOntIRI = ontT.getOntologyID().getOntologyIRI();

        initNamespaces();
        createNewOntology();

        initMetaModelObjects();

        /* Don't really need to import the domain meta-model */
//        addImports();

        doClasses();

        def oProps = ontT.getObjectPropertiesInSignature( false );
        for (OWLObjectProperty p : oProps) {
            doObjectProperty( p );
        }

        def dProps = ontT.getDataPropertiesInSignature( false );
        for (OWLDataProperty p : dProps) {
            doDataProperty( p );
        }

        saveOutputOntology();
    }

    def doClasses () {
        def classes = ontT.getClassesInSignature( false );
        for (OWLClass cl : classes) {
            doClass( cl );
        }
    }

    String getLocalName (final OWLNamedObject oo) {
        String iriString = oo.getIRI().toString();
        int pos = iriString.lastIndexOf( '#' );
        if (pos < 0) {
            pos = iriString.lastIndexOf( '/' );
        }
        return iriString.substring( pos + 1 );
    }

    def iClass (OWLClass owlClass) {

        String localName = getLocalName( owlClass );
        OWLNamedIndividual iClass = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        return iClass;
    }

    def doClass (final OWLClass owlClass) {
        String localName = getLocalName( owlClass );
        OWLNamedIndividual iClass = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        OWLAxiom isClass = odf.getOWLClassAssertionAxiom( aClass, iClass );

        oom.addAxiom( ontA, isClass );

        for (OWLClassExpression tSuper : owlClass.getSuperClasses( ontT )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLClass;
            OWLClass oClass = (OWLClass) tSuper;
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subClassOf = odf.getOWLObjectPropertyAssertionAxiom( aSubClassOf, iClass, iSuper );
            oom.addAxiom( ontA, subClassOf );
        }
    }

    def doObjectProperty (final OWLObjectProperty owlProperty) {
        String localName = getLocalName( owlProperty );
        OWLNamedIndividual iProperty = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        OWLAxiom isProperty = odf.getOWLClassAssertionAxiom( aProperty, iProperty );

        oom.addAxiom( ontA, isProperty );

        for (OWLPropertyExpression tSuper : owlProperty.getSuperProperties( ontT )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLProperty;
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subPropertyOf = odf.getOWLObjectPropertyAssertionAxiom( aSubPropertyOf, iProperty, iSuper );
            oom.addAxiom( ontA, subPropertyOf );
        }

        for (OWLClassExpression cl : owlProperty.getDomains( ontT )) {

            OWLClass oClass = (OWLClass) cl;
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom domain = odf.getOWLObjectPropertyAssertionAxiom( aDomain, iProperty, iSuper );
            oom.addAxiom( ontA, domain );
        }
        for (OWLClassExpression cl : owlProperty.getRanges( ontT )) {

            OWLClass oClass = (OWLClass) cl;
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom range = odf.getOWLObjectPropertyAssertionAxiom( aRange, iProperty, iSuper );
            oom.addAxiom( ontA, range );
        }

    }

    def doDataProperty (final OWLDataProperty owlProperty) {
        String localName = getLocalName( owlProperty );
        OWLNamedIndividual iProperty = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        OWLAxiom isProperty = odf.getOWLClassAssertionAxiom( aProperty, iProperty );

        oom.addAxiom( ontA, isProperty );

        for (OWLPropertyExpression tSuper : owlProperty.getSuperProperties( ontT )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLProperty;
            OWLProperty oProperty = (OWLProperty) tSuper;
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subPropertyOf = odf.getOWLObjectPropertyAssertionAxiom( aSubPropertyOf, iProperty, iSuper );
            oom.addAxiom( ontA, subPropertyOf );
        }

        for (OWLClassExpression cl : owlProperty.getDomains( ontT )) {

            OWLClass oClass = (OWLClass) cl;
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom domain = odf.getOWLObjectPropertyAssertionAxiom( aDomain, iProperty, iSuper );
            oom.addAxiom( ontA, domain );
        }
        for (OWLDataRange cl : owlProperty.getRanges( ontT )) {

            if (cl instanceof OWLNamedObject) {
                String localNameRange = getLocalName( (OWLNamedObject) cl );
                OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameRange, pm );
                OWLAxiom range = odf.getOWLObjectPropertyAssertionAxiom( aRange, iProperty, iSuper );
                oom.addAxiom( ontA, range );
            }
        }

    }

    void initMetaModelObjects () {

        aClass = odf.getOWLClass( "mma:Class", pm );
        aIndividual = odf.getOWLClass( "mma:Individual", pm );
        aProperty = odf.getOWLClass( "mma:Property", pm );
        aType = odf.getOWLObjectProperty( "mma:type", pm );
        aSubClassOf = odf.getOWLObjectProperty( "mma:subClassOf", pm );
        aSubPropertyOf = odf.getOWLObjectProperty( "mma:subPropertyOf", pm );
        aDomain = odf.getOWLObjectProperty( "mma:domain", pm );
        aRange = odf.getOWLObjectProperty( "mma:range", pm );
    }

    def initNamespaces () {

        pm = IriUtil.getDefaultSharpOntologyFormat();
        pm.setPrefix( "a:", outputOntIRI.toString() + "#" );
        pm.setPrefix( "t:", inputOntIRI.toString() + "#" );
        pm.setPrefix( "mma:", mmaIRI.toString() + "#" );
    }

    def createNewOntology () {

        ontA = oom.createOntology( outputOntIRI );
    }

    void addImports () {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        assert odf;
        assert mma;
        assert mma.getOntologyID();

        importsAxiom = odf.getOWLImportsDeclaration( mma.getOntologyID().getOntologyIRI() );
        imp = new AddImport( ontA, importsAxiom );
        oom.applyChange( imp );

//        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
//        imp = new AddImport( ontA, importsAxiom );
//        oom.applyChange( imp );
    }

    void saveOutputOntology () {

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( ontA, oFormat );
        IRI docIRI = IRI.create( outputOntFile );

        oom.saveOntology( ontA, oFormat, docIRI );
    }

}
