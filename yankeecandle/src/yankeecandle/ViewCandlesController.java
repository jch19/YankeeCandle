package yankeecandle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewCandlesController {

    @FXML
    private AnchorPane rootpane;
    
    @FXML
    private Button addLemonade;

    @FXML
    private Button viewCartButton;

    @FXML
    private Button addCashmere;

    @FXML
    private Button exitProgram;

    @FXML
    private Button addPinkSands;

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage viewCandlesStage = (Stage) rootpane.getScene().getWindow();
            viewCandlesStage.close();
    }

    @FXML
    void addPinkSands(ActionEvent event) {

    }

    @FXML
    void addCashmere(ActionEvent event) {

    }

    @FXML
    void addLemonade(ActionEvent event) {

    }

    @FXML
    void viewCartButton(ActionEvent event) {

    }

}
