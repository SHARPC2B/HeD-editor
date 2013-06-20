package sharpc2b.transform

import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.model.IRI

/**
 * User: rk
 * Date: 6/19/13
 * Time: 3:45 PM
 */
class ShopsTest extends SharpGroovyTestCase {

    static IRI shopsOntIRI = IriUtil.sharpIRI( "shops" );
    static File shopsOntFile = FileUtil.getFileInProjectDir(
            "/editor-models/src/main/resources/ontologies/shops.ofn" );

    static String opsCoreBaseIRI = IriUtil.sharpIRI( "ops" ).toString() + "#";
    static String operatorsBaseIRI = shopsOntIRI.toString() + "#";

    void setUp(){
        super.setUp()

        reasonerFactory = new Reasoner.ReasonerFactory();  // Hermit
        if (reasoner != null) {
            reasoner.flush()
            reasoner.dispose()
            reasoner = null
        }

    }

   void testIntPlus(){


       ont = oom.loadOntologyFromOntologyDocument(IRI.create(shopsOntFile));

       reasoner = OwlapiUtil.getHermitReasoner(ont,false);

       assert reasoner.isConsistent();

    }
}
