/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customer;

/**
 *
 * @author carol
 */

//THIS IS BASED ON "ORDER_ITEMS" IN THE DATA BASE
public class Orders {
    private int id;
    private int orderid; 
    private int prodid;
    private int quantity; 

    public Orders(int id, int orderid, int prodid, int quantity) {
        this.id = id;
        this.orderid = orderid;
        this.prodid = prodid;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
