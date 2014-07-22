package db;

import exceptions.MissingConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;
import results.Experiment;


public class Database {

  private static List<Experiment> content;

  public static void setContent(List<Experiment> all) {
    content = all;
  }

  public static List<Experiment> getContent() {
    return content;
  }

  public static List<Execution> getAllExecutionsByExperimentId(String experimentId) {
    for (Experiment exp : content)
      if (exp.getId().equals(experimentId))
        return exp.getExecutions();
      
    return Collections.emptyList();
  }
  
  public static HashMap<String, String> getObjectivesBySolutionId(String solutionId, String experimentId) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM objectives WHERE solution_name LIKE '%");
    query.append(solutionId);
    query.append("'");

    String ordenedObjectives[] = db.Database.getOrdenedObjectives(experimentId).split(" ");

    try {
      try (Statement statement = database.Database.getConnection().createStatement()) {
        ResultSet r = statement.executeQuery(query.toString());
        String objectives[] = r.getString("objectives").split("\\|");
        HashMap<String, String> map = new HashMap<>();
        
        for (int i = 0; i < objectives.length; i++)
          map.put(ordenedObjectives[i], objectives[i]);

        statement.close();
        return map;
      }

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return (HashMap<String, String>) Collections.EMPTY_MAP;
    
  }

  public static Map<String, String> getAllObjectivesByExecution(String idExecution, String experimentId) {
    Map<String, String> funs = new HashMap<>();

    try {
      try (Statement statement = database.Database.getConnection().createStatement()) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT * FROM objectives where execution_id = ");
        query.append(idExecution);
        query.append(" OR experiement_id= ");
        query.append(experimentId);

        ResultSet r = statement.executeQuery(query.toString());
        while (r.next())
          funs.put(r.getString("id"), r.getString("objectives"));
        
        statement.close();
      }

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }


    return funs;
  }
  
  /**
   * 
   * @param experimentId
   * @return elegance,conventional, PLAExtensibility, featureDriven
   */
  public static String getOrdenedObjectives(String experimentId) {
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
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return "";

  }

  public static void reloadContent() {
    try {
      content = results.Experiment.all();
    } catch (SQLException ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static PLAExtensibility getPlaExtMetricsForSolution(String idSolution, String experimentId) {
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (PLAExtensibility plaExt : exec.getAllMetrics().getPlaExtensibility()) {
            if (plaExt.getIdSolution().equals(idSolution))
              return plaExt;
          }
        }
      }
    }

    return null;

  }

  public static Elegance getEleganceMetricsForSolution(String idSolution, String experimentId) {
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (Elegance elegance : exec.getAllMetrics().getElegance()) {
            if (elegance.getIdSolution().equals(idSolution))
              return elegance;
          }
        }
      }
    }

    return null;
  }

  public static Conventional getConventionalsMetricsForSolution(String idSolution, String experimentId) {
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (Conventional con : exec.getAllMetrics().getConventional()) {
            if (con.getIdSolution().equals(idSolution))
              return con;
          }
        }
      }
    }

    return null;
  }

  public static FeatureDriven getFeatureDrivenMetricsForSolution(String idSolution, String experimentId) {
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (FeatureDriven f : exec.getAllMetrics().getFeatureDriven()) {
            if (f.getIdSolution().equals(idSolution))
              return f;
          }
        }
      }
    }

    return null;
  }



  public static List<Elegance> getAllEleganceMetricsForExperimentId(String experimentId) {
    List<Elegance> listFd = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for(Elegance m : exec.getAllMetrics().getElegance()){
            if (m.getIsAll() == 1)
              listFd.add(m);
          }
        }
      }

    }

    return listFd;
  }
  
  public static List<FeatureDriven> getAllFeatureDrivenMetricsForExperimentId(String experimentId) {
    List<FeatureDriven> listFd = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for(FeatureDriven m : exec.getAllMetrics().getFeatureDriven()){
            if (m.getIsAll() == 1)
              listFd.add(m);
          }
        }
      }
    }

    return listFd;
  }

  public static List<Conventional> getAllConventionalMetricsForExperimentId(String experimentId) {
    List<Conventional> listCons = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()){
          for(Conventional m : exec.getAllMetrics().getConventional()){
            if (m.getIsAll() == 1)
              listCons.add(m);
          }
        }
      }

    }

    return listCons;
  }

  public static List<PLAExtensibility> getAllPLAExtMetricsForExperimentId(String experimentId) {
    List<PLAExtensibility> listCons = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()){
          for(PLAExtensibility m : exec.getAllMetrics().getPlaExtensibility()){
            if (m.getIsAll() == 1)
              listCons.add(m);
          }
        }
      }

    }

    return listCons;
  }
 
}
