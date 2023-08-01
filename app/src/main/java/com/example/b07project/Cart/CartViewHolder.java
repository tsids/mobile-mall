package com.example.b07project.Cart;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.UserProducts.UserProductsFragment;

public class CartViewHolder extends RecyclerView.ViewHolder {

    TextView productNameView, priceView, quantityView;
    Button incBtn, decBtn;

    public CartViewHolder(@NonNull View itemView, CartFragment cartFragment) {
        super(itemView);
        productNameView = itemView.findViewById(R.id.product_name);
        priceView = itemView.findViewById(R.id.price);
        quantityView = itemView.findViewById(R.id.quantity);
        incBtn = itemView.findViewById(R.id.increment_btn);
        decBtn = itemView.findViewById(R.id.decrement_btn);


        itemView.findViewById(R.id.preview_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartFragment != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        cartFragment.onItemClick(pos);
                    }
                }
            }
        });
    }

}
