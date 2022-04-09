package admin;

/**
 *
 * @author Logan Jolicoeur
 * Purpose: Load user data from the sql DB
 */
public class UserModel {
    
    private String id; 
    private String email;
    private String name;
    private String password;
    private int role;
    private int active;

    public UserModel(String id, String email, String name, String password, int role, int active)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.active = active;
    }
    
     public UserModel(String id, String email, String name, String password, int role)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
