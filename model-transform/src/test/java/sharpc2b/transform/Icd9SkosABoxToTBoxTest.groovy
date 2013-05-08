package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.util.DefaultPrefixManager


import java.util.regex.Pattern

/**
 * User: rk
 * Date: 4/24/13
 *
 * Transform a SKOS A-Box ICD9 codes ontology into a T-Box ontology.
 *
 * Groovy Test version of TransformIcd9ABoxToTBox.
 */
@RunWith(JUnit4.class)
public class Icd9SkosABoxToTBoxTest extends GroovyTestCase {

    static String ontologiesHttpFileRoot =
        "/Users/rk/asu/prj" +
                "/sharp-editor/model-transform/src/test/resources/http";
    static String ontologiesDocUriRoot = "file:" + ontologiesHttpFileRoot;

    static String sharpCodesOntsRelPath = "/asu.edu/sharpc2b/codes/03/";

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
     * Published ICD9 Codes Ontology
     */
    static String pubCodesOntRelPath = sharpCodesOntsRelPath + "icd9-pub";
//    static String sharpCodesOntRelPath = sharpCodesOntsRelPath +"icd9-Sharp" ;
    static String pubCodesUriPath = "http:/" + pubCodesOntRelPath;
    static String pubCodesNamespace = pubCodesUriPath + "#";
    static IRI pubCodesIRI = new IRI( pubCodesUriPath );
    static IRI pubCodesDocIRI = new IRI( ontologiesDocUriRoot + pubCodesOntRelPath + ".ofn" );

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static String sharpCodesOntRelPath = sharpCodesOntsRelPath + "icd9-classes2";
    static String sharpCodesUriPath = "http:/" + sharpCodesOntRelPath;
    static String sharpCodesNamespace = sharpCodesUriPath + "#";
    static IRI sharpCodesIRI = new IRI( sharpCodesUriPath );
    static IRI sharpCodesDocIRI = new IRI( ontologiesDocUriRoot + sharpCodesOntRelPath + ".ofn" );


    OWLOntologyManager oom;
    OWLDataFactory odf;

    PrefixManager pm;

    OWLOntology skos;
    OWLOntology icd9pub;
    OWLOntology icd9cl;
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

    void setUp () {
        oom = OWLManager.createOWLOntologyManager();
        odf = oom.getOWLDataFactory();

//        skos = oom.loadOntologyFromOntologyDocument( new File( skosRootPath + ".rdf" ) );
//        aboxModel = oom.loadOntologyFromOntologyDocument( new File(
//                ontologiesHttpFileRoot + pubCodesOntRelPath + ".ofn" ) );
        println "SKOS Doc IRI = <${skosDocIRI}>";

        assert new File( skosDocIRI.toURI() ).exists();
        assert new File( pubCodesDocIRI.toURI() ).exists();

        skos = oom.loadOntologyFromOntologyDocument( skosDocIRI );
        icd9pub = oom.loadOntologyFromOntologyDocument( pubCodesDocIRI );

        onts = new HashSet<OWLOntology>();
        onts.add( skos );
        onts.add( icd9pub );
//        onts = [skos, aboxModel];
    }

    @After
    void tearDown () {

    }

    @Test
    public void testIt () {

        createClassesOntology();
    }

    OWLOntology createClassesOntology () {

        setUp();

        initNamespaces();
        createNewOntology();
        addImports();
        initObjects();
        addCommonAxioms();
        addAxiomsForCodes();

        setUpOntologyFormat();
        serialize();

        return icd9cl;
    }

    def initNamespaces () {

//        icd9ClassesIriString = sharpUriRoot + "/" + "icd9Classes"
//        icd9ClassesNamespace = icd9ClassesIriString + "#"
//        icd9ClassesIRI = new IRI( icd9ClassesIriString );
//        icd9ClassesDocIRI = new IRI( ontRootPath + "/" + "icd9Classes" + ".ofn" );

        pm = new DefaultPrefixManager( sharpCodesNamespace );
        pm.setPrefix( "icd9:", pubCodesNamespace );
        pm.setPrefix( "skos:", skosNamespace );
    }

    def createNewOntology () {

        icd9cl = oom.createOntology( sharpCodesIRI );
    }

    def addImports () {
        OWLImportsDeclaration importsAxiom;
        AddImport imp;

        importsAxiom = odf.getOWLImportsDeclaration( icd9pub.getOntologyID().getOntologyIRI() );
        imp = new AddImport( icd9cl, importsAxiom );
        oom.applyChange( imp );

        importsAxiom = odf.getOWLImportsDeclaration( skos.getOntologyID().getOntologyIRI() );
        imp = new AddImport( icd9cl, importsAxiom );
        oom.applyChange( imp );
    }

    def initObjects () {

        topCodeClass = odf.getOWLClass( "icd9:ICD9_Concept", pm );
        refinesProp = odf.getOWLObjectProperty( ":refines", pm );
        icd9Prop = odf.getOWLDataProperty( "skos:notation", pm );
        skosBroaderTransitive = odf.getOWLObjectProperty( "skos:broaderTransitive", pm );
        prefLabelProp = odf.getOWLAnnotationProperty( "skos:prefLabel", pm );
    }

    def addCommonAxioms () {

        Set<OWLAxiom> axioms = new TreeSet();
        axioms.add( odf.getOWLSubObjectPropertyOfAxiom( skosBroaderTransitive, refinesProp ) );
        oom.addAxioms( icd9cl, axioms );
    }

    def addAxiomsForCodes () {

        Set<OWLIndividual> codeIndividuals = topCodeClass.getIndividuals( icd9pub );
        assert codeIndividuals.size() > 0;

        for (OWLIndividual ind : codeIndividuals) {
            addAxiomsForCode( (OWLNamedIndividual) ind );
        }
    }

    def addAxiomsForCode (OWLNamedIndividual codeInd) {

        Set<OWLAnnotationAssertionAxiom> annos = icd9pub.getAnnotationAssertionAxioms( codeInd.getIRI() );
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
        oom.addAxiom( icd9cl, eqAxiom );
    }

    def addDefinitionUsingCodeValue (OWLNamedIndividual codeInd, OWLClass codeClass) {

        Set<OWLLiteral> codeValues = codeInd.getDataPropertyValues( icd9Prop, icd9pub );
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
        oom.addAxiom( icd9cl, eqAxiom );
    }

    String localName (String s) {

        Pattern pat
        pat = ~/[^a-zA-Z0-9_]/;
        s.replaceAll( pat ) { "_" }
    }

    def setUpOntologyFormat () {

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( pm );
        oom.setOntologyFormat( icd9cl, oFormat );
    }

    def serialize () {

        oom.saveOntology( icd9cl, sharpCodesDocIRI );
    }

}
