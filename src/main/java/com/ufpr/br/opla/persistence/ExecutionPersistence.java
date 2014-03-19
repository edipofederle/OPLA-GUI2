/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.persistence;

import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.InfoResult;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author elf
 */
public class ExecutionPersistence {
    
    private final Statement statement;
    private final ResultPersistence resultPersistence;

    public void persist(Execution fakeExecution) throws SQLException {
       StringBuilder query = new StringBuilder();
       query.append("insert into executions (id) values ");
       query.append("(");
       query.append(fakeExecution.getId());
       query.append(")");
        
        
       statement.executeUpdate(query.toString());
       
       for(InfoResult ir : fakeExecution.getInfos())
        this.resultPersistence.persistInfoDatas(ir);
    }

    public ExecutionPersistence(Statement st, ResultPersistence resultPersistence) {
        this.statement = st;
        this.resultPersistence = resultPersistence;
    }

}
