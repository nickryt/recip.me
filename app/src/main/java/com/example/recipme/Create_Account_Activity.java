package com.example.recipme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Create_Account_Activity extends AppCompatActivity{

    //OBJECTS
    Button submit;
    ImageButton backButton;
    EditText nameField;
    EditText emailField;
    EditText passwordField;
    EditText cookDescField;

    //DATABASE
    Database firebaseLink = new Database();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference userDirectoryRef = firebaseDatabase.getReference();

    //COUNTERS FOR INDIVIDUAL FIELDS
    int completedFields = 0;
    boolean createAllowed = false;

    //IF COUNTERS ADD TO 4 THEN USER CAN PROCEED
    int nameChecked = 0;
    int emailChecked = 0;
    int pwChecked = 0;
    int descriptChecked = 0;

    //STRINGS
    String sendName;
    String sendEmail;
    String sendPW;
    String sendCED;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_launch_prompt_userinfo);

        //BACK BUTTON
        backButton = findViewById(R.id.create_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Create_Account_Activity.this, Log_In_Activity.class));
            }
        });

        //SUBMIT BUTTON
        submit = findViewById(R.id.submit_info);
        submit.setVisibility(View.GONE);

        //NAME TEXTFIELD
        nameField = findViewById(R.id.enter_name_text);
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(nameField.getText().toString().isEmpty()){
                    nameChecked = 0;
                }
                else {
                    nameChecked = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(proceedAllowed() == 4){
                submit.setVisibility(View.VISIBLE);
                }
                if(proceedAllowed() < 4){
                submit.setVisibility(View.GONE);
                }
            }
        });

        //EMAIL TEXTFIELD
        emailField = findViewById(R.id.enter_email_text);
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(emailField.getText().toString().isEmpty() || !emailField.getText().toString().contains("@")){
                    emailChecked = 0;
                }
                else {
                    emailChecked = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(proceedAllowed() == 4){
                    submit.setVisibility(View.VISIBLE);
                }

                if(proceedAllowed() < 4){
                    submit.setVisibility(View.GONE);
                }
            }
        });

        //PASSWORD TEXTFIELD
        passwordField = findViewById(R.id.enter_password_text);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordField.getText().toString().isEmpty() || passwordField.getText().toString().length() < 6){
                    pwChecked = 0;
                    passwordField.setTextColor(Color.parseColor("#D02222"));
                }
                else {
                    pwChecked = 1;
                    passwordField.setTextColor(Color.parseColor("#00C26E"));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(proceedAllowed() == 4){
                    submit.setVisibility(View.VISIBLE);
                }

                if(proceedAllowed() < 4){
                    submit.setVisibility(View.GONE);
                }
            }
        });

        //COOKING EXPERIENCE TEXTFIELD
        cookDescField = findViewById(R.id.enter_cook_desc);
        cookDescField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cookDescField.getText().toString().isEmpty()){
                    descriptChecked = 0;
                }
                else {
                    descriptChecked = 1;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(proceedAllowed() == 4){
                    submit.setVisibility(View.VISIBLE);
                }

                if(proceedAllowed() < 4){
                    submit.setVisibility(View.GONE);
                }
            }
        });

        //WHEN USER SUBMIT'S THEIR INFO, SET SCREEN TO ADD RECIPE SCREEN AND SETUP ACCOUNT
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SCREEN WILL NEVER SHOW ON STARTUP NOW
                sendName = nameField.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");
                sendEmail = emailField.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");
                sendPW = passwordField.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");
                sendCED = cookDescField.getText().toString().replaceAll("[^A-Za-z0-9@ ]", "");

                //userDirectoryRef.child("UPDATE").setValue(0);
                userDirectoryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (sendName != null && sendEmail != null && sendPW != null && sendCED != null) {

                            if (dataSnapshot.child(sendEmail).exists() == false) {
                                createAllowed = true;
                            }

                            if (createAllowed == true) {
                                firebaseLink.createUserID(sendName, sendEmail, sendPW, sendCED);
                                User_Data.setEmail(sendEmail);
                                User_Data.setName(sendName);
                                User_Data.setUser_Experience(sendCED);
                                    sendName = null;
                                    sendEmail = null;
                                    sendPW = null;
                                    sendCED = null;
                                startActivity(new Intent(Create_Account_Activity.this, Recipe_Book_Main.class));
                            }

                            if (createAllowed == false) {
                                Toast.makeText(Create_Account_Activity.this, "The email you entered is currently in use.", Toast.LENGTH_SHORT).show();
                            }

                        }//OnDataChange
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });//ValueEventListener

            }//OnClick
        });//OnClickListener

    }//onCreate

    public int proceedAllowed(){
        completedFields = nameChecked + emailChecked + pwChecked + descriptChecked;
        //System.out.println("\nFIELDS COMPLETED : " + completedFields);
        //System.out.println("\nName : " + nameChecked);
        //System.out.println("Email : " + emailChecked);
        //System.out.println("Pass : " + pwChecked);
        //System.out.println("Desc : " + descriptChecked);
       return completedFields;
    }


    @Override
    public void onBackPressed(){
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        cookDescField.setText("");
        startActivity(new Intent(Create_Account_Activity.this, Log_In_Activity.class));
    }

}//First_Launch_Prompt
