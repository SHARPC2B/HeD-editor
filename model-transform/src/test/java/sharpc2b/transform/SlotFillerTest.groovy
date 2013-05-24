package sharpc2b.transform

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.semanticweb.HermiT.Reasoner
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.AddImport
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLClassExpression
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

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
        TestFileUtil.getFileInTestResourceDir( "onts/in/" + name + ".ofn" )
    }

    static IRI ontIRI (String name) {
        IRI.create( "http://" + ontIriBasePath + "/" + name )
    }

    OWLOntologyManager oom;
    PrefixOWLOntologyFormat oFormat;

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
        oFormat = IriUtil.getDefaultSharpOntologyFormat()
        oFormat.setDefaultPrefix( ontNamespace )
        oFormat.setPrefix( "dmt:", dmNamespace )

//        dma = oom.createOntology( dmIri )
        onts = new HashSet<OWLOntology>();
    }

    @After
    void tearDown () {

        oom = null
        odf = null
        oFormat = null
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

        OWLClass cPatient = odf.getOWLClass( "dmt:Patient", oFormat )
        OWLClass cDrug = odf.getOWLClass( "dmt:Drug", oFormat )
        OWLClass cDisorder = odf.getOWLClass( "dmt:Disorder", oFormat )
        OWLObjectProperty hasDisorder = odf.getOWLObjectProperty( "dmt:hasDisorder", oFormat )

        OWLNamedIndividual joe = odf.getOWLNamedIndividual( ":Joe", oFormat )
        OWLNamedIndividual aspirin = odf.getOWLNamedIndividual( ":Aspirin", oFormat )
        OWLNamedIndividual hangover = odf.getOWLNamedIndividual( ":Hangover", oFormat )

//        println oFormat.getIRI( "dmt:Disorder" )

        Set<OWLClassExpression> superOfDisorder
        superOfDisorder = cDisorder.getSuperClasses( onts )
        assert superOfDisorder.size() == 0
//        println "supers of Disorder = " + superOfDisorder

        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cPatient, joe ) )
        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDrug, aspirin ) )
        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( cDisorder, hangover ) )

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
