/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import exceptions.MissingConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import results.Execution;
import results.Experiment;

/**
 *
 * @author elf
 */
public class Database {

    private static List<Experiment> content;

    public static void getAllExperiments() {
        try {
            Statement statement = database.Database.getConnection().createStatement();
            ResultSet r = statement.executeQuery("select * from experiments");


        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setContent(List<Experiment> all) {
        content = all;
    }

    public static List<Experiment> getContent() {
        return content;
    }

    public static List<Execution> getAllExecutionsByExperimentId(String experiementId) {
        for (Experiment exp : content) {
            if (exp.getId().equals(experiementId))
                return exp.getExecutions();
        }
        return Collections.emptyList();
    }

    public static Map<String, String> getAllObjectivesByExecution(String idExecution, String idExperiment) {
        Map<String, String> funs = new HashMap<>();

        try {
            try (Statement statement = database.Database.getConnection().createStatement()) {
                StringBuilder query = new StringBuilder();
                
                query.append("SELECT * FROM objectives where execution_id = ");
                query.append(idExecution);
                query.append(" OR experiement_id= ");
                query.append(idExperiment);
                
                ResultSet r = statement.executeQuery(query.toString());
                while (r.next()) {
                    funs.put(r.getString("id"), r.getString("objectives"));
                }
                statement.close();
            }

        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }


        return funs;
    }
    
    public static String getOrdenedObjectives(String experimentId){
        Statement statement = null;
        try {
            statement = database.Database.getConnection().createStatement();
            
            StringBuilder query = new StringBuilder();
            query.append("SELECT names FROM map_objectives_names WHERE experiment_id=");
            query.append(experimentId);
            
            ResultSet r = statement.executeQuery(query.toString());
            return r.getString("names");
            
        } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return "";       
         
    }
}
