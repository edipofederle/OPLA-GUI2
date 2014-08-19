/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.charts;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartGeneratorScatter {

  private String name;
  private final XYSeriesCollection dataset = new XYSeriesCollection();
  private String xAxisLabel;
  private String yAxisLabel;

  /**
   *
   *
   * @param chartName - Chart Name
   * @param xLabel - Axis X Label Name
   * @param yLabel - Axis Y Label Name
   */
  public ChartGeneratorScatter(String chartName, String xLabel, String yLabel) {
    this.name = chartName;
    this.xAxisLabel = xLabel;
    this.yAxisLabel = yLabel;
  }

  /**
   * Set a HashMap of series
   *
   * @param series
   */
  public void setDataSet(HashMap<String, List<XYDataItem>>... series) {
    for (HashMap<String, List<XYDataItem>> mapValue : series) {
      for (Entry<String, List<XYDataItem>> entry : mapValue.entrySet()) {
        String serieName = entry.getKey(); // Exemplo NSGA-II

        final XYSeries serie = new XYSeries(serieName);
        List<XYDataItem> value = entry.getValue();
        for (XYDataItem xyValues : value) {
          serie.add(xyValues.getX(), xyValues.getY());
        }

        this.dataset.addSeries(serie);
      }
    }
  }

  public ChartPanel plot() {
    if (this.dataset.getSeries().isEmpty()) {
      throw new ExceptionInInitializerError("dataset not initialized. Call .setDataSet before cal this method");
    }

    final JFreeChart chart = createChart();
    final ChartPanel chartPanel = new ChartPanel(chart);
    return chartPanel;
  }

  private JFreeChart createChart() {
    final JFreeChart chart = ChartFactory.createScatterPlot(this.name, this.xAxisLabel, this.yAxisLabel, this.dataset,
            PlotOrientation.VERTICAL, true, true, false);

    custonConfs(chart);

    return chart;
  }

  private void custonConfs(final JFreeChart chart) {
    chart.setBackgroundPaint(Color.white);

    final XYPlot plot = chart.getXYPlot();

    plot.setBackgroundPaint(Color.white);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);

    final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

    // change the auto tick unit selection to integer units only...
    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    NumberFormat format = NumberFormat.getNumberInstance();
    format.setMaximumFractionDigits(20);
    StandardXYToolTipGenerator ttG = new StandardXYToolTipGenerator("{1},{2}", format, format);
    renderer.setBaseToolTipGenerator(ttG);

    plot.setRenderer(renderer);
  }
  
}
