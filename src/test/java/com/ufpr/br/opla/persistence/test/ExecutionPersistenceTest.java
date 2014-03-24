/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.persistence.ExecutionPersistence;
import com.ufpr.br.opla.persistence.FunsResultPersistence;
import com.ufpr.br.opla.persistence.InfosResultPersistence;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.Experiment;
import com.ufpr.br.opla.results.FunResults;
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
        InfosResultPersistence infosPersistence = mock(InfosResultPersistence.class);
        FunsResultPersistence funsPersistence  = mock(FunsResultPersistence.class);
        Execution fakeExecution             = mock(Execution.class);
        Experiment experiment = mock(Experiment.class);
        
        when(experiment.getId()).thenReturn("100");
        
        when(db.getConnection()).thenReturn(st);
        when(fakeExecution.getExperiement()).thenReturn(experiment);
        
        PowerMockito.when(Id.generateUniqueId()).thenReturn("10");
        
        ExecutionPersistence persistence = new ExecutionPersistence(st, infosPersistence, funsPersistence);
        
        List<InfoResult> infos = Factory.givenInfos(fakeExecution.getId());
        FunResults funs = Factory.givenFuns(fakeExecution.getId());
        when(fakeExecution.getId()).thenReturn("10");
        when(fakeExecution.getInfos()).thenReturn(infos);
        when(fakeExecution.getFuns()).thenReturn(funs);
        
        String query = "insert into executions (id, experiement_id) values (10,100)";
               
        persistence.persist(fakeExecution);      
        verify(st, times(1)).executeUpdate(query);
        verify(infosPersistence, times(2)).persistInfoDatas(any(InfoResult.class));
        verify(funsPersistence, times(1)).persistFunsDatas(any(FunResults.class));
        
    }
    

    
}
