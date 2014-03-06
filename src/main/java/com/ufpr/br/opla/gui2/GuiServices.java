package com.ufpr.br.opla.gui2;

import javax.swing.JTextField;


public class GuiServices {
    
    private final ManagerApplicationConfig config;
    private final String user_home;
    private final String file_separator;
    private final String profileSmartyName;
    private final String profileConcernsName;
    private final String profilePatternName;
    private final String profileRelationshipName;
    
    
    public GuiServices(ManagerApplicationConfig managerConfig){
        config = managerConfig;
       
        user_home =  System.getProperty("user.home");
        file_separator = System.getProperty("file.separator");
        
        profileSmartyName       = "smarty.profile.uml";
        profileConcernsName     = "concerns.profile.uml";
        profilePatternName      = "patterns.profile.uml";
        profileRelationshipName = "relationships.profile.uml";  
    }
    
    
    private String getDefaultPath() {
      return  user_home +"/oplatool/";
    }

    public void configureSmartyProfile(JTextField fieldSmartyProfile) {
        if(hasSmartyInConfiFile()){
          fieldSmartyProfile.setText(config.getConfig().getPathToProfile());
        }else{
            FilesManager.createPath(getDefaultPath()+"profiles");
            final String smarty = getDefaultPath()+"profiles" + this.file_separator + profileSmartyName;
            FilesManager.copyFile("resources/teste.txt", smarty); //TODO mudar para arquivo correto
            fieldSmartyProfile.setText(smarty);
            config.updatePathToProfileSmarty(smarty);
        }
    }

    public void configureConcernsProfile(JTextField fieldConcernProfile) {
        if(hasConcernsInConfiFile()){
          fieldConcernProfile.setText(config.getConfig().getPathToProfileConcern());
        }else{
            FilesManager.createPath(getDefaultPath()+"profiles");
            final String concern = getDefaultPath()+"profiles" + this.file_separator + profileConcernsName;
            FilesManager.copyFile("resources/teste.txt", concern); //TODO mudar para arquivo correto
            fieldConcernProfile.setText(concern);
            config.updatePathToProfileConcerns(concern);
        }
    }
    
    public void configurePatternsProfile(JTextField fieldPatterns ) {
       if(hasPatternsInConfigFile()){
          fieldPatterns.setText(config.getConfig().getPathToProfilePatterns());
        }else{
            FilesManager.createPath(getDefaultPath()+"profiles");
            final String pattern = getDefaultPath()+"profiles"+ this.file_separator + profilePatternName;
            FilesManager.copyFile("resources/teste.txt", pattern); //TODO mudar para arquivo correto
            fieldPatterns.setText(pattern);
            config.updatePathToProfilePatterns(pattern);
        }
    }
    
    
     public void configureRelationshipsProfile(JTextField fieldRelationships) {
        if(hasRelationshipsInConfigFile()){
          fieldRelationships.setText(config.getConfig().getPathToProfileRelationships());
        }else{
            FilesManager.createPath(getDefaultPath()+"profiles");
            final String relationship = getDefaultPath()+"profiles"+ this.file_separator +profileRelationshipName;
            FilesManager.copyFile("resources/teste.txt", relationship); //TODO mudar para arquivo correto
            fieldRelationships.setText(relationship);
            config.updatePathToProfileRelationships(relationship);
        }
    }
    
    private boolean hasRelationshipsInConfigFile() {
       return config.getConfig().getPathToProfileRelationships()!= null;
    }
    
    private boolean hasPatternsInConfigFile() {
        return config.getConfig().getPathToProfilePatterns()!= null;
    }
    
    private boolean hasSmartyInConfiFile() {
        return config.getConfig().getPathToProfile() != null;
    }
    
    private boolean hasConcernsInConfiFile() {
        return config.getConfig().getPathToProfileConcern() != null;
    }
    
    private boolean hasTemplateInConfigFile() {
        return config.getConfig().getPathToTemplateModelsDirectory() != null;
    }

    public void configureTemplates(JTextField fieldTemplate) {
        if(hasTemplateInConfigFile()){
            fieldTemplate.setText(config.getConfig().getPathToTemplateModelsDirectory());
        }else{
            FilesManager.createPath(getDefaultPath()+"templates");
            FilesManager.copyFile("resources/templates/simples.uml", getDefaultPath() + "templates"+ this.file_separator + "simples.uml");
            FilesManager.copyFile("resources/templates/simples.di", getDefaultPath() + "templates" + this.file_separator + "simples.di");
            FilesManager.copyFile("resources/templates/simples.notation", getDefaultPath() + "templates"+ this.file_separator + "simples.notation");
            final String template = getDefaultPath() + "templates/";
            fieldTemplate.setText(template);
            config.updatePathToTemplateFiles(template);
        }
    }
    
}
