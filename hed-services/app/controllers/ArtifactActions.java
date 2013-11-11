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

import java.util.LinkedList;
import java.util.List;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 7/31/13 Package: controllers
 */
public class ArtifactActions
        extends Controller
{

//    static final String templateListResourcePath = ModelHome
//            .jsonFileForClass( "template-list-grouped" );
//
//    /**
//     * Implements GET /template/list
//     */
//    public static Result getTemplateListJson ()
//    {
//        final URL url = Resources.getResource( templateListResourcePath );
//        final String value;
//        try
//        {
//            value = Resources.toString( url, Charsets.UTF_8 );
//        }
//        catch (IOException e)
//        {
//            setHeaderCORS();
//            return internalServerError(
//                    "IO error while trying to find and load resource " + templateListResourcePath );
//        }
//        try
//        {
//            JsonNode jsonValue = Json.parse( value );
////            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
//            setHeaderCORS();
//            return ok( jsonValue );
//        }
//        catch (Exception e)
//        {
//            setHeaderCORS();
//            return notFound( "Error parsing as JSON, text = " + "\n" + value );
//        }
//    }

    /**
     * Implements GET /template/list/:category and /template/list
     */
    public static Result getTemplateListJson () {
        return getTemplateListByGroupJson( null );
    }

    public static Result getTemplateListByGroupJson (final String category)
    {
        final String cat = category != null ? category : request().getQueryString( "category" );

        System.out.println( "category = " + category );
        System.out.println( "cat = " + cat );
        System.out.println( "uri = " + request().uri() );
        System.out.println( "query = " + request().queryString() );

        TemplateList tList = ModelHome.getTemplateList( category );

        System.out.println( "Retrieved " + tList );

        final List<TemplateGroup> templateGroups = new LinkedList<TemplateGroup>();

        for ( PrimitiveTemplate template : tList.templates ) {
            if ( cat == null || cat.equals( template.category ) ) {
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
        }
        try {
            JsonNode jsonValue = Json.toJson( templateGroups );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
            setHeaderCORS();
            return ok( jsonValue );
        } catch (Exception e) {
            setHeaderCORS();
            return notFound( "Error converting to JSON, object = " + "\n" + templateGroups );
        }
    }

    /**
     * Implements GET /template/:id
     */
    public static Result getConstructedTemplateForID (final String id)
    {
        System.out.println( "Template ID = " + id );

        PrimitiveTemplate selectedTemplate = ModelHome.getPrimitiveTemplate( id );

//        TemplateList tList = ModelHome.createJavaInstanceFromJsonFile( TemplateList.class );
//
//        ParameterList pList = ModelHome.createJavaInstanceFromJsonFile( ParameterList.class );
//
//        PrimitiveTemplate selectedTemplate = null;
//        for ( PrimitiveTemplate t : tList.templates )
//        {
//            if ( t.id.equals( id ) )
//            {
//                selectedTemplate = t;
//            }
//        }
//        spliceInParameters( selectedTemplate, pList );
        JsonNode json = Json.toJson( selectedTemplate );
        setHeaderCORS();
        return ok( json );
    }

//    private static void spliceInParameters (final PrimitiveTemplate selectedTemplate,
//                                            final ParameterList allParameters)
//    {
//        if ( selectedTemplate == null )
//        {
//            return;
//        }
//        for ( String paramId : selectedTemplate.parameterIds )
//        {
//            ParameterType p = findParameter( paramId, allParameters );
//            if ( p != null )
//            {
//                selectedTemplate.parameters.add( p );
//            }
//        }
//    }
//
//    private static ParameterType findParameter (final String paramId,
//                                                    final ParameterList allParameters)
//    {
//        for ( ParameterType p : allParameters.parameters )
//        {
//            if ( p.id.equals( paramId ) )
//            {
//                return p;
//            }
//        }
//        return null;
//    }

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
