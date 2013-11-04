package edu.asu.sharpc2b.hed.impl;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class HeDArtifactData {

    private String artifactId;

    private Map<String,HeDNamedExpression> blocklyExpressions = new HashMap<String,HeDNamedExpression>();


    public HeDArtifactData( String artifactId ) {
        this.artifactId = artifactId;
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
