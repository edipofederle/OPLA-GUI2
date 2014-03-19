/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.persistence.ExecutionPersistence;
import com.ufpr.br.opla.persistence.ResultPersistence;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.InfoResult;
import com.ufpr.br.opla.utils.Id;
import factories.Factory;
import java.sql.Statement;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author elf
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Id.class )
public class ExecutionPersistenceTest {
    
    
    
    @Test
    public void persistExecution() throws Exception{
        
        PowerMockito.mockStatic(Id.class);
        
        Database db                         = mock(Database.class);
        Statement st                        = mock(Statement.class);
        ResultPersistence resultPersistence = mock(ResultPersistence.class);
        Execution fakeExecution             = mock(Execution.class);
           
        when(db.getConnection()).thenReturn(st);
        
        PowerMockito.when(Id.generateUniqueId()).thenReturn("10");
        
        ExecutionPersistence persistence = new ExecutionPersistence(st, resultPersistence);
        
        List<InfoResult> infos = Factory.givenInfos(fakeExecution.getId());
        when(fakeExecution.getId()).thenReturn("10");
        when(fakeExecution.getInfos()).thenReturn(infos);
        when(fakeExecution.getFuns()).thenReturn(Factory.givenFuns());
        
        String query = "insert into executions (id) values (10)";
               
        persistence.persist(fakeExecution);      
        verify(st, times(1)).executeUpdate(query);
        verify(resultPersistence, times(2)).persistInfoDatas(any(InfoResult.class));
        
    }
    

    
}
