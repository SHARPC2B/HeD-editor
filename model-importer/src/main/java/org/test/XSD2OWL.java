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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class XSD2OWL {

    private KnowledgeBase kBase;

    private OWLOntologyManager manager;
    private OWLDataFactory factory;

    private static File metaSchema = new File( "/home/davide/Projects/xsd/src/main/resources/xsd/xmlschema.xsd" );

    private static List<InferredAxiomGenerator<? extends OWLAxiom>> fullAxiomGenerators = Collections.unmodifiableList(
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



    public XSD2OWL() {
        System.out.println( "Creating converter...." );
        manager = OWLManager.createOWLOntologyManager();
        factory = manager.getOWLDataFactory();
        kBase = initKBase();
        System.out.println( "Created converter...." );
    }

    private KnowledgeBase initKBase() {
        KnowledgeBuilder kBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kBuilder.add( new ClassPathResource("drl/xsd2owl_bug.drl"), ResourceType.DRL );
        if ( kBuilder.hasErrors() ) {
            throw new RuntimeDroolsException( kBuilder.getErrors().toString() );
        }
        kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addKnowledgePackages( kBuilder.getKnowledgePackages() );

        return kBase;
    }

    public Schema parse( String schemaLocation ) {
        try {
            System.out.println( "Parsing schema...." );
            JAXBContext context = JAXBContext.newInstance( Schema.class.getPackage().getName() );
            javax.xml.validation.Schema metax = SchemaFactory.newInstance( Namespaces.XSD.toString().replace( "#", "" ) ).newSchema(metaSchema);
            Unmarshaller loader = context.createUnmarshaller();
            loader.setSchema( metax );

            Schema schema = (Schema) loader.unmarshal( new File( schemaLocation ) );
            System.out.println( "Parsed schema...." );
            return schema;
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    public OWLOntology transform(Schema schema, boolean verbose, boolean checkConsistency) {
        System.out.println( "Transforming...." );
        OWLOntology ontology = null;
        try {
            ontology = manager.createOntology( new OWLOntologyID(
                    IRI.create( schema.getTargetNamespace() ),
                    IRI.create( schema.getTargetNamespace() + "/" +
                            ( schema.getVersion() != null ? schema.getVersion() : "1.0" ) ) ) );
        } catch ( OWLOntologyCreationException e ) {
            e.printStackTrace();
            return null;
        }

        StatefulKnowledgeSession kSession = kBase.newStatefulKnowledgeSession();
        kSession.setGlobal( "ontology", ontology );
        kSession.setGlobal( "manager", manager );
        kSession.setGlobal( "factory", factory );

        visit( schema, kSession );

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

        System.out.println( "DONE!" );

        return ontology;
    }

    private String getPrefix( String targetNamespace ) {
        return targetNamespace.endsWith( "/" ) || targetNamespace.endsWith( "#" ) || targetNamespace.endsWith( ":" ) ?
                targetNamespace : targetNamespace + "#";
    }

    private void visit( Schema schema, StatefulKnowledgeSession kSession ) {
        for ( OpenAttrs ext : schema.getIncludeOrImportOrRedefine() ) {
            if ( ext instanceof Include ) {
                Include include = (Include) ext;
                //TODO: make it work with downloadable files
                Schema sub = parse( include.getSchemaLocation() );
                visit( sub, kSession );
            } else if ( ext instanceof Import ) {
                Import imp = (Import) ext;
                Schema sub = parse( imp.getSchemaLocation() );
                visit( sub, kSession );
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

//        for ( Object o : kSession.getObjects() ) {
//            System.out.println( ">> " + o );
//        }
//        if ( 1 == 1 ) System.exit(-1);


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




}
