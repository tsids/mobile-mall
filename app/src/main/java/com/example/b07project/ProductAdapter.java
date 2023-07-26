package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {


    Context context;
    List<Product> products;
    RecyclerViewInterface recyclerViewInterface;

    public ProductAdapter(Context context, List<Product> products, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.products = products;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_view, parent,  false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productNameView.setText(products.get(position).getTitle());
        holder.priceView.setText(String.valueOf(products.get(position).getPrice()) + "$");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}