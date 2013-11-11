package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import static controllers.SharpController.setHeaderCORS;

public class Application
        extends Controller
{

    public static Result index ()
    {

        setHeaderCORS();

        return ok( index.render( "Your new application is ready." ) );
    }

}
