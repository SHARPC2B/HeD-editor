package controllers.old;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import models.ParameterList;
import models.PrimitiveTemplate;
import models.TemplateList;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 8/29/13
 */
public class TestActions
        extends Controller
{

    static final String primitivesResourcePath = "public/data/primitive-list-2.json";

    static Map<String, String> jsonMap;

    public static Result getPrimitiveListJson ()
    {
        final URL url = Resources.getResource( primitivesResourcePath );
        final String value;
        try
        {
            value = Resources.toString( url, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            setHeaderCORS();
            return internalServerError(
                    "IO error while trying to find and load resource " + primitivesResourcePath );
        }
        try
        {
            JsonNode jsonValue = Json.parse( value );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
            setHeaderCORS();
            return ok( jsonValue );
        }
        catch (Exception e)
        {
            setHeaderCORS();
            return notFound( "Error parsing as JSON, text = " + "\n" + value );
        }
    }

    public static Result getPrimitiveByID (final String id)
    {
        System.out.println( "primitiveID = " + id );

        String primitiveJsonResourcePath = "public/data/primitive-" + id + ".json";
        System.out.println( "primitiveJsonResourcePath = " + primitiveJsonResourcePath );

        final URL url = Resources.getResource( primitiveJsonResourcePath );
        final String value;
        try
        {
            value = Resources.toString( url, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            setHeaderCORS();
            return internalServerError(
                    "IO error while trying to find and load resource " + primitiveJsonResourcePath );
        }
        try
        {
            JsonNode jsonValue = Json.parse( value );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
            setHeaderCORS();
            return ok( jsonValue );
        }
        catch (Exception e)
        {
            setHeaderCORS();
            return notFound( "Error parsing as JSON, text = " + "\n" + value );
        }
    }

    public static Result getCannedTemplateForID (final String id)
    {
        System.out.println( "Template ID = " + id );

        String cannedTemplateJsonResourcePath = "public/data/can-template-" + id + ".json";
        System.out.println( "primitiveJsonResourcePath = " + cannedTemplateJsonResourcePath );

        final URL url = Resources.getResource( cannedTemplateJsonResourcePath );
        final String value;
        try
        {
            value = Resources.toString( url, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            setHeaderCORS();
            return internalServerError( "IO error while trying to find and load resource " +
                                                cannedTemplateJsonResourcePath );
        }
        try
        {
            JsonNode jsonValue = Json.parse( value );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
            setHeaderCORS();
            return ok( jsonValue );
        }
        catch (Exception e)
        {
            setHeaderCORS();
            return notFound( "Error parsing as JSON, text = " + "\n" + value );
        }
    }

    /**
     * I believe this was used to test sending in a URI as a request parameter to see if URL encoding
     * and decoding worked.
     */
    public static Result getArtifactJson (String id)
    {
        jsonMap = createJsonMap();

//            showEncodedURI();

        System.out.println( "input id = |" + id + "|" );

//            String decodedKey = URLDecoder.decode( id, "UTF-8" );
//            System.out.println( "input id, decoded = |" + decodedKey + "|" );

        String value = jsonMap.get( id );

        if ( value == null )
        {
            StringBuilder sb = new StringBuilder( "No Artifact for id: " + id );
            sb.append( "\n" ).append( "Try one of:" );
            for ( String k : jsonMap.keySet() )
            {
                try
                {
                    sb.append( "\n    " ).append( URLEncoder.encode( k, "UTF-8" ) );
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            setHeaderCORS();
            return badRequest( sb.toString() );
        }
        else
        {
            System.out.println( "fetched value (text) = |" + value + "|" );
            JsonNode jsonValue = Json.parse( value );
            System.out.println( "fetched value (json) = |" + jsonValue + "|" );
            setHeaderCORS();
            return ok( jsonValue );
        }
    }

    private static void showEncodedURI ()
    {
        try
        {
            String plainURI = "http://www.newmentor.com/cds/rule/NQF-0068#CD_332273291";
            String encodedExample = URLEncoder.encode( plainURI, "UTF-8" );
            System.out.println( "encoded URI key = |" + encodedExample + "|" );
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    static Map<String, String> createJsonMap ()
    {

        Map<String, String> jsonMap = new HashMap<String, String>();

        jsonMap.put( "http://www.newmentor.com/cds/rule/NQF-0068#CD_332273291",
                     "{\"codeSystem\":\"2.16.840.1.113883.6.96\",\"isA\":\"ConceptCode\"," +
                             "\"label\":\"\",\"code\":\"11101003\"," +
                             "\"_id\":\"http://www.newmentor.com/cds/rule/NQF-0068#CD_332273291\"," +
                             "\"_type\":\"vertex\"}" );

        jsonMap.put( "http://www.newmentor.com/cds/rule/NQF-0068#RightsDeclaration_1608586554",
                     "{\"isA\":\"RightsDeclaration\"," +
                             "\"rightsHolder\":\"http://www.newmentor.com/cds/rule/NQF-0068#Organization_842580995\"," +
                             "\"_id\":\"http://www.newmentor.com/cds/rule/NQF-0068#RightsDeclaration_1608586554\"," +
                             "\"_type\":\"vertex\"}" );

        jsonMap.put( "http://www.newmentor.com/cds/rule/NQF-0068#Var_CABG_Encounters",
                     "{\"name\":\"CABG_Encounters\",\"isA\":\"RuleVariable\"," +
                             "\"variableFilterExpression\":\"http://www.newmentor.com/cds/rule/NQF-0068#Expr_937344243\"," +
                             "\"_id\":\"http://www.newmentor.com/cds/rule/NQF-0068#Var_CABG_Encounters\"," +
                             "\"_type\":\"vertex\"}" );

        return jsonMap;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getConstructedTemplateForID_1 (final String id)
    {
        System.out.println( "Template ID = " + id );

//        final Http.Request request = request();
//
//        Http.RequestBody body = request.body();
//
//        JsonNode jn = body.asJson();
//
//        System.out.println( "body as JsonNode = \n" + jn );
//        return ok( "JSON received Graph with " + n + " Triples" );

        String templateListJsonResourcePath = "public/data/TemplateList.json";
        String parameterListJsonResourcePath = "public/data/ParameterList.json";
        System.out.println( "templateListJsonResourcePath = " + templateListJsonResourcePath );
        System.out.println( "parameterListJsonResourcePath = " + parameterListJsonResourcePath );

        final URL urlTemplates = Resources.getResource( templateListJsonResourcePath );
        final URL urlParameters = Resources.getResource( parameterListJsonResourcePath );

        final String templatesText;
        final String parametersText;
        try
        {
            templatesText = Resources.toString( urlTemplates, Charsets.UTF_8 );
            parametersText = Resources.toString( urlParameters, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            setHeaderCORS();
            return internalServerError(
                    "IO error while trying to find and load resource " + templateListJsonResourcePath );
        }
        try
        {
            JsonNode templatesNode = Json.parse( templatesText );
            JsonNode parametersNode = Json.parse( parametersText );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );

            Object o;
            o = Json.fromJson( templatesNode, TemplateList.class );

            TemplateList tList = (TemplateList) o;

            o = Json.fromJson( parametersNode, ParameterList.class );

            ParameterList pList = (ParameterList) o;

            int nTemplates = tList.templates.size();
            int nParameters = pList.parameters.size();

            PrimitiveTemplate selectedTemplate = null;
            for ( PrimitiveTemplate t : tList.templates )
            {
                if ( t.templateId.equals( id ) )
                {
                    selectedTemplate = t;
                }
            }

            // private
//            ArtifactActions.spliceInParameters( selectedTemplate, pList );
            JsonNode json = Json.toJson( selectedTemplate );
            setHeaderCORS();
            return ok( json );
//            return ok( splicedParameterValue );
        }
        catch (Exception e)
        {
            setHeaderCORS();
            e.printStackTrace();
            return notFound( "Error parsing as JSON, text = " + "\n" + templatesText );
        }
    }

}
