/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vendor;
import admin.EditUserController;
import javafx.scene.control.*;
import java.io.IOException;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import util.DBConnector;



/**
 * FXML Controller class
 *
 * @author kkirk
 */

// Use this to update stock in store
public class VendorViewController implements Initializable{
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet = null;
    private String query = "";
    Product product = null;
    private ObservableList <Product> productList = FXCollections.observableArrayList();
   
    @FXML
    private AnchorPane rootpane;

    @FXML
    protected TableView<Product> product_table;
        
    @FXML
    private TableColumn<Product, String> product_name;
        
    @FXML
    private TableColumn<Product, String> product_description;
    
    @FXML
    private TableColumn<Product, Double> product_price;
    
    @FXML
    private TableColumn<Product, Integer> product_quantity;
        
    @FXML
    private TableColumn<Product, String> product_edit;
    
    private InputStream input;
    private Image image;
    private ImageView finalImage;

     @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        conn = connector.connect();
        loadData();
    }
    
    
     @FXML
    private void refreshBtn(ActionEvent event){
        refreshTable();
    }
    
    private void refreshTable(){

          try {
            productList.clear();
            
            query = "SELECT * FROM products";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            while(resultSet.next()){
                
                productList.add(new Product(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("description"), resultSet.getDouble("price"),
                resultSet.getInt("quantity")));
            }
            
            product_table.setItems(productList);
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        
     private void loadData(){
        refreshTable();
        
        product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
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

                        final Button removeBtn = new Button(); 
                        final Button editBtn = new Button();
                        
                        Image rmImage = new Image("res/remove.png");
                        Image editImage = new Image("res/edit.png");
                        
                        ImageView rmView = new  ImageView(rmImage);
                        ImageView editView = new ImageView(editImage);
                        
                        rmView.setFitHeight(15);
                        rmView.setFitWidth(15);
                        rmView.setPreserveRatio(true);
                        
                        
                        editView.setFitHeight(15);
                        editView.setFitWidth(15);
                        editView.setPreserveRatio(true);
                        
                        
                        removeBtn.setGraphic(rmView);
                        editBtn.setGraphic(editView);
                        
                        
                        removeBtn.setOnAction( event -> {
                            
                            try {
                                product = product_table.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM products WHERE id  = " + product.getId();
                                conn = connector.connect();
                                prep = conn.prepareStatement(query);
                                prep.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                    ex.printStackTrace();
                            }

                        });
                        editBtn.setOnAction(event -> {
                            
                            product = product_table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditProduct.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            
                            
                            EditProductController edit = loader.getController();
                            edit.setUpdate(true);
                            edit.setText(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setResizable(false);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                             
                        });

                        HBox managebtn = new HBox(editBtn, removeBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(editBtn, new Insets(2, 2, 0, 3));
                        HBox.setMargin(removeBtn, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         product_edit.setCellFactory(cellCreator);
         product_table.setItems(productList);
 
    }
     
    @FXML
    private void addProduct(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("EditProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }
    }
     
    @FXML
    private void exitProgram(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        try{
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            final Stage loginStage = (Stage) rootpane.getScene().getWindow();
            loginStage.close();
        }
    }
           
         
    }
    
    
    

   



    

