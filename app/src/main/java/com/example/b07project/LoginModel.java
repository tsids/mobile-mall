package com.example.b07project;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel implements LoginContract.Model {
    LoginContract.Presenter presenter;
    FirebaseDatabase db;
    DatabaseReference ref;
    public LoginModel() {
        this.db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void usernameExists(String username, String userType) {

        this.ref= this.db.getReference();
        DatabaseReference query = this.ref.child(userType); //userType is either 'stores' or 'users'
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String name = postSnapshot.child("username").getValue(String.class);
                    if (name != null && name.equals(username)) {
                        presenter.usernameExists();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void passwordMatches(String username, String userType, String password) {

        this.ref = this.db.getReference();
        DatabaseReference query = this.ref.child(userType);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String name = postSnapshot.child("username").getValue(String.class);

                    if (name.equals(username)) {
                        String pass = postSnapshot.child("password").getValue(String.class);
                        if (pass.equals(password)) {
                            presenter.passwordMatches();
                            break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

    public void checkLogin(String username, String password, String userType) {
        this.ref = db.getReference();
        DatabaseReference query = this.ref.child(userType);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String name = postSnapshot.child("username").getValue(String.class);
                    String pass = postSnapshot.child("password").getValue(String.class);


                    if (name != null && name.equals(username) && pass.equals(password)) {
//                        presenter.usernameExists();
//                        presenter.passwordMatches();
                        presenter.login();
                        break;
                    }
                }
                presenter.invalidLogin();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
