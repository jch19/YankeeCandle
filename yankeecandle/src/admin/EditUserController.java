package admin;

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
public class EditUserController implements Initializable{
    
    
     //For when to either update or edit existing data;
    private boolean update;
    
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private String query = "";
    User user = null;
    private int userID;
    
    String [] roles = {"User", "Vendor", "Salesperson", "Admin"};
    
    
    
    
    @FXML
    private TextField user_name;
    
    @FXML
    private TextField user_email;
     
    @FXML
    private TextField user_password;
    
    @FXML
    private ComboBox role_select;
    
    @FXML
    private CheckBox user_active;
    
    @FXML
    private Label dialog_title; 
    
    @FXML
    private TextField user_question;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        role_select.setItems(FXCollections.observableArrayList(roles));
    }
    
    
    @FXML
    private void save(ActionEvent event){
        
        conn = connector.connect();
        
        String email = user_email.getText().trim();
        String name = user_name.getText().trim();
        String password = user_password.getText().trim(); 
        String question = user_question.getText().trim();
        
        
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
        user_name.setText(null);
        user_email.setText(null);
        user_password.setText(null);
        user_active.setSelected(true);
        role_select.getSelectionModel().selectFirst();
        user_question.setText(null);
    }
    
    
    private void setQuery(){
        if(!update){
            query = "INSERT INTO users (email, name, password, role, question, alive) VALUES (?, ?, ?, ?, ?, ?) ";
        }else{
            query = "UPDATE users SET email=?, name=?, password=?, role=?, question=?, alive=? WHERE id= " + userID;
        }
    }
    
    private void insertData() {

        try {

            prep = conn.prepareStatement(query);
            prep.setString(1, user_email.getText());
            prep.setString(2, user_name.getText());
            prep.setString(3, user_password.getText());
            
             // 1-> User, 2-> Vendor, 3-> Salesperson, 4-> Admin
            if(role_select.getValue().toString().equals("User")){
                prep.setString(4, "1");
            }else if(role_select.getValue().toString().equals("Salesperson"))
            {
                prep.setString(4, "2");
            }else if(role_select.getValue().toString().equals("Vendor"))
            {
                prep.setString(4, "3");
            } else{
                prep.setString(4, "4");
            }
            
            prep.setString(5, user_question.getText().toLowerCase());
            
            
            if(user_active.isSelected()){
                prep.setString(6, "1");
            }else{
                prep.setString(6,"0");
            }
          
            prep.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    
    public void setText(String dialog_title, int id, String email, String name, String password, int role, String question, int active) {

        this.dialog_title.setText(dialog_title);
        this.userID = id;
        this.user_email.setText(email);
        this.user_name.setText(name);
        this.user_password.setText(password);
        this.user_question.setText(question);
        
        if(active == 1){
            this.user_active.setSelected(true);
        }else if(active == 0){
            this.user_active.setSelected(false);
        }
        
        switch(role){
            case 1:
                role_select.getSelectionModel().select(0);
                break;
            case 2:
                role_select.getSelectionModel().select(1);
                break;
            case 3:
                role_select.getSelectionModel().select(2);
                break;
            case 4:
                role_select.getSelectionModel().select(3);
                break;   
        }
        
        
    }
    

    public void setUpdate(boolean update){
        this.update = update;
    }
    
}

