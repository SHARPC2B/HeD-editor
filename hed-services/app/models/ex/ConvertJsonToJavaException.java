package models.ex;

/**
 * User: rk Date: 8/19/13 Package: models.ex
 */
public class ConvertJsonToJavaException extends RuntimeException

{
    public ConvertJsonToJavaException ()
    {
    }

    public ConvertJsonToJavaException (final String message)
    {
        super( message );
    }

    public ConvertJsonToJavaException (final String message,
                                       final Throwable cause)
    {
        super( message, cause );
    }

    public ConvertJsonToJavaException (final Throwable cause)
    {
        super( cause );
    }

//    public ConvertJsonToJavaException (final String message,
//                                       final Throwable cause,
//                                       final boolean enableSuppression,
//                                       final boolean writableStackTrace)
//    {
//        super( message, cause, enableSuppression, writableStackTrace );
//    }
}
