
import com.ufpr.br.opla.gui2.UserHome;
import org.junit.Test;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class ReaderSolutionsFilesTest {
    
    @Test
    public void test(){
        database.Database.setPathToDB(UserHome.getPathToDb());
        System.out.println(db.Database.getOrdenedObjectives("8605639795"));
    }
    
}
