package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil

import java.util.regex.Pattern

/**
 * User: rk
 * Date: 4/30/13
 *
 * This was the first attempt to go in the T-Box to A-Box direction.  As input,
 * decided to use the ontology that was the output of the A to T direction.  That had class definitions
 * based on EquivalentClass definitions and a property named "refines" (or "refinesDisorder").
 *
 * XXX Abandoned.
 *
 * Part way through this process, realized something was kind of wrong with this whole scenario,
 * so stopped.  So this class could be scrapped.  However, the code for navigating through these class
 * definitions seems like it will likely be required at some point.
 */
public class Icd9TboxToAboxTest
extends GroovyTestCase {

    static String commonCodesOntsRelPath = "asu.edu/sharpc2b/codes/03/";

    /*
     * SKOS
     */
    static File skosFile = TestFileUtil.getResourceAsFile( "/ontologies/in/skos-core.rdfxml" );

    /*
     * Original SKOS A-Box ICD9 Codes Ontology.  Imported by icd9-classes (T-Box).
     */
    static File icdFile = TestFileUtil.getResourceAsFile( "/ontologies/in/icd9-pub.ofn" );

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static IRI tIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-classes" );
    static tFile = TestFileUtil.getResourceAsFile( "/ontologies/in/icd9-classes.ofn" )

    /*
     * A-Box ICD9 Codes Ontology
     */
    static IRI aIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-abox" );
//    static aFile = TestFileUtil.getFileForTestOutput( "/ontologies/in/icd9-abox.ofn" )

    OWLOntologyManager oom;
    OWLDataFactory odf;

    PrefixOWLOntologyFormat pm;

    OWLOntology skos;
    OWLOntology icd;
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
//        ontA = oom.loadOntologyFromOntologyDocument( new File(
//                ontologiesHttpFileRoot + aOntRelPath + ".ofn" ) );
//        println "SKOS Doc IRI = <${skosDocIRI}>";
//        println "T-Box classes Doc IRI = <${tDocIRI}>";

        assert skosFile.exists();
//        println "tFile = " + tFile
        assert tFile.exists();

        skos = oom.loadOntologyFromOntologyDocument( skosFile );
        icd = oom.loadOntologyFromOntologyDocument( icdFile );
        ontt = oom.loadOntologyFromOntologyDocument( tFile );

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
//        serialize();

        return ontt;
    }

    @Test
//    @Ignore
    void testRunIt () {

//        ontA = createABoxOntology();
//        assert ontT;
    }

    def initNamespaces () {

//        icd9ClassesIriString = sharpUriRoot + "/" + "icd9Classes"
//        icd9ClassesNamespace = icd9ClassesIriString + "#"
//        icd9ClassesIRI = new IRI( icd9ClassesIriString );
//        icd9ClassesDocIRI = new IRI( ontRootPath + "/" + "icd9Classes" + ".ofn" );

        pm = IriUtil.getDefaultSharpOntologyFormat()
        pm.setDefaultPrefix( aIRI.toString() + "#" );
        pm.setPrefix( "a:", aIRI.toString() + "#" );
        pm.setPrefix( "t:", tIRI.toString() + "#" );
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
//        oom.addAxioms( ontT, axioms );
    }

    def addAxiomsForCodes () {

        Set<OWLEquivalentClassesAxiom> eqAxioms = ontt.getAxioms( AxiomType.EQUIVALENT_CLASSES, false );
        assert eqAxioms.size() > 0;

        for (OWLEquivalentClassesAxiom ax : eqAxioms) {
            addAxiomsForEquivalentClassesAxiom( (OWLEquivalentClassesAxiom) ax );
        }
    }

    def addAxiomsForEquivalentClassesAxiom (OWLEquivalentClassesAxiom ax) {

        Set<OWLClassExpression> namedClasses = ax.getNamedClasses();
        Set<OWLClassExpression> defClasses = ax.getClassExpressionsMinus( namedClasses );

        assertEquals( 1, namedClasses.size() );
        assertEquals( 1, defClasses.size() );

        OWLClass conceptClass = namedClasses.iterator().next();
        OWLAnonymousClassExpression tDef = defClasses.iterator().next(); ;

        Set<OWLClassExpression> codeClasses = topCodeClass.getSubClasses( ontt );
        assert codeClasses.size() > 0;

        for (OWLClassExpression cl : codeClasses) {
            addAxiomsForCode( (OWLClass) cl );
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
            println getClass().getSimpleName().toString() + ": no icd9 code: " + codeInd;
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

//    def serialize () {
//
//        oom.saveOntology( ontt, aFile );
//    }

}
