package customer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.DBConnector;

public class OrdersViewController implements Initializable {

    @FXML
    private AnchorPane rootpane;

    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;

    Orders order = null;
    private ObservableList<Orders> orderList = FXCollections.observableArrayList();
    private String query = "";

    @FXML
    protected TableView<Orders> order_table;

     @FXML
    private TableColumn<Orders, String> id;

    @FXML
    private TableColumn<Orders, String> orderid;

    @FXML
    private TableColumn<Orders, String> prodid;

    @FXML
    private TableColumn<Orders, String> quantity;

    public void initialize(URL url, ResourceBundle rb) {
        rootpane.setPrefSize(600, 600);
        conn = connector.connect();
        loadData();

    }

    private void refreshTable() {
        conn = connector.connect();

        try {
            orderList.clear();

            query = "SELECT * FROM order_items";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();

            InputStream input;
            Image image;
            ImageView finalImage;

            while (resultSet.next()) {

                orderList.add(new Orders(resultSet.getInt("id"), resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"), resultSet.getInt("quantity")));
            }

            order_table.setItems(orderList);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadData(){
                        
        refreshTable();
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderid.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        prodid.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        order_table.setItems(orderList);
         
    }

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));

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
