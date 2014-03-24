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
    private final InfosResultPersistence infosPersistence;
    private final FunsResultPersistence funsPersistence;

    public void persist(Execution fakeExecution) throws SQLException {
       StringBuilder query = new StringBuilder();
       query.append("insert into executions (id, experiement_id) values ");
       query.append("(");
       query.append(fakeExecution.getId());
       query.append(",");
       query.append(fakeExecution.getExperiement().getId());
       query.append(")");
        
        
       statement.executeUpdate(query.toString());
       
       for(InfoResult ir : fakeExecution.getInfos())
        this.infosPersistence.persistInfoDatas(ir);
       
       this.funsPersistence.persistFunsDatas(fakeExecution.getFuns());
    }

    public ExecutionPersistence(Statement st, InfosResultPersistence resultPersistence, FunsResultPersistence funsPersistence) {
        this.statement = st;
        this.infosPersistence = resultPersistence;
        this.funsPersistence = funsPersistence;
    }

}
