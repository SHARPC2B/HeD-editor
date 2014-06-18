package edu.asu.bmi.sharpc2b;

import edu.asu.sharpc2b.transform.TemplateGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import java.io.File;
import java.io.IOException;

/**
 * Goal
 *
 * @Mojo( name = "generate-templates" )
 * @goal generate-templates
 * @phase generate-sources
 * @requires DependencyResolution compile
 */
public class TemplateParserPlugin
        extends AbstractMojo
{

    //=====================================================================================================
    // Maven parameters
    //=====================================================================================================

    /**
     * The Spreadsheet containing the template definitions to be parsed
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File inputTemplateFile;

    /**
     * File in which to save the output template A-box
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


    //=====================================================================================================
    // Maven parameter Getters and Setters
    //=====================================================================================================


    public File getInputTemplateFile() {
        return inputTemplateFile;
    }

    public void setInputTemplateFile( File inputTemplateFile ) {
        this.inputTemplateFile = inputTemplateFile;
    }

    public File getOutputOntologyFile() {
        return outputOntologyFile;
    }

    public void setOutputOntologyFile( File outputOntologyFile ) {
        this.outputOntologyFile = outputOntologyFile;
    }

    public String getOutputOntologyIriString() {
        return outputOntologyIriString;
    }

    public void setOutputOntologyIriString( String outputOntologyIriString ) {
        this.outputOntologyIriString = outputOntologyIriString;
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        TemplateGenerator converter;

        OWLOntologyManager oom;
        PrefixOWLOntologyFormat oFormat;

        OWLOntology ontT;

        oom = OWLManager.createOWLOntologyManager();

        try {
            ontT = oom.createOntology( IRI.create( getOutputOntologyIriString() ) );
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to create output ontology", e );
        }

        oFormat = new OWLFunctionalSyntaxOntologyFormat();

        oFormat.setPrefix( "templates:", ontT.getOntologyID().getOntologyIRI().toString() + "#" );

        try {
            converter = new TemplateGenerator( getOutputOntologyIriString(), getInputTemplateFile() );
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to generate templates.", e );
        } catch ( InvalidFormatException e ) {
            e.printStackTrace();
            throw new MojoFailureException( "Failed to generate templates.", e );
        }

        try {
            ontT = converter.createTemplateOntology();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( OWLOntologyCreationException e ) {
            e.printStackTrace();
        }
        try {
            oom.saveOntology( ontT, oFormat, IRI.create( getOutputOntologyFile() ) );
        }
        catch ( OWLOntologyStorageException e ) {
            e.printStackTrace();
            throw new MojoFailureException(
                    "Failed to save output ontology to File = " + getOutputOntologyFile(), e );
        }
    }

}


