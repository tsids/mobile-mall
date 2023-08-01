package com.example.b07project;

import com.example.b07project.Cart.Cart;
import com.example.b07project.Cart.CartProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Order implements Serializable {

    private ArrayList<CartProduct> orders = new ArrayList<>();
    private String createdAt = "";

    public Order() {
    }

    public Order(ArrayList<CartProduct> orders, String createdAt) {
        this.orders = orders;
        this.createdAt = createdAt;
    }

    public void addProduct(CartProduct product) { orders.add(product); }

    public void removeProduct(CartProduct product) { orders.remove(product); }

    public ArrayList<CartProduct> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<CartProduct> orders) {
        this.orders = orders;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
