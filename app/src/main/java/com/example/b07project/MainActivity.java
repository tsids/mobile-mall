package com.example.b07project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button switchButton = findViewById(R.id.button_switch);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to the new activity
                Intent intent = new Intent(MainActivity.this, StoreOwnerProducts.class);
                startActivity(intent);
            }
        });
    }

        //Writing to a realtime database
/*DatabaseReference ref =
FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getRe
ference(); //update URL!!!
Student student = new Student(800, "dan");
ref.child("students").child("s1").setValue(student);*/
//Reading from a realtime database using a persistent listener
//DatabaseReference ref =
//FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getRe
//ference("students"); //update URL!!!
//ValueEventListener listener = new ValueEventListener() {
//@Override
//public void onDataChange(DataSnapshot dataSnapshot) {
//Log.i("demo", "data changed");
//for(DataSnapshot child:dataSnapshot.getChildren()) {
//Student student = child.getValue(Student.class);
//Log.i("demo", student.toString());
//}
//}
//@Override
//public void onCancelled(DatabaseError databaseError) {
//Log.w("warning", "loadPost:onCancelled",
//databaseError.toException());
//}
//};
//ref.addValueEventListener(listener);
//Reading once from a realtime database
//DatabaseReference ref =
//FirebaseDatabase.getInstance("https://DATABASE_NAME.firebaseio.com").getRe
//ference("students"); //update URL!!!
//ref.child("s1").get().addOnCompleteListener(new
//OnCompleteListener<DataSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DataSnapshot> task) {
//if (!task.isSuccessful()) {
//Log.e("demo", "Error getting data", task.getException());
//}
//else {
//Log.i("demo", task.getResult().getValue().toString());
//}
//}
//});
}