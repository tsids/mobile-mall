package com.example.b07project;

public class Order {
    int productID,storeID,userID,amount;

    public Order() {
    }

    public Order(int productID, int storeID, int userID, int amount) {
        this.productID = productID;
        this.storeID = storeID;
        this.userID = userID;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }
}
