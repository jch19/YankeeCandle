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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.DBConnector;

public class ViewCartController implements Initializable {

    @FXML
    private AnchorPane rootpane;

    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;

    Cart cart = null;
    private ObservableList<Cart> cartList = FXCollections.observableArrayList();
    private String query = "";

    @FXML
    protected TableView<Cart> cart_table;

    
    private TableColumn<Cart, String> cart_prodname;

    @FXML
    private TableColumn<Cart, String> cart_pid;

    @FXML
    private TableColumn<Cart, String> cart_quantity;
    
      @FXML
    private TableColumn<Cart, String> cart_editCart;

    public void initialize(URL url, ResourceBundle rb) {
        rootpane.setPrefSize(600, 600);
        conn = connector.connect();
        loadData();

    }

    private void refreshTable() {
        conn = connector.connect();

        try {
            cartList.clear();

            query = "SELECT product_id, quantity FROM cart";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();

            while (resultSet.next()) {

                cartList.add(new Cart(resultSet.getInt("product_id"), resultSet.getInt("quantity")));
            }

            cart_table.setItems(cartList);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {

        refreshTable();

        cart_pid.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        cart_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        Callback<TableColumn<Cart, String>, TableCell<Cart, String>> cellCreator = (TableColumn<Cart, String> param) -> {
            final TableCell<Cart, String> cell = new TableCell<Cart, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        final Button removeProdBtn = new Button();

                        Image cartImage = new Image("res/remove.png");

                        ImageView cartImageView = new ImageView(cartImage);

                        cartImageView.setFitHeight(15);
                        cartImageView.setFitWidth(15);
                        cartImageView.setPreserveRatio(true);

                        removeProdBtn.setGraphic(cartImageView);

                        removeProdBtn.setOnAction(event -> {

                            try {
                                cart = cart_table.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM cart WHERE id  = " + cart.getProduct_id();
                                conn = connector.connect();
                                prep = conn.prepareStatement(query);
                                prep.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        });

                        HBox managebtn = new HBox(removeProdBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(removeProdBtn, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        cart_editCart.setCellFactory(cellCreator);
        cart_table.setItems(cartList);

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
    void checkout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckoutView.fxml"));

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
