/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results.test;

import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.Experiment;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 *
 * @author elf
 */
public class ExecutionTest {
    
    private Experiment experiment;
    
    @Before
    public void setup(){
        experiment = mock(Experiment.class);
        
    }
    
    
    @Test
    public void shouldHaveAExperiement(){
        Execution exec = new Execution(experiment);
        assertNotNull(exec.getExperiement());
    }
    
    @Test
    public void shouldHaveIdWhenCreateExecution(){
        Execution exec = new Execution(experiment);
        assertNotNull(exec.getId());
    }
    
}
