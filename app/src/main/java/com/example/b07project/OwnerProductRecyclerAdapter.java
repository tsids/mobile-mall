package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OwnerProductRecyclerAdapter extends RecyclerView.Adapter<OwnerProductRecyclerAdapter.CustomViewHolder>{
    Context context;
    ArrayList<Product> products;

    public OwnerProductRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public OwnerProductRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_layout,parent,false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerProductRecyclerAdapter.CustomViewHolder holder, int position) {
        holder.name.setText(products.get(position).getTitle());
        holder.desciption.setText(products.get(position).getDescription());
        String price = "$"+products.get(position).getPrice();
        holder.price.setText(price);
        System.out.println(products.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,desciption;
        ImageView img;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            price = itemView.findViewById(R.id.prod_price);
            desciption = itemView.findViewById(R.id.prod_description);
            img = itemView.findViewById(R.id.prod_img);
        }
    }
}
