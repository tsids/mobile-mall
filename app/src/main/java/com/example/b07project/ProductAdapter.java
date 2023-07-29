package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {


    Context context;
    List<Product> products;
    UserProductsFragment userProductsFragment;

    public ProductAdapter(Context context, List<Product> products, UserProductsFragment userProductsFragment) {
        this.context = context;
        this.products = products;
        this.userProductsFragment = userProductsFragment;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_view, parent,  false), userProductsFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.productNameView.setText(products.get(position).getTitle());
        holder.priceView.setText(String.valueOf("$" + products.get(position).getPrice()));
        holder.quantityView.setText("1");

        holder.incBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantityView.getText().toString());
                count++;
                holder.quantityView.setText("" + count);

            }
        });

        holder.decBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.quantityView.getText().toString());
                if (count > 1) {
                    count--;
                }
                holder.quantityView.setText("" + count);

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}