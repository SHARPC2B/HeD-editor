package sharpc2b.transform.test

import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.reasoner.OWLReasoner
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import edu.asu.sharpc2b.transform.OwlUtil

/**
 * User: rk
 * Date: 6/19/13
 * Time: 3:46 PM
 */
abstract class SharpGroovyTestCase
extends GroovyTestCase {

    OWLOntologyManager oom;
    OWLDataFactory odf;
    PrefixOWLOntologyFormat oFormat
//    Set<OWLOntology> ontologies;
    OWLReasonerFactory reasonerFactory;
    OWLReasoner reasoner;

    OWLOntology ont;

    @Override
    protected void setUp () throws Exception {
        super.setUp()

        oom = OwlUtil.createSharpOWLOntologyManager()
        odf = oom.getOWLDataFactory()
    }

    @Override
    protected void tearDown () throws Exception {
        oom.clearIRIMappers()
        oom = null
        odf = null
        oFormat = null
        reasonerFactory = null
        reasoner = null
        ont = null
        super.tearDown()
    }
}
