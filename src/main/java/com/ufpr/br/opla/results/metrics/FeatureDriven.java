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
public class FeatureDriven {
    
    private String msiAggregation;
    private String cdac;
    private String cdai;
    private String cdao;
    private String cibc;
    private String iibc;
    private String oobc;
    private String lcc;
    private String lccClass;
    private String cdaClass;
    private String cibClass;
    
    private final Execution execution;
    private final Database db;

    public FeatureDriven(Execution execution, Database db){
        this.execution = execution;
        this.db = db;
    }
    
    public String getMsiAggregation() {
        return msiAggregation;
    }

    public void setMsiAggregation(String msiAggregation) {
        this.msiAggregation = msiAggregation;
    }

    public String getCdac() {
        return cdac;
    }

    public void setCdac(String cdac) {
        this.cdac = cdac;
    }

    public String getCdai() {
        return cdai;
    }

    public void setCdai(String cdai) {
        this.cdai = cdai;
    }

    public String getCdao() {
        return cdao;
    }

    public void setCdao(String cdao) {
        this.cdao = cdao;
    }

    public String getCibc() {
        return cibc;
    }

    public void setCibc(String cibc) {
        this.cibc = cibc;
    }

    public String getIibc() {
        return iibc;
    }

    public void setIibc(String iibc) {
        this.iibc = iibc;
    }

    public String getOobc() {
        return oobc;
    }

    public void setOobc(String oobc) {
        this.oobc = oobc;
    }

    public String getLcc() {
        return lcc;
    }

    public void setLcc(String lcc) {
        this.lcc = lcc;
    }

    public String getLccClass() {
        return lccClass;
    }

    public void setLccClass(String lccClass) {
        this.lccClass = lccClass;
    }

    public String getCdaClass() {
        return cdaClass;
    }

    public void setCdaClass(String cdaClass) {
        this.cdaClass = cdaClass;
    }

    public String getCibClass() {
        return cibClass;
    }

    public void setCibClass(String cibClass) {
        this.cibClass = cibClass;
    }
    
    public Execution getExecution(){
        return this.execution;
    }

    public void save() {
        StringBuilder query = new StringBuilder();
        query.append("insert into FeatureDrivenMetrics (msiAggregation, cdac, cdai, cdao, cibc, iibc, oobc, lcc, lccClass, cdaClass, cibClass, execution_id) values (");
        query.append(this.getMsiAggregation());
        query.append(",");
        query.append(this.getCdac());
        query.append(",");
        query.append(this.getCdai());
        query.append(",");
        query.append(this.getCdao());
        query.append(",");
        query.append(this.getCibc());
        query.append(",");
        query.append(this.getIibc());
        query.append(",");
        query.append(this.getOobc());
        query.append(",");
        query.append(this.getLcc());
        query.append(",");
        query.append(this.getLccClass());
        query.append(",");
        query.append(this.getCdaClass());
        query.append(",");
        query.append(this.getCibClass());
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
