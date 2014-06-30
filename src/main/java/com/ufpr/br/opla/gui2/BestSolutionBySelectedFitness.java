package com.ufpr.br.opla.gui2;

import java.util.Map.Entry;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;

class BestSolutionBySelectedFitness {

  public static SortedMap<String, Double> calculateBestFeatureDriven(String experimentId) {
    List<FeatureDriven> data = db.Database.getAllFeatureDrivenMetricsForExperimentId(experimentId);
    return buildMapFeatureDriven(data);
  }

  public static SortedMap<String, Double> calculateBestElegance(String experimentId) {
    List<Elegance> data = db.Database.getAllEleganceMetricsForExperimentId(experimentId);
    return buildMapElegance(data);
  }

  public static SortedMap<String, Double> calculateBestConventional(String experimentId) {
    List<Conventional> data = db.Database.getAllConventionalMetricsForExperimentId(experimentId);
    return buildMapConventional(data);
  }

  public static SortedMap<String, Double> calculateBestPlaExt(String experimentId) {
    List<PLAExtensibility> data = db.Database.getAllPLAExtMetricsForExperimentId(experimentId);
    return buildMapPLAExt(data);
  }

  public static void buildTable(JTable tableMinorFitnessValues, SortedMap map) {

    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("Solution ID");
    model.addColumn("Value");
    tableMinorFitnessValues.setModel(model);

    Iterator<Entry<Double, String>> it = map.entrySet().iterator();
    while (it.hasNext()) {
      Object[] row = new Object[2];
      Map.Entry pairs = (Map.Entry<Double, String>) it.next();
      row[1] = pairs.getValue();
      row[0] = pairs.getKey();
      it.remove(); // evitar ConcurrentModificationException
      model.addRow(row);
    }

    tableMinorFitnessValues.updateUI();
  }

  static void buildTableObjectives(JTable tableObjectives, HashMap<String, String> result) {
    DefaultTableModel model = new DefaultTableModel();

    model.addColumn("Objective");
    model.addColumn("Value");
    tableObjectives.setModel(model);

    Iterator<Entry<String, String>> it = result.entrySet().iterator();
    while (it.hasNext()) {
      Object[] row = new Object[2];
      Map.Entry pairs = (Map.Entry<String, String>) it.next();
      row[1] = pairs.getValue();
      row[0] = pairs.getKey();
      it.remove(); // evitar ConcurrentModificationException
      model.addRow(row);
    }

    tableObjectives.updateUI();
  }

  private static SortedMap<String, Double> buildMapElegance(List<Elegance> data) {
    SortedMap<String, Double> map = new TreeMap<>();
    for (int i = 0; i < data.size(); i++) {
      Elegance elegance = data.get(i);
      map.put(elegance.getIdSolution(), elegance.total());
    }
    return map;
  }

  private static SortedMap<String, Double> buildMapConventional(List<Conventional> data) {
    SortedMap<String, Double> map = new TreeMap();
    for (int i = 0; i < data.size(); i++) {
      Conventional elegance = data.get(i);
      map.put(elegance.getIdSolution(), elegance.getMacAggregation());
    }
    return map;
  }

  private static SortedMap<String, Double> buildMapFeatureDriven(List<FeatureDriven> data) {
    SortedMap<String, Double> map = new TreeMap();
    for (int i = 0; i < data.size(); i++) {
      FeatureDriven fd = data.get(i);
      map.put(fd.getIdSolution(), fd.getMsiAggregation());
    }
    return map;
  }

  private static SortedMap<String, Double> buildMapPLAExt(List<PLAExtensibility> data) {
    SortedMap<String, Double> map = new TreeMap();
    for (int i = 0; i < data.size(); i++) {
      PLAExtensibility plaExt = data.get(i);
      map.put(plaExt.getIdSolution(), plaExt.getPlaExtensibility());
    }
    return map;
  }

  
  
}
