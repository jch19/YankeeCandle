package vendor;

import admin.*;
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
public class EditProductController{
    
    
     //For when to either update or edit existing data;
    private boolean update;
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private String query = "";
    Product product = null;
    private int productID; 
    
    
    @FXML
    private TextField name;
    
    @FXML
    private TextArea description;
     
    @FXML
    private TextField price;
    
    @FXML
    private TextField quantity;
        
    
    @FXML
    private void save(ActionEvent event){
        
        conn = connector.connect();
        
        String email = name.getText().trim();
        String name = description.getText().trim();
        String password = price.getText().trim(); 
        String question = quantity.getText().trim();
        
        
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || question.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error: Fill all items.");
            alert.showAndWait();
        }else{
            setQuery();
            insertData();
            reset();
        }
        
    }
    
    @FXML
    private void resetButton(ActionEvent event){
        reset();
    }
    
    
    
    private void reset(){
        name.setText(null);
        description.setText(null);
        price.setText(null);
        quantity.setText(null);
        
    }
    
    
    private void setQuery(){
        if(!update){
            query = "INSERT INTO products (name, description, price, quantity, image) VALUES (?, ?, ?, ?, null) ";
        }else{
            query = "UPDATE products SET name=?, description=?, price=?, quantity=?, image=null WHERE id= " + productID;
        }
    }
    
    private void insertData() {

        try {

            prep = conn.prepareStatement(query);
            prep.setString(1, name.getText());
            prep.setString(2, description.getText());
            prep.setString(3, price.getText());
            prep.setString(4, quantity.getText());
            
          
         
          
            prep.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    
    public void setText(int id, String name, String description, double price, int quantity) {

        this.productID = id;
        this.name.setText(name);
        this.description.setText(description);
        this.price.setText(Double.toString(price));
        this.quantity.setText(Integer.toString(quantity));      
    }
    

    public void setUpdate(boolean update){
        this.update = update;
    }
    
}
