package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.api.ExpressionSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EditorCoreImpl implements EditorCore {

    private ConcurrentMap<String,Object> rules;


    private static EditorCoreImpl instance = new EditorCoreImpl();

    public static EditorCore getInstance() {
        return instance;
    }

    protected EditorCoreImpl() {
        rules = new ConcurrentHashMap<String,Object>();
    }







    @Override
    public Map<String,String> getExpressions() {
        Map<String,String> map = new LinkedHashMap<String,String>();

            map.put( "http://expr1", "Expression #1" );
            map.put( "http://expr2", "Expression #2" );

        return map;
    }

    @Override
    public String getExpression( String exprId ) {
        return "foo";
    }




    @Override
    public String createNew() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String open( String artifactId ) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean save( String artifactId ) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete( String artifactId ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
