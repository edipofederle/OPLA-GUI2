package com.ufpr.br.opla.gui2;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ho.yaml.Yaml;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class ManagerApplicationConfig {
    
   
    private DirTarget configurationFile;
    
    
    public ManagerApplicationConfig(String pathConfigFile) throws FileNotFoundException{
         InputStream is = Thread.currentThread().getContextClassLoader().
					getResourceAsStream("config/application.yaml");
        this.configurationFile = Yaml.loadType(is, DirTarget.class);
    }

    public DirTarget getConfig() {
        return this.configurationFile;
    }

    public void updatePathToProfileSmarty(String newpath) {
        this.configurationFile.setPathToProfile(newpath);
        updateConfigurationFile();
    }
    
    public void updatePathToProfilePatterns(String newpath) {
        this.configurationFile.setPathToProfilePatterns(newpath);
        updateConfigurationFile();
    }
    
    public void updatePathToProfileRelationships(String newpath) {
        this.configurationFile.setPathToProfileRelationships(newpath);
        updateConfigurationFile();
    }
    
    public void updatePathToProfileConcerns(String newpath){
        this.configurationFile.setPathToProfileConcern(newpath);
        updateConfigurationFile();
    }
    
    
    public void updatePathToTemplateFiles(String newpath){
        this.configurationFile.setPathToTemplateModelsDirectory(newpath);
        updateConfigurationFile();
    }
    
    public void updatePathToExportModels(String newpath){
        this.configurationFile.setDirectoryToExportModels(newpath);
        updateConfigurationFile();
    }
    
    public void updatePathToSaveModels(String path) {
        this.configurationFile.setDirectoryToSaveModels(path);
        updateConfigurationFile();
    }
    
    
    private void updateConfigurationFile() {
        try {
            Yaml.dump(configurationFile, new File("config/application.yaml"), true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManagerApplicationConfig.class.getName()).log(Level.SEVERE, null, "Ops, Error when try update configuration file: " +ex);
        }
    }

  
    
}
