package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.SupportsRdfId;
import edu.asu.sharpc2b.actions.CancelActionImpl;
import edu.asu.sharpc2b.actions.CollectInformationActionImpl;
import edu.asu.sharpc2b.actions.CompositeAction;
import edu.asu.sharpc2b.actions.CompositeActionImpl;
import edu.asu.sharpc2b.actions.CreateActionImpl;
import edu.asu.sharpc2b.actions.FireEventActionImpl;
import edu.asu.sharpc2b.actions.MessageActionImpl;
import edu.asu.sharpc2b.actions.ModifyActionImpl;
import edu.asu.sharpc2b.actions.SharpAction;
import edu.asu.sharpc2b.actions.SharpActionImpl;
import edu.asu.sharpc2b.metadata.Evidence;
import edu.asu.sharpc2b.metadata.KnowledgeResource;
import edu.asu.sharpc2b.metadata.RightsDeclaration;
import edu.asu.sharpc2b.metadata.VersionedIdentifier;
import edu.asu.sharpc2b.metadata.VersionedIdentifierImpl;
import edu.asu.sharpc2b.ops.BooleanExpression;
import edu.asu.sharpc2b.ops.IteratorExpression;
import edu.asu.sharpc2b.ops.LiteralExpression;
import edu.asu.sharpc2b.ops.ObjectExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.Variable;
import edu.asu.sharpc2b.ops.VariableExpression;
import edu.asu.sharpc2b.ops.VariableExpressionImpl;
import edu.asu.sharpc2b.ops.VariableImpl;
import edu.asu.sharpc2b.ops_set.AddExpression;
import edu.asu.sharpc2b.ops_set.AddExpressionImpl;
import edu.asu.sharpc2b.ops_set.BooleanLiteralExpression;
import edu.asu.sharpc2b.ops_set.BooleanLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpressionImpl;
import edu.asu.sharpc2b.ops_set.EqualExpressionImpl;
import edu.asu.sharpc2b.ops_set.IntegerLiteralExpression;
import edu.asu.sharpc2b.ops_set.IntegerLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.NotEqualExpression;
import edu.asu.sharpc2b.ops_set.NotEqualExpressionImpl;
import edu.asu.sharpc2b.ops_set.OrExpression;
import edu.asu.sharpc2b.ops_set.OrExpressionImpl;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpression;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpressionImpl;
import edu.asu.sharpc2b.ops_set.StringLiteralExpression;
import edu.asu.sharpc2b.ops_set.StringLiteralExpressionImpl;
import edu.asu.sharpc2b.prr.ComputerExecutableRule;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.ExpressionImpl;
import edu.asu.sharpc2b.prr.ProductionRule;
import edu.asu.sharpc2b.prr.ProductionRuleImpl;
import edu.asu.sharpc2b.prr.RuleAction;
import edu.asu.sharpc2b.prr.RuleCondition;
import edu.asu.sharpc2b.prr.RuleConditionImpl;
import edu.asu.sharpc2b.prr.RuleVariable;
import edu.asu.sharpc2b.prr.RuleVariableImpl;
import edu.asu.sharpc2b.prr_sharp.DataRuleTrigger;
import edu.asu.sharpc2b.prr_sharp.DataRuleTriggerImpl;
import edu.asu.sharpc2b.prr_sharp.ExpressionInSHARPImpl;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import edu.asu.sharpc2b.prr_sharp.RuleTrigger;
import edu.asu.sharpc2b.prr_sharp.TemporalRuleTrigger;
import edu.asu.sharpc2b.prr_sharp.TemporalRuleTriggerImpl;
import edu.asu.sharpc2b.sharp.HasInterpretantRange;
import edu.asu.sharpc2b.sharp.RelatedRuleElementRange;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import edu.asu.sharpc2b.templates.CollectTemplate;
import edu.asu.sharpc2b.templates.CreateTemplate;
import edu.asu.sharpc2b.templates.DeclareTemplate;
import edu.asu.sharpc2b.templates.FireEventTemplate;
import edu.asu.sharpc2b.templates.RemoveTemplate;
import edu.asu.sharpc2b.templates.Template;
import edu.asu.sharpc2b.templates.UpdateTemplate;
import org.drools.semantics.Literal;
import org.ontologydesignpatterns.ont.dul.dul.Description;
import org.ontologydesignpatterns.ont.dul.dul.Entity;
import org.ontologydesignpatterns.ont.dul.dul.InformationObject;
import org.ontologydesignpatterns.ont.dul.dul.InformationRealization;
import org.openrdf.model.Graph;
import org.purl.dc.dcam.VocabularyEncodingScheme;
import org.purl.dc.terms.Agent;
import org.purl.dc.terms.AgentClass;
import org.purl.dc.terms.LicenseDocument;
import org.purl.dc.terms.LinguisticSystem;
import org.purl.dc.terms.Location;
import org.purl.dc.terms.LocationPeriodOrJurisdiction;
import org.purl.dc.terms.MediaType;
import org.purl.dc.terms.MediaTypeOrExtent;
import org.purl.dc.terms.PeriodOfTime;
import org.purl.dc.terms.ProvenanceStatement;
import org.purl.dc.terms.RightsStatement;
import org.purl.dc.terms.SizeOrDuration;
import org.purl.dc.terms.Standard;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.w3._2002._07.owl.Thing;
import riotcmd.trig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class HeDArtifactData {

    private HeDKnowledgeDocument knowledgeDocument;
    private byte[] owlData;

    private Map<String,HeDNamedExpression> blocklyExpressions = new HashMap<String,HeDNamedExpression>();
    private HeDNamedExpression logicExpression;
    private HeDNamedExpression triggerExpression;
    private HeDAction actionExpression;
    private Map<String, String> usedDomainClasses = new HashMap<String,String>();
    private SortedMap<String, String> domainClasses;
    private SortedMap<String, SortedMap<String, String>> domainProperties;




    public HeDArtifactData( HeDKnowledgeDocument dok, byte[] owlData, SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domainProperties ) {
        this.knowledgeDocument = dok;
        this.owlData = owlData;

        if ( dok.getArtifactId().isEmpty() && ! dok.getArtifactVersion().isEmpty() ) {
            // move the id up in a more convenient position
            VersionedIdentifier vid = dok.getArtifactVersion().get( 0 );
            dok.getArtifactId().addAll( vid.getArtifactId() );
        }

        this.domainClasses = Collections.unmodifiableSortedMap( domainClasses );
        this.domainProperties = Collections.unmodifiableSortedMap( domainProperties );

        cacheBlocklyExpressions( dok, domainClasses, domainProperties );
        cacheLogicExpression( dok, domainClasses, domainProperties );
        cacheTriggers( dok, domainClasses, domainProperties );
        cacheActions( dok, domainClasses, domainProperties );
    }

    public HeDArtifactData( SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domProptis ) {
        String artifactId = EditorCoreImpl.newArtifactId();
        String title =  "HeD Artifact " + artifactId.substring( artifactId.lastIndexOf( "/" ) + 1 );

        knowledgeDocument = new HeDKnowledgeDocumentImpl();
        ((HeDKnowledgeDocumentImpl) knowledgeDocument).setDyEntryId( artifactId + "#KnowledgeDocument" );

        knowledgeDocument.addArtifactId( artifactId );
        knowledgeDocument.addIdentifier( artifactId );
        VersionedIdentifier vid = new VersionedIdentifierImpl();
        vid.addVersionId( "1.0" );
        vid.addArtifactId( artifactId );
        knowledgeDocument.addArtifactVersion( vid );

        knowledgeDocument.addTitle( title );
        knowledgeDocument.addStatus( "Draft" );

        knowledgeDocument.addKeyTerm( new ConceptCodeImpl() );

        ProductionRule innerRule = new ProductionRuleImpl();
        knowledgeDocument.addContains( innerRule );

        this.domainClasses = domainClasses;
        this.domainProperties = domProptis;

        refreshOwlData();
    }


    public String getArtifactId() {
        return knowledgeDocument.getArtifactId().get( 0 );
    }

    public String getTitle() {
        return knowledgeDocument.getTitle().get( 0 );
    }

    public HeDKnowledgeDocument getKnowledgeDocument() {
        return knowledgeDocument;
    }

    public byte[] getOwlData() {
        return owlData;
    }

    public void setOwlData( byte[] owlData ) {
        this.owlData = owlData;
    }

    public byte[] refreshOwlData() {
        OWLOntology onto = ModelManagerOwlAPIHermit.getInstance().objectGraphToHeDOntology( knowledgeDocument );
        ByteArrayOutputStream baos = new ByteArrayOutputStream( );
        try {
            onto.getOWLOntologyManager().saveOntology( onto, new OWLFunctionalSyntaxOntologyFormat(), baos );
            this.owlData = baos.toByteArray();
            baos.close();
        } catch ( OWLOntologyStorageException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return owlData;
    }


    public Map<String, String> getUsedDomainClasses() {
        return usedDomainClasses;
    }

    public void addUsedDomainClass( String fqn, String name ) {
        this.usedDomainClasses.put( fqn, name );
    }

    public void cacheBlocklyExpressions( HeDKnowledgeDocument dok, SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domainProperties ) {
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                for ( RuleVariable var : ( (ProductionRule) rule ).getProductionRuleBoundRuleVariable() ) {
                    String name = var.getName().get( 0 );

                    if ( ! var.getVariableFilterExpression().isEmpty() ) {
                        Expression prrExpr = var.getVariableFilterExpression().get( 0 );
                        if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                            SharpExpression sharpExpression = prrExpr.getBodyExpression().get( 0 );

                            BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
                            blocklyExpressions.put( idFromName( name ), new HeDNamedExpression(
                                    idFromName( name ),
                                    name,
                                    sharpExpression,
                                    factory.fromExpression( name, sharpExpression, BlocklyFactory.ExpressionRootType.EXPRESSION ) ) );
                            usedDomainClasses.putAll( factory.getRequiredDomainClasses() );
                        }
                    }
                }
            }
        }
    }

    public Map<String,String> getNamedExpressions( String returnType ) {
        HashMap<String,String> expressions = new HashMap<String,String>( blocklyExpressions.size() );
        for ( String exprId : blocklyExpressions.keySet() ) {
            if ( returnType == null || isReturnTypeCompatible( blocklyExpressions.get( exprId ), returnType ) ) {
                expressions.put( exprId, blocklyExpressions.get( exprId ).getName() );
            }
        }
        return expressions;
    }

    private boolean isReturnTypeCompatible( HeDNamedExpression heDNamedExpression, String returnType ) {
        if ( returnType == null ) {
            return true;
        }
        //TODO replace with enum or URI
        if ( "Logic".equalsIgnoreCase( returnType ) ) {
            return heDNamedExpression.getExpression() instanceof BooleanExpression;
        } else if ( "Request".equalsIgnoreCase( returnType ) ) {
            return heDNamedExpression.getExpression() instanceof IteratorExpression || heDNamedExpression.getExpression() instanceof ClinicalRequestExpression;
        } else if ( "Action".equalsIgnoreCase( returnType ) ) {
            return heDNamedExpression.getExpression() instanceof ObjectExpression;
        }
        return true;
    }

    public Map<String,String> getNamedExpressions( Class<?> returnType ) {
        HashMap<String,String> expressions = new HashMap<String,String>( blocklyExpressions.size() );
        for ( String exprId : blocklyExpressions.keySet() ) {
            if ( returnType == null || isReturnTypeCompatible( blocklyExpressions.get( exprId ), returnType ) ) {
                expressions.put( exprId, blocklyExpressions.get( exprId ).getName() );
            }
        }
        return expressions;
    }

    private boolean isReturnTypeCompatible( HeDNamedExpression heDNamedExpression, Class returnType ) {
        if ( returnType == null ) {
            return true;
        }
        return returnType.isInstance( heDNamedExpression.getExpression() );

    }

    public HeDNamedExpression getNamedExpression( String exprId ) {
        return blocklyExpressions.get( exprId );
    }

    public String updateNamedExpression( String exprId, String name, byte[] doxBytes, SharpExpression expression ) {
        HeDNamedExpression namedExpression;
        if ( ! blocklyExpressions.containsKey( exprId ) ) {
            namedExpression = createVariableFromBlocks( idFromName( name ), name, doxBytes, expression );
            blocklyExpressions.put( idFromName( name ), namedExpression  );
        } else {
            namedExpression = blocklyExpressions.get( exprId );
            String candidateId = idFromName( name );

            namedExpression.setName( name );
            namedExpression.setDoxBytes( doxBytes );
            replaceExpression( knowledgeDocument, namedExpression );

            if ( ! exprId.equals( candidateId ) ) {
                namedExpression.setId( candidateId );
                blocklyExpressions.remove( exprId );
                blocklyExpressions.put( candidateId, namedExpression );
                replaceReferences( exprId, name );
            }
        }
        return exprId;
    }

    private void replaceReferences( String oldName, String newName ) {
        for ( HeDNamedExpression expr : blocklyExpressions.values() ) {
            boolean found = replaceReferences( oldName, newName, expr.getExpression() );
            if ( found ) {
                expr.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( expr.getName(), expr.getExpression(), BlocklyFactory.ExpressionRootType.EXPRESSION )
                );
            }
        }
        if ( logicExpression != null ) {
            boolean foundExpr = replaceReferences( oldName, newName, logicExpression.getExpression() );
            if ( foundExpr ) {
                logicExpression.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( logicExpression.getName(), logicExpression.getExpression(), BlocklyFactory.ExpressionRootType.CONDITION )
                );
            }
        }
        if ( triggerExpression != null ) {
            boolean foundTrig = replaceReferences( oldName, newName, triggerExpression.getExpression() );
            if ( foundTrig ) {
                triggerExpression.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( triggerExpression.getName(), triggerExpression.getExpression(), BlocklyFactory.ExpressionRootType.TRIGGER )
                );
            }
        }
        if ( actionExpression != null ) {
            boolean foundAct = replaceReferences( oldName, newName, actionExpression.getAction() );
            if ( foundAct ) {
                actionExpression.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( actionExpression.getName(), actionExpression.getAction(), BlocklyFactory.ExpressionRootType.ACTION )
                );
            }
        }
    }

    private boolean replaceReferences( String oldName, String newName, SharpAction action ) {
        return new ReferenceHunter( action ).replace( oldName, newName );
    }

    private boolean replaceReferences( String oldName, String newName, SharpExpression expression ) {
        return new ReferenceHunter( expression ).replace( oldName, newName );
    }

    private void replaceExpression( HeDKnowledgeDocument dok, HeDNamedExpression namedExpression ) {
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                for ( RuleVariable expr : ( (ProductionRule) rule ).getProductionRuleBoundRuleVariable() ) {
                    String name = expr.getName().get( 0 );

                    if ( ! expr.getVariableFilterExpression().isEmpty() ) {
                        Expression prrExpr = expr.getVariableFilterExpression().get( 0 );
                        if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                            SharpExpression sharpExpression = prrExpr.getBodyExpression().get( 0 );
                            if ( namedExpression.getExpression() == sharpExpression ) {
                                SharpExpression rebuiltExpression = new ExpressionFactory<SharpExpression>().parseBlockly( namedExpression.getDoxBytes(), BlocklyFactory.ExpressionRootType.EXPRESSION );

                                prrExpr.getBodyExpression().clear();
                                expr.getName().clear();

                                expr.addName( namedExpression.getName() );
                                prrExpr.addBodyExpression( rebuiltExpression );
                            }
                        }
                    }
                }
            }
        }
    }


    public void deleteExpression( String exprId ) {
        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                for ( RuleVariable var : ( (ProductionRule) rule ).getProductionRuleBoundRuleVariable() ) {
                    if ( ! var.getVariableFilterExpression().isEmpty() ) {
                        Expression prrExpr = var.getVariableFilterExpression().get( 0 );
                        if ( idFromName( var.getName().get( 0 ) ).equals( exprId ) ) {
                            blocklyExpressions.remove( exprId );
                            ( (ProductionRule) rule ).removeProductionRuleBoundRuleVariable( var );
                            return;
                        }
                    }
                }
            }
        }
    }

    private HeDNamedExpression createVariableFromBlocks( String exprId, String name, byte[] doxBytes, SharpExpression expression ) {
        // new expression
        RuleVariable newVar = new RuleVariableImpl();
        newVar.addName( name );

        Expression shExp = new ExpressionInSHARPImpl();
        SharpExpression sharpExpression = expression != null
                                          ? expression
                                          : new ExpressionFactory<SharpExpression>().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.EXPRESSION );
        shExp.addBodyExpression( sharpExpression );
        newVar.addVariableFilterExpression( shExp );

        exprId = idFromName( newVar.getName().get( 0 ) );
        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                ( (ProductionRule) rule ).addProductionRuleBoundRuleVariable( newVar );
            }
        }
        return new HeDNamedExpression( exprId, name, sharpExpression, doxBytes );
    }

    public HeDNamedExpression getLogicExpression() {
        return logicExpression;
    }

    public byte[] updateLogicExpression( byte[] doxBytes ) {
        SharpExpression logic = new ExpressionFactory<SharpExpression>().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.CONDITION );

        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {

                if ( ( (ProductionRule) rule ).getProductionRuleCondition().isEmpty() ) {
                    RuleCondition cond = new RuleConditionImpl();
                    Expression prrExpr = new ExpressionImpl();
                    cond.addConditionRepresentation( prrExpr );
                    ( (ProductionRule) rule ).addProductionRuleCondition( cond );
                }

                RuleCondition premise = ( (ProductionRule) rule ).getProductionRuleCondition().get( 0 );
                Expression prrExpr = premise.getConditionRepresentation().get( 0 );
                if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                    prrExpr.getBodyExpression().clear();
                }
                if ( logic != null ) {
                    prrExpr.getBodyExpression().add( logic );
                } else {
                    ( (ProductionRule) rule ).getProductionRuleCondition().clear();
                }
            }
        }
        logicExpression.setDoxBytes( doxBytes );
        logicExpression.setExpression( logic );
        return doxBytes;
    }

    public void cacheLogicExpression( HeDKnowledgeDocument dok, SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domainProperties ) {
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule && ! ( (ProductionRule) rule ).getProductionRuleCondition().isEmpty() ) {
                RuleCondition premise = ( (ProductionRule) rule ).getProductionRuleCondition().get( 0 );
                Expression prrExpr = premise.getConditionRepresentation().get( 0 );
                if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                    SharpExpression sharpExpression = prrExpr.getBodyExpression().get( 0 );
                    logicExpression = createLogicExpression( sharpExpression );
                }
            }
        }
    }

    private HeDNamedExpression createLogicExpression( SharpExpression sharpExpression ) {
        String name = "$$$_LOGIC_PREMISE";
        BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
        return new HeDNamedExpression(
                idFromName( name ),
                name,
                sharpExpression,
                factory.fromExpression( name, sharpExpression, BlocklyFactory.ExpressionRootType.CONDITION ) );
    }

    private HeDAction createActionExpression( SharpAction sharpAction ) {
        String name = "$$$_ACTIONS";
        BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
        return new HeDAction(
                idFromName( name ),
                name,
                sharpAction,
                factory.fromExpression( name, sharpAction, BlocklyFactory.ExpressionRootType.ACTION ) );
    }


    public HeDNamedExpression getTriggers() {
        return triggerExpression;
    }

    public byte[] updateTriggers( byte[] doxBytes ) {
        OrExpression triggers = (OrExpression) new ExpressionFactory().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.TRIGGER );

        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                ( (ProductionRule) rule ).getProductionRuleTrigger().clear();
                for ( SharpExpression trigger : triggers.getHasOperand() ) {
                    Expression prr = new ExpressionImpl();
                    prr.addBodyExpression( trigger );
                    RuleTrigger t;
                    if ( trigger instanceof PeriodLiteralExpression ) {
                        t = new TemporalRuleTriggerImpl();
                    } else {
                        t = new DataRuleTriggerImpl();
                    }
                    t.addTriggerExpression( prr );
                    ( (ProductionRule) rule ).addProductionRuleTrigger( t );
                }
            }
        }

        triggerExpression.setDoxBytes( doxBytes );
        triggerExpression.setExpression( triggers );
        return doxBytes;
    }

    public void cacheTriggers( HeDKnowledgeDocument dok, SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domainProperties ) {
        String name = "$$$_TRIGGERS";
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                List<RuleTrigger> triggers = ( (ProductionRule) rule ).getProductionRuleTrigger();
                if ( ! triggers.isEmpty() ) {
                    OrExpression sharpExpression = new OrExpressionImpl();
                    for ( RuleTrigger trigger : triggers ) {
                        Expression prrExpr = trigger.getTriggerExpression().get( 0 );
                        if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                            sharpExpression.addHasOperand( prrExpr.getBodyExpression().get( 0 ) );
                        }
                    }
                    BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
                    triggerExpression = new HeDNamedExpression(
                            idFromName( name ),
                            name,
                            sharpExpression, factory.fromExpression( name, sharpExpression, BlocklyFactory.ExpressionRootType.TRIGGER ) );
                }

            }
        }
    }



    private void cacheActions( HeDKnowledgeDocument dok, SortedMap<String, String> domainClasses, SortedMap<String, SortedMap<String, String>> domainProperties ) {
        String name = "$$$_ACTIONS";
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                List<RuleAction> actions = ( (ProductionRule) rule ).getProductionRuleAction();
                if ( ! actions.isEmpty() ) {
                    SharpAction topAction = (SharpAction) actions.get( 0 );

                    BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
                    actionExpression = new HeDAction(
                            idFromName( name ),
                            name,
                            topAction, factory.fromExpression( name, topAction, BlocklyFactory.ExpressionRootType.ACTION ) );
                }
            }
        }
    }


    public HeDAction getActions() {
        return actionExpression;
    }

    public byte[] updateActions( byte[] doxBytes ) {
        SharpAction action = new ExpressionFactory<SharpAction>().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.ACTION );

        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                ( (ProductionRule) rule ).getProductionRuleAction().clear();
                ( (ProductionRule) rule ).getProductionRuleAction().add( action );
            }
        }

        if ( actionExpression == null ) {
            actionExpression = createActionExpression( new CompositeActionImpl() );
        }

        actionExpression.setDoxBytes( doxBytes );
        actionExpression.setAction( action );
        return doxBytes;
    }



    public static String idFromName( String id ) {
        return id;
    }



    public byte[] instantiateExpressionFromTemplate( String templateId, String name, Template source ) {
        SharpExpression expr = instantiateExpression( source );
        BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
        byte[] blocks = factory.fromExpression( name, expr, BlocklyFactory.ExpressionRootType.EXPRESSION );

        expr.addIncarnationOf( source );

        if ( ! ( expr instanceof LiteralExpression ) ) {
            updateNamedExpression( templateId, name, blocks, expr );
        }

        if ( ! source.getCategory().isEmpty() ) {
            String cat = source.getCategory().get( 0 );
            BlocklyFactory.ExpressionRootType type = BlocklyFactory.ExpressionRootType.valueOf( cat );

            switch ( type ) {
                case CONDITION:
                    if ( logicExpression == null ) {
                        logicExpression = createLogicExpression( null );

                    }
                    return factory.updateRoot( expr,
                                               name,
                                               getLogicExpression().getDoxBytes(),
                                               BlocklyFactory.ExpressionRootType.CONDITION );
                case TRIGGER:
                    if ( triggerExpression == null ) {
                        triggerExpression = new HeDNamedExpression(
                                idFromName( "$$$_TRIGGGERS" ),
                                "$$$_TRIGGGERS",
                                new OrExpressionImpl(),
                                BlocklyFactory.emptyRoot( BlocklyFactory.ExpressionRootType.TRIGGER ) );

                    }

                    SharpExpression trix;
                    if ( expr instanceof LiteralExpression ) {
                        trix = expr;
                    } else {
                        VariableExpression varexp = new VariableExpressionImpl();
                        Variable var = new VariableImpl();
                        var.addName( name );
                        varexp.addReferredVariable( var );
                        trix = varexp;
                    }
                    OrExpression or = new OrExpressionImpl();
                    or.getHasOperand().addAll( ((OrExpression)triggerExpression.getExpression()).getHasOperand() );
                    or.addHasOperand( trix );

                    return new BlocklyFactory( domainClasses, domainProperties ).fromExpression( "$$$_TRIGGGERS", or, BlocklyFactory.ExpressionRootType.TRIGGER );
                case ACTION:
                    if ( actionExpression == null ) {
                        actionExpression = createActionExpression( new CompositeActionImpl() );
                    }

                    if ( expr instanceof BooleanExpression ) {
                        return factory.updateRoot( expr,
                                                   name,
                                                   getActions().getDoxBytes(),
                                                   BlocklyFactory.ExpressionRootType.ACTION );
                    } else {
                        return factory.updateActionRoot( determineActionFromTemplate( source ),
                                                         name,
                                                         getActions().getDoxBytes(),
                                                         BlocklyFactory.ExpressionRootType.ACTION );
                    }

                case EXPRESSION:
                default:
                    throw new IllegalStateException( "Can only instantiate a template for CONDITION, TRIGGER, ACTION, found " + type );
            }

        } else {
            throw new IllegalStateException( "Unable to instantiate a template without a category : CONDITION, TRIGGER, ACTION" );
        }
    }

    private SharpAction determineActionFromTemplate( Template templ ) {
        if ( templ instanceof CreateTemplate ) {
            return new CreateActionImpl();
        } else if ( templ instanceof CollectTemplate ) {
            return new CollectInformationActionImpl();
        } else if ( templ instanceof UpdateTemplate ) {
            return new ModifyActionImpl();
        } else if ( templ instanceof RemoveTemplate ) {
            return new CancelActionImpl();
        } else if ( templ instanceof FireEventTemplate ) {
            return new FireEventActionImpl();
        } else if ( templ instanceof DeclareTemplate ) {
            // TODO Replace! with DeclareAction
            return new MessageActionImpl();
        }
        throw new UnsupportedOperationException( "Unable to determine action for template " + templ.getClass() );
    }

    private SharpExpression instantiateExpression( Template source ) {
        switch ( BlocklyFactory.ExpressionRootType.valueOf( source.getCategory().get( 0 ) ) ) {
            case CONDITION:
                EqualExpressionImpl eq = new EqualExpressionImpl();
                BooleanLiteralExpression boo = new BooleanLiteralExpressionImpl();
                boo.addValue_Boolean( true );
                eq.addFirstOperand( boo );
                eq.addSecondOperand( boo );
                return eq;
            case TRIGGER:
                if ( source.getGroup().contains( "Timed" ) ) {
                    PeriodLiteralExpression period = new PeriodLiteralExpressionImpl();
                    return period;
                } else {
                    ClinicalRequestExpression clix = new ClinicalRequestExpressionImpl();
                    return clix;
                }
            case ACTION:
                switch ( BlocklyFactory.ExpressionRootType.valueOf( source.getCategory().get( 1 ) ) ) {
                    case CONDITION:
                        EqualExpressionImpl eq2 = new EqualExpressionImpl();
                        BooleanLiteralExpression boo2 = new BooleanLiteralExpressionImpl();
                        boo2.addValue_Boolean( true );
                        eq2.addFirstOperand( boo2 );
                        eq2.addSecondOperand( boo2 );
                        return eq2;
                    case ACTION:
                        default:
                            AddExpression neq = new AddExpressionImpl();
                            IntegerLiteralExpression lit = new IntegerLiteralExpressionImpl();
                            lit.addValue( 3 );
                            neq.addFirstOperand( lit );
                            neq.addSecondOperand( lit );
                        return neq;
                }
        }
        return null;
    }

}
