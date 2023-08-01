package com.example.b07project;

// import java.io.Serializable; Don't need to implement this as Product already does

public class CartProduct extends Product {

    private boolean ordered;
    private boolean verified;
    private boolean pickedUp;

    public CartProduct() {}

    public CartProduct(String imageURL, String title, float price, String description, int storeID, int productID, boolean ordered, boolean verified, boolean pickedUp) {
        super(imageURL, title, price, description, storeID, productID);
        this.ordered = ordered;
        this.verified = verified;
        this.pickedUp = pickedUp;
    }

    public boolean getOrdered() { return this.ordered; }

    public boolean getVerified() { return this.verified; }

    public boolean getPickedUp() { return this.pickedUp; }

    public void setOrdered(boolean ordered) { this.ordered = ordered; }

    public void setVerified(boolean verified) { this.verified = verified; }

    public void setPickedUp(boolean pickedUp) { this.pickedUp = pickedUp; }

}
