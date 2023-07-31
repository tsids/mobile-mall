package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.Cart.CartProduct;
import com.example.b07project.UserProducts.UserProductsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductPreview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPreview extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase db;

    Product product;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public ProductPreview() {
        // Required empty public constructor
    }


    public static ProductPreview newInstance(String param1, String param2) {
        ProductPreview fragment = new ProductPreview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_preview, container, false);


        v.findViewById(R.id.addToCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = v.findViewById(R.id.editTextNumber);

// Get the text from the EditText as a String
                int quantity = Integer.parseInt(editText.getText().toString());
                if (quantity <= 0) {
                    StyleableToast.makeText(getContext(), "Quantity must be greater than zero", Toast.LENGTH_LONG, R.style.error).show();
                } else {
                    addToCart(quantity);
                }

            }
        });



        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores").child(mParam1);





        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.child("products").child(mParam2).getValue(Product.class);
                setText(product.getTitle(), Float.toString(product.getPrice()), product.getDescription(), snapshot.child("store").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    public void setText(String title, String price, String description, String store) {
        TextView prodTitle = (TextView) getView().findViewById(R.id.preview_product_title);
        prodTitle.setText(title);

        TextView prodPrice = (TextView) getView().findViewById(R.id.preview_product_price);
        prodPrice.setText(price);

        TextView prodDesc = (TextView) getView().findViewById(R.id.preview_product_description);
        prodDesc.setText(description);

        TextView prodStore = (TextView) getView().findViewById(R.id.preview_store_name);
        prodStore.setText(store);
    }


    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("Preview");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserProductsFragment fragment = UserProductsFragment.newInstance(mParam1);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();

            }
        });
    }


    private void addToCart(int quantity) {
        Log.d("Quantity", "" + quantity);
        CartProduct cartProduct = new CartProduct(product.getImageURL(), product.getTitle(), product.getPrice(), product.getDescription(), product.getStoreID(), product.getProductID(), quantity, false, false, false);

        DatabaseReference ref = db.getReference();
        DatabaseReference query = ref.child("users").child("1").child("cart").child(product.getStoreID() + ":" + product.getProductID());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Product already exists in the cart, update the quantity
                    CartProduct existingProduct = snapshot.getValue(CartProduct.class);
                    int currentQuantity = existingProduct.getQuantity();
                    cartProduct.setQuantity(currentQuantity + quantity);
                    StyleableToast.makeText(getContext(), "Successfully added " + quantity + " more " + product.getTitle() + "s to the cart", Toast.LENGTH_LONG, R.style.success).show();
                } else {
                    StyleableToast.makeText(getContext(), "Successfully added " + quantity + " " + product.getTitle() + "s to the cart", Toast.LENGTH_LONG, R.style.success).show();
                }
                // Set the cartProduct with the updated or initial quantity
                query.setValue(cartProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }

}