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

public class ViewCartController {

    @FXML
    private AnchorPane rootpane;

   // @FXML
   // private Button exitProgram;

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage viewCartStage = (Stage) rootpane.getScene().getWindow();
        viewCartStage.close();
    }

    @FXML
    void checkout(ActionEvent event) {
        //will move to checkout for user upon clicking button 
    }

}
