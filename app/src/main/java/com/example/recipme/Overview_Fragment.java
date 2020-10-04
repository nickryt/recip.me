package com.example.recipme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

public class Overview_Fragment extends Fragment {

    //ADD PHOTO IMAGEBUTTONS
    ImageButton image1;
    ImageButton image2;
    ImageButton image3;
    ImageButton clickedButton;

    //TEXTFIELDS
    EditText titleTextInput;
    EditText prepTimeTextInput;
    EditText descriptionTextInput;

    //DATABASE OBJECTS
    Database firebaseLink = new Database();
    private static final int PICK_IMAGE = 2;
    Uri imageUri;

    //INTERFACE TO SEND DATA TO ADD_ACTIVITY
    public interface OnInputListenerOF{
        void sendInput(String title, String preptime, String description);
    }

    public OnInputListenerOF mOnInputListener;

    public Overview_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_overview, container, false);
        //INITIALIZE PHOTO BUTTONS
        image1 = v.findViewById(R.id.addimage1);
        image2 = v.findViewById(R.id.addimage2);
        image3 = v.findViewById(R.id.addimage3);
        //INITIALIZE TEXT INPUT FIELDS
        titleTextInput = v.findViewById(R.id.edittitle);
        prepTimeTextInput = v.findViewById(R.id.editpreptime);
        descriptionTextInput = v.findViewById(R.id.editdescription);

        if(User_Data.getTempEditingRecipe() != null){
            titleTextInput.setText(User_Data.getTempEditingRecipe().getTitle());
            prepTimeTextInput.setText(User_Data.getTempEditingRecipe().getPrepTime());
            descriptionTextInput.setText(User_Data.getTempEditingRecipe().getDescription());

            mOnInputListener.sendInput(titleTextInput.getText().toString(), prepTimeTextInput.getText().toString(), descriptionTextInput.getText().toString());

            if(User_Data.getTempEditingRecipe().imagePath1 != null){
                Picasso.get().load(User_Data.getTempEditingRecipe().getImagePath1()).into(image1);
            }

            if(User_Data.getTempEditingRecipe().imagePath2 != null){
                Picasso.get().load(User_Data.getTempEditingRecipe().getImagePath2()).into(image2);
            }

            if(User_Data.getTempEditingRecipe().imagePath3 != null){
                Picasso.get().load(User_Data.getTempEditingRecipe().getImagePath3()).into(image3);
            }

        }



        //UPLOAD IMAGE LISTENERS
        image1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openGallery(image1);
              setSelectedButton(image1);
            }
         });


        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(image2);
                setSelectedButton(image2);

            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(image3);
                setSelectedButton(image3);
            }
        });

        //TEXTCHANGE LISTENERS
        titleTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputListener.sendInput(titleTextInput.getText().toString(), prepTimeTextInput.getText().toString(), descriptionTextInput.getText().toString());
            }
        });

        prepTimeTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputListener.sendInput(titleTextInput.getText().toString(), prepTimeTextInput.getText().toString(), descriptionTextInput.getText().toString());
            }
        });

        descriptionTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputListener.sendInput(titleTextInput.getText().toString(), prepTimeTextInput.getText().toString(), descriptionTextInput.getText().toString());
            }
        });

        return v;
    }//On Create View

    private void openGallery(ImageButton button){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }//openGallery

    //START ACTIVITY FOR SELECTING UPLOAD BUTTON
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            firebaseLink.uploadFile(imageUri);

            //INSTANTLY SET IMAGEURI SO USER CAN VIEW IN FRAGMENT
            if(clickedButton == image1){
                image1.setImageURI(imageUri);
            }
            if(clickedButton == image2){
                image2.setImageURI(imageUri);
            }
            if(clickedButton == image3){
                image3.setImageURI(imageUri);
            }

            //WAIT 5 SECONDS UNTIL SETTING INFORMATION IN USER_DATA; FIREBASE DELAY WITH UPLOADING NEEDS BUFFER TIME
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if(clickedButton == image1){
                        Log.d("URL FROM FRAGMENT : \n", "" + User_Data.tempImageLink + " ||| Button #1");
                        User_Data.setImages(1);
                    }
                    if(clickedButton == image2){
                        Log.d("URL FROM FRAGMENT : \n", "" + User_Data.tempImageLink + " ||| Button #2");
                        User_Data.setImages(2);
                    }
                    if(clickedButton == image3){
                        Log.d("URL FROM FRAGMENT : \n", "" + User_Data.tempImageLink + " ||| Button #3");
                        User_Data.setImages(3);
                    }
                }
            },2000);

        }
        else {Log.d("Linking : \nFAILED\n", " ");}
    }//onActivityResult

    //HELPER FOR ONACTIVITYRESULT
    public void setSelectedButton(ImageButton hitButton){
        clickedButton = hitButton;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListenerOF) getActivity();
        }
        catch(ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }


    }

}//Overview Fragment
