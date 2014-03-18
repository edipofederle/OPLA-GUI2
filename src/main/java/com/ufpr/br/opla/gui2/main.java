/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ufpr.br.opla.gui2;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author elf
 */
public class main extends javax.swing.JFrame {
    
    
     private ManagerApplicationConfig config = null;
     private OplaServices oplaService = null;
    

    /**
     * Creates new form main
     */
    public main() throws ExecutionException {
        initComponents();
      

        try {
            config = new ManagerApplicationConfig("config/application.yaml");
            oplaService = new OplaServices(config);
        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Configuration file config/application.yaml not found. ");
           System.exit(0);
        }

        GuiServices guiservices = new GuiServices(config);
        guiservices.configureSmartyProfile(fieldSmartyProfile);
        guiservices.configureConcernsProfile(fieldConcernProfile);
        guiservices.configurePatternsProfile(fieldPatterns);
        guiservices.configureRelationshipsProfile(fieldRelationships);
        guiservices.configureTemplates(fieldTemplate);
        
        guiservices.configureLocaleToSaveModels(fieldOutput);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fieldSmartyProfile = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        fieldConcernProfile = new javax.swing.JTextField();
        btnSmartyProfile = new javax.swing.JButton();
        btnConcernProfile = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        fieldArchitectureInput = new javax.swing.JTextField();
        btnInput = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fieldTemplate = new javax.swing.JTextField();
        btnTemplate = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        fieldPatterns = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fieldRelationships = new javax.swing.JTextField();
        btnPatternProfile = new javax.swing.JButton();
        btnRelationshipProfile = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        fieldOutput = new javax.swing.JTextField();
        btnOutput = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("SMarty Profile:");

        fieldSmartyProfile.setName("pathToSmarty"); // NOI18N
        fieldSmartyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSmartyProfileActionPerformed(evt);
            }
        });

        jLabel2.setText("Concerns Profile:");

        fieldConcernProfile.setName("sdfs"); // NOI18N
        fieldConcernProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldConcernProfileActionPerformed(evt);
            }
        });

        btnSmartyProfile.setText("Browser...");
        btnSmartyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmartyProfileActionPerformed(evt);
            }
        });

        btnConcernProfile.setText("Browser...");
        btnConcernProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConcernProfileActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("Templates Configuration");
        jLabel3.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel4.setText("Select where you want save outputs");

        jLabel5.setText("Architecture (should be .uml file)");

        btnInput.setText("Browser...");
        btnInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel6.setText("Profiles Configuration");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel7.setText("about templates");
        jLabel7.setToolTipText("<html><h3>About Templtes</h3><br/>\n\nTexto explicando brevemente o que são os templates e para que servem.");

        jLabel8.setText("Templates Directory:");

        btnTemplate.setText("Select a Directory...");
        btnTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemplateActionPerformed(evt);
            }
        });

        jLabel9.setText("Patterns Profile:");

        fieldPatterns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPatternsActionPerformed(evt);
            }
        });

        jLabel10.setText("Relationships Profile:");

        btnPatternProfile.setText("Browser...");
        btnPatternProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatternProfileActionPerformed(evt);
            }
        });

        btnRelationshipProfile.setText("Browser...");
        btnRelationshipProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelationshipProfileActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel11.setText("Select a Architecture as Input");

        jLabel12.setText("Output:");

        fieldOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldOutputActionPerformed(evt);
            }
        });

        btnOutput.setText("Browser...");
        btnOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutputActionPerformed(evt);
            }
        });

        jButton1.setText("Visualize your application.yaml file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Teste Rodar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fieldArchitectureInput, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInput)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(fieldTemplate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(454, 454, 454)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(fieldConcernProfile, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                                                    .addComponent(fieldSmartyProfile))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnSmartyProfile)
                                                    .addComponent(btnConcernProfile))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10)
                                            .addComponent(fieldPatterns)
                                            .addComponent(fieldRelationships, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnPatternProfile)
                                            .addComponent(btnRelationshipProfile))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel5)))
                                        .addGap(261, 261, 261)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(fieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btnOutput))
                                                    .addComponent(jLabel12))))))
                                .addGap(0, 108, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(207, 207, 207))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldSmartyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmartyProfile)
                    .addComponent(fieldPatterns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPatternProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldConcernProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConcernProfile)
                    .addComponent(fieldRelationships, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRelationshipProfile))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTemplate))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldArchitectureInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInput)
                    .addComponent(btnOutput))
                .addGap(80, 80, 80)
                .addComponent(jButton2)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Path Confs", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Paths Confs");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputActionPerformed
        String path = fileChooser(fieldArchitectureInput, "uml");
        
        VolatileConfs.setArchitectureInputPath(path);
    }//GEN-LAST:event_btnInputActionPerformed

    private void btnConcernProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcernProfileActionPerformed
        String newPath = fileChooser(fieldConcernProfile, "uml");
        if(newPath.equals("")){
            this.config.updatePathToProfileConcerns(fieldConcernProfile.getText());
        }else{
            this.config.updatePathToProfileConcerns(newPath);
        }
    }//GEN-LAST:event_btnConcernProfileActionPerformed

    private void btnSmartyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmartyProfileActionPerformed
        String newPath = fileChooser(fieldSmartyProfile, "uml");
        if(newPath.equals("")){
            this.config.updatePathToProfileSmarty(fieldSmartyProfile.getText());
        }else{
            this.config.updatePathToProfileSmarty(newPath);
        }
    }//GEN-LAST:event_btnSmartyProfileActionPerformed

    private void fieldConcernProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldConcernProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldConcernProfileActionPerformed

    private void fieldSmartyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSmartyProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldSmartyProfileActionPerformed

    private void btnTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTemplateActionPerformed
        String path  = dirChooser(fieldTemplate);
        if("".equals(path)){
            this.config.updatePathToTemplateFiles(fieldTemplate.getText());
        }else{
            this.config.updatePathToTemplateFiles(path);
        }
    }//GEN-LAST:event_btnTemplateActionPerformed

    private String dirChooser(JTextField field) throws HeadlessException {
        JFileChooser c = new JFileChooser();
        String path;
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int rVal = c.showOpenDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            path = c.getSelectedFile().getAbsolutePath();
            field.setText(path);
            field.updateUI();
            return path;
        }
        return "";
    }

    private void btnRelationshipProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelationshipProfileActionPerformed
        String newPath = fileChooser(fieldRelationships, "uml");
        if(newPath.equals("")){
            this.config.updatePathToProfileRelationships(fieldRelationships.getText());
        }else{
            this.config.updatePathToProfileRelationships(newPath);
        }
    }//GEN-LAST:event_btnRelationshipProfileActionPerformed

    private void fieldPatternsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPatternsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPatternsActionPerformed

    private void btnPatternProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatternProfileActionPerformed
      String newPath = fileChooser(fieldPatterns, "uml");
        if("".equals(newPath)){
            this.config.updatePathToProfilePatterns(fieldPatterns.getText());
        }else{
            this.config.updatePathToProfilePatterns(newPath);
        }
    }//GEN-LAST:event_btnPatternProfileActionPerformed

    private void btnOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutputActionPerformed
        String path = dirChooser(fieldOutput);
        if("".equals(path)){
            this.config.updatePathToSaveModels(fieldOutput.getText());
        }else{
            this.config.updatePathToSaveModels(path);
        }
                
    }//GEN-LAST:event_btnOutputActionPerformed

    private void fieldOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldOutputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldOutputActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StringBuffer sb = new StringBuffer();
        sb.append("Application.yml file content").append("\n\n");
        sb.append("directoryToExportModels:").append(config.getConfig().getDirectoryToExportModels()).append("\n");
        sb.append("pathToProfile:").append(config.getConfig().getPathToProfile()).append("\n");
        sb.append("pathToProfileConcern:").append(config.getConfig().getPathToProfileConcern()).append("\n");
        sb.append("pathToProfilePatterns").append(config.getConfig().getPathToProfilePatterns()).append("\n");
        sb.append("pathToProfileRelationships").append(config.getConfig().getPathToProfileRelationships()).append("\n");
        sb.append("pathToTemplateModelsDirectory").append(config.getConfig().getPathToTemplateModelsDirectory()).append("\n");
        JOptionPane.showMessageDialog(null, sb);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        oplaService.run();
    }//GEN-LAST:event_jButton2ActionPerformed

    private String fileChooser(JTextField fieldToSet, String allowExtension) throws HeadlessException {
        JFileChooser c = new JFileChooser();
        int rVal = c.showOpenDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {

            File f = new File(c.getCurrentDirectory() + c.getSelectedFile().getName());
            String ext = FilesManager.getExtension(f);
            
            if(!ext.equalsIgnoreCase(allowExtension)){
                JOptionPane.showMessageDialog(null, "The selected file is not allowed. You need selects a file with extension .uml, but you selects a ."+ext + " file");
                return "";
            }else{
                final String path = c.getCurrentDirectory() + "/"+ c.getSelectedFile().getName();
                fieldToSet.setText(path);
                fieldToSet.updateUI();
                return path;
                
            }
        }
        
        return "";
        
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcernProfile;
    private javax.swing.JButton btnInput;
    private javax.swing.JButton btnOutput;
    private javax.swing.JButton btnPatternProfile;
    private javax.swing.JButton btnRelationshipProfile;
    private javax.swing.JButton btnSmartyProfile;
    private javax.swing.JButton btnTemplate;
    private javax.swing.JTextField fieldArchitectureInput;
    private javax.swing.JTextField fieldConcernProfile;
    private javax.swing.JTextField fieldOutput;
    private javax.swing.JTextField fieldPatterns;
    private javax.swing.JTextField fieldRelationships;
    private javax.swing.JTextField fieldSmartyProfile;
    private javax.swing.JTextField fieldTemplate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables


 }
