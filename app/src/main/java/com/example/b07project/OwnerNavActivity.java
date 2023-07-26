package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.b07project.databinding.ActivityOwnerNavBinding;

public class OwnerNavActivity extends AppCompatActivity {

    ActivityOwnerNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new UserProductsFragment());

        binding.ownerNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.products) {
                replaceFragment(new UserProductsFragment());
            }
            else if (item.getItemId() == R.id.orders) {
                replaceFragment(new OrdersFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}