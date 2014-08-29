package com.ufpr.br.opla.indicators;

import java.util.Collections;
import java.util.List;
import com.ufpr.br.opla.utils.MathUtils;

public class HypervolumeData {

  private String algorithm;
  private String plaName;
  private List<Double> values;

  HypervolumeData(List<Double> values, String pla, String algorithm) {
    this.algorithm = algorithm;
    this.plaName = pla;
    this.values = values;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public String getStDev() {
    return String.format("%.1f", MathUtils.stDev(values));
  }

  public String getMean() {
    return calculeMean(values);
  }

  public String getPlaName() {
    return plaName;
  }

  public List<Double> getValues() {
    return Collections.unmodifiableList(this.values);
  }

  private String calculeMean(List<Double> values) {
    return String.format("%.1f", MathUtils.mean(values));
  }
  

}
