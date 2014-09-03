//
//import com.ufpr.br.opla.charts.ReadObjectives;
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.Test;
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
///**
// *
// * @author elf
// */
//public class ReadObjectivesTest {
//  
//  @Test
//  public void teste() throws IOException{
//    int columns[] ={0};
//    System.out.println(
//            ReadObjectives.read(columns, "/Users/elf/Desktop/output/5159742946/Hypervolume/hypervolume.txt"));
//  }
//  
//  @Test
//  public void testDb() throws  Exception{
//   database.Database.setPathToDB("/Users/elf/Desktop/oplatool.db");
//   Statement statement = null;
//   statement = database.Database.getConnection().createStatement();
//   
//   String experimendId = "5159742946";
//   int columns[] ={0};
//   
//    List<List<Double>> content = new ArrayList< >();
//   
//   StringBuilder query = new StringBuilder();
//   query.append("SELECT objectives FROM objectives where experiement_id=")
//        .append(experimendId)
//        .append(" AND execution_id=''");
//   
//   ResultSet result = statement.executeQuery(query.toString());
//   while(result.next()){
//      String objs = result.getString("objectives").trim().replace("|", " ");
//      String[] ov = objs.split(" ");
//      List<Double> objectiveValue = new ArrayList<>();
//      for (int i = 0; i < columns.length; i++) {
//       objectiveValue.add(Double.parseDouble(ov[i].trim()));
//     }
//      content.add(objectiveValue);
//   }
//   
//   System.out.println("=>"+content);
//   
//  }
//  
//}
