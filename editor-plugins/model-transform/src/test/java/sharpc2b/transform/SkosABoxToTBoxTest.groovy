package sharpc2b.transform

import edu.asu.sharpc2b.transform.IriUtil
import edu.asu.sharpc2b.transform.OwlUtil
import edu.asu.sharpc2b.transform.SkosABoxToTBox
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.util.SimpleIRIMapper
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil

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
//    static String skosResourcePath = "/ontologies/in/skos-core.rdfxml";

    /*
     * Published ICD9 Codes Ontology
     */
    static String pubCodesResourcePath = "/ontologies/in/icd9-pub.ofn";

    /*
     * Sharp Ontology of ICD9 Code OWL Classes
     */
    static String sharpCodesResourcePath = "/ontologies/out/icd9-T.ofn";

    static IRI sharpCodesIRI = IRI.create( "http://" + sharpCodesOntsRelPath + "icd9-classes" );

    //===============================================================================

    OWLOntologyManager oom;
    OWLOntology aboxModel;
    OWLOntology tboxModel

    File skosFile;
    SimpleIRIMapper iriMapper;

    void setUp () {

//        oom = OWLManager.createOWLOntologyManager();
        oom = OwlUtil.createSharpOWLOntologyManager();
//        skosFile = TestFileUtil.getResourceAsFile( "/ontologies/in/skos-core.rdfxml" );
//        skosFile = OwlUtil.getSharpEditorOntologyFile("skos-core.owl");
//        iriMapper = new SimpleIRIMapper( IriUtil.skosIRI,
//                IRI.create( skosFile ) );
//        oom.addIRIMapper( iriMapper );
    }

    void tearDown () {
        aboxModel = null
        tboxModel = null
        oom = null
    }

    void testRunIt () {

        File inFile = TestFileUtil.getResourceAsFile( pubCodesResourcePath );
        File outFile = TestFileUtil.getFileForTestOutput( sharpCodesResourcePath );
        println "inFile = "+ inFile
        println "outFile = "+ outFile

        assert inFile.exists()
        assert inFile.canRead()

//        println "inFile = '" + inFile + "'"

        aboxModel = oom.loadOntologyFromOntologyDocument( inFile );

        SkosABoxToTBox tr = new SkosABoxToTBox();

        tboxModel = oom.createOntology( sharpCodesIRI );

        tr.addTBoxAxioms( aboxModel, tboxModel );

        OWLFunctionalSyntaxOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();

        oFormat.setDefaultPrefix( tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "skos:", IriUtil.skos + "#" );

        tboxModel.getOWLOntologyManager().saveOntology( tboxModel, oFormat, IRI.create( outFile ) );
    }

    /**
     * This is an example / test case of usage.
     */
    void test2 ()
    throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntologyFormat oFormat;

        String sharpCodesOntsVersion = "03";
        String commonCodesOntsRelPath = "asu.edu/sharpc2b/codes/" + sharpCodesOntsVersion + "/";

        File aFile = TestFileUtil.getResourceAsFile( "/ontologies/in/icd9-pub.ofn" );
        File tFile = TestFileUtil.getFileForTestOutput( "/ontologies/out/icd9-classes.ofn" );
        IRI aIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-pub" );
        IRI tIRI = IRI.create( "http://" + commonCodesOntsRelPath + "icd9-classes5" );

        PrefixOWLOntologyFormat pm = IriUtil.getDefaultSharpOntologyFormat();

        pm.setDefaultPrefix( tIRI.toString() + "#" );
        pm.setPrefix( "a:", aIRI.toString() + "#" );
        pm.setPrefix( "t:", tIRI.toString() + "#" );
        pm.setPrefix( "skos:", IriUtil.skos + "#" );

        oFormat = new OWLFunctionalSyntaxOntologyFormat();
        ((OWLFunctionalSyntaxOntologyFormat) oFormat).copyPrefixesFrom( pm );

        /*
         * Everything up to here was creating paths, IRIs, prefixes, etc.
         */

//        OWLOntologyManager oom = OWLManager.createOWLOntologyManager();

        assertEquals( true, aFile.exists() )

        OWLOntology ont1 = oom.loadOntologyFromOntologyDocument( aFile );

        SkosABoxToTBox inst = new SkosABoxToTBox();

        OWLOntology ont2 = oom.createOntology( tIRI );
        inst.addTBoxAxioms( ont1, ont2 );

        oom.setOntologyFormat( ont2, oFormat );
        oom.saveOntology( ont2, oFormat, IRI.create( tFile ) );
    }

}
