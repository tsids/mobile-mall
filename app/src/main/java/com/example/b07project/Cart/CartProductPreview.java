package com.example.b07project.Cart;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.muddz.styleabletoast.StyleableToast;


public class CartProductPreview extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase db;

    CartProduct product;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public CartProductPreview() {
        // Required empty public constructor
    }


    public static CartProductPreview newInstance(String param1, String param2) {
        CartProductPreview fragment = new CartProductPreview();
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
        v.findViewById(R.id.addToCart).setVisibility(View.INVISIBLE);
        v.findViewById(R.id.editTextNumber).setVisibility(View.INVISIBLE);
        v.findViewById(R.id.quantityLabel).setVisibility(View.INVISIBLE);
        v.findViewById(R.id.preview_store_name).setVisibility(View.INVISIBLE);






        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("users").child(mParam2);





        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product = snapshot.child("cart").child(mParam1).getValue(CartProduct.class);
                setText(product.getTitle(), Float.toString(product.getPrice()), product.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    public void setText(String title, String price, String description) {
        TextView prodTitle = (TextView) getView().findViewById(R.id.preview_product_title);
        prodTitle.setText(title);

        TextView prodPrice = (TextView) getView().findViewById(R.id.preview_product_price);
        prodPrice.setText(price);

        TextView prodDesc = (TextView) getView().findViewById(R.id.preview_product_description);
        prodDesc.setText(description);


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

                CartFragment fragment = CartFragment.newInstance(mParam2);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();

            }
        });
    }




}