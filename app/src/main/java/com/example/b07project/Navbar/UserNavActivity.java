package com.example.b07project.Navbar;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.b07project.CartPackage.CartFragment;
import com.example.b07project.CartPackage.CartFragment;
import com.example.b07project.Login.LoginView;
import com.example.b07project.UserOrders.PastOrdersFragment;
import com.example.b07project.R;
import com.example.b07project.Stores.StoresFragment;
import com.example.b07project.databinding.ActivityUserNavBinding;

import io.github.muddz.styleabletoast.StyleableToast;

public class UserNavActivity extends AppCompatActivity {

    ActivityUserNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserNavBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        replaceFragment(new StoresFragment());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);







        binding.userNavActivity.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.stores) {
                replaceFragment(new StoresFragment());
            }
            else if (item.getItemId() == R.id.cart) {
                replaceFragment(new CartFragment());
            }
            else if (item.getItemId() == R.id.past_orders) {
                replaceFragment(new PastOrdersFragment());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            StyleableToast.makeText(this.getBaseContext(), "Successfully logged out!", Toast.LENGTH_LONG, R.style.success).show();
            Intent switchActivityIntent = new Intent(this, LoginView.class);
            setContentView(R.layout.login);
            startActivity(switchActivityIntent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}