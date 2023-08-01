package com.example.b07project;

import java.io.Serializable;
import java.util.ArrayList;


public class Cart implements Serializable {

    ArrayList<CartProduct> cart = new ArrayList<>();

    public Cart() {}

    public void addObject(CartProduct item) { cart.add(item); }

    public void removeObject(CartProduct item) { cart.remove(item); }

    public ArrayList<CartProduct> getCart() { return cart; }

}
