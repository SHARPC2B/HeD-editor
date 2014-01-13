package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocumentImpl;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeDArtifactData {

    private HeDKnowledgeDocument knowledgeDocument;

    public HeDArtifactData() {
        String uuid = UUID.randomUUID().toString();
        String artifactId = "http://asu.bmi.edu/Rule_" + System.identityHashCode( uuid );
        String title =  "HeD Artifact " + System.identityHashCode( uuid );

        knowledgeDocument = new HeDKnowledgeDocumentImpl();
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
