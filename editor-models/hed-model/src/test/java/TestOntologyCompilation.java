import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.semantics.builder.DLFactory;
import org.drools.semantics.builder.DLFactoryImpl;
import org.drools.semantics.builder.model.Concept;
import org.drools.semantics.builder.model.ModelFactory;
import org.drools.semantics.builder.model.OntoModel;
import org.drools.semantics.builder.model.PropertyRelation;
import org.drools.semantics.builder.model.SemanticXSDModel;
import org.drools.semantics.builder.model.compilers.ModelCompiler;
import org.drools.semantics.builder.model.compilers.ModelCompilerFactory;
import org.drools.semantics.builder.model.compilers.SemanticXSDModelCompiler;
import org.drools.semantics.builder.model.compilers.XSDModelCompiler;
import org.drools.shapes.OntoModelCompiler;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import java.io.InputStream;
import java.util.Collections;

public class TestOntologyCompilation {


    @Test
    public void testDUL() {
        DLFactory factory = DLFactoryImpl.getInstance();
        InputStream source = TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/DUL.owl" );

        OntoModel results = factory.buildModel( "DUL",
                                                ResourceFactory.newInputStreamResource( source ),
                                                OntoModel.Mode.OPTIMIZED,
                                                OntoModelCompiler.AXIOM_INFERENCE.NONE.getGenerators() );
    }

    @Test
//    @Ignore("Takes too much time")
    public void testSkosLMMIntegration() {
        DLFactory factory = DLFactoryImpl.getInstance();


        OntoModel results = factory.buildModel( "HeD",
                new Resource[] {
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/DUL.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/IOLite.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/LMM_L1.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/skos-core.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/skos-ext.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/skos_lmm.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/dc_owl2dl.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/dc2dul.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/metadata.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/expr-core.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/prr-core.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/sharp-time.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/sharp_operators.ofn" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/event.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/actions.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/prr-sharp.owl" ) ),
                    ResourceFactory.newInputStreamResource( TestOntologyCompilation.class.getResourceAsStream( "/ontologies/editor_models/sharp.owl" ) )
                },
                OntoModel.Mode.OPTIMIZED,
                OntoModelCompiler.AXIOM_INFERENCE.NONE.getGenerators() );

        System.out.println( results );

        SemanticXSDModelCompiler xcompiler = (SemanticXSDModelCompiler) ModelCompilerFactory.newModelCompiler( ModelFactory.CompileTarget.XSDX );
        SemanticXSDModel xmlModel = (SemanticXSDModel) xcompiler.compile( results );

        xmlModel.streamAll( System.err );

        Concept con = results.getConcept( IRI.create( "http://asu.edu/sharpc2b/metadata#DataModel" ).toQuotedString() );
        System.out.println( con.getEffectiveProperties() );



    }

}
