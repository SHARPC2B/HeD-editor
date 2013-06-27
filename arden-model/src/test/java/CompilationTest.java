import junit.framework.Assert;
//import org.drools.io.ResourceFactory;
//import org.drools.semantics.builder.DLFactoryBuilder;
//import org.drools.semantics.builder.model.OntoModel;
//import org.drools.shapes.OntoModelCompiler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;

import static junit.framework.Assert.assertFalse;

public class CompilationTest {


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Test
    public void testArdenOntology() {

//        OntoModel arden = DLFactoryBuilder.newDLFactoryInstance().buildModel( "arden",
//                ResourceFactory.newClassPathResource( "ontologies/arden.owl" ),
//                OntoModel.Mode.OPTIMIZED );
//        Assert.assertNotNull( arden );
//
//        OntoModelCompiler compiler = new OntoModelCompiler( arden, folder.getRoot() );
//
//        List<Diagnostic<? extends JavaFileObject>> diags = compiler.compileOnTheFly( OntoModelCompiler.minimalOptions, OntoModelCompiler.MOJO_VARIANTS.JPA2 );
//        for ( Diagnostic dx : diags ) {
//            assertFalse( dx.getKind() == Diagnostic.Kind.ERROR );
//        }

    }

}
