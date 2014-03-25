package com.ufpr.br.opla.results.metrics.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.metrics.PLAExtensibility;
import java.sql.Statement;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class PLAExtensibilityTest {
    
    @Test
    public void shouldSavePLAExtensibilityMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("10");
        when(db.getConnection()).thenReturn(st);
        
        PLAExtensibility plaext = new PLAExtensibility(exec, db);
        plaext.setPlaExtensibility("10");
        
        plaext.save();
        String query = "insert into PLAExtensibilityMetrics (plaExtensibility, execution_id) values (10,10)";
        
        verify(db.getConnection(), times(1)).executeUpdate(query);
        
        
        
    }
}
