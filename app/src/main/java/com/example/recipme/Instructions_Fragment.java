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

public class Instructions_Fragment extends Fragment {

    EditText instructionsTextInput;

    public Instructions_Fragment() {

    }

    //INTERFACE TO SEND DATA TO ADD_ACTIVITY
    public interface OnInputListenerInsF{
        void sendInput(String instructions);
    }

    public OnInputListenerInsF mOnInputListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_instructions, container, false);
        instructionsTextInput = v.findViewById(R.id.editinstructions);

        if(User_Data.getTempEditingRecipe() != null){
            instructionsTextInput.setText(User_Data.getTempEditingRecipe().getInstructions());
            mOnInputListener.sendInput(instructionsTextInput.getText().toString());
        }

        instructionsTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mOnInputListener.sendInput(instructionsTextInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnInputListener.sendInput(instructionsTextInput.getText().toString());
            }
        });
        return v;
    }//onCreateView

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListenerInsF) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

}//Instructions_Fragment
