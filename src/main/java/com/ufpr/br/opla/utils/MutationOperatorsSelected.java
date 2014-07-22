package com.ufpr.br.opla.utils;

import java.util.ArrayList;
import java.util.List;

public class MutationOperatorsSelected {

  private static List<String> selectedMutationOperators = new ArrayList<>();
  private static List<String> selectedPatternsToApply = new ArrayList<>();

  public static List<String> getSelectedMutationOperators() {
    return selectedMutationOperators;
  }

  public static List<String> getSelectedPatternsToApply() {
    return selectedPatternsToApply;
  }
}
