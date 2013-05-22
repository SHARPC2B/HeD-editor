package sharpc2b

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import sharpc2b.transform.FileUtil
import sharpc2b.transform.SkosABoxToTBox

/**
 * User: rk
 * Date: 4/16/13
 *
 * A TestCase to test SkosABoxToTBox.
 *
 * At this point [2013.05.05] mainly serves as an example of how to run SkosABoxToTBox with OWL files in
 * test/resources folder.
 */
public class SkosABoxToTBoxTest
extends GroovyTestCase {

    static String sharpCodesOntsRelPath = "/asu.edu/sharpc2b/codes/03/";

    /*
     * SKOS
     */
    static String skosRelPath = "/www.w3.org/2004/02/skos/core";
    static IRI skosDocIRI = IRI.create( FileUtil.getFileInTestResourceDir( "http/" + skosRelPath + ".rdf" ).toURI() );

    /*
     * Published ICD9 Codes Ontology
     */
    static String pubCodesOntRelPath = sharpCodesOntsRelPath + "icd9-pub";
    static String pubCodesUriPath = "http:/" + pubCodesOntRelPath;
    static IRI pubCodesDocIRI = new IRI( FileUtil.getFileInTestResourceDir( "http/" + pubCodesOntRelPath + "" +
            ".ofn" ).toURI()
    );

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static String sharpCodesOntRelPath = sharpCodesOntsRelPath + "icd9-classes";
    static String sharpCodesUriPath = "http:/" + sharpCodesOntRelPath;
    static IRI sharpCodesIRI = new IRI( sharpCodesUriPath );
    static IRI sharpCodesDocIRI = new IRI( FileUtil.getFileInTestResourceDir( "http/" + sharpCodesOntRelPath +
            "2" + ".ofn" ).toURI() );

    OWLOntologyManager oom;
    OWLOntology aboxModel;

    void setUp () {

        oom = OWLManager.createOWLOntologyManager();
//        skos = oom.loadOntologyFromOntologyDocument( new File( skosRootPath + ".rdf" ) );
//        final File outFile = new File(
//                ontologiesHttpFileRoot + pubCodesOntRelPath + ".ofn" )
        final File outFile = FileUtil.getFileInTestResourceDir(
                "http/" + pubCodesOntRelPath + ".ofn" )
        aboxModel = oom.loadOntologyFromOntologyDocument( outFile );
//        println "SKOS Doc IRI = <${skosDocIRI}>";

        assert new File( skosDocIRI.toURI() ).exists();
        assert new File( pubCodesDocIRI.toURI() ).exists();

//        skos = oom.loadOntologyFromOntologyDocument( skosDocIRI );
    }

    void tearDown () {

    }

    void testRunIt () {
        SkosABoxToTBox tr = new SkosABoxToTBox();

        OWLOntology tboxModel = oom.createOntology( sharpCodesIRI );

        tr.createTBoxOntology( aboxModel, tboxModel );

        tboxModel.getOWLOntologyManager().saveOntology( tboxModel, sharpCodesDocIRI );
    }

}
