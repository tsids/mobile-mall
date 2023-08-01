package com.example.b07project.Login;

import com.example.b07project.Product;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private List<Product> cart;
    private String password;
    private List<Product> pastOrders;
    private String username;

    // Constructors
    public User() {
    }

    public User(List<Product> cart, String password, List<Product> pastOrders, String username) {
        this.cart = cart;
        this.password = password;
        this.pastOrders = pastOrders;
        this.username = username;
    }

    // Getters and Setters
    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getPastOrders() {
        return pastOrders;
    }

    public void setPastOrders(List<Product> pastOrders) {
        this.pastOrders = pastOrders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
