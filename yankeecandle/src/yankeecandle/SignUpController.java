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
 * 
 */
public class SignUpController {
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField password;
    
    @FXML
    private Label createMsg;
    
    @FXML
    private void signUp(ActionEvent event){
        if(email.getText().trim().isEmpty() || password.getText().trim().isEmpty()){
            createMsg.setVisible(true);
            createMsg.setText("Please enter valid credentials");
        }else{
            createMsg.setVisible(true);
            createMsg.setText("Your account has been created");
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
     
}
