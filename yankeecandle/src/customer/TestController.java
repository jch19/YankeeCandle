/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import util.DBConnector;

/**
 * FXML Controller class
 *
 * @author warso
 */
public class TestController {
 
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;
    
    @FXML
    private Label label;
    
    
    @FXML
    private void testTable(ActionEvent event){
        
          conn = connector.connect();
        
          try{
            String query = "SELECT name FROM users WHERE id=1;";
            
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            label.setText(resultSet.getString("name"));
           
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        
    }
    
}
