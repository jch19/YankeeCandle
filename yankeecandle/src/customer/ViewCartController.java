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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import util.DBConnector;
import java.util.Random;

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

    @FXML
    private TableColumn<Cart, String> cart_prodname;

    @FXML
    private TableColumn<Cart, Integer> cart_quantity;
    
    @FXML
    private TableColumn<Cart, ImageView> cart_image;
    
    @FXML
    private TableColumn<Cart, String> cart_editCart;
    
    private Random rand = new Random();
    
    private String[] provider = {"Amazon", "UPS", "Fedex", "MailBunny", "MailMe"};
    
    private String[] status = {"Procesing", "Completed", "Shipped"};

    public void initialize(URL url, ResourceBundle rb) {
        conn = connector.connect();
        loadData();
    }


    private void refreshTable() {
        conn = connector.connect();

        try {
            cartList.clear();

            query = "SELECT cart.id AS id, products.name AS name, cart.quantity AS quantity, products.image AS image FROM cart JOIN products WHERE products.id = cart.product_id AND user_id ="+userID.uID+";";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            
            InputStream input;
            Image image;
            ImageView finalImage;
           

            while (resultSet.next()) {
                
                input = resultSet.getBinaryStream("image");
                image = new Image(input);
                finalImage = new ImageView(image);
                finalImage.setFitHeight(50);
                finalImage.setFitWidth(50);

                cartList.add(new Cart(resultSet.getInt("id"), 
                        resultSet.getString("name"), resultSet.getInt("quantity"), 
                        finalImage));
            }

            cart_table.setItems(cartList);
            
            


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {

        refreshTable();

        cart_prodname.setCellValueFactory(new PropertyValueFactory<>("name"));
        cart_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cart_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        

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
                                query = "DELETE FROM cart WHERE id  = " + cart.getId();
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

    }

    @FXML
    void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        try{
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            final Stage viewCart = (Stage) rootpane.getScene().getWindow();
            viewCart.close();
        }
    }
    

    @FXML
    void checkout(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("CheckoutView.fxml"));
        loader.load();
        
        if(cart_table.getItems().size() > 0){
              
             try{                
                query = "INSERT INTO order_details (user_id, total, product_id, quantity, provider, status)  \n" +
                        "SELECT user_id, SUM(price) as total, product_id, cart.quantity, ?, ? FROM cart JOIN products \n" +
                        "WHERE products.id = cart.id AND user_id = " + userID.uID;

                prep = conn.prepareStatement(query);
                
                prep.setString(1, provider[rand.nextInt(provider.length)]);
                prep.setString(2, status[rand.nextInt(status.length)]);

                prep.executeUpdate();
                                
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
              Parent parent = loader.getRoot();
              Stage stage = new Stage();
              stage.setResizable(false);
              stage.setScene(new Scene(parent));
              stage.initStyle(StageStyle.DECORATED);
              stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("There is nothing in your cart.");
            alert.showAndWait();
        }
    }

    @FXML
    void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        try{
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            final Stage viewCart = (Stage) rootpane.getScene().getWindow();
            viewCart.close();
        }
    }

}
