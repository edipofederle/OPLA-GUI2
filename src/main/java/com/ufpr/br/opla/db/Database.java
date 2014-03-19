package com.ufpr.br.opla.db;

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
    private static Database uniqueDB;
    private static String pathDatabase;
       
    private Database(){}
    
    public static synchronized Database getInstance(){
        if(uniqueDB  == null)
            uniqueDB = new Database();
        return uniqueDB;
    }

    /**
     * Create a connection with database and returns a Statement to working with.
     * 
     * @return
     * @throws ClassNotFoundException 
     * @throws java.sql.SQLException 
     * @throws com.ufpr.br.opla.exceptions.MissingConfigurationException 
     */
    public Statement getConnection() throws MissingConfigurationException,
                                            SQLException,
                                            ClassNotFoundException {
        
        if ("".equals(pathDatabase))
            throw new MissingConfigurationException("Path to database should not be blank");
       
        
        return makeConnection();
    }  
    
    private Statement makeConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        
        connection = DriverManager.getConnection("jdbc:sqlite:"+pathDatabase);
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30); //in seconds
                
        return statement;
    }
    
    public static void setPathToDB(String path){
        pathDatabase = path;
    }

}
