import edu.asu.sharpc2b.TemplateInstantiator;
import edu.asu.sharpc2b.TemplateInstantiatorFactory;
import edu.asu.sharpc2b.TemplateStoreImpl;
import edu.asu.sharpc2b.hed.impl.DomainHierarchyExplorer;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.templates.Parameter;
import edu.asu.sharpc2b.templates.Template;
import org.junit.Test;

import java.util.Map;


public class TestSharpGeneration {

    private static final String DOMAIN_MODEL_PATH = "ontologies/domain_models/domain-vmr.ofn";
    private static final String DOMAIN_NS = "urn:hl7-org:vmr:r2#";

    private DomainHierarchyExplorer explorer = DomainHierarchyExplorer.getInstance( DOMAIN_MODEL_PATH, DOMAIN_NS );
    private TemplateStoreImpl store = TemplateStoreImpl.getInstance( explorer );

    @Test
    public void testTriggerTemplates() {
        System.out.println( store.getTemplateIds( null ) );
        Template trig = store.getTemplateInfo( "PatientInPredischargeStatus" );

        trig.addCategory( "TRIGGER" );
        for ( Parameter parm : trig.getHasParameter() ) {
            if ( parm.getName().contains( "observationFocus" ) ) {
                parm.addValue( "codeSystem=TBD;code=PREDISCHARGE;displayValue=Patient About to Be Discharged" );
            }
        }

        Map<String, SharpExpression> out = TemplateInstantiatorFactory.newTemplateInstantiator().instantiateExpression( "test", trig );

        System.out.println( out );
    }

    @Test
    public void testPatientCondition() {
        System.out.println( store.getTemplateIds( null ) );
        Template cond = store.getTemplateInfo( "PatientAge" );

        cond.addCategory( "CONDITION" );
        for ( Parameter parm : cond.getHasParameter() ) {
            if ( parm.getName().contains( "age" ) ) {
                parm.addValue( "operation=GreaterOrEqual;value=22;unit=years" );
            }
        }

        Map<String, SharpExpression> out = TemplateInstantiatorFactory.newTemplateInstantiator().instantiateExpression( "test", cond );

        System.out.println( out );
    }

    @Test
    public void testProblemConditions() {
        System.out.println( store.getTemplateIds( null ) );
        Template cond = store.getTemplateInfo( "ProblemDiagnosis" );

        cond.addCategory( "CONDITION" );
        for ( Parameter parm : cond.getHasParameter() ) {
            if ( parm.getName().contains( "diagnosticEventTime" ) ) {
                parm.addValue( "operation=Equal;value=-30;unit=days;" );
            }
            if ( parm.getName().contains( "problemEffectiveTime" ) ) {
                parm.addValue( "operation=Equal;value=0;unit=days" );
            }
            if ( parm.getName().contains( "problemStatus" ) ) {
                parm.addValue( "operation=Equal;" );
            }
            if ( parm.getName().contains( "problemCode" ) ) {
                parm.addValue( "operation=Equal;displayValue=Acute infection of bone;code=409779000;codeSystem=SNOMED" );
            }
        }

        Map<String, SharpExpression> out = TemplateInstantiatorFactory.newTemplateInstantiator().instantiateExpression( "test", cond );

        System.out.println( out );
    }

}
