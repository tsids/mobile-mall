package com.example.b07project;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPresenter implements LoginContract.Presenter {


    LoginContract.View view;
    LoginContract.Model model;
    public boolean usernameExists;
    public boolean passwordMatches;
    FirebaseDatabase db;

    public LoginPresenter(LoginContract.View view, LoginContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void validLogin() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();

        this.usernameExists = false;
        this.passwordMatches = false;

        if (username.equals("") || password.equals("")) {
            view.setErrorText("Username and/or password cannot be empty.");
            return;
        }

        // Check if the username exists
        model.usernameExists(username, userType, new LoginContract.UsernameExistsCallback() {
            @Override
            public void onUsernameExists(boolean exists) {
                usernameExists = exists;

                // Check if the password matches
                model.passwordMatches(username, userType, password, new LoginContract.PasswordMatchesCallback() {
                    @Override
                    public void onPasswordMatches(boolean matches) {
                        passwordMatches = matches;

                        // Perform login check after both username and password checks are done
                        if (usernameExists && passwordMatches) {
                            view.setViewAndActivity();
                        } else {
                            view.setErrorText("Incorrect username or password. Please try again");
                        }
                    }
                });
            }
        });
    }

    /*public void usernameExists() { usernameExists = true; }*/

    /*public void passwordMatches() {
        passwordMatches = true;
    }*/

    //////////CREATING NEW ACCOUNT FUNCTIONS/////////

    @Override
    public void validNewAccount() {
        String username = view.getUsername();
        String userType = view.getUserType();
        String password = view.getPassword();

        if (username.equals("") || password.equals("")) {
            view.setErrorText("Username or password cannot be empty");
            return;
        }

        this.usernameExists = false;

        //model.usernameExists(username, userType);
        model.usernameExists(username, userType, new LoginContract.UsernameExistsCallback() {
            public void onUsernameExists(boolean exists) {
                usernameExists = exists;

                if (usernameExists) {
                    view.setErrorText("Username is already taken, please choose a different one.");
                } else {
                    createAccount(username, userType, password);
                    view.accountSuccessfulRedirect();
                    view.setSuccessText("Account successfully created. Please log in");
                }
            }
        });
    }

    @Override
    public void createAccount (String username, String userType, String password) {
        //add username, password to database and switch to user navigation
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
        DatabaseReference ref= db.getReference();
        DatabaseReference query = ref.child("users").child(username);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                query.child("username").setValue(username);
                query.child("password").setValue(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}
