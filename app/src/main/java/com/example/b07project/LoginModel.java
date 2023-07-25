package com.example.b07project;

import android.widget.TextView;

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
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void usernameExists(String userName, String userType) {

        ref= db.getReference();
        DatabaseReference query = ref.child(userType).child(userName); //userType is either 'stores' or 'users'
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    presenter.usernameExists();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void passwordMatches(String username, String userType, String password) {

        ref = db.getReference();
        DatabaseReference query = ref.child(userType).child(username).child(password);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    presenter.passwordMatches();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }


}
