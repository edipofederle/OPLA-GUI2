/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.algorithms;

import arquitetura.io.ReaderConfig;
import com.ufpr.br.opla.experiementsUtils.MutationOperatorsSelected;
import com.ufpr.br.opla.gui2.UserHome;
import com.ufpr.br.opla.gui2.VolatileConfs;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import jmetal.experiments.NSGAIIConfig;
import jmetal.experiments.NSGAII_OPLA_FeatMutInitializer;
import jmetal.experiments.OPLAConfigs;

/**
 *
 * @author elf
 */
public class NSGAII {

    public void execute(JComboBox comboAlgorithms, JCheckBox checkMutation, JTextField fieldMutationProb,
            JTextArea fieldArchitectureInput, JTextField fieldNumberOfRuns, JTextField fieldPopulationSize,
            JTextField fieldMaxEvaluations, JCheckBox checkCrossover, JTextField fieldCrossoverProbability) {
        
        ReaderConfig.setPathToConfigurationFile("/Users/elf/oplatool/application.yaml");
        ReaderConfig.load();
        
        NSGAIIConfig configs = new NSGAIIConfig();

        //Se mutação estiver marcada, pega os operadores selecionados
        //,e seta a probabilidade de mutacao
        if (checkMutation.isSelected()) {
            List<String> mutationsOperators = MutationOperatorsSelected.getSelectedMutationOperators();
            configs.setMutationOperators(mutationsOperators);
            configs.setMutationProbability(Double.parseDouble(fieldMutationProb.getText()));
        }
        
        configs.setPlas(fieldArchitectureInput.getText());
        configs.setNumberOfRuns(Integer.parseInt(fieldNumberOfRuns.getText()));
        configs.setPopulationSize(Integer.parseInt(fieldPopulationSize.getText()));
        configs.setMaxEvaluations(Integer.parseInt(fieldMaxEvaluations.getText()));

        //Se crossover estiver marcado, configura probabilidade
        //Caso contrario desativa
        if (checkCrossover.isSelected()) {
            configs.setCrossoverProbability(Double.parseDouble(fieldCrossoverProbability.getText()));
        } else {
            configs.disableCrossover();            
        }

        //Configura onde o db esta localizado
        configs.setPathToDb(UserHome.getOplaUserHome()+"db"+UserHome.getFileSeparator()+"oplatool.db");

        //Instancia a classe de configuracao da OPLA.java
        OPLAConfigs oplaConfig = new OPLAConfigs();
        
        //Numero de objetivos /TODO ver isso com thelma. Numero de objetivos/numero de metricas
        oplaConfig.setNumberOfObjectives(VolatileConfs.getMetricsSelecteds().size());
        oplaConfig.setSelectedMetrics(VolatileConfs.getMetricsSelecteds());

        //Add as confs de OPLA na classe de configuracoes gerais.
        configs.setOplaConfigs(oplaConfig);

        //Utiliza a classe Initializer do NSGAII passando as configs.
        NSGAII_OPLA_FeatMutInitializer nsgaii = new NSGAII_OPLA_FeatMutInitializer(configs);

        //Executa
        nsgaii.run();
        
    }

}