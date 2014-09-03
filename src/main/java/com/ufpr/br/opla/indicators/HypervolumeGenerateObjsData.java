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

  public static List<HypervolumeData> generate(Map<String, List<Double>> content, String idReferenceExperiment) throws IOException {
    List<HypervolumeData> hypervolumeDatas = new ArrayList<>();
    
    //Acha o ponto de referencia
    
      String referencePoint = findReferencePoint(content, idReferenceExperiment);

    for (Map.Entry<String, List<Double>> entry : content.entrySet()) {
      String pathToFile = entry.getKey();
      
      String[] splited = pathToFile.split("_");
      String pla = getPlaName(splited[1]);
      String algorithm = getAlgorithmName(splited[2]);

      //Execute o programa em C que calcula o hypervolume
      List<Double> values = ExecuteHypervolumeScript.exec(referencePoint, pathToFile);

      hypervolumeDatas.add(new HypervolumeData(values, pla, algorithm));
    }
    
    deleteGeneratedFiles(content);
    
    return hypervolumeDatas;
  }

  /**
   * Retorna o ponto de referência que é usado ao chamar o script em C.
   * 
   * @param values
   * @param experimentId
   * @return 
   */
  private static String findReferencePoint(Map<String, List<Double>> content, String experimentId) {
    Double max = Double.MIN_VALUE;
    int numberOfObjectives = db.Database.getNumberOfFunctionForExperimentId(experimentId);

    for (Map.Entry<String, List<Double>> entry : content.entrySet()) {
      List<Double> list = entry.getValue();
      for (Double double1 : list)
        if (double1 > max)
          max = double1;
    }
        
    double point = max + 1;
    String ref = "";
    for(int i=0; i < numberOfObjectives; i++)
      ref += String.valueOf(point) + " ";
    
    return ref.trim();
  }

  /**
   * Deleta arquivos .txt gerados após processamento
   * 
   * @param content 
   */
  private static void deleteGeneratedFiles(Map<String, List<Double>> content) {
    for (Map.Entry<String, List<Double>> entry : content.entrySet()) {
      String pathToFile = entry.getKey();
      File f = new File(pathToFile);
      f.delete();
    }
  }

  private static String getExperimentId(String str) {
    return str.substring(str.lastIndexOf("/") + 1, str.length());
  }
  
  private static String getPlaName(String str) {
    return str.substring(str.lastIndexOf("/") + 1, str.length());
  }

  private static String getAlgorithmName(String str) {
    return str.substring(0, str.lastIndexOf("."));
  }
}
