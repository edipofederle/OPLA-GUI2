
import com.ufpr.br.opla.configuration.ManagerApplicationConfig;
import com.ufpr.br.opla.gui2.GuiServices;
import java.io.FileNotFoundException;
import org.junit.Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class GuiServicesTest {
  
  @Test
  public void shouldCopyGuiSettingFileToOplaToolUserFolder() throws FileNotFoundException{
    ManagerApplicationConfig config = new ManagerApplicationConfig();
    GuiServices gs = new GuiServices(config);
    
    gs.copyFileGuiSettings();
    
    
  }
  
}
