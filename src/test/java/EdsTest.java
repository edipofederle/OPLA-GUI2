
import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.indicators.Indicators;
import com.ufpr.br.opla.utils.ReadSolutionsFiles;
import java.util.Map;
import java.util.Map.Entry;
import junit.framework.Assert;
import org.junit.Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class EdsTest {
 
  @Test
  public void test(){
    database.Database.setPathToDB(UserHome.getPathToDb());
    
    Indicators.getAllEdsForExperiments("4726543913", "5727237783");
  }
  
  @Test
  public void numberOfNon(){
    String dir = "src/test/resources/output/";
    int a = ReadSolutionsFiles.countNumberNonDominatedSolutins("4726543913", dir);
    int b = ReadSolutionsFiles.countNumberNonDominatedSolutins("5727237783", dir);
    
    Assert.assertEquals(4, a);
    Assert.assertEquals(7, b);
  }
  
  @Test
  public void sumTotalNonDominatedSolutions(){
   int total = Indicators.sumTotalNonDominatedSolutions("4726543913", "5727237783"); 
   
   Assert.assertEquals(11, total);
  }
  
  
  @Test
  public void quantityByEdValue() {
    database.Database.setPathToDB(UserHome.getPathToDb());

    String ids[] = {"4726543913", "5727237783"}; // Todos os EDs para soluções não domindas.
    String id = "4726543913"; // experimento que quero o resultado.
    Map<String, Map<Integer, Integer>> map = Indicators.quantityEdBySolutions(ids, id);
    
    
    Entry<String, Map<Integer, Integer>> content = map.entrySet().iterator().next();
    System.out.println(content.getKey());
    
    Map<Integer, Integer> a = content.getValue();
    
  }

  
}
