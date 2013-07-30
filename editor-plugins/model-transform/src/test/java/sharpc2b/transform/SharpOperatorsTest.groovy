package sharpc2b.transform

import edu.asu.sharpc2b.transform.FileUtil
import edu.asu.sharpc2b.transform.IriUtil
import edu.asu.sharpc2b.transform.SharpOperators
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil

/**
 * User: rk
 * Date: 6/19/13
 * Time: 2:36 PM
 */
class SharpOperatorsTest extends GroovyTestCase {

    static File excelFile = FileUtil.getExistingResourceAsFile(
            "/ontologies/import-operators/SharpOperators.xlsx" );

    static IRI outputOntIRI = IriUtil.sharpEditorIRI( "shops" );
    static File outputOntFile = TestFileUtil.getFileForTestOutput( "/ontologies/out/shops.ofn" );

    static String opsCoreBaseIRI = IriUtil.sharpEditorIRI( "ops" ).toString() + "#";
    static String operatorsBaseIRI = outputOntIRI.toString() + "#";

    OWLOntologyManager oom;
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
//    Set<OWLOntology> ontologies;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    OWLOntology ont;

    void setUp () {

    }

    void tearDown () {

    }

    void testSharpOperators () {

        SharpOperators sharpOperatorsCreator = new SharpOperators();

        oom = OWLManager.createOWLOntologyManager();
        ont = oom.createOntology( outputOntIRI );
        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        sharpOperatorsCreator.addSharpOperators( excelFile, ont );

        oom.saveOntology( ont, oFormat, IRI.create( outputOntFile ) );
    }

}
