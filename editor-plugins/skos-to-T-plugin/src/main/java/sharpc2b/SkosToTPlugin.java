package sharpc2b;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import edu.asu.sharpc2b.transform.FileUtil;
import edu.asu.sharpc2b.transform.IriUtil;
import edu.asu.sharpc2b.transform.OwlUtil;
import edu.asu.sharpc2b.transform.SkosABoxToTBox;

import java.io.File;

/**
 * Goal
 *
 * @goal generate-skos-abox-to-tbox
 * @phase generate-sources
 * @requiresDependencyResolution compile
 */
public class SkosToTPlugin
        extends AbstractMojo
{

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * The input SKOS A-Box ontology file, with a coding hierarchy, where each code is represented as an OWL
     * Individual that is an instance of skos:Concept, related to other individuals via skos:broader or
     * skos:broaderTransitive, the code values are represented using skos:notation, and a friendly name of
     * the concept is indicated using skos:prefLabel.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File inputOntologyFile;

    /**
     * The File where the output T-Box ontology will be saved.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File outputOntologyFile;

    /**
     * The IRI to give the saved output Ontology.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private String outputOntologyIriString;

    /**
     * The underlying classes were written to look for resource files (such as OWL files) in the
     * classpath.  This is an alternate file system directory under which to search for resource files.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File alternateResourceDir;

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

    public File getAlternateResourceDir ()
    {
        return alternateResourceDir;
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
        System.out.println( "getAlternateResourceDir() = '" + getAlternateResourceDir() + "'" );

        if (getAlternateResourceDir() != null)
        {
            FileUtil.alternateResourceRootFolder = getAlternateResourceDir();
        }
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

        /*
         * Defines serialization format and namespace prefix abbreviations
         */
        oFormat = IriUtil.getDefaultSharpOntologyFormat();

        oFormat.setPrefix( "a:", ontA.getOntologyID().getOntologyIRI().toString() + "#" );
        oFormat.setPrefix( "t:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );

        converter = new SkosABoxToTBox();

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
