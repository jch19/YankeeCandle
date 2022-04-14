package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBConnector;
import util.Roles;

/**
 *
 * @author Logan Jolicoeur
 * 
 */
public class SignUpController implements Initializable {
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;
    private String query;

    
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    private TextField email;
    
    @FXML 
    private TextField fname;
    
    @FXML
    private TextField lname;
    
    @FXML
    private TextField password;
    
    @FXML
    private Label createMsg;
    
    @FXML
    private ComboBox role_select;
    
    @FXML 
    private TextField user_answer;  
    
    Roles role = new Roles();
    
     
    private final ObservableList<String> roleList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {         
        roleList.add(role.roles.get(1));
        roleList.add(role.roles.get(2));
        roleList.add(role.roles.get(3));

        role_select.setItems(roleList);
        role_select.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void signUp(ActionEvent event){        
        
        conn = connector.connect();
        
        
        if(email.getText().trim().isEmpty() || fname.getText().trim().isEmpty() || 
                lname.getText().trim().isEmpty() 
                || password.getText().trim().isEmpty() || user_answer.getText().trim().isEmpty()){
            
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error: Fill all items.");
            alert.showAndWait();
            
        }else{
            insertQuery();

        }  
    }
    
    private void insertQuery()
    {
        try{
             if(checkEmail(email.getText())){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Email already exists.");
                alert.showAndWait();
                 
                }else {
                
                resultSet.close();
                 
                query = "INSERT INTO users (email, name, password, role, question, alive) VALUES (?, ?, ?, ?, ?, 1);";

                prep = conn.prepareStatement(query);

                prep.setString(1, email.getText().trim());
                prep.setString(2, fname.getText().trim() + " " + lname.getText().trim());
                prep.setString(3, password.getText().trim());

                // 1-> User, 2-> Vendor, 3-> Salesperson, 4-> Admin
                if(role_select.getValue().toString().equals("User")){
                    prep.setString(4, "1");
                }else if(role_select.getValue().toString().equals("Vendor"))
                {
                    prep.setString(4, "2");
                }else if(role_select.getValue().toString().equals("Salesperson"))
                {
                    prep.setString(4, "3");
                } 
                
                prep.setString(5, user_answer.getText().trim().toLowerCase());

                prep.execute();

                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Account Created. Welcome "+ fname.getText().trim() + " " + lname.getText().trim() );
                alert.showAndWait();
                       
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); 

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        try {
            conn.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally{
            final Stage loginStage = (Stage) rootpane.getScene().getWindow();
            loginStage.close();
        }

      
        
    }
    
    private boolean checkEmail(String email) throws SQLException {
        
            stat = conn.createStatement();

            resultSet = stat.executeQuery("SELECT email FROM users WHERE email ='"+ email +"' ");

            return resultSet.next();
          
    }
     
}
