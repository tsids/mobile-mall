package com.example.b07project.CartPackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.Order;
import com.example.b07project.R;
import com.example.b07project.RecyclerViewInterface;
import com.example.b07project.Stores.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements RecyclerViewInterface {

    FirebaseDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;

    List<CartProduct> cartProducts;


    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);


        Button checkout = v.findViewById(R.id.checkout);
        Button clear_cart = v.findViewById(R.id.clear_cart);
        TextView cart_empty = v.findViewById(R.id.cart_empty_text);
        TextView total_price = v.findViewById(R.id.total_price);
        checkout.setVisibility(View.INVISIBLE);
        cart_empty.setVisibility(View.INVISIBLE);
        total_price.setVisibility(View.INVISIBLE);
        clear_cart.setVisibility(View.INVISIBLE);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("Cart");
        toolbar.setNavigationIcon(null);


        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference pastOrders = ref.child("users").child(mParam1).child("pastOrders");
        DatabaseReference cart = ref.child("users").child(mParam1).child("cart");
        DatabaseReference stores = ref.child("stores");

        RecyclerView recyclerView = v.findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        cart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                float price = 0;
                cartProducts = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartProduct cartProduct = dataSnapshot.getValue(CartProduct.class);
                    cartProducts.add(cartProduct);
                    price += cartProduct.getQuantity() * cartProduct.getPrice();
                    count++;
                }

                if (count == 0) {
                    checkout.setVisibility(View.INVISIBLE);
                    clear_cart.setVisibility(View.INVISIBLE);
                    total_price.setVisibility(View.INVISIBLE);
                    cart_empty.setVisibility(View.VISIBLE);
                } else {
                    checkout.setVisibility(View.VISIBLE);
                    clear_cart.setVisibility(View.VISIBLE);
                    total_price.setVisibility(View.VISIBLE);
                    cart_empty.setVisibility(View.INVISIBLE);
                } 

                total_price.setText("Total Price: " +price);

                recyclerView.setAdapter(new CartAdapter(CartFragment.this.getContext(), cartProducts, CartFragment.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Checkout Logic
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Cart exists
                            StyleableToast.makeText(getContext(), "Order placed successfully!", Toast.LENGTH_LONG, R.style.success).show();
                            SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
                            String now = ISO_8601_FORMAT.format(new Date());

                            // get all products
                            ArrayList<CartProduct> products = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                products.add(dataSnapshot.getValue(CartProduct.class));
                            }

                            Order order = new Order(products, now);
                            // Save the cart in pastOrders
                            pastOrders.child(now).setValue(order);

                            // loop over all stores and find where each storeID matches the correct store
                            stores.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        ArrayList<CartProduct> cartProducts = new ArrayList<>();
                                        Store store = ds.getValue(Store.class); // grab each store
                                        for (CartProduct product : products) {
                                            if (product.getStoreID() == store.getStoreID()) { // if product id matches store id
                                                cartProducts.add(product); // add product
                                            }
                                        }
                                        // objects ordered from the same store get put in one order
                                        DatabaseReference ordersRef = ref.child("stores").child(store.getUsername()).child("orders");
                                        Order storeOrder = new Order(cartProducts, now);
                                        ordersRef.child(now).setValue(storeOrder);
                                        ordersRef.child(now).child("user").setValue(mParam1);
                                    }
                                    clearCart();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            StyleableToast.makeText(getContext(), "Cart cannot be empty!", Toast.LENGTH_LONG, R.style.error).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled if needed
                    }
                });
            }
        });

        // clears cart if clear_cart button is clicked
        clear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
                StyleableToast.makeText(getContext(), "Cleared cart!", Toast.LENGTH_LONG, R.style.success).show();
            }
        });

        return v;
    }

    public void clearCart() {
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference cart = ref.child("users").child(mParam1).child("cart");

        cart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Clear cart
                    cart.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });
    }

    // Recycler View stuff
    @Override
    public void onItemClick(int position) {
//        DatabaseReference ref= db.getReference();
//        DatabaseReference query = ref.child("users").child(mParam1).child("cart");
//        List<String> keys = new ArrayList<>();
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    String key = postSnapshot.getKey();
//                    keys.add(key);
//                }
//
//                CartProductPreview fragment = CartProductPreview.newInstance(keys.get(position), mParam1);
//
//// Then, you can add this fragment to your activity using FragmentManager
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        CartProduct cartProduct = cartProducts.get(position);
        CartProductPreview fragment = CartProductPreview.newInstance(cartProduct.getTitle(), String.valueOf(cartProduct.getPrice()), cartProduct.getDescription());
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void adjustQuantity(int position, int adjustment, boolean add) {

        CartProduct cartProduct = cartProducts.get(position);
        int quantity = adjustment;

        if (add){
            quantity += cartProduct.getQuantity();
        }

        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("users").child(mParam1).child("cart").child(cartProduct.getStoreID() + ":" + cartProduct.getProductID()).child("quantity");

        if (quantity > 0) {
            query.setValue(quantity);
        }



    }

    public void removeFromCart(int position) {
        CartProduct cartProduct = cartProducts.get(position);


        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("users").child(mParam1).child("cart").child(cartProduct.getStoreID() + ":" + cartProduct.getProductID());

        query.removeValue();
    }
}