package sales;
 
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import util.DBConnector;

/**
 * FXML Controller class
 *
 * @author Logan Jolicoeur
 */
public class EditSaleController implements Initializable{
    
    
     //For when to either update or edit existing data;
    private boolean update;
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private String query = "";
    private int saleID; 
    
    private String[] status = {"Completed", "Processing", "Shipped"};
    
    @FXML
    private ComboBox status_select;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        status_select.setItems(FXCollections.observableArrayList(status));
    }
        
    
    @FXML
    private void save(ActionEvent event){
        
        conn = connector.connect();
        
        setQuery();
        insertData();
        reset();
        
        
    }
    
    
    private void reset(){
         status_select.setPromptText("Select Status");
        
    }
    
    
    private void setQuery(){
         query = "UPDATE order_details SET status=? WHERE id =" + saleID;
         
    }
    
    private void insertData() {

        
         try {

            prep = conn.prepareStatement(query);
            prep.setString(1, status_select.getValue().toString());
            
          
            prep.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    
    public void setText(int id, String status) {

        this.saleID = id;
        switch(status){
            case "Completed":
                status_select.getSelectionModel().select(0);
                break;
            case "Processing":
                status_select.getSelectionModel().select(1);
                break;
            case "Shipped":
                status_select.getSelectionModel().select(2);
                break;
        }
        
    }
    

    
}
