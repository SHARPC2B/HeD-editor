package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ModelHome;
import models.PrimitiveInst;
import models.PrimitiveTemplate;
import models.TemplateGroup;
import models.TemplateList;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 7/31/13 Package: controllers
 */
public class ArtifactActions extends play.mvc.Controller {

    /**
     * Implements GET /template/list/:category and /template/list
     */
    public static Result getTemplateListJson () {
        return getTemplateListByGroupJson( null );
    }

    public static Result getTemplateListByGroupFiltered( String category, String cs, String cd ) {
        final String cat = category != null ? category : request().getQueryString( "category" );

        TemplateList tList = ModelHome.getTemplateList( category, cs, cd );

        return groupTemplates( tList );
    }

    public static Result getTemplateListByGroupJson( final String category ) {
        final String cat = category != null ? category : request().getQueryString( "category" );

        TemplateList tList = ModelHome.getTemplateList( category );

        return groupTemplates( tList );
    }

    private static Result groupTemplates( TemplateList tList ) {
        final List<TemplateGroup> templateGroups = new LinkedList<TemplateGroup>();

        for ( PrimitiveTemplate template : tList.templates ) {
            TemplateGroup tGroup = null;
            for ( TemplateGroup g : templateGroups ) {
                if ( template.group.equals( g.label ) ) {
                    tGroup = g;
                }
            }
            if ( tGroup == null ) {
                tGroup = new TemplateGroup();
                tGroup.label = template.group;
                templateGroups.add( tGroup );
            }
            tGroup.templates.add( template );
        }

        Collections.sort( templateGroups );

        try {
            JsonNode jsonValue = Json.toJson( templateGroups );
            setHeaderCORS();
            return ok( jsonValue );
        } catch (Exception e) {
            setHeaderCORS();
            return notFound( "Error converting to JSON, object = " + "\n" + templateGroups );
        }
    }

    public static Result verifyTemplate() {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        PrimitiveTemplate templ = Json.fromJson( body.asJson(), PrimitiveTemplate.class );

        List<String> errors = ModelHome.verifyTemplate( templ );
        setHeaderCORS();
        return ok( Json.toJson( errors ) );
    }


    public static Result getTemplateDetails( String id ) {
        PrimitiveTemplate template = ModelHome.getTemplateDetail( id );

        JsonNode jsonValue = Json.toJson( template );
        System.out.println( "fetched value (json) = |" + jsonValue + "|" );
        setHeaderCORS();
        return ok( jsonValue );
    }





    //##############################################################################################

    /**
     * Implements GET /template/inst
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result saveTemplateInst (final boolean verify,
                                           final boolean save)
    {
//        String msg = "submitted: " + "verify = " + verify + ", save = " + save;
//        JsonNode js = Json.parse( "submitted: " + "verify = " + verify + ", save = " + save );

//        ObjectNode j = Json.newObject();
//        j.put( "verify", verify );
//        j.put( "save", save );

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
