package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Logan Jolicoeur
 */
public class ConnectionHandler {
    
    public Connection connection;
    
    public Connection getConnection(){
        String connectionURL = "jdbc:sqlite:db/yankee.db";
        
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connectionURL);

        }catch(Exception e){
            e.printStackTrace();
        }

        return connection;
        
    }
}
