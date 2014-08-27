/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import com.ufpr.br.opla.charts.EdLine;
import com.ufpr.br.opla.configuration.UserHome;

/**
 *
 * @author elf
 */
public class EdLineTest {
  
    public static void main(String a[]) {
    database.Database.setPathToDB(UserHome.getPathToDb());
    String ids[] = {"5549843218", "8327246135"};
    EdLine line = new EdLine(ids, "Title");
    line.displayOnFrame();
  }
  
}
