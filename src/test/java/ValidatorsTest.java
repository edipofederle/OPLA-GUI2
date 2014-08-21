
import com.ufpr.br.opla.utils.Validators;
import java.util.HashMap;
import junit.framework.Assert;
import org.junit.Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class ValidatorsTest {
  
  @Test
  public void selectedsExperimentsHasTheSameObjectiveFunctionsTestWrongOrder(){
    HashMap<String, String[]> map = new HashMap<>();
    map.put("123",  new String[] { "String1", "string2"});
    map.put("124",  new String[] { "String2", "string1"});
    
    Assert.assertFalse(Validators.selectedsExperimentsHasTheSameObjectiveFunctions(map));
  }
  
  @Test
  public void selectedsExperimentsHasTheSameObjectiveFunctionsTestWrongOrder2(){
    HashMap<String, String[]> map = new HashMap<>();
    map.put("123",  new String[] { "String1", "string2", "string5"});
    map.put("124",  new String[] { "String2", "string1"});
    
    Assert.assertFalse(Validators.selectedsExperimentsHasTheSameObjectiveFunctions(map));
  }
  
    @Test
  public void selectedsExperimentsHasTheSameObjectiveFunctionsTestWrong2(){
    HashMap<String, String[]> map = new HashMap<>();
    map.put("123",  new String[] {"string2", "string5"});
    map.put("124",  new String[] {"string2"});
    
    Assert.assertFalse(Validators.selectedsExperimentsHasTheSameObjectiveFunctions(map));
  }
  
  @Test
  public void selectedsExperimentsHasTheSameObjectiveFunctionsTestRightOrder(){
    HashMap<String, String[]> map = new HashMap<>();
    map.put("123",  new String[] { "String1", "string2"});
    map.put("124",  new String[] { "String1", "string2"});
    
    Assert.assertTrue(Validators.selectedsExperimentsHasTheSameObjectiveFunctions(map));
  }
  
   @Test
  public void selectedsExperimentsHasTheSameObjectiveFunctionsTestRightOrder2(){
    HashMap<String, String[]> map = new HashMap<>();
    map.put("123",  new String[] { "String1", "string2"});
    map.put("124",  new String[] { "String1", "string2"});
    map.put("125",  new String[] { "String1", "string2"});
    map.put("126",  new String[] { "String1", "string2"});
    
    Assert.assertTrue(Validators.selectedsExperimentsHasTheSameObjectiveFunctions(map));
  }
  
  
}
