package org.test;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuntimeDroolsException;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.Variable;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NullReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.w3.x2001.xmlschema.Import;
import org.w3.x2001.xmlschema.Include;
import org.w3.x2001.xmlschema.OpenAttrs;
import org.w3.x2001.xmlschema.Redefine;
import org.w3.x2001.xmlschema.Schema;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Xsd2OwlImpl implements Xsd2Owl {

    private static Xsd2OwlImpl instance;

    public static Xsd2Owl getInstance() {
        if ( instance == null ) {
            instance = new Xsd2OwlImpl();
        }
        return instance;
    }

    private KnowledgeBase kBase;
    private static File metaSchema;

    private static List<InferredAxiomGenerator<? extends OWLAxiom>> fullAxiomGenerators;

    {
        try {
            metaSchema = new File( new ClassPathResource( "xsd/xmlschema.xsd" ).getURL().toURI() );
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        fullAxiomGenerators = Collections.unmodifiableList(
                new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>(
                        Arrays.asList(
                                new InferredClassAssertionAxiomGenerator(),
                                new InferredDataPropertyCharacteristicAxiomGenerator(),
                                new InferredEquivalentClassAxiomGenerator(),
                                new InferredEquivalentDataPropertiesAxiomGenerator(),
                                new InferredEquivalentObjectPropertyAxiomGenerator(),
                                new InferredInverseObjectPropertiesAxiomGenerator(),
                                new InferredObjectPropertyCharacteristicAxiomGenerator(),
                                new InferredPropertyAssertionGenerator(),
                                new InferredSubClassAxiomGenerator(),
                                new InferredSubDataPropertyAxiomGenerator(),
                                new InferredSubObjectPropertyAxiomGenerator()
                        )));
    }


    protected Xsd2OwlImpl() {
        System.out.println( "Creating converter...." );
        kBase = initKBase();
        System.out.println( "Created converter...." );
    }

    private KnowledgeBase initKBase() {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add( new ClassPathResource( "drl/xsd2owl.drl" ), ResourceType.DRL );
        if ( kBuilder.hasErrors() ) {
            throw new RuntimeDroolsException( kBuilder.getErrors().toString() );
        }
        kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addKnowledgePackages( kBuilder.getKnowledgePackages() );

        return kBase;
    }

    public URL getSchemaURL( String schemaLocation ) {
        ClassPathResource cpr = new ClassPathResource( schemaLocation );
        try {
            return cpr.getURL();
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public Schema parse( URL schemaLocation ) {
        try {
            System.out.println( "Parsing schema.... " + schemaLocation );
            JAXBContext context = JAXBContext.newInstance( Schema.class.getPackage().getName() );
            javax.xml.validation.Schema metax = SchemaFactory.newInstance( Namespaces.XSD.toString().replace( "#", "" ) ).newSchema(metaSchema);
            Unmarshaller loader = context.createUnmarshaller();
            loader.setSchema( metax );

            Schema schema = (Schema) loader.unmarshal( new File( schemaLocation.toURI() ) );
            System.out.println( "Parsed schema...." );

            if ( schema.getTargetNamespace() == null ) {
                schema.setTargetNamespace( "" );
            }

            return schema;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public OWLOntology transform( Schema schema, URL schemaURL, boolean verbose, boolean checkConsistency ) {
        System.out.println( "Transforming...." );
        StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession();
        OWLOntology ontology = null;

        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();
            ontology = manager.createOntology( new OWLOntologyID(
                    IRI.create( schema.getTargetNamespace() ),
                    IRI.create( schema.getTargetNamespace() + "/" +
                            ( schema.getVersion() != null ? schema.getVersion() : "1.0" ) ) ) );


            kSession.setGlobal( "ontology", ontology );
            kSession.setGlobal( "manager", manager );
            kSession.setGlobal( "factory", factory );

            visit( schema, schemaURL, kSession, manager );

            if ( verbose ) {
                try {
                    manager.saveOntology(
                            ontology,
                            new ManchesterOWLSyntaxOntologyFormat(),
                            System.out);
                } catch ( OWLOntologyStorageException e ) {
                    e.printStackTrace();
                }
            }

            if ( checkConsistency ) {
                launchReasoner( ontology, fullAxiomGenerators );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            try {
                return OWLManager.createOWLOntologyManager().createOntology();
            } catch (OWLOntologyCreationException e1) {
                e1.printStackTrace();
                return null;
            }
        } finally {
            kSession.dispose();
        }

        System.out.println( "DONE!" );

        return ontology;
    }

    private String getPrefix( String targetNamespace ) {
        return targetNamespace.endsWith( "/" ) || targetNamespace.endsWith( "#" ) || targetNamespace.endsWith( ":" ) ?
                targetNamespace : targetNamespace + "#";
    }

    private void visit( Schema schema, URL schemaLocation, StatefulKnowledgeSession kSession, OWLOntologyManager manager ) throws MalformedURLException {
        for ( OpenAttrs ext : schema.getIncludeOrImportOrRedefine() ) {
            if ( ext instanceof Include ) {
                Include include = (Include) ext;
                URL url = new URL( schemaLocation, include.getSchemaLocation() );
                Schema sub = parse( url );
                visit( sub, url, kSession, manager );
            } else if ( ext instanceof Import ) {
                Import imp = (Import) ext;
                URL url = new URL( schemaLocation, imp.getSchemaLocation() );
                Schema sub = parse( url );
                visit( sub, url, kSession, manager );
            } else if ( ext instanceof Redefine ) {
                Redefine redefine = (Redefine) ext;
                throw new UnsupportedOperationException( "Implement redefines" );
            }
        }

        System.out.println( "Visiting schema.... " );
        kSession.setGlobal( "tns", schema.getTargetNamespace() );

//        kSession.insert( schema );
        new Jaxplorer( schema ).deepInsert( kSession );

        kSession.fireAllRules();
        System.out.println( "Visited, now adding axioms " );

        Set set = (Set) kSession.getQueryResults( "axioms", Variable.v ).iterator().next().get( "$set" );
        OWLOntology ontology = (OWLOntology) kSession.getGlobal( "ontology" );

        for ( Object ax : set ) {
            manager.addAxiom( ontology, (OWLAxiom) ax );
        }

        System.out.println( "Done with schema.... " );

    }


    public boolean stream( OWLOntology onto, OutputStream stream, OWLOntologyFormat format ) {
        try {
            onto.getOWLOntologyManager().saveOntology( onto, format, stream );
            return true;
        } catch (OWLOntologyStorageException e) {
            return false;
        }
    }

    private void launchReasoner( OWLOntology ontoDescr,
                                 List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators ) {
        long now = new Date().getTime();
        System.err.println( " START REASONER " );

        InferredOntologyGenerator reasoner = initReasoner( ontoDescr, axiomGenerators );

        reasoner.fillOntology( ontoDescr.getOWLOntologyManager(), ontoDescr );

        System.err.println( " STOP REASONER : time elapsed >> " + ( new Date().getTime() - now ) );

    }




    protected InferredOntologyGenerator initReasoner( OWLOntology ontoDescr,
                                                      List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators ) {

        ReasonerProgressMonitor progressMonitor = new NullReasonerProgressMonitor(); //new ConsoleProgressMonitor();
        OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);


        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        OWLReasoner owler = reasonerFactory.createReasoner( ontoDescr, config );
        owler.precomputeInferences(

                InferenceType.CLASS_HIERARCHY,
                InferenceType.CLASS_ASSERTIONS,

                InferenceType.DATA_PROPERTY_ASSERTIONS,
                InferenceType.DATA_PROPERTY_HIERARCHY,

                InferenceType.DIFFERENT_INDIVIDUALS,

                InferenceType.DISJOINT_CLASSES,

                InferenceType.OBJECT_PROPERTY_ASSERTIONS,
                InferenceType.OBJECT_PROPERTY_HIERARCHY,

                InferenceType.SAME_INDIVIDUAL
        );


        return new InferredOntologyGenerator( owler, axiomGenerators );

    }




    public String compactXMLSchema( String resourceName ) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, TransformerException, URISyntaxException {
        ClassPathResource cpr = new ClassPathResource( resourceName );

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xPath = xpathFactory.newXPath();

        DocumentBuilderFactory doxFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = doxFactory.newDocumentBuilder();
        InputSource is = new InputSource( new FileInputStream( new File( cpr.getURL().toURI().getPath() ) ) );
        Document dox = builder.parse( is );
        dox.normalize();

        XPathExpression xpathExp = xPath.compile( "//*[local-name()='annotation']" );
        NodeList annotationNodes  = (NodeList) xpathExp.evaluate( dox, XPathConstants.NODESET );
        // Remove each empty text node from document.
        for (int i = 0; i < annotationNodes.getLength(); i++) {
            Node annotationNode = annotationNodes.item(i);
            annotationNode.getParentNode().removeChild(annotationNode);
        }


        XPathExpression xpathExp2 = xPath.compile( "//text()[normalize-space(.) = '']" );
        NodeList emptyTextNodes = (NodeList) xpathExp2.evaluate(dox, XPathConstants.NODESET );
        for (int i = 0; i < emptyTextNodes.getLength(); i++) {
            Node emptyTextNode = emptyTextNodes.item(i);
            emptyTextNode.getParentNode().removeChild( emptyTextNode );
        }

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource domSrc = new DOMSource( dox );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult( baos );
        transformer.transform( domSrc, result );

        return new String( baos.toByteArray() );
    }


}
