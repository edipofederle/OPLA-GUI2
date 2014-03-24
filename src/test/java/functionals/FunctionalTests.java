/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package functionals;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.persistence.ExecutionPersistence;
import com.ufpr.br.opla.persistence.FunsResultPersistence;
import com.ufpr.br.opla.persistence.InfosResultPersistence;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.Experiment;
import com.ufpr.br.opla.results.FunResults;
import com.ufpr.br.opla.results.InfoResult;
import factories.Factory;
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
    public void test1() throws Exception{
        
        //First step
        Experiment experiment = new Experiment("ExpTest1", "a description"); 
        experiment.save();
        
        //Second step
        //Create a execution. Execution is each run of experiement.
        Execution execution = new Execution(experiment);
        
        List<InfoResult> infos = Factory.givenInfos(execution.getId());
        FunResults funs = Factory.givenFuns(execution.getId()); // Nao sendo persistido ainda
        
        execution.setInfos(infos);
        execution.setFuns(funs);
        
        InfosResultPersistence resultPersistence = new InfosResultPersistence(connection);
        FunsResultPersistence funPersistence = new FunsResultPersistence(connection);
        
        ExecutionPersistence persistence = new ExecutionPersistence(
                connection, resultPersistence, funPersistence);
        
        persistence.persist(execution);
        

    }
    
}
