import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.EmpireOptions;
import com.clarkparsia.empire.config.ConfigKeys;
import com.clarkparsia.empire.config.EmpireConfiguration;
import com.clarkparsia.empire.sesametwo.OpenRdfEmpireModule;
import com.clarkparsia.empire.sesametwo.RepositoryDataSourceFactory;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.sail.SailGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import org.coode.owlapi.rdf.model.AbstractTranslator;
import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFTranslator;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.drools.io.ResourceFactory;
import org.drools.semantics.AbstractObjectGraphVisitor;
import org.hl7.v3.hed.KnowledgeDocument;
import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.event.base.NotifyingRepositoryWrapper;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.Sail;
import org.openrdf.sail.memory.MemoryStore;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredIndividualAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;
import org.w3._2002._07.owl.Thing;
import org.w3._2002._07.owl.ThingImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//import edu.asu.sharpc2b.prr.ProductionRuleSetImpl;
//import org.drools.semantics.AbstractObjectGraphVisitor;
//import org.w3._2002._07.owl.Thing;
//import org.w3._2002._07.owl.ThingImpl;

public class ModelManagerOwlAPIHermit
{

    private static final String NS_BASE = "http://asu.edu/sharpc2b/";

    private static final String EXAMPLE1 = NS_BASE + "example1";

    private static final String PRR = NS_BASE + "prr";

    private static final String EXAMPLE_root = EXAMPLE1 + "#" + "ruleSet1";



    public OWLOntology parseOntology ( IRI target,
                                       org.drools.io.Resource[] resources)
    {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setMissingOntologyHeaderStrategy(
                OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy.IMPORT_GRAPH );

        for ( org.drools.io.Resource res : resources ) {

            try
            {
                InputStream is = res.getInputStream();
                OWLOntologyDocumentSource source = new StreamDocumentSource( is );
                manager.loadOntologyFromOntologyDocument( source, config );
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println( "MANAGER HAS ONTOL " + manager.getOntologies().size() );
        for (OWLOntology ox : manager.getOntologies())
        {
            System.out.println( ox.getOntologyID() + " >> imports " + ox.getImports().size() );
        }
        return manager.getOntology( new OWLOntologyID( target ) );
    }


//    public void loadModel ()
//            throws Exception
//    {
//
//        OWLOntology onto = parseOntology( IRI.create( PRR ), "/ontologies/", sources );
//        System.out.println( "Loaded model size " + onto.getImports().size() );
//
//        System.out.println( onto.getABoxAxioms( true ).size() );
//
//        List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators
//                = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
//        axiomGenerators.add( new InferredClassAssertionAxiomGenerator() );
////            axiomGenerators.add(new InferredDataPropertyCharacteristicAxiomGenerator());
//        axiomGenerators.add( new InferredEquivalentClassAxiomGenerator() );
////            axiomGenerators.add(new InferredEquivalentDataPropertiesAxiomGenerator());
//        axiomGenerators.add( new InferredEquivalentObjectPropertyAxiomGenerator() );
////            axiomGenerators.add(new InferredInverseObjectPropertiesAxiomGenerator());
////            axiomGenerators.add(new InferredObjectPropertyCharacteristicAxiomGenerator());
//        axiomGenerators.add( new InferredPropertyAssertionGenerator() );
//        axiomGenerators.add( new InferredSubClassAxiomGenerator() );
////            axiomGenerators.add(new InferredSubDataPropertyAxiomGenerator());
//        axiomGenerators.add( new InferredSubObjectPropertyAxiomGenerator() );
//        applyReasoner( onto, axiomGenerators );
//
//        System.out.println( onto.getABoxAxioms( true ).size() );
//
//        File base = new File(
//                ModelManagerOwlAPIHermit.class.getResource( "/ontologies/" + sources[0] ).getPath() );
////        File dir = new File( base.getParent() + File.separator + "precompiled" );
////        if ( ! dir.exists() ) {
////            dir.mkdirs();
////        }
////        System.out.println( dir.getAbsolutePath() );
//
//        OutputStream outStream = new FileOutputStream(
//                new File( base.getParentFile().getAbsolutePath() + File.separator + "prr_inf.owl" ) );
//        OWLOntologyFormat format = new TurtleOntologyFormat();
//        onto.getOWLOntologyManager().saveOntology( onto, format, outStream );
//
//    }


    public void processExample ()
            throws Exception
    {


        org.drools.io.Resource[] res = new org.drools.io.Resource[] {
                ResourceFactory.newClassPathResource( "ontologies/DUL.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/IOLite.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/LMM_L1.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/skos-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/skos-ext.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/skos_lmm.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/dc_owl2dl.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/dc2dul.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/metadata.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/expr-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/actions.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/events.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/domain.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/prr-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/prr-sharp.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/sharp.owl" ),

                ResourceFactory.newFileResource( "/home/davide/Projects/Git/sharp-editor/model-importer/target/generated-sources/org/hl7/v3/hed/newMentor.owl" ),
        };

        OWLOntology onto = parseOntology( IRI.create( "www.newmentor.com/cds/rule/NQF-0068" ), res );
        System.out.println( "Loaded model size " + onto.getImports().size() );

        List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators
                = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
//        axiomGenerators.add( new InferredClassAssertionAxiomGenerator() );
//        applyReasoner( onto, axiomGenerators );


        convertToOwlAPI_RDF( onto );


        Repository repo = dumpInSailRDFStore( onto );


        Map<String, Object> aMap = new HashMap<String, Object>();

        aMap.put( ConfigKeys.FACTORY, RepositoryDataSourceFactory.class.getName() );
        aMap.put( RepositoryDataSourceFactory.REPO_HANDLE, repo );

        OpenRdfEmpireModule mod = new OpenRdfEmpireModule();
        EmpireConfiguration ec = new EmpireConfiguration();
        Empire.init( ec, mod );
        EmpireOptions.STRICT_MODE = false;
        EmpireOptions.ENFORCE_ENTITY_ANNOTATION = false;

        PersistenceProvider aProvider = Empire.get().persistenceProvider();
        EntityManagerFactory emf = aProvider.createEntityManagerFactory(
                "HeD",
                aMap );
        EntityManager em = emf.createEntityManager();


        System.out.println( em.isOpen() );


        long now = System.currentTimeMillis();
        Object o;
        o = em.find( HeDKnowledgeDocument.class,
                     URI.create( "http://www.newmentor.com/cds/rule/NQF-0068#KnowledgeDocument" ) );
        now = System.currentTimeMillis() - now;
        System.err.println( "Empire de-frosting " + now );

        com.tinkerpop.blueprints.Graph blueGraph1 = asPropertyGraph( onto );

        com.tinkerpop.blueprints.Graph blueGraph2 = asObjectGraph( (Thing) o );
//
//
        System.out.println( o );
        em.close();
        repo.shutDown();
//
//
        File base = new File( ModelManagerOwlAPIHermit.class.getResource( "." ).getPath() + "../../target/generated-sources/" );
        if ( ! base.exists() ) {
            base.mkdirs();
        }
        System.out.println( base.getAbsolutePath() );
        OutputStream outStream = new FileOutputStream(
                new File( base.getAbsolutePath() + File.separator + "newmentor.obj.gson" ) );
        GraphSONWriter.outputGraph( blueGraph2, outStream );

    }




    private com.tinkerpop.blueprints.Graph asObjectGraph (Thing o)  {

        long now = System.currentTimeMillis();

        Graph blueGraph = (Graph) new AbstractObjectGraphVisitor() {

            private Graph blueGraph = new TinkerGraph();

            @Override
            protected Object visitRoot (org.drools.semantics.Thing root)
            {
                visitNode( root );
                return blueGraph;
            }

            @Override
            protected void visitRelationEdge ( org.drools.semantics.Thing node,
                                               String prop,
                                               Object tgt ) {
                if ( tgt instanceof ThingImpl ) {

                    Vertex v = blueGraph.getVertex( ( (ThingImpl) tgt ).getRdfId() );
                    if ( v == null ) {
                        v = blueGraph.addVertex( ( (ThingImpl) tgt ).getRdfId() );
                    }

                    visitNode( (Thing) tgt );

                    blueGraph.addEdge( UUID.randomUUID(),
                                       blueGraph.getVertex( node.getRdfId() ),
                                       v,
                                       prop );

                } else {
                    Vertex v = blueGraph.getVertex( node.getRdfId() );
                    v.setProperty( prop, tgt );
                }
            }

            @Override
            protected void visitTypeEdge ( org.drools.semantics.Thing node,
                                           URI tgt ) {
                visitRelationEdge( node, "isA", tgt );
            }

            @Override
            protected void postVisitNode ( org.drools.semantics.Thing node ) {

            }

            @Override
            protected void preVisitNode ( org.drools.semantics.Thing node ) {
                if ( blueGraph.getVertex( node.getRdfId() ) == null ) {
                    blueGraph.addVertex( node.getRdfId() );
                }
            }

        }.visit( o );

        System.err.println(
                " DUMPING AS OBJECT GRAPH : time elapsed >> " + (System.currentTimeMillis() - now) );
        System.err.println( blueGraph );

        return blueGraph;
    }


    private com.tinkerpop.blueprints.Graph asPropertyGraph (OWLOntology onto)
    {

        long now = System.currentTimeMillis();
        Set<OWLNamedIndividual> inds = onto.getIndividualsInSignature( false );
        final com.tinkerpop.blueprints.Graph blueGraph = new TinkerGraph();

        AbstractTranslator tinker = new AbstractTranslator( onto.getOWLOntologyManager(), onto, false )
        {

            @Override
            protected Object getResourceNode (IRI IRI)
            {
                return new URIImpl( IRI.toURI().toString() );
            }

            @Override
            protected Object getPredicateNode (IRI IRI)
            {
                return new URIImpl( IRI.toURI().toString() );
            }

            @Override
            protected Object getAnonymousNode (Object key)
            {
                return new URIImpl( key.toString() );
            }

            @Override
            protected Object getLiteralNode (OWLLiteral literal)
            {
                return new LiteralImpl( literal.getLiteral(), literal.getLang() );
            }

            @Override
            protected void addTriple (Object subject,
                                      Object pred,
                                      Object object)
            {
                Vertex src = blueGraph.getVertex( subject );
                if (src == null)
                {
                    src = blueGraph.addVertex( subject );
                }
                Vertex tgt = blueGraph.getVertex( object );
                if (tgt == null)
                {
                    tgt = blueGraph.addVertex( object );
                }
                blueGraph.addEdge( UUID.randomUUID(), src, tgt,
                                   ((org.openrdf.model.URI) pred).getLocalName() );

            }
        };

        for (OWLNamedIndividual ind : inds)
        {
            Set<OWLIndividualAxiom> data = onto.getAxioms( ind );
            for (OWLIndividualAxiom ax : data)
            {
                ax.accept( tinker );
            }
        }
        System.err.println( " DUMPING AS GRAPH : time elapsed >> " + (System.currentTimeMillis() - now) );


        return blueGraph;

    }


    private Repository dumpInSailRDFStore (OWLOntology onto)
            throws RepositoryException
    {

        long now = new Date().getTime();
        Sail sail = new MemoryStore();
        Repository repo = new NotifyingRepositoryWrapper( new SailRepository( sail ) );
        final com.tinkerpop.blueprints.Graph sailGraph = new SailGraph( sail );
        Set<OWLNamedIndividual> inds = onto.getIndividualsInSignature( false );

        final RepositoryConnection conn = repo.getConnection();

        AbstractTranslator sailor = new AbstractTranslator( onto.getOWLOntologyManager(), onto, false )
        {

            @Override
            protected Object getResourceNode (IRI IRI)
            {
                return new URIImpl( IRI.toURI().toString() );
            }

            @Override
            protected Object getPredicateNode (IRI IRI)
            {
                return new URIImpl( IRI.toURI().toString() );
            }

            @Override
            protected Object getAnonymousNode (Object key)
            {
                return new URIImpl( key.toString() );
            }

            @Override
            protected Object getLiteralNode (OWLLiteral literal)
            {
                return new LiteralImpl( literal.getLiteral(), literal.getLang() );
            }

            @Override
            protected void addTriple (Object subject,
                                      Object pred,
                                      Object object)
            {
                try
                {
                    conn.add( (Resource) subject, (org.openrdf.model.URI) pred, (Value) object );
                }
                catch (RepositoryException re)
                {
                    re.printStackTrace();
                }
            }
        };

        for (OWLNamedIndividual ind : inds)
        {
            Set<OWLIndividualAxiom> data = onto.getAxioms( ind );
            for (OWLIndividualAxiom ax : data)
            {
                ax.accept( sailor );
            }
        }
        conn.commit();
        conn.close();

        System.err.println( " DUMPING INTO SAIL : time elapsed >> " + (new Date().getTime() - now) );
        return repo;
    }


    private void convertToOwlAPI_RDF (OWLOntology onto)
    {
        RDFTranslator translator = new RDFTranslator( onto.getOWLOntologyManager(), onto, true );

        long now = new Date().getTime();
        Set<OWLNamedIndividual> inds = onto.getIndividualsInSignature( false );
        for (OWLNamedIndividual ind : inds)
        {
            Set<OWLIndividualAxiom> data = onto.getAxioms( ind );
            for (OWLIndividualAxiom ax : data)
            {
                ax.accept( translator );

            }
        }
        System.err.println( " CONVERSION : time elapsed >> " + (new Date().getTime() - now) );

        RDFGraph rdfGraph = translator.getGraph();

        try
        {
            rdfGraph.dumpTriples( new PrintWriter( System.err ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    private void applyReasoner (OWLOntology ontoDescr,
                                List<InferredAxiomGenerator<? extends OWLAxiom>> axiomGenerators)
    {
        long now = new Date().getTime();
        System.err.println( " START REASONER " );

        ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
        OWLReasonerConfiguration config = new SimpleConfiguration( progressMonitor );

        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        OWLReasoner owler = reasonerFactory.createReasoner( ontoDescr, config );


        InferredOntologyGenerator reasoner = new InferredOntologyGenerator( owler, axiomGenerators );
        for (InferredAxiomGenerator ax : reasoner.getAxiomGenerators())
        {
            System.err.println( "Inference reasoner will do " + ax );
        }

        if (!owler.isConsistent())
        {
            throw new IllegalStateException( "INCONSISTENCY " );
        }

        reasoner.fillOntology( ontoDescr.getOWLOntologyManager(), ontoDescr );

        System.err.println( " STOP REASONER : time elapsed >> " + (new Date().getTime() - now) );


    }


}
