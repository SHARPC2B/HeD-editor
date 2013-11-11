package controllers.graph;

import com.fasterxml.jackson.databind.JsonNode;
import models.graph.Graph;
import models.graph.Triple;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 7/23/13
 */
public class GraphActions
        extends Controller
{

    static Graph testGraph1 = createTestGraph();

    static Graph testGraph ()
    {
        return testGraph1;
    }

    public static Result index ()
    {
        setHeaderCORS();
        return ok( Json.toJson( testGraph() ) );
    }

    public static Result first ()
    {
        setHeaderCORS();
        return ok( Json.toJson( testGraph().triples.get( 0 ) ) );
//                .as("application/json");
    }

    public static Result list ()
    {
        setHeaderCORS();
        return ok( Json.toJson( testGraph().triples ) );
    }

    public static Result forSubject (String subject)
    {
        List<Triple> tt = new ArrayList<Triple>();

        for (Triple t : testGraph().triples)
        {
            if (subject.equals( t.subject ))
            {
                tt.add( t );
            }
        }
        setHeaderCORS();
        return ok( Json.toJson( tt ) );
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result addJsonGraph ()
    {
        final Http.Request request = request();

        Http.RequestBody body = request.body();

        JsonNode jn = body.asJson();

        System.out.println( "body as JsonNode = \n" + jn );

        Object o = Json.fromJson( jn, Graph.class );

        Graph inGraph = (Graph) o;

        int n = inGraph.triples.size();

        setHeaderCORS();
        return ok( "JSON received Graph with " + n + " Triples" );
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result addJsonTriple ()
    {
        final Http.Request request = request();

        Http.RequestBody body = request.body();

        JsonNode jn = body.asJson();

        System.out.println( "body as JsonNode = \n" + jn );

        Object o = Json.fromJson( jn, Triple.class );

        if (o instanceof Triple)
        {
            Triple t = (Triple) o;

            // return the 'subject' in the response.
            //
            String subject = t.subject;

            setHeaderCORS();
            return ok( "JSON received with Triple, subject = " + subject );
        }
        else
        {
            return internalServerError( "Unable to convert input into a Triple, " +
                                                "input body = " + "\n" + body.toString() );
        }
    }

    static <T extends Model> T fromJsonRequest (JsonNode jn)
    {
        System.out.println( "body as JsonNode = \n" + jn );

        Object o = Json.fromJson( jn, Triple.class );

        T t;
        t = (T) o;

//        t = (Triple) Ebean.createJsonContext().toObject( Triple.class, jsonText, new JsonReadOptions() );
//        Object bean = (Triple) Ebean.createJsonContext().toBean( Triple.class, jsonText );
//        t = new Triple.Finder();
//        Triple t = body.as( Triple.class );

        Class<?> type = t.getClass();
        return t;
    }

    public static Graph createTestGraph ()
    {
        Graph g = new Graph();
        g.name = "Test-Graph-1";

        g.add( new Triple( "Jack", "wife", "Jill" ) );
        g.add( new Triple( "Jack", "livesIn", "Texas" ) );
        g.add( new Triple( "Jill", "livesIn", "Texas" ) );
        g.add( new Triple( "Pete", "livesIn", "Kansas" ) );
        g.add( new Triple( "Linda", "livesIn", "Ohio" ) );
        g.add( new Triple( "Joe", "livesIn", "New York" ) );
        g.add( new Triple( "Lance", "livesIn", "California" ) );
        g.add( new Triple( "Sharon", "livesIn", "California" ) );
        g.add( new Triple( "Sharon", "brother", "Pete" ) );
        g.add( new Triple( "Jack", "brother", "Pete" ) );
        g.add( new Triple( "Pete", "wife", "Linda" ) );
        g.add( new Triple( "Lance", "wife", "Sharon" ) );

        return g;
    }

}
