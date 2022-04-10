package admin;


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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Logan Jolicoeur
 */
public class AdminController implements Initializable {
    
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    protected TableView<UserModel> user_table;
    
    @FXML
    private TableColumn<UserModel, String> user_id;
    
    @FXML
    private TableColumn<UserModel, String> user_email;
        
    @FXML
    private TableColumn<UserModel, String> user_name;
    
    @FXML
    private TableColumn<UserModel, String> user_role;
    
    @FXML
    private TableColumn<UserModel, String> user_password;
    
    @FXML
    private TableColumn<UserModel, String> user_active;
    
    @FXML
    protected TableView<UserModel> user_table1;
    
    @FXML
    private TableColumn<UserModel, String> user_id1;
    
    @FXML
    private TableColumn<UserModel, String> user_email1;
        
    @FXML
    private TableColumn<UserModel, String> user_name1;
    
    @FXML
    private TableColumn<UserModel, String> user_role1;
    
    @FXML
    private TableColumn<UserModel, String> user_password1;
        
    private ObservableList <UserModel> userList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {               
        user_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        user_active.setCellValueFactory(new PropertyValueFactory<>("active"));
        
        user_id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_email1.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_name1.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_password1.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_role1.setCellValueFactory(new PropertyValueFactory<>("role"));
        
        
        try{
            //Connect to the database
            conn = connector.connect();
            
            ResultSet rs = conn.createStatement().executeQuery("SELECT id, email, name, password, role, alive FROM users;");
                        
            while(rs.next()){
                userList.add(new UserModel(rs.getString("id"), rs.getString("email"),
                rs.getString("name"), rs.getString("password"), rs.getInt("role"),
                rs.getInt("alive")));
                
                
            }
            user_table.setItems(userList);
                        
            user_table1.setItems(userList);
            
            
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    
    
    //Require the connection and get the connection to the database
 
    @FXML 
    private void signOut(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage adminStage = (Stage) rootpane.getScene().getWindow();
            adminStage.close();
    }
    
}
