package sharpc2b.transform

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.util.DefaultPrefixManager
import org.semanticweb.owlapi.vocab.Namespaces

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

//    static IRI mmaIRI = IRI.create( "http://asu.edu/sharpc2b/rk/SharpOwlABoxDomainMetaModel" );
//    static File mmaFile = new File( testResourcesPath + "/onts/in/SharpOwlABoxMetaModel.ofn" );

    static IRI outputOntIRI = IRI.create( "http://asu.edu/sharpc2b/rk/ClinicalDomainInsts" );
    static File outputOntFile = new File( testResourcesPath + "/onts/out/ClinicalDomainInsts6.ofn" );

    TBoxToABox inst;

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat

    OWLOntology tboxModel;
    OWLOntology aboxModel;

    void setUp () {

        inst = new TBoxToABox();

        oom = OWLManager.createOWLOntologyManager();

        tboxModel = oom.loadOntologyFromOntologyDocument( inputOntFile );

        aboxModel = oom.createOntology( outputOntIRI );

        oFormat = new OWLFunctionalSyntaxOntologyFormat();
//        oFormat.copyPrefixesFrom( inst.getPrefixManager() );
        oFormat.copyPrefixesFrom( new DefaultPrefixManager() );
        oFormat.setDefaultPrefix( aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "mm:", TBoxToABox.aboxDomainMetaModelIRI.toString() + "#" );

    }

    void tearDown () {

    }

    void testDefaultABox () {
        println "BEGIN Test"

//        useSkosABoxConcepts( inst, oFormat )

        inst.populateABox( tboxModel, aboxModel );

        File outputOntFile = new File( testResourcesPath + "/onts/out/ClinicalDomainInsts6.ofn" );
        oom.saveOntology( aboxModel, oFormat, IRI.create( outputOntFile ) );

        println "END Test"
    }

    void testSkosABox () {
        println "BEGIN Test"

        useSkosABoxConcepts( inst, oFormat )

        inst.populateABox( tboxModel, aboxModel );

        File outputOntFile = new File( testResourcesPath + "/onts/out/SkosClinicalDomainInsts6.ofn" );
        oom.saveOntology( aboxModel, oFormat, IRI.create( outputOntFile ) );

        println "END Test"
    }

    def useSkosABoxConcepts (TBoxToABox inst,
                             OWLFunctionalSyntaxOntologyFormat oFormat) {
        oFormat.setPrefix( "skos:", Namespaces.SKOS.toString() );

        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "owl:Class" ), oFormat.getIRI( "skos:Concept" ) );
        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "rdfs:subClassOf" ),
                oFormat.getIRI( "skos:broaderTransitive" ) );
        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "rdfs:subPropertyOf" ),
                oFormat.getIRI( "skos:broaderTransitive" ) );
    }
}
