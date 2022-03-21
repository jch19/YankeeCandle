package yankeecandle;

/**
 *
 * @author Logan Jolicoeur
 * Purpose: Prototype purposes, allow for multiple users.
 */
public class User {
    
    private String email;
    private String password;
    private String role;
    
    public User(String email, String password, String role){
       this.email = email;
       this.password = password;
       this.role = role;
    }
 
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }



}
