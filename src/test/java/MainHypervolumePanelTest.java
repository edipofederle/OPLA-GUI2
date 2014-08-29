
import com.ufpr.br.opla.configuration.UserHome;
import com.ufpr.br.opla.gui2.HypervolumeWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class MainHypervolumePanelTest {
  
  public static void main(String args[]){
    try {
      database.Database.setPathToDB(UserHome.getPathToDb());
      db.Database.reloadContent();
          
      HypervolumeWindow hyperPanel = new HypervolumeWindow();
      hyperPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      hyperPanel.pack();
      hyperPanel.setResizable(false);
      hyperPanel.setVisible(true);
      
       String[] ids = {"9951836391"};
       
      hyperPanel.loadData(ids);
      
    } catch (IOException ex) {
      Logger.getLogger(MainHypervolumePanelTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
}
