package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.metadata.VersionedIdentifier;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.prr.ComputerExecutableRule;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.ProductionRule;
import edu.asu.sharpc2b.prr.RuleVariable;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeDArtifactData {

    private HeDKnowledgeDocument knowledgeDocument;
    private byte[] owlData;

    private Map<String,HeDNamedExpression> blocklyExpressions = new HashMap<String,HeDNamedExpression>();


    public HeDArtifactData( HeDKnowledgeDocument dok, byte[] owlData ) {
        this.knowledgeDocument = dok;
        this.owlData = owlData;

        if ( dok.getArtifactId().isEmpty() && ! dok.getArtifactVersion().isEmpty() ) {
            // move the id up in a more convenient position
            VersionedIdentifier vid = dok.getArtifactVersion().get( 0 );
            dok.getArtifactId().addAll( vid.getArtifactId() );
        }

        cacheBlocklyExpressions( dok );
    }

    public HeDArtifactData() {
        String artifactId = EditorCoreImpl.newArtifactId();
        String title =  "HeD Artifact " + artifactId.substring( artifactId.lastIndexOf( "/" ) + 1 );

        knowledgeDocument = new HeDKnowledgeDocumentImpl();
        ((HeDKnowledgeDocumentImpl) knowledgeDocument).setDyEntryId( artifactId + "#KnowledgeDocument" );
        knowledgeDocument.addArtifactId( artifactId );
        knowledgeDocument.addIdentifier( artifactId );
        knowledgeDocument.addTitle( title );

        knowledgeDocument.addKeyTerm( new ConceptCodeImpl() );

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






    //    private void initDemoExpressions() {
//        String req1 = "<xml><block type=\"logic_root\" inline=\"false\" deletable=\"false\" movable=\"false\" x=\"0\" y=\"0\"><field name=\"NAME\">request</field><value name=\"NAME\"><block type=\"http://asu.edu/sharpc2b/ops#ClinicalRequestExpression\" inline=\"false\"><value name=\"ARG_0\"><block type=\"ObservationProposal\"></block></value><value name=\"ARG_2\"><block type=\"http://asu.edu/sharpc2b/ops#DomainPropertyExpression\" inline=\"false\"><field name=\"DomainProperty\">http://asu.edu/sharpc2b/vmr-clean-A#substanceCode</field></block></value><value name=\"ARG_3\"><block type=\"http://asu.edu/sharpc2b/ops-set#ListExpression\" inline=\"false\"><list_mutation items=\"2\"></list_mutation></block></value><value name=\"ARG_5\"><block type=\"xsd:boolean\"><field name=\"VALUE\">FALSE</field></block></value><value name=\"ARG_6\"><block type=\"xsd:boolean\"><field name=\"VALUE\">TRUE</field></block></value></block></value></block></xml>";
//        updateNamedExpression( "HemoglobinA1cResultsFromLast12Months", "HemoglobinA1cResultsFromLast12Months", req1.getBytes() );
//    }





    public void cacheBlocklyExpressions( HeDKnowledgeDocument dok ) {
        for ( ComputerExecutableRule rule : dok.getContains() ) {
            if ( rule instanceof ProductionRule ) {
                for ( RuleVariable expr : ( (ProductionRule) rule ).getProductionRuleBoundRuleVariable() ) {
                    String name = expr.getName().get( 0 );

                    if ( ! expr.getVariableFilterExpression().isEmpty() ) {
                        Expression prrExpr = expr.getVariableFilterExpression().get( 0 );
                        if ( ! prrExpr.getBodyExpression().isEmpty() ) {
                            SharpExpression sharpExpression = prrExpr.getBodyExpression().get( 0 );

                            blocklyExpressions.put( name, new HeDNamedExpression( prrExpr.getRdfId().toString(), name, BlocklyFactory.fromExpression( name, sharpExpression ) ) );
                        }
                    }

                }
            }
        }
    }

    public Map<String,String> getNamedExpressions() {
        HashMap<String,String> expressions = new HashMap<String,String>( blocklyExpressions.size() );
        for ( String exprId : blocklyExpressions.keySet() ) {
            expressions.put( exprId, blocklyExpressions.get( exprId ).getName() );
        }
        return expressions;
    }

    public HeDNamedExpression getNamedExpression( String exprId ) {
        return blocklyExpressions.get( exprId );
    }

    public boolean updateNamedExpression( String exprId, String name, byte[] doxBytes ) {
        if ( ! blocklyExpressions.containsKey( exprId ) ) {
            blocklyExpressions.put( exprId, new HeDNamedExpression( exprId, name, doxBytes ) );
        } else {
            HeDNamedExpression expr = blocklyExpressions.get( exprId );
            expr.setName( name );
            expr.setDoxBytes( doxBytes );
        }
        return true;
    }





}
