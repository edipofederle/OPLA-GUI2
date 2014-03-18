package com.ufpr.br.opla.results;

import com.ufpr.br.opla.exceptions.MissingConfigurationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elf
 */
public class DatabaseInitializer {
    
    private static Connection connection;
    private static String pathDatabase = "";

    /**
     * Create a connection with database and returns a Statement to working with.
     * 
     * @return
     * @throws ClassNotFoundException 
     * @throws java.sql.SQLException 
     * @throws com.ufpr.br.opla.exceptions.MissingConfigurationException 
     */
    public static Statement initialize() throws MissingConfigurationException, SQLException, ClassNotFoundException {
        
        if ("".equals(pathDatabase))
            throw new MissingConfigurationException("Path to database should not be blank");
       
        
        return getConnection();
    }  

    private static Statement getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Statement statement = null;
        connection = DriverManager.getConnection("jdbc:sqlite:"+pathDatabase);
        statement = connection.createStatement();
        statement.setQueryTimeout(30); //in seconds
        
        
        return statement;
    }

    public static void setPathToDb(String pathToDB) {
        pathDatabase = pathToDB;
    }

}
