/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.utils.test;

import com.ufpr.br.opla.utils.Id;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class IdTest {
    
    @Test
    public void generateId(){
       assertNotNull(Id.generateUniqueId());
    }
}
