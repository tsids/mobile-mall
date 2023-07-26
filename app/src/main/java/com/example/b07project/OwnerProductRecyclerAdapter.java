package com.example.b07project;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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
    ProductsFragment caller;

    public OwnerProductRecyclerAdapter(Context context, ArrayList<Product> products,ProductsFragment caller) {
        this.context = context;
        this.products = products;
        this.caller = caller;
    }

    @NonNull
    @Override
    public OwnerProductRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_layout,parent,false);

        return new CustomViewHolder(view,caller);
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
        public CustomViewHolder(@NonNull View itemView,ProductsFragment caller) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            price = itemView.findViewById(R.id.prod_price);
            desciption = itemView.findViewById(R.id.prod_description);
            img = itemView.findViewById(R.id.prod_img);
            itemView.findViewById(R.id.prod_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    caller.loadEdit(getAdapterPosition());
                }
            });

            itemView.findViewById(R.id.prod_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO Delete product function
                }
            });
        }
    }
}
