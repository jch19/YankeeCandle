

package main;

import customer.CustomerViewController;
import customer.ViewCandlesController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import util.DBConnector;
import customer.userID;

/**
 *
 * @author Logan Jolicoeur
 */
public class LoginController{
     
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField password;
    
    @FXML
    private Label signinError;
    
    DBConnector connector = new DBConnector();
    
    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;
    private String query = "";
    
    private String emailFound = "";
    private String passwordFound = "";
    private int role; 
    private int alive; // 1=notbanned, 0 = banned
    
    /*
        function that handles when we press the login button    
    */
     @FXML 
     private void login(ActionEvent event) throws IOException {
         conn = connector.connect();
         
         String user_email = email.getText().trim();
         String user_password = password.getText().trim();
         
       try{
          
              String query = "SELECT id, role, alive FROM users WHERE email = ? AND password = ?";
              prep = conn.prepareStatement(query);
              
              prep.setString(1, user_email);
              prep.setString(2, user_password);
              
              resultSet  = prep.executeQuery();
                            
              role =  resultSet.getInt("role");
              alive = resultSet.getInt("alive");
              int id = resultSet.getInt("id");
                            
              if(alive != 0){
                if(role == 1){ //User View (Customer)
                      Parent root = FXMLLoader.load(getClass().getResource("/customer/CustomerView.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();
                      
                      //Grab the uID and set it;
                      //TODO add a sessionID to the DB
                      userID.uID = id;
                      
                      try{
                        conn.close();
                      }catch(SQLException ex){
                        ex.printStackTrace();
                      }finally{
                        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                        loginStage.close();
                      }

                }else if(role == 2){ //Vendor Login
                     Parent root = FXMLLoader.load(getClass().getResource("/vendor/VendorView.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      
                      try{
                            conn.close();
                      }catch(SQLException ex){
                          ex.printStackTrace();
                      }finally{
                        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                        loginStage.close();
                      }

                }else if(role == 3){ //Salesperson view 
                     Parent root = FXMLLoader.load(getClass().getResource(""));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      try{
                           conn.close();
                      }catch(SQLException ex){
                          ex.printStackTrace();
                      }finally{
                        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                        loginStage.close();
                      }
                    
                    
                }else if(role == 4){
                      Parent root = FXMLLoader.load(getClass().getResource("/admin/Admin.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      try{
                           conn.close();
                      }catch(SQLException ex){
                          ex.printStackTrace();
                      }finally{
                        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                        loginStage.close();
                      }
                }

          }else{
              signinError.setVisible(true);
              signinError.setText("You have been banned.");
          }
              
         
          password.clear();
           
          
           
       }catch (SQLException e){
           e.printStackTrace();
       }
     }
     
     @FXML
     private void forgotPwd(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
        Parent parent = fxmlLoader.load();
 
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); 
     }
     
     @FXML
     private void signUp(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml")); //NO FXML found, redo

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        try{
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            final Stage loginStage = (Stage) rootpane.getScene().getWindow();
            loginStage.close();
        }
     }
     
}
