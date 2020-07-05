package com.onlinehocam.ozel.ders.okul;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Arrays;

public class CustomYoutubeLinkFormattingTextWatcher implements TextWatcher
{
    Context context;
    EditText editText;
    boolean isChecking;
    String previousEditTextString;

    public CustomYoutubeLinkFormattingTextWatcher(Context context, EditText editText) {
        super();
        this.context = context;
        this.editText = editText;
        this.isChecking = false;
        previousEditTextString = "";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(isChecking)
        {
            //GlobalVariables.Log(context, "onTextChanged  isChecking = true");
            isChecking = false;
            return;
        }
        else
        {
            //GlobalVariables.Log(context, "onTextChanged  isChecking = false");
        }

        //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher" + " CharSequence: " + s.toString() + "  stringLength: " + s.toString().length() );
        String newS = s.toString();

        String first32 = "https://www.youtube.com/watch?v=";

        String text = s.toString();
        int length = text.length();

        //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher CharSequence s, int start, int before, int count: "
        //        + s.toString() +" X " + start +" X " + before +" X " + count);

        if(length == 1)
        {
            //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher first digit 0: "+newS);
            newS = first32 + text;

            isChecking = true;
            editText.setText(newS);
            int position = newS.length();
            Editable etext = editText.getText();
            Selection.setSelection(etext, position);

            //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher first digit 1: "+newS);
        }
        else
        {
            if(newS.length() == 32)
            {
                //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else returned: "+newS);
                return;
            }
            else if(newS.length() < 32)
            {
                newS = first32;
                int position = newS.length();

                isChecking = true;
                editText.setText(newS);
                Editable etext = editText.getText();
                Selection.setSelection(etext, position);

                //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else: "+newS);
            }
            else if (newS.length() > 43)
            {
                //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else beginning: "+newS);
                newS = newS.substring(0, newS.length()-1);
                int position = newS.length();

                isChecking = true;
                editText.setText(newS);
                Editable etext = editText.getText();
                Selection.setSelection(etext, position);

                //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else: "+newS);
            }
        }

        try
        {

        }
        catch (Exception ex)
        {
            //GlobalVariables.Log(context, "EXCEPTION IN CustomPhoneNumberFormattingTextWatcher: " + ex.getMessage());
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}

