/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.results.metrics;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.exceptions.MissingConfigurationException;
import com.ufpr.br.opla.results.Execution;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
public class PLAExtensibility {
    
    private String plaExtensibility;
    private final Execution execution;
    private final Database db;

    public PLAExtensibility(Execution execution, Database db) {
        this.execution = execution;
        this.db = db;
    }

    public String getPlaExtensibility() {
        return plaExtensibility;
    }

    public void setPlaExtensibility(String plaExtensibility) {
        this.plaExtensibility = plaExtensibility;
    }

    public void save() {
        StringBuilder query = new StringBuilder();
        
        
        query.append("insert into PLAExtensibilityMetrics (plaExtensibility, execution_id) values (");
        query.append(this.getPlaExtensibility());
        query.append(",");
        query.append(this.execution.getId());
        query.append(")");
        
        try {
            this.db.getConnection().executeUpdate(query.toString());
        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
}
