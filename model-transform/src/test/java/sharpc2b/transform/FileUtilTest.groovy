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

        assertEquals true, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "/ClinicalDomainT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.OFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/CLINICALDOMAINT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "ONTS/IN/ClinicalDomainT.OFN" ).exists()

        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/clinicaldomaint.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.oFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/cLINICALdOMAINt.oFn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT" ).exists()
    }


    void testFindResourceFileFromURI () {
//        println new URI( "onts/in/ClinicalDomainT.ofn" )
//        println new URI( "ClinicalDomainT.ofn" )
//        println new URI( "/ClinicalDomainT.ofn" )
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.ofn" ) ).exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "onts/in/ClinicalDomainT.ofn" ) )}
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "file:" + "/onts/in/ClinicalDomainT.ofn" ) )
                .exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "ClinicalDomainT.ofn" ) )}
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isAbsolute()
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isOpaque()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "/ClinicalDomainT.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.OFN" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/CLINICALDOMAINT.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "ONTS/IN/ClinicalDomainT.OFN" ) ).exists()

        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/clinicaldomaint.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.oFN" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/cLINICALdOMAINt.oFn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT" ) ).exists()

    }

    void testFindResource2 () {

//        String path = "/onts/in/ClinicalDomainT.ofn";
//        File f = TestFileUtil.getFileForTestOutput(path)

        /* The Java default for getting resources appears to be case-insensitive */

        assertNotNull( FileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.ofn" ) );

        assertEquals true, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "ClinicalDomainT.ofn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "/ClinicalDomainT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT.OFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/CLINICALDOMAINT.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "ONTS/IN/ClinicalDomainT.OFN" ).exists()

        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/clinicaldomaint.ofn" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/ClinicalDomainT.oFN" ).exists()
        assertEquals true, TestFileUtil.getResourceAsFile( "/onts/in/cLINICALdOMAINt.oFn" ).exists()
        assertEquals false, TestFileUtil.getResourceAsFile( "onts/in/ClinicalDomainT" ).exists()
    }


    void testFindResourceFileFromURI2 () {
//        println new URI( "onts/in/ClinicalDomainT.ofn" )
//        println new URI( "ClinicalDomainT.ofn" )
//        println new URI( "/ClinicalDomainT.ofn" )
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.ofn" ) ).exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "onts/in/ClinicalDomainT.ofn" ) )}
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "file:" + "/onts/in/ClinicalDomainT.ofn" ) )
                .exists()
        shouldFail {FileUtil.getTestResourceFile( new URI( "file:" + "ClinicalDomainT.ofn" ) )}
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isAbsolute()
        assertEquals false, new URI(  "/onts/in/ClinicalDomainT.ofn" ).isOpaque()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "ClinicalDomainT.ofn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "/ClinicalDomainT.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT.OFN" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/CLINICALDOMAINT.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "ONTS/IN/ClinicalDomainT.OFN" ) ).exists()

        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/clinicaldomaint.ofn" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI(  "/onts/in/ClinicalDomainT.oFN" ) ).exists()
        assertEquals true, TestFileUtil.getTestResourceFile( new URI( "/onts/in/cLINICALdOMAINt.oFn" ) ).exists()
        assertEquals false, TestFileUtil.getTestResourceFile( new URI( "onts/in/ClinicalDomainT" ) ).exists()

    }

    void testFindInAnyTargetResourcesDir(){
        final String resourcePath = "/onts/in/icd9-pub.ofnxxx"

        URL url;
        println System.class.getResource(resourcePath)
        println FileUtil.class.getResource(resourcePath)
        println TestFileUtil.class.getResource(resourcePath)
        println getClass().getResource(resourcePath)

        File pubCodesFile = FileUtil.getExistingResourceAsFile( resourcePath );

        println "pubCodesFile = "+pubCodesFile;
        assert pubCodesFile.exists();
    }
}
