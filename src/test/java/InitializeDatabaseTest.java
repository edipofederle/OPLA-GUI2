


import com.ufpr.br.opla.results.DatabaseInitializer;
import java.io.File;
import org.junit.After;
import static org.junit.Assert.assertTrue;
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
    
    @After
    public void tearDown(){
        File db = new File("resources/opla.db");
        db.delete();
    }
    
    @Test
    public void shouldCreateDBOnlyOnce() throws Exception{
        DatabaseInitializer.initialize();
        
        File db = new File("resources/opla.db");
        
        assertTrue(db.exists());
        long timestamp = db.lastModified();
        
        File db2 = new File("resources/opla.db");
        
        DatabaseInitializer.initialize();
        assertTrue(timestamp == db2.lastModified() );
        
    }
    
}
