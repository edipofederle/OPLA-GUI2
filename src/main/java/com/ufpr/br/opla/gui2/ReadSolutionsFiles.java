/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author elf
 */
public class ReadSolutionsFiles {

    public static List<File> read(String experimentId, String executionId,
            String pathToOutput) {
        
        
        
        String exts[] = {"uml"};
        StringBuilder path = new StringBuilder();
        path.append(pathToOutput);
        path.append(experimentId);
        path.append("/");
        path.append(executionId);
        path.append("/");
        System.out.println(path.toString());
        List<File> files = (List<File>) FileUtils.listFiles(
                new File(path.toString()),
                exts, false);
        
     return files;
               
        
        
    }
    
}
