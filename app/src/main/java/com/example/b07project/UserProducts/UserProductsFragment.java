package com.example.b07project.UserProducts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.Cart.CartProduct;
import com.example.b07project.Product;
import com.example.b07project.ProductPreview;
import com.example.b07project.R;
import com.example.b07project.RecyclerViewInterface;
import com.example.b07project.Stores.StoresFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class UserProductsFragment extends Fragment implements RecyclerViewInterface {

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    List<Product> products = new ArrayList<>();
    FirebaseDatabase db;


    public UserProductsFragment() {

    }



    public static UserProductsFragment newInstance(String param1) {
        UserProductsFragment fragment = new UserProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_display_products, container, false);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }




        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores").child(mParam1).child("products");

        RecyclerView recyclerView = v.findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    products.add(product);

                }
                recyclerView.setAdapter(new ProductAdapter(UserProductsFragment.this.getContext(), products, UserProductsFragment.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    @Override
    public void onItemClick(int position) {
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores").child(mParam1).child("products");
        List<String> keys = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    keys.add(key);
                }

                ProductPreview fragment = ProductPreview.newInstance(mParam1, keys.get(position));

// Then, you can add this fragment to your activity using FragmentManager
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new StoresFragment());
                fragmentTransaction.commit();

            }
        });
    }


    public void onAddToCartClick(int position, int quantity) {
        Product product = products.get(position);
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
                    StyleableToast.makeText(getContext(), "Successfully added " + quantity + " more " + product.getTitle() + "s to the cart", Toast.LENGTH_LONG, R.style.addedToCart).show();
                    cartProduct.setQuantity(currentQuantity + quantity);
                } else {
                    StyleableToast.makeText(getContext(), "Successfully added " + quantity + " " + product.getTitle() + "s to the cart", Toast.LENGTH_LONG, R.style.addedToCart).show();
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