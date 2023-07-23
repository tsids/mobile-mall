package com.example.b07project.ui.dashboard;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.b07project.Product;
import com.example.b07project.R;
import com.example.b07project.Store;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    FirebaseDatabase db;
    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/")
                .getReference("stores").getDatabase();

        DatabaseReference ref = db.getReference();

        mText = new MutableLiveData<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot storeSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot stores : storeSnapshot.getChildren()) {
                        Store store = stores.getValue(Store.class);
                        if (store != null) {
                            List<Product> products = store.getProducts();
                            if (products != null) {
                                for (Product product : products) {
                                    if (product != null) {
                                        mText.setValue(product.getTitle());
                                    }
                                }
                            } else {
                                mText.setValue("No products found");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mText.setValue("No stores found");
            }
        });
    }

    public LiveData<String> getText() {
        return mText;
    }
}