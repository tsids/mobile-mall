package com.example.b07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores").child(mParam1);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
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



        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product = snapshot.child("products").child(mParam2).getValue(Product.class);
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
}