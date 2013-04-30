package sharpc2b.transform

import org.codehaus.groovy.ast.expr.ClassExpression
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.util.DefaultPrefixManager

import java.util.regex.Pattern

//import static org.junit.Assert.assertEquals
//import static org.junit.Assume.*;

//import org.junit.runners.Parameterized
//import org.junit.runners.Parameterized.Parameters

//import org.junit.Ignore;
//import org.junit.experimental.theories.*
//import static org.junit.Assume.assumeTrue as assume

/**
 * User: rk
 * Date: 4/30/13
 *
 * An empty Groovy TestCase to use as a Copy-n-Paste template to create a new Groovy TestCase.
 */
@RunWith(JUnit4.class)
public class Icd9TboxToAboxTest extends GroovyTestCase {

    static String ontologiesHttpFileRoot =
        "/Users/rk/asu/prj" +
                "/sharp-editor/model-transform/src/test/resources/http";
    static String ontologiesDocUriRoot = "file:" + ontologiesHttpFileRoot;

    static String commonCodesOntsRelPath = "/asu.edu/sharpc2b/codes/03/";

    /*
     * SKOS
     */
    static String skosRelPath = "/www.w3.org/2004/02/skos/core";
    static String skosRootPath = ontologiesHttpFileRoot + skosRelPath;
    static String skosUriPath = "http:/" + skosRelPath;
    static String skosNamespace = skosUriPath + "#";
    static IRI skosIRI = new IRI( skosUriPath );
    static IRI skosDocIRI = new IRI( ontologiesDocUriRoot + skosRelPath + ".rdf" );

    /*
     * A-Box ICD9 Codes Ontology
     */
    static String aOntRelPath = commonCodesOntsRelPath + "icd9-abox";
    static String aUriPath = "http:/" + aOntRelPath;
    static String aNamespace = aUriPath + "#";
    static IRI aIRI = new IRI( aUriPath );
    static IRI aDocIRI = new IRI( ontologiesDocUriRoot + aOntRelPath + ".ofn" );

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static String tOntRelPath = commonCodesOntsRelPath + "icd9-classes";
    static String tUriPath = "http:/" + tOntRelPath;
    static String tNamespace = tUriPath + "#";
    static IRI tIRI = new IRI( tUriPath );
    static IRI tDocIRI = new IRI( ontologiesDocUriRoot + tOntRelPath + ".ofn" );

    OWLOntologyManager oom;
    OWLDataFactory odf;

    PrefixManager pm;

    OWLOntology skos;
    OWLOntology onta;
    OWLOntology ontt;
    Set<OWLOntology> onts;

    OWLClass topCodeClass;
    OWLObjectProperty refinesProp;
    OWLObjectProperty skosBroaderTransitive;
    OWLAnnotationProperty prefLabelProp;
    OWLDataProperty icd9Prop;

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
//        onta = oom.loadOntologyFromOntologyDocument( new File(
//                ontologiesHttpFileRoot + aOntRelPath + ".ofn" ) );
        println "SKOS Doc IRI = <${skosDocIRI}>";

        assert new File( skosDocIRI.toURI() ).exists();
        assert new File( tDocIRI.toURI() ).exists();

        skos = oom.loadOntologyFromOntologyDocument( skosDocIRI );
        ontt = oom.loadOntologyFromOntologyDocument( tDocIRI );

        onts = new HashSet<OWLOntology>();
        onts.add( skos );
        onts.add( ontt );
    }

    @After
    void tearDown () {

    }

    OWLOntology createABoxOntology () {

        setUp();

        initNamespaces();
        createNewOntology();
        addImports();
        initObjects();
        addCommonAxioms();
        addAxiomsForCodes();

        setUpOntologyFormat();
        serialize();

        return ontt;
    }

    @Test
//    @Ignore
    void testRunIt () {

        onta = createABoxOntology();
        assert ontt;
    }

    def initNamespaces () {

//        icd9ClassesIriString = sharpUriRoot + "/" + "icd9Classes"
//        icd9ClassesNamespace = icd9ClassesIriString + "#"
//        icd9ClassesIRI = new IRI( icd9ClassesIriString );
//        icd9ClassesDocIRI = new IRI( ontRootPath + "/" + "icd9Classes" + ".ofn" );

        pm = new DefaultPrefixManager( aNamespace );
        pm.setPrefix( "a:", aNamespace );
        pm.setPrefix( "t:", tNamespace );
        pm.setPrefix( "skos:", skosNamespace );
    }

    def createNewOntology () {

        onta = oom.createOntology( aIRI );
    }

    def addImports () {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        importsAxiom = odf.getOWLImportsDeclaration( ontt.getOntologyID().getOntologyIRI() );
        imp = new AddImport( onta, importsAxiom );
        oom.applyChange( imp );

        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
        imp = new AddImport( onta, importsAxiom );
        oom.applyChange( imp );
    }

    def initObjects () {

        topCodeClass = odf.getOWLClass( "a:ICD9_Concept", pm );
        refinesProp = odf.getOWLObjectProperty( "t:refines", pm );
        icd9Prop = odf.getOWLDataProperty( "skos:notation", pm );
        skosBroaderTransitive = odf.getOWLObjectProperty( "skos:broaderTransitive", pm );
        prefLabelProp = odf.getOWLAnnotationProperty( "skos:prefLabel", pm );
    }

    def addCommonAxioms () {

//        Set<OWLAxiom> axioms = new TreeSet();
//        axioms.add( odf.getOWLSubObjectPropertyOfAxiom( skosBroaderTransitive, refinesProp ) );
//        oom.addAxioms( ontt, axioms );
    }

    def addAxiomsForCodes () {

        Set<OWLEquivalentClassesAxiom> eqAxioms = ontt.getAxioms( AxiomType.EQUIVALENT_CLASSES, false );
        assert eqAxioms.size() > 0;

        for (OWLEquivalentClassesAxiom ax:eqAxioms) {
            addAxiomsForEquivalentClassesAxiom( (OWLEquivalentClassesAxiom) ax  );
        }
    }

    def addAxiomsForEquivalentClassesAxiom (OWLEquivalentClassesAxiom ax) {

        Set<OWLClassExpression> namedClasses = ax.getNamedClasses() ;
        Set<OWLClassExpression> defClasses = ax.getClassExpressionsMinus( namedClasses );

        assertEquals( 1, namedClasses.size());
        assertEquals( 1, defClasses.size());

        OWLClass conceptClass = namedClasses.iterator().next() ;
        OWLAnonymousClassExpression tDef = defClasses.iterator().next() ;;

        Set<OWLClassExpression> codeClasses = topCodeClass.getSubClasses( ontt );
        assert codeClasses.size() > 0;

        for (OWLClassExpression cl : codeClasses) {
            addAxiomsForCode( (OWLClass) cl  );
        }
    }

    def addAxiomsForCode (OWLClass codeInd) {

        Set<OWLAnnotationAssertionAxiom> annos = onta.getAnnotationAssertionAxioms( codeInd.getIRI() );
        Set<OWLAnnotationAssertionAxiom> labelAnnos = annos.findAll {
            it.getProperty().equals( prefLabelProp )
        };
        assert 1 == labelAnnos.size();
        OWLAnnotationValue value = labelAnnos.iterator().next().getValue();
        assert value instanceof OWLLiteral;
        String label = ((OWLLiteral) value).getLiteral();
        String name = localName( label );

        OWLClass codeClass = odf.getOWLClass( ":" + name, pm );

        addDefinitionUsingIndividual( codeInd, codeClass )
        addDefinitionUsingCodeValue( codeInd, codeClass )
    }

    def addDefinitionUsingIndividual (OWLNamedIndividual codeInd, OWLClass codeClass) {

        OWLObjectHasValue hasCodeValue = odf.getOWLObjectHasValue( refinesProp, codeInd );
        OWLObjectIntersectionOf codeConceptAndValue = odf.getOWLObjectIntersectionOf( hasCodeValue,
                topCodeClass );
        OWLEquivalentClassesAxiom eqAxiom = odf.getOWLEquivalentClassesAxiom( codeClass,
                codeConceptAndValue );
        assert eqAxiom;
        oom.addAxiom( ontt, eqAxiom );
    }

    def addDefinitionUsingCodeValue (OWLNamedIndividual codeInd, OWLClass codeClass) {

        Set<OWLLiteral> codeValues = codeInd.getDataPropertyValues( icd9Prop, onta );
//        println codeValues.size();
        if (codeValues.isEmpty()) {
            println "no icd9 code: " + codeInd;
            return;
        }
        OWLLiteral litValue = codeValues.iterator().next();
        assert litValue;
//        println "icd9 code = " + litValue;
//        OWLLiteral litValue = odf.getOWLLiteral( codeValue );
        OWLDataHasValue hasCodeValue = odf.getOWLDataHasValue( icd9Prop, litValue );
        OWLObjectSomeValuesFrom some = odf.getOWLObjectSomeValuesFrom( refinesProp, hasCodeValue );

        OWLObjectIntersectionOf codeConceptAndValue = odf.getOWLObjectIntersectionOf( some,
                topCodeClass );
        OWLSubClassOfAxiom eqAxiom = odf.getOWLSubClassOfAxiom( codeConceptAndValue, codeClass );
        assert eqAxiom;
        oom.addAxiom( ontt, eqAxiom );
    }

    String localName (String s) {

        Pattern pat
        pat = ~/[^a-zA-Z0-9_]/;
        s.replaceAll( pat ) { "_" }
    }

    def setUpOntologyFormat () {

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( ontt, oFormat );
    }

    def serialize () {

        oom.saveOntology( ontt, tDocIRI );
    }

}
