package edu.asu.sharpc2b.hed.api;


import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

public interface ExpressionSource {

    public Map<String,String> getNamedExpressions();

    public byte[] getNamedExpression( String exprId );

    public boolean deleteNamedExpression( String exprId );

    public String cloneNamedExpression( String exprId );

    public boolean updateNamedExpression( String exprId, String exprName, byte[] doxBytes );

}
