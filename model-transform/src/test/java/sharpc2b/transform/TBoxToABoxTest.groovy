package sharpc2b.transform

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager

/**
 * User: rk
 * Date: 5/8/13
 * Time: 8:30 AM
 */
class TBoxToABoxTest extends GroovyTestCase {

    static String testResourcesPath = "/Users/rk/asu/prj/sharp-editor/model-transform/src/test/resources";
//    static String inputOntUriCorePath = "asu.edu/sharpc2b/rk/ClinicalDomain"
//    static File inputOntFile = new File( "/Users/rk/VOM/export/http/" + inputOntUriCorePath +
//            ".ofn" );
    static File inputOntFile = new File( testResourcesPath + "/onts/in/ClinicalDomain.ofn" );
//    static IRI inputOntIRI = IRI.create( "http://"+ inputOntUriCorePath );

    static IRI mmaIRI = IRI.create( "asu.edu/sharpc2b/rk/SharpOwlABoxMetaModel" );
//    static File mmaFile = new File( testResourcesPath + "/onts/in/SharpOwlABoxMetaModel.ofn" );

    static IRI outputOntIRI = IRI.create( "http://asu.edu/sharpc2b/rk/ClinicalDomainInsts" );
    static File outputOntFile = new File( testResourcesPath + "/onts/out/ClinicalDomainInsts3.ofn" );

    void setUp () {

    }

    void tearDown () {

    }

    void testIt () {
        println "BEGIN Test"

        TBoxToABox inst = new TBoxToABox();

        OWLOntology tboxModel;
        OWLOntology aboxModel;
        OWLOntologyManager oom;

        oom = OWLManager.createOWLOntologyManager();

        tboxModel = oom.loadOntologyFromOntologyDocument( inputOntFile );

        aboxModel = oom.createOntology( outputOntIRI );

        inst.populateABox( tboxModel, aboxModel );

        OWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
//        oFormat.copyPrefixesFrom( inst.getPrefixManager() );
        oFormat.setDefaultPrefix( aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "mm:", mmaIRI.toString() + "#" );

        oom.saveOntology( aboxModel, oFormat, IRI.create( outputOntFile ) );

        println "END Test"
    }
}
