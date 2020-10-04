package com.example.recipme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Log_In_Activity extends AppCompatActivity {

    //USER / PASS
    String email = "";
    String password = "";
    boolean emailAllowed = false;
    boolean passwordAllowed = false;

    //SCREEN ELEMENTS
    EditText emailTextbox;
    EditText passwordTextbox;
    Button logInButton;
    Button dontHaveAccountButton;

    //FIREBASE
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userDirectoryRef = firebaseDatabase.getReference();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        //INITIALIZE SCREEN BUTTONS
        emailTextbox = findViewById(R.id.enter_email);
        passwordTextbox = findViewById(R.id.enter_pass);
        logInButton = findViewById(R.id.log_in);
        dontHaveAccountButton = findViewById(R.id.donthaveaccount);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_Data.clearData();

                if(emailTextbox.getText().toString().isEmpty() || passwordTextbox.getText().toString().isEmpty()){
                    Toast.makeText(Log_In_Activity.this, "Not All Fields Are Complete.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //userDirectoryRef.child("UPDATE").setValue((int)(Math.random()*1000));
                userDirectoryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        emailAllowed=false;
                        passwordAllowed = false;
                        email = emailTextbox.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");
                        password = passwordTextbox.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");

                        if(email != "" && password != "") {

                            if (!dataSnapshot.child(email).exists()) {
                                Log.d("-", "\nDataSnap: " + dataSnapshot.child(email));
                                Toast.makeText(Log_In_Activity.this, "The email that was entered does not exist.", Toast.LENGTH_SHORT).show();
                                emailAllowed = false;
                                return;
                            }

                            if (dataSnapshot.child(email).getValue() != null || dataSnapshot.child(email).getValue() == email) {
                                emailAllowed = true;

                                if (dataSnapshot.child(email).child("User Data").child("Password").getValue().toString().contentEquals(password)) {
                                    passwordAllowed = true;

                                    if (emailAllowed == true && passwordAllowed == true) {
                                        User_Data.setEmail(email);
                                        User_Data.setName(dataSnapshot.child(email).child("User Data").child("Name").getValue().toString());
                                        User_Data.setUser_Experience(dataSnapshot.child(email).child("User Data").child("Cooking Experience Description").getValue().toString());
                                        emailAllowed = false;
                                        passwordAllowed = false;
                                        email = "";
                                        password = "";
                                        startActivity(new Intent(Log_In_Activity.this, Recipe_Book_Main.class));
                                        return;
                                    }
                                }
                                if (dataSnapshot.child(email).child("User Data").child("Password").getValue().toString().contentEquals(password) != true) {
                                    passwordAllowed = false;
                                    Toast.makeText(Log_In_Activity.this, "The password associated with the given email is incorrect.", Toast.LENGTH_SHORT).show();
                                }
                            }//Email Existing
                        }

                    }//OnDataChange

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });//ValueEventListener

            }
        });//ONCLICK

        dontHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailTextbox.setText("");
                passwordTextbox.setText("");
                startActivity(new Intent(Log_In_Activity.this, Create_Account_Activity.class));
            }
        });

        }//onCreate

    //DISABLE BACK BUTTON
    @Override
    public void onBackPressed(){
    }

}//Class
