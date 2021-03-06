package sharpc2b.transform

import edu.asu.sharpc2b.transform.IriUtil
import edu.asu.sharpc2b.transform.TBoxToABox
import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.model.*
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat
import sharpc2b.transform.test.TestFileUtil

/**
 * Demonstrate use of TBoxToABox.
 *
 * User: rk
 * Date: 5/20/13
 * Time: 11:11 PM
 */
class OwlTDomainToSharpAConceptsTest
extends GroovyTestCase {

    static File inputOntFile = TestFileUtil.getResourceAsFile( "/ontologies/in/ClinicalDomainT.ofn" );

    static IRI outputOntIRI = IRI.create( "http://asu.edu/sharpc2b/test/ClinicalDomainConceptsA" );
    static String outputOntFile = "/out/ClinicalDomainConcepts.ofn" ;

    /**
     * Properties file to load to specify the meta-model entity IRIs to use for the output ontology.
     * This file uses concepts from new Sharp ontologies, like ops:DomainProperty and
     * skos-ext:evaluatesAs.
     */
    final static String resourceName_metaModelNames = "/OWL-to-Sharp-ABox-Concepts.properties";

    //====================================================================================

    TBoxToABox inst;

    OWLOntologyManager oom;
    PrefixOWLOntologyFormat oFormat
    OWLDataFactory odf

    /**
     * input Ontology
     */
    OWLOntology tboxModel;

    /**
     * output Ontology
     */
    OWLOntology aboxModel;

    void setUp () {

        inst = new TBoxToABox( resourceName_metaModelNames );

        oom = OWLManager.createOWLOntologyManager();

        odf = oom.getOWLDataFactory()

        tboxModel = oom.loadOntologyFromOntologyDocument( inputOntFile );

        aboxModel = oom.createOntology( outputOntIRI );

        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        oFormat.setDefaultPrefix( aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", aboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "a:", "http://asu.edu/sharpc2b/ops" + "#" );
        oFormat.setPrefix( "t:", tboxModel.getOntologyID().getOntologyIRI().toString() + "#" );
//        oFormat.setPrefix( "mm:", TBoxToABox.aboxDomainMetaModelIRI.toString() + "#" );
    }

    void tearDown () {

    }

    void testCreateABox () {
//        println "BEGIN Test"

        inst.addABoxAxioms( tboxModel, aboxModel );

        OWLNamedIndividual patientA = odf.getOWLNamedIndividual( "t:Patient", oFormat )
        OWLClass domainClass = odf.getOWLClass( "a:DomainClass", oFormat )

        def axioms = patientA.getReferencingAxioms( aboxModel )

        def types = patientA.getTypes( aboxModel )
        assertEquals( 1, types.size() )
        assertEquals( domainClass, types.iterator().next() )

        assertEquals( 5, axioms.size() )

        String path = inputOntFile.getParentFile().getParentFile().getPath() + outputOntFile;
        oom.saveOntology( aboxModel, oFormat, IRI.create( new File( path ) ) );

//        println "END Test"
    }


}
