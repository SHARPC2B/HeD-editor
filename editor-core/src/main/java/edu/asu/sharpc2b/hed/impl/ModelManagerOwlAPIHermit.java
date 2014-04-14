package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.common.base.SystemUtil;
import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.EmpireOptions;
import com.clarkparsia.empire.annotation.InvalidRdfException;
import com.clarkparsia.empire.annotation.RdfGenerator;
import com.clarkparsia.empire.config.ConfigKeys;
import com.clarkparsia.empire.config.EmpireConfiguration;
import com.clarkparsia.empire.sesametwo.OpenRdfEmpireModule;
import com.clarkparsia.empire.sesametwo.RepositoryDataSourceFactory;
import com.clarkparsia.empire.util.DefaultEmpireModule;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.sail.SailGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import edu.asu.sharpc2b.transform.HeD2OwlDumper;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import edu.asu.sharpc2b.transform.SharpAnnotationProvider;
import info.aduna.iteration.CloseableIteration;
import org.coode.owlapi.rdf.model.AbstractTranslator;
import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFTranslator;
import org.coode.owlapi.rdfxml.parser.AnonymousNodeChecker;
import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ClassPathResource;
import org.drools.semantics.AbstractObjectGraphVisitor;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.event.base.NotifyingRepositoryWrapper;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.turtle.TurtleWriter;
import org.openrdf.sail.Sail;
import org.openrdf.sail.SailException;
import org.openrdf.sail.memory.MemoryStore;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.w3._2002._07.owl.Thing;
import org.w3._2002._07.owl.ThingImpl;
import org.xml.sax.InputSource;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleParser;

import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
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

    private static final OWLOntology baseTheory = getBaseTheory();
    private static OWLOntologyManager manager;
    private static Set<OWLDeclarationAxiom> cachedBaseAxioms = baseTheory.getAxioms( AxiomType.DECLARATION, true );

    private static PersistenceProvider provider = initEmpire();

    private static ModelManagerOwlAPIHermit instance = new ModelManagerOwlAPIHermit();

    public static ModelManagerOwlAPIHermit getInstance() {
        return instance;
    }


    protected ModelManagerOwlAPIHermit() {
    }


    protected OWLRDFConsumer createConsumer( OWLOntology onto ) {
        OWLRDFConsumer consumer = new OWLRDFConsumer( onto, new AnonymousNodeChecker() {
            @Override
            public boolean isAnonymousNode( IRI iri ) {
                return false;
            }

            @Override
            public boolean isAnonymousNode( String iri ) {
                return false;
            }

            @Override
            public boolean isAnonymousSharedNode( String iri ) {
                return false;
            }
        }, new OWLOntologyLoaderConfiguration() );

        Set<OWLDeclarationAxiom> props = cachedBaseAxioms;
        for ( OWLDeclarationAxiom dat : props ) {
            if ( dat.getEntity().isOWLDataProperty() ) {
                consumer.addDataProperty( dat.getEntity().getIRI(), true );
            } else if ( dat.getEntity().isOWLObjectProperty() ) {
                if ( dat.getEntity().getIRI().toQuotedString().contains( "author" ) ) {
                }
                consumer.addObjectProperty( dat.getEntity().getIRI(), true );
            } else if ( dat.getEntity().isOWLClass() ) {
                consumer.addClassExpression( dat.getEntity().getIRI(), true );
            }
        }
        return consumer;
    }

    protected static OWLOntology getBaseTheory() {
        long now = System.currentTimeMillis();
        org.drools.io.Resource[] res = new org.drools.io.Resource[] {
                ResourceFactory.newClassPathResource( "ontologies/editor_models/DUL.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/IOLite.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/LMM_L1.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/skos-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/skos-ext.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/skos_lmm.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/dc_owl2dl.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/dc2dul.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/metadata.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/expr-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/sharp_operators.ofn" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/prr-core.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/bfo-1.1.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/sharp-time.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/event.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/actions.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/prr-sharp.owl" ),
                ResourceFactory.newClassPathResource( "ontologies/editor_models/sharp.owl" ),
        };

        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        manager = OWLManager.createOWLOntologyManager();

        for ( org.drools.io.Resource r : res ) {

            try {
                InputStream is = r.getInputStream();
                OWLOntologyDocumentSource source = new StreamDocumentSource( is );
                manager.loadOntologyFromOntologyDocument( source, config );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        IRI ontoId = IRI.create( "http://asu.edu/sharpc2b/sharp_merged" );
        try {
            OWLOntology onto = new OWLOntologyMerger( manager ).createMergedOntology( manager, ontoId );
            now = System.currentTimeMillis() - now;
            System.err.println( "Base theory load took " + now );
            return onto;
        } catch( OWLOntologyCreationException e ){
            e.printStackTrace();
        }
        return null;
    }


    public HeDKnowledgeDocument loadRootThingFromOntologyStream( byte[] owlData ) {
        try {
            return hedToObjectGraph( ResourceFactory.newByteArrayResource( owlData ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }


    public HeDKnowledgeDocument hedToObjectGraph( org.drools.io.Resource dataRes )
            throws Exception {

        String id = getOntoId( dataRes );
        OWLOntologyID ontoId = new OWLOntologyID( IRI.create( id ) );
        OWLOntologyManager manager = baseTheory.getOWLOntologyManager();
        manager.loadOntologyFromOntologyDocument( dataRes.getInputStream() );
        OWLOntology onto = manager.getOntology( ontoId );


        convertToOwlAPI_RDF( onto );
        Repository repo = dumpInSailRDFStore( onto );

        EntityManager em = getEmpireEM( provider, repo );

        long now = System.currentTimeMillis();
        System.err.println( "Start de-frosting " );
        HeDKnowledgeDocument o;
        o = em.find( HeDKnowledgeDocumentImpl.class,
                     URI.create( "http://" + id + "#KnowledgeDocument" ) );
        now = System.currentTimeMillis() - now;
        System.err.println( "Empire de-frosting " + now );

        em.close();
        repo.shutDown();

        manager.removeOntology( onto );

        return o;
    }


    public OWLOntology objectGraphToHeDOntology( HeDKnowledgeDocument dok ) {
        try {
            long now = System.currentTimeMillis();
            OWLOntologyID ontoId = new OWLOntologyID( IRI.create( dok.getArtifactId().get( 0 ) ) );

            Sail sail = new MemoryStore();
            Repository repo = new SailRepository( sail );
            repo.initialize();
            final RepositoryConnection conn = repo.getConnection();

            EntityManager em = getEmpireEM( provider, repo );
            em.persist( dok );

            RepositoryResult<Statement> result = conn.getStatements( null, null, null, true, (Resource) null );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            RDFXMLWriter writer = new RDFXMLWriter( baos );
            try {
                writer.startRDF();
                while ( result.hasNext() ) {
                    Statement stat = result.next();
                    writer.handleStatement( stat );
                }
                writer.endRDF();
            } catch ( RDFHandlerException e ) {
                e.printStackTrace();
            }
            conn.close();
            repo.shutDown();
            // And then parse it with an RDF reader, which requires an adapter.

            OWLOntology onto = baseTheory.getOWLOntologyManager().createOntology( ontoId );
            OWLRDFConsumer consumer = createConsumer( onto );

            // The adapter needs to know the classes, data and obj
            // rect properties in the target ontology
            RDFParser rdfParser = new RDFParser();
            InputSource src = new InputSource( new ByteArrayInputStream( baos.toByteArray() ) );
            src.setSystemId( ontoId.getOntologyIRI().toString() );
            rdfParser.parse( src, consumer );

            baseTheory.getOWLOntologyManager().removeOntology( ontoId );

            System.err.println( "EMPIRE frosting took " + (System.currentTimeMillis()-now) );
            return onto;

        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }


    private String getOntoId( org.drools.io.Resource res ) throws IOException {
        InputStream is = res.getInputStream();
        byte[] data = new byte[ is.available() ];
        is.read( data );
        String content = new String( data );
        int x0 = content.indexOf( "Ontology(" );
        int start = content.indexOf( "<", x0 );
        int end = content.indexOf( ">", x0 );
        String ontoId = content.substring( start + 1, end );
        is.close();

        return ontoId;
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


    private com.tinkerpop.blueprints.Graph asPropertyGraph (OWLOntology onto) {

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

        return blueGraph;

    }


    private Repository dumpInSailRDFStore (OWLOntology onto) throws RepositoryException {

        long now = new Date().getTime();
        Sail sail = new MemoryStore();
        Repository repo = new SailRepository( sail );
        repo.initialize();
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
                return new LiteralImpl( literal.getLiteral(), new URIImpl( literal.getDatatype().getIRI().toString() ) );
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


    private void convertToOwlAPI_RDF (OWLOntology onto) {
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

        /*
        RDFGraph rdfGraph = translator.getGraph();
        try
        {
            rdfGraph.dumpTriples( new PrintWriter( System.err ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        */
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


    protected static PersistenceProvider initEmpire() {
        URL rx = ModelManagerOwlAPIHermit.class.getResource( "/empire.configuration.file" );
        System.setProperty( "empire.configuration.file", rx.getPath() );

        OpenRdfEmpireModule mod = new OpenRdfEmpireModule();
        EmpireConfiguration ec = new EmpireConfiguration();
        ec.setAnnotationProvider( SharpAnnotationProvider.class );

        Empire.init( ec, new DefaultEmpireModule( ec ), mod );
        EmpireOptions.STRICT_MODE = false;
        EmpireOptions.ENFORCE_ENTITY_ANNOTATION = false;

        return Empire.get().persistenceProvider();
    }

    public EntityManager getEmpireEM( PersistenceProvider provider, Repository repo ) {
        Map<String, Object> aMap = new HashMap<String, Object>();
        aMap.put( ConfigKeys.FACTORY, RepositoryDataSourceFactory.class.getName() );
        aMap.put( RepositoryDataSourceFactory.REPO_HANDLE, repo );
//        aMap.put( RepositoryDataSourceFactory.QUERY_LANG, RepositoryDataSourceFactory.LANG_SERQL );

        EntityManagerFactory emf = provider.createEntityManagerFactory(
                "HeD",
                aMap );

        EntityManager em = emf.createEntityManager();
        return em;
    }
}
