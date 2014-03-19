/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.ufpr.br.opla.gui2.FilesManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author elf
 */
public class FilesManagerTest{
    
   @Test
   public void shouldCreatePathIfDontExists() throws IOException{
       String path = "tests/path";
       File filePath = new File(path);
       assertFalse(filePath.exists());
       
       FilesManager.createPath(path);
       
       assertTrue(filePath.exists());
       
       Files.delete(Paths.get(path));
   }
   
   
   @Test
   public void shuldCopyFileFromSourceToDestination() throws IOException{
       File filePath = new File("src/test/java/destination/teste.txt");
       assertFalse(filePath.exists());
       FilesManager.copyFile("src/test/java/source/teste.txt",
               "src/test/java/destination/teste.txt");
       assertTrue(filePath.exists());
       
       Files.delete(Paths.get(filePath.getPath()));
   }
    
}
