/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.charts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataItem;

/**
 *
 * @author elf
 */
public class ChartGenerate {

  /**
   *
   * @param functions - functions[0] = x Axis, functions[1] y Axis
   * @param mapExperimentIdToFile - map contendo o id do experimento e o path
   * para o arquivo contendo os valores das funcoes objetivos
   */
public static void generate(String[] functions, HashMap<String, String> experimentToAlgorithmUsed, int[] columns, String outputDir) {

      String name = "";
      ChartGeneratorScatter g = new ChartGeneratorScatter(name, functions[0], functions[1]);

      for (Map.Entry<String, String> entry : experimentToAlgorithmUsed.entrySet()) {
        
        List<List<Double>> content = db.Database.getAllObjectivesForNonDominatedSolutions(entry.getKey(), columns);
        HashMap<String, List<XYDataItem>> algo = new HashMap<>();
        List<XYDataItem> one = new ArrayList<>();

        for (List<Double> list : content) {
          one.add(new XYDataItem(list.get(0), list.get(1)));
        }

        algo.put(entry.getValue(), one);
        g.setDataSet(algo);
      }

      ChartPanel chartPanel = g.plot();

      JFrame frame = new JFrame(name);
      frame.add(chartPanel);

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
    
  }

}