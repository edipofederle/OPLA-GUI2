package com.ufpr.br.opla.gui2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class FilesManager {

public static void createPath(String path) {
       File pathDir = new File(path);
       if (!pathDir.exists()){
          System.out.println("Diretorio não existe, criando....");
          pathDir.mkdirs();
          System.out.println("Diretório "+ path + " criado com sucesso");
       }
    } 

    public static void copyFile(String sourceFile, String destinationFile) {
        Path source = Paths.get(sourceFile);
        Path target = Paths.get(destinationFile);
        if(!target.toFile().exists()){
            try {
               Files.copy(source, target);
           } catch (IOException ex) {
               Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
           }
           Logger.getLogger(main.class.getName()).log(Level.INFO, "Arquivo: {0} copiado para: {1}", new Object[]{sourceFile, destinationFile});
        }
     }
   
    
    /*
    * Get the extension of a file.
    */  
    public static String getExtension(File f) {
       String ext = null;
       String s = f.getName();
       int i = s.lastIndexOf('.');

       if (i > 0 &&  i < s.length() - 1) {
           ext = s.substring(i+1).toLowerCase();
       }
       return ext;
    }

    
}
