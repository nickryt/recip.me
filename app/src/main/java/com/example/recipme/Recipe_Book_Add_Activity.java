package com.example.recipme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Recipe_Book_Add_Activity extends AppCompatActivity implements Overview_Fragment.OnInputListenerOF, Instructions_Fragment.OnInputListenerInsF, Ingredients_Fragment.OnInputListenerIngF{
    //ITEMS FOR TAB MENU
    ImageButton backButton;
    ImageButton saveButton;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabItem overviewTab;
    TabItem ingredientsTab;
    TabItem instructionsTab;

    //RECIPE FIELDS (TAKING FIELDS FROM)
    Recipe recipe = new Recipe();
    String recipeTitle;
    String recipePrepTime;
    String recipeDescription;
    String recipeIngredients;
    String recipeInstructions;

    Database firebaseLink;


    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.recipe_book_add);

            firebaseLink = new Database();

          //IMPORT SET IMAGE PATHS
            if(User_Data.getTempEditingRecipe() != null){

                if(User_Data.getTempEditingRecipe().getImagePath1() != null){
                    User_Data.imagePath1 = User_Data.getTempEditingRecipe().getImagePath1(); }
                if(User_Data.getTempEditingRecipe().getImagePath2() != null){
                    User_Data.imagePath2 = User_Data.getTempEditingRecipe().getImagePath2(); }
                if(User_Data.getTempEditingRecipe().getImagePath3() != null){
                    User_Data.imagePath3 = User_Data.getTempEditingRecipe().getImagePath3(); }
            }

            //BACK BUTTON LISTENER + CONFIRMATION DIALOG
            backButton = findViewById(R.id.backarrow);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackButtonPressed();
                }});

            //SAVE CHECK MARK BUTTON LISTENER -> PACK RECIPE DATA + SEND TO FIREBASE
            saveButton = findViewById(R.id.savecheckmark);
            saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ALERT IF FIELDS ARE NOT SET
              if       (recipeTitle == null ||
                        recipePrepTime == null ||
                        recipeDescription == null ||
                        recipeIngredients == null ||
                        recipeInstructions == null){
                    Toast.makeText(Recipe_Book_Add_Activity.this, "Please Verify That All Fields Are Correct And Completed Before Proceeding.",
                            Toast.LENGTH_LONG).show();
                    return;
                }


                else //SEND OFF DATA TO FIREBASE IF ALL FIELDS ARE COMPLETE

                //If the recipe in this activity is being edited, and not freshly created....
                if(User_Data.getTempEditingRecipe() != null){
                    DatabaseReference deleteOldRecipe = FirebaseDatabase.getInstance().getReference(User_Data.getEmail()).child("Recipes").child(User_Data.tempRecipeOldTitle);
                    deleteOldRecipe.removeValue();

                }

                recipe.setTitle(recipeTitle);
                recipe.setPrepTime(recipePrepTime);
                recipe.setDescription(recipeDescription);
                recipe.setIngredients(recipeIngredients);
                recipe.setInstructions(recipeInstructions);

                firebaseLink.createNewRecipe(
                        recipe.getTitle(),
                        recipe.getPrepTime(),
                        recipe.getDescription(),
                        recipe.getIngredients(),
                        recipe.getInstructions()
                        );

                User_Data.clearTempEditingRecipe();

                startActivity(new Intent(Recipe_Book_Add_Activity.this, Recipe_Book_Main.class));
            }});

        //TAB LAYOUTS
        tabLayout = findViewById(R.id.tablayout);
        overviewTab = findViewById(R.id.overview);
        ingredientsTab = findViewById(R.id.ingredients);
        instructionsTab = findViewById(R.id.instructions);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }//onTabSelected

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }//onTabUnselected

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }//onTabReselected
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }//onCreate

    //BACK BUTTON CONFIRMATION DIALOG
    public void onBackButtonPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to exit? Unsaved changes will be lost.")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(User_Data.getTempEditingRecipe() != null){
                            User_Data.clearTempEditingRecipe();
                        }

                        User_Data.clearTempImageLink();
                        User_Data.clearImages();
                        startActivity(new Intent(Recipe_Book_Add_Activity.this, Recipe_Book_Main.class));

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

    @Override
    public void sendInput(String title, String preptime, String description) {
        recipeTitle = title;
        recipePrepTime = preptime;
        recipeDescription = description;
    }

    @Override
    public void sendInput(String instructions) {
        recipeInstructions = instructions;
    }

    @Override
    public void sendInput(String ingredients, String nullString) {
        recipeIngredients = ingredients;
    }

    @Override
    public void onBackPressed(){

    }

}//Recipe_Book_Add_Activity
