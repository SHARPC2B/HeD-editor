package sharpc2b;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.OWLDataUtil;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import sharpc2b.transform.IriUtil;
import sharpc2b.transform.OwlUtil;
import sharpc2b.transform.SkosABoxToTBox;

import java.io.File;

/**
 * Moved to Module skos-to-T-Plugin
 */

///**
// * Goal
// *
// * @goal generate-skos-abox-to-tbox
// * @phase generate-sources
// * @requiresDependencyResolution compile
// */

class SkosABoxToTBoxPlugin
        extends AbstractMojo
{

//    static File inputOntFile = TestFileUtil.getFileInTestResourceDir( "ontologies/in/ClinicalDomainT.ofn" );
//    static IRI outputOntIRI = TestUtil.testIRI( "ClinicalDomainA" );
//    static File outputOntFile = TestFileUtil.getFileInTestResourceDir( "ontologies/out/ClinicalDomainInsts8.ofn" );
//    static File outputSkosOntFile = TestFileUtil.getFileInTestResourceDir( "ontologies/out/SkosClinicalDomainInsts8" +
//                                                                                   ".ofn" );

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * @parameter default-value="./target/generated-sources"
     */
    private File inputOntologyFile;

    /**
     * @parameter default-value="./target/generated-sources"
     */
    private File outputOntologyFile;

    /**
     * @parameter default-value="./target/generated-sources"
     */
    private String outputOntologyIriString;

    //=====================================================================================================
    // Maven parameter Getters and Setters
    //=====================================================================================================

    public File getInputOntologyFile ()
    {
        return inputOntologyFile;
    }

    public void setInputOntologyFile (final File inputOntologyFile)
    {
        this.inputOntologyFile = inputOntologyFile;
    }

    public File getOutputOntologyFile ()
    {
        return outputOntologyFile;
    }

    public void setOutputOntologyFile (final File outputOntologyFile)
    {
        this.outputOntologyFile = outputOntologyFile;
    }

    public String getOutputOntologyIriString ()
    {
        return outputOntologyIriString;
    }

    public void setOutputOntologyIriString (final String outputOntologyIriString)
    {
        this.outputOntologyIriString = outputOntologyIriString;
    }

    //=====================================================================================================

    public void execute ()
            throws MojoExecutionException, MojoFailureException
    {
        SkosABoxToTBox converter;

        OWLOntologyManager oom;
        PrefixOWLOntologyFormat oFormat;

        OWLOntology ontT;
        OWLOntology ontA;

        System.out.println( "getInputOntologyFile() = '" + getInputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyFile() = '" + getOutputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyIriString() = '" + getOutputOntologyIriString() + "'" );

//        oom = OWLManager.createOWLOntologyManager();
        oom = OwlUtil.createSharpOWLOntologyManager();

        try
        {
            ontA = oom.loadOntologyFromOntologyDocument( getInputOntologyFile() );
        }
        catch (OWLOntologyCreationException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to load input ontology", e );
        }
        try
        {
            ontT = oom.createOntology( IRI.create( getOutputOntologyIriString() ) );
        }
        catch (OWLOntologyCreationException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to create output ontology", e );
        }

        oFormat = IriUtil.getDefaultSharpOntologyFormat();

        oFormat.setPrefix( "a:", ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );

        converter = new SkosABoxToTBox();
//            throw new MojoFailureException( "Failed to create TBoxToABox converter instance.", e );

        converter.addTBoxAxioms( ontA, ontT );

        try
        {
            oom.saveOntology( ontT, oFormat, IRI.create( getOutputOntologyFile() ) );
        }
        catch (OWLOntologyStorageException e)
        {
            e.printStackTrace();
            throw new MojoFailureException(
                    "Failed to save output ontology to File = " + getOutputOntologyFile(), e );
        }
    }

}
