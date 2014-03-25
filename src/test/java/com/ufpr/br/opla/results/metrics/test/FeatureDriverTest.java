package com.ufpr.br.opla.results.metrics.test;

import com.ufpr.br.opla.db.Database;
import com.ufpr.br.opla.results.Execution;
import com.ufpr.br.opla.results.metrics.FeatureDriven;
import java.sql.Statement;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author elf
 */
public class FeatureDriverTest {
    
    @Test
    public void saveFeatureDrivenMetrics() throws Exception{
        
        Database db     = mock(Database.class);
        Statement st    = mock(Statement.class);
        Execution exec  = mock(Execution.class);                      
        
        when(exec.getId()).thenReturn("12");
        when(db.getConnection()).thenReturn(st);
        
        FeatureDriven fd = new FeatureDriven(exec, db);
        fd.setCdaClass("10");
        fd.setCdac("1");
        fd.setCdai("3");
        fd.setCdao("3");
        fd.setCibClass("1");
        fd.setCibc("1");
        fd.setIibc("12");
        fd.setLcc("12");
        fd.setLccClass("3");
        fd.setMsiAggregation("3");
        fd.setMsiAggregation("1");
        fd.setOobc("4");
        
        String query = buildQuery(fd);
        
              
        fd.save();
        
        verify(db.getConnection()).executeUpdate(query);
        
    }
    
    private String buildQuery(FeatureDriven fd){
        StringBuilder query = new StringBuilder();
        query.append("insert into FeatureDrivenMetrics (msiAggregation, cdac, cdai, cdao, cibc, iibc, oobc, lcc, lccClass, cdaClass, cibClass, execution_id) values (");
        query.append(fd.getMsiAggregation());
        query.append(",");
        query.append(fd.getCdac());
        query.append(",");
        query.append(fd.getCdai());
        query.append(",");
        query.append(fd.getCdao());
        query.append(",");
        query.append(fd.getCibc());
        query.append(",");
        query.append(fd.getIibc());
        query.append(",");
        query.append(fd.getOobc());
        query.append(",");
        query.append(fd.getLcc());
        query.append(",");
        query.append(fd.getLccClass());
        query.append(",");
        query.append(fd.getCdaClass());
        query.append(",");
        query.append(fd.getCibClass());
        query.append(",");
        query.append(fd.getExecution().getId());
        query.append(")");
        
        return query.toString();
    }
    
}
