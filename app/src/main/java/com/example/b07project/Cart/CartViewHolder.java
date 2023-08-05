package com.example.b07project.Cart;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.Cart.CartFragment;
import com.example.b07project.R;
import com.example.b07project.UserProducts.UserProductsFragment;

public class CartViewHolder extends RecyclerView.ViewHolder {

    TextView productNameView, priceView;
    EditText quantityView;
    Button incBtn, decBtn;

    public CartViewHolder(@NonNull View itemView, CartFragment cartFragment) {
        super(itemView);
        productNameView = itemView.findViewById(R.id.product_name);
        priceView = itemView.findViewById(R.id.price);
        quantityView = itemView.findViewById(R.id.quantity);

        quantityView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called while the text is being changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has been changed
                String quantityText = editable.toString();
                // Convert the quantityText to an integer (you may want to handle invalid input here)
                int quantity = Integer.parseInt(quantityText);

                // Call the cartFragment.adjustQuantity() method with the new quantity
                if (cartFragment != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        cartFragment.adjustQuantity(pos, quantity, false);
                    }
                }
            }
        });



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

        itemView.findViewById(R.id.increment_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (cartFragment != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        cartFragment.adjustQuantity(pos, 1, true);
                    }
                }

            }
        });

        itemView.findViewById(R.id.decrement_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (cartFragment != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {


                            cartFragment.adjustQuantity(pos, -1, true);


                    }
                }

            }
        });

        itemView.findViewById(R.id.prod_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartFragment != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {


                        cartFragment.removeFromCart(pos);


                    }
                }
            }
        });
    }

}
