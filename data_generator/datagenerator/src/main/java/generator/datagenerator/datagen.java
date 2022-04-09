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

        final int userAmount = 50;
        
        try{
            Connection conn = handle.connect();
            
            sql = "DELETE FROM users";
            
            PreparedStatement delUser = conn.prepareStatement(sql);
  
            delUser.execute();  
            
            sql = "INSERT INTO users (id, email, name, password, role, alive) VALUES (?, ?, ?, ?, ?, 1); ";
            
            PreparedStatement userData = conn.prepareStatement(sql);
            
            for(int i = 1; i < userAmount + 1; i++)
            {
                userData.setString(1, Integer.toString(i));
                userData.setString(2, faker.internet().emailAddress());
                userData.setString(3, faker.name().firstName() + " " + faker.name().lastName());
                userData.setString(4, faker.internet().password());
                userData.setString(5, Integer.toString(faker.number().numberBetween(1, 4)));
                userData.execute();
            }
           
            System.out.println("Data entered");
                        
        }catch(SQLException e){
            System.out.println(e.toString());
        }
         
                
        
    }
    
}
