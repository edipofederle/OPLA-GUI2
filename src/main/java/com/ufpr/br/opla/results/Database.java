package com.ufpr.br.opla.results;

import com.ufpr.br.opla.exceptions.MissingConfigurationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author elf
 */
public class Database {
    
    private static Connection connection;
    private static String pathDatabase;
    
    public Database(String pathToDatabase){
        this.pathDatabase = pathToDatabase;
    }

    /**
     * Create a connection with database and returns a Statement to working with.
     * 
     * @return
     * @throws ClassNotFoundException 
     * @throws java.sql.SQLException 
     * @throws com.ufpr.br.opla.exceptions.MissingConfigurationException 
     */
    public Statement getConnection() throws MissingConfigurationException, SQLException, ClassNotFoundException {
        
        if ("".equals(this.pathDatabase))
            throw new MissingConfigurationException("Path to database should not be blank");
       
        
        return makeConnection();
    }  

    //TODO singleton
    private static Statement makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Statement statement = null;
        connection = DriverManager.getConnection("jdbc:sqlite:"+pathDatabase);
        statement = connection.createStatement();
        statement.setQueryTimeout(30); //in seconds
        
        
        return statement;
    }

}
