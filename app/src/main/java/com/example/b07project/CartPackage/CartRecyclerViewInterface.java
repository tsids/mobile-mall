package com.example.b07project.CartPackage;

public interface CartRecyclerViewInterface {
    void onItemClick(int position);

    void adjustQuantity(int pos, int i, boolean b);

    void removeFromCart(int pos);
}
