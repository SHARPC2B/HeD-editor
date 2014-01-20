package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeDArtifactData {

    private HeDKnowledgeDocument knowledgeDocument;


    public HeDArtifactData( HeDKnowledgeDocument dok ) {
        knowledgeDocument = dok;
        if ( knowledgeDocument.getArtifactId().isEmpty() ) {
            knowledgeDocument.addArtifactId( knowledgeDocument.getArtifactVersion().get( 0 ).getArtifactId().get( 0 ) );
        }

        initDemoExpressions();
    }


    public HeDArtifactData() {
        String uuid = UUID.randomUUID().toString();
        String artifactId = "urn:asu.bmi.edu:Rule_" + System.identityHashCode( uuid );
        String title =  "HeD Artifact " + System.identityHashCode( uuid );

        knowledgeDocument = new HeDKnowledgeDocumentImpl();
        knowledgeDocument.addArtifactId( artifactId );
        knowledgeDocument.addIdentifier( artifactId );
        knowledgeDocument.addTitle( title );

    }


    public String getArtifactId() {
        return knowledgeDocument.getArtifactId().get( 0 );
    }

    public String getTitle() {
        return knowledgeDocument.getTitle().get( 0 );
    }








    private Map<String,HeDNamedExpression> blocklyExpressions = new HashMap<String,HeDNamedExpression>();


    private void initDemoExpressions() {
        String req1 = "<xml><block type=\"logic_root\" inline=\"false\" deletable=\"false\" movable=\"false\" x=\"0\" y=\"0\"><field name=\"NAME\">request</field><value name=\"NAME\"><block type=\"http://asu.edu/sharpc2b/ops#ClinicalRequestExpression\" inline=\"false\"><value name=\"ARG_0\"><block type=\"ObservationProposal\"></block></value><value name=\"ARG_2\"><block type=\"http://asu.edu/sharpc2b/ops#DomainPropertyExpression\" inline=\"false\"><field name=\"DomainProperty\">http://asu.edu/sharpc2b/vmr-clean-A#substanceCode</field></block></value><value name=\"ARG_3\"><block type=\"http://asu.edu/sharpc2b/ops-set#ListExpression\" inline=\"false\"><list_mutation items=\"2\"></list_mutation></block></value><value name=\"ARG_5\"><block type=\"xsd:boolean\"><field name=\"VALUE\">FALSE</field></block></value><value name=\"ARG_6\"><block type=\"xsd:boolean\"><field name=\"VALUE\">TRUE</field></block></value></block></value></block></xml>";
        updateNamedExpression( "HemoglobinA1cResultsFromLast12Months", "HemoglobinA1cResultsFromLast12Months", req1.getBytes() );
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
