/*
Fun files is where objective values are stored.
*/
package com.ufpr.br.opla.results;

/**
 *
 * @author elf`
 */
public class FunResults {
    
    
    private Integer id; //DB propose
    
    /**
     * Objectives are stored on a single attribute,
     * the values are separated by pipe ("|").
     *  
     */
    private String objectives;

    public String getObjectives() {
        return objectives;
    }
    
    /**
     *  String objs should be a string of values separated with pipes |.
     * 
     * Ex: 0.19191919 | 0.199193393 | 39393993
     * 
     * @param objs 
     */
    public void setObjectives(String objs) {
        this.objectives = objs;
    }
    
    public Integer getId(){
        return this.id;
    }
    
}
