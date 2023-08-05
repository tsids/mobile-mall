package com.example.b07project.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.Product;
import com.example.b07project.R;
import com.example.b07project.UserProducts.ProductViewHolder;
import com.example.b07project.UserProducts.UserProductsFragment;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {


    Context context;
    List<CartProduct> products;
    CartFragment cartFragment;

    public CartAdapter(Context context, List<CartProduct> products, CartFragment cartFragment) {
        this.context = context;
        this.products = products;
        this.cartFragment = cartFragment;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_view, parent,  false), cartFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.productNameView.setText(products.get(position).getTitle());
        float price = products.get(position).getPrice() * products.get(position).getQuantity();
        holder.priceView.setText("$" + price);
        holder.quantityView.setText("" + products.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}