package sharpc2b.transform

import com.clarkparsia.pellet.owlapiv3.PelletReasoner
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory
import org.junit.*
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk
 * Date: 5/12/13
 * Time: 9:17 PM
 */
class SlotFillerTest extends GroovyTestCase {

    static String testResourcesPath = "/Users/rk/asu/prj/sharp-editor/model-transform/src/test/resources";
    static String ontIriBasePath = "asu.edu/sharpc2b/rk/SlotFill/01"
    static IRI ontIri = IRI.create( "http://" + ontIriBasePath + "/BadAge" )
    static String ontNamespace = ontIri.toString() + "#"
    static IRI dmIri = IRI.create( "http://" + ontIriBasePath + "/DomainModel" )
    static String dmNamespace = dmIri.toString() + "#"

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat;
    DefaultPrefixManager pm;

    OWLOntology ont;
    OWLOntology dmOnt;
    OWLDataFactory odf;

    PelletReasoner pellet;

    @BeforeClass
    static void setUpOnce () {

    }

    @AfterClass
    static void tearDownOnce () {

    }

    @Before
    void setUp () {

        oom = OWLManager.createOWLOntologyManager();
        odf = oom.getOWLDataFactory()
        pm = new DefaultPrefixManager( ontNamespace )
        pm.setPrefix( "dm:", dmNamespace )

        ont = oom.createOntology( ontIri )
        dmOnt = oom.createOntology( dmIri )

        pellet = PelletReasonerFactory.getInstance().createReasoner(  ont )
    }

    @After
    void tearDown () {

    }

    @Test
//    @Ignore
    void testIt () {


    }

}
