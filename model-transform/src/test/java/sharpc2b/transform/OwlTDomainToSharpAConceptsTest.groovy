package sharpc2b.transform

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.util.DefaultPrefixManager

/**
 * User: rk
 * Date: 5/20/13
 * Time: 11:11 PM
 */
class OwlTDomainToSharpAConceptsTest
extends GroovyTestCase {

    static File inputOntFile = FileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.ofn" );

    static IRI outputOntIRI = IRI.create( "http://asu.edu/sharpc2b/test/ClinicalDomainConceptsA" );
    static File outputOntFile = FileUtil.getFileInTestResourceDir( "onts/out/ClinicalDomainConcepts.ofn" );

    final static String resourceName_metaModelNames = "/OWL-to-Sharp-ABox-Concepts.properties";

    //====================================================================================

    TBoxToABox inst;

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat
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

        oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( new DefaultPrefixManager() );
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

        inst.populateABox( tboxModel, aboxModel );

        OWLNamedIndividual patientA = odf.getOWLNamedIndividual( ":Patient", oFormat )
        OWLClass domainClass = odf.getOWLClass( "a:DomainClass", oFormat )

        def axioms = patientA.getReferencingAxioms( aboxModel )

        def types = patientA.getTypes( aboxModel )
        assertEquals( 1, types.size() )
        assertEquals( domainClass, types.iterator().next() )

        assertEquals( 5, axioms.size() )

        oom.saveOntology( aboxModel, oFormat, IRI.create( outputOntFile ) );

//        println "END Test"
    }


}
