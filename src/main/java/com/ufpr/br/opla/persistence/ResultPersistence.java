/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence;

import com.ufpr.br.opla.results.InfoResult;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author elf
 */
public class ResultPersistence {
    
    private final Statement statement;

    public void persistInfoDatas(InfoResult fakeInfoResult) throws SQLException {
        StringBuffer query = new StringBuffer();
        
        query.append("insert into infos (executation_id, name, list_of_concerns, number_of_packages, number_of_variabilities, number_of_interfaces, "
                + "number_of_classes, number_of_dependencies, number_of_abstractions, number_of_generalizations, number_of_associations, number_of_associations_class) values (");
        query.append(fakeInfoResult.getExecutation_id());
        query.append(",");
        query.append("'");
        query.append(fakeInfoResult.getName());
        query.append("'");
        query.append(",");
        query.append("'");
        query.append(fakeInfoResult.getListOfConcerns());
        query.append("'");
        query.append(",");
        query.append(fakeInfoResult.getNumberOfPackages());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfVariabilities());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfInterfaces());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfClasses());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfDependencies());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfAbstraction());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfGeneralizations());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfAssociations());
        query.append(",");
        query.append(fakeInfoResult.getNumberOfassociationsClass());
        query.append(")");
        System.out.println(query);
        statement.executeUpdate(query.toString());
//        statement.executeUpdate("insert into infos (executation_id, name, list_of_concerns, number_of_packages, number_of_variabilities) values ("
//                +fakeInfoResult.getExecutation_id()
//                + ","
//                +"'"+fakeInfoResult.getName()+"'"
//                +","
//                +"'"+fakeInfoResult.getListOfConcerns()+"'"
//                +","
//                +fakeInfoResult.getNumberOfPackages()
//                +","
//                +fakeInfoResult.getNumberOfVariabilities()
//                + ")");
    }
    

    public ResultPersistence(Statement st) {
        this.statement = st;
    }
    
}
