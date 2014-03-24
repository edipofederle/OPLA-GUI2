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
        
        //First step: Creates a experiement and persist it
        Experiment experiement = new Experiment("ExpTest1", "a description"); 
        experiement.save();
        
        //Second step
        //Create a execution. Execution is each run of experiement.
        //A execution belongs to a experiement.
        Execution execution = new Execution(experiement);
        
        //Third step: Some fake datas (Test propose only).
        //This datas will come from algoritm execution.
        List<InfoResult> infos = Factory.givenInfos(execution.getId());
        FunResults funs = Factory.givenFuns(execution.getId());
        
        execution.setInfos(infos);
        execution.setFuns(funs);
        
        //Fourth step: Initialize ExecutionPersistence class
        ExecutionPersistence persistence = new ExecutionPersistence(
                connection,
                new InfosResultPersistence(connection),
                new FunsResultPersistence(connection));
        
        
        //Fifth Step: Persiste One execution.
        //This will persiste the execution, funs datas (objectives), infos datas (numbers of classes etc.)
        //Also persite Metrics
        persistence.persist(execution);
        

    }
    
}
