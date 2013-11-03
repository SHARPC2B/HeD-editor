package edu.asu.sharpc2b.hed.api;


import java.util.List;
import java.util.Map;

public interface ExpressionSource {

    public Map<String,String> getExpressions();

    public String getExpression( String exprId );

}
