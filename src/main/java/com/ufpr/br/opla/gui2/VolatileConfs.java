package com.ufpr.br.opla.gui2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elf
 * 
 * Classe que guarda algumas configurações/paths que não são persistidos.
 */
public class VolatileConfs {
    
    private static String[] architecturesInputPath;
    private static String algorithmName; // Cambo algorithm
    private static List<String> metricsSelecteds = new ArrayList<>(); //checkboxs metrics
    private static int numberOfRuns; //numberOfRuns text-field
    private static int maxEvaluations; //maxEvaluations text-field
    private static int fieldPopulationSize; //entendeu ja neh?

    public static String[] getArchitectureInputPath() {
        return architecturesInputPath;
    }

    public static void setArchitectureInputPath(String[] path) {
        architecturesInputPath = path;
    }

    public static String getAlgorithmName() {
        return algorithmName;
    }

    public static void setAlgorithmName(String algorithmName) {
        VolatileConfs.algorithmName = algorithmName;
    }

    public static String[] getArchitecturesInputPath() {
        return architecturesInputPath;
    }

    public static void setArchitecturesInputPath(String[] architecturesInputPath) {
        VolatileConfs.architecturesInputPath = architecturesInputPath;
    }

    public static List<String> getMetricsSelecteds() {
        return metricsSelecteds;
    }

    public static int getNumberOfRuns() {
        return numberOfRuns;
    }

    public static void setNumberOfRuns(int i) {
        numberOfRuns = i;
    }

    static void setMaxEvaluations(int i) {
        maxEvaluations = i;
    }

    static void setPopulationSize(int i) {
        fieldPopulationSize = i;
    }
   
    
}
