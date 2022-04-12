package customer;

import admin.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.DBConnector;

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

    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    Product product = null;
    private ObservableList<Product> productList = FXCollections.observableArrayList();
    private String query;

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void addPinkSands(ActionEvent event) throws SQLException {
       conn = connector.connect();
       
       query = "INSERT INTO cart(id, user_id, product_id, quantity) VALUES(?, ?, ?, 1)";
       
       prep = conn.prepareStatement(query);
       
       prep.execute(); 
    }

    @FXML
    void addCashmere(ActionEvent event) {

    }

    @FXML
    void addLemonade(ActionEvent event) {

    }

    @FXML
    void viewCartButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewCart.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
