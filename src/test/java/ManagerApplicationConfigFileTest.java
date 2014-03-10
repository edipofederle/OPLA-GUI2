/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ufpr.br.opla.gui2.DirTarget;
import com.ufpr.br.opla.gui2.ManagerApplicationConfig;
import java.io.File;
import java.io.FileNotFoundException;
import org.ho.yaml.Yaml;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class ManagerApplicationConfigFileTest {
    
   private ManagerApplicationConfig managerConfig;
   private final String pathConfigFile = "src/test/java/config/application.yaml";
   
   @Before
   public void setUp() throws FileNotFoundException{
        managerConfig = new ManagerApplicationConfig(pathConfigFile);
   }
    
    
   @Test
   public void shouldReadFile() throws Exception{
        assertNotNull(managerConfig.getConfig());
   }
   
   @Test
   public void shouldWriteIntoConfigurationFile() throws Exception{
        assertNotNull(getConfigValue());
        
        managerConfig.updatePathToProfileSmarty("newpath");
        
        assertEquals("newpath", getConfigValue());
   }

    private String getConfigValue() throws FileNotFoundException {
        DirTarget conf = Yaml.loadType(new File(pathConfigFile), DirTarget.class);
        String profilePath = conf.getPathToProfile();
        return profilePath;
    }
    
}