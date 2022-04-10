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

/**
 *
 * @author Logan Jolicoeur
 */
public class AdminController implements Initializable {
    
    DBConnector connector = new DBConnector();

    private static Connection conn;
    private static Statement stat;
    private PreparedStatement prep;
    private ResultSet resultSet = null;
    
    User user = null;
    private ObservableList <User> userList = FXCollections.observableArrayList();
    private String query = "";
    
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    protected TableView<User> user_table;
    
    @FXML
    private TableColumn<User, String> user_id;
    
    @FXML
    private TableColumn<User, String> user_email;
        
    @FXML
    private TableColumn<User, String> user_name;
    
    @FXML
    private TableColumn<User, String> user_role;
    
    @FXML
    private TableColumn<User, String> user_password;
    
    @FXML
    private TableColumn<User, String> user_active;
    
    @FXML
    private TableColumn<User, String> user_question;
    
    @FXML
    private TableColumn<User, String> user_edit;
    
    @FXML
    private BarChart user_chart;
    
    @FXML
    private Chart user_pie_chart;
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {         
        conn = connector.connect();
        loadData(); 
        loadStats();

    }
    
    @FXML
    private void refreshBtn(ActionEvent event) {
        refreshTable();
    }
    
    private void refreshTable(){
        
          try {
            userList.clear();
            
            query = "SELECT * FROM users";
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            while(resultSet.next()){
                userList.add(new User(resultSet.getInt("id"), resultSet.getString("email"),
                resultSet.getString("name"), resultSet.getString("password"),
                resultSet.getInt("role"), 
                resultSet.getString("question"),
                resultSet.getInt("alive")));
                user_table.setItems(userList);
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
 
    private void loadData(){
                        
        refreshTable();
        
        user_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        user_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        user_question.setCellValueFactory(new PropertyValueFactory<>("question"));
        user_active.setCellValueFactory(new PropertyValueFactory<>("active"));
        
         Callback<TableColumn<User, String>, TableCell<User, String>> cellCreator = (TableColumn<User, String> param) -> {
            final TableCell<User, String> cell = new TableCell<User, String>() {
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
                                user = user_table.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM users WHERE id  = " + user.getId();
                                
                                conn = connector.connect();
                                prep = conn.prepareStatement(query);
                                prep.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                    ex.printStackTrace();
                            }

                        });
                        editBtn.setOnAction(event -> {
                            
                            user = user_table.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("AddUser.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
 
                            }
                            
                            EditUserController addUser = loader.getController();
                            addUser.setUpdate(true);
                            addUser.setText("Edit User", user.getId(), user.getEmail(), user.getName(), user.getPassword(), user.getRole(), user.getQuestion(), user.getActive());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
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
         user_edit.setCellFactory(cellCreator);
         user_table.setItems(userList);
         
         
    }
    
    @FXML
    private void addUser(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            
        }
    }
     
    @FXML 
    private void signOut(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("/main/Login.fxml"));
        
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            stage.setTitle("YankeeCandle");
            stage.setScene(scene);
            stage.show();
            
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            
            final Stage adminStage = (Stage) rootpane.getScene().getWindow();
            adminStage.close();
    }

    @FXML
    private void loadStats() {
        
      //Prepare XYChart.Series objects by setting data       
      XYChart.Series<String, Integer> userSeries = new XYChart.Series<>();
     
      
      XYChart.Series<String, Integer> vendorSeries = new XYChart.Series<>();
       
      
      XYChart.Series<String, Integer> salesSeries = new XYChart.Series<>();
      
      XYChart.Series<String, Integer> adminSeries = new XYChart.Series<>();
      
      XYChart.Series<String, Integer> totalSeries = new XYChart.Series<>();
        
      try{
            query = "SELECT COUNT(CASE WHEN role = 1 THEN 1 ELSE NULL END) AS User, \n" +
                    "COUNT(CASE WHEN role = 2 THEN 1 ELSE NULL END) AS Vendor,\n" +
                    "COUNT(CASE WHEN role = 3 THEN 1 ELSE NULL END) AS Salesperson,\n" +
                    "COUNT(CASE WHEN role = 4 THEN 1 ELSE NULL END) AS Admin,\n" +
                    "COUNT(*) as Total\n" +
                    "FROM users;";
            
            prep = conn.prepareStatement(query);
            resultSet = prep.executeQuery();
            
            int UserAmount = Integer.parseInt(resultSet.getString("User"));
            int VendorAmount = Integer.parseInt(resultSet.getString("Vendor"));
            int SalespersonAmount = Integer.parseInt(resultSet.getString("Salesperson"));
            int AdminAmount = Integer.parseInt(resultSet.getString("Admin"));
            int TotalAmount = Integer.parseInt(resultSet.getString("Total"));
            
            userSeries.getData().add(new XYChart.Data<>("User", UserAmount));
            vendorSeries.getData().add(new XYChart.Data<>("Vendor", VendorAmount));
            salesSeries.getData().add(new XYChart.Data<>("Salesperson", SalespersonAmount));
            adminSeries.getData().add(new XYChart.Data<>("Admin", AdminAmount ));
            totalSeries.getData().add(new XYChart.Data<>("Total", TotalAmount));
            
            userSeries.setName("Users" + " (" + UserAmount + ")");  
            vendorSeries.setName("Vendor" + " (" + VendorAmount + ")"); 
            salesSeries.setName("Salesperson"+ " (" + SalespersonAmount + ")"); 
            adminSeries.setName("Admin" + " (" + AdminAmount + ")");  
            totalSeries.setName("Total" + " (" + TotalAmount + ")");  


            

            
            user_chart.getData().addAll(userSeries, vendorSeries, salesSeries, adminSeries, totalSeries);
            
            resultSet.close();
            
      }catch(SQLException ex){
          ex.printStackTrace();
      }
      
        
      
      

        
    }
    
}
