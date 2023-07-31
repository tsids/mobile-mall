package com.example.b07project.Cart;

// import java.io.Serializable; Don't need to implement this as Product already does

import com.example.b07project.Product;

public class CartProduct extends Product {

    private boolean ordered;
    private boolean verified;
    private boolean pickedUp;



    private int quantity;

    public CartProduct() {}

    public CartProduct(String imageURL, String title, float price, String description, int storeID, int productID, int quantity, boolean ordered, boolean verified, boolean pickedUp) {
        super(imageURL, title, price, description, storeID, productID);
        this.ordered = ordered;
        this.verified = verified;
        this.pickedUp = pickedUp;
        this.quantity = quantity;
    }

    public boolean getOrdered() { return this.ordered; }

    public boolean getVerified() { return this.verified; }

    public boolean getPickedUp() { return this.pickedUp; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrdered(boolean ordered) { this.ordered = ordered; }

    public void setVerified(boolean verified) { this.verified = verified; }

    public void setPickedUp(boolean pickedUp) { this.pickedUp = pickedUp; }

}