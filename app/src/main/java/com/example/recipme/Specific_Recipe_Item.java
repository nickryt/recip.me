package com.example.recipme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Specific_Recipe_Item extends AppCompatActivity {

    public static Recipe recipe;
    List<String> photoList;
    int photoPosition = 0;

    ImageButton backbutton;
    ImageView photos;
    TextView preptime;
    TextView desc;
    TextView ingred;
    TextView instruc;
    TextView title;
    TextView photosText;
    ImageButton deleteRecipeButton;
    ImageButton editRecipeButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_specific_item);

        backbutton = findViewById(R.id.backbutton_specific);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Specific_Recipe_Item.this, Recipe_Book_Main.class));
            }
        });

        deleteRecipeButton = findViewById(R.id.trash_can);
        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecipeConfirmation();
            }
        });

        editRecipeButton = findViewById(R.id.specific_edit_button);
        editRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Data.setTempEditingRecipe(recipe);
                Intent intent1 = new Intent(Specific_Recipe_Item.this, Recipe_Book_Add_Activity.class);
                startActivity(intent1);
            }
        });

        preptime = findViewById(R.id.dummy_preptime);
        desc = findViewById(R.id.dummy_description);
        ingred = findViewById(R.id.dummy_ingredients);
        instruc = findViewById(R.id.dummy_instructions);
        title = findViewById(R.id.title);
        photos = findViewById(R.id.dummy_image_placeholder);
        photosText = findViewById(R.id.photos_text_specific);

        loadTextViews();
        loadImageView();

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPosition++;

                if(photoPosition >= photoList.size()){
                    photoPosition = 0;
                }

                Picasso.get().load(photoList.get(photoPosition)).into(photos);

            }
        });

    }//onCreate

    private void loadImageView() {
        photoList = new ArrayList<>();
        photoPosition = 0;

        if(recipe.getImagePath1()== null && recipe.getImagePath2() == null && recipe.getImagePath3() == null){
            photos.setVisibility(View.GONE);
            photosText.setVisibility(View.GONE);

        }

        else{photos.setVisibility(View.VISIBLE);}

        if(recipe.getImagePath1() != null){
            photoList.add(recipe.getImagePath1()); }

        if(recipe.getImagePath2() != null){
            photoList.add(recipe.getImagePath2()); }

        if(recipe.getImagePath3() != null){
            photoList.add(recipe.getImagePath3()); }

            //-----------------------------------------------------

        if(photoList.size() > 0){
            Picasso.get().load(photoList.get(photoPosition)).into(photos);
        }


    }

    private void loadTextViews() {
        title.setText(recipe.getTitle());
        preptime.setText(recipe.getPrepTime());
        desc.setText(recipe.getDescription());
        ingred.setText(recipe.getIngredients());
        instruc.setText(recipe.getInstructions());
    }

    public static void importCurrentRecipe(Recipe importedRecipe){
        recipe = importedRecipe;
    }

    public void deleteRecipeConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Are you sure you want to delete this recipe?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference deleteRecipe = FirebaseDatabase.getInstance().getReference(User_Data.getEmail()).child("Recipes").child(recipe.title);
                        deleteRecipe.removeValue();
                        startActivity(new Intent(Specific_Recipe_Item.this, Recipe_Book_Main.class));

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

}
