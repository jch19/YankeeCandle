package customer;

import admin.EditUserController;
import admin.User;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import util.DBConnector;

public class ViewCandlesController implements Initializable {

    @FXML
    private AnchorPane rootpane;

    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet;
    
    Product product = null;
    private ObservableList <Product> productList = FXCollections.observableArrayList();
    private String query = "";
    
    @FXML
    protected TableView<Product> product_table;
    
    @FXML
    private TableColumn<Product, String> product_id;
    
    @FXML
    private TableColumn<Product, String> product_name;
        
    @FXML
    private TableColumn<Product, String> product_description;
    
    @FXML
    private TableColumn<Product, String> product_price;
    
    @FXML
    private TableColumn<Product, String> product_quantity;
    
    @FXML
    private TableColumn<Product, ImageView> product_image;
    
    @FXML
    private TableColumn<Product, String> product_categoryid;
    
    @FXML
    private TableColumn<Product, String> product_addToCart;
    
    public void initialize(URL url, ResourceBundle rb)
    {         
        rootpane.setPrefSize(600, 600);
        conn = connector.connect();
        loadData();
 
    }
    
    @FXML
    private void refreshBtn(ActionEvent event){
        refreshTable();
    }
    
    private void refreshTable(){
         conn = connector.connect();
         
          try {
            productList.clear();
            
            query = "SELECT * FROM products";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            InputStream input;
            Image image;
            ImageView finalImage;
            
            while(resultSet.next()){
                
                
                input = resultSet.getBinaryStream("image");
                image = new Image(input);
                finalImage = new ImageView(image);
                
                
                productList.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getDouble("price"),
                resultSet.getInt("quantity"), 
                finalImage,
                resultSet.getInt("category_id")));
            }
            
            product_table.setItems(productList);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     private void loadData(){
                        
        refreshTable();
        
        product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        product_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        product_categoryid.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        
         Callback<TableColumn<Product, String>, TableCell<Product, String>> cellCreator = (TableColumn<Product, String> param) -> {
            final TableCell<Product, String> cell = new TableCell<Product, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        final Button addToCartBtn = new Button(); 
                        
                        Image cartImage = new Image("res/addtocart.png");
                   
                        
                        ImageView cartImageView = new  ImageView(cartImage);
                        
                        cartImageView.setFitHeight(15);
                        cartImageView.setFitWidth(15);
                        cartImageView.setPreserveRatio(true);
                       
                        
                        addToCartBtn.setGraphic(cartImageView);
                        
                        addToCartBtn.setOnAction( event -> {
                            
                            try {
                                product = product_table.getSelectionModel().getSelectedItem();
                                query = "INSERT INTO cart FROM products WHERE id  = " + product.getId();
                                conn = connector.connect();
                                prep = conn.prepareStatement(query);
                                prep.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                    ex.printStackTrace();
                            }

                        });
                     

                        HBox managebtn = new HBox(addToCartBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(addToCartBtn, new Insets(2, 2, 0, 3));
                      

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         product_addToCart.setCellFactory(cellCreator);
         product_table.setItems(productList);
         
         
    }
     
    
     
     
    @FXML
    private void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage loginStage = (Stage) rootpane.getScene().getWindow();
        loginStage.close();
    }


    @FXML
    private void viewCartButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewCart.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage viewCart = (Stage) rootpane.getScene().getWindow();
        viewCart.close();

    }

    @FXML
    private void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage customerView = (Stage) rootpane.getScene().getWindow();
        customerView.close();
    }

}
