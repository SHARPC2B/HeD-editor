import com.clarkparsia.empire.Empire;
import com.clarkparsia.empire.EmpireOptions;
import com.clarkparsia.empire.config.ConfigKeys;
import com.clarkparsia.empire.config.EmpireConfiguration;
import com.clarkparsia.empire.sesametwo.OpenRdfEmpireModule;
import com.clarkparsia.empire.sesametwo.RepositoryDataSourceFactory;
//import edu.asu.sharpc2b.prr.ExecutableRuleSet;
//import edu.asu.sharpc2b.prr.ExecutableRuleSetImpl;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.asu.sharpc2b.hed.impl.ModelManagerOwlAPIHermit;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

public class ModelManagerTest {

//    @Test
//    @Ignore
//    public void testOntologyLoad() throws Exception {
//
//        ModelManagerOwlAPIHermit mm = new ModelManagerOwlAPIHermit();
//
//        mm.loadModel();
//
//    }


    @Test
    @Ignore
    public void testPesx() throws Exception {

        EditorCore core = EditorCoreImpl.getInstance();

        core.createArtifact();
        System.out.println( core.getAvailableArtifacts() );
    }

    @Test
    @Ignore
    public void testEmpireResourceAccess() {
//        URL res = ModelManagerTest.class.getResource( "META-INF/empire.configuration.file" );
//        System.setProperty( "empire.configuration.file", res.getPath() );
        OpenRdfEmpireModule mod = new OpenRdfEmpireModule();
        EmpireConfiguration ec = new EmpireConfiguration();
        Empire.init( ec, mod );
        EmpireOptions.STRICT_MODE = false;

        PersistenceProvider aProvider = Empire.get().persistenceProvider();

        EntityManagerFactory emf = aProvider.createEntityManagerFactory(
                "sharp",
                getTestEMConfigMap() );
        EntityManager em = emf.createEntityManager();

        assertTrue( em.isOpen() );


    }

    @Test
    @Ignore
        public void testEmpireResourceRefresh() {
    //        URL res = ModelManagerTest.class.getResource( "META-INF/empire.configuration.file" );
    //        System.setProperty( "empire.configuration.file", res.getPath() );
            OpenRdfEmpireModule mod = new OpenRdfEmpireModule();
            EmpireConfiguration ec = new EmpireConfiguration();
            Empire.init( ec, mod );
            EmpireOptions.STRICT_MODE = false;

            PersistenceProvider aProvider = Empire.get().persistenceProvider();

            EntityManagerFactory emf = aProvider.createEntityManagerFactory(
                    "sharp",
                    getTestEMConfigMap() );
            EntityManager em = emf.createEntityManager();

//      -  ExecutableRuleSet rs = new ExecutableRuleSetImpl();
//            rs.addName( "test" );
//        em.persist( rs );

//        em.close();
//        emf.close();


//        emf = aProvider.createEntityManagerFactory(
//                "edu.asu.sharpc2b.example1:edu.asu.sharpc2b.lmm:edu.asu.sharpc2b.ocl:edu.asu.sharpc2b.ops:edu.asu.sharpc2b.prr:org.w3._2002._07.owl",
//                getTestEMConfigMap() );
//        em = emf.createEntityManager();

//        Object x = em.find( ExecutableRuleSetImpl.class, rs.getRdfId() );

//        System.out.println( x );
//        System.out.println( ((ExecutableRuleSetImpl) x).getPropertyNames() );
        }

    private static Map<String, String> getTestEMConfigMap() {
            Map<String, String> aMap = new HashMap<String, String>();

            aMap.put(ConfigKeys.FACTORY, RepositoryDataSourceFactory.class.getName());
            aMap.put(RepositoryDataSourceFactory.REPO, "test-repo");
            aMap.put(RepositoryDataSourceFactory.FILES, "");
            aMap.put(RepositoryDataSourceFactory.QUERY_LANG, RepositoryDataSourceFactory.LANG_SERQL);

            return aMap;
        }



}
