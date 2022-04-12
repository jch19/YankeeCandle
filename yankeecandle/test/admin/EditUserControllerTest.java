/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.sql.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import util.DBConnector;

/**
 *
 * @author Logan Jolicoeur
 */
public class EditUserControllerTest {   
    
    @Test
    public void testInsertData(){
        
        DBConnector connector = new DBConnector();
        
        
        
        try(Connection conn = connector.connect()){
            
            try(Statement stat = conn.createStatement()){
                
                String email = "";
                String name = "";
                String password = ""; 
                String question = ""; 
                int role = 1;
                int active = 1;
                
                
                
                
                
                
                
            }catch(SQLException e){
               fail(e.toString());
               conn.rollback();
            }
            
            conn.setAutoCommit(false);
            
            
        }catch(SQLException e){
            fail(e.toString());
        }
        
        
    }

}
