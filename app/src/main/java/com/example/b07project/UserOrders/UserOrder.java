package com.example.b07project.UserOrders;

import com.example.b07project.OwnerOrders.Order;

import java.util.ArrayList;

public class UserOrder {
    ArrayList<Order> orders;

    public UserOrder() {
        orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
