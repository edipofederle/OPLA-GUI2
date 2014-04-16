/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import java.io.File;

/**
 *
 * @author elf
 */
public class UserHome {

    private static final String user_home = System.getProperty("user.home");
    private static final String file_separator = System.getProperty("file.separator");
    private static final String home = user_home + file_separator + "oplatool" + file_separator;

    public static void copyConfigFileToUserHome() {
        //Copia arquivo de configuracao para pasta default (oplatool).
        String a = Thread.currentThread().getContextClassLoader().
                getResource("config/application.yaml").getFile();
        
        FileUtil.copy(a, home + "application.yaml");
    }
    
    public static String getOplaUserHome(){
        return System.getProperty("user.home") + System.getProperty("file.separator") + "oplatool" + System.getProperty("file.separator") ;
    }
    
    public static String getConfigurationFilePath(){
        return home + file_separator + "application.yaml";
    }
    
    public static void createDefaultOplaPathIfDontExists(){
        File f = new File(getOplaUserHome());
        if(!f.exists())
            f.mkdirs();
    }

    public static void createProfilesPath() {
      FileUtil.createPath(home + "profiles");
    }

   public static void createTemplatePath() {
     FileUtil.createPath(home + "templates");
   }

   public static void createOutputPath() {
    FileUtil.createPath(home + "output");
   }

   public static void createTempPath() {
    FileUtil.createPath(home + "temp");
   }
       
}
