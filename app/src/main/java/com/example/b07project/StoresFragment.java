package com.example.b07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoresFragment extends Fragment implements RecyclerViewInterface {


    List<List<String>> storesInfo = new ArrayList<>();
    FirebaseDatabase db;
    public StoresFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StoresFragment newInstance() {
        StoresFragment fragment = new StoresFragment();
        Bundle args = new Bundle();
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
        View v = inflater.inflate(R.layout.fragment_display_store, container, false);

        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores");

        RecyclerView recyclerView = v.findViewById(R.id.store_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<List<String>> storesInfo = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String name = postSnapshot.child("store").getValue(String.class);
                    String category = postSnapshot.child("category").getValue(String.class);
                    List<String> storeInfo = new ArrayList<>();
                    storeInfo.add(name);
                    storeInfo.add(category);
                    storesInfo.add(storeInfo);
                }
                recyclerView.setAdapter(new StoreAdapter(StoresFragment.this.getContext(), storesInfo, StoresFragment.this));
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
        DatabaseReference query = ref.child("stores");
        List<String> keys = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    keys.add(key);
                }

                UserProductsFragment fragment = UserProductsFragment.newInstance(keys.get(position));

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
}
