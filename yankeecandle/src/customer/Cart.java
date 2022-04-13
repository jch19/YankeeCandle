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
public class Cart {
    private Product product; 
    private int quantity; 

    public Cart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void increaseQuantity() {
        this.quantity++;
    }
    
    public void decreaseQuantity() { 
        if(this.quantity > 0) { 
        this.quantity--;
        }
    
    
}
}
