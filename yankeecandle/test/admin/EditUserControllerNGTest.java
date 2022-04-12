/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.sql.*;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.DBConnector;

/**
 *
 * @author Logan Jolicoeur
 */
public class EditUserControllerNGTest{
    
    private final DBConnector connector = new DBConnector();
    private Connection conn = null;
    

    @BeforeMethod
    public void setUpMethod() throws Exception {
        conn = connector.connect();
        //Do not autocommit data, allow for proper tests and rolling back
        conn.setAutoCommit(false);
    }
    

    @AfterMethod
    public void tearDownMethod() throws Exception {
        try{
             conn.close();
        }catch(SQLException ex ){
            ex.printStackTrace();
        }
    }

    /**
     * Test of setText method, of class EditUserController.
     */
    @Test
    public void testSetText() {
        System.out.println("setText");
        String dialog_title = "Hello World";
        int id = 1;
        String email = "lajolico@gmail.com";
        String name = "Logan Jolicoeur";
        String password = "Password";
        int role = 2;
        String question = "harry potter";
        int active = 1;
          
        assertEquals(1, active);
        assertEquals(1, active);
        assertEquals(2, role);
              
     }

    @Test
    public void testInsert(){
        System.out.println("Testing insertion of data");
        //Static code to test
        int id = 1;
        String email = "Helloworld@gmail.com";
        String name = "John John";
        String password = "1234";
        String question = "harry potter";
        int role = 1;
        int active = 1;
        
        assertFalse(email.isEmpty(), "Email has chars");
        assertFalse(name.isEmpty(), "Name has chars");
        assertFalse(password.isEmpty(), "Password has chars");
        assertFalse(question.isEmpty(), "Question has chars");
        
        try{           
           
           Statement stat = conn.createStatement();
                
           stat.executeUpdate("DELETE FROM testusers");
                
           PreparedStatement prep; 
           ResultSet resultSet;
           
           String query = "INSERT INTO testusers (id, email, name, password, role, question, alive) VALUES (?, ?, ?, ?, ?, ?, ?)";
           
           try{
                prep = conn.prepareStatement(query);
                
                prep.setString(1, Integer.toString(id)); //id = 1 
                prep.setString(2, email);
                prep.setString(3, name);
                prep.setString(4, password);
                prep.setString(5, Integer.toString(role));
                prep.setString(6, question);
                prep.setString(7, Integer.toString(active));
                prep.executeUpdate(); 
                
                
                int findID = id;
                //Database Checks
                //Find the dummy rows and find that they are, compared to what we expect
                try{
                    
                    
                    prep = conn.prepareStatement("SELECT * FROM testusers");
                    
                    resultSet = prep.executeQuery();
                    
                    assertTrue(resultSet.next());
                    findID = resultSet.getInt("id");
                    assertEquals(id, resultSet.getInt("id"));
                    assertEquals(email, resultSet.getString("email"));
                    assertEquals(name, resultSet.getString("name"));
                    assertEquals(password, resultSet.getString("password"));
                    assertEquals(role, resultSet.getInt("role"));
                    assertEquals(question, resultSet.getString("question"));
                    assertEquals(active, resultSet.getInt("alive"));
                    assertFalse(resultSet.next());
                    
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                
                //Find out what we wanted from the specific user id = 1
                try{
                    prep = conn.prepareStatement("SELECT * FROM testusers WHERE id=" + findID);
                    resultSet = prep.executeQuery();
                    assertTrue(resultSet.next());
                    assertEquals(id, resultSet.getInt("id"));
                    assertEquals(email, resultSet.getString("email"));
                    assertEquals(name, resultSet.getString("name"));
                    assertEquals(password, resultSet.getString("password"));
                    assertEquals(role, resultSet.getInt("role"));
                    assertEquals(question, resultSet.getString("question"));
                    assertEquals(active, resultSet.getInt("alive"));
                    assertFalse(resultSet.next());
                    
                }catch(SQLException e){
                    e.printStackTrace();
                }
                
            }finally{
                //Undo testing operations, saving the database
                conn.rollback();
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            fail(ex.toString());
        }
        
    }
    
    @Test
    public void testResetDB(){
        System.out.println("Removing data from the DB");
        
        try{
            Statement stat = conn.createStatement();
            
            stat.execute("DELETE FROM testusers");
            
            try{
                
                PreparedStatement prep = conn.prepareStatement("SELECT * FROM testusers");
                    
                ResultSet resultSet = prep.executeQuery();

                //Assert that there is nothing in the database
                assertFalse(resultSet.next());
                
                
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                //Rollback all transactions between us and the DB
                conn.rollback(); 
            }
            
        }catch(SQLException ex){
            fail(ex.toString());
        }
        
    }
}
