package com.example.b07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProductActivity extends AppCompatActivity {


    FirebaseDatabase db;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        //How the key is found will need to be updated
        DatabaseReference query = ref.child("stores").child(getIntent().
                getStringExtra("store_id")).child("products").
                child(getIntent().getStringExtra("prod_id"));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentProduct = snapshot.getValue(Product.class);
                EditText name = findViewById(R.id.edit_prod_name);
                EditText price = findViewById(R.id.edit_prod_price);
                EditText desc = findViewById(R.id.edit_prod_description);
                Button save = findViewById(R.id.edit_prod_save);
                Button cancel = findViewById(R.id.edit_prod_cancel);

                name.setText(currentProduct.getTitle());
                price.setText(currentProduct.getPrice()+"");
                desc.setText(currentProduct.getDescription());

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO Update database with new data from inputs

                        EditText name = findViewById(R.id.edit_prod_name);
                        EditText price = findViewById(R.id.edit_prod_price);
                        EditText desc = findViewById(R.id.edit_prod_description);

                        DatabaseReference ref= db.getReference();
                        //How the key is found will need to be updated

                        DatabaseReference query = ref.child("stores").child(getIntent().
                                        getStringExtra("store_id")).child("products").
                                child(getIntent().getStringExtra("prod_id"));
                        query.setValue(new Product("",
                                name.getText().toString(),Float.parseFloat(price.getText().toString()),
                                desc.getText().toString(),currentProduct.getStoreID(),
                                currentProduct.getProductID()));
                        System.out.println("Editing: "+currentProduct.getProductID());
                        finish();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}