package util;

import java.util.Dictionary;
import java.util.Hashtable;



/**
 *
 * @author Logan Jolicoeur
 */
public class Roles {
    
    public Dictionary<Integer, String> roles = new Hashtable<Integer, String>();
    
    public Roles(){
    
      roles.put(1, "User");
      roles.put(2, "Salesperson");
      roles.put(3, "Vendor");
      roles.put(4, "Admin");
   
    }      
    
    
    
    
}
