package com.ufpr.br.opla.gui2;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class GuiServices {

    private static final String PROFILES = "profiles";
    private static final String user_home = System.getProperty("user.home");
    private static final String file_separator = "/";
    private final ManagerApplicationConfig config;
    private final String profileSmartyName;
    private final String profileConcernsName;
    private final String profilePatternName;
    private final String profileRelationshipName;

    public GuiServices(ManagerApplicationConfig managerConfig) {
        config = managerConfig;

        profileSmartyName = "smarty.profile.uml";
        profileConcernsName = "concerns.profile.uml";
        profilePatternName = "patterns.profile.uml";
        profileRelationshipName = "relationships.profile.uml";
    }

    public void configureSmartyProfile(JTextField fieldSmartyProfile, JCheckBox check, JButton button) {
        if(config.getConfig().getPathToProfile() != null && config.getConfig().getPathToProfile().equals("")){
            check.setSelected(true);
            button.setEnabled(false);
        }else if (hasSmartyInConfiFile()) {
            fieldSmartyProfile.setText(config.getConfig().getPathToProfile());
        } else {
            String source = PROFILES + file_separator + profileSmartyName;
            final String target = UserHome.getOplaUserHome() + "profiles" + file_separator + profileSmartyName;
            FileUtil.copy(source, target);

            fieldSmartyProfile.setText(target);
            fieldSmartyProfile.updateUI();
            config.updatePathToProfileSmarty(target);
        }
    }

    public void configureConcernsProfile(JTextField fieldConcernProfile, JCheckBox check, JButton button) {
         if(config.getConfig().getPathToProfileConcern() != null && config.getConfig().getPathToProfileConcern().equals("")){
            check.setSelected(true);
            button.setEnabled(false);
        }else if (hasConcernsInConfiFile()) {
            fieldConcernProfile.setText(config.getConfig().getPathToProfileConcern());
        } else {

            String source = PROFILES + file_separator + profileConcernsName;

            final String concernDest = UserHome.getOplaUserHome() + "profiles" + file_separator + profileConcernsName;
            FileUtil.copy(source, concernDest);
            
            fieldConcernProfile.setText(concernDest);
            fieldConcernProfile.updateUI();
            config.updatePathToProfileConcerns(concernDest);
        }
    }

    public void configurePatternsProfile(JTextField fieldPatterns, JCheckBox check, JButton button) {   
        if(config.getConfig().getPathToProfilePatterns() != null && config.getConfig().getPathToProfilePatterns().equals("")){
            check.setSelected(true);
            button.setEnabled(false);
        }else if (hasPatternsInConfigFile()) {
            fieldPatterns.setText(config.getConfig().getPathToProfilePatterns());
        } else {

            String source = PROFILES + file_separator + profilePatternName;
            
            final String patternDest = UserHome.getOplaUserHome() + "profiles" + file_separator + profilePatternName;
            FileUtil.copy(source, patternDest);
            
            fieldPatterns.setText(patternDest);
            fieldPatterns.updateUI();
            config.updatePathToProfilePatterns(patternDest);
        }
    }

    public void configureRelationshipsProfile(JTextField fieldRelationships, JCheckBox check, JButton button) {
        if(config.getConfig().getPathToProfileRelationships() != null && config.getConfig().getPathToProfileRelationships().equals("")){
            check.setSelected(true);
            button.setEnabled(false);
        }else 
        if (hasRelationshipsInConfigFile()) {
            fieldRelationships.setText(config.getConfig().getPathToProfileRelationships());
        } else {

            String source = PROFILES + file_separator + profileRelationshipName;

            final String relationshipDest = UserHome.getOplaUserHome() + "profiles" + file_separator + profileRelationshipName;
            
            
            FileUtil.copy(source, relationshipDest);

            
            fieldRelationships.setText(relationshipDest);
            fieldRelationships.updateUI();
            config.updatePathToProfileRelationships(relationshipDest);
        }
    }

    public void configureTemplates(JTextField fieldTemplate) {
        if (hasTemplateInConfigFile()) {
            fieldTemplate.setText(config.getConfig().getPathToTemplateModelsDirectory());
        } else {
           FileUtil.copy("templates/simples.uml", UserHome.getOplaUserHome() + "templates" + file_separator + "simples.uml");
            FileUtil.copy("templates/simples.di", UserHome.getOplaUserHome() + "templates" + this.file_separator + "simples.di");
            FileUtil.copy("templates/simples.notation", UserHome.getOplaUserHome() + "templates" + this.file_separator + "simples.notation");

            final String template = UserHome.getOplaUserHome() + "templates/";
            fieldTemplate.setText(template);
            fieldTemplate.updateUI();
            config.updatePathToTemplateFiles(template);
        }
    }

    /**
     * Saida, arquivos uml, di e notation.
     *
     * @param fieldOutput
     */
    public void configureLocaleToExportModels(JTextField fieldOutput) {
        if (hasPathToSaveModelsInConfigFile()) {
            fieldOutput.setText(config.getConfig().getDirectoryToExportModels());
        } else {
            final String path = UserHome.getOplaUserHome() + "output/";
            fieldOutput.setText(path);
            fieldOutput.updateUI();
            config.updatePathToExportModels(path);
        }

    }

    /**
     * Diretorio usado no processo de maniuplacao.
     *
     * @param fieldManipulationDir
     */
    public void configureLocaleToSaveModels(JTextField fieldManipulationDir) {
        if (hasPathToManipulationDir()) {
            fieldManipulationDir.setText(config.getConfig().getDirectoryToSaveModels());
        } else {
            final String path = UserHome.getOplaUserHome() + "temp/";
            fieldManipulationDir.setText(path);
            fieldManipulationDir.updateUI();
            config.updatePathToSaveModels(path);
        }
    }

    private boolean hasRelationshipsInConfigFile() {
        return config.getConfig().getPathToProfileRelationships() != null;
    }

    private boolean hasPatternsInConfigFile() {
        return config.getConfig().getPathToProfilePatterns() != null;
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

    private boolean hasPathToSaveModelsInConfigFile() {
        return config.getConfig().getDirectoryToExportModels() != null;
    }

    private boolean hasPathToManipulationDir() {
        return config.getConfig().getDirectoryToSaveModels() != null;
    }
}
