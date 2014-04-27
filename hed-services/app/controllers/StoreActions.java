package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Artifact;
import models.ModelHome;
import models.PrimitiveInst;
import models.Rule;
import models.RuleGroup;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static controllers.SharpController.setHeaderCORS;


public class StoreActions
        extends play.mvc.Controller {

    public static Result getAvailableArtifacts() {
        List<String> ruleIds = ModelHome.getAvailableArtifacts();

        System.out.println( "found rules = " + ruleIds );

        List<RuleGroup> groups = new ArrayList<RuleGroup>();

        RuleGroup ruleGroup = new RuleGroup();
        ruleGroup.label = "HeD rules";
        ruleGroup.templates = new ArrayList<Artifact>( ruleIds.size() );

        for ( String id : ruleIds ) {
            Artifact artifact = new Artifact();
            artifact.name = id;
            ruleGroup.templates.add( artifact );
        }

        groups.add( ruleGroup );

        JsonNode jsonOut = Json.toJson( groups );

        System.out.println( jsonOut );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result createNewArtifact() {
        Rule rule = ModelHome.createArtifact();

        System.out.println( "Created New rule = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( rule );

        setHeaderCORS();
        return ok( jsonOut );
    }


    public static Result importFromStream(String fileName) {
        final Http.Request request = request();
        System.out.println( "Starting import process" );

        Http.RequestBody body = request.body();
        byte[] data = body.asRaw().asBytes();

        // TODO : Improve this, as it is removing the wrapper { "bytes" : " .... " } created by the js controller
        data = Arrays.copyOfRange( data, 10, data.length - 2 );
        String hed = new String ( data );
        hed = hed.replace( "\\\"", "\"" );
        hed = hed.replace( "\\n", "\n" );

        String result = ModelHome.importFromStream( hed.getBytes() );

        System.out.println( "Import result = " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result cloneArtifact( String id ) {
        String result = ModelHome.cloneArtifact( id );

        System.out.println( "Cloned " + id + " := " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return created( jsonOut );
    }

    public static Result openArtifact( String id ) {
        System.out.println( "Loading " + id  );
        String result = ModelHome.openArtifact( id );

        System.out.println( "Loaded " + id + " := " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return created( jsonOut );
    }

    public static Result snapshotArtifact( String id ) {

        String result = ModelHome.snapshotArtifact( id );

        System.out.println( "Snapshot " + id + " := " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return created( jsonOut );
    }

    public static Result saveArtifact( String id ) {
        String result = ModelHome.saveArtifact( id );

        System.out.println( "Save " + id + " := " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result exportArtifact( String id, String format ) {
        byte[] result = ModelHome.exportArtifact( id , format );

        System.out.println( "Export " + id + " := " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result closeArtifact( ) {

        String result = ModelHome.closeArtifact();

        System.out.println( "Close " + result );
        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result deleteArtifact( String id ) {
        System.out.println( "Delete " + id + " := " + id );

        String result = ModelHome.deleteArtifact( id );


        JsonNode jsonOut = Json.toJson( result );

        setHeaderCORS();
        return ok( jsonOut );
    }


    public static Result configStoreAccess( String path ) {
        setHeaderCORS();
        return ok();
    }


    public static Result list() {
        List<String> ids = ModelHome.getAvailableArtifacts();

        JsonNode jsonOut = Json.toJson( ids );
        setHeaderCORS();

        return ok( jsonOut );
    }

    public static Result get(String id) {
        Rule rule = ModelHome.getArtifact( id );
        JsonNode jsonOut = Json.toJson( rule );

        setHeaderCORS();
        return ok( jsonOut );
    }



}
