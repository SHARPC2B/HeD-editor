package sharpc2b

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import sharpc2b.transform.FileUtil
import sharpc2b.transform.IriUtil
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

    static String sharpCodesOntsVersion = "03";
    static String sharpCodesOntsRelPath = "asu.edu/sharpc2b/codes/${sharpCodesOntsVersion}/";

    /*
     * SKOS
     */
//    static String skosResourcePath = "/onts/in/skos-core.rdfxml";

    /*
     * Published ICD9 Codes Ontology
     */
    static String pubCodesResourcePath = "/onts/in/icd9-pub.ofn";

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static String sharpCodesResourcePath = "/onts/out/icd9-T.ofn";

    static IRI sharpCodesIRI = IRI.create("http://" + sharpCodesOntsRelPath + "icd9-classes");
//    static IRI sharpCodesDocIRI = new IRI( FileUtil.getFileInTestResourceDir( "http/" + sharpCodesOntRelPath +
//            "2" + ".ofn" ).toURI() );

    OWLOntologyManager oom;
    OWLOntology aboxModel;
    OWLOntology tboxModel

    void setUp () {

        oom = OWLManager.createOWLOntologyManager();
    }

    void tearDown () {
        aboxModel = null
        tboxModel = null
        oom = null
    }

    void testRunIt () {

        File inFile = FileUtil.getFileInTestResourceDir( pubCodesResourcePath );
        File outFile = FileUtil.getFileInTestResourceDir( sharpCodesResourcePath );

//        assert new File( pubCodesDocIRI.toURI() ).exists();
        assert inFile.exists()

//                "http/" + pubCodesOntRelPath + ".ofn" )
//        "http/" + pubCodesOntRelPath + ".ofn" )
//                aboxModel = oom.loadOntologyFromOntologyDocument( outFile );

        aboxModel = oom.loadOntologyFromOntologyDocument( inFile );

        SkosABoxToTBox tr = new SkosABoxToTBox();

        tboxModel = oom.createOntology( sharpCodesIRI );

        tr.createTBoxOntology( aboxModel, tboxModel );

        OWLFunctionalSyntaxOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();

        oFormat.setDefaultPrefix( tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "skos:", IriUtil.skos + "#" );

//        tboxModel.getOWLOntologyManager().saveOntology( tboxModel, sharpCodesDocIRI );
        tboxModel.getOWLOntologyManager().saveOntology( tboxModel, oFormat, IRI.create( outFile ) );
    }

}
