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
//        File f = FileUtil.getFileInTestResourceDir(path)

        /* The Java default for getting resources appears to be case-insensitive */

        assertEquals true, FileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "/onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals false, FileUtil.getFileInTestResourceDir( "ClinicalDomainT.ofn" ).exists()
        assertEquals false, FileUtil.getFileInTestResourceDir( "/ClinicalDomainT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.OFN" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "/onts/in/CLINICALDOMAINT.ofn" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "ONTS/IN/ClinicalDomainT.OFN" ).exists()

        assertEquals true, FileUtil.getFileInTestResourceDir( "/onts/in/clinicaldomaint.ofn" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "/onts/in/ClinicalDomainT.oFN" ).exists()
        assertEquals true, FileUtil.getFileInTestResourceDir( "/onts/in/cLINICALdOMAINt.oFn" ).exists()
        assertEquals false, FileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT" ).exists()
    }


    void testFindResourceFileFromURI () {
//        println new URI( "onts/in/ClinicalDomainT.ofn" )
//        println new URI( "ClinicalDomainT.ofn" )
//        println new URI( "/ClinicalDomainT.ofn" )
        assertEquals true, FileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.ofn" ) ).exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "onts/in/ClinicalDomainT.ofn" ) )}
        assertEquals false, FileUtil.getTestResourceFile( new URI( "file:" + "/onts/in/ClinicalDomainT.ofn" ) )
                .exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "ClinicalDomainT.ofn" ) )}
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isAbsolute()
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isOpaque()
        assertEquals true, FileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, FileUtil.getTestResourceFile( new URI( "ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, FileUtil.getTestResourceFile( new URI( "/ClinicalDomainT.ofn" ) ).exists()
        assertEquals true, FileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.OFN" ) ).exists()
        assertEquals true, FileUtil.getTestResourceFile( new URI( "/onts/in/CLINICALDOMAINT.ofn" ) ).exists()
        assertEquals true, FileUtil.getTestResourceFile( new URI( "ONTS/IN/ClinicalDomainT.OFN" ) ).exists()

        assertEquals true, FileUtil.getTestResourceFile( new URI(  "/onts/in/clinicaldomaint.ofn" ) ).exists()
        assertEquals true, FileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.oFN" ) ).exists()
        assertEquals true, FileUtil.getTestResourceFile( new URI( "/onts/in/cLINICALdOMAINt.oFn" ) ).exists()
        assertEquals false, FileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT" ) ).exists()

    }

}
