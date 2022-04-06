package admin;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import util.ConnectionHandler;

/**
 *
 * @author Logan Jolicoeur
 */
public class AdminController {
    
    @FXML
    private AnchorPane rootpane;
    
    //Create an object to acess the handler file;
    private ConnectionHandler connectionHandler = new ConnectionHandler();
    
    //Require the connection and get the connection to the database
    private Connection connection = connectionHandler.getConnection();
    
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
    
    
    @FXML
    private void test(ActionEvent event) throws IOException{
        
        try{
            String sql = "INSERT INTO user VALUES (1, 'test', 'test');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            System.out.println("Inserting"); 
        }catch(SQLException e){
            e.getStackTrace();
        }
    }
    
}
