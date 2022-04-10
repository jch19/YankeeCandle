

package main;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import util.DBConnector;


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
    
    String emailFound = "";
    String passwordFound = "";
    int role; 
    int alive; // 1=notbanned, 0 = banned
    
    /*
        function that handles when we press the login button    
    */
     @FXML 
     private void login(ActionEvent event) throws IOException {
         
       try{
          if(loginValidation(email.getText().trim(), password.getText().trim()))
          {
              String query = "SELECT role, alive FROM users WHERE email = '"+emailFound+"' AND '"+ passwordFound+"';";
              prep = conn.prepareStatement(query);
              ResultSet rs = prep.executeQuery();
              
              role =  Integer.parseInt(rs.getString("role"));
              alive = Integer.parseInt(rs.getString("alive"));
              
              if(alive != 0){
                if(role == 1){ //User View (Customer)
                      Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                      loginStage.close();


                }else if(role == 2){ //Vendor Login
                     Parent root = FXMLLoader.load(getClass().getResource("VendorView.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                      loginStage.close();

                }else if(role == 3){ //Salesperson view 
                      Parent root = FXMLLoader.load(getClass().getResource("ShopView.fxml")); //NO FXML found, redo

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                      loginStage.close();
                }else if(role == 4){
                     Parent root = FXMLLoader.load(getClass().getResource("/admin/Admin.fxml"));

                      Scene scene = new Scene(root);
                      Stage stage = new Stage();

                      stage.setTitle("YankeeCandle");
                      stage.setScene(scene);
                      stage.show();

                      final Stage loginStage = (Stage) rootpane.getScene().getWindow();
                      loginStage.close();
                }

          }else{
              signinError.setVisible(true);
              signinError.setText("You have been banned.");
          }
              
          } else{
            signinError.setVisible(true); //show that the creds are invalid 
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
 
        Scene scene = new Scene(parent, 600, 262);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait(); 
     }
     
     @FXML
     private void signUp(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Signup.fxml"));
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
     
     public boolean loginValidation(String email, String password) throws SQLException{
         
         conn = connector.connect();
         
         String query = "SELECT email, name, role FROM users WHERE email = ? AND password = ?";
         
         prep = conn.prepareStatement(query);
         prep.setString(1, email.trim());
         prep.setString(2, password.trim());
         ResultSet rs = prep.executeQuery();
         if(rs.next()){
             emailFound = email;
             passwordFound = password;
             return true;
         }else{
             return false;
         }
         
     }
     
     
}
