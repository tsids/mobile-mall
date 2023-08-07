package com.example.b07project.OwnerProducts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b07project.Product;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProductActivity extends AppCompatActivity {


    FirebaseDatabase db;
    private Product currentProduct;

    //Flag for whether product being edited is also being added
    public static boolean newProd = false;

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
        AppCompatActivity me = this;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentProduct = snapshot.getValue(Product.class);
                EditText name = findViewById(R.id.edit_prod_name);
                EditText price = findViewById(R.id.edit_prod_price);
                EditText desc = findViewById(R.id.edit_prod_description);
                ImageView save = findViewById(R.id.edit_prod_save);
                ImageView cancel = findViewById(R.id.edit_prod_cancel);

                name.setText(currentProduct.getTitle());
                price.setText(currentProduct.getPrice()+"");
                desc.setText(currentProduct.getDescription());

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (price.getText().toString().length()>0 &&
                                isNumeric(price.getText().toString())) {
                            EditText name = findViewById(R.id.edit_prod_name);
                            EditText price = findViewById(R.id.edit_prod_price);
                            EditText desc = findViewById(R.id.edit_prod_description);
                            //Use value rounded to nearest cent
                            float priceVal = (float) (Math.round(Float.parseFloat(price.getText().toString())*100)/100.0);
                            DatabaseReference ref = db.getReference();

                            DatabaseReference query = ref.child("stores").child(getIntent().
                                            getStringExtra("store_id")).child("products").
                                    child(getIntent().getStringExtra("prod_id"));
                            query.setValue(new Product("",
                                    name.getText().toString(), priceVal,
                                    desc.getText().toString(), currentProduct.getStoreID(),
                                    currentProduct.getProductID()));

                            newProd = false;
                            finish();
                        }else{
                            Toast toast = Toast.makeText(me, "Invalid price, try again", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (newProd){
                            DatabaseReference ref= db.getReference();
                            //How the key is found will need to be updated
                            ref.child("stores").child(getIntent().
                                            getStringExtra("store_id")).child("products").
                                    child(getIntent().getStringExtra("prod_id")).removeValue();
                        }

                        newProd = false;
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private boolean isNumeric(String s){
        int len = s.length();
        return s.replaceAll("([0-9]+[.]?[0-9]+)","").length() == 0;
    }
}