
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
    String[] expected = "patterns.profile.uml, concerns.profile.uml,  relationships.profile.uml, smarty.profile.uml".split(", ");
    String result = Utils.getProfilesUsedForSelectedExperiment("4574212955", "src/test/resources/output/");
    
    for (String profile : expected) {
      if(!result.contains(profile.trim())) 
        Assert.fail("Error. List dont contains profile: " + profile.trim());
    }
  }
  
}
