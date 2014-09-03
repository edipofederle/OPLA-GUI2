package db;

import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.utils.Utils;
import exceptions.MissingConfigurationException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import logs.log_log.Level;
import logs.log_log.Logger;
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
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        return exp.getExecutions();
      }
    }

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

        for (int i = 0; i < objectives.length; i++) {
          map.put(ordenedObjectives[i], objectives[i]);
        }

        statement.close();
        return map;
      }

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
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
        while (r.next()) {
          funs.put(r.getString("id"), r.getString("objectives"));
        }

        statement.close();
      }

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
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
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return "";

  }

  public static String getAlgoritmUsedToExperimentId(String id) {
    Statement statement = null;
    try {
      statement = database.Database.getConnection().createStatement();

      StringBuilder query = new StringBuilder();
      query.append("SELECT algorithm, description FROM experiments WHERE id=");
      query.append(id);

      ResultSet r = statement.executeQuery(query.toString());
      String description = r.getString("description");
      if ("null".equals(description)) {
        return r.getString("algorithm");
      }
      return r.getString("algorithm") + " (" + description + ")";

    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return "";
  }

  public static String getPlaUsedToExperimentId(String id) {
    Statement statement = null;
    try {
      statement = database.Database.getConnection().createStatement();

      StringBuilder query = new StringBuilder();
      query.append("SELECT name FROM experiments WHERE id=");
      query.append(id);

      ResultSet r = statement.executeQuery(query.toString());
      return r.getString("name");

    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return "";
  }

  public static void reloadContent() {
    try {
      content = results.Experiment.all();
    } catch (SQLException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } catch (Exception ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    }
  }

  public static PLAExtensibility getPlaExtMetricsForSolution(String idSolution, String experimentId) {
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (PLAExtensibility plaExt : exec.getAllMetrics().getPlaExtensibility()) {
            if (plaExt.getIdSolution().equals(idSolution)) {
              return plaExt;
            }
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
            if (elegance.getIdSolution().equals(idSolution)) {
              return elegance;
            }
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
            if (con.getIdSolution().equals(idSolution)) {
              return con;
            }
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
            if (f.getIdSolution().equals(idSolution)) {
              return f;
            }
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
          for (Elegance m : exec.getAllMetrics().getElegance()) {
            if (m.getIsAll() == 1) {
              listFd.add(m);
            }
          }
          return listFd;
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
          for (FeatureDriven m : exec.getAllMetrics().getFeatureDriven()) {
            if (m.getIsAll() == 1) {
              listFd.add(m);
            }
          }
          return listFd;
        }
      }
    }

    return listFd;
  }

  public static List<Conventional> getAllConventionalMetricsForExperimentId(String experimentId) {
    List<Conventional> listCons = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (Conventional m : exec.getAllMetrics().getConventional()) {
            if (m.getIsAll() == 1) {
              listCons.add(m);
            }
          }
          return listCons;
        }
      }

    }

    return listCons;
  }

  public static List<PLAExtensibility> getAllPLAExtMetricsForExperimentId(String experimentId) {
    List<PLAExtensibility> listCons = new ArrayList<>();
    for (Experiment exp : content) {
      if (exp.getId().equals(experimentId)) {
        for (Execution exec : exp.getExecutions()) {
          for (PLAExtensibility m : exec.getAllMetrics().getPlaExtensibility()) {
            if (m.getIsAll() == 1) {
              listCons.add(m);
            }
          }
          return listCons;
        }
      }

    }

    return listCons;
  }

  public static int getNumberOfFunctionForExperimentId(String experimentId) {
    Statement statement = null;
    try {
      statement = database.Database.getConnection().createStatement();

      StringBuilder query = new StringBuilder();
      query.append("SELECT names FROM map_objectives_names WHERE experiment_id=");
      query.append(experimentId.trim());

      ResultSet r = statement.executeQuery(query.toString());
      return r.getString("names").split(" ").length;

    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return 0;
  }

  /**
   * Retorna o número de soluções não dominadas dado um experimentID
   *
   * @param experimentId
   * @return number of non dominated solutions
   */
  public static int countNumberNonDominatedSolutins(String experimentId) {
    Statement statement = null;
    try {
      statement = database.Database.getConnection().createStatement();

      StringBuilder query = new StringBuilder();
      query.append("SELECT count(*) FROM objectives where experiement_id=");
      query.append(experimentId.trim());
      query.append(" AND execution_id=''");

      ResultSet r = statement.executeQuery(query.toString());
      return Integer.parseInt(r.getString("count(*)"));

    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return 0;

  }

  /**
   * Retorna uma lista contendo o nome de todas as soluções dado um experimentId
   * e um executionID
   *
   * @param experimentId
   * @param executionId
   * @return
   */
  public static List<String> getAllSolutionsForExecution(String experimentId, String executionId) {
    List<String> solutionsNames = new ArrayList<>();

    Statement statement = null;
    try {
      statement = database.Database.getConnection().createStatement();

      StringBuilder query = new StringBuilder();
      query.append("SELECT solution_name FROM objectives where experiement_id=");
      query.append(experimentId.trim());
      query.append(" AND execution_id=");
      query.append(executionId);
      query.append(" OR execution_id=''");

      ResultSet r = statement.executeQuery(query.toString());
      while (r.next()) {
        solutionsNames.add(r.getString("solution_name"));
      }


    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return solutionsNames;

  }

  /**
   * Esse método é usado apenas para o cálculo do hypervolume. Ele faz duas
   * coisas. :(
   *
   * 1) Retorna (fileToContent) um Map<String, List<Double>>, sendo que **key**
   * = path para o arquivo em disco contendo os valores referentes ao **value**.
   *
   * 2) Cria os arquivos (citados em 1) no disco. Estes arquivos são utilizados
   * apenas para a chamada do script em C que de fato calcula o hypervolume.
   * Estes arquivos são deletados (método deleteGeneratedFiles() em
   * HypervolumeGenerateObjsData) após a execução e obtenção dos resultados.
   *
   */
  public static Map<String, List<Double>> getAllObjectivesForDominatedSolutions(String... exeprimentIds) throws Exception {
    Statement statement = null;
    Statement statementExecutions = null;
    List<String> idsExecutions = new ArrayList<>();

    //Usado temporariamente. Após cálculos estes arquivos serão apagados.
    String pathToSaveFiles = UserHome.getOplaUserHome();

    Map<String, List<Double>> fileToContent = new HashMap<>();

    for (String exeprimentId : exeprimentIds) {
      String nameFile = (pathToSaveFiles + Utils.generateFileName(exeprimentId)).replaceAll("\\s+", "");

      try (PrintWriter pw = new PrintWriter(new FileWriter(nameFile))) {
        List<Double> values = new ArrayList<>();

        statementExecutions = database.Database.getConnection().createStatement();
        statement = database.Database.getConnection().createStatement();

        StringBuilder executionsQuery = new StringBuilder();
        executionsQuery.append("select id from executions where experiement_id=").append(exeprimentId);

        ResultSet executionsSet = statementExecutions.executeQuery(executionsQuery.toString());

        while (executionsSet.next()) {
          idsExecutions.add(executionsSet.getString("id"));
        }

        for (String idExecuton : idsExecutions) {
          StringBuilder query = new StringBuilder();

          query.append("SELECT objectives FROM objectives where experiement_id=").append(exeprimentId).append(" AND execution_id=").append(idExecuton);

          ResultSet r = statement.executeQuery(query.toString());
          while (r.next()) {
            String objs = r.getString("objectives").trim().replace("|", " ");
            String[] ov = objs.split(" ");

            for (int i = 0; i < ov.length; i++) {
              values.add(Double.parseDouble(ov[i]));
            }

            pw.write(objs);
            pw.write("\n");
          }

          pw.write("\n");

        }
        fileToContent.put(nameFile, values);
      }

    }
    return fileToContent;

  }

  public static List< List<Double>> getAllObjectivesForNonDominatedSolutions(String experimentId, int[] columns) {
  
    Statement statement = null;
    
    try { 
      statement = database.Database.getConnection().createStatement();

      List<List<Double>> values = new ArrayList<>();

      StringBuilder query = new StringBuilder();
      query.append("SELECT objectives FROM objectives where experiement_id=").append(experimentId).append(" AND execution_id=''");

      ResultSet result = statement.executeQuery(query.toString());
      while (result.next()) {
        String objs = result.getString("objectives").trim().replace("|", " ");
        String[] ov = objs.split(" ");
        List<Double> objectiveValue = new ArrayList<>();

        for (int i = 0; i < columns.length; i++) {
          objectiveValue.add(Double.parseDouble(ov[i].trim()));
        }


        values.add(objectiveValue);
      }
      
      return values;

    } catch (SQLException | MissingConfigurationException | ClassNotFoundException ex) {
      Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
    } finally {
      try {
        statement.close();
      } catch (SQLException ex) {
        Logger.getLogger().putLog(ex.getMessage(), Level.ERROR);
      }
    }

    return Collections.emptyList();

  }
}
