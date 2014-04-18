package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ModelHome;
import models.metadata.Contributor;
import models.metadata.Coverage;
import models.metadata.Resource;
import models.metadata.SupportingResource;
import models.metadata.UsageTerm;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

import static controllers.SharpController.setHeaderCORS;


public class MetadataActions extends play.mvc.Controller {


    public static Result getKeyTerms( String id ) {
        List<String> keyTerms = ModelHome.getKeyTerms( id );
        JsonNode jsonOut = Json.toJson( keyTerms );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getCategories( String id ) {
        List<String> keyTerms = ModelHome.getCategories( id );
        JsonNode jsonOut = Json.toJson( keyTerms );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getContributors( String id ) {
        List<Contributor> contribs = ModelHome.getContributors( id );
        JsonNode jsonOut = Json.toJson( contribs );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getCoverage( String id ) {
        List<Coverage> covers = models.ModelHome.getCoverage( id );
        JsonNode jsonOut = Json.toJson( covers );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getPublishers( String id ) {
        List<Contributor> pubs = ModelHome.getPublishers( id );
        JsonNode jsonOut = Json.toJson( pubs );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getRights( String id ) {
        List<UsageTerm> terms = ModelHome.getUsageRights( id );
        JsonNode jsonOut = Json.toJson( terms );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getEvidence( String id ) {
        List<Resource> ress = ModelHome.getRelatedResources( id );
        JsonNode jsonOut = Json.toJson( ress );
        setHeaderCORS();
        return ok( jsonOut );
    }

    public static Result getResources( String id ) {
        List<SupportingResource> supp = ModelHome.getSupportingEvidence( id );
        JsonNode jsonOut = Json.toJson( supp );
        setHeaderCORS();
        return ok( jsonOut );

    }

    public static Result getDocumentation( String id ) {
        String doc = ModelHome.getArtifactDocumentation( id );
        setHeaderCORS();
        return ok( doc );
    }






    public static Result addKeyTerm( String id ) {
        setHeaderCORS();
        return created();
    }

    public static Result addCategory( String id ) {
        setHeaderCORS();
        return created();
    }

    public static Result addContributor( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Contributor contri = Json.fromJson( body.asJson(), Contributor.class );

        ModelHome.addContributor( contri, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result addCoverage( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Coverage cover = Json.fromJson( body.asJson(), Coverage.class );

        ModelHome.addCoverage( cover, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result addPublisher( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Contributor pub = Json.fromJson( body.asJson(), Contributor.class );

        ModelHome.addPublisher( pub, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result addRights( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        UsageTerm term = Json.fromJson( body.asJson(), UsageTerm.class );

        ModelHome.addUsageRights( term, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result addEvidence( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        SupportingResource res = Json.fromJson( body.asJson(), SupportingResource.class );

        ModelHome.addSupportingEvidence( res, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result addResources( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Resource res = Json.fromJson( body.asJson(), Resource.class );

        ModelHome.addRelatedResource( res, id );

        setHeaderCORS();
        return created( body.asJson() );
    }

    public static Result setDocumentation( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();

        ModelHome.setArtifactDocumentation( body.asText(), id );

        setHeaderCORS();
        return ok();
    }





    public static Result removeKeyTerm( String id ) {
        setHeaderCORS();
        return created();
    }

    public static Result removeCategory( String id ) {
        setHeaderCORS();
        return created();
    }

    public static Result removeContributor( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Contributor contri = Json.fromJson( body.asJson(), Contributor.class );

        ModelHome.removeContributor( contri, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

    public static Result removePublisher( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Contributor pub = Json.fromJson( body.asJson(), Contributor.class );

        ModelHome.removePublisher( pub, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

    public static Result removeCoverage( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Coverage cover = Json.fromJson( body.asJson(), Coverage.class );

        ModelHome.removeCoverage( cover, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

    public static Result removeRights( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        UsageTerm term = Json.fromJson( body.asJson(), UsageTerm.class );

        ModelHome.removeUsageRights( term, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

    public static Result removeEvidence( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        SupportingResource res = Json.fromJson( body.asJson(), SupportingResource.class );

        ModelHome.removeSupportingEvidence( res, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

    public static Result removeResources( String id ) {
        final Http.Request request = request();
        Http.RequestBody body = request.body();
        Resource res = Json.fromJson( body.asJson(), Resource.class );

        ModelHome.removeRelatedResource( res, id );

        setHeaderCORS();
        return ok( body.asJson() );
    }

}
