package sharpc2b.transform;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * User: rk Date: 5/16/13 Time: 9:28 AM
 */
public class FileUtil
{

    /**
     * This is a file that must exist inside the main classpath resources directory, right now:
     * ".../sharp-editor/model-transform/src/main/resources/".  This code uses the Java Resource locator to
     * find this file in a machine-independent way, then uses the folder of this located file as the file
     * system folder to use as the root for locating other resource files.
     */
    private static final String knownRootResourcePath = "/FileInMainResourcesRoot.properties";

    /**
     * Create a java.io.File object for the input path argument.  The path is a String value relative to the
     * main resources root folder, using '/' as the path separator character (whether Windows or Unix file
     * system).  An example value of path would be "onts/in/ClinicalDomainT.ofn".  Thus, if resource root is
     * ".. ./sharp-editor/model-transform/src/main/resources/", then the File object will correspond to
     * ./sharp-editor/model-transform/src/main/resources/onts/in/ClinicalDomainT.ofn".
     *
     * The file does not have to exist for the File object to be successfully created.
     */
    public static final File getExistingResourceAsFile (final String path)
    {
        URL url = System.class.getResource( path );
        if (url != null)
        {
            try
            {
                return new File( url.toURI() );
            }
            catch (URISyntaxException e)
            {
//                e.printStackTrace();
            }
        }
        URL referenceURL = System.class.getResource( knownRootResourcePath );

        System.out.println( "referenceURL = " + referenceURL );

        if (referenceURL == null)
        {
            final String message = "Not able to find root of Resources directory because method " +
                    "depends on finding known resource with path = '" + knownRootResourcePath + "'";
            throw new RuntimeException( message );
        }
        try
        {
            URI rootURI = new File( referenceURL.toURI() ).getParentFile().toURI();
//            System.out.println(rootURI);
            final String fullPath = rootURI.toString() + path;
            URI fullURI = new URI( fullPath );
            File f = new File( fullURI );
//            System.out.println( "fullPath to create URI = " + fullPath );
//            System.out.println( "resource file = " + f );
//            System.out.println( "Resource Root = " + knownResouceFile.getParentFile() );
//            String fullPath = knownResouceFile.getParentFile().getAbsolutePath() + path;
//            File f = new File( fullPath );

            return f;
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();

            return null;
        }
    }

    public static final File getFileInProjectDir (final String path)
    {
        URL url = System.class.getResource( knownRootResourcePath );

        if (url == null)
        {
            final String message = "Not able to find root of Resources directory because method " +
                    "depends on finding known resource with path = '" + knownRootResourcePath + "'";
            throw new RuntimeException( message );
        }
        try
        {
            File resourceDir = new File( url.toURI() ).getParentFile();
            File projectDir;
            try
            {
                projectDir = resourceDir.getParentFile().getParentFile().getParentFile();
            }
            catch (Exception ex)
            {
                throw new RuntimeException( ex );
            }
            String projectUriString = projectDir.toURI().toString();
            String fileUriString = projectUriString + (projectUriString.endsWith( "/" ) ? "" : "/") + path;
            URI fullURI = new URI( fileUriString );
            File file = new File( fullURI );
            return file;
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Create a java.io.InputStream object for the input path argument.  The path is a String value relative
     * to the main resources root folder, using '/' as the path separator character An example value of
     * resourcePath would be "onts/in/ClinicalDomainT.ofn".
     */
    public static final InputStream getResourceAsStream (final String resourcePath)
    {
//        InputStream inStream = System.class.getResourceAsStream( resourcePath );
        InputStream inStream = FileUtil.class.getResourceAsStream( resourcePath );

        if (inStream == null)
        {
            final String message = "Not able to find root of Resources directory because method " +
                    "depends on finding known resource with path = '" + knownRootResourcePath + "'";
            throw new RuntimeException( message );
        }

        return inStream;
    }

    /**
     * An alternative to getExistingResourceAsFile() that takes a URI as input.  The input URI can either be an
     * absolute file URI (i.e., URI scheme = "file"), in which case the method will return a File for that
     * URI, or it can be a relative URI (no URI 'scheme'), in which case the File will be relative to the
     * resource root -- this method will delegate to getFileInTestResourceDir().
     */
    public static final File getExistingResourceAsFile (final URI uri)
    {
        if (uri == null)
        {
            return null;
        }
        String scheme = uri.getScheme();
        if (scheme != null && !"file".equals( scheme ))
        {
            return null;
        }
        if (uri.isAbsolute())
        {
//            System.out.println( "abs URI = " + uri );

            return new File( uri );
        }
        else
        {
//            System.out.println( "uri.getPath() = " + uri.getPath() );

            return getExistingResourceAsFile( uri.getPath() );
        }
    }

}
