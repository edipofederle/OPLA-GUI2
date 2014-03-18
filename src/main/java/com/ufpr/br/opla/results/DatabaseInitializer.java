package com.ufpr.br.opla.results;

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
    private static final String PATH_TO_DATABASE = "resources/opla.db";

    /**
     * Create a connection with database and returns a Statement to working with.
     * 
     * @return
     * @throws ClassNotFoundException 
     */
    public static Statement initialize() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Statement statement = null;
               
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+PATH_TO_DATABASE);
            statement = connection.createStatement();
            statement.setQueryTimeout(30); //in seconds
        }catch(SQLException e){
            Logger.getLogger(DatabaseInitializer.class.getName()).log(Level.SEVERE, null, "Ops, Error when try initialize DB: " +e);
        }
        
        return statement;
    }  
}