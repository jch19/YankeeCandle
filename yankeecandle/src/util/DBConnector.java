package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Logan Jolicoeur
 */
public class DBConnector {
    
      public Connection connect(){
       
       Connection conn = null;
       
       try{
           //Point to the directory where yankee.db is located to handle records 
           String url = "jdbc:sqlite:src/database/yankee.db";
           
           conn = DriverManager.getConnection(url);
           
       }catch(SQLException e){
           System.out.println(e.getMessage());
       } 
       
       return conn;
       
   }
}
