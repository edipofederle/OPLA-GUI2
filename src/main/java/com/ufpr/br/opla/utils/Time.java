/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author elf
 */
public class Time {

  public static String startAt() {
    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
  }
  
}
