package com.example.b07project.OwnerOrders;

import com.example.b07project.Cart.CartProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Order implements Serializable {

    private ArrayList<CartProduct> orders = new ArrayList<>();
    private String createdAt = "";

    public Order() {
    }

    public Order(int productID, int storeID, int userID, int quantity) {
        this.productID = productID;
        this.storeID = storeID;
        this.userID = userID;
        this.quantity = quantity;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getProductID() {
        return productID;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getUserID() {
        return userID;
    }

    public int getQuantity() {
        return quantity;
    }
}
