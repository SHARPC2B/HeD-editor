package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Expression;
import models.ModelHome;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static controllers.SharpController.setHeaderCORS;

public class ExpressionActions extends Controller {


    public static Result getExpressions() {
        return getExpressions( null );
    }

    public static Result getExpressions(String type) {

        List<Expression> expressions = retrieveExpressions(type);

        JsonNode jsonOut = Json.toJson( expressions );

        System.out.println( "Expressions " );
        System.out.println( jsonOut );

        setHeaderCORS();

        return ok( jsonOut );
    }

    public static Result getExpression( String exprId ) {

        byte[] namedExpression = ModelHome.getNamedExpression( exprId );

        setHeaderCORS();

        return ok( namedExpression );
    }

    public static Result deleteExpression( String exprId ) {
        System.out.println(" Trying to delete exprid " + exprId );
        ModelHome.deleteNamedExpression( exprId );

        List<Expression> expressions = retrieveExpressions( null );

        JsonNode jsonOut = Json.toJson( expressions );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result cloneExpression( String exprId ) {
        String clonedExpr = ModelHome.cloneNamedExpression( exprId );

        setHeaderCORS();
        return ok( clonedExpr );
    }


    public static Result updateExpression(String expressionIRI, String exprName) {
        System.out.println( "Update expression request for  " + expressionIRI );

        final Http.Request request = request();
        Http.RequestBody body = request.body();


        String exprId = ModelHome.updateNamedExpression( expressionIRI, exprName, body.asRaw().asBytes() );
        System.out.println( new String( body.asRaw().asBytes() ) );
        setHeaderCORS();

        return ok(exprId);
    }

    private static List<Expression> retrieveExpressions( String type ) {
        Map<String,String> namedExpressions = ModelHome.getNamedExpressions( type );

        List<Expression> expressions = new ArrayList<Expression>();
        List<String> ids = new ArrayList( namedExpressions.keySet() );
        Collections.sort( ids );
        for ( String exprIRI : ids ) {
            Expression expr = new Expression( exprIRI, namedExpressions.get( exprIRI ) );
            expressions.add( expr );
        }
        return expressions;
    }


    public static Result getConditionExpression() {
        byte[] logic = ModelHome.getConditionExpression();
        setHeaderCORS();
        return ok( logic );
    }

    public static Result updateConditionExpression() {
        final Http.Request request = request();
        Http.RequestBody body = request.body();

        byte[] logic = ModelHome.setConditionExpression( body.asRaw().asBytes() );

        setHeaderCORS();
        return ok( logic );
    }

    public static Result getTriggers() {
        byte[] triggers = ModelHome.getTriggers();
        System.out.println( new String( triggers ) );
        setHeaderCORS();
        return ok( triggers );
    }

    public static Result updateTriggers() {
        final Http.Request request = request();
        Http.RequestBody body = request.body();

        byte[] triggers = ModelHome.setTriggers( body.asRaw().asBytes() );

        setHeaderCORS();
        return ok( triggers );
    }

    public static Result getActions() {
        byte[] triggers = ModelHome.getActions();
        System.out.println( new String( triggers ) );
        setHeaderCORS();
        return ok( triggers );
    }

    public static Result updateActions() {
        final Http.Request request = request();
        Http.RequestBody body = request.body();

        byte[] triggers = ModelHome.setActions( body.asRaw().asBytes() );

        setHeaderCORS();
        return ok( triggers );
    }
}
