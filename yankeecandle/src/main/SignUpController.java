package main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
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
    
    Roles role = new Roles();
    
     
    private final ObservableList<String> roleList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {         
        roleList.add(role.roles.get(1));
        roleList.add(role.roles.get(2));
        roleList.add(role.roles.get(3));

        role_select.setItems(roleList);
    }
    
    @FXML
    private void signUp(ActionEvent event){
        
        if(role_select.getValue().equals(null))
        {
            System.out.println("Here I am!");
        }
        
        if(email.getText().trim().isEmpty() || fname.getText().trim().isEmpty() || 
                lname.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            
            
            createMsg.setVisible(true);
            createMsg.setText("Please enter valid credentials");
            
        }else{
            try{
                
                if(checkEmail(email.getText())){
                    createMsg.setVisible(true);
                    createMsg.setText("Email already exists");
                }else {
                
                    try{
                        
                    conn = connector.connect();

                    String query = "INSERT INTO users (email, name, password, role, alive) VALUES (?, ?, ?, ?, 1);";
                   

                    prep = conn.prepareStatement(query);
                    
                    prep.setString(1, email.getText().trim());
                    prep.setString(2, fname.getText().trim() + " " + lname.getText().trim());
                    prep.setString(3, password.getText().trim());
                    
                    // 1-> User, 2-> Vendor, 3-> Salesperson, 4-> Admin
                    if(role_select.getValue().toString().equals("User")){
                        prep.setString(4, "1");
                    }else if(role_select.getValue().toString().equals("Salesperson"))
                    {
                        prep.setString(4, "2");
                    }else if(role_select.getValue().toString().equals("Vendor"))
                    {
                        prep.setString(4, "3");
                    } 
                    
                    prep.execute();
                    
                    createMsg.setVisible(true);
                    createMsg.setText("Your account has been created.");   
                        
                        }catch(SQLException e){
                            e.printStackTrace();
                        } 
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            
            

        }  
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException{
         AnchorPane pane = FXMLLoader.load(getClass().getResource("Login.fxml"));
         rootpane.getChildren().setAll(pane);
    }
    
    @FXML
    private void shop(ActionEvent event) throws IOException{
           Parent root = FXMLLoader.load(getClass().getResource("ShopView.fxml"));

           Scene scene = new Scene(root);
           Stage stage = new Stage();

           stage.setTitle("YankeeCandle");
           stage.setScene(scene);
           stage.show();

           final Stage loginStage = (Stage) rootpane.getScene().getWindow();
           loginStage.close();
    }
    
    private boolean checkEmail(String email) throws SQLException {
        
        
            conn = connector.connect();
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT email FROM users WHERE email ='"+ email +"' ");

            return rs.next();
          
    }
     
}
