package com.example.b07project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    boolean isFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance("https://b07project-4cc9c-default-rtdb.firebaseio.com/");
    }

    public void onClickAdd(View view){
        DatabaseReference ref= db.getReference();
        EditText userText = (EditText) findViewById(R.id.editTextTextPersonName);
        String username = userText.getText().toString();
        userText.setText("");
        DatabaseReference query = ref.child("users").child(username);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    ref.child("users").child(username).child("username").setValue(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void onClickCheckOutside(View view){
        DatabaseReference ref= db.getReference();
        EditText userText = (EditText) findViewById(R.id.editTextTextPersonName);
        String username = userText.getText().toString();
        DatabaseReference query = ref.child("users").child(username);
        this.isFound = false;

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isFound = snapshot.exists();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        setOutputText();
    }

    public void onClickCheckInside(View view){
        DatabaseReference ref= db.getReference();
        EditText userText = (EditText) findViewById(R.id.editTextTextPersonName);
        String username = userText.getText().toString();
        DatabaseReference query = ref.child("users").child(username);
        this.isFound = false;

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isFound = snapshot.exists();
                setOutputText();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void setOutputText()
    {
        TextView output = (TextView) findViewById(R.id.output);
        if(isFound)
        {
            output.setText("Found it!");
        }
        else
        {
            output.setText("Didn't find it!");
        }
    }

}