package com.example.b07project.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.RecyclerViewInterface;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private final CartRecyclerViewInterface recyclerViewInterface;

    Context context;
    List<CartProduct> products;
    CartFragment cartFragment;

    public CartAdapter(Context context, List<CartProduct> products, CartRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.products = products;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_view, parent,  false), recyclerViewInterface);
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