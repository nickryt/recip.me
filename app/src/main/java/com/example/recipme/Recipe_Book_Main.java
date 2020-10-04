package com.example.recipme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Recipe_Book_Main extends AppCompatActivity implements Recipe_Adapter.OnRecipeListener{

    RecyclerView recyclerView;
    Recipe_Adapter adapter;
    List<Recipe> recipeList;
    DatabaseReference recipeRef;

    Button editButton;
    ImageButton settingButton;
    Button addRecipeButton;
    Toolbar topToolbar;

    Boolean editButtonSelected = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_book_main);

        //Listener for adding a new recipe
        addRecipeButton = (Button) findViewById(R.id.addNewRecipe);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recipe_Book_Main.this, Recipe_Book_Add_Activity.class));
            }
        });

        //Listener for going to settings page
        settingButton = findViewById(R.id.settingbutton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recipe_Book_Main.this, Settings_Activity.class));
            }
        });

        topToolbar = findViewById(R.id.main_recipe_toolbar);
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editButtonSelected){
                    editButtonSelected = false;
                    editButton.setText("Edit");
                    editButton.setTextColor(Color.parseColor("#ffffff"));

                }

                else {
                    editButtonSelected = true;
                    editButton.setText("Editing");
                    editButton.setTextColor(Color.parseColor("#262626"));
                }

            }
        });

        recipeList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_recipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeRef = FirebaseDatabase.getInstance().getReference().child(User_Data.getEmail()).child("Recipes");
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Recipe recipe =  new Recipe();

                    try {recipe.setTitle(postSnapshot.child("Title").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setInstructions(postSnapshot.child("Instructions").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setIngredients(postSnapshot.child("Ingredients").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setPrepTime(postSnapshot.child("Prep Time").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setDescription(postSnapshot.child("Description").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setImagePath1(postSnapshot.child("Image1").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setImagePath2(postSnapshot.child("Image2").getValue().toString());}
                    catch(NullPointerException e){ }
                    try {recipe.setImagePath3(postSnapshot.child("Image3").getValue().toString());}
                    catch(NullPointerException e){ }
                    recipeList.add(recipe);
                }
                recreateAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(Recipe_Book_Main.this,"" + databaseError, Toast.LENGTH_SHORT);
            }
        });

       // recreateAdapter();


    }//viewMain

    @Override
    public void onBackPressed(){

    }

    @Override
    public void onRecipeClick(int position) {
        if(editButtonSelected){
            //If Editing
            User_Data.setTempEditingRecipe(recipeList.get(position));
            Intent intent1 = new Intent(this, Recipe_Book_Add_Activity.class);
            startActivity(intent1);}
        else {
            //If Only Viewing
            Specific_Recipe_Item.importCurrentRecipe(recipeList.get(position));
            Intent intent2 = new Intent(this, Specific_Recipe_Item.class);
            startActivity(intent2);}

    }//onClick

    public void recreateAdapter(){
        adapter = new Recipe_Adapter(Recipe_Book_Main.this, recipeList,this);
        recyclerView.setAdapter(adapter);
    }


}
