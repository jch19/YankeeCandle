package yankeecandle;

import java.io.IOException;
import javafx.event.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.scene.control.Menu;

/**
 * 
 *
 * @author Dominique Simmons
 */
public class SalesViewController{

    //Creating new mmenu with menu items
    Menu logout = new Menu("logout");
    
    
    @FXML 
    private BorderPane rootpane;
     
    @FXML 
    //Sign out method
    private void signOut(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            final Stage sales = (Stage) rootpane.getScene().getWindow();
            sales.close();
    }
    
    
    
}
