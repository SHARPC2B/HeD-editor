package controllers.graph;

import play.mvc.Controller;
import play.mvc.Result;

import static controllers.SharpController.setHeaderCORS;

/**
 * Created with IntelliJ IDEA. User: rk Date: 7/23/13 Time: 12:09 AM
 */
public class Arithmetic
        extends Controller
{

    public static Result zero ()
    {
        return ok( String.valueOf( 0 ) );
    }

//    public static Result add(String x, String y) {
//        int xi = Integer.parseInt(x);
//        int yi = Integer.parseInt(y);
//        int r = xi + yi;
//        return ok(String.valueOf(r));
//    }

    public static Result add (Long x,
                              Long y)
    {
        long xi = x.longValue();
        long yi = y.longValue();
        long r = xi + yi;

        setHeaderCORS();
        return ok( String.valueOf( r ) );
    }

    public static Result mult (Long x,
                               Long y)
    {
        long xi = x.longValue();
        long yi = y.longValue();
        long r = xi * yi;

        setHeaderCORS();
        return ok( String.valueOf( r ) );
    }

//    public static Result add(int x, int y) {
//        int r = x + y;
//        return ok(String.valueOf(r));
//    }

}
