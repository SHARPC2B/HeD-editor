package sharpc2b.transform
/**
 * User: rk
 * Date: 5/16/13
 * Time: 9:41 AM
 */
class FileUtilTest extends GroovyTestCase {

    void setUp () {

    }

    void tearDown () {

    }

    void testFindResource () {

//        String path = "/onts/in/ClinicalDomainT.ofn";
//        File f = FileUtil.getFileInResourceDir(path)

        /* The Java default for getting resources appears to be case-insensitive */

        assertEquals true, FileUtil.getFileInResourceDir( "onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "/onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals false, FileUtil.getFileInResourceDir( "ClinicalDomainT.ofn" ).exists()
        assertEquals false, FileUtil.getFileInResourceDir( "/ClinicalDomainT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "onts/in/ClinicalDomainT.OFN" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "/onts/in/CLINICALDOMAINT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "ONTS/IN/ClinicalDomainT.OFN" ).exists()

        assertEquals true, FileUtil.getFileInResourceDir( "/onts/in/clinicaldomaint.ofn" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "/onts/in/ClinicalDomainT.oFN" ).exists()
        assertEquals true, FileUtil.getFileInResourceDir( "/onts/in/cLINICALdOMAINt.oFn" ).exists()
        assertEquals false, FileUtil.getFileInResourceDir( "onts/in/ClinicalDomainT" ).exists()
    }


    void testFindResourceFileFromURI () {
//        println new URI( "onts/in/ClinicalDomainT.ofn" )
//        println new URI( "ClinicalDomainT.ofn" )
//        println new URI( "/ClinicalDomainT.ofn" )
        assertEquals true, FileUtil.getResourceFile( new URI( "onts/in/ClinicalDomainT.ofn" ) ).exists()
        shouldFail {FileUtil.getResourceFile( new URI( "file:" + "onts/in/ClinicalDomainT.ofn" ) )}
        assertEquals false, FileUtil.getResourceFile( new URI( "file:" + "/onts/in/ClinicalDomainT.ofn" ) )
                .exists()
        shouldFail {FileUtil.getResourceFile( new URI( "file:" + "ClinicalDomainT.ofn" ) )}
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isAbsolute()
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isOpaque()
        assertEquals true, FileUtil.getResourceFile( new URI(  "/onts/in/ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, FileUtil.getResourceFile( new URI( "ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, FileUtil.getResourceFile( new URI( "/ClinicalDomainT.ofn" ) ).exists()
        assertEquals true, FileUtil.getResourceFile( new URI( "onts/in/ClinicalDomainT.OFN" ) ).exists()
        assertEquals true, FileUtil.getResourceFile( new URI( "/onts/in/CLINICALDOMAINT.ofn" ) ).exists()
        assertEquals true, FileUtil.getResourceFile( new URI( "ONTS/IN/ClinicalDomainT.OFN" ) ).exists()

        assertEquals true, FileUtil.getResourceFile( new URI(  "/onts/in/clinicaldomaint.ofn" ) ).exists()
        assertEquals true, FileUtil.getResourceFile( new URI(  "/onts/in/ClinicalDomainT.oFN" ) ).exists()
        assertEquals true, FileUtil.getResourceFile( new URI( "/onts/in/cLINICALdOMAINt.oFn" ) ).exists()
        assertEquals false, FileUtil.getResourceFile( new URI( "onts/in/ClinicalDomainT" ) ).exists()

    }

}
