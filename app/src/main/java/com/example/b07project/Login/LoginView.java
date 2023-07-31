package com.example.b07project.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.Navbar.OwnerNavActivity;
import com.example.b07project.R;
import com.example.b07project.Navbar.UserNavActivity;


public class LoginView extends AppCompatActivity implements LoginContract.View {

    LoginContract.Presenter presenter;
    LoginContract.Model model;
    Intent switchActivityIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //emptyFields();
        model = new LoginModel();
        presenter = new LoginPresenter(this, model);
        model.setPresenter(presenter);
        //RadioButton rb_customer = (RadioButton) findViewById(R.id.radia_customer);
        //rb_customer.setChecked(true);
    }

    @Override
    public void setViewAndActivity() {
        if (getUserType().equals("stores")) {
            switchActivityIntent = new Intent(this, OwnerNavActivity.class);
            setContentView(R.layout.activity_owner_nav);

        } else {
            switchActivityIntent = new Intent(this, UserNavActivity.class);
            setContentView(R.layout.activity_user_nav);

        }
        startActivity(switchActivityIntent);
    }

    public void onClickLogin(View view){
        setErrorText("");
        presenter.validLogin();
        //emptyFields();
    }

    public void onClickCreateAccount(View view){

        setErrorText("");
        presenter.validNewAccount();
        emptyFields();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton rb_customer = (RadioButton) findViewById(R.id.radia_customer);
        TextView storeName = (TextView) findViewById(R.id.storeName);
        TextView category = (TextView) findViewById(R.id.category);
        EditText editStoreName = (EditText) findViewById(R.id.editStoreName);
        EditText editCategory = (EditText) findViewById(R.id.editCategory);

        if (rb_customer.isChecked()) {
            storeName.setVisibility(View.INVISIBLE);
            category.setVisibility(View.INVISIBLE);
            editStoreName.setVisibility(View.INVISIBLE);
            editCategory.setVisibility(View.INVISIBLE);
            editStoreName.setClickable(false);
            editCategory.setClickable(false);

        } else  {
            storeName.setVisibility(View.VISIBLE);
            category.setVisibility(View.VISIBLE);
            editStoreName.setVisibility(View.VISIBLE);
            editCategory.setVisibility(View.VISIBLE);
            editStoreName.setClickable(true);
            editCategory.setClickable(true);
        }
    }


    @Override
    public void onClickSwitchToNewAccount(View view) {
        emptyFields();
        setContentView(R.layout.register);
    }

    @Override
    public void onClickSwitchToLogin(View view) {
        emptyFields();
        setContentView(R.layout.login);
        RadioButton rb_customer = (RadioButton) findViewById(R.id.radia_customer);
        rb_customer.setChecked(true);
    }

    public void emptyFields() {
        EditText userTextUser = (EditText) findViewById(R.id.editUsername);
        EditText userTextPass = (EditText) findViewById(R.id.editPassword);
        userTextUser.setText("");
        userTextPass.setText("");
    }
    public String getUsername() {
        EditText userText = (EditText) findViewById(R.id.editUsername);
        String username = userText.getText().toString();
        return username;
    }
    public String getPassword() {
        EditText userText = (EditText) findViewById(R.id.editPassword);
        String username = userText.getText().toString();
        return username;
    }

    @Override
    public String getUserType() {
        RadioButton rb_customer = (RadioButton) findViewById(R.id.radia_customer);
        if (rb_customer.isChecked()) {
            return "users";
        } else  {
            return "stores";
        }
    }

    public String getStoreName() {
        EditText storeName = (EditText) findViewById(R.id.editStoreName);
        return storeName.getText().toString();
    }

    public String getCategory() {
        EditText category = (EditText) findViewById(R.id.editCategory);
        return category.getText().toString();

    }

    public void setErrorText(String message) {
        TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setTextColor(Color.RED);
        errorText.setText(message);
    }

    public void setSuccessText(String message) {
        TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setTextColor(Color.GREEN);
        errorText.setText(message);
    }

    @Override
    public void accountSuccessfulRedirect() {
        setContentView(R.layout.login);
    }


//    public void onClickCheckOutside(View view){
//        DatabaseReference ref= db.getReference();
//        EditText userText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String username = userText.getText().toString();
//        DatabaseReference query = ref.child("users").child(username);
//        this.isFound = false;
//
//        query.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                isFound = snapshot.exists();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//        setOutputText();
//    }

//    public void onClickCheckInside(View view){
//        DatabaseReference ref= db.getReference();
//
//        DatabaseReference query = ref.child("users").child(username);
//        this.isFound = false;
//
//        query.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                isFound = snapshot.exists();
//                setOutputText();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
//    }

}