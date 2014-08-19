/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.charts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  public static void generate(String[] functions, HashMap<String, String> mapExperimentIdToFile, int[] columns,
          String[] algorithms) {
    try {
      String name = "Meu Gráfico";
      ChartGeneratorScatter g = new ChartGeneratorScatter(name, functions[0], functions[1]);
      int counter = 0;

      for (Map.Entry<String, String> entry : mapExperimentIdToFile.entrySet()) {
        List<List<Double>> content = ReadObjectives.read(columns, entry.getValue());
        HashMap<String, List<XYDataItem>> algo = new HashMap<>();
        List<XYDataItem> one = new ArrayList<>();

        for (List<Double> list : content) {
          one.add(new XYDataItem(list.get(0), list.get(1)));
        }

        algo.put(algorithms[counter], one);
        counter++;

        g.setDataSet(algo);

      }

      ChartPanel chartPanel = g.plot();

      JFrame frame = new JFrame(name);
      frame.add(chartPanel);

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);

    } catch (IOException ex) {
      Logger.getLogger(ChartGenerate.class.getName()).log(Level.SEVERE, null, ex);
    }





  }
}
