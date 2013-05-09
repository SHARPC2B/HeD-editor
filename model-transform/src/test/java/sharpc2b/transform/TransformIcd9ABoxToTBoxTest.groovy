package sharpc2b

import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import sharpc2b.transform.TransformIcd9ABoxToTBox

//import static org.junit.Assert.assertEquals
//import static org.junit.Assume.*;

//import org.junit.runners.Parameterized
//import org.junit.runners.Parameterized.Parameters

//import org.junit.Ignore;
//import org.junit.experimental.theories.*
//import static org.junit.Assume.assumeTrue as assume

/**
 * User: rk
 * Date: 4/16/13
 *
 * An incomplete TestCase to test TransformIcd9ABoxToTBox.
 */
@RunWith(JUnit4.class)
public class TransformIcd9ABoxToTBoxTest extends GroovyTestCase {

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
    static String sharpCodesOntRelPath = sharpCodesOntsRelPath + "icd9-classes";
    static String sharpCodesUriPath = "http:/" + sharpCodesOntRelPath;
    static String sharpCodesNamespace = sharpCodesUriPath + "#";
    static IRI sharpCodesIRI = new IRI( sharpCodesUriPath );
    static IRI sharpCodesDocIRI = new IRI( ontologiesDocUriRoot + sharpCodesOntRelPath + "2" + ".ofn" );

    OWLOntologyManager oom;
    OWLOntology aboxModel;

    @BeforeClass
    static void setUpOnce () {

    }

    @AfterClass
    static void tearDownOnce () {

    }

    @Before
    void setUp () {

        oom = OWLManager.createOWLOntologyManager();
//        skos = oom.loadOntologyFromOntologyDocument( new File( skosRootPath + ".rdf" ) );
        aboxModel = oom.loadOntologyFromOntologyDocument( new File(
                ontologiesHttpFileRoot + pubCodesOntRelPath + ".ofn" ) );
        println "SKOS Doc IRI = <${skosDocIRI}>";

        assert new File( skosDocIRI.toURI() ).exists();
        assert new File( pubCodesDocIRI.toURI() ).exists();

//        skos = oom.loadOntologyFromOntologyDocument( skosDocIRI );

    }

    @After
    void tearDown () {

    }

    @Test
//    @Ignore
    void testRunIt () {
        TransformIcd9ABoxToTBox tr = new TransformIcd9ABoxToTBox();

        OWLOntology tboxModel = tr.createTBoxOntology( aboxModel, sharpCodesIRI );

        tboxModel.getOWLOntologyManager().saveOntology( tboxModel, sharpCodesDocIRI );
    }

}
