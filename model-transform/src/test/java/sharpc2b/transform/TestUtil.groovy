package sharpc2b.transform

import org.semanticweb.owlapi.model.IRI

/**
 * User: rk
 * Date: 5/16/13
 * Time: 7:30 PM
 */
class TestUtil {

    static IRI testIRI (String name) {
        IRI.create( "http://asu.edu/sharpc2b/test/" + name );
    }
}
