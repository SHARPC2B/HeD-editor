package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk
 * Date: 5/12/13
 * Time: 9:17 PM
 */
class SlotFillerTest extends GroovyTestCase {

    static String ontIriBasePath = "asu.edu/sharpc2b/rk"
    static IRI ontIri = IRI.create( "http://" + ontIriBasePath + "/" + "JoeHasAspirin" )
    static String ontNamespace = ontIri.toString() + "#"
    static IRI dmIri = IRI.create( "http://" + ontIriBasePath + "/ClinicalDomain" )
    static String dmNamespace = dmIri.toString() + "#"

    static File ontFile (String name) {
        FileUtil.getFileInTestResourceDir( "onts/in/" + name + ".ofn" )
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

//    PelletReasoner pellet;
    Reasoner hermit;

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

        oom = null
        odf = null
        pm = null
        onts = null
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

        OWLClass cPatient = odf.getOWLClass( "dmt:Patient", pm )
        OWLClass cDrug = odf.getOWLClass( "dmt:Drug", pm )
        OWLClass cDisorder = odf.getOWLClass( "dmt:Disorder", pm )
        OWLObjectProperty hasDisorder = odf.getOWLObjectProperty( "dmt:hasDisorder", pm )

        OWLNamedIndividual joe = odf.getOWLNamedIndividual( ":Joe", pm )
        OWLNamedIndividual aspirin = odf.getOWLNamedIndividual( ":Aspirin", pm )
        OWLNamedIndividual hangover = odf.getOWLNamedIndividual( ":Hangover", pm )

//        println pm.getIRI( "dmt:Disorder" )

        Set<OWLClassExpression> superOfDisorder
        superOfDisorder = cDisorder.getSuperClasses( onts )
        assert superOfDisorder.size() == 0
//        println "supers of Disorder = " + superOfDisorder

        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cPatient, joe ) )
        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDrug, aspirin ) )
        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDisorder, hangover ) )

        oFormat = new OWLFunctionalSyntaxOntologyFormat()
        oFormat.copyPrefixesFrom( pm )

        oom.saveOntology( ont, oFormat, IRI.create( ontFile( "JoeHasAspirinT" ) ) )

//        pellet = PelletReasonerFactory.getInstance().createReasoner( ont )
//        pellet = PelletReasonerFactory.getInstance().createNonBufferingReasoner( ont )

        hermit = new Reasoner( ont );

        assert true == hermit.isConsistent()
//        assert pellet.isSatisfiable()

        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( hasDisorder, joe, hangover ) )

//        hermit.flush()
        assert true == hermit.isConsistent()

        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( hasDisorder, joe, aspirin ) )

//        hermit.flush()
//        assert false == hermit.isConsistent()
    }

}
