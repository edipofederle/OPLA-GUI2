/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.gui2;

import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, String> formatObjectives(String content, String experimentId) {
        String[] titles = db.Database.getOrdenedObjectives(experimentId).split(" ");  
        String objs[] = content.split("\\|");
        
        Map<String, String> mapObjectives = new HashMap<>();
        
        for (int i = 0; i < objs.length; i++)
            mapObjectives.put(titles[i].toUpperCase(), objs[i]);
        
        return mapObjectives;
    }

    /**
     * 
     * @param selectedExperiment
     * @param idExperiment
     * @param panelSolutions
     * @param panelExecutions 
     */
    public static void hideSolutionsAndExecutionPaneIfExperimentSelectedChange(
            String selectedExperiment, String idExperiment, JPanel panelSolutions, JPanel panelObjectives) {
        if (selectedExperiment != null && !selectedExperiment.equals(idExperiment)) {
            panelObjectives.setVisible(false);
            panelSolutions.setVisible(false);
        }
    }
}
