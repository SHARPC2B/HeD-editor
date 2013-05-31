package sharpc2b;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import sharpc2b.transform.IriUtil;
import sharpc2b.transform.TBoxToABox;

import java.io.File;
import java.io.IOException;

/**
 * Goal
 *
 * @goal generate-tbox-to-abox
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class TBoxToABoxPlugin
        extends AbstractMojo
{

//    static File inputOntFile = TestFileUtil.getFileInTestResourceDir( "onts/in/ClinicalDomainT.ofn" );
//    static IRI outputOntIRI = TestUtil.testIRI( "ClinicalDomainA" );
//    static File outputOntFile = TestFileUtil.getFileInTestResourceDir( "onts/out/ClinicalDomainInsts8.ofn" );
//    static File outputSkosOntFile = TestFileUtil.getFileInTestResourceDir( "onts/out/SkosClinicalDomainInsts8" +
//                                                                                   ".ofn" );

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * Location in the classpath to find properties file containing entity IRIs to use in the output A-Box
     * ontology.
     *
     * @parameter default-value="./OWL-to-Sharp-ABox-Concepts.properties"
     */
    private String tToAConfigResourcePath;

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

    public String gettToAConfigResourcePath ()
    {
        return tToAConfigResourcePath;
    }

    public void settToAConfigResourcePath (final String tToAConfigResourcePath)
    {
        this.tToAConfigResourcePath = tToAConfigResourcePath;
    }

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

    public void execute ()
            throws MojoExecutionException, MojoFailureException
    {
        TBoxToABox converter;

        OWLOntologyManager oom;
        PrefixOWLOntologyFormat oFormat;

        OWLOntology ontT;
        OWLOntology ontA;

        oom = OWLManager.createOWLOntologyManager();

        try
        {
            ontT = oom.loadOntologyFromOntologyDocument( getInputOntologyFile() );
        }
        catch (OWLOntologyCreationException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to load input ontology", e );
        }
        try
        {
            ontA = oom.createOntology( IRI.create( getOutputOntologyIriString() ) );
        }
        catch (OWLOntologyCreationException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to create output ontology", e );
        }

        oFormat = IriUtil.getDefaultSharpOntologyFormat();

        oFormat.setPrefix( "a:", ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );

        try
        {
            converter = new TBoxToABox( gettToAConfigResourcePath() );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to create TBoxToABox converter instance.", e );
        }

        converter.addABoxAxioms( ontT, ontA );
    }

}


