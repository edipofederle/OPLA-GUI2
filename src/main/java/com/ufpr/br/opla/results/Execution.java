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
    
    private String id;
    private List<InfoResult> infos;
    private List<FunResults> funs;
    
    //TODO metrics, time, hypervolume etc.
    
    public Execution(){
        this.id = Id.generateUniqueId();
    }

    public List<InfoResult> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoResult> infos) {
        this.infos = infos;
    }

    public List<FunResults> getFuns() {
        return funs;
    }

    public void setFuns(List<FunResults> funs) {
        this.funs = funs;
    }

    public String getId() {
        return id;
    }
      
}
