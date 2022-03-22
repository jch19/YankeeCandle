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

public class CustomerViewController {

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Menu exit;

    @FXML
    private Button viewOrders;

    @FXML
    private Button viewCandles;

    @FXML
    private Button editCart;

    //Function that handles when we click on logout
    @FXML
    void exitProgram(ActionEvent event) {
        System.exit(0);
    }

    //Function that handles when we click on view candles
    @FXML
    void viewCandles(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ViewCandles.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage customerViewStage = (Stage) rootpane.getScene().getWindow();
        customerViewStage.close();

    }

    //Function that handles when we click on edit cart
    @FXML
    void editCart(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ViewCart.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage customerViewStage = (Stage) rootpane.getScene().getWindow();
        customerViewStage.close();
    }

    //Function that handles when we click on view orders
    @FXML
    void viewOrders(ActionEvent event) {
        
    }

}
