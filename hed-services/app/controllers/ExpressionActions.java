package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Expression;
import models.ModelHome;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controllers.SharpController.setHeaderCORS;

public class ExpressionActions extends Controller {


    public static Result getExpressions() {

        Map<String,String> namedExpressions = ModelHome.getNamedExpressions();

        List<Expression> expressions = new ArrayList<Expression>();
        for ( String exprIRI : namedExpressions.keySet() ) {
            Expression expr = new Expression( exprIRI, namedExpressions.get( exprIRI ) );
            expressions.add( expr );
        }

        JsonNode jsonOut = Json.toJson( expressions );

        System.out.println( "Expressions " );
        System.out.println( jsonOut );

        setHeaderCORS();

        return ok( jsonOut );
    }


    public static Result updateExpression(String expressionIRI, String exprName) {
        System.out.println( "Update expression request for  " + expressionIRI );

        final Http.Request request = request();
        Http.RequestBody body = request.body();


        ModelHome.updateNamedExpression( expressionIRI, exprName, body.asRaw().asBytes() );
        System.out.println( new String( body.asRaw().asBytes() ) );
        setHeaderCORS();

        return ok();
    }


    public static Result configExpressionsAccess(String path) {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST");
        response().setHeader("Access-Control-Allow-Headers", "accept, origin, Content-type, x-json, x-prototype-version, x-requested-with");
        return ok();
    }


}
