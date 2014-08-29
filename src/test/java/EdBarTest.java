
import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.indicators.Indicators;
import java.text.NumberFormat;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class EdBarTest extends JFrame {

  private static final long serialVersionUID = 7319359755265781240L;

  public EdBarTest(String applicationTitle) {
    super(applicationTitle);

    DefaultCategoryDataset dataset = createDataset();
    JFreeChart chart = createChart(dataset, applicationTitle);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);
  }

  private DefaultCategoryDataset createDataset() {
    DefaultCategoryDataset objDataset = new DefaultCategoryDataset();

    String ids[] = {"4726543913", "5727237783"};
    final XYSeriesCollection dataset = new XYSeriesCollection();

    for (int i = 0; i < ids.length; i++) {
      Map<String, Map<Double, Integer>> map = Indicators.quantityEdBySolutions(ids, ids[i]);

      Map.Entry<String, Map<Double, Integer>> content = map.entrySet().iterator().next();
      final XYSeries serie = new XYSeries(content.getKey());

      Map<Double, Integer> a = content.getValue();

      for (Map.Entry<Double, Integer> entry : a.entrySet()) {
        Double double1 = entry.getKey();
        Integer integer = entry.getValue();
        objDataset.addValue(integer, content.getKey(), double1);
      }

      dataset.addSeries(serie);
    }

    return objDataset;

  }

  private JFreeChart createChart(DefaultCategoryDataset chartData, String title) {
    String xLabel = "Euclidean Distance";
    String yLabel = "Number of Solutions";
    JFreeChart chart = ChartFactory.createBarChart("", xLabel, yLabel,
            chartData,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);

    configureRangeYAxis(chart);
    configureToolTip(chart);
    configureBarMarginSize(chart);

    return chart;
  }

  public static void main(String[] args) {
    database.Database.setPathToDB(UserHome.getPathToDb());
    EdBarTest frame = new EdBarTest("Demo");
    frame.pack();
    frame.setVisible(true);
  }

  private void configureBarMarginSize(JFreeChart chart) {
    BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
    renderer.setItemMargin(-0.5);
  }

  private void configureRangeYAxis(JFreeChart chart) {
    NumberAxis yAxis = (NumberAxis) chart.getCategoryPlot().getRangeAxis();
    yAxis.setTickUnit(new NumberTickUnit(0.75));
    ValueAxis rangeAxis = chart.getCategoryPlot().getRangeAxis();
    yAxis.setRange(0, rangeAxis.getUpperBound() + 0.5);
  }

  private void configureToolTip(JFreeChart chart) {
    BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
    renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator(
            "({1}, {2})", NumberFormat.getInstance()));
  }
}
