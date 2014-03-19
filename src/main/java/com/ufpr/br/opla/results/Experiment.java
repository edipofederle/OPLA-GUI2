/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results;

import com.ufpr.br.opla.utils.Id;
import java.util.List;

/**
 * Classe que representa um experiemento.
 * 
 * @author elf
 */
public class Experiment {
    
    private final String id;
    private String name;
    private String description;
    private List<Execution> executions;
    
    public Experiment(){
        this.id = Id.generateUniqueId();
    }

    public String getName() {
        return name;
    }
    
    public String getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
