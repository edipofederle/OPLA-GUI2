package com.ufpr.br.opla.results.metrics;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.exceptions.MissingConfigurationException;
import com.ufpr.br.opla.results.Execution;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
public class Elegance {
    
    private String nac;
    private String atmr;
    private String ec;
    private final Execution execution;
    
    private final Database db;
    
    
    /**
     *  
     * @param execution
     * @param db 
     */
    public Elegance(Execution execution, Database db ){
        this.db = db;
        this.execution = execution;
    }

    public String getNac() {
        return nac;
    }

    public void setNac(String nac) {
        this.nac = nac;
    }

    public String getAtmr() {
        return atmr;
    }

    public void setAtmr(String atmr) {
        this.atmr = atmr;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }
    
    /**
     * Save to database
     */
    public void save() {
       String query = "insert into EleganceMetrics (nac, atmr, ec, execution_id) values (30, 10, 20, "+this.execution.getId()+")";
        try {
            this.db.getConnection().executeUpdate(query);
        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
}
