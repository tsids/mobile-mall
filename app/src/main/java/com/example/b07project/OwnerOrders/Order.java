package com.example.b07project.OwnerOrders;

public class Order {
    int productID,storeID,userID, quantity;

    public Order() {
    }

    public Order(int productID, int storeID, int userID, int quantity) {
        this.productID = productID;
        this.storeID = storeID;
        this.userID = userID;
        this.quantity = quantity;
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
