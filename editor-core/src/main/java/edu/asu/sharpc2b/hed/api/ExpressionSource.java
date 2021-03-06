package edu.asu.sharpc2b.hed.api;


import org.w3c.dom.Document;

import java.util.List;
import java.util.Map;

public interface ExpressionSource {

    public Map<String,String> getNamedExpressions( String returnType );

    public byte[] getNamedExpression( String exprId );

    public boolean deleteNamedExpression( String exprId );

    public String cloneNamedExpression( String exprId );

    public String updateNamedExpression( String exprId, String exprName, byte[] doxBytes );

    public byte[] getLogicExpression();

    public byte[] updateLogicExpression( byte[] conditionDoxBytes );

    public byte[] getTriggers();

    public byte[] updateTriggers( byte[] triggerDoxBytes );

    public byte[] getActions();

    public byte[] updateActions( byte[] triggerDoxBytes );

}
