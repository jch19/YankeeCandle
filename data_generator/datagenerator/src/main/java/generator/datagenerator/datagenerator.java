package generator.datagenerator;

import com.github.javafaker.Faker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 *
 * @author Logan Jolicoeur
 * Purpose: Generate fake data for our project's database
 */
public class datagenerator {
        
    
    private final DBConnector connector = new DBConnector();
    private Statement stat;
    private PreparedStatement prep;
    private Connection conn;
    private String query = "";
    
    private final int userAmount = 150;

    private  Faker faker = new Faker(); 
    
    private String[] status = {"Completed", "Processing", "Shipped"};
    
    private Random rand = new Random();

    
    
    public datagenerator(){
        
        conn = connector.connect();
        
        
        //Uncomment the functions to generate the specific parts of the database
        
        //generateUsers();
        
        
        try{
            generateProducts();
        }catch(FileNotFoundException ex){
            ex.printStackTrace(); 
        }
        
        generateOrders();
        
        try{
         
           prep.close();
           conn.close();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    private void generateUsers(){
        
         try{
            
            query = "DELETE FROM users";
            
            prep = conn.prepareStatement(query);
  
            prep.execute();  
            
            query = "INSERT INTO users (id, email, name, password, role, question, alive) VALUES (?, ?, ?, ?, ?, ?, 1); ";
            
            prep = conn.prepareStatement(query);
            
            for(int i = 1; i < userAmount + 1; i++)
            {
                prep.setString(1, Integer.toString(i));
                prep.setString(2, faker.internet().emailAddress());
                prep.setString(3, faker.name().firstName() + " " + faker.name().lastName());
                prep.setString(4, faker.internet().password());
                prep.setString(5, Integer.toString(faker.number().numberBetween(1, 4)));
                prep.setString(6, faker.book().title().toLowerCase());
                prep.executeUpdate();
            }
            
            query ="INSERT INTO users VALUES ('"+ userAmount+1 +"', 'admin', 'admin', '1234', 4, 'harry potter' ,1);";
            
            prep = conn.prepareStatement(query);

            prep.execute();
           
            System.out.println("Users generated.");
                        
        }catch(SQLException e){
            System.out.println(e.toString());
        }
    }

    private void generateProducts() throws FileNotFoundException {
        //Delete from the table
        try{
            
            query = "DELETE FROM products";

            prep = conn.prepareStatement(query);
            
            prep.execute();
            
            query = "INSERT INTO products (id, name, description, price, quantity, image, category_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?); ";
            
            prep = conn.prepareStatement(query);
            
            
            for(int i = 1; i <= 60; ++i){
                
                File file = new File("C:/Users/warso/Desktop/YankeeCandle/yankeecandle/"
                   + "src/res/dbResources/candle" + faker.number().numberBetween(1, 13)+".jpg");
                FileInputStream image = new FileInputStream(file);
                
                
                prep.setString(1, Integer.toString(i));
                prep.setString(2,  faker.lorem().word());
                prep.setString(3,  faker.lorem().paragraph(2));
                prep.setString(4,  faker.commerce().price(5.99, 29.99));
                prep.setString(5,  Integer.toString(faker.number().numberBetween(1, 5)));
                prep.setBinaryStream(6, (InputStream)image, (int) file.length());
                prep.setString(7, Integer.toString(faker.number().numberBetween(1, 19)));
                prep.executeUpdate();
            }
            
            System.out.println("Products generated.");
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    private void generateOrders(){
        //Delete from the table
        try{
            
            query = "DELETE FROM order_details";

            prep = conn.prepareStatement(query);
            
            prep.execute();
            
            query = "INSERT INTO order_details (id, user_id, total, product_id, quantity, provider, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?); ";
            
            prep = conn.prepareStatement(query);
            
            
            for(int i = 1; i <= 15; ++i){
                
                prep.setString(1, Integer.toString(i));
                prep.setString(2,  Integer.toString(faker.number().numberBetween(1, 30)));
                prep.setString(3,  faker.commerce().price(50, 70));
                prep.setString(4,  Integer.toString(faker.number().numberBetween(2, 15)));
                prep.setString(5,  Integer.toString(faker.number().numberBetween(1, 5)));
                prep.setString(6, faker.company().name());
                prep.setString(7, status[rand.nextInt(status.length)]);
                prep.executeUpdate();
            }
            
            System.out.println("Orders generated.");
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}
