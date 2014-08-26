
import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.indicators.Indicators;
import java.awt.Color;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class GraficoEdTest extends ApplicationFrame {

  /**
   * Creates a new demo.
   *
   * @param title the frame title.
   */
  public GraficoEdTest(final String title) {

    super(title);

    final XYDataset dataset = createDataset();
    final JFreeChart chart = createChart(dataset);
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);

  }

  /**
   * Creates a sample dataset.
   *
   * @return a sample dataset.
   */
  private XYDataset createDataset() {

    String ids[] = {"4726543913", "5727237783"};
    final XYSeriesCollection dataset = new XYSeriesCollection();

    for (int i = 0; i < ids.length; i++) {
      Map<String, Map<Integer, Integer>> map = Indicators.quantityEdBySolutions(ids, ids[i]);

      Map.Entry<String, Map<Integer, Integer>> content = map.entrySet().iterator().next();
      final XYSeries serie = new XYSeries(content.getKey());

      Map<Integer, Integer> a = content.getValue();

      for (Map.Entry<Integer, Integer> entry : a.entrySet()) {
        Integer double1 = entry.getKey();
        Integer integer = entry.getValue();
        serie.add(double1, integer);
      }
      dataset.addSeries(serie);
    }


    return dataset;

  }

  /**
   * Creates a chart.
   *
   * @param dataset the data for the chart.
   *
   * @return a chart.
   */
  private JFreeChart createChart(final XYDataset dataset) {

    // create the chart...
    final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6", // chart title
            "X", // x axis label
            "Y", // y axis label
            dataset, // data
            PlotOrientation.VERTICAL,
            true, // include legend
            true, // tooltips
            false // urls
            );

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
    chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
    //      legend.setDisplaySeriesShapes(true);

    // get a reference to the plot for further customisation...
    final XYPlot plot = chart.getXYPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);

    final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    renderer.setSeriesLinesVisible(0, true);
    renderer.setSeriesShapesVisible(1, true);
    renderer.setSeriesShapesVisible(2, true);
    plot.setRenderer(renderer);


    NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
    yAxis.setTickUnit(new NumberTickUnit(0.75));
    ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();
    yAxis.setRange(0, rangeAxis.getUpperBound() + 0.5);

    return chart;

  }

  public static void main(final String[] args) {
    database.Database.setPathToDB(UserHome.getPathToDb());
    final GraficoEdTest demo = new GraficoEdTest("");
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);

  }
}
