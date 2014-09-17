
import com.ufpr.br.opla.configuration.UserHome;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import results.Execution;
import results.FunResults;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author elf
 */
public class HypervolumeTest {

  @Test @Ignore
  public void test1() throws IOException {
    database.Database.setPathToDB(UserHome.getPathToDb());
    db.Database.reloadContent();
    Collection<Execution> fitnesses = db.Database.getAllExecutionsByExperimentId("2124944579");


    PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
    List<Double> values = new ArrayList<>();
    
    for (Execution execution : fitnesses) {
      for (FunResults fun : execution.getFuns()) {
        
        
        String o = fun.getObjectives().trim().replace("|", " ");
        String[] ov = o.split(" ");
        
        for(int i=0; i < ov.length; i++){
          values.add(Double.parseDouble(ov[i]));
        }
        
        pw.write(o);
        pw.write("\n");
      }
      pw.write("\n");
      pw.write("\n");
    }
    
    pw.close();
    System.out.println(findUpperValue(values));
    
   
    
  }

  private Double findUpperValue(List<Double> values) {
    Double max = Double.MIN_VALUE;
    
    for (Double double1 : values)
      if(double1 > max)
        max = double1;

    return max+1;
  }
}
