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
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat

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
        TestFileUtil.getFileInTestResourceDir( "onts/in/" + name + ".ofn" )
    }

//    static IRI ontIRI (String name) {
//        IRI.create( "http://" + ontIriBasePath + "/" + name )
//    }

    OWLOntologyManager oom;
    PrefixOWLOntologyFormat oFormat;

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
        oFormat = IriUtil.getDefaultSharpOntologyFormat()
        oFormat.setDefaultPrefix( ontNamespace )
        oFormat.setPrefix( "dma:", dmNamespace )
        oFormat.setPrefix( "mm:", mmNamespace )

//        dma = oom.createOntology( dmIri )
        onts = new HashSet<OWLOntology>();
    }

    @After
    void tearDown () {

        oom = null
        odf = null
        onts = null
        oFormat = null
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

        OWLClass mmClass = odf.getOWLClass( "mm:Class", oFormat )
        OWLClass mmProperty = odf.getOWLClass( "mm:Property", oFormat )
        OWLClass mmIndividual = odf.getOWLClass( "mm:Individual", oFormat )

        OWLObjectProperty mmType = odf.getOWLObjectProperty( "mm:type", oFormat )

        OWLClass mmTriple = odf.getOWLClass( "mm:Triple", oFormat )
        OWLObjectProperty mmPredicate = odf.getOWLObjectProperty( "mm:predicate", oFormat )
        OWLObjectProperty mmSubject = odf.getOWLObjectProperty( "mm:subject", oFormat )
        OWLObjectProperty mmValue = odf.getOWLObjectProperty( "mm:value", oFormat )


        OWLNamedIndividual cPatient = odf.getOWLNamedIndividual( "dma:Patient", oFormat )
        OWLNamedIndividual cDrug = odf.getOWLNamedIndividual( "dma:Drug", oFormat )
        OWLNamedIndividual cDisorder = odf.getOWLNamedIndividual( "dma:Disorder", oFormat )
        OWLNamedIndividual hasDisorder = odf.getOWLNamedIndividual( "dma:hasDisorder", oFormat )

        OWLNamedIndividual joe = odf.getOWLNamedIndividual( ":Joe", oFormat )
        OWLNamedIndividual aspirin = odf.getOWLNamedIndividual( ":Aspirin", oFormat )
        OWLNamedIndividual hangover = odf.getOWLNamedIndividual( ":Hangover", oFormat )

        /* Class Assertions */

        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, joe, cPatient ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, aspirin, cDrug ) )
        oom.addAxiom( ont, odf.getOWLObjectPropertyAssertionAxiom( mmType, hangover, cDisorder ) )

        reasoner = new Reasoner( ont );

        assert true == reasoner.isConsistent()

        OWLNamedIndividual joeHasHangover = addAPropertyAssertion( ont, oFormat.getIRI( ":joeHasHangover" ),
                hasDisorder, joe, hangover )

        assert joeHasHangover

//        reasoner.flush()
        reasoner = new Reasoner( ont );
        assert true == reasoner.isConsistent()

//        OWLNamedIndividual joeHasAspirin = addAPropertyAssertion( ont, oFormat.getIRI( ":joeHasAspirin" ),
//                hasDisorder, joe, aspirin )

//        assert joeHasAspirin

//        reasoner.flush()
//        assert false == reasoner.isConsistent()
//
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

        OWLClass mmTriple = odf.getOWLClass( "mm:Triple", oFormat )
        OWLObjectProperty mmPredicate = odf.getOWLObjectProperty( "mm:predicate", oFormat )
        OWLObjectProperty mmSubject = odf.getOWLObjectProperty( "mm:subject", oFormat )
        OWLObjectProperty mmValue = odf.getOWLObjectProperty( "mm:value", oFormat )

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
        oFormat.copyPrefixesFrom( oFormat )

        File outFile = TestFileUtil.getFileInTestResourceDir( "onts/out/" + ontFileName + ".ofn" )
        oom.saveOntology( ont, oFormat, IRI.create( outFile ) )
    }

}
