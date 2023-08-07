package com.example.b07project.OwnerProducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.Product;
import com.example.b07project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OwnerProductRecyclerAdapter extends RecyclerView.Adapter<OwnerProductRecyclerAdapter.CustomViewHolder>{
    Context context;
    ArrayList<Product> products;
    OwnerProductsFragment caller;
    public static DatabaseReference db;

    public OwnerProductRecyclerAdapter(Context context, ArrayList<Product> products,OwnerProductsFragment caller) {
        this.context = context;
        this.products = products;
        this.caller = caller;
        db = FirebaseDatabase.
                getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/").
                getReference();
    }

    @NonNull
    @Override
    public OwnerProductRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_layout,parent,false);
        return new OwnerProductRecyclerAdapter.CustomViewHolder(view,caller);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.name.setText(products.get(position).getTitle());
        holder.desciption.setText(products.get(position).getDescription());
        String price = "$"+products.get(position).getPrice();
        holder.price.setText(price);

        holder.prod_key = products.get(position).getKey();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,desciption;
        ImageView img;
        String prod_key;
        public CustomViewHolder(@NonNull View itemView,OwnerProductsFragment caller) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_name);
            price = itemView.findViewById(R.id.prod_price);
            desciption = itemView.findViewById(R.id.prod_description);
            img = itemView.findViewById(R.id.prod_img);

            itemView.findViewById(R.id.prod_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditProductActivity.newProd = false;
                    caller.loadEdit(getAbsoluteAdapterPosition());
                }
            });

            itemView.findViewById(R.id.prod_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.child("stores").
                            child(caller.getActivity().getIntent().getExtras().get("USERNAME").toString()).
                            child("products").child(prod_key).removeValue();
                }
            });
        }
    }
}
