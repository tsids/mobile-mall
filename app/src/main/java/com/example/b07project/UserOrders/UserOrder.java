package com.example.b07project.UserOrders;

import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.OwnerOrders.Order;

import java.util.ArrayList;

public class UserOrder {
    public String getKey() {
        return key;
    }

    private String key;
    public void setUserID(String userID) {
        this.userID = userID;
    }

    ArrayList<CartProduct> orders;

    public String getUserID() {
        return userID;
    }

    String userID="";

    public UserOrder() {
        orders = new ArrayList<>();
    }

    public ArrayList<CartProduct> getOrders() {
        return orders;
    }

    public void setKey(String key) {
        this.key =key;
    }

}
