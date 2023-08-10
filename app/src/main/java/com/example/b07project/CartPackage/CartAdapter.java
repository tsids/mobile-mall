package com.example.b07project.CartPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.CartPackage.CartFragment;
import com.example.b07project.CartPackage.CartProduct;
import com.example.b07project.R;
import com.example.b07project.RecyclerViewInterface;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<com.example.b07project.CartPackage.CartViewHolder> {

    private final com.example.b07project.CartPackage.CartRecyclerViewInterface recyclerViewInterface;

    Context context;
    List<CartProduct> products;
    CartFragment cartFragment;

    public CartAdapter(Context context, List<CartProduct> products, com.example.b07project.CartPackage.CartRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.products = products;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public com.example.b07project.CartPackage.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new com.example.b07project.CartPackage.CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_view, parent,  false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.b07project.CartPackage.CartViewHolder holder, int position) {
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