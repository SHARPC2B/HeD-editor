package sharpc2b.transform

import org.semanticweb.owlapi.apibinding.OWLManager
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat
import org.semanticweb.owlapi.model.IRI
import org.semanticweb.owlapi.model.OWLClass
import org.semanticweb.owlapi.model.OWLDataFactory
import org.semanticweb.owlapi.model.OWLNamedIndividual
import org.semanticweb.owlapi.model.OWLObjectProperty
import org.semanticweb.owlapi.model.OWLOntology
import org.semanticweb.owlapi.model.OWLOntologyFormat
import org.semanticweb.owlapi.model.OWLOntologyManager
import org.semanticweb.owlapi.vocab.Namespaces
import sharpc2b.transform.test.TestFileUtil
import sharpc2b.transform.test.TestUtil

/**
 * User: rk
 * Date: 5/8/13
 * Time: 8:30 AM
 */
class TBoxToABoxTest
extends GroovyTestCase {

    static File inputOntFile = TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.ofn" );

    static IRI outputOntIRI = TestUtil.testIRI( "ClinicalDomainA" );
    static File outputOntFile = TestFileUtil.getFileForTestOutput( "/onts/out/ClinicalDomainInsts8.ofn" );
    static File outputSkosOntFile = TestFileUtil.getFileForTestOutput(
            "/onts/out/SkosClinicalDomainInsts8.ofn" );

    /**
     * Location in the classpath to find properties file containing entity IRIs to use in the output
     * A-Box ontology.
     */
    static String tToAConfigResourcePath = "/OWL-to-Sharp-ABox-Concepts.properties";

    OWLOntologyManager oom;
    OWLOntologyFormat oFormat

    OWLOntology ontT;
    OWLOntology ontA;

    void setUp () {

        oom = OWLManager.createOWLOntologyManager();

        ontT = oom.loadOntologyFromOntologyDocument( inputOntFile );

        ontA = oom.createOntology( outputOntIRI );

        oFormat = IriUtil.getDefaultSharpOntologyFormat();

        oFormat.setPrefix( "a:", ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );
    }

    void tearDown () {
        oom = null;
        ontT = null;
        ontA = null;
        oFormat = null;
    }

    void testResource () {
        URL url;
        url = System.getResource( "/DomainMetaModelABoxEntities.properties" );
        def stream = url.openStream()
        assert stream
        assert 0 < stream.available()
        println url;

        Properties props
        url = System.getResource( "/onts/in/DoesNotExist" );
        assertNull url

//        x= TBoxToABox.class.getResource("onts/in/ClinicalDomain.ofn");
//        println x;
        url = TBoxToABox.class.getResource( "/onts/in/ClinicalDomainT.ofn" );
//        println "url = ${url}";
//        println url.class;
        File f = new File( url.toURI() );
//        println "file = " + f.absolutePath;
        URLConnection conn
        conn = url.openConnection();

        def typ = conn.getContentType();
//        println typ.getClass()
//        println typ


        def content = conn.getContent();
//        println content.class;
//        println "length = " + conn.getContentLength();
        def contentString = conn.getContent( String.class );
//        println contentString?.class;
//
//        String text = url.openStream().text;
//        println text.length();

//       println Thread.currentThread().getContextClassLoader().getResource("/onts/in/ClinicalDomain.ofn")
    }

    void testMetaModelPropertiesMap () {

        URL url;
        url = System.getResource( "/DomainMetaModelABoxEntities.properties" );

        Properties props
        props = new Properties();

        props.load( url.openStream() );

//        println props.stringPropertyNames()

        props.list( System.out );

        assertEquals( 11, props.stringPropertyNames().size() );

        println props;
    }
    /**
     * This is the primary test method.
     */
    void testDefaultABox () {

//        TBoxToABox inst = new TBoxToABox();
        TBoxToABox inst = new TBoxToABox( tToAConfigResourcePath );

        inst.addABoxAxioms( ontT, ontA );

        oom.saveOntology( ontA, oFormat, IRI.create( outputOntFile ) );

        OWLDataFactory odf = oom.getOWLDataFactory();

        /* Check that triple: [age partOf Patient] in output ontology */

        OWLNamedIndividual patient = odf.getOWLNamedIndividual( "a:Patient", oFormat )
        OWLNamedIndividual age = odf.getOWLNamedIndividual( "a:age", oFormat )
        OWLClass clazz = odf.getOWLClass( "ops:DomainClass", oFormat )
        OWLClass prop = odf.getOWLClass( "ops:DomainProperty", oFormat )
        OWLObjectProperty partOf = odf.getOWLObjectProperty( "skos-ext:partOf", oFormat )

        assert patient.getTypes( ontA ).contains( clazz )
        assert age.getTypes( ontA ).contains( prop )
        assert ontA.containsAxiom( odf.getOWLObjectPropertyAssertionAxiom( partOf, age, patient ) )
        assert !ontA.containsAxiom( odf.getOWLObjectPropertyAssertionAxiom( partOf, patient, age ) )
    }

    /**
     * Shows how to use skos:Concept and skos:broaderTransitive as substitutes for owl:Class,
     * rdfs:subClassOf and rdfs:subPropertyOf, instead of the default A-Box entities.
     */
    void testSkosABox () {

        TBoxToABox inst = new TBoxToABox();

        useSkosABoxConcepts( inst, oFormat )

        inst.addABoxAxioms( ontT, ontA );

        oom.saveOntology( ontA, oFormat, IRI.create( outputSkosOntFile ) );
    }

    def useSkosABoxConcepts (TBoxToABox inst,
                             OWLFunctionalSyntaxOntologyFormat oFormat) {
        oFormat.setPrefix( "skos:", Namespaces.SKOS.toString() );

        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "owl:Class" ), oFormat.getIRI( "skos:Concept" ) );
        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "rdfs:subClassOf" ),
                oFormat.getIRI( "skos:broaderTransitive" ) );
        inst.setDomainModelABoxSubstitution( oFormat.getIRI( "rdfs:subPropertyOf" ),
                oFormat.getIRI( "skos:broaderTransitive" ) );
    }
}
