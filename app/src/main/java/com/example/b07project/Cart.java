package com.example.b07project;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Cart implements Serializable {

    ArrayList<CartProduct> cart = new ArrayList<>();

    public Cart() {}

    public Cart(ArrayList<CartProduct> cart) {
        for (CartProduct item: cart) {
            addProduct(item);
        }
    }

    public void addProduct(CartProduct product) { cart.add(product); }

    public void removeProduct(CartProduct product) { cart.remove(product); }

    public ArrayList<CartProduct> getCart() { return cart; }

}
