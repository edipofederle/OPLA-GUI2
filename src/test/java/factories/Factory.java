/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import com.ufpr.br.opla.results.FunResults;
import com.ufpr.br.opla.results.InfoResult;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elf
 */
public class Factory {

   public static FunResults givenFuns(String executionId) {
        
        FunResults f1 = new FunResults();
        f1.setExecutionId(executionId);
        f1.setObjectives("101010 | 19919191 | 1919911");

        return f1;
    }

    public static List<InfoResult> givenInfos(String executionId) {
        List<InfoResult> infos = new ArrayList<>();
        InfoResult i1 = new InfoResult();
        i1.setExecutionId(executionId);
        i1.setListOfConcerns("c1 | c2");
        i1.setNumberOfAbstraction(1);
        i1.setNumberOfAssociations(2);
        i1.setNumberOfClasses(10);
        i1.setNumberOfDependencies(1);
        i1.setNumberOfGeneralizations(2);
        i1.setNumberOfInterfaces(2);
        i1.setNumberOfPackages(2);
        i1.setNumberOfVariabilities(1);
        i1.setNumberOfassociationsClass(0);

        InfoResult i2 = new InfoResult();
        i2.setExecutionId(executionId);
        i2.setListOfConcerns("c1 | c2");
        i2.setNumberOfAbstraction(1);
        i2.setNumberOfAssociations(2);
        i2.setNumberOfClasses(10);
        i2.setNumberOfDependencies(1);
        i2.setNumberOfGeneralizations(2);
        i2.setNumberOfInterfaces(2);
        i2.setNumberOfPackages(2);
        i2.setNumberOfVariabilities(1);
        i2.setNumberOfassociationsClass(0);

        infos.add(i1);
        infos.add(i2);
        return infos;
    }

}
