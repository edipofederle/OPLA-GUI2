/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import com.ufpr.br.opla.charts.EdBar;
import com.ufpr.br.opla.configuration.UserHome;

/**
 *
 * @author elf
 */
public class EdBarTest {

  public static void main(String a[]) {
    database.Database.setPathToDB(UserHome.getPathToDb());
    String ids[] = {"5549843218", "8327246135"};
    EdBar bar = new EdBar(ids, null);
    bar.displayOnFrame();
  }
}
