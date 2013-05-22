package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLAnnotationProperty
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
import org.semanticweb.owlapi.model.PrefixManager
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk
 * Date: 5/7/13
 *
 * The initial step-by-step development version of class TBoxToABox.  Take the domain model in
 * inputOntFile, represented with OWL Classes and Properties, and convert to an Individual-based (A-Box)
 * version.
 */

@RunWith(JUnit4.class)
class TADomainModelTest extends GroovyTestCase {

//    static String testResourcesPath = "/Users/rk/asu/prj/sharp-editor/model-transform/src/test/resources";
    static String inputOntUriCorePath = "asu.edu/sharpc2b/rk/ClinicalDomain"
//    static File inputOntFile = new File( "/Users/rk/VOM/export/http/" + inputOntUriCorePath +
//            ".ofn" );
//    static File inputOntFile = new File( testResourcesPath + "/onts/in/ClinicalDomainT.ofn" );
    static File inputOntFile = FileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.ofn" );
    static IRI inputOntIRI = IRI.create( "http://", inputOntUriCorePath );

    static IRI mmaIRI = IRI.create( "asu.edu/sharpc2b/rk/SharpOwlABoxMetaModel" );
//    static File mmaFile = new File( testResourcesPath + "/onts/in/SharpOwlABoxMetaModel.ofn" );
    static File mmaFile = FileUtil.getFileInTestResourceDir( "onts/in/SharpOwlABoxMetaModel.ofn" );

    static IRI outputOntIRI = IRI.create( "http://asu.edu/sharpc2b/rk/ClinicalDomainInsts" );
//    static File outputOntFile = new File( testResourcesPath + "/onts/out/ClinicalDomainInsts.ofn" );
    static File outputOntFile = FileUtil.getFileInTestResourceDir( "onts/out/ClinicalDomainInsts.ofn" );


    OWLOntologyManager oom;
    OWLDataFactory odf;

    PrefixManager pm;

    OWLOntology mma;
    OWLOntology onta;
    OWLOntology ontt;
    Set<OWLOntology> onts;

    OWLClass topCodeClass;
    OWLObjectProperty refinesProp;
    OWLObjectProperty skosBroaderTransitive;
    OWLAnnotationProperty prefLabelProp;
    OWLDataProperty icd9Prop;

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
//        aboxModel = oom.loadOntologyFromOntologyDocument( new File(
//                ontologiesHttpFileRoot + aOntRelPath + ".ofn" ) );

        ontt = oom.loadOntologyFromOntologyDocument( inputOntFile );
//        aboxModel = oom.createOntology( outputOntIRI );

        onts = new HashSet<OWLOntology>();
        onts.add( mma );
        onts.add( ontt );
        onts.add( onta );
    }

    @After
    void tearDown () {

    }

//        addCommonAxioms();
//        addAxiomsForCodes();
//
//        setUpOntologyFormat();
//        serialize();

    def doClasses () {
        def classes = ontt.getClassesInSignature( false );
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

        oom.addAxiom( onta, isClass );

        for (OWLClassExpression tSuper : owlClass.getSuperClasses( ontt )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLClass;
            OWLClass oClass = (OWLClass) tSuper;
//            OWLNamedIndividual iSuper = iClass( oClass );
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subClassOf = odf.getOWLObjectPropertyAssertionAxiom( aSubClassOf, iClass, iSuper );
            oom.addAxiom( onta, subClassOf );
        }
    }

    def doObjectProperty (final OWLObjectProperty owlProperty) {
        String localName = getLocalName( owlProperty );
        OWLNamedIndividual iProperty = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        OWLAxiom isProperty = odf.getOWLClassAssertionAxiom( aProperty, iProperty );

        oom.addAxiom( onta, isProperty );

        for (OWLPropertyExpression tSuper : owlProperty.getSuperProperties( ontt )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLProperty;
            OWLProperty oProperty = (OWLProperty) tSuper;
//            OWLNamedIndividual iSuper = iProperty( oProperty );
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subPropertyOf = odf.getOWLObjectPropertyAssertionAxiom( aSubPropertyOf, iProperty, iSuper );
            oom.addAxiom( onta, subPropertyOf );
        }

        for (OWLClassExpression cl : owlProperty.getDomains( ontt )) {

            OWLClass oClass = (OWLClass) cl;
//            OWLNamedIndividual iSuper = iClass( oClass );
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom domain = odf.getOWLObjectPropertyAssertionAxiom( aDomain, iProperty, iSuper );
            oom.addAxiom( onta, domain );
        }
        for (OWLClassExpression cl : owlProperty.getRanges( ontt )) {

            OWLClass oClass = (OWLClass) cl;
//            OWLNamedIndividual iSuper = iClass( oClass );
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom range = odf.getOWLObjectPropertyAssertionAxiom( aRange, iProperty, iSuper );
            oom.addAxiom( onta, range );
        }

    }

    def doDataProperty (final OWLDataProperty owlProperty) {
        String localName = getLocalName( owlProperty );
        OWLNamedIndividual iProperty = odf.getOWLNamedIndividual( "mma:" + localName, pm );
        OWLAxiom isProperty = odf.getOWLClassAssertionAxiom( aProperty, iProperty );

        oom.addAxiom( onta, isProperty );

        for (OWLPropertyExpression tSuper : owlProperty.getSuperProperties( ontt )) {

//            println tSuper;
//            println tSuper.class;
            assert tSuper instanceof OWLProperty;
            OWLProperty oProperty = (OWLProperty) tSuper;
//            OWLNamedIndividual iSuper = iProperty( oProperty );
            String localNameSuper = getLocalName( tSuper );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom subPropertyOf = odf.getOWLObjectPropertyAssertionAxiom( aSubPropertyOf, iProperty, iSuper );
            oom.addAxiom( onta, subPropertyOf );
        }

        for (OWLClassExpression cl : owlProperty.getDomains( ontt )) {

            OWLClass oClass = (OWLClass) cl;
//            OWLNamedIndividual iSuper = iClass( oClass );
            String localNameSuper = getLocalName( oClass );
            OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameSuper, pm );
            OWLAxiom domain = odf.getOWLObjectPropertyAssertionAxiom( aDomain, iProperty, iSuper );
            oom.addAxiom( onta, domain );
        }
        for (OWLDataRange cl : owlProperty.getRanges( ontt )) {

            if (cl instanceof OWLNamedObject) {
//            OWLClass oClass = (OWLClass) cl;
//            OWLNamedIndividual iSuper = iClass( oClass );
                String localNameRange = getLocalName( (OWLNamedObject) cl );
                OWLNamedIndividual iSuper = odf.getOWLNamedIndividual( "mma:" + localNameRange, pm );
                OWLAxiom range = odf.getOWLObjectPropertyAssertionAxiom( aRange, iProperty, iSuper );
                oom.addAxiom( onta, range );
            }
        }

    }

    void initMetaModel () {

        mma = oom.createOntology( mmaIRI );

        aClass = odf.getOWLClass( "mma:a_Class", pm );
        aIndividual = odf.getOWLClass( "mma:a_Individual", pm );
        aProperty = odf.getOWLClass( "mma:a_Property", pm );
        aType = odf.getOWLObjectProperty( "mma:a_type", pm );
        aSubClassOf = odf.getOWLObjectProperty( "mma:a_subClassOf", pm );
        aSubPropertyOf = odf.getOWLObjectProperty( "mma:a_subPropertyOf", pm );
        aDomain = odf.getOWLObjectProperty( "mma:a_domain", pm );
        aRange = odf.getOWLObjectProperty( "mma:a_range", pm );

        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aClass ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aIndividual ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aProperty ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aSubClassOf ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aSubPropertyOf ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aType ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aDomain ) );
        oom.addAxiom( mma, odf.getOWLDeclarationAxiom( aRange ) );
    }

    def initNamespaces () {

//        icd9ClassesIriString = sharpUriRoot + "/" + "icd9Classes"
//        icd9ClassesNamespace = icd9ClassesIriString + "#"
//        icd9ClassesIRI = new IRI( icd9ClassesIriString );
//        icd9ClassesDocIRI = new IRI( ontRootPath + "/" + "icd9Classes" + ".ofn" );

        pm = new DefaultPrefixManager();
        pm.setPrefix( "a:", outputOntIRI.toString() + "#" );
        pm.setPrefix( "t:", inputOntIRI.toString() + "#" );
        pm.setPrefix( "mma:", mmaIRI.toString() + "#" );
    }

    def createNewOntology () {

        onta = oom.createOntology( outputOntIRI );
    }

    void addImports () {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        /* imports MM */

        assert odf;
        assert mma;
        assert mma.getOntologyID();

        importsAxiom = odf.getOWLImportsDeclaration( mma.getOntologyID().getOntologyIRI() );
        imp = new AddImport( onta, importsAxiom );
        oom.applyChange( imp );

//        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
//        imp = new AddImport( aboxModel, importsAxiom );
//        oom.applyChange( imp );
    }

    void saveOutputOntology () {

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( onta, oFormat );
        IRI docIRI = IRI.create( outputOntFile );

        oom.saveOntology( onta, oFormat, docIRI );
    }

    /**
     * Only needed if want to save the meta model ontology to a file.
     */
    void saveMetaModelOntology () {

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( mma, oFormat );
        IRI docIRI = IRI.create( mmaFile );

        oom.saveOntology( mma, oFormat, docIRI );
    }

    @Test
    void testRunIt () {

        assert ontt;

        initNamespaces();
        createNewOntology();
        initMetaModel();

        /* Don't really need to import the domain meta-model */
//        addImports();

        doClasses();

        def oProps = ontt.getObjectPropertiesInSignature( false );
        for (OWLObjectProperty p : oProps) {
            doObjectProperty( p );
        }

        def dProps = ontt.getDataPropertiesInSignature( false );
        for (OWLDataProperty p : dProps) {
            doDataProperty( p );
        }

        /*
         * Uncomment the following line to saved the default domain meta model ontology to a file.
         */
//        saveMetaModelOntology();
        saveOutputOntology();
    }

}
