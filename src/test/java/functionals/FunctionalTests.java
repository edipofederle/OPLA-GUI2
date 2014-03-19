/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functionals;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.persistence.ExecutionPersistence;
import com.ufpr.br.opla.persistence.ResultPersistence;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.Experiment;
import com.ufpr.br.opla.results.FunResults;
import com.ufpr.br.opla.results.InfoResult;
import factories.Factory;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class FunctionalTests {
    
    
    private static final String PATH_TO_DB = "src/test/resources/opla_test.db";
    private Statement connection;
    
    
    @Before
    public void setup() throws Exception{
        Database.setPathToDB(PATH_TO_DB);
        connection = Database.getInstance().getConnection();
    }
    
    @Test
    public void test1() throws SQLException{
        
        //First step
        Experiment experiment = new Experiment();
        experiment.setName("ExpTest1");
        experiment.setDescription("A Test");
        
        //Second step
        //Create a execution. Execution is each run of experiement.
        Execution execution = new Execution();
        
        List<InfoResult> infos = Factory.givenInfos(execution.getId());
        List<FunResults> funs = Factory.givenFuns(); // Nao sendo persistido ainda
        
        execution.setInfos(infos);
        execution.setFuns(funs);
        
        ResultPersistence resultPersistence = new ResultPersistence(connection);
        
        ExecutionPersistence persistence = new ExecutionPersistence(
                connection, resultPersistence);
        
        persistence.persist(execution);
        

    }
    
}
