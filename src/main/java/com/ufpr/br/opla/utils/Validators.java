/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import com.ufpr.br.opla.configuration.VolatileConfs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author elf
 */
public class Validators {

    public static boolean validateEntries(String archsInput) {
        if(archsInput.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "You need enter at least one architecture");
            return false;
        }
        String archs[] = archsInput.trim().split(",");
        List<String> invalidsEntries = new ArrayList<>();

        for (int i = 0; i < archs.length; i++) {
            String arch;
            try {
                arch = archs[i].substring(archs[i].indexOf('.'), archs[i].length());
                if (!arch.equalsIgnoreCase(".uml")) {
                    invalidsEntries.add(archs[i]);
                }
                File f = new File(archs[i]);
                if (!f.exists()) {
                    throw new FileNotFoundException();
                }
            } catch (Exception e) {
                invalidsEntries.add(archs[i]);
            }

        }

        if (invalidsEntries.isEmpty()) {
            VolatileConfs.setArchitectureInputPath(archs);
        } else {
            JOptionPane.showMessageDialog(null, "The fowlling architecture(s) are not valid: " + invalidsEntries.toString() + "Check it please");
        }
        
        if(invalidsEntries.isEmpty()){return true;}else{return false;}
    }
}
