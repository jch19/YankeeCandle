

package yankeecandle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author kkirk
 */
public class UpdateStockController  {
    
    @FXML
    private TextArea t1;
    
     @FXML
    private TextArea t2;
    
     @FXML
    private TextArea t3;
     
      @FXML
    private TextArea t4;
   
   @FXML
    private AnchorPane pane;
    
    
    @FXML
    void signout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

       ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    @FXML
    void home(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("VendorView.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("YankeeCandle");
        stage.setScene(scene);
        stage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
     @FXML
    void save(ActionEvent event) {
        //will close program
    }
}
