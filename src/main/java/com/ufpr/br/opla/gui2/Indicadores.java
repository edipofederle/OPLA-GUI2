/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import exceptions.MissingConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
class Indicadores {

  /**
   * Only for non dominated solutions
   *
   */
  public static HashMap<String, String> getEdsForExperiment(String experimentID) {

    try {
      try (Statement statement = database.Database.getConnection().createStatement()) {
        HashMap<String, String> results = new HashMap<>();

        StringBuilder query = new StringBuilder();
        query.append("SELECT ed, solution_name FROM distance_euclidean WHERE experiment_id = ");
        query.append(experimentID);

        ResultSet result = statement.executeQuery(query.toString());

        while (result.next()) {
          results.put(result.getString("solution_name"), result.getString("ed"));
        }
        statement.close();

        return results;
      }



    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Indicadores.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public static String getEdForSelectedSolution(String fileName, String experimentID) {
    try {
      Statement statement = database.Database.getConnection().createStatement();
      StringBuilder query = new StringBuilder();
      query.append("SELECT ed FROM distance_euclidean WHERE experiment_id = ");
      query.append(experimentID);
      query.append(" AND solution_name ='");
      query.append(fileName.substring(0, fileName.length() - 4));
      query.append("'");

      ResultSet result = statement.executeQuery(query.toString());

    String ed =  result.getString("ed");
    statement.close();
    return ed;

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Indicadores.class.getName()).log(Level.SEVERE, null, ex);
    }

    return "0.0";

  }
}
