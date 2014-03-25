/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results.metrics.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.metrics.Conventional;
import java.sql.Statement;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class ConventionalTest {
    
    @Test
    public void saveConventionalMetrics() throws  Exception{
         Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("1");
        when(db.getConnection()).thenReturn(st);
        
        Conventional conventionalMetrics = new Conventional(exec, db);
        conventionalMetrics.setChoesion("10");
        conventionalMetrics.setMacAggregation("10");
        conventionalMetrics.setMeanDepComps("10");
        conventionalMetrics.setMeanNumOps("10");
        conventionalMetrics.setSumClassesDepIn("11");
        conventionalMetrics.setSumClassesDepOut("12");
        conventionalMetrics.setSumDepIn("1");
        conventionalMetrics.setSumDepOut("3");
        
        String expectedQuery = "insert into ConventionalMetrics (choesion,"
                + " macAggregation, meanDepComps, meanNumOps, sumClassesDepIn,"
                + " sumClassesDepOut, sumDepIn, sumDepOut, execution_id)"
                + " values (10,10,10,10,11,12,1,3,1)";
        
        
        conventionalMetrics.save();
               
        verify(db.getConnection()).executeUpdate(expectedQuery);
        
        
        
        
        
    }
    
}
