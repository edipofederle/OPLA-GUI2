/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.google.common.collect.HashBiMap;
import exceptions.MissingConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import results.Execution;
import results.Experiment;
import results.FunResults;

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

    public static Map<String, String> getAllObjectivesByExecution(String idExecution) {
        Map<String, String> funs = new HashMap<String, String>();

        try {
            Statement statement = database.Database.getConnection().createStatement();
            ResultSet r = statement.executeQuery("SELECT * FROM objectives where execution_id=" + idExecution);

            while (r.next()) {
                funs.put(r.getString("id"), r.getString("objectives"));
            }


        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }


        return funs;
    }
}
