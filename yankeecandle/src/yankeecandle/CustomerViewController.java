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

public class CustomerViewController {

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Button viewOrders;

    @FXML
    private Button viewCandles;

    @FXML
    private Button editCart;

    @FXML
    private Button exitProgram;

    @FXML
    void viewCandles(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewCandles.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage CustomerViewStage = (Stage) rootpane.getScene().getWindow();
        CustomerViewStage.close();
    }

    @FXML
    void editCart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewCart.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage CustomerViewStage = (Stage) rootpane.getScene().getWindow();
        CustomerViewStage.close();
    }

    @FXML
    void viewOrders(ActionEvent event) {

    }

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage CustomerViewStage = (Stage) rootpane.getScene().getWindow();
        CustomerViewStage.close();
    }

}
