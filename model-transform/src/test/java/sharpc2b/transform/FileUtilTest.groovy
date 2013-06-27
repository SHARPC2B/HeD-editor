package sharpc2b.transform

import sharpc2b.transform.test.TestFileUtil

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
//        File f = TestFileUtil.getFileForTestOutput(path)

        /* The Java default for getting resources appears to be case-insensitive */

        assertEquals false, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "/ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.OFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/CLINICALDOMAINT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "ONTS/IN/ClinicalDomainT.OFN" ).exists()

        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/clinicaldomaint.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.oFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/cLINICALdOMAINt.oFn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT" ).exists()
    }


//    void testFindResourceFileFromURI () {
////        println new URI( "onts/in/ClinicalDomainT.ofn" )
////        println new URI( "ClinicalDomainT.ofn" )
////        println new URI( "/ClinicalDomainT.ofn" )
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.ofn" ) ).exists()
//        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "onts/in/ClinicalDomainT.ofn" ) )}
//        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "file:" + "/onts/in/ClinicalDomainT.ofn" ) )
//                .exists()
//        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "ClinicalDomainT.ofn" ) )}
//        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isAbsolute()
//        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isOpaque()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.ofn" ) ).exists()
//        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "ClinicalDomainT.ofn" ) ).exists()
//        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "/ClinicalDomainT.ofn" ) ).exists()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.OFN" ) ).exists()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/CLINICALDOMAINT.ofn" ) ).exists()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "ONTS/IN/ClinicalDomainT.OFN" ) ).exists()
//
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/clinicaldomaint.ofn" ) ).exists()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.oFN" ) ).exists()
//        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/cLINICALdOMAINt.oFn" ) ).exists()
//        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT" ) ).exists()
//
//    }

    void testFindResource2 () {

//        String path = "/onts/in/ClinicalDomainT.ofn";
//        File f = TestFileUtil.getFileForTestOutput(path)

        /* The Java default for getting resources appears to be case-insensitive */

        ClassLoader ld = getClass().getClassLoader();
//        ld = System.getClassLoader();
        println ld;
        assertNotNull( getClass().getResource( "/onts/in/ClinicalDomainT.ofn" ) );
        assertNull( getClass().getResource( "onts/in/ClinicalDomainT.ofn" ) );

        assertNull getClass().getResource( "onts/in/ClinicalDomainT.ofn" )
        assertNotNull getClass().getResource( "/onts/in/ClinicalDomainT.ofn" )
        assertNull getClass().getResource( "ClinicalDomainT.ofn" )
        assertNull getClass().getResource( "/ClinicalDomainT.ofn" )
        assertNull getClass().getResource( "onts/in/ClinicalDomainT.OFN" )
        assertNotNull getClass().getResource( "/onts/in/CLINICALDOMAINT.ofn" )
        assertNull getClass().getResource( "ONTS/IN/ClinicalDomainT.OFN" )

        assertNotNull getClass().getResource( "/onts/in/clinicaldomaint.ofn" )
        assertNotNull getClass().getResource( "/onts/in/ClinicalDomainT.oFN" )
        assertNotNull getClass().getResource( "/onts/in/cLINICALdOMAINt.oFn" )
        assertNull getClass().getResource( "onts/in/ClinicalDomainT" )
    }

    void testFindInAnyTargetResourcesDir(){
        final String resourcePath = "/onts/in/icd9-pub.ofn"

        URL url;
        assert System.class.getResource(resourcePath)
        assert FileUtil.class.getResource(resourcePath)
        assert TestFileUtil.class.getResource(resourcePath)
        assert getClass().getResource(resourcePath)

        File pubCodesFile = FileUtil.getExistingResourceAsFile( resourcePath );

//        println "pubCodesFile = " + pubCodesFile;
        assert pubCodesFile.exists();
    }
}
