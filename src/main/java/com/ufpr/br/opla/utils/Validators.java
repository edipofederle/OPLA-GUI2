/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import com.ufpr.br.opla.configuration.VolatileConfs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author elf
 */
public class Validators {

  public static boolean validateEntries(String archsInput) {
    if (archsInput.isEmpty()) {
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

    if (invalidsEntries.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean validateCheckedsFunctions(List<JCheckBox> checkeds) {     
    HashMap<String, String> map = new HashMap<>();
    
    for (JCheckBox checkBox : checkeds) {
      String experimentId = checkBox.getName().split(",")[0];
      if(map.containsKey(experimentId)){
        String actualContent = map.get(experimentId);
        map.put(experimentId, actualContent + "," + checkBox.getName().split(",")[1]);
      }else{
        map.put(experimentId, checkBox.getName().split(",")[1]);
      }
    }
    
    //Checa se map contem funcoes das duas execucoes selecinadas
    //Ou seja, se o usuário selecionou funcoes das duas execuções escolhidas.
    if(map.entrySet().size() == 1)
      return false;
    
    String first = map.entrySet().iterator().next().getValue();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String value = entry.getValue();
      if(!value.equals(first))
        return false;
    }
   
    return true;
  }
}
