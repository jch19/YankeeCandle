package generator.datagenerator;

import com.github.javafaker.Faker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
        
      
/**
 *
 * @author Logan Jolicoeur
 * Purpose: Generate data for a database
 */

public class datagen {
    
    public static void main(String[] args){
        
        System.out.println("Generating data");
        
        DBConnector handle = new DBConnector();
        
        Faker faker = new Faker(); 
        
        String sql = "";

        final int userAmount = 150;
        
        try{
            Connection conn = handle.connect();
            
            sql = "DELETE FROM users";
            
            PreparedStatement delUser = conn.prepareStatement(sql);
  
            delUser.execute();  
            
            sql = "INSERT INTO users (id, email, name, password, role, question, alive) VALUES (?, ?, ?, ?, ?, ?, 1); ";
            
            PreparedStatement userData = conn.prepareStatement(sql);
            
            for(int i = 1; i < userAmount + 1; i++)
            {
                userData.setString(1, Integer.toString(i));
                userData.setString(2, faker.internet().emailAddress());
                userData.setString(3, faker.name().firstName() + " " + faker.name().lastName());
                userData.setString(4, faker.internet().password());
                userData.setString(5, Integer.toString(faker.number().numberBetween(1, 4)));
                userData.setString(6, faker.book().title().toLowerCase());
                userData.execute();
            }
            
            sql ="INSERT INTO users VALUES ('"+ userAmount+1 +"', 'admin', 'admin', '1234', 4, 'harry potter' ,1);";
            
            PreparedStatement insertAdmin = conn.prepareStatement(sql);

            insertAdmin.execute();
           
            System.out.println("Data entered");
            
            conn.close();
                        
        }catch(SQLException e){
            System.out.println(e.toString());
        }
         
                
        
    }
    
}
