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
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import sharpc2b.transform.IriUtil;
import sharpc2b.transform.OwlUtil;
import sharpc2b.transform.SharpOperators;

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
     * @parameter default-value="./target/generated-sources"
     */
    private File outputOntologyFile;

    /**
     * @parameter default-value="./target/generated-sources"
     */
    private String outputOntologyIriString;

    /**
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
        SharpOperators converter;

        OWLOntologyManager oom;
        PrefixOWLOntologyFormat oFormat;

//        OWLOntology ontT;
        OWLOntology operatorOntology;

        System.out.println( "getOperatorDefinitionFile() = '" + getOperatorDefinitionFile() + "'" );
        System.out.println( "getOutputOntologyFile() = '" + getOutputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyIriString() = '" + getOutputOntologyIriString() + "'" );
        System.out.println(
                "getOperatorDefinitionFile().exists() = '" + getOperatorDefinitionFile().exists() + "'" );
        System.out.println( "getAlternateResourceDir() = '" + getAlternateResourceDir() + "'" );

//        oom = OWLManager.createOWLOntologyManager();
        oom = OwlUtil.createSharpOWLOntologyManager();

//        if (false)
//        {
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
