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
import edu.asu.sharpc2b.transform.IriUtil;
import edu.asu.sharpc2b.transform.OwlUtil;
import edu.asu.sharpc2b.transform.SharpOperators;

import java.io.File;

/**
 * Goal
 *
 * @Mojo( name = "define-operators" )
 * @goal define-operators
 * @phase generate-sources
 * @requires DependencyResolution compile
 */
public class DefineOperatorsPlugin
        extends AbstractMojo
{

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * Location in the classpath to find Excel file containing Operator definitions.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File operatorDefinitionFile;

    /**
     * File in which to save the output OWL Ontology containing Operator and Expression definitions..
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
     * The underlying classes were written to look for resource files (such as OWL files) in the classpath.
     * This is an alternate file system directory under which to search for resource files.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File alternateResourceDir;

    //=====================================================================================================
    // Maven parameter Getters and Setters
    //=====================================================================================================

    public File getOperatorDefinitionFile ()
    {
        return operatorDefinitionFile;
    }

    public void setOperatorDefinitionFile (final File operatorDefinitionFile)
    {
        this.operatorDefinitionFile = operatorDefinitionFile;
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

    public File getAlternateResourceDir ()
    {
        return alternateResourceDir;
    }

    public void execute ()
            throws MojoExecutionException, MojoFailureException
    {
        final SharpOperators converter;

        final OWLOntologyManager oom;
        final PrefixOWLOntologyFormat oFormat;

//        OWLOntology ontT;
        final OWLOntology operatorOntology;

        System.out.println( "getOperatorDefinitionFile() = '" + getOperatorDefinitionFile() + "'" );
        System.out.println( "getOutputOntologyFile() = '" + getOutputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyIriString() = '" + getOutputOntologyIriString() + "'" );
        System.out.println(
                "getOperatorDefinitionFile().exists() = '" + getOperatorDefinitionFile().exists() + "'" );
        System.out.println( "getAlternateResourceDir() = '" + getAlternateResourceDir() + "'" );

        oom = OwlUtil.createSharpOWLOntologyManager();

        try
        {
            operatorOntology = oom.createOntology( IRI.create( getOutputOntologyIriString() ) );
        }
        catch (OWLOntologyCreationException e)
        {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to create output ontology", e );
        }

        oFormat = IriUtil.getDefaultSharpOntologyFormat();

        oFormat.setPrefix( "a:", operatorOntology.getOntologyID().getOntologyIRI().toString() + "#" );

        converter = new SharpOperators();

        converter.addSharpOperators( operatorDefinitionFile, operatorOntology );

        try
        {
            oom.saveOntology( operatorOntology, oFormat, IRI.create( getOutputOntologyFile() ) );
        }
        catch (OWLOntologyStorageException e)
        {
            e.printStackTrace();
            throw new MojoFailureException(
                    "Failed to save output ontology to File = " + getOutputOntologyFile(), e );
        }
    }
}
