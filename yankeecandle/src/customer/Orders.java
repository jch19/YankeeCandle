package customer;

/**
 *
 * @author carol
 */

//THIS IS BASED ON "ORDER_ITEMS" IN THE DATA BASE
public class Orders {
    private int id;
    private String user_name;
    private double total; 
    private int quantity; 
    private String provider; 
    private String status;

    public Orders(int id,String user_name, double total, int quantity, String provider, String status) {
        this.id = id;
        this.user_name = user_name;
        this.quantity = quantity;
        this.total = total;
        this.provider = provider;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
