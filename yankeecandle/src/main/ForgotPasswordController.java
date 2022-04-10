package main;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.DBConnector;

/**
 * FXML Controller class 
 *
 * @author Logan Jolicoeur
 */
public class ForgotPasswordController {

    @FXML
    private TextField user_email;
    
    @FXML
    private TextField user_answer;
    
    @FXML
    private TextField user_password;
    
    @FXML
    private Label success_label;
    
    DBConnector connector = new DBConnector();
    
    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private String query;
    private ResultSet resultSet;
    
    @FXML
    private void retrieveData(ActionEvent event){
        
        conn = connector.connect();
        
        String email = user_email.getText().trim();
        String answer = user_answer.getText().trim();
        
        if(email.isEmpty() || answer.isEmpty() )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        }else{
            searchQuery(email, answer); 
        }
    }
    
    private void searchQuery(String email, String answer){
        try{
            query = "SELECT email, password, question FROM users WHERE email= ? AND question = ?";
            
            prep = conn.prepareStatement(query);
            prep.setString(1, email.trim());
            prep.setString(2, answer.trim());
            resultSet = prep.executeQuery();
            
            if(resultSet.next()){
                user_password.setText(resultSet.getString("password"));
                success_label.setVisible(true);
                conn.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Incorrect information.");
                alert.showAndWait();
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
  
    
}
