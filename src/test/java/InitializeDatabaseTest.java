import com.ufpr.br.opla.exceptions.MissingConfigurationException;
import com.ufpr.br.opla.results.DatabaseInitializer;
import java.io.File;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class InitializeDatabaseTest {
    
    private static final String PATH_TO_DATABASE = "src/test/resources/opla.db";
    
    @Before
    public void setUp(){
        DatabaseInitializer.setPathToDb(PATH_TO_DATABASE);
    }
    
    @After
    public void cleanUp(){
        File db = new File(PATH_TO_DATABASE);
        db.delete();
    }
    
    @Test(expected=MissingConfigurationException.class)
    public void shouldExceptionWhenDontHavePathToDatabaseFile() throws  Exception{
        DatabaseInitializer.setPathToDb("");
        DatabaseInitializer.initialize();
        
    }
    
    @Test
    public void shouldCreateDBOnlyOnce() throws Exception{
        DatabaseInitializer.initialize();
        
        File db = new File(PATH_TO_DATABASE);
        
        assertTrue(db.exists());
        long timestamp = db.lastModified();
        
        File db2 = new File(PATH_TO_DATABASE);
        
        DatabaseInitializer.initialize();
        assertTrue(timestamp == db2.lastModified() );
    }
    
}
