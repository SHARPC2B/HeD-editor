package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ModelHome;
import models.PrimitiveInst;
import models.Rule;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 9/15/13
 */
public class RuleActions
        extends Controller
{

    public static Result create()
    {
        Rule rule = ModelHome.createRule();

        System.out.println( "new rule ruleId = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( rule );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();

//        return created( jsonOut );
        return created( jsonOut );
    }

    public static Result list()
    {
        List<String> ids = ModelHome.getRuleIds();

//        System.out.println( "new rule ruleId = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( ids );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();

//        return created( jsonOut );
        return ok( jsonOut );
    }

    public static Result get(String id)
    {
        Rule rule = ModelHome.getRule( id );

        System.out.println( "new rule ruleId = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( rule );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();

//        return created( jsonOut );
        return ok( jsonOut );
    }

    public static Result createPrimitive(String id,
                                         String pId)
    {
        System.out.println( "createPrimitive(id,pId), id = " + id + ", pId = " + pId );

        PrimitiveInst primitiveInst = ModelHome.createPrimitiveInst( id, pId );

//        Rule rule = ModelHome.getRule( id );
//
//        PrimitiveTemplate template = ModelHome.getPrimitiveTemplate( pId );
//
//        PrimitiveInst inst = new PrimitiveInst();
//        inst.type = template;

        System.out.println( "new primitive Id = " + primitiveInst.id );
        JsonNode jsonOut = Json.toJson( primitiveInst );
        System.out.println( "created Rule = " + jsonOut );

        if ( jsonOut instanceof ObjectNode )
        {
            ObjectNode oNode = (ObjectNode) jsonOut;
//            oNode.remove( "parameterInsts" );
//            oNode.remove( "type" );

            setHeaderCORS();
            return ok( oNode );
        }
//        jsonOut.

        setHeaderCORS();

//        return created( jsonOut );
        return ok( jsonOut );
    }

    /**
     * New version of creating a new instance of a Primitive from a template.
     */
    public static Result createPrimitive2(String tId)
    {
        System.out.println( "createPrimitive2(tId), tId = " + tId );



        PrimitiveInst primitiveInst = ModelHome.createPrimitiveInst( tId );
//
////        Rule rule = ModelHome.getRule( id );
////
////        PrimitiveTemplate template = ModelHome.getPrimitiveTemplate( pId );
////
////        PrimitiveInst inst = new PrimitiveInst();
////        inst.type = template;
//
//        System.out.println( "new primitive Id = " + primitiveInst.id );
//        final boolean trimJson = true;
//        final JsonNode jsonOut = trimJson ? primitiveInst.toJsonCore() : Json.toJson( primitiveInst );
//        System.out.println( "created Rule = " + jsonOut );
//
//        if ( jsonOut instanceof ObjectNode )
//        {
//            ObjectNode oNode = (ObjectNode) jsonOut;
//
//            setHeaderCORS();
//            return ok( oNode );
//        }

        setHeaderCORS();

//        return ok( jsonOut );
        return ok();
    }

    //##############################################################################################

    /**
     * Implements GET /template/inst
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result savePrimitive2 (final boolean verify,
                                           final boolean save)
    {
//        String msg = "submitted: " + "verify = " + verify + ", save = " + save;
//        JsonNode js = Json.parse( "submitted: " + "verify = " + verify + ", save = " + save );
        ObjectNode j = Json.newObject();
        j.put( "verify", verify );
        j.put( "save", save );

//        JsonNode jn = new JsonNode();
//        String jText = "{submitted: "+"verify = "+verify+", save = "+save;
//        JsonNode json = Json.toJson(  );

        final Http.Request request = request();

        Http.RequestBody body = request.body();

        JsonNode jn = body.asJson();

//        System.out.println( "body as JsonNode = \n" + jn );

        Object o = Json.fromJson( jn, PrimitiveInst.class );

        PrimitiveInst template = (PrimitiveInst) o;

        if ( verify )
        {
            template.verify();

            System.out.println( "verified template.id = " + template.id );
        }

//        int n = inGraph.triples.size();

        JsonNode jsonOut = Json.toJson( template );
        setHeaderCORS();

        return ok( jsonOut );
    }

}
