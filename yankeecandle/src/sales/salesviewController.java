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
import javafx.scene.chart.*;
import util.DBConnector;


/**
 * 
 *
 * @author Dominique Simmons
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
    private TableColumn<?, ?> user_name;
    
    @FXML
    private TableColumn<?, ?> user_ordernum;
    
    @FXML
    private TableColumn<?, ?> user_email;
        
    @FXML
    private TableColumn<?, ?> user_order;
    
    
    //create one for Status Label
    
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
    
    //Populate table with data from database
    @FXML
    private void loadData() {
        
        
    }

   @Override
    public void initialize(URL url, ResourceBundle rb)
    {         
        conn = connector.connect();
        loadData(); 
        loadStats();

    }

    private void loadStats() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
