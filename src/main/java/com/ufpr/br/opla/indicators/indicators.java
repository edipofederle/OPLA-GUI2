/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.indicators;

import exceptions.MissingConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
public class Indicators {

  /**
   * Only for non dominated solutions
   *
   */
  public static SortedMap<String, Double> getEdsForExperiment(String experimentID) {

    try {
      try (Statement statement = database.Database.getConnection().createStatement()) {
        SortedMap<String, Double> results = new TreeMap();

        StringBuilder query = new StringBuilder();
        query.append("SELECT ed, solution_name FROM distance_euclidean WHERE experiment_id = ");
        query.append(experimentID);

        ResultSet result = statement.executeQuery(query.toString());

        while (result.next()) {
          results.put(result.getString("solution_name"), Double.parseDouble(result.getString("ed")));
        }
        statement.close();

        return results;
      }



    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Indicators.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  public static String getEdForSelectedSolution(String fileName, String experimentID) {
    try {
      String ed;
      try (Statement statement = database.Database.getConnection().createStatement()) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ed FROM distance_euclidean WHERE experiment_id = ");
        query.append(experimentID);
        query.append(" AND solution_name ='");
        query.append(fileName.substring(0, fileName.length() - 4));
        query.append("'");
        ResultSet result = statement.executeQuery(query.toString());
        ed = result.getString("ed");
      }
    return ed;

    } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
      Logger.getLogger(Indicators.class.getName()).log(Level.SEVERE, null, ex);
    }

    return "0.0";

  }
  
  public static Entry<String, Double> getSolutionWithBestTradeOff(String experimentId) {

    SortedMap<String, Double> eds = getEdsForExperiment(experimentId);
    Double ed = Double.MAX_VALUE;
    Entry<String, Double> solution = null;
    
    for(Map.Entry<String, Double> entry : eds.entrySet()){
     if(entry.getValue() < ed){
       ed = entry.getValue();
       solution = entry;
     }
    }  
    
    return solution;
  }
}
