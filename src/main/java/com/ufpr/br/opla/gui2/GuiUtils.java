/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author elf
 */
public class GuiUtils {

    static void makeTableNotEditable(JTable table) {
        for (int c = 0; c < table.getColumnCount(); c++) {
            Class<?> col_class = table.getColumnClass(c);
            table.setDefaultEditor(col_class, null); // remove editor
        }
    }

    public static String formatObjectives(String content) {
        StringBuilder objectivesFormated = new StringBuilder();
        System.out.println(content);
        String objs[] = content.split("\\|");

        for (int i = 0; i < objs.length; i++) {
            objectivesFormated.append(objs[i]);
            objectivesFormated.append("\n");
        }

        return objectivesFormated.toString();
    }

    /**
     * 
     * @param selectedExperiment
     * @param idExperiment
     * @param panelSolutions
     * @param panelExecutions 
     */
    public static void hideSolutionsAndExecutionPaneIfExperimentSelectedChange(
            String selectedExperiment, String idExperiment, JPanel panelSolutions,
            JPanel panelExecutions) {
        if (selectedExperiment != null && !selectedExperiment.equals(idExperiment)) {
            panelSolutions.setVisible(false);
            panelExecutions.setVisible(false);
        }
    }
}
