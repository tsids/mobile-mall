package com.example.b07project;

import java.io.Serializable;


public class Order implements Serializable {

    private Object orders = new Object();
    private String createdAt = "";

    public Order() {
    }

    public Order(Object orders, String createdAt) {
        this.orders = orders;
        this.createdAt = createdAt;
    }

    public Object getOrders() {
        return orders;
    }

    public void setOrders(Object orders) {
        this.orders = orders;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
