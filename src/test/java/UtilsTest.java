
import com.ufpr.br.opla.utils.Utils;
import java.util.Arrays;
import java.util.List;
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
    List<String> expected = Arrays.asList("concerns.profile.uml, patterns.profile.uml, relationships.profile.uml, smarty.profile.uml");
    String result = Utils.getProfilesUsedForSelectedExperiment("4574212955", "src/test/resources/output/");
    
    for (String profile : expected) {
      if(!result.contains(profile))
        Assert.fail("Returned List dont contains profile: " + profile);
    }
  }
  
}
