/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results.test;

import com.ufpr.br.opla.results.Execution;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class ExecutionTest {
    
    @Test
    public void shouldHaveIdWhenCreateExecution(){
        Execution exec = new Execution();
        
        
        assertNotNull(exec.getId());
        
    }
    
}
