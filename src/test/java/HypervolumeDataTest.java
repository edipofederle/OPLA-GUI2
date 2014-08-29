
import junit.framework.Assert;
import org.junit.Test;
import com.ufpr.br.opla.utils.MathUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class HypervolumeDataTest {
  
  @Test @Ignore
  public void testMath(){
    List<Double> values = new ArrayList<>();
    values.add(10d);
    values.add(40d);
    values.add(50d);
    values.add(30.5);
    values.add(500.45);
    
    
    Assert.assertEquals("126.19", String.format("%.2f", MathUtils.mean(values)));
    Assert.assertEquals(187.595774, MathUtils.stDev(values));
  }
  
}
