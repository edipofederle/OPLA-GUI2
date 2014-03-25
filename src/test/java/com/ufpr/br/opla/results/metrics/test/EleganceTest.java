package com.ufpr.br.opla.results.metrics.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.metrics.Elegance;
import java.sql.Statement;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class EleganceTest {
    
    @Test
    public void saveEleganceMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("12");
        when(db.getConnection()).thenReturn(st);
        
        Elegance eleganceMetric = new Elegance(exec, db);
        
        eleganceMetric.setAtmr("10");
        eleganceMetric.setEc("20");
        eleganceMetric.setNac("30");
        
        eleganceMetric.save();
       
        String query = "insert into EleganceMetrics (nac, atmr, ec, execution_id) values (30, 10, 20, "+exec.getId()+")";
                
        verify(db.getConnection()).executeUpdate(query);
    }
    
}