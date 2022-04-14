package sales;

import admin.User;
import customer.OrdersViewController;
import customer.CustomerViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Menu;
import util.DBConnector;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import util.DBConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import util.DBConnector;


/**
 * 
 *
 * @author Logan Jolicoeur
 */
public class salesviewController implements Initializable {

    //Creating new menu with menu items
    Menu logout = new Menu("logout");
    
    @FXML 
    private BorderPane rootpane;
    
    DBConnector connector = new DBConnector();
    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet = null;
    
    @FXML
    private TableView<Sale> sale_table;
    
    @FXML
    private TableColumn<Sale, Integer> order_number;
    
    @FXML
    private TableColumn<Sale, String> name;
    
    @FXML
    private TableColumn<Sale, String> email;
        
    @FXML
    private TableColumn<Sale, String> product;
    
    @FXML
    private TableColumn<Sale, String> status;
    
    @FXML
    private TableColumn<Sale, String> edit;    
    
    Sale sale = null;
    private ObservableList <Sale> saleList = FXCollections.observableArrayList();
    private String query = "";

    
    @FXML
    private void refreshBtn(ActionEvent event){
        refreshTable();
    }
    
     private void refreshTable(){

          try {
            saleList.clear();
            
            query = "SELECT order_details.id AS id, users.name AS name, email, products.name AS product_name, status FROM order_details \n" +
                     "JOIN users ON users.id = order_details.user_id JOIN products ON products.id = product_id";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            while(resultSet.next()){
                
                saleList.add(new Sale(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("email"),resultSet.getString("product_name"), resultSet.getString("status")));
            }
            
            sale_table.setItems(saleList);
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    //Populate table with data from database
    @FXML
    private void loadData() {
        
        refreshTable();
        
        order_number.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        product.setCellValueFactory(new PropertyValueFactory<>("product"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
               
         Callback<TableColumn<Sale, String>, TableCell<Sale, String>> cellCreator = (TableColumn<Sale, String> param) -> {
            final TableCell<Sale, String> cell = new TableCell<Sale, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                         final Button editBtn = new Button();
                        
                         
                        Image editImage = new Image("res/edit.png");
                        
                         
                        ImageView editView = new ImageView(editImage);
                        
                       
                        
                        editView.setFitHeight(15);
                        editView.setFitWidth(15);
                        editView.setPreserveRatio(true);
                        
                        
                        
                        editBtn.setGraphic(editView);
                        
                        
                        editBtn.setOnAction(event -> {
                            
                            sale = sale_table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditSale.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            
                            
                            EditSaleController edit = loader.getController();
                            edit.setText(sale.getId(), sale.getStatus());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setResizable(false);
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                             
                        });

                        HBox managebtn = new HBox(editBtn);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(editBtn, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         edit.setCellFactory(cellCreator);
        
    }

   @Override
    public void initialize(URL url, ResourceBundle rb)
    {         
        conn = connector.connect();
        loadData(); 

    }

    
    
        //Sign out method
    @FXML 
    private void signOut(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage salesStage = (Stage) rootpane.getScene().getWindow();
            salesStage.close();
    }
    
}
