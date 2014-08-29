/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import java.util.List;

/**
 *
 * @author elf
 */
public class MathUtils {

  public static double mean(List<Double> values) {
    double total = 0;

    for (Double double1 : values) {
      total += double1;
    }

    return total/values.size();
  }

  /**
   * Desvio Padrão.
   *
   * @param objetos
   * @return
   */
  public static double stDev(List<Double> values) {
    double media = mean(values);
    double somatorio = 0d;
    
    for (Double d : values) {
      somatorio += (media - d.doubleValue()) * (media - d.doubleValue());
    }
    
   return Math.sqrt(((double) 1 /( values.size()))  
                    * somatorio);  
  }
}
