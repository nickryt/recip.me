package com.example.recipme;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static android.support.constraint.Constraints.TAG;

public class Ingredients_Fragment extends Fragment {

    EditText ingredientsTextInput;

    //INTERFACE TO SEND DATA TO ADD_ACTIVITY
    public interface OnInputListenerIngF{
        void sendInput(String ingredients, String nullString);
    }

    public OnInputListenerIngF mOnInputListener;

    public Ingredients_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ingredientsTextInput = v.findViewById(R.id.editingredients);

        if(User_Data.getTempEditingRecipe() != null){
            ingredientsTextInput.setText(User_Data.getTempEditingRecipe().getIngredients());
            mOnInputListener.sendInput(ingredientsTextInput.getText().toString(), null);
        }



        ingredientsTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputListener.sendInput(ingredientsTextInput.getText().toString(), null);
            }
        });

        return v;
    }//OnCreateView

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListenerIngF) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

}//Ingredients_Fragment

