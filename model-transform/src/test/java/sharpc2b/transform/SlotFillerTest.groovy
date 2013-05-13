package sharpc2b.transform

import com.clarkparsia.pellet.owlapiv3.PelletReasoner
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory
import org.junit.*
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk
 * Date: 5/12/13
 * Time: 9:17 PM
 */
class SlotFillerTest extends GroovyTestCase {

    static String testResourcesPath = "/Users/rk/asu/prj/sharp-editor/model-transform/src/test/resources";
//    static String ontIriBasePath = "asu.edu/sharpc2b/rk/SlotFill/01"
    static String ontIriBasePath = "asu.edu/sharpc2b/rk"
    static IRI ontIri = IRI.create( "http://" + ontIriBasePath + "/" + "JoeHasAspirin" )
    static String ontNamespace = ontIri.toString() + "#"
    static IRI dmIri = IRI.create( "http://" + ontIriBasePath + "/ClinicalDomain" )
    static String dmNamespace = dmIri.toString() + "#"

    static File ontFile (String name) {
        new File( testResourcesPath + "/onts/in/" + name + ".ofn" )
    }

    static IRI ontIRI (String name) {
        IRI.create( "http://" + ontIriBasePath + "/" + name )
    }

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat;
    DefaultPrefixManager pm;

    OWLOntology ont;
    OWLOntology dma;
    OWLOntology dmt;
    OWLOntology mm;
    Set<OWLOntology> onts
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
        pm.setPrefix( "dmt:", dmNamespace )

//        dma = oom.createOntology( dmIri )
        onts = new HashSet<OWLOntology>();

    }

    @After
    void tearDown () {

    }

    @Test
//    @Ignore
    void testIt () {

        ont = oom.createOntology( ontIri )

//        dma = oom.loadOntologyFromOntologyDocument( ontFile( "ClinicalDomainA" ) )
        dmt = oom.loadOntologyFromOntologyDocument( ontFile( "ClinicalDomainT" ) )
//        mm = oom.loadOntologyFromOntologyDocument( ontFile( "ABoxMetaModel" ) )
        onts.add( ont )
        onts.add( dmt )
//        assert dma
        assert dmt
        AddImport imp = new AddImport( ont, odf.getOWLImportsDeclaration( dmt.getOntologyID().getOntologyIRI() ) )
        oom.applyChange( imp )
//        oom.addAxiom( ont, odf.getOWLImportsDeclaration( dmt.getOntologyID().getOntologyIRI() ) )

        OWLNamedIndividual joe = odf.getOWLNamedIndividual( ":Joe", pm )
        OWLNamedIndividual aspirin = odf.getOWLNamedIndividual( ":Aspirin", pm )
        OWLClass cPatient = odf.getOWLClass( "dmt:Patient", pm )
        OWLClass cDrug = odf.getOWLClass( "dmt:Drug", pm )
        OWLClass cDisorder = odf.getOWLClass( "dmt:Disorder", pm )
        OWLObjectProperty hasDisorder = odf.getOWLObjectProperty( "dmt:hasDisorder", pm )

//        println pm.getIRI( "dmt:Disorder" )

        Set<OWLClassExpression> superOfDisorder
        superOfDisorder = cDisorder.getSuperClasses( onts )
        assert superOfDisorder.size() == 0
        println "supers of Disorder = "+ superOfDisorder

        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cPatient, joe ) )
        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDrug, aspirin ) )

//        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDisorder, aspirin ) )

        oFormat = new OWLFunctionalSyntaxOntologyFormat()
        oFormat.copyPrefixesFrom( pm )

        oom.saveOntology( ont, oFormat, IRI.create( ontFile( "JoeHasAspirin" ) ) )

        pellet = PelletReasonerFactory.getInstance().createReasoner( ont )
//        pellet = PelletReasonerFactory.getInstance().createNonBufferingReasoner( ont )

        println "isConsistent 1 = "+ pellet.isConsistent()
        assert pellet.isConsistent()
//        assert pellet.isSatisfiable()

        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( hasDisorder, joe, aspirin ) )
//        assertFalse pellet.isConsistent()

        PelletReasoner pellet2
        pellet2 = PelletReasonerFactory.getInstance().createReasoner( ont )
//        pellet2 = PelletReasonerFactory.getInstance().createNonBufferingReasoner( ont )

        println "isConsistent 2 = "+ pellet2.isConsistent()
        assertFalse pellet2.isConsistent()
    }

}
