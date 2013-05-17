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
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk Date: 5/16/13 Time: 6:09 PM
 */
public class SlotFillerABoxTest
extends GroovyTestCase {

//    static String ontIriBasePath = "asu.edu/sharpc2b/rk"

    static IRI ontIri = TestUtil.testIRI( "JoeHasAspirinA" )
    static IRI mmIri = TestUtil.testIRI( "ABoxMetaModel" )
    static IRI dmIri = TestUtil.testIRI( "ClinicalDomainA" )

    static String ontNamespace = ontIri.toString() + "#"
    static String dmNamespace = dmIri.toString() + "#"
    static String mmNamespace = mmIri.toString() + "#"

    static File ontFile (String name) {
        FileUtil.getFileInResourceDir( "onts/in/" + name + ".ofn" )
    }

//    static IRI ontIRI (String name) {
//        IRI.create( "http://" + ontIriBasePath + "/" + name )
//    }

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat;
    DefaultPrefixManager pm;

    OWLOntology ont;
    OWLOntology dma;
//    OWLOntology dmt;
    OWLOntology mm;
    Set<OWLOntology> onts
    OWLDataFactory odf;

//    PelletReasoner pellet;
    Reasoner reasoner;

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
        pm.setPrefix( "dma:", dmNamespace )
        pm.setPrefix( "mm:", mmNamespace )

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
    void testIt () {

        ont = oom.createOntology( ontIri )

        File file

        file = ontFile( "ABoxMetaModel" )
        println file
        assert file.exists()
        mm = oom.loadOntologyFromOntologyDocument( file )
//        assert dmt
        assert mm

        file = ontFile( "ClinicalDomainA" )
        println file
        assert file.exists()
        dma = oom.loadOntologyFromOntologyDocument( file )
//        dmt = oom.loadOntologyFromOntologyDocument( ontFile( "ClinicalDomainT" ) )
        assert dma

        onts.add( ont )
//        onts.add( dmt )
        onts.add( dma )
        onts.add( mm )

        addOwlImports( ont, mm )
        addOwlImports( ont, dma )

        OWLClass mmClass = odf.getOWLClass( "mm:Class", pm )
        OWLClass mmProperty = odf.getOWLClass( "mm:Property", pm )
        OWLClass mmIndividual = odf.getOWLClass( "mm:Individual", pm )

        OWLObjectProperty mmType = odf.getOWLObjectProperty( "mm:type", pm )

        OWLClass mmTriple = odf.getOWLClass( "mm:Triple", pm )
        OWLObjectProperty mmPredicate = odf.getOWLObjectProperty( "mm:predicate", pm )
        OWLObjectProperty mmSubject = odf.getOWLObjectProperty( "mm:subject", pm )
        OWLObjectProperty mmValue = odf.getOWLObjectProperty( "mm:value", pm )


        OWLNamedIndividual cPatient = odf.getOWLNamedIndividual( "dma:Patient", pm )
        OWLNamedIndividual cDrug = odf.getOWLNamedIndividual( "dma:Drug", pm )
        OWLNamedIndividual cDisorder = odf.getOWLNamedIndividual( "dma:Disorder", pm )
        OWLNamedIndividual hasDisorder = odf.getOWLNamedIndividual( "dma:hasDisorder", pm )

        OWLNamedIndividual joe = odf.getOWLNamedIndividual( ":Joe", pm )
        OWLNamedIndividual aspirin = odf.getOWLNamedIndividual( ":Aspirin", pm )
        OWLNamedIndividual hangover = odf.getOWLNamedIndividual( ":Hangover", pm )

        /* Class Assertions */

        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, joe, cPatient ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, aspirin, cDrug ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, hangover, cDisorder ) )

        reasoner = new Reasoner( ont );

        assert true == reasoner.isConsistent()

        OWLNamedIndividual joeHasHangover = addAPropertyAssertion( ont, pm.getIRI( ":joeHasHangover" ),
                hasDisorder, joe, hangover )

        assert joeHasHangover

//        reasoner.flush()
        reasoner = new Reasoner( ont );
        assert true == reasoner.isConsistent()


        OWLNamedIndividual joeHasAspirin = addAPropertyAssertion( ont, pm.getIRI( ":joeHasAspirin" ),
                hasDisorder, joe, aspirin )

        assert joeHasAspirin

        reasoner.flush()
        assert false == reasoner.isConsistent()

        /*
         * Save it to a File (for eyeball debugging)
         */
        saveOntologyToFile( ont, "JoeHasAspirinA" )

    }

    public OWLNamedIndividual addAPropertyAssertion (OWLOntology ont,
                                                     IRI tripleIRI,
                                                     OWLNamedIndividual predicate,
                                                     OWLNamedIndividual subject,
                                                     OWLNamedIndividual value) {

        OWLNamedIndividual triple = odf.getOWLNamedIndividual( tripleIRI )

        OWLClass mmTriple = odf.getOWLClass( "mm:Triple", pm )
        OWLObjectProperty mmPredicate = odf.getOWLObjectProperty( "mm:predicate", pm )
        OWLObjectProperty mmSubject = odf.getOWLObjectProperty( "mm:subject", pm )
        OWLObjectProperty mmValue = odf.getOWLObjectProperty( "mm:value", pm )

        oom.addAxiom( ont, odf.getOWLClassAssertionAxiom( mmTriple, triple, ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmPredicate, triple, predicate ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmSubject, triple, subject ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmValue, triple, value ) )

        return triple
    }

    def addOwlImports (OWLOntology ont, OWLOntology ontToImport) {
        oom.applyChange( new AddImport( ont, odf.getOWLImportsDeclaration(
                ontToImport.getOntologyID().getOntologyIRI() ) ) )
    }

    def saveOntologyToFile (OWLOntology ont, final String ontFileName) {

        oFormat = new OWLFunctionalSyntaxOntologyFormat()
        oFormat.copyPrefixesFrom( pm )

        File outFile = FileUtil.getFileInResourceDir( "onts/out/" + ontFileName + ".ofn" )
        oom.saveOntology( ont, oFormat, IRI.create( outFile ) )
    }

}
