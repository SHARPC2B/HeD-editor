package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * User: rk Date: 7/31/13 Package: controllers
 */
public class SharpController
        extends play.mvc.Controller
{

    // static String CORS_ORIGIN = "http://localhost:8080/";
    public static String CORS_ORIGIN = "*";

    //    @Before
    public static void setHeaderCORS ()
    {
        response().setHeader( "Access-Control-Allow-Origin", CORS_ORIGIN );
    }

    /**
     * @param ignore wanted one version of this method whether or not there were args in the URL, so
     *               added a parameter that is not really used but can be passed in to avoid compile
     *               errors.
     */
    public static Result checkPreFlight (String ignore)
    {
        response().setHeader( "Access-Control-Allow-Origin", CORS_ORIGIN );
        response().setHeader( "Access-Control-Allow-Methods", "POST,PUT,GET,DELETE" );
        response()
                .setHeader( "Access-Control-Max-Age", "300" );          // Cache response for 5 minutes
        response().setHeader( "Access-Control-Allow-Headers",
                              "Origin, X-Requested-With, Content-Type, Accept" );         // Ensure this header is also allowed!
        return ok();
    }

}
