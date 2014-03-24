/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.Experiment;
import java.sql.Statement;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
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
@PrepareForTest(Database.class)
public class ExperimentTest {
    
    private Database db;
    
    @Before
    public void setup() throws Exception{
        PowerMockito.mockStatic(Database.class);
        Statement st = mock(Statement.class);
        db = mock(Database.class);
        when(Database.getInstance()).thenReturn(db);
        PowerMockito.when(Database.getInstance().getConnection()).thenReturn(st);
    }
    
    @Test
    public void shouldHaveIdWhenCreateExperiment() throws Exception{
        
        Experiment experiment = new Experiment("ExpTest1", "a description");
        
        assertNotNull(experiment.getId());    
    }
    
    @Test
    public void shouldPersisteExperiement() throws  Exception{

        
        Experiment experiment = new Experiment("ExpTest1", "a description");
        experiment.setId("200");
        experiment.save();
        
        
        verify(db.getConnection())
                        .executeUpdate("insert into experiments (id, name, description) values "
                                + "(200,'ExpTest1','a description')");
    }
    
}
