/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.persistence.test;

import com.ufpr.br.opla.persistence.ResultPersistence;
import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.InfoResult;
import java.sql.Statement;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class InfoResultsPersistenceTest {
    
    @Test
    public void shouldNotBeNull() throws  Exception{
        Database db = mock(Database.class);
        Statement st = mock(Statement.class);
        when(db.getConnection()).thenReturn(st);
        ResultPersistence persistence = new ResultPersistence(st);
        assertNotNull(persistence);
    }

    
    @Test
    public void persistInfosDatas() throws Exception{
        Database db = mock(Database.class);
        Statement st = mock(Statement.class);
        
        when(db.getConnection()).thenReturn(st);
        
        ResultPersistence persistence = new ResultPersistence(st);
        
        InfoResult fakeInfoResult = mock(InfoResult.class);
        
        when(fakeInfoResult.getListOfConcerns())
                           .thenReturn("concern1 | concern2");
        when(fakeInfoResult.getExecutionId()).thenReturn("1");
        when(fakeInfoResult.getName()).thenReturn("INFO_AGM_1");
        when(fakeInfoResult.getNumberOfPackages()).thenReturn(10);
        when(fakeInfoResult.getNumberOfVariabilities()).thenReturn(5);
        when(fakeInfoResult.getNumberOfInterfaces()).thenReturn(6);
        when(fakeInfoResult.getNumberOfClasses()).thenReturn(20);
        when(fakeInfoResult.getNumberOfDependencies()).thenReturn(2);
        when(fakeInfoResult.getNumberOfAbstraction()).thenReturn(3);
        when(fakeInfoResult.getNumberOfGeneralizations()).thenReturn(3);
        when(fakeInfoResult.getNumberOfAssociations()).thenReturn(5);
        when(fakeInfoResult.getNumberOfassociationsClass()).thenReturn(1);
        
        
        
        persistence.persistInfoDatas(fakeInfoResult);
        
        String query = "insert into infos(execution_id, name,"
                + " list_of_concerns, number_of_packages, number"
                + "_of_variabilities, number_of_interfaces, number_of_classes,"
                + " number_of_dependencies, number_of_abstractions,"
                + " number_of_generalizations, number_of_associations,"
                + " number_of_associations_class)"
                + " values (1,'INFO_AGM_1',"
                + "'concern1 | concern2',10,5,6,20,2,3,3,5,1)";
        verify(st).executeUpdate(query);
    }
    
}
