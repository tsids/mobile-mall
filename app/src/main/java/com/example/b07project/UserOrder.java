package com.example.b07project;

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
