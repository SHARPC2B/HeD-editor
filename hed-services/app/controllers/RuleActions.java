package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ModelHome;
import models.NamedConcept;
import models.Rule;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controllers.SharpController.setHeaderCORS;

/**
 * User: rk Date: 9/15/13
 */
public class RuleActions
        extends play.mvc.Controller
{

    public static Result create() {
        Rule rule = ModelHome.createArtifact();

        System.out.println( "new rule ruleId = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( rule );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();
        return created( jsonOut );
    }

    public static Result list() {
        List<String> ids = ModelHome.getAvailableArtifacts();

        JsonNode jsonOut = Json.toJson( ids );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result get(String id) {
        Rule rule = ModelHome.getArtifact( id );

        System.out.println( "new rule ruleId = " + rule.ruleId );
        JsonNode jsonOut = Json.toJson( rule );
        System.out.println( "created Rule = " + jsonOut );

        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getExternalFormat( String id, String format ) {
        byte[] outerFormat = ModelHome.exportArtifact( id, format );
        setHeaderCORS();
        return ok( outerFormat );
    }


    public static Result getCurrentInfo() {
        Rule rule = ModelHome.getCurrentArtifact();
        JsonNode jsonOut = Json.toJson( rule );

        if ( rule.ruleId != null ) {
            System.out.println( "Rule is active : " + rule.ruleId );
            setHeaderCORS();
            return ok( jsonOut );
        } else {
            System.out.println( "No Rule active" );
            setHeaderCORS();
            return notFound( jsonOut );
        }
    }

    public static Result updateRuleInfo( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Rule rule = Json.fromJson( body.asJson(), Rule.class );

        if ( rule.ruleId.equals( id ) ) {
            ModelHome.updateBasicInfo( rule );
        } else {
            throw new IllegalStateException( "Something went wrong. UI has ruleId " + id + " but background info is set to " + rule.ruleId );
        }

        setHeaderCORS();
        return created( body.asJson() );
    }


    public static Result getUsedDomainClasses( String id ) {
        Map<String,String> domainClasses = ModelHome.getUsedDomainClasses( id );

        List<NamedConcept> expressions = new ArrayList<NamedConcept>();
        for ( String klassId : domainClasses.keySet() ) {
            NamedConcept klass = new NamedConcept( klassId, domainClasses.get( klassId ) );
            expressions.add( klass );
        }

        JsonNode jsonOut = Json.toJson( expressions );
        setHeaderCORS();

        return ok( jsonOut );
    }

    public static Result addUsedDomainClass( String id ) {
        ModelHome.addUsedDomainClass( id );
        setHeaderCORS();
        return ok();
    }


}
