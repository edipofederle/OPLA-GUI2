/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence;

import com.ufpr.br.opla.results.FunResults;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author elf
 */
public class FunsResultPersistence {
  
    private final Statement statement;
    

    public FunsResultPersistence(Statement st) {
        this.statement = st;
    }

    public void persistFunsDatas(FunResults funs) throws SQLException {
        String query =
                "insert into objectives (execution_id, objectives) values ("+funs.getExecutionId()+",'"+funs.getObjectives()+"')";
        
        this.statement.executeUpdate(query);
    }
   
}
