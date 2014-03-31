package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ModelHome;
import models.NamedConcept;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static controllers.SharpController.setHeaderCORS;
import static play.mvc.Results.ok;

public class DomainModelActions extends Controller {


    public static Result getDomainClassHierarchyDescr() {
        String hier = ModelHome.getDomainClassHierarchyDescription();
        setHeaderCORS();
        return ok( hier );
    }

    public static Result getAvailableClasses() {
        Map<String,String> domainClasses = ModelHome.getDomainClasses();
        System.out.println( "Retrieved domain klasses from model " + domainClasses );

        List<NamedConcept> expressions = new ArrayList<NamedConcept>();
        for ( String klassId : domainClasses.keySet() ) {
            NamedConcept klass = new NamedConcept( klassId, domainClasses.get( klassId ) );
            expressions.add( klass );
        }

        JsonNode jsonOut = Json.toJson( expressions );

        setHeaderCORS();

        return ok( jsonOut );
    }

    public static Result getAvailableProperties() {
        Map<String,String> domainClasses = ModelHome.getDomainProperties();

        final Http.Request request = request();
        Http.RequestBody body = request.body();
        System.out.println( "live classes " + body );


        List<NamedConcept> properties = new ArrayList<NamedConcept>();
        for ( String propId : domainClasses.keySet() ) {
            NamedConcept prop = new NamedConcept( propId, domainClasses.get( propId ) );
            properties.add( prop );
        }

        JsonNode jsonOut = Json.toJson( properties );

        setHeaderCORS();

        return ok( jsonOut );
    }

    public static Result getAvailablePropertiesForKlass( String klass ) {
        Map<String,String> domainClasses = ModelHome.getDomainProperties( klass );

        List<NamedConcept> expressions = new ArrayList<NamedConcept>();
        for ( String propId : domainClasses.keySet() ) {
            NamedConcept prop = new NamedConcept( propId, domainClasses.get( propId ) );
            expressions.add( prop );
        }

        JsonNode jsonOut = Json.toJson( expressions );

        setHeaderCORS();

        return ok( jsonOut );
    }


}
