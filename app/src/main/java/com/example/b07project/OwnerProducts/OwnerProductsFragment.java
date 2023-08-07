package com.example.b07project.OwnerProducts;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.b07project.Product;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerProductsFragment extends Fragment {
    FirebaseDatabase db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static ArrayList<Product> products = new ArrayList<>();
    public OwnerProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OwnerProductsFragment newInstance(String param1) {
        OwnerProductsFragment fragment = new OwnerProductsFragment();
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


    private void setProducts(DataSnapshot snapshot, RecyclerView recycler){
        ArrayList<Product> products = new ArrayList<>();
        for (DataSnapshot prodSnapshot: snapshot.getChildren()) {
            Product product = prodSnapshot.getValue(Product.class);
            product.setKey(prodSnapshot.getKey());
            products.add(product);
        }
        recycler.setAdapter(new OwnerProductRecyclerAdapter(this.getContext(),products,this));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        //How the key is found will need to be updated
        DatabaseReference query = ref.child("stores").child(mParam1).child("products");

        RecyclerView recycler = v.findViewById(R.id.prod_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ImageView addProd = v.findViewById(R.id.prod_add_new);
        //addProd.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN); //Change color to theme colour

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked");
                DatabaseReference store = db.getReference().child("stores").
                        child(getActivity().getIntent().getExtras().get("USERNAME").toString());

                DatabaseReference children = store.child("products");
                store.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot prods = snapshot.child("products");
                        int storeID = Integer.parseInt(snapshot.child("storeID").getValue().toString());
                        int queryLen = (int) (prods.getChildrenCount());
                        int newID=genID();

                        DatabaseReference newProd = children.push();
                        newProd.setValue(new Product("", "Product Title", 0f,
                                "Product Description", storeID, newID));
                        EditProductActivity.newProd = true;
                        loadEdit(queryLen);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setProducts(snapshot,recycler);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    private int genID() {
        int newID=0;
        boolean unique = false;
        Random r = new Random();

        while (!unique){
            newID = Math.abs(r.nextInt());
            unique = true;
            for(Product p:products){
                if (p.getProductID() == newID)
                    unique = false;
            }
        }

        return newID;
    }

    public void loadEdit(int pos){
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores").child(mParam1).child("products");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<>();

                Intent intent = new Intent(getContext(), EditProductActivity.class);

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    keys.add(key);
                }

                intent.putExtra("prod_id", keys.get(pos));
                intent.putExtra("store_id", mParam1);

                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //intent.putExtra("pos")
    }

    @Override
    public void onResume() {
        super.onResume();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("My Products");
        toolbar.setNavigationIcon(null);
    }
}