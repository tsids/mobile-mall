package com.example.b07project.UserOrders;

import com.example.b07project.OwnerOrders.Order;

import java.util.ArrayList;

public class UserOrder {
    public void setUserID(String userID) {
        this.userID = userID;
    }

    ArrayList<Order> orders;

    public String getUserID() {
        return userID;
    }

    String userID="";

    public UserOrder() {
        orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
