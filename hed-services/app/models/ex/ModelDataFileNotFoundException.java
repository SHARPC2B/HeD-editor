package models.ex;

/**
 * User: rk Date: 8/19/13 Package: models.ex
 */
public class ModelDataFileNotFoundException
        extends RuntimeException
{
    public ModelDataFileNotFoundException ()
    {
    }

    public ModelDataFileNotFoundException (final String message)
    {
        super( message );
    }

    public ModelDataFileNotFoundException (final String message,
                                           final Throwable cause)
    {
        super( message, cause );
    }

    public ModelDataFileNotFoundException (final Throwable cause)
    {
        super( cause );
    }

//    public ModelDataFileNotFoundException (final String message,
//                                           final Throwable cause,
//                                           final boolean enableSuppression,
//                                           final boolean writableStackTrace)
//    {
//        super( message, cause, enableSuppression, writableStackTrace );
//    }

}
