package com.ufpr.br.opla.gui2;

import javax.swing.JTextField;


public class GuiServices {
    
    private final ManagerApplicationConfig config;
    private final String username;
    private final String os;
    private final String profileSmartyName;
    private final String profileConcernsName;
    private final String profilePatternName;
    private final String profileRelationshipName;
    
    
    public GuiServices(ManagerApplicationConfig managerConfig){
        config = managerConfig;
        os = System.getProperty("os.name");
        username =  System.getProperty("user.name");
        
        profileSmartyName       = "smarty.profile.uml";
        profileConcernsName     = "concerns.profile.uml";
        profilePatternName      = "patterns.profile.uml";
        profileRelationshipName = "relationships.profile.uml";  
    }
    
     /**
     * Returns default path based in running OS.
     * 
     * TODO Linux e Windows
     * @return 
     */
    private String getDefaultPath() {
        String osPath;
        if(os.contains("Mac OS X")){
            osPath =  "/Users/"+ username +"/oplatool/";
        }else{
            osPath = "";
        }
        return osPath;
    }

    public void configureSmartyProfile(JTextField fieldSmartyProfile) {
        if(hasSmartyInConfiFile()){
          fieldSmartyProfile.setText(config.getConfig().getPathToProfile());
        }else{
            FilesManager.createPath(getDefaultPath()+"profiles");
            final String smarty = getDefaultPath()+"profiles/"+profileSmartyName;
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
            final String concern = getDefaultPath()+"profiles/"+profileConcernsName;
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
            final String pattern = getDefaultPath()+"profiles/"+profilePatternName;
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
            final String relationship = getDefaultPath()+"profiles/"+profileRelationshipName;
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
            FilesManager.copyFile("resources/templates/simples.uml", getDefaultPath() + "templates/simples.uml");
            FilesManager.copyFile("resources/templates/simples.di", getDefaultPath() + "templates/simples.di");
            FilesManager.copyFile("resources/templates/simples.notation", getDefaultPath() + "templates/simples.notation");
            final String template = getDefaultPath() + "templates/";
            fieldTemplate.setText(template);
            config.updatePathToTemplateFiles(template);
        }
    }
    
}
