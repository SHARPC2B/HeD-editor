package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.TemplateStoreImpl;
import edu.asu.sharpc2b.hed.ArtifactRepository;
import edu.asu.sharpc2b.hed.ArtifactRepositoryFactory;
import edu.asu.sharpc2b.hed.api.ArtifactStore;
import edu.asu.sharpc2b.hed.api.DomainModel;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.ops.ScalarExpression;
import edu.asu.sharpc2b.ops_set.IndividualFactory;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import edu.asu.sharpc2b.templates.Parameter;
import edu.asu.sharpc2b.templates.Template;
import edu.asu.sharpc2b.transform.HeD2OwlDumper;
import edu.asu.sharpc2b.transform.HeDExporterFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EditorCoreImpl implements EditorCore, DomainModel, ArtifactStore {

    private static final String DOMAIN_MODEL_PATH = "ontologies/domain_models/domain-vmr.ofn";
    private static final String DOMAIN_NS = "urn:hl7-org:vmr:r2#";


    private ConcurrentMap<String,HeDArtifactData> artifacts;

    private String currentArtifactId;

    private ArtifactRepository knowledgeRepo = ArtifactRepositoryFactory.getRepository( "FILE" );
    private TemplateStoreImpl templateStore;

    private static EditorCoreImpl instance = new EditorCoreImpl();

    public static EditorCore getInstance() {
        return instance;
    }



    protected EditorCoreImpl() {
        artifacts = new ConcurrentHashMap<String,HeDArtifactData>();
        templateStore = TemplateStoreImpl.getInstance( DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ) );
    }



    public static String newArtifactId() {
        String uuid = UUID.randomUUID().toString();
        return "asu.bmi.edu_" + System.identityHashCode( uuid );
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
        if ( id == null || "undefined".equals( id ) ) {
            return null;
        }
        HeDArtifactData data = getArtifactData( id );
        if ( data != null ) {
            return getArtifactData( id ).getKnowledgeDocument();
        } else {
            return null;
        }
    }

    public HeDArtifactData getCurrentArtifactData() {
        if ( currentArtifactId == null ) {
            return null;
        }
        return getArtifactData( currentArtifactId );
    }

    public void updateBasicInfo( String ruleId, String name, String description, String status, String type ) {
        HeDArtifactData data = getArtifactData( ruleId );
        if ( data == null ) {
            return;
        }
        HeDKnowledgeDocument dok = data.getKnowledgeDocument();

        dok.getTitle().clear();
        dok.addTitle( name );

        dok.getDescription().clear();
        dok.addDescription( description );

        dok.getStatus().clear();
        dok.addStatus( status );
    }

    public HeDArtifactData getArtifactData( String id ) {
        if ( artifacts.containsKey( id ) ) {
            return artifacts.get( id );
        } else {
            if ( currentArtifactId == null ) {
                return null;
            }
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

            System.out.println( "Preparing artifactData " );
            long now = System.currentTimeMillis();
            HeDArtifactData artifact = new HeDArtifactData( knowledgeDocument,
                                                            owlBytes,
                                                            getDomainClasses(),
                                                            DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomProptis() );
            System.out.println( "Analysis took " + ( System.currentTimeMillis() - now ) );

            currentArtifactId = artifact.getArtifactId();

            artifacts.put( currentArtifactId, artifact );

            System.out.println( "Persisting artifact..." );
            now = System.currentTimeMillis();
            knowledgeRepo.createArtifact( currentArtifactId, artifact.getTitle(), new ByteArrayInputStream( owlBytes ) );
            System.out.println( "Save took " + ( System.currentTimeMillis() - now ) );

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
        if ( artifacts.containsKey( id ) ) {
            return id;
        }

        try {
            InputStream in = knowledgeRepo.loadArtifact( id );
            byte[] data = new byte[ in.available() ];
            in.read( data );

            HeDKnowledgeDocument knowledgeDocument = new ModelManagerOwlAPIHermit().loadRootThingFromOntologyStream( data );
            HeDArtifactData artifact = new HeDArtifactData( knowledgeDocument,
                                                            data,
                                                            getDomainClasses(),
                                                            DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomProptis() );

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
    /**
     * @param returnType************************************************************************************************************************/



    @Override
    public Map<String,String> getNamedExpressions( String returnType ) {
        return getCurrentArtifact().getNamedExpressions( returnType );
    }

    @Override
    public byte[] getNamedExpression( String exprId ) {
        return getCurrentArtifact().getNamedExpression( exprId ).getDoxBytes();
    }

    @Override
    public boolean deleteNamedExpression( String exprId ) {
        boolean hasExpr = getCurrentArtifact().getNamedExpressions( (String) null ).containsKey( exprId );
        if ( hasExpr ) {
            getCurrentArtifact().deleteExpression( exprId );
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
    public String updateNamedExpression( String exprId, String exprName, byte[] doxBytes ) {
        Document dox = null;
        try {
            DocumentBuilder doxBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doxBuilder.parse( new ByteArrayInputStream( doxBytes ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }

        return getCurrentArtifact().updateNamedExpression( exprId, exprName, doxBytes );
    }

    public byte[] getLogicExpression() {
        HeDNamedExpression logic = getCurrentArtifact().getLogicExpression();
        return logic != null ? logic.getDoxBytes() : BlocklyFactory.emptyRoot( BlocklyFactory.ExpressionRootType.CONDITION );
    }

    public byte[] updateLogicExpression( byte[] doxBytes ) {
        return getCurrentArtifact().updateLogicExpression( doxBytes );
    }

    public byte[] getTriggers() {
        HeDNamedExpression trig = getCurrentArtifact().getTriggers();
        return trig != null ? trig.getDoxBytes() : BlocklyFactory.emptyRoot( BlocklyFactory.ExpressionRootType.TRIGGER );
    }

    public byte[] updateTriggers( byte[] doxBytes ) {
        return getCurrentArtifact().updateTriggers( doxBytes );
    }

    public byte[] getActions() {
        HeDAction action = getCurrentArtifact().getActions();
        return action != null ? action.getDoxBytes() : BlocklyFactory.emptyRoot( BlocklyFactory.ExpressionRootType.ACTION );
    }

    public byte[] updateActions( byte[] doxBytes ) {
        return getCurrentArtifact().updateActions( doxBytes );
    }



    /**************************************************************************************************************************/
    /* DOMAIN MODEL */
    /**************************************************************************************************************************/


    @Override
    public SortedMap<String, String> getDomainClasses() {
        return DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS ).getDomKlasses();
    }

    @Override
    public SortedMap<String, String> getDomainProperties() {
        SortedMap<String,String> allProps = new TreeMap<String,String>(  );
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

    @Override
    public void addUsedDomainClass( String id ) {
        for ( String fqn : getDomainClasses().keySet() ) {
            String simpleName = getDomainClasses().get( fqn );
            if ( simpleName.equals( id ) ) {
                getCurrentArtifactData().addUsedDomainClass( fqn, simpleName );
            }
        }
    }


    /**************************************************************************************************************************/
    /* TEMPLATES */
    /**************************************************************************************************************************/

    @Override
    public Set<String> getTemplateIds( String category ) {
        return templateStore.getTemplateIds( category );
    }

    @Override
    public Template getTemplateInfo( String templateId ) {
        Template template = templateStore.getTemplateInfo( templateId );
        lookupCompatibleExpressionsAndOperations( template );
        return template;
    }

    private void lookupCompatibleExpressionsAndOperations( Template template ) {
        for ( Parameter param : template.getHasParameter() ) {
            if ( param.getTypeName().isEmpty() ) {
                continue;
            }
            String hedParam = param.getTypeName().get( 0 );
            if ( hedParam == null ) {
                continue;
            }
            Class<?> paramType = templateStore.getClassForHeDType( hedParam );

            Map<String,String> exprs = getCurrentArtifact().getNamedExpressions( paramType );
            param.getCompatibleExpression().clear();
            for ( String exprId : exprs.keySet() ) {
                param.addCompatibleExpression( exprId );
            }
            lookupCompatibleOperations( param, paramType );
        }
    }

    private void lookupCompatibleOperations( Parameter param, Class<?> paramType ) {
        //TODO Explore the ontology, using the indivudals, and cache the results!
        param.getCompatibleOperation().clear();
        addOperation( param, "EqualCode" );
        addOperation( param, "InCode" );
        if ( ScalarExpression.class.isAssignableFrom( paramType ) ) {
            addOperation( param, "GreaterCode" );
            addOperation( param, "GreaterOrEqualCode" );
            addOperation( param, "LessCode" );
            addOperation( param, "LessOrEqualCode" );
        }

    }

    private void addOperation( Parameter param, String code ) {
        param.addCompatibleOperation( (( ConceptCode ) IndividualFactory.getNamedIndividuals().get( code )).getCode().get( 0 ) );
    }

    @Override
    public String instantiateTemplate( String templateId, String name, Map<String, Map<String, Object>> parameterValues ) {
        return null;
    }

}
