package yankeecandle;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;




/**
 *
 * @author Logan Jolicoeur
 */
public class ShopController {
     
    @FXML 
    AnchorPane rootpane;
     
    ArrayList <Image> candleList = new ArrayList<>();
    
    GridPane gridPaneMain = new GridPane();

    
 
     @FXML
     private void login(ActionEvent event) throws IOException {
      
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
        loginStage.close();
          
     }
     
     
     
     @FXML
     private void signup(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
        loginStage.close();
     }
}
    

