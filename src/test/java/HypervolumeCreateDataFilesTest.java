
import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.indicators.HypervolumeCreateDataFiles;
import com.ufpr.br.opla.indicators.HypervolumeData;
import com.ufpr.br.opla.indicators.HypervolumeGenerateObjsData;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class HypervolumeCreateDataFilesTest {
  
  @Before @Ignore
  public void setUp(){
    database.Database.setPathToDB(UserHome.getPathToDb());
    db.Database.reloadContent();
  }
  
  @Test @Ignore
  public void teste1() throws IOException{    
//    HypervolumeCreateDataFiles hcdf = new HypervolumeCreateDataFiles();
//    String[] ids = {"4911865736", "7933414618"};
//    
//    Assert.assertEquals(2,hcdf.generateHyperVolumeFiles(ids).size());
  }
  
  @Test @Ignore
  public void teste2() throws Exception{
    HypervolumeCreateDataFiles hcdf = new HypervolumeCreateDataFiles();
    String[] ids = {"9521138526", "2124944579"};
    
    Map<String, List<Double>> content = hcdf.generateHyperVolumeFiles(ids);
    List<HypervolumeData> hypers = HypervolumeGenerateObjsData.generate(content);
    
    for (HypervolumeData hypervolumeData : hypers) {
      System.out.println("PLA:"+ hypervolumeData.getPlaName());
      System.out.println("Algorithm:"+ hypervolumeData.getAlgorithm());
      System.out.println("Mean: "+hypervolumeData.getMean());
      System.out.println("Standard Dev: "+hypervolumeData.getStDev());
    }
    
  }
}
