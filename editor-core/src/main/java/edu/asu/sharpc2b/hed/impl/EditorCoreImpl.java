package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.hed.ArtifactRepository;
import edu.asu.sharpc2b.hed.ArtifactRepositoryFactory;
import edu.asu.sharpc2b.hed.FilesystemArtifactRepository;
import edu.asu.sharpc2b.hed.api.ArtifactStore;
import edu.asu.sharpc2b.hed.api.DomainModel;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.transform.HeD2OwlDumper;
import edu.asu.sharpc2b.transform.HeDExporterFactory;
import edu.asu.sharpc2b.transform.OOwl2HedDumper;
import org.drools.io.Resource;
import org.drools.io.impl.ClassPathResource;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EditorCoreImpl implements EditorCore, DomainModel, ArtifactStore {

    private static final String DOMAIN_MODEL_PATH = "ontologies/domain_models/domain-vmr.ofn";
    private static final String DOMAIN_NS = "urn:hl7-org:vmr:r2#";


    private ConcurrentMap<String,HeDArtifactData> artifacts;

    private String currentArtifactId;

    private ArtifactRepository knowledgeRepo = ArtifactRepositoryFactory.getRepository( "FILE" );

    private static EditorCoreImpl instance = new EditorCoreImpl();

    public static EditorCore getInstance() {
        return instance;
    }


    public static String newArtifactId() {
        String uuid = UUID.randomUUID().toString();
        return "asu.bmi.edu_" + System.identityHashCode( uuid );
    }


    protected EditorCoreImpl() {
        artifacts = new ConcurrentHashMap<String,HeDArtifactData>();
    }

    
    public String getCurrentArtifactId() {
        return currentArtifactId;
    }
    
    public HeDArtifactData getCurrentArtifact() {
        return artifacts.get( currentArtifactId ); 
    }

    void setCurrentArtifactId( String currentArtifactId ) {
        this.currentArtifactId = currentArtifactId;
    }

    public HeDKnowledgeDocument getArtifact( String id ) {
        return getArtifactData( id ).getKnowledgeDocument();
    }
    public HeDArtifactData getArtifactData( String id ) {
        if ( artifacts.containsKey( id ) ) {
            return artifacts.get( id );
        } else {
            return artifacts.get( currentArtifactId );
        }
    }


    @Override
    public String createArtifact() {
        System.out.println( "Create artifact using repo" +  knowledgeRepo.getClass().getName() );

        HeDArtifactData artifact = new HeDArtifactData();

        currentArtifactId = artifact.getArtifactId();

        InputStream stream = new ByteArrayInputStream( artifact.getOwlData() );

        artifacts.put( currentArtifactId, artifact );

        knowledgeRepo.createArtifact( currentArtifactId, artifact.getTitle(), stream );

        return currentArtifactId;
    }


    @Override
    public List<String> getAvailableArtifacts() {
        return knowledgeRepo.getAvailableArtifacts();
    }

    @Override
    public String importFromStream( byte[] hedStream ) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteArrayInputStream bais = new ByteArrayInputStream( hedStream );
            new HeD2OwlDumper().compile( bais, baos );
            byte[] owlBytes = baos.toByteArray();

            HeDKnowledgeDocument knowledgeDocument = new ModelManagerOwlAPIHermit().loadRootThingFromOntologyStream( owlBytes );
            HeDArtifactData artifact = new HeDArtifactData( knowledgeDocument, owlBytes );

            currentArtifactId = artifact.getArtifactId();

            artifacts.put( currentArtifactId, artifact );

            knowledgeRepo.createArtifact( currentArtifactId, artifact.getTitle(), new ByteArrayInputStream( owlBytes ) );

            baos.close();

            return currentArtifactId;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String cloneArtifact( String id ) {
        HeDArtifactData artifact = artifacts.get( id );
        return knowledgeRepo.cloneArtifact( id, artifact.getTitle() + "_copy", newArtifactId() );
    }

    @Override
    public String openArtifact( String id ) {
        System.out.println( "Core opens artifact " + id );
        if ( artifacts.containsKey( id ) ) {
            return id;
        }

        try {
            InputStream in = knowledgeRepo.loadArtifact( id );
            byte[] data = new byte[ in.available() ];
            in.read( data );

            System.out.println( "Found bytes " + data.length );
            System.out.println( "Found content " + new String( data ) );

            HeDKnowledgeDocument knowledgeDocument = new ModelManagerOwlAPIHermit().loadRootThingFromOntologyStream( data );
            HeDArtifactData artifact = new HeDArtifactData( knowledgeDocument, data );

            currentArtifactId = artifact.getArtifactId();

            artifacts.put( currentArtifactId, artifact );
            in.close();
            System.out.println( "Opened artifact with title " + knowledgeDocument.getTitle() );
            return id;
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
        return null;
    }

    @Override
    public String snapshotArtifact( String id ) {
        return knowledgeRepo.snapshotArtifact( id );
    }

    @Override
    public String saveArtifact( String id ) {
        HeDArtifactData artifactData = getArtifactData( id );
        return knowledgeRepo.saveArtifact( id, new ByteArrayInputStream( artifactData.refreshOwlData() ) );
    }

    @Override
    public byte[] exportArtifact( String id, String format ) {
        HeDKnowledgeDocument dok = getArtifact( id );

        return HeDExporterFactory.getExporter( format ).export( dok );
    }

    @Override
    public String closeArtifact() {
        String lastId = currentArtifactId;
        this.artifacts.remove( lastId );
        this.currentArtifactId = null;
        return lastId;
    }

    @Override
    public String deleteArtifact( String id ) {
        this.artifacts.remove( id );
        this.currentArtifactId = null;
        return knowledgeRepo.deleteArtifact( id );
    }






    /**************************************************************************************************************************/
    /* EXPRESSIONS */
    /**************************************************************************************************************************/



    @Override
    public Map<String,String> getNamedExpressions() {
        return getCurrentArtifact().getNamedExpressions();
    }

    @Override
    public byte[] getNamedExpression( String exprId ) {
        return getCurrentArtifact().getNamedExpression( exprId ).getDoxBytes();
    }

    @Override
    public boolean deleteNamedExpression( String exprId ) {
        System.out.println( "Deleting .. " + exprId );
        boolean hasExpr = getCurrentArtifact().getNamedExpressions().containsKey( exprId );
        if ( hasExpr ) {
            System.out.println( "Deleted .. " + exprId );
            getCurrentArtifact().getNamedExpressions().remove( exprId );
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String cloneNamedExpression( String exprId ) {
        HeDNamedExpression expr = getCurrentArtifact().getNamedExpression( exprId );
        String id = UUID.randomUUID().toString();
        getCurrentArtifact().updateNamedExpression( id, expr.getName(), expr.getDoxBytes() );
        return id;
    }

    @Override
    public boolean updateNamedExpression( String exprId, String exprName, byte[] doxBytes ) {
        Document dox = null;
        try {
            DocumentBuilder doxBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doxBuilder.parse( new ByteArrayInputStream( doxBytes ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }

        return getCurrentArtifact().updateNamedExpression( exprId, exprName, doxBytes );
    }




    /**************************************************************************************************************************/
    /* DOMAIN MODEL */
    /**************************************************************************************************************************/


    @Override
    public Map<String, String> getDomainClasses() {
        return DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomKlasses();
    }

    @Override
    public Map<String, String> getDomainProperties() {
        Map<String,String> allProps = new HashMap<>(  );
        for ( Map<String,String> props : DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomProptis().values() ) {
            allProps.putAll( props );
        }
        return allProps;
    }

    @Override
    public Map<String, String> getDomainProperties( String klassId ) {
        // TODO improve when multiple models are supported
        if ( ! klassId.startsWith( DOMAIN_NS ) ) {
            klassId = DOMAIN_NS + klassId;
        }
        return DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomProptis().get( klassId );
    }

    @Override
    public String getDomainClassHierarchyDescription() {
        return DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomainHierarchy();
    }


/**************************************************************************************************************************/
    /* TEMPLATES */
    /**************************************************************************************************************************/



    private OWLOntology templates;

    private void loadTemplateOntology() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        try {
            templates = manager.createOntology( IRI.create( "http://asu.edu/sharpc2b/templates/data" ) );
            manager = templates.getOWLOntologyManager();
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/skos-core.owl" ) );
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/skos-ext.owl" ) );
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/expr-core.owl" ) );
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/sharp_operators.ofn" ) );
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/domain_models/domain-vmr.ofn" ) );
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/templates/template_schema.owl" ) );
            File dataDir = new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/templates/data" );
            for ( File tempFile : dataDir.listFiles( new FilenameFilter() {
                @Override
                public boolean accept( File dir, String name ) {
                    return name.endsWith( ".owl" );
                }
            }) ) {
                OWLOntologyManager subManager = OWLManager.createOWLOntologyManager();
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/skos-core.owl" ) );
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/skos-ext.owl" ) );
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/expr-core.owl" ) );
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/editor_models/sharp_operators.ofn" ) );
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/domain_models/domain-vmr.ofn" ) );
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/templates/template_schema.owl" ) );
                OWLOntology temp = subManager.loadOntologyFromOntologyDocument( tempFile );
                manager.addAxioms( templates, temp.getAxioms() );
            }

        } catch ( OWLOntologyCreationException e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Override
    public Set<String> getTemplateIds( String category ) {
        System.out.println( "Called core getTemplateIds with " + category );
        if ( templates == null ) {
            loadTemplateOntology();
        }
        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        Set<OWLNamedIndividual> individuals = templates.getIndividualsInSignature( true );
        Set<String> individualIds = new HashSet<String>();
        for ( OWLNamedIndividual ind : individuals ) {
            if ( ind.getTypes( templates.getImportsClosure() ).contains( odf.getOWLClass( templateIRI( "Template" ) ) ) ) {
                Set<OWLLiteral> cats = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( "category" ) ), templates );
                if ( category != null && ! category.isEmpty() ) {
                    if ( cats.contains( odf.getOWLLiteral( category ) ) ) {
                        individualIds.add( ind.getIRI().toString() );
                    }
                } else {
                    individualIds.add( ind.getIRI().toString() );
                }
            }
        }
        System.out.println( "Retrieved individual ids " + individualIds );
        return individualIds;
    }

    private IRI templateIRI( String s ) {
        return IRI.create( "http://asu.edu/sharpc2b/templates#" + s );
    }

    private IRI templateDataIRI( String s ) {
        return IRI.create( "http://asu.edu/sharpc2b/templates/data#" + s );
    }


    @Override
    public Map<String, Object> getTemplateInfo( String templateId ) {
        if ( templates == null ) {
            loadTemplateOntology();
        }
        System.out.println( "Requesting info about " + templateId );
        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        Map<String,Object> info = new HashMap<String,Object>();

        OWLNamedIndividual ind = odf.getOWLNamedIndividual( IRI.create( templateId ) );

        info.put( "templateId", templateId );
        info.put( "category", getDataPropertyValue( ind, "category", odf ) );
        info.put( "name", getDataPropertyValue( ind, "name", odf ) );
        info.put( "group", getDataPropertyValue( ind, "group", odf ) );
        info.put( "description", getDataPropertyValue( ind, "description", odf ) );
        info.put( "example", getDataPropertyValue( ind, "example", odf ) );

        List<String> params = getObjectPropertyValues( ind, "hasParameter", odf );
        info.put( "parameterIds", params );

        LinkedHashMap<String,Map<String,Object>> paramData = new LinkedHashMap<String,Map<String,Object>>();
        for ( String pid : params ) {
            Map<String,Object> details = new HashMap<String,Object>();
            OWLNamedIndividual param = odf.getOWLNamedIndividual( IRI.create( pid ) );

            details.put( "key", pid );
            String name = getDataPropertyValue( param, "name", odf );
            details.put( "name", name );
            details.put( "label", name );
            details.put( "description", getDataPropertyValue( param, "description", odf ) );
            details.put( "typeName", getDataPropertyValue( param, "typeName", odf ) );
            details.put( "expressions", new ArrayList( getNamedExpressions().keySet() ) );

            paramData.put( pid, details );
        }
        info.put( "parameterData", paramData );

        System.out.println( "Retrieved info " + info );
        return info;
    }

    private String getDataPropertyValue( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLLiteral> values = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            return values.iterator().next().getLiteral();
        }
    }

    private List<String> getDataPropertyValues( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLLiteral> values = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            ArrayList<String> literals = new ArrayList<String>( values.size() );
            for ( OWLLiteral lit : values ) {
                literals.add( lit.getLiteral() );
            }
            return literals;
        }
    }

    private List<String> getObjectPropertyValues( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLIndividual> values = ind.getObjectPropertyValues( odf.getOWLObjectProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            ArrayList<String> targets = new ArrayList<String>( values.size() );
            for ( OWLIndividual tgt : values ) {
                targets.add( tgt.toStringID() );
            }
            return targets;
        }
    }


    public String instantiateTemplate( String tId, String name, Map<String,Map<String,Object>> parameterValues ) {
        System.out.println( "Core init " + tId + " with name " + name + " using " + parameterValues );
        if ( templates == null ) {
            loadTemplateOntology();
        }

        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        OWLNamedIndividual root = odf.getOWLNamedIndividual( templateDataIRI( tId ) );

        List<String> exprRoots = getObjectPropertyValues( root, "hasIncarnation", odf );
        for ( String exprName : exprRoots ) {
            String expression = buildExpression( exprName, odf );
            this.getCurrentArtifact().updateNamedExpression( exprName, exprName, expression.getBytes() );
        }

        return null;
    }

    private String buildExpression( String exprName, OWLDataFactory odf ) {
        try {
            OWLNamedIndividual root = odf.getOWLNamedIndividual( IRI.create( exprName ) );
            Document dox = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElem = dox.createElement( "xml" );
            dox.appendChild( rootElem );
            visitNode( root, rootElem, dox, odf );

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty( OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(dox);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            System.out.println(xmlString);

            return "";
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void visitNode( OWLIndividual root, Element rootElem, Document dox, OWLDataFactory odf ) {
        OWLClassExpression type = root.getTypes( templates ).iterator().next();
        Element block = dox.createElement( "block" );
            block.setAttribute( "type", type.asOWLClass().toStringID() );
            visitLinks( root, rootElem, dox, odf );
        rootElem.appendChild( block );
    }

    private void visitLinks( OWLIndividual root, Element rootElem, Document dox, OWLDataFactory odf ) {
        //To change body of created methods use File | Settings | File Templates.
    }


}
