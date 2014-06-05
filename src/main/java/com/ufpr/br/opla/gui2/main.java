/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import com.ufpr.br.opla.algorithms.NSGAII;
import com.ufpr.br.opla.algorithms.PAES;
import com.ufpr.br.opla.experiementsUtils.MutationOperatorsSelected;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import jmetal.experiments.*;
import metrics.Conventional;
import metrics.Elegance;
import metrics.FeatureDriven;
import metrics.PLAExtensibility;
import results.Execution;

/**
 *
 * @author elf
 */
public class main extends javax.swing.JFrame {

  private ManagerApplicationConfig config = null;
  //private OplaServices oplaService = null;
  private String pathSmartyBck;
  private String pathConcernBck;
  private String pathRelationchipBck;
  private String pathPatternBck;
  private String crossoverProbabilityBck;
  private String mutationProbabilityBck;
  private String selectedExperiment;
  private String selectedExecution;

  /**
   * Creates new form main
   */
  public main() throws Exception {

    initComponents();

    configureDb();
    initAlgorithmsCombo();
    disableFieldsOnStart();
    checkAllMutationOperatorsByDefault();
    hidePanelMutationOperatorsByDefault();
    hidePanelCrossoverProbabilityByDefault();
    hidePanelMutationProbabilityByDefault();
    hidePanelSolutionsByDefault();
    hidePanelShowMetricsByDefault();
    checkAllMetricsByDefault();
    initiExecutedExperiments();
    panelExecutions.setVisible(false);
    panelEds.setVisible(false);

    try {
      UserHome.createDefaultOplaPathIfDontExists();

      String source = "config/application.yaml";
      String target = UserHome.getOplaUserHome() + "application.yaml";

      //Somente copia arquivo de configuracao se
      //ainda nao existir na pasta da oplatool do usuario
      if (!(new File(target).exists())) {
        Utils.copy(source, target);
      }

      UserHome.createProfilesPath();
      UserHome.createTemplatePath();
      UserHome.createOutputPath();
      UserHome.createTempPath(); //Manipulation dir. apenas para uso intenro

      config = new ManagerApplicationConfig();
    } catch (FileNotFoundException ex) {
      java.util.logging.Logger.getLogger(main.class.getName()).log(Level.SEVERE, ex.getMessage());
    }

    //Text Field are disabled
    fieldSmartyProfile.setEditable(false);
    fieldConcernProfile.setEditable(false);
    // fieldPatterns.setEditable(false);
    //  fieldRelationships.setEditable(false);


    GuiServices guiservices = new GuiServices(config);
    guiservices.configureSmartyProfile(fieldSmartyProfile, checkSmarty, btnSmartyProfile);
    guiservices.configureConcernsProfile(fieldConcernProfile, checkConcerns, btnConcernProfile);
    // guiservices.configurePatternsProfile(fieldPatterns, checkPatterns, btnPatternProfile);
    // guiservices.configureRelationshipsProfile(fieldRelationships, checkRelationship, btnRelationshipProfile);
    guiservices.configureTemplates(fieldTemplate);
    guiservices.configureLocaleToSaveModels(fieldManipulationDir);

    guiservices.configureLocaleToExportModels(fieldOutput);
  }

  private void addOrRemoveOperatorMutation(final String operatorName) {
    if (!checkFeatureMutation.isSelected()) {
      MutationOperatorsSelected.getSelectedMutationOperators().remove(operatorName);
    } else {
      MutationOperatorsSelected.getSelectedMutationOperators().add(operatorName);
    }
  }

  private void addToMetrics(JCheckBox check, final String metric) {
    if (check.isSelected()) {
      VolatileConfs.getMetricsSelecteds().add(metric);
    } else {
      VolatileConfs.getMetricsSelecteds().remove(metric);
    }
  }

  private void createTableExecutions(String idExperiment) throws HeadlessException {
    panelExecutions.setVisible(true);
    DefaultTableModel modelTableExecutions = new DefaultTableModel();
    modelTableExecutions.addColumn("Execution");
    modelTableExecutions.addColumn("Time (ms)");
    modelTableExecutions.addColumn("Generated Solutions");
    modelTableExecutions.addColumn("Non Dominated Solutons");
    tableExecutions.setModel(modelTableExecutions);

    GuiUtils.makeTableNotEditable(tableExecutions);

    try {
      List<Execution> all = db.Database.getAllExecutionsByExperimentId(idExperiment);
      for (Execution exec : all) {
        Object[] row = new Object[5];
        row[0] = exec.getId();
        row[1] = exec.getTime();
        int numberNonDominatedSolutions = ReadSolutionsFiles.countNumberNonDominatedSolutins(idExperiment, this.config.getConfig().getDirectoryToExportModels());
        int numberSolutions = ReadSolutionsFiles.read(idExperiment,
                exec.getId(),
                this.config.getConfig().getDirectoryToExportModels()).size();
        row[2] = numberSolutions - numberNonDominatedSolutions;
        row[3] = numberNonDominatedSolutions;
        modelTableExecutions.addRow(row);
      }
    } catch (Exception e) {
      System.out.println(e);
      JOptionPane.showMessageDialog(null,
              "Possibly the data are not found on disk",
              "Erro when try load data.", 0);
    }
  }

  private void executeNSGAII() {
    NSGAII nsgaii = new NSGAII();
    nsgaii.execute(comboAlgorithms, checkMutation, fieldMutationProb,
            fieldArchitectureInput, fieldNumberOfRuns, fieldPopulationSize,
            fieldMaxEvaluations, checkCrossover,
            fieldCrossoverProbability);
  }

  private void executePAES() {
    PAES paes = new PAES();
    paes.execute(comboAlgorithms, checkMutation, fieldMutationProb,
            fieldArchitectureInput, fieldNumberOfRuns, fieldPaesArchiveSize,
            fieldMaxEvaluations, checkCrossover,
            fieldCrossoverProbability);
  }

  private void initAlgorithmsCombo() {
    String algoritms[] = {"Select One", "NSGA-II", "PAES"};
    comboAlgorithms.removeAllItems();

    for (int i = 0; i < algoritms.length; i++) {
      comboAlgorithms.addItem(algoritms[i]);
    }

    comboAlgorithms.setSelectedIndex(0);
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
        jTabbedPane1.addChangeListener(changeListener);
        ApplicationConfs = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        checkSmarty = new javax.swing.JCheckBox();
        checkConcerns = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        fieldSmartyProfile = new javax.swing.JTextField();
        btnSmartyProfile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        fieldConcernProfile = new javax.swing.JTextField();
        btnConcernProfile = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fieldTemplate = new javax.swing.JTextField();
        btnTemplate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        fieldManipulationDir = new javax.swing.JTextField();
        btnManipulationDir = new javax.swing.JButton();
        algorithms = new javax.swing.JPanel();
        panelExperimentSettings = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fieldNumberOfRuns = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldMaxEvaluations = new javax.swing.JTextField();
        panelCrossProb = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        fieldCrossoverProbability = new javax.swing.JTextField();
        crossProbSlider = new javax.swing.JSlider();
        panelMutationProb = new javax.swing.JPanel();
        mutatinProbSlider = new javax.swing.JSlider();
        fieldMutationProb = new javax.swing.JTextField();
        checkMutation = new javax.swing.JCheckBox();
        labelOperators = new javax.swing.JLabel();
        checkCrossover = new javax.swing.JCheckBox();
        comboAlgorithms = new javax.swing.JComboBox();
        labelAlgorithms = new javax.swing.JLabel();
        panelMetrics = new javax.swing.JPanel();
        checkConventional = new javax.swing.JCheckBox();
        checkElegance = new javax.swing.JCheckBox();
        checkPLAExt = new javax.swing.JCheckBox();
        checkFeatureDriven = new javax.swing.JCheckBox();
        panelOperatorsMutation = new javax.swing.JPanel();
        checkFeatureMutation = new javax.swing.JCheckBox();
        checkMoveMethod = new javax.swing.JCheckBox();
        checkMoveOperation = new javax.swing.JCheckBox();
        checkManagerClass = new javax.swing.JCheckBox();
        checkMoveAttribute = new javax.swing.JCheckBox();
        checkAddClass = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        fieldPopulationSize = new javax.swing.JTextField();
        labelArchivePAES = new javax.swing.JLabel();
        fieldPaesArchiveSize = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fieldArchitectureInput = new javax.swing.JTextArea();
        btnCleanListArchs1 = new javax.swing.JButton();
        btnInput1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        fieldOutput = new javax.swing.JTextField();
        btnOutput = new javax.swing.JButton();
        btnRun = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        experiments = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableExp = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        panelExecutions = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableExecutions = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        panelSolutions = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        comboSolutions = new javax.swing.JComboBox();
        panelObjectives = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableObjectives = new javax.swing.JTable();
        comboMetrics = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        panelShowMetrics = new javax.swing.JPanel();
        labelSelectedMetricTitle = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableMetrics = new javax.swing.JTable();
        panelEds = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        edTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OPLA-Tool 0.0.1");

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jButton1.setText("Visualize your application.yaml file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profiles Configuration", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        checkSmarty.setText("SMarty");
        checkSmarty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSmartyActionPerformed(evt);
            }
        });

        checkConcerns.setText("Concerns");
        checkConcerns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkConcernsActionPerformed(evt);
            }
        });

        jLabel1.setText("SMarty Profile:");

        fieldSmartyProfile.setName("pathToSmarty"); // NOI18N
        fieldSmartyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSmartyProfileActionPerformed(evt);
            }
        });

        btnSmartyProfile.setText("Browser...");
        btnSmartyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSmartyProfileActionPerformed(evt);
            }
        });

        jLabel2.setText("Concerns Profile:");

        fieldConcernProfile.setName("sdfs"); // NOI18N
        fieldConcernProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldConcernProfileActionPerformed(evt);
            }
        });

        btnConcernProfile.setText("Browser...");
        btnConcernProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConcernProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldSmartyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSmartyProfile))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(checkSmarty)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkConcerns))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldConcernProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConcernProfile)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkSmarty)
                    .addComponent(checkConcerns))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldSmartyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSmartyProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldConcernProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConcernProfile))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Template Configuration", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel7.setText("about templates");
        jLabel7.setToolTipText("<html><h3>About Templtes</h3><br/>\n\nTexto explicando brevemente o que são os templates e para que servem.");

        jLabel8.setText(" Directory:");

        btnTemplate.setText("Select a Directory...");
        btnTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTemplateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(fieldTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTemplate))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manipulation Directory", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel14.setText("about manipulation directory");

        jLabel15.setText("Directory");

        btnManipulationDir.setText("Select a Directory...");
        btnManipulationDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManipulationDirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fieldManipulationDir, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnManipulationDir)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldManipulationDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManipulationDir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ApplicationConfsLayout = new javax.swing.GroupLayout(ApplicationConfs);
        ApplicationConfs.setLayout(ApplicationConfsLayout);
        ApplicationConfsLayout.setHorizontalGroup(
            ApplicationConfsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApplicationConfsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ApplicationConfsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(ApplicationConfsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(354, Short.MAX_VALUE))
        );
        ApplicationConfsLayout.setVerticalGroup(
            ApplicationConfsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ApplicationConfsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General Configurations", ApplicationConfs);

        algorithms.setName("algorithms");

        panelExperimentSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Experiment Settings", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        jLabel3.setText("Number of Runs:");

        fieldNumberOfRuns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNumberOfRunsActionPerformed(evt);
            }
        });
        fieldNumberOfRuns.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                numberOfRunsFocusLost(evt);
            }
        });
        fieldNumberOfRuns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldNumberOfRunsKeyTyped(evt);
            }
        });

        jLabel4.setText("Max Evaluations:");

        fieldMaxEvaluations.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldMaxEvaluationsFocusLost(evt);
            }
        });
        fieldMaxEvaluations.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldMaxEvaluationsKeyTyped(evt);
            }
        });

        panelCrossProb.setBorder(javax.swing.BorderFactory.createTitledBorder("Crossover Probability"));

        fieldCrossoverProbability.setText("0.9");
        fieldCrossoverProbability.setEnabled(false);

        crossProbSlider.setMajorTickSpacing(1);
        crossProbSlider.setMaximum(10);
        crossProbSlider.setMinimum(1);
        crossProbSlider.setMinorTickSpacing(1);
        crossProbSlider.setPaintLabels(true);
        crossProbSlider.setPaintTicks(true);
        crossProbSlider.setSnapToTicks(true);
        crossProbSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                crossProbSliderMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout panelCrossProbLayout = new javax.swing.GroupLayout(panelCrossProb);
        panelCrossProb.setLayout(panelCrossProbLayout);
        panelCrossProbLayout.setHorizontalGroup(
            panelCrossProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrossProbLayout.createSequentialGroup()
                .addComponent(crossProbSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fieldCrossoverProbability, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(151, 151, 151))
        );
        panelCrossProbLayout.setVerticalGroup(
            panelCrossProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrossProbLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCrossProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crossProbSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCrossProbLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fieldCrossoverProbability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMutationProb.setBorder(javax.swing.BorderFactory.createTitledBorder("Mutation Probability"));

        mutatinProbSlider.setMajorTickSpacing(1);
        mutatinProbSlider.setMaximum(10);
        mutatinProbSlider.setMinimum(1);
        mutatinProbSlider.setMinorTickSpacing(1);
        mutatinProbSlider.setPaintLabels(true);
        mutatinProbSlider.setPaintTicks(true);
        mutatinProbSlider.setSnapToTicks(true);
        mutatinProbSlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mutatinProbSliderMouseDragged(evt);
            }
        });

        fieldMutationProb.setText("0.9");
        fieldMutationProb.setEnabled(false);

        javax.swing.GroupLayout panelMutationProbLayout = new javax.swing.GroupLayout(panelMutationProb);
        panelMutationProb.setLayout(panelMutationProbLayout);
        panelMutationProbLayout.setHorizontalGroup(
            panelMutationProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMutationProbLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mutatinProbSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldMutationProb, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMutationProbLayout.setVerticalGroup(
            panelMutationProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMutationProbLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelMutationProbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mutatinProbSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMutationProbLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(fieldMutationProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checkMutation.setText("Mutation");
        checkMutation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMutationActionPerformed(evt);
            }
        });

        labelOperators.setText("Select operators which want to use");

        checkCrossover.setText("Crossover");
        checkCrossover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCrossoverActionPerformed(evt);
            }
        });

        comboAlgorithms.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboAlgorithms.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAlgorithmsItemStateChanged(evt);
            }
        });
        comboAlgorithms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAlgorithmsActionPerformed(evt);
            }
        });

        labelAlgorithms.setText("Select algorithm which want to use");

        panelMetrics.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Metrics", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        checkConventional.setText("Conventional");
        checkConventional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkConventionalActionPerformed(evt);
            }
        });

        checkElegance.setText("Elegance");
        checkElegance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEleganceActionPerformed(evt);
            }
        });

        checkPLAExt.setText("PLA Extensibility");
        checkPLAExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPLAExtActionPerformed(evt);
            }
        });

        checkFeatureDriven.setText("Feature Driven");
        checkFeatureDriven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFeatureDrivenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMetricsLayout = new javax.swing.GroupLayout(panelMetrics);
        panelMetrics.setLayout(panelMetricsLayout);
        panelMetricsLayout.setHorizontalGroup(
            panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMetricsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkConventional)
                    .addComponent(checkElegance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkFeatureDriven)
                    .addComponent(checkPLAExt))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        panelMetricsLayout.setVerticalGroup(
            panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMetricsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkConventional)
                    .addComponent(checkPLAExt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkElegance)
                    .addComponent(checkFeatureDriven))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelOperatorsMutation.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Mutation Operators wich want to use", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        checkFeatureMutation.setText("Feature Mutation");
        checkFeatureMutation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkFeatureMutationActionPerformed(evt);
            }
        });

        checkMoveMethod.setText("Move Method Mutation");
        checkMoveMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMoveMethodActionPerformed(evt);
            }
        });

        checkMoveOperation.setText("Move Operation Mutation");
        checkMoveOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMoveOperationActionPerformed(evt);
            }
        });

        checkManagerClass.setText("Add Manager Class Mutation");

        checkMoveAttribute.setText("Move Attribute Mutation");
        checkMoveAttribute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMoveAttributeActionPerformed(evt);
            }
        });

        checkAddClass.setText("Add Class Mutation");
        checkAddClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAddClassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOperatorsMutationLayout = new javax.swing.GroupLayout(panelOperatorsMutation);
        panelOperatorsMutation.setLayout(panelOperatorsMutationLayout);
        panelOperatorsMutationLayout.setHorizontalGroup(
            panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperatorsMutationLayout.createSequentialGroup()
                .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelOperatorsMutationLayout.createSequentialGroup()
                            .addComponent(checkFeatureMutation)
                            .addGap(35, 35, 35))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperatorsMutationLayout.createSequentialGroup()
                            .addComponent(checkMoveMethod)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(panelOperatorsMutationLayout.createSequentialGroup()
                        .addComponent(checkAddClass)
                        .addGap(27, 27, 27)))
                .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkMoveAttribute)
                    .addComponent(checkManagerClass)
                    .addComponent(checkMoveOperation))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelOperatorsMutationLayout.setVerticalGroup(
            panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperatorsMutationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkFeatureMutation)
                    .addComponent(checkMoveOperation))
                .addGap(5, 5, 5)
                .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkMoveMethod)
                    .addComponent(checkManagerClass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperatorsMutationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkAddClass)
                    .addComponent(checkMoveAttribute))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Population Size:");

        fieldPopulationSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldPopulationSizeFocusLost(evt);
            }
        });
        fieldPopulationSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fieldPopulationSizeKeyTyped(evt);
            }
        });

        labelArchivePAES.setText("Archive Size:");

        javax.swing.GroupLayout panelExperimentSettingsLayout = new javax.swing.GroupLayout(panelExperimentSettings);
        panelExperimentSettings.setLayout(panelExperimentSettingsLayout);
        panelExperimentSettingsLayout.setHorizontalGroup(
            panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                        .addComponent(panelCrossProb, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelMutationProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelAlgorithms)
                    .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelOperators)
                            .addComponent(comboAlgorithms, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(fieldNumberOfRuns, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                                .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                                    .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fieldMaxEvaluations, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(fieldPopulationSize))))
                            .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                                .addComponent(labelArchivePAES)
                                .addGap(30, 30, 30)
                                .addComponent(fieldPaesArchiveSize, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(checkMutation)
                                .addGap(18, 18, 18)
                                .addComponent(checkCrossover)))
                        .addGap(26, 26, 26)
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelOperatorsMutation, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        panelExperimentSettingsLayout.setVerticalGroup(
            panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                        .addComponent(labelAlgorithms)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAlgorithms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(fieldNumberOfRuns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fieldMaxEvaluations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOperatorsMutation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelExperimentSettingsLayout.createSequentialGroup()
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fieldPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelArchivePAES)
                            .addComponent(fieldPaesArchiveSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelOperators, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkCrossover, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkMutation))))
                .addGap(18, 18, 18)
                .addGroup(panelExperimentSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelCrossProb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelMutationProb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(113, 113, 113))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Architecture(s)", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        jLabel11.setText("A list of paths separated by comma");

        fieldArchitectureInput.setColumns(20);
        fieldArchitectureInput.setRows(5);
        jScrollPane2.setViewportView(fieldArchitectureInput);

        btnCleanListArchs1.setText("Clean");
        btnCleanListArchs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanListArchs1ActionPerformed(evt);
            }
        });

        btnInput1.setText("Confirme");
        btnInput1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInput1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnInput1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCleanListArchs1))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCleanListArchs1)
                    .addComponent(btnInput1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select where you want save outputs", 0, 0, new java.awt.Font("Verdana", 1, 14), java.awt.Color.magenta)); // NOI18N

        fieldOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldOutputActionPerformed(evt);
            }
        });

        btnOutput.setText("Select a Directory...");
        btnOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnOutput)
                    .addComponent(fieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOutput)
                .addContainerGap())
        );

        btnRun.setBackground(new java.awt.Color(255, 204, 102));
        btnRun.setForeground(new java.awt.Color(0, 153, 0));
        btnRun.setText("RUN");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        jLabel12.setText("Status:");

        javax.swing.GroupLayout algorithmsLayout = new javax.swing.GroupLayout(algorithms);
        algorithms.setLayout(algorithmsLayout);
        algorithmsLayout.setHorizontalGroup(
            algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(algorithmsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(algorithmsLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRun)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, algorithmsLayout.createSequentialGroup()
                        .addComponent(panelExperimentSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addGap(9, 9, 9)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        algorithmsLayout.setVerticalGroup(
            algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(algorithmsLayout.createSequentialGroup()
                .addGroup(algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(algorithmsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelExperimentSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(algorithmsLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(algorithmsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(algorithmsLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Experiment Configurations", algorithms);

        experiments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                experimentsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                experimentsMouseEntered(evt);
            }
        });
        experiments.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                experimentsFocusGained(evt);
            }
        });

        tableExp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "name", "algorithm", "Created at"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableExp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableExpMouseClicked(evt);
            }
        });
        tableExp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableExpKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableExp);

        jLabel13.setFont(new java.awt.Font("Monaco", 1, 18)); // NOI18N
        jLabel13.setText("Experiments");

        tableExecutions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableExecutions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableExecutionsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableExecutionsMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tableExecutions);

        jLabel16.setFont(new java.awt.Font("Monaco", 1, 18)); // NOI18N
        jLabel16.setText("Executions");

        javax.swing.GroupLayout panelExecutionsLayout = new javax.swing.GroupLayout(panelExecutions);
        panelExecutions.setLayout(panelExecutionsLayout);
        panelExecutionsLayout.setHorizontalGroup(
            panelExecutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExecutionsLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelExecutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelExecutionsLayout.setVerticalGroup(
            panelExecutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExecutionsLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel17.setText("Solution:");

        comboSolutions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboSolutionsItemStateChanged(evt);
            }
        });
        comboSolutions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSolutionsActionPerformed(evt);
            }
        });
        comboSolutions.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboSolutionsFocusLost(evt);
            }
        });

        javax.swing.GroupLayout panelSolutionsLayout = new javax.swing.GroupLayout(panelSolutions);
        panelSolutions.setLayout(panelSolutionsLayout);
        panelSolutionsLayout.setHorizontalGroup(
            panelSolutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolutionsLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comboSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        panelSolutionsLayout.setVerticalGroup(
            panelSolutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSolutionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel17)
                .addComponent(comboSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane4.setViewportView(tableObjectives);

        comboMetrics.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboMetricsItemStateChanged(evt);
            }
        });
        comboMetrics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMetricsActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel9.setText("Metrics:");

        javax.swing.GroupLayout panelObjectivesLayout = new javax.swing.GroupLayout(panelObjectives);
        panelObjectives.setLayout(panelObjectivesLayout);
        panelObjectivesLayout.setHorizontalGroup(
            panelObjectivesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelObjectivesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(comboMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelObjectivesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelObjectivesLayout.setVerticalGroup(
            panelObjectivesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelObjectivesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelObjectivesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelSelectedMetricTitle.setFont(new java.awt.Font("Monaco", 1, 12)); // NOI18N

        tableMetrics.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tableMetrics);

        javax.swing.GroupLayout panelShowMetricsLayout = new javax.swing.GroupLayout(panelShowMetrics);
        panelShowMetrics.setLayout(panelShowMetricsLayout);
        panelShowMetricsLayout.setHorizontalGroup(
            panelShowMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShowMetricsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSelectedMetricTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelShowMetricsLayout.setVerticalGroup(
            panelShowMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShowMetricsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelShowMetricsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShowMetricsLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(panelShowMetricsLayout.createSequentialGroup()
                        .addComponent(labelSelectedMetricTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                        .addGap(95, 95, 95))))
        );

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel18.setText("Distance Euclidean for Non Dominated Solutions");

        edTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        edTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(edTable);

        javax.swing.GroupLayout panelEdsLayout = new javax.swing.GroupLayout(panelEds);
        panelEds.setLayout(panelEdsLayout);
        panelEdsLayout.setHorizontalGroup(
            panelEdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEdsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelEdsLayout.setVerticalGroup(
            panelEdsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEdsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout experimentsLayout = new javax.swing.GroupLayout(experiments);
        experiments.setLayout(experimentsLayout);
        experimentsLayout.setHorizontalGroup(
            experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(experimentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(experimentsLayout.createSequentialGroup()
                        .addGroup(experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addComponent(panelExecutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(experimentsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelShowMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, 751, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(experimentsLayout.createSequentialGroup()
                                .addComponent(panelObjectives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelEds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(panelSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        experimentsLayout.setVerticalGroup(
            experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(experimentsLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(experimentsLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelExecutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(experimentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(experimentsLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(panelEds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(experimentsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelObjectives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelShowMetrics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Executed Experiments", experiments);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 977, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Paths Confs");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConcernProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConcernProfileActionPerformed
      String newPath = fileChooser(fieldConcernProfile, "uml");
      if (newPath.equals("")) {
        this.config.updatePathToProfileConcerns(fieldConcernProfile.getText());
      } else {
        this.config.updatePathToProfileConcerns(newPath);
      }
    }//GEN-LAST:event_btnConcernProfileActionPerformed

    private void btnSmartyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSmartyProfileActionPerformed
      String newPath = fileChooser(fieldSmartyProfile, "uml");
      if (newPath.equals("")) {
        this.config.updatePathToProfileSmarty(fieldSmartyProfile.getText());
      } else {
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
      String path = dirChooser(fieldTemplate);
      if ("".equals(path)) {
        this.config.updatePathToTemplateFiles(fieldTemplate.getText() + UserHome.getFileSeparator());
      } else {
        this.config.updatePathToTemplateFiles(path + UserHome.getFileSeparator());
      }
    }//GEN-LAST:event_btnTemplateActionPerformed

  private String dirChooser(JTextField field) throws HeadlessException {
    JFileChooser c = new JFileChooser();
    String path;
    c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int rVal = c.showOpenDialog(this);
    if (rVal == JFileChooser.APPROVE_OPTION) {
      path = c.getSelectedFile().getAbsolutePath();
      field.setText(path + UserHome.getFileSeparator());
      field.updateUI();
      return path;
    }
    return "";
  }

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

    private void btnManipulationDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManipulationDirActionPerformed
      String path = dirChooser(fieldManipulationDir);
      if ("".equals(path)) {
        this.config.updatePathToSaveModels(fieldManipulationDir.getText() + UserHome.getFileSeparator());
      } else {
        this.config.updatePathToSaveModels(path + UserHome.getFileSeparator());
      }
    }//GEN-LAST:event_btnManipulationDirActionPerformed

    private void checkSmartyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSmartyActionPerformed
      if (!checkSmarty.isSelected()) {
        fieldSmartyProfile.setText(pathSmartyBck);
        btnSmartyProfile.setEnabled(true);
        this.config.updatePathToProfileSmarty(pathSmartyBck);
      } else {
        pathSmartyBck = fieldSmartyProfile.getText();
        fieldSmartyProfile.setText("");
        this.config.updatePathToProfileSmarty("");
        btnSmartyProfile.setEnabled(false);
      }
    }//GEN-LAST:event_checkSmartyActionPerformed

    private void checkConcernsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkConcernsActionPerformed
      if (!checkConcerns.isSelected()) {
        fieldConcernProfile.setText(pathConcernBck);
        btnConcernProfile.setEnabled(true);
        this.config.updatePathToProfileConcerns(pathConcernBck);
      } else {
        pathConcernBck = fieldConcernProfile.getText();
        fieldConcernProfile.setText("");
        this.config.updatePathToProfileConcerns("");
        btnConcernProfile.setEnabled(false);
      }
    }//GEN-LAST:event_checkConcernsActionPerformed

    private void comboAlgorithmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAlgorithmsActionPerformed
    }//GEN-LAST:event_comboAlgorithmsActionPerformed

    private void checkMutationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMutationActionPerformed
      if (!checkMutation.isSelected()) {
        panelOperatorsMutation.setVisible(false);
        panelMutationProb.setVisible(false);
      } else {
        panelOperatorsMutation.setVisible(true);
        if (crossoverProbabilityBck != null) {
          fieldCrossoverProbability.setText(crossoverProbabilityBck);
        }
        panelMutationProb.setVisible(true);
      }
    }//GEN-LAST:event_checkMutationActionPerformed

    private void checkAddClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAddClassActionPerformed
      addOrRemoveOperatorMutation(FeatureMutationOperators.ADD_CLASS_MUTATION.getOperatorName());
    }//GEN-LAST:event_checkAddClassActionPerformed

    private void checkMoveAttributeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMoveAttributeActionPerformed
      addOrRemoveOperatorMutation(FeatureMutationOperators.MOVE_ATTRIBUTE_MUTATION.getOperatorName());
    }//GEN-LAST:event_checkMoveAttributeActionPerformed

    private void checkFeatureMutationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFeatureMutationActionPerformed
      addOrRemoveOperatorMutation(FeatureMutationOperators.FEATURE_MUTATION.getOperatorName());
    }//GEN-LAST:event_checkFeatureMutationActionPerformed

    private void checkMoveMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMoveMethodActionPerformed
      addOrRemoveOperatorMutation(FeatureMutationOperators.MOVE_METHOD_MUTATION.getOperatorName());
    }//GEN-LAST:event_checkMoveMethodActionPerformed

    private void checkMoveOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMoveOperationActionPerformed
      addOrRemoveOperatorMutation(FeatureMutationOperators.MOVE_OPERATION_MUTATION.getOperatorName());
    }//GEN-LAST:event_checkMoveOperationActionPerformed

    private void crossProbSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_crossProbSliderMouseDragged
      double a = (double) crossProbSlider.getValue() / 10;
      fieldCrossoverProbability.setText(String.valueOf(a));
    }//GEN-LAST:event_crossProbSliderMouseDragged

    private void checkCrossoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCrossoverActionPerformed
      if (checkCrossover.isSelected()) {
        if (crossoverProbabilityBck != null) {
          fieldCrossoverProbability.setText(crossoverProbabilityBck);
        }
        panelCrossProb.setVisible(true);
      } else {
        crossoverProbabilityBck = fieldCrossoverProbability.getText();
        fieldCrossoverProbability.setText("0");
        panelCrossProb.setVisible(false);
      }
    }//GEN-LAST:event_checkCrossoverActionPerformed

    private void mutatinProbSliderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mutatinProbSliderMouseDragged
      double a = (double) mutatinProbSlider.getValue() / 10;
      fieldMutationProb.setText(String.valueOf(a));
    }//GEN-LAST:event_mutatinProbSliderMouseDragged

    private void btnCleanListArchs1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanListArchs1ActionPerformed
      fieldArchitectureInput.setText("");
      VolatileConfs.setArchitectureInputPath(null);
    }//GEN-LAST:event_btnCleanListArchs1ActionPerformed

    private void btnInput1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInput1ActionPerformed

      Validators.validateEntries(fieldArchitectureInput.getText());
    }//GEN-LAST:event_btnInput1ActionPerformed

    private void fieldOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldOutputActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_fieldOutputActionPerformed

    private void btnOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutputActionPerformed
      String path = dirChooser(fieldOutput);
      if ("".equals(path)) {
        this.config.updatePathToExportModels(fieldOutput.getText() + UserHome.getFileSeparator());
      } else {
        this.config.updatePathToExportModels(path + UserHome.getFileSeparator());
      }
    }//GEN-LAST:event_btnOutputActionPerformed

    private void comboAlgorithmsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboAlgorithmsItemStateChanged
      if (comboAlgorithms.getSelectedItem() != null && comboAlgorithms.getSelectedIndex() != 0) {
        String algorithmName = comboAlgorithms.getSelectedItem().toString();
        VolatileConfs.setAlgorithmName(algorithmName);
        if ("Paes".equalsIgnoreCase(algorithmName)) {
          enableFieldForPaes();
          hideFieldsForNSGAII();
        }
        if ("NSGA-II".equalsIgnoreCase(algorithmName)) {
          enableFieldsForNSGAII();
          hideFieldsForPases();
        }

        Logger.getLogger(main.class.getName()).log(Level.INFO, "Selected: " + comboAlgorithms.getSelectedItem().toString());
      } else {
        VolatileConfs.setAlgorithmName(null);
      }
    }//GEN-LAST:event_comboAlgorithmsItemStateChanged

    private void checkEleganceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEleganceActionPerformed
      final String metric = Metrics.ELEGANCE.getName();
      addToMetrics(checkElegance, metric);
    }//GEN-LAST:event_checkEleganceActionPerformed

    private void checkConventionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkConventionalActionPerformed
      final String metric = Metrics.CONVENTIONAL.getName();
      addToMetrics(checkConventional, metric);
    }//GEN-LAST:event_checkConventionalActionPerformed

    private void checkPLAExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPLAExtActionPerformed
      final String metric = Metrics.PLA_EXTENSIBILIY.getName();
      addToMetrics(checkPLAExt, metric);
    }//GEN-LAST:event_checkPLAExtActionPerformed

    private void checkFeatureDrivenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFeatureDrivenActionPerformed
      final String metric = Metrics.FEATURE_DRIVEN.getName();
      addToMetrics(checkFeatureDriven, metric);
    }//GEN-LAST:event_checkFeatureDrivenActionPerformed

    private void numberOfRunsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numberOfRunsFocusLost
      if (isDigit(fieldNumberOfRuns.getText())) {
        Logger.getLogger(main.class.getName()).log(Level.INFO, "Number Of Runs: {0}", fieldNumberOfRuns.getText());
        VolatileConfs.setNumberOfRuns(Integer.parseInt(fieldNumberOfRuns.getText()));
      }
    }//GEN-LAST:event_numberOfRunsFocusLost

    private void fieldNumberOfRunsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNumberOfRunsActionPerformed
      // TODO add your handling code here:
    }//GEN-LAST:event_fieldNumberOfRunsActionPerformed

    private void fieldMaxEvaluationsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldMaxEvaluationsFocusLost
      if (isDigit(fieldMaxEvaluations.getText())) {
        Logger.getLogger(main.class.getName()).log(Level.INFO, "Max Evaluations: {0}", fieldMaxEvaluations.getText());
        VolatileConfs.setMaxEvaluations(Integer.parseInt(fieldMaxEvaluations.getText()));
      }
    }//GEN-LAST:event_fieldMaxEvaluationsFocusLost

    private void fieldPopulationSizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldPopulationSizeFocusLost
      if (isDigit(fieldPopulationSize.getText())) {
        Logger.getLogger(main.class.getName()).log(Level.INFO, "Population Size: {0}", fieldPopulationSize.getText());
        VolatileConfs.setPopulationSize(Integer.parseInt(fieldPopulationSize.getText()));
      }
    }//GEN-LAST:event_fieldPopulationSizeFocusLost

    private void fieldNumberOfRunsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldNumberOfRunsKeyTyped
    onlyDigit(evt);
  }

  private boolean onlyDigit(KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
      getToolkit().beep();
      evt.consume();
      return false;
    }
    return true;
    }//GEN-LAST:event_fieldNumberOfRunsKeyTyped

    private void fieldMaxEvaluationsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldMaxEvaluationsKeyTyped
      onlyDigit(evt);
    }//GEN-LAST:event_fieldMaxEvaluationsKeyTyped

    private void fieldPopulationSizeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldPopulationSizeKeyTyped
      onlyDigit(evt);
    }//GEN-LAST:event_fieldPopulationSizeKeyTyped

  /**
   * Rodar experimento
   *
   * @param evt
   */
    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed

      //Validacoes inicias
      //Verifica se as entradas sao validas. Caso contrario finaliza
      if (!Validators.validateEntries(fieldArchitectureInput.getText())) {
        return;
      }

      //Recupera o algoritmo selecionado pelo usuário
      String algoritmToRun = VolatileConfs.getAlgorithmName();

      //Caso nenhum for selecionado, informa o usuario
      if (comboAlgorithms.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this, "You need select a algorithm");
      } else {
        //Pede confirmacao para o usuario para de fato executar o
        //experimento.
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "You have sure than"
                + " want execute this experiement? This will take a time."
                + " Meanwhile the UI will be blocked",
                "You have sure?", dialogButton);
        //Caso usuário aceite, verifica qual algoritmo executar
        //E invoca a classe responsável.
        if (dialogResult == 0) {
          if ("NSGA-II".equalsIgnoreCase(algoritmToRun)) {
            jLabel12.setText("Working....");
            java.awt.EventQueue.invokeLater(new Runnable() {

              @Override
              public void run() {
                executeNSGAII();
                jLabel12.setText("Done");
                db.Database.reloadContent();
              }
            });
          }
          if ("PAES".equalsIgnoreCase(algoritmToRun)) {
            jLabel12.setText("Working....");
            java.awt.EventQueue.invokeLater(new Runnable() {

              @Override
              public void run() {
                executePAES();
                jLabel12.setText("Done");
                db.Database.reloadContent();
              }
            });
          }
        }
      }
    }//GEN-LAST:event_btnRunActionPerformed

    private void tableExpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableExpMouseClicked
      if (evt.getClickCount() == 2) {
        panelEds.setVisible(true);
        panelShowMetrics.setVisible(false);
        JTable target = (JTable) evt.getSource();
        int rowIndex = target.getSelectedRow();
    

        String idExperiment = target.getModel().getValueAt(rowIndex, 0).toString();

        GuiUtils.hideSolutionsAndExecutionPaneIfExperimentSelectedChange(
                this.selectedExperiment, idExperiment, panelSolutions,
                panelObjectives);

        this.selectedExperiment = idExperiment;
        createTableExecutions(idExperiment);
        
        DefaultTableModel modelTableEds = new DefaultTableModel();
        modelTableEds.addColumn("Solution Name");
        modelTableEds.addColumn("ED");
        
        edTable.setModel(modelTableEds);
        HashMap<String, String> resultsEds = Indicadores.getEdsForExperiment(selectedExperiment);
        
        for (Map.Entry<String, String> entry : resultsEds.entrySet()) {
            Object[] row = new Object[2];
            row[0] =entry.getKey();
            row[1] =entry.getValue();
            modelTableEds.addRow(row);
        }
        
      }
    }//GEN-LAST:event_tableExpMouseClicked

    private void tableExpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableExpKeyPressed
      // TODO add your handling code here:
    }//GEN-LAST:event_tableExpKeyPressed

    private void tableExecutionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableExecutionsMouseClicked
      if (evt.getClickCount() == 2) {
        panelSolutions.setVisible(true);
        
        JTable target = (JTable) evt.getSource();
        int rowIndex = target.getSelectedRow();
        String idExecution = target.getModel().getValueAt(rowIndex, 0).toString();
        this.selectedExecution = idExecution;
        List<File> solutions = ReadSolutionsFiles.read(this.selectedExperiment,
                idExecution,
                this.config.getConfig().getDirectoryToExportModels());

        comboSolutions.setModel(new SolutionsComboBoxModel(idExecution, solutions));
        
        comboSolutions.setSelectedIndex(0);
      }
    }//GEN-LAST:event_tableExecutionsMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void experimentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_experimentsMouseClicked

    }//GEN-LAST:event_experimentsMouseClicked

    private void comboSolutionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboSolutionsItemStateChanged
    }//GEN-LAST:event_comboSolutionsItemStateChanged

    private void comboSolutionsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_comboSolutionsFocusLost
      // TODO add your handling code here:
    }//GEN-LAST:event_comboSolutionsFocusLost

  private void comboSolutionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSolutionsActionPerformed
    panelShowMetrics.setVisible(false);
    initiComboMetrics();
    Map<String, String> objectives = db.Database.getAllObjectivesByExecution(((Solution) comboSolutions.getSelectedItem()).getId(), this.selectedExperiment);

    String fileName = ((Solution) comboSolutions.getSelectedItem()).getName();
   
    
    String objectiveId = Utils.extractObjectiveIdFromFile(fileName);

    Map<String, String> r = GuiUtils.formatObjectives(objectives.get(objectiveId), this.selectedExperiment);

    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Metric");
    model.addColumn("Value");

    GuiUtils.makeTableNotEditable(tableObjectives);

    tableObjectives.setModel(model);

    Iterator<Entry<String, String>> it = r.entrySet().iterator();
    while (it.hasNext()) {
      Object[] row = new Object[2];
      Map.Entry pairs = (Map.Entry<String, String>) it.next();
      row[0] = pairs.getKey();
      row[1] = pairs.getValue();
      it.remove(); // evitar ConcurrentModificationException
      model.addRow(row);
    }

    panelObjectives.setVisible(true);

  }//GEN-LAST:event_comboSolutionsActionPerformed

  private void comboMetricsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMetricsActionPerformed
    
  }//GEN-LAST:event_comboMetricsActionPerformed

  private void comboMetricsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboMetricsItemStateChanged
    
    
    
    String selectedMetric = comboMetrics.getSelectedItem().toString().toLowerCase().replaceAll("\\s+", "");
    labelSelectedMetricTitle.setText(comboMetrics.getSelectedItem().toString());

    Map<String, String[]> mapColumns = new HashMap<>();
    String[] plaExtColumns = {"PLA Extensibility"};
    String[] eleganceColumns = {"NAC", "ATMR", "EC"};
    String[] conventionalsColumns = {"macAggregation", "choesion", "meanDepComps", "meanNumOps", "sumClassesDepIn", "sumClassesDepOut", "sumDepIn", "sumDepOut"};
    String[] featureColumns = {"msiAggregation", "cdac ", "cdai", "cdao", "cibc", "iibc", "oobc", "lcc", "lccClass", "cdaClass", "cibClass"};

    mapColumns.put("plaextensibility", plaExtColumns);
    mapColumns.put("elegance", eleganceColumns);
    mapColumns.put("conventional", conventionalsColumns);
    mapColumns.put("feature", featureColumns);

    DefaultTableModel model = new DefaultTableModel();
    tableMetrics.setModel(model);
    int numberOfColumns = 0;

    if (comboSolutions.getSelectedItem() != null) {
      String idSolution = Utils.extractSolutionIdFromSolutionFileName(comboSolutions.getSelectedItem().toString());

      if (selectedMetric.equalsIgnoreCase("plaextensibility")) {
        PLAExtensibility plaExt = db.Database.getPlaExtMetricsForSolution(idSolution, this.selectedExperiment);

        for (int i = 0; i < mapColumns.get(selectedMetric).length; i++) {
          model.addColumn(mapColumns.get("plaextensibility")[i]);
          numberOfColumns++;
        }
        GuiUtils.addComomColumnsToTable(model);

        Object[] row = new Object[numberOfColumns + 2];
        row[0] = plaExt.getPlaExtensibility();
        row[1] = this.selectedExperiment;
        row[2] = this.selectedExecution;
        model.addRow(row);
      } else if (selectedMetric.equalsIgnoreCase("elegance")) {

        Elegance elegance = db.Database.getEleganceMetricsForSolution(idSolution, this.selectedExperiment);

        for (int i = 0; i < mapColumns.get(selectedMetric).length; i++) {
          model.addColumn(mapColumns.get("elegance")[i]);
          numberOfColumns++;
        }
        GuiUtils.addComomColumnsToTable(model);

        Object[] row = new Object[numberOfColumns + 2];
        row[0] = elegance.getNac();
        row[1] = elegance.getAtmr();
        row[2] = elegance.getEc();
        row[3] = this.selectedExperiment;
        row[4] = this.selectedExecution;
        model.addRow(row);
      } else if (selectedMetric.equalsIgnoreCase("conventional")) {
        Conventional conventional = db.Database.getConventionalsMetricsForSolution(idSolution, this.selectedExperiment);

        for (int i = 0; i < mapColumns.get(selectedMetric).length; i++) {
          model.addColumn(mapColumns.get("conventional")[i]);
          numberOfColumns++;
        }

        GuiUtils.addComomColumnsToTable(model);
        Object[] row = new Object[numberOfColumns + 2];
        row[0] = conventional.getMacAggregation();
        row[1] = conventional.getChoesion();
        row[2] = conventional.getMeanDepComps();
        row[3] = conventional.getMeanNumOps();
        row[4] = conventional.getSumClassesDepIn();
        row[5] = conventional.getSumClassesDepOut();
        row[6] = conventional.getSumDepIn();
        row[7] = conventional.getSumDepOut();
        row[8] = this.selectedExperiment;
        row[9] = this.selectedExecution;
        model.addRow(row);
      } else if (selectedMetric.equalsIgnoreCase("featuredriven")) {
        FeatureDriven f = db.Database.getFeatureDrivenMetricsForSolution(idSolution, this.selectedExperiment);

        for (int i = 0; i < mapColumns.get("feature").length; i++) {
          model.addColumn(mapColumns.get("feature")[i]);
          numberOfColumns++;
        }
        GuiUtils.addComomColumnsToTable(model);
        Object[] row = new Object[numberOfColumns + 2];
        row[0] = f.getMsiAggregation();
        row[1] = f.getCdac();
        row[2] = f.getCdai();
        row[3] = f.getCdao();
        row[4] = f.getCibc();
        row[5] = f.getIibc();
        row[6] = f.getOobc();
        row[7] = f.getLcc();
        row[8] = f.getLccClass();
        row[9] = f.getCdaClass();
        row[10] = f.getCibClass();
        row[11] = this.selectedExperiment;
        row[12] = this.selectedExecution;
        model.addRow(row);

      }
      panelShowMetrics.setVisible(true);

    }
  }//GEN-LAST:event_comboMetricsItemStateChanged

  private void edTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edTableMouseClicked
    // TODO add your handling code here:
  }//GEN-LAST:event_edTableMouseClicked

  private void tableExecutionsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableExecutionsMouseEntered
    // TODO add your handling code here:
  }//GEN-LAST:event_tableExecutionsMouseEntered

  private void experimentsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_experimentsFocusGained

  }//GEN-LAST:event_experimentsFocusGained

  private void experimentsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_experimentsMouseEntered
    // TODO add your handling code here:
  }//GEN-LAST:event_experimentsMouseEntered

  private String fileChooser(JTextField fieldToSet, String allowExtension) throws HeadlessException {
    JFileChooser c = new JFileChooser();
    int rVal = c.showOpenDialog(this);
    if (rVal == JFileChooser.APPROVE_OPTION) {

      File f = new File(c.getCurrentDirectory() + c.getSelectedFile().getName());
      String ext = Utils.getExtension(f);

      if (!ext.equalsIgnoreCase(allowExtension)) {
        JOptionPane.showMessageDialog(null, "The selected file is not allowed. You need selects a file with extension .uml, but you selects a ." + ext + " file");
        return "";
      } else {
        final String path = c.getCurrentDirectory() + "/" + c.getSelectedFile().getName();
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
    private javax.swing.JPanel ApplicationConfs;
    private javax.swing.JPanel algorithms;
    private javax.swing.JButton btnCleanListArchs1;
    private javax.swing.JButton btnConcernProfile;
    private javax.swing.JButton btnInput1;
    private javax.swing.JButton btnManipulationDir;
    private javax.swing.JButton btnOutput;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnSmartyProfile;
    private javax.swing.JButton btnTemplate;
    private javax.swing.JCheckBox checkAddClass;
    private javax.swing.JCheckBox checkConcerns;
    private javax.swing.JCheckBox checkConventional;
    private javax.swing.JCheckBox checkCrossover;
    private javax.swing.JCheckBox checkElegance;
    private javax.swing.JCheckBox checkFeatureDriven;
    private javax.swing.JCheckBox checkFeatureMutation;
    private javax.swing.JCheckBox checkManagerClass;
    private javax.swing.JCheckBox checkMoveAttribute;
    private javax.swing.JCheckBox checkMoveMethod;
    private javax.swing.JCheckBox checkMoveOperation;
    private javax.swing.JCheckBox checkMutation;
    private javax.swing.JCheckBox checkPLAExt;
    private javax.swing.JCheckBox checkSmarty;
    private javax.swing.JComboBox comboAlgorithms;
    private javax.swing.JComboBox comboMetrics;
    private javax.swing.JComboBox comboSolutions;
    private javax.swing.JSlider crossProbSlider;
    private javax.swing.JTable edTable;
    private javax.swing.JPanel experiments;
    private javax.swing.JTextArea fieldArchitectureInput;
    private javax.swing.JTextField fieldConcernProfile;
    private javax.swing.JTextField fieldCrossoverProbability;
    private javax.swing.JTextField fieldManipulationDir;
    private javax.swing.JTextField fieldMaxEvaluations;
    private javax.swing.JTextField fieldMutationProb;
    private javax.swing.JTextField fieldNumberOfRuns;
    private javax.swing.JTextField fieldOutput;
    private javax.swing.JTextField fieldPaesArchiveSize;
    private javax.swing.JTextField fieldPopulationSize;
    private javax.swing.JTextField fieldSmartyProfile;
    private javax.swing.JTextField fieldTemplate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelAlgorithms;
    private javax.swing.JLabel labelArchivePAES;
    private javax.swing.JLabel labelOperators;
    private javax.swing.JLabel labelSelectedMetricTitle;
    private javax.swing.JSlider mutatinProbSlider;
    private javax.swing.JPanel panelCrossProb;
    private javax.swing.JPanel panelEds;
    private javax.swing.JPanel panelExecutions;
    private javax.swing.JPanel panelExperimentSettings;
    private javax.swing.JPanel panelMetrics;
    private javax.swing.JPanel panelMutationProb;
    private javax.swing.JPanel panelObjectives;
    private javax.swing.JPanel panelOperatorsMutation;
    private javax.swing.JPanel panelShowMetrics;
    private javax.swing.JPanel panelSolutions;
    private javax.swing.JTable tableExecutions;
    private javax.swing.JTable tableExp;
    private javax.swing.JTable tableMetrics;
    private javax.swing.JTable tableObjectives;
    // End of variables declaration//GEN-END:variables

  private void hidePanelMutationOperatorsByDefault() {
    panelOperatorsMutation.setVisible(false);
  }

  private void checkAllMutationOperatorsByDefault() {
    checkAddClass.setSelected(true);
    checkFeatureMutation.setSelected(true);
    checkManagerClass.setSelected(true);
    checkMoveAttribute.setSelected(true);
    checkMoveMethod.setSelected(true);
    checkMoveOperation.setSelected(true);

    FeatureMutationOperators[] operators = FeatureMutationOperators.values();
    for (FeatureMutationOperators operator : operators) {
      MutationOperatorsSelected.getSelectedMutationOperators().add(operator.getOperatorName());
    }
  }

  private void hidePanelCrossoverProbabilityByDefault() {
    panelCrossProb.setVisible(false);
  }

  private void hidePanelMutationProbabilityByDefault() {
    panelMutationProb.setVisible(false);
  }

  private void checkAllMetricsByDefault() {
    for (Metrics m : Metrics.values()) {
      VolatileConfs.getMetricsSelecteds().add(m.getName());
    }

    checkElegance.setSelected(true);
    checkPLAExt.setSelected(true);
    checkConventional.setSelected(true);
    checkFeatureDriven.setSelected(true);
  }

  private boolean isDigit(String text) {
    try {
      Integer.parseInt(text);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Somente faz uma copia do banco de dados vazio para a pasta da oplatool no
   * diretorio do usaurio se o mesmo nao existir.
     *
   */
  private void configureDb() {
    final String pathDb = UserHome.getPathToDb();

    if (!(new File(pathDb).exists())) {
      File dirDb = new File(UserHome.getOplaUserHome() + "db");
      if (!dirDb.exists()) {
        dirDb.mkdirs();
      }

      Utils.copy("oplatool.db", pathDb);
    }
    try {
      db.Database.setContent(results.Experiment.all());
    } catch (SQLException ex) {
      Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  private void initiExecutedExperiments() {
    try {
      DefaultTableModel model = new DefaultTableModel();
      model.addColumn("ID");
      model.addColumn("Name");
      model.addColumn("Algorithm");
      model.addColumn("Created at");

      GuiUtils.makeTableNotEditable(tableExp);
         
      tableExp.setModel(model);

      List<results.Experiment> allExp = db.Database.getContent();


      for (results.Experiment exp : allExp) {
        Object[] row = new Object[4];
        row[0] = exp.getId();
        row[1] = exp.getName();
        row[2] = exp.getAlgorithm();
        row[3] = exp.getCreatedAt();
        model.addRow(row);
      }
    } catch (Exception ex) {
      Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void hidePanelSolutionsByDefault() {
    panelSolutions.setVisible(false);
    panelObjectives.setVisible(false);
  }

  private void disableFieldsOnStart() {
    fieldNumberOfRuns.setEnabled(false);
    fieldMaxEvaluations.setEnabled(false);
    fieldPopulationSize.setEnabled(false);
    fieldPaesArchiveSize.setEnabled(false);
  }

  private void enableFieldForPaes() {
    fieldNumberOfRuns.setEnabled(true);
    fieldMaxEvaluations.setEnabled(true);
    fieldPaesArchiveSize.setEnabled(true);
  }

  private void hideFieldsForNSGAII() {
    fieldPopulationSize.setEnabled(false);
  }

  private void enableFieldsForNSGAII() {
    fieldNumberOfRuns.setEnabled(true);
    fieldMaxEvaluations.setEnabled(true);
    fieldPopulationSize.setEnabled(true);
  }

  private void hideFieldsForPases() {
    fieldPaesArchiveSize.setEnabled(false);
  }

  private void initiComboMetrics() {
   
    String metricsSelectedForCurrentExperiment[] =
            db.Database.getOrdenedObjectives(this.selectedExperiment).split(" ");
    
   
    comboMetrics.setModel(new DefaultComboBoxModel());

    for (int i = 0; i < metricsSelectedForCurrentExperiment.length; i++) {
      comboMetrics.addItem(Utils.capitalize(metricsSelectedForCurrentExperiment[i]));
    }

    comboMetrics.updateUI();


  }

  private void hidePanelShowMetricsByDefault() {
    panelShowMetrics.setVisible(false);
  }
  
  ChangeListener changeListener = new ChangeListener() {

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
      JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
      int index = sourceTabbedPane.getSelectedIndex();
      String tabName = sourceTabbedPane.getTitleAt(index);
      if ("Executed Experiments".equalsIgnoreCase(tabName)) {
        if (db.Database.getContent().isEmpty()) {
          JOptionPane.showMessageDialog(null, "No experiment executed yet. ", "OPLA-Tool", 0);
        } else {
          db.Database.reloadContent();
          initiExecutedExperiments();
        }
      }

    }
  };
    
}
