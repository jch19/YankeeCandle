/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import util.DBConnector;



/**
 * FXML Controller class
 *
 * @author kkirk
 */

// Use this to update stock in store
public class UpdateStock implements Initializable{
    
    
     //For when to either update or edit existing data;
    private boolean update;
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet = null;
    private String query = "";
     Product product = null;
    private ObservableList <Product> productList = FXCollections.observableArrayList();
   
    
    
public class VendorViewController {
     @FXML
    private AnchorPane pane;

    @FXML
    private logout;
    
     @FXML
    private Button exit;
    
    @FXML
    private Button ViewInventory;

    @FXML
    private Button update;
    
   @FXML
    protected TableView<Prodcut> product;
    
    @FXML
    private TableColumn<Product, String> inventory;
    
    @FXML
    private TableColumn<Product, String> productID;
        
    @FXML
    private TableColumn<Product, String> quantity;
    
    @FXML
    private TableColumn<Product, String> pn;

    
    @Override
   public void initialize(URL url, ResourceBundle rb)
    {         
        rootpane.setPrefSize(600, 600);
        conn = connector.connect();
        loadData();
    }
    
    private void refreshTable(){
        
          try {
            productList.clear();
            
            query = "SELECT * FROM products";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            while(resultSet.next()){
                productList.add(new Product(resultSet.getInt("product ID"), resultSet.getString("product name"),
                resultSet.getInt("quantity")));
                product_table.setItems(productList);
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
          private void loadData(){
                        
        refreshTable();
        
        product_productID.setCellValueFactory(new PropertyValueFactory<>("product ID"));
        product_productName.setCellValueFactory(new PropertyValueFactory<>("product name"));
        product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        
         Callback<TableColumn<Product, String>, TableCell<Product, String>> cellCreator = (TableColumn<Product, String> param) -> {
            final TableCell<Product, String> cell = new TableCell<Product, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        final Button updateInventoryBtn = new Button(); 
                        
                        Image inventoryImage = new Image("updateInventory.png");
                   
                        
                        ImageView inventoryImageView = new  ImageView(inventoryImage);
                       
                        
                        cartImageView.setFitHeight(15);
                        cartImageView.setFitWidth(15);
                        cartImageView.setPreserveRatio(true);
                       
                        
                        updateInventoryBtn.setGraphic(inventoryImageView);
                        
                        updateInventoryBtn.setOnAction( event -> {
                            
                            try {
                                product = product_table.getSelectionModel().getSelectedItem();
                                query = "INSERT INTO inventory FROM products WHERE id  = " + product.getId();
                                conn = connector.connect();
                                prep = conn.prepareStatement(query);
                                prep.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                    ex.printStackTrace();
                            }

                        });
                     

                        HBox managebtn = new HBox(updateInventoryBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(addToCartBtn, new Insets(2, 2, 0, 3));
                      

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         product_updateInventory.setCellFactory(cellCreator);
         product_table.setItems(productList);
         
         
    }
    
    
    }

    @FXML
    private void refreshBtn(ActionEvent event) {
        refreshTable();
    }
   @FXML
    private void exit(ActionEvent event) throws IOException {
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
    void ViewInventory(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ViewInventory.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        final Stage VendorViewStage = (Stage) pane.getScene().getWindow();
        VendorViewStage.close();

    }
    
     @FXML
    void UpdateStock(ActionEvent event){

        
}
    
}
