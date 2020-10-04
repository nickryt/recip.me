package com.example.recipme;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class Database extends AppCompatActivity {

    //DATABASE OBJECTS
    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    public DatabaseReference reff;
    public DatabaseReference createRecipe;
    public String imageUrl;

    public void createUserID(String user_name, String email, String password, String c_e_d){

    String filteredEmail = email.replaceAll("[^A-Za-z0-9@ ]", "");

            reff = firebaseDatabase.getReference().child(filteredEmail);
            User_Data.setEmail(filteredEmail);

            reff.child("User Data").child("Name").setValue(user_name);
            reff.child("User Data").child("Email").setValue(email);
            reff.child("User Data").child("Password").setValue(password);
            reff.child("User Data").child("Cooking Experience Description").setValue(c_e_d);

    }//createUserID

    public void createNewRecipe(String title, String prepTime, String description, String ingredients, String instructions){

        createRecipe = firebaseDatabase.getReference().child(User_Data.getEmail());
        createRecipe.child("Recipes").child(title).child("Instructions").setValue(instructions);
        createRecipe.child("Recipes").child(title).child("Ingredients").setValue(ingredients);
        createRecipe.child("Recipes").child(title).child("Description").setValue(description);
        createRecipe.child("Recipes").child(title).child("Prep Time").setValue(prepTime);
        createRecipe.child("Recipes").child(title).child("Title").setValue(title);

        Log.d("FILE LINKED IN DATABASE : \n", "Url : " + User_Data.imagePath1);
        Log.d("FILE LINKED IN DATABASE : \n", "Url : " + User_Data.imagePath2);
        Log.d("FILE LINKED IN DATABASE : \n", "Url : " + User_Data.imagePath3);

        createRecipe.child("Recipes").child(title).child("Image1").setValue(User_Data.imagePath1);
        createRecipe.child("Recipes").child(title).child("Image2").setValue(User_Data.imagePath2);
        createRecipe.child("Recipes").child(title).child("Image3").setValue(User_Data.imagePath3);

        User_Data.clearTempImageLink();
        User_Data.clearImages();

    }//createNewRecipe

    public void uploadFile(Uri passedURI){

        if(passedURI != null) {

            //REFERENCES STORAGE AND DATABASE PATHS
            String randomID = UUID.randomUUID().toString();
            final StorageReference uploadRef = firebaseStorage.child("/images/" + User_Data.getName() + "/" + randomID +   ".jpeg");

            final UploadTask uploadTask = uploadRef.putFile(passedURI);

            uploadTask
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful()){
                                        throw task.getException(); }
                                    imageUrl = uploadRef.getDownloadUrl().toString();
                                    return uploadRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    imageUrl = task.getResult().toString();
                                    User_Data.setTempImageLink(imageUrl);
                                    Log.d("INITIAL FILE UPLOADED\n", "Image Url : " + imageUrl);
                                }
                            });

                        }//onSuccess
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("File Upload : \nFAILED\n", " ");
                        }//onFailure
                    });
        }//If
    }//Upload File






}//Database