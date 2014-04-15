package com.ufpr.br.opla.gui2;

/**
 *
 * @author elf
 * 
 * Classe que guarda algumas configurações/paths que não são persistidos.
 */
public class VolatileConfs {
    
    private static String[] architectureInputPath;

    public static String[] getArchitectureInputPath() {
        return architectureInputPath;
    }

    public static void setArchitectureInputPath(String[] path) {
        architectureInputPath = path;
    } 
    
}
