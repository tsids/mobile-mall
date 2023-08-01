package com.example.b07project.CartPackage;

// import java.io.Serializable; Don't need to implement this as Product already does

import com.example.b07project.Product;

public class CartProduct extends Product {

    private boolean verified;
    private boolean pickedUp;
    private int quantity;
    public CartProduct() {}

    public CartProduct(String imageURL, String title, float price, String description, int storeID, int productID, int quantity, boolean verified, boolean pickedUp) {
        super(imageURL, title, price, description, storeID, productID);
        this.verified = verified;
        this.pickedUp = pickedUp;
        this.quantity = quantity;
    }

    public boolean getVerified() { return this.verified; }

    public boolean getPickedUp() { return this.pickedUp; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setVerified(boolean verified) { this.verified = verified; }

    public void setPickedUp(boolean pickedUp) { this.pickedUp = pickedUp; }

}
