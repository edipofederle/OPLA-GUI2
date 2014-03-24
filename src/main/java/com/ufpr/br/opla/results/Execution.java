package com.ufpr.br.opla.results;

import com.ufpr.br.opla.utils.Id;
import java.util.List;



/**
 *
 * Essa classe representa cada execução de um dado experiementos.
 * 
 *
 */
public class Execution {
    
    private final String id;
    private List<InfoResult> infos;
    private FunResults funs;
    private final Experiment experiment;
    
    //TODO metrics, time, hypervolume etc.
    
    public Execution(Experiment experiment){
        this.id = Id.generateUniqueId();
        this.experiment = experiment;
    }

    public List<InfoResult> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoResult> infos) {
        this.infos = infos;
    }

    public FunResults getFuns() {
        return funs;
    }

    public void setFuns(FunResults funs) {
        this.funs = funs;
    }

    public String getId() {
        return this.id;
    }
    
    public Experiment getExperiement(){
        return this.experiment;
    }
      
}
