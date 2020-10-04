package com.example.recipme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {

    int secondsDelayed = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_start);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Splash_Screen.this, Log_In_Activity.class));
                    finish();
                }
            }, secondsDelayed * 1000);

    }//onCreate

    //DISABLE BACK BUTTON
    @Override
    public void onBackPressed(){

    }

}//Splash_Screen
