package sharpc2b;

import edu.asu.sharpc2b.transform.BlocklyGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import edu.asu.sharpc2b.transform.IriUtil;
import edu.asu.sharpc2b.transform.OwlUtil;
import edu.asu.sharpc2b.transform.SharpOperators;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

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
     * Location in the classpath to find Excel file containing Operator definitions.
     *
     * @parameter default-value="./target/generated-sources"
     */
    private String hedSourceOntologyPath;

    /**
     * File in which to save the output OWL Ontology containing Operator and Expression definitions..
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File outputOntologyFile;

    /**
     * File in which to save the Blockly configuration files
     *
     * @parameter default-value="./target/generated-sources"
     */
    private File outputBlocklyDir;

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

    /**
     * The plugin can pre-generate the spreadsheet containing the operators (SPREADSHEET)
     * OR
     * can generate the operator ontology (ONTOLOGY)
     * The latter is the default;
     */
    private String generationTarget;

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

    public File getOutputBlocklyDir() {
        return outputBlocklyDir;
    }

    public void setOutputBlocklyDir( File outputBlocklyDir ) {
        this.outputBlocklyDir = outputBlocklyDir;
    }

    public String getHedSourceOntologyPath() {
        return hedSourceOntologyPath;
    }

    public void setHedSourceOntologyPath( String hedSourceOntologyPath ) {
        this.hedSourceOntologyPath = hedSourceOntologyPath;
    }

    public String getGenerationTarget() {
        return generationTarget;
    }

    public void setGenerationTarget( String generationTarget ) {
        this.generationTarget = generationTarget;
    }

    public void setAlternateResourceDir( File alternateResourceDir ) {
        this.alternateResourceDir = alternateResourceDir;
    }

    public void execute ()
            throws MojoExecutionException, MojoFailureException
    {
        final SharpOperators converter;

        final OWLOntologyManager oom;
        final PrefixOWLOntologyFormat oFormat;

        final OWLOntology operatorOntology;
        OWLOntology hedOntology = null;

        System.out.println( "getOperatorDefinitionFile() = '" + getOperatorDefinitionFile() + "'" );
        System.out.println( "getOutputOntologyFile() = '" + getOutputOntologyFile() + "'" );
        System.out.println( "getOutputOntologyIriString() = '" + getOutputOntologyIriString() + "'" );
        System.out.println(
                "getOperatorDefinitionFile().exists() = '" + getOperatorDefinitionFile().exists() + "'" );
        System.out.println( "getAlternateResourceDir() = '" + getAlternateResourceDir() + "'" );

        oom = OwlUtil.createSharpOWLOntologyManager();

        InputStream stream0 = SharpOperators.class.getResourceAsStream( "/ontologies/editor_models/skos-core.owl" );
        InputStream stream1 = SharpOperators.class.getResourceAsStream( "/ontologies/editor_models/skos-ext.owl" );
        InputStream stream2 = SharpOperators.class.getResourceAsStream( "/ontologies/editor_models/expr-core.owl" );

        OWLOntologyDocumentSource s0 = new StreamDocumentSource( stream0 );
        OWLOntologyDocumentSource s1 = new StreamDocumentSource( stream1 );
        OWLOntologyDocumentSource s2 = new StreamDocumentSource( stream2 );
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setMissingOntologyHeaderStrategy(
                OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy.IMPORT_GRAPH );

        OWLOntologyDocumentSource hed = new FileDocumentSource( new File( getHedSourceOntologyPath() ) );
        try {
            hedOntology = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument( hed );
        } catch ( OWLOntologyCreationException e ) {
            e.printStackTrace();
        }

        try {
            oom.loadOntologyFromOntologyDocument( s0, config );
            oom.loadOntologyFromOntologyDocument( s1, config );
            oom.loadOntologyFromOntologyDocument( s2, config );
            operatorOntology = oom.createOntology( IRI.create( getOutputOntologyIriString() ) );
        } catch ( OWLOntologyCreationException oce ) {
            oce.printStackTrace();
            return;
        }


        oFormat = IriUtil.getDefaultSharpOntologyFormat();
        oFormat.setPrefix( "a:", operatorOntology.getOntologyID().getOntologyIRI().toString() + "#" );

        converter = new SharpOperators( );
        converter.addSharpOperators( operatorDefinitionFile, hedOntology, operatorOntology, getGenerationTarget() );


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


        BlocklyGenerator blockly = new BlocklyGenerator();
        blockly.processOperators( outputBlocklyDir, operatorOntology );
    }





    /*
        For testing purposes only. The real invocation is done through maven configuration, which uses
        paths relative to the build directory.
     */
    public static void main( String... args ) throws MojoFailureException, MojoExecutionException, URISyntaxException {
        DefineOperatorsPlugin def = new DefineOperatorsPlugin();

        def.setHedSourceOntologyPath( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/generated-models/target/generated-sources/knowledgedocument.xsd.ttl" );
        def.setOperatorDefinitionFile( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/generated-models/src/main/resources/SharpOperators.xlsx" ) );
        def.setOutputOntologyIriString( "http://asu.edu/sharpc2b/ops-set" );

        def.setOutputBlocklyDir( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/generated-models/target/generated-sources/blockly" ) );
        def.setOutputOntologyFile( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/generated-models/target/generated-sources/ontologies/editor_models/sharp_operators.ofn" ) );

        def.setGenerationTarget( "ONTOLOGY" );

        def.execute();
    }

}
