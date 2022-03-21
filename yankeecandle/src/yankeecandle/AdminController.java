package yankeecandle;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author Logan Jolicoeur
 */
public class AdminController {
    
    @FXML
    private AnchorPane rootpane;
    
    
    @FXML 
    private void signOut(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage adminStage = (Stage) rootpane.getScene().getWindow();
            adminStage.close();
    }
    
    
}
