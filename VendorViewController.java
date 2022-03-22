/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yankeecandle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author kkirk
 */
public class VendorViewController {
     @FXML
    private AnchorPane pane;

    @FXML
    private Menu exit;

    @FXML
    private Button ViewInventory;

    @FXML
    private Button UpdateStock;

 @FXML
    void exitProgram(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    void ViewInventory(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("VendorView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage VendorViewStage = (Stage) pane.getScene().getWindow();
        VendorViewStage.close();

    }
    
     @FXML
    void UpdateStock(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("VendorView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage VendorViewStage = (Stage) pane.getScene().getWindow();
        VendorViewStage.close();
}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

}
