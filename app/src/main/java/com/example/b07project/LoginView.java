package com.example.b07project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class LoginView extends AppCompatActivity implements LoginContract.View {

    LoginContract.Presenter presenter;
    LoginContract.Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        model = new LoginModel();
        presenter = new LoginPresenter(this, model);
        model.setPresenter(presenter);
    }

    public void onClickLogin(View view){
        emptyFields();
        TextView error = (TextView)findViewById(R.id.errorText);
        error.setText("");
        presenter.validLogin();
    }

    public void onClickCreateAccount(View view){
        emptyFields();
        TextView error = (TextView)findViewById(R.id.errorText);
        error.setText("");
        presenter.validLogin();
    }

    public void onRadioButtonClicked(View view) {
        RadioButton rb_customer = (RadioButton) findViewById(R.id.radia_customer);
        TextView storeName = (TextView) findViewById(R.id.storeName);
        TextView category = (TextView) findViewById(R.id.category);
        EditText editStoreName = (EditText) findViewById(R.id.editStoreName);
        EditText editCategory = (EditText) findViewById(R.id.editCategory);

        if (rb_customer.isSelected()) {
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

    public void onClickSwitchToNewAccount(View view) {
        emptyFields();
        setContentView(R.layout.register);

    }

    public void onClickSwitchToLogin(View view) {
        emptyFields();
        setContentView(R.layout.login);
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
        if (rb_customer.isSelected()) {
            return "users";
        } else  {
            return "stores";
        }
    }

    public void setErrorText(String message) {
        TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setText(message);
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