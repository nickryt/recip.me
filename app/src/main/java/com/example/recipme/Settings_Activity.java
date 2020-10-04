package com.example.recipme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Settings_Activity extends AppCompatActivity {

    ImageButton backbutton;
    ImageButton logoutButton;
    TextView name;
    TextView email;
    TextView experience;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        backbutton = findViewById(R.id.backarrow2);
        logoutButton = findViewById(R.id.logout_button);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings_Activity.this, Recipe_Book_Main.class));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings_Activity.this);

                builder.setTitle("Are you sure you want to log out of " + User_Data.getEmail() + "?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User_Data.clearData();
                                startActivity(new Intent(Settings_Activity.this, Log_In_Activity.class));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        name = findViewById(R.id.setting_setname);
        email = findViewById(R.id.setting_setemail);
        experience = findViewById(R.id.setting_setuserdesc);

        name.setText(User_Data.getName());
        email.setText(User_Data.getEmail());
        experience.setText(User_Data.getUser_Experience());

    }//onCreate

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Settings_Activity.this, Recipe_Book_Main.class));
    }

}
