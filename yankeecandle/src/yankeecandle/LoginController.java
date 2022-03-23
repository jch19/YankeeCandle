

package yankeecandle;

import java.io.IOException;
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
    
    User admin = new User("admin", "1234", "admin");
    User user1 = new User("user@gmail.com", "1234", "user");
    User vendor1 = new User("vendor@gmail.com", "1234", "vendor");
   
    
    /*
        function that handles when we press the login button    
    */
     @FXML 
     private void login(ActionEvent event) throws IOException {
       if(email.getText().equals(admin.getEmail()) && password.getText().equals(admin.getPassword())){   
            Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage loginStage = (Stage) rootpane.getScene().getWindow();
            loginStage.close();
            
       }
 
       if(email.getText().equals(user1.getEmail()) && password.getText().equals(user1.getPassword())){   
            Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage loginStage = (Stage) rootpane.getScene().getWindow();
            loginStage.close();
            
       }
       
       else{
            signinError.setVisible(true); //show that the creds are invalid 
       }
        password.clear();
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
     
     
}
