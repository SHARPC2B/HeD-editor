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
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import java.util.Collections;

public class TestOntologyCompilation {


    @Test
    public void testDUL() {
        DLFactory factory = DLFactoryImpl.getInstance();

        OntoModel results = factory.buildModel( "diamond",
                ResourceFactory.newClassPathResource( "ontologies/DUL.owl" ),
                OntoModel.Mode.OPTIMIZED,
                OntoModelCompiler.AXIOM_INFERENCE.NONE.getGenerators() );
    }

    @Test
    public void testSkosLMMIntegration() {
        DLFactory factory = DLFactoryImpl.getInstance();


        OntoModel results = factory.buildModel( "HeD",
                new Resource[] {
                    ResourceFactory.newClassPathResource( "ontologies/DUL.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/IOLite.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/LMM_L1.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/skos-core.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/skos-ext.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/skos_lmm.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/dc_owl2dl.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/dc2dul.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/metadata.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/expr-core.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/actions.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/events.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/domain.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/prr-core.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/prr-sharp.owl" ),
                    ResourceFactory.newClassPathResource( "ontologies/sharp.owl" ),
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
