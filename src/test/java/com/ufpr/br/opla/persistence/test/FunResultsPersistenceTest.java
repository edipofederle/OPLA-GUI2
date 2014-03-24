/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.persistence.FunsResultPersistence;
import com.ufpr.br.opla.results.FunResults;
import factories.Factory;
import java.sql.Statement;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class FunResultsPersistenceTest {
    
    @Test
    public void persistFunsDatas() throws Exception{
        Database db = mock(Database.class);
        Statement st = mock(Statement.class);
        
        when(db.getConnection()).thenReturn(st);
        
        FunsResultPersistence persistence = new FunsResultPersistence(st);
        
        FunResults funs = Factory.givenFuns("100");
        
        persistence.persistFunsDatas(funs);
        
        String query = "insert into objectives (execution_id, objectives) values ("+funs.getExecutionId()+",'"+funs.getObjectives()+"')";
        System.out.println(query);
        verify(st).executeUpdate(query);
        
        
        
    }
}
