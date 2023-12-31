package com.example.b07project.Login;

import androidx.annotation.NonNull;

import com.example.b07project.Stores.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    public void usernameExists(String username, String userType, LoginContract.UsernameExistsCallback callback) {
        this.ref = this.db.getReference();
        DatabaseReference query = this.ref.child(userType); //userType is either 'stores' or 'users'
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String name = postSnapshot.child("username").getValue(String.class);
                    if (name != null && name.equals(username)) {
                        callback.onUsernameExists(true);
                        return; // No need to continue the loop after finding a match
                    }
                }
                callback.onUsernameExists(false); // No match found
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onUsernameExists(false); // Error occurred, treat as no match found
            }
        });
    }

    public void passwordMatches(String username, String userType, String password, LoginContract.PasswordMatchesCallback callback) {
        this.ref = this.db.getReference();
        DatabaseReference query = this.ref.child(userType); //userType is either 'stores' or 'users'
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String name = postSnapshot.child("username").getValue(String.class);
                    String storedPassword = postSnapshot.child("password").getValue(String.class);

                    if (name != null && name.equals(username) && storedPassword != null && storedPassword.equals(password)) {
                        callback.onPasswordMatches(true);
                        return; // No need to continue the loop after finding a match
                    }
                }
                callback.onPasswordMatches(false); // No match found
            }


            @Override
            public void onCancelled(DatabaseError error) {
                callback.onPasswordMatches(false); // Error occurred, treat as no match found
            }
        });
    }

    public void storeIDExists(int id, LoginContract.StoreIDExistsCallback callback) {

        this.ref = this.db.getReference();
        DatabaseReference query = this.ref.child("stores");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Long integerValue = postSnapshot.child("storeid").getValue(Long.class);
                    int storeID = 0;

                    if (integerValue != null) {
                        storeID = integerValue.intValue();
                    }

                    if (storeID == id) {
                        callback.onStoreIDExists(true);
                        return; // No need to continue the loop after finding a match
                    }
                }
                callback.onStoreIDExists(false); // No match found
            }
            @Override
            public void onCancelled(DatabaseError error) {
                callback.onStoreIDExists(false); // Error occurred, treat as no match found
            }
        });

    }

    public void addAccount(Object newAccount, String userType, String username) {

        this.ref= this.db.getReference();


        ref.child(userType).child(username).setValue(newAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //System.out.println("Username added to the 'users' array.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //System.out.println("Failed to add username to the 'users' array: " + e.getMessage());
                    }
                });
    }

    /*public void checkLogin(String username, String password, String userType) {
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
    }*/
}
