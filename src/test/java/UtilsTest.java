
import com.ufpr.br.opla.utils.Utils;
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
public class UtilsTest {
  

          
  @Test
  public void testProfileUsed(){
    String expected = "concerns.profile.uml, patterns.profile.uml, relationships.profile.uml, smarty.profile.uml";
    Assert.assertTrue(Utils.getProfilesUsedForSelectedExperiment("4574212955", "src/test/resources/output/").contains(expected));
  }
  
}
