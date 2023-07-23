package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayStoreActivity extends AppCompatActivity implements RecyclerViewInterface {

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_store);


        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("stores");

        RecyclerView recyclerView = findViewById(R.id.store_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                recyclerView.setAdapter(new StoreAdapter(getApplicationContext(), storesInfo, DisplayStoreActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(DisplayStoreActivity.this, DisplayProductsActivity.class);

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

                intent.putExtra("KEY", keys.get(position));
                startActivity(intent);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }
}