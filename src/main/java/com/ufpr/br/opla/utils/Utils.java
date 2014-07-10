/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ufpr.br.opla.gui2.main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author elf
 */
public class Utils {

  public static String extractSolutionIdFromSolutionFileName(String fileName) {
    return fileName.substring(fileName.indexOf("-") + 1, fileName.length() - 4);
  }

  public static String capitalize(String word) {
    return WordUtils.capitalize(word);
  }

  public static void copy(String source, String target) {
    try {
      InputStream in = Thread.currentThread().getContextClassLoader().
              getResourceAsStream(source);


      FileOutputStream out = new FileOutputStream(target);

      byte[] buffer = new byte[1024];
      int len = in.read(buffer);
      while (len != -1) {
        out.write(buffer, 0, len);
        len = in.read(buffer);
      }
      out.close();
      Logger.getLogger(main.class.getName()).log(Level.INFO, "File copy from {0} to {1}", new Object[]{source, target});
    } catch (Exception e) {
      Logger.getLogger(main.class.getName()).log(Level.SEVERE, e.toString());
      System.exit(1);
    }
  }

  public static void createPath(String path) {
    File pathDir = new File(path);
    if (!pathDir.exists()) {
      Logger.getLogger(main.class.getName()).log(Level.INFO, "Diretorio não existe, criando....");
      pathDir.mkdirs();
      Logger.getLogger(main.class.getName()).log(Level.INFO, "Directory {0} created successfully", path);
    }
  }

  /*
   * Get the extension of a file.
   */
  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }

  //exemplo: VAR_2_agm-5629174275.uml
  public static String extractObjectiveIdFromFile(String name) {
    String b = name.split("-")[1];
    String a = b.substring(0, b.length() - 4);
    if (a.startsWith("0")) {
      return a.substring(1, a.length());
    }

    return a;
  }

  public static boolean selectedSolutionIsNonDominated(String fileName) {
    if (fileName.startsWith("VAR_All")) {
      return true;
    }
    return false;
  }

  public static List<Entry<String, Double>> shortMap(SortedMap<String, Double> resultsEds) {
    List<Map.Entry<String, Double>> edsValues = Lists.newArrayList(resultsEds.entrySet());

    Ordering<Map.Entry<String, Double>> byMapValues = new Ordering<Map.Entry<String, Double>>() {

      @Override
      public int compare(Map.Entry<String, Double> left, Map.Entry<String, Double> right) {
        return left.getValue().compareTo(right.getValue());
      }
    };

    Collections.sort(edsValues, byMapValues);

    return edsValues;
  }

  public static boolean isDigit(String text) {
    try {
      Integer.parseInt(text);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
