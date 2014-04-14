package edu.asu.sharpc2b.hed.impl;

import com.clarkparsia.empire.SupportsRdfId;
import edu.asu.sharpc2b.metadata.VersionedIdentifier;
import edu.asu.sharpc2b.ops.BooleanExpression;
import edu.asu.sharpc2b.ops.IteratorExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops_set.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops_set.OrExpression;
import edu.asu.sharpc2b.ops_set.OrExpressionImpl;
import edu.asu.sharpc2b.ops_set.PeriodLiteralExpression;
import edu.asu.sharpc2b.prr.ComputerExecutableRule;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.ExpressionImpl;
import edu.asu.sharpc2b.prr.ProductionRule;
import edu.asu.sharpc2b.prr.ProductionRuleImpl;
import edu.asu.sharpc2b.prr.RuleCondition;
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
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import riotcmd.trig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeDArtifactData {

    private HeDKnowledgeDocument knowledgeDocument;
    private byte[] owlData;

    private Map<String,HeDNamedExpression> blocklyExpressions = new HashMap<String,HeDNamedExpression>();
    private HeDNamedExpression logicExpression;
    private HeDNamedExpression triggerExpression;
    private Map<String, String> usedDomainClasses = new HashMap<String,String>();
    private Map<String, String> domainClasses;
    private Map<String, Map<String, String>> domainProperties;




    public HeDArtifactData( HeDKnowledgeDocument dok, byte[] owlData, Map<String, String> domainClasses, Map<String, Map<String, String>> domainProperties ) {
        this.knowledgeDocument = dok;
        this.owlData = owlData;

        if ( dok.getArtifactId().isEmpty() && ! dok.getArtifactVersion().isEmpty() ) {
            // move the id up in a more convenient position
            VersionedIdentifier vid = dok.getArtifactVersion().get( 0 );
            dok.getArtifactId().addAll( vid.getArtifactId() );
        }

        this.domainClasses = Collections.unmodifiableMap( domainClasses );
        this.domainProperties = Collections.unmodifiableMap( domainProperties );

        cacheBlocklyExpressions( dok, domainClasses, domainProperties );
        cacheLogicExpression( dok, domainClasses, domainProperties );
        cacheTriggers( dok, domainClasses, domainProperties );
    }

    public HeDArtifactData() {
        String artifactId = EditorCoreImpl.newArtifactId();
        String title =  "HeD Artifact " + artifactId.substring( artifactId.lastIndexOf( "/" ) + 1 );

        knowledgeDocument = new HeDKnowledgeDocumentImpl();
        ((HeDKnowledgeDocumentImpl) knowledgeDocument).setDyEntryId( artifactId + "#KnowledgeDocument" );
        knowledgeDocument.addArtifactId( artifactId );
        knowledgeDocument.addIdentifier( artifactId );
        knowledgeDocument.addTitle( title );
        knowledgeDocument.addStatus( "Draft" );

        knowledgeDocument.addKeyTerm( new ConceptCodeImpl() );

        ProductionRule innerRule = new ProductionRuleImpl();
        knowledgeDocument.addContains( innerRule );

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

    public void cacheBlocklyExpressions( HeDKnowledgeDocument dok, Map<String, String> domainClasses, Map<String, Map<String, String>> domainProperties ) {
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
        if ( "logic".equalsIgnoreCase( returnType ) ) {
            return heDNamedExpression.getExpression() instanceof BooleanExpression;
        } else if ( "Request".equalsIgnoreCase( returnType ) ) {
            return heDNamedExpression.getExpression() instanceof IteratorExpression || heDNamedExpression.getExpression() instanceof ClinicalRequestExpression;
        }
        return true;
    }

    public HeDNamedExpression getNamedExpression( String exprId ) {
        return blocklyExpressions.get( exprId );
    }

    public String updateNamedExpression( String exprId, String name, byte[] doxBytes ) {
        HeDNamedExpression namedExpression;
        if ( ! blocklyExpressions.containsKey( exprId ) ) {
            namedExpression = createVariableFromBlocks( exprId, name, doxBytes );
            blocklyExpressions.put( exprId, namedExpression  );
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
        boolean found = replaceReferences( oldName, newName, logicExpression.getExpression() );
            if ( found ) {
                logicExpression.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( logicExpression.getName(), logicExpression.getExpression(), BlocklyFactory.ExpressionRootType.CONDITION )
                );
            }
        found = replaceReferences( oldName, newName, triggerExpression.getExpression() );
            if ( found ) {
                triggerExpression.setDoxBytes(
                        new BlocklyFactory( domainClasses, domainProperties ).fromExpression( triggerExpression.getName(), triggerExpression.getExpression(), BlocklyFactory.ExpressionRootType.TRIGGER )
                );
            }
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
                                SharpExpression rebuiltExpression = new ExpressionFactory().parseBlockly( namedExpression.getDoxBytes(), BlocklyFactory.ExpressionRootType.EXPRESSION );

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

    private HeDNamedExpression createVariableFromBlocks( String exprId, String name, byte[] doxBytes ) {
        // new expression
        RuleVariable newVar = new RuleVariableImpl();
        newVar.addName( name );

        Expression shExp = new ExpressionInSHARPImpl();
        SharpExpression sharpExpression = new ExpressionFactory().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.EXPRESSION );
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
        SharpExpression logic = new ExpressionFactory().parseBlockly( doxBytes, BlocklyFactory.ExpressionRootType.CONDITION );

        for ( ComputerExecutableRule rule : knowledgeDocument.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                RuleCondition premise = ( (ProductionRule) rule ).getProductionRuleCondition().get( 0 );
                Expression prrExpr = premise.getConditionRepresentation().get( 0 );
                if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                    prrExpr.getBodyExpression().clear();
                    prrExpr.getBodyExpression().add( logic );
                }
            }
        }
        logicExpression.setDoxBytes( doxBytes );
        logicExpression.setExpression( logic );
        return doxBytes;
    }

    public void cacheLogicExpression( HeDKnowledgeDocument dok, Map<String, String> domainClasses, Map<String, Map<String, String>> domainProperties ) {
        String name = "$$$_LOGIC_PREMISE";
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                RuleCondition premise = ( (ProductionRule) rule ).getProductionRuleCondition().get( 0 );
                Expression prrExpr = premise.getConditionRepresentation().get( 0 );
                if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                    SharpExpression sharpExpression = prrExpr.getBodyExpression().get( 0 );
                    BlocklyFactory factory = new BlocklyFactory( domainClasses, domainProperties );
                    logicExpression = new HeDNamedExpression(
                            idFromName( name ),
                            name,
                            sharpExpression, factory.fromExpression( name, sharpExpression, BlocklyFactory.ExpressionRootType.CONDITION ) );
                }
            }
        }
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

    public void cacheTriggers( HeDKnowledgeDocument dok, Map<String, String> domainClasses, Map<String, Map<String, String>> domainProperties ) {
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




    public static String idFromName( String id ) {
        return id;
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

}
