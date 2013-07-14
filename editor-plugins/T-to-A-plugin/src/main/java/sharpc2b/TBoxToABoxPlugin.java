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
import edu.asu.sharpc2b.transform.IriUtil;
import edu.asu.sharpc2b.transform.TBoxToABox;

import java.io.File;
import java.io.IOException;

/**
 * Goal
 *
 * @Mojo( name = "generate-tbox-to-abox" )
 * @goal generate-tbox-to-abox
 * @phase generate-sources
 * @requires DependencyResolution compile
 */
public class TBoxToABoxPlugin
        extends AbstractMojo
{

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * Location in the classpath to find the properties file containing entity IRIs to use in the output
     * A-Box ontology.
     *
     * For example, a couple of example entries are shown here, which means that for any OWL Class in the
     * input ontology, in the output ontology it would be represented as an OWL Individual, who's rdf:type
     * is the OWL Class with IRI "http://asu.edu/sharpc2b/ops#DomainClass", and if it had a rdfs:subClassOf
     * relationship to OWL Class Foo, the output ontology would have an object property assertion added,
     * pointing to the OWL Individual for Foo, with a predicate of "skos:subConceptOf".
     *
     * http\://www.w3.org/2002/07/owl#Class = http\://asu.edu/sharpc2b/ops#DomainClass
     * http\://www.w3.org/2000/01/rdf-schema#subClassOf = http\://asu.edu/sharpc2b/skos-ext#subConceptOf
     *
     * Note: in the properties file, the ':' must be quoted with a '\' character, otherwise it will be
     * interpreted as a delimiter (like '=').
     *
     * @parameter default-value="./OWL-to-Sharp-ABox-Concepts.properties"
     */
    private String tToAConfigResourcePath;

    /**
     * The input T-Box domain model ontology to convert into an A-Box model.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File inputOntologyFile;

    /**
     * File in which to save the output A-Box domain model Ontology.
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

    public File getAlternateResourceDir ()
    {
        return alternateResourceDir;
    }

    public void execute ()
            throws MojoExecutionException, MojoFailureException
    {
        TBoxToABox converter;

        OWLOntologyManager oom;
        PrefixOWLOntologyFormat oFormat;

        OWLOntology ontT;
        OWLOntology ontA;

        System.out.println( "gettToAConfigResourcePath() = '" + gettToAConfigResourcePath() + "'" );
        System.out.println( "getInputOntologyFile() = '" + getInputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyFile() = '" + getOutputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyIriString() = '" + getOutputOntologyIriString() + "'" );
        System.out.println( "getInputOntologyFile().exists() = '" + getInputOntologyFile().exists() + "'" );
//        System.out.println( "gettToAConfigResourcePath.exists() = '" + new File( gettToAConfigResourcePath()).exists() + "'" );
        System.out.println( "getAlternateResourceDir() = '" + getAlternateResourceDir() + "'" );

        oom = OWLManager.createOWLOntologyManager();

//        if (false)
//        {
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

        try
        {
            oom.saveOntology( ontA, oFormat, IRI.create( getOutputOntologyFile() ) );
        }
        catch (OWLOntologyStorageException e)
        {
            e.printStackTrace();
            throw new MojoFailureException(
                    "Failed to save output ontology to File = " + getOutputOntologyFile(), e );
        }
//        }
    }

}


