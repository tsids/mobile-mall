package com.example.b07project.OwnerOrders;

import android.widget.ArrayAdapter;

import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.Product;

import java.util.ArrayList;

public class Order {
    int productID,storeID, quantity;
    String imageURL, title, description;
    float price;
    boolean verified,pickedUp;
    ArrayList products;
    String now;

    public Order() {
    }

    public Order(int productID, int storeID, int userID, int quantity) {
        this.productID = productID;
        this.storeID = storeID;
        this.quantity = quantity;
    }

    public Order(ArrayList<CartProduct> products, String now) {
        this.products = products;
        this.now = now;
    }

    public ArrayList getProducts() {
        return products;
    }

    public void setProducts(ArrayList products) {
        this.products = products;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
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

    public int getQuantity() {
        return quantity;
    }
}
