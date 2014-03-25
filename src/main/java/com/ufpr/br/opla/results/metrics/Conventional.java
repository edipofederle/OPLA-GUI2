/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Conventional {

    private String macAggregation;
    private String choesion;
    private String meanDepComps;
    private String meanNumOps;
    private String sumClassesDepIn;
    private String sumClassesDepOut;
    private String sumDepIn;
    private String sumDepOut;

    private final Execution execution;
    private final Database db;

    public Conventional(Execution execution, Database db) {
        this.db = db;
        this.execution = execution;
    }

    public String getMacAggregation() {
        return macAggregation;
    }

    public void setMacAggregation(String macAggregation) {
        this.macAggregation = macAggregation;
    }

    public String getChoesion() {
        return choesion;
    }

    public void setChoesion(String choesion) {
        this.choesion = choesion;
    }

    public String getMeanDepComps() {
        return meanDepComps;
    }

    public void setMeanDepComps(String meanDepComps) {
        this.meanDepComps = meanDepComps;
    }

    public String getMeanNumOps() {
        return meanNumOps;
    }

    public void setMeanNumOps(String meanNumOps) {
        this.meanNumOps = meanNumOps;
    }

    public String getSumClassesDepIn() {
        return sumClassesDepIn;
    }

    public void setSumClassesDepIn(String sumClassesDepIn) {
        this.sumClassesDepIn = sumClassesDepIn;
    }

    public String getSumClassesDepOut() {
        return sumClassesDepOut;
    }

    public void setSumClassesDepOut(String sumClassesDepOut) {
        this.sumClassesDepOut = sumClassesDepOut;
    }

    public String getSumDepIn() {
        return sumDepIn;
    }

    public void setSumDepIn(String sumDepIn) {
        this.sumDepIn = sumDepIn;
    }

    public String getSumDepOut() {
        return sumDepOut;
    }

    public void setSumDepOut(String sumDepOut) {
        this.sumDepOut = sumDepOut;
    }

    public void save() {
        StringBuilder query = new StringBuilder();
        
        query.append("insert into ConventionalMetrics (choesion,"
                + " macAggregation, meanDepComps, meanNumOps, sumClassesDepIn,"
                + " sumClassesDepOut, sumDepIn, sumDepOut, execution_id)"
                + " values (");
        
        query.append(this.getChoesion());
        query.append(",");
        query.append(this.getMacAggregation());
        query.append(",");
        query.append(this.getMeanDepComps());
        query.append(",");
        query.append(this.getMeanNumOps());
        query.append(",");
        query.append(this.sumClassesDepIn);
        query.append(",");
        query.append(this.sumClassesDepOut);
        query.append(",");
        query.append(this.sumDepIn);
        query.append(",");
        query.append(this.sumDepOut);
        query.append(",");
        query.append(this.execution.getId());
        query.append(")");
        
        try {
            this.db.getConnection().executeUpdate(query.toString());
        } catch (MissingConfigurationException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Elegance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
