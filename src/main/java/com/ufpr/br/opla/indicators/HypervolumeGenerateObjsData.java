/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufpr.br.opla.indicators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author elf
 */
public class HypervolumeGenerateObjsData {

  public static void generate(List<String> files) {
  }

  public static List<HypervolumeData> generate(Map<String, List<Double>> content) throws IOException {
    
    List<HypervolumeData> hypervolumeDatas = new ArrayList<>();
    
    for (Map.Entry<String, List<Double>> entry : content.entrySet()) {
      String pathToFile = entry.getKey();
      List<Double> list = entry.getValue();

      String[] splited = pathToFile.split("_");
      String pla = getPlaName(splited[0]);
      String algorithm = getAlgorithmName(splited[1]);
      
      //Acha o ponto de referencia
      String referencePoint = findReferencePoint(list);
     
      
//      //Execute o programa em C que calcula o hypervolume
      List<Double> values = ExecuteHypervolumeScript.exec(referencePoint, pathToFile);
      
      hypervolumeDatas.add(new HypervolumeData(values, pla, algorithm));
      
      
    }
   // deleteGeneratedFiles(content);
    return hypervolumeDatas;
  }

  private static String getPlaName(String str) {
    return str.substring(str.lastIndexOf("/") + 1, str.length());
  }

  private static String getAlgorithmName(String str) {
    return str.substring(0, str.lastIndexOf("."));
  }

  //TODO gerar pontos de referêncai com base na quantidade de objectivos.
  private static String findReferencePoint(List<Double> values) {
    Double max = Double.MIN_VALUE;

    for (Double double1 : values) {
      if (double1 > max) {
        max = double1;
      }
    }
    double point = max + 1;
    return String.valueOf(point) + " " + String.valueOf(point);
  }

  private static void deleteGeneratedFiles(Map<String, List<Double>> content) {
    for (Map.Entry<String, List<Double>> entry : content.entrySet()) {
      String pathToFile = entry.getKey();
      File f = new File(pathToFile);
      f.delete();
    }
  }
 
}
