package com.onlinehocam.ozel.ders.okul;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Arrays;

class CustomIBANNoFormattingTextWatcher implements TextWatcher
{
    Context context;
    EditText editText;
    boolean isChecking;
    String previousEditTextString;

    public CustomIBANNoFormattingTextWatcher(Context context, EditText editText) {
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
            GlobalVariables.Log(context, "onTextChanged  isChecking = true");
            isChecking = false;
            return;
        }
        else
        {
            GlobalVariables.Log(context, "onTextChanged  isChecking = false");
        }

        GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher"
                + " CharSequence: " + s.toString() + "  stringLength: " + s.toString().length() );
        String newS = s.toString();

        String first2 = "TR";

        String text = s.toString();
        int length = text.length();
        int[] digits;

        //GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher CharSequence s, int start, int before, int count: "
        //        + s.toString() +" X " + start +" X " + before +" X " + count);


        if(length == 1)
        {
            GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher first digit 0: "+newS);
            newS = first2 + text;

            isChecking = true;
            editText.setText(newS);
            int position = newS.length();
            Editable etext = editText.getText();
            Selection.setSelection(etext, position);

            GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher first digit 1: "+newS);
        }
        else
        {
            if(newS.length() == 4)
            {
                GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else returned: "+newS);
                return;
            }
            GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else beginning: "+newS);
            digits = CreateDigitsArray(text);
            newS = FillDigitsAsPatternedString(digits, first2);
            int position = newS.length();

            isChecking = true;
            editText.setText(newS);
            Editable etext = editText.getText();
            Selection.setSelection(etext, position);

            GlobalVariables.Log(context, "CustomPhoneNumberFormattingTextWatcher else: "+newS+ Arrays.toString(digits));
        }

        try
        {

        }
        catch (Exception ex)
        {
            GlobalVariables.Log(context, "EXCEPTION IN CustomPhoneNumberFormattingTextWatcher: " + ex.getMessage());
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    private String FillDigitsAsPatternedString(int[] digits, String first2)
    {
        String result = first2;

        for (int i = 0; i < digits.length; i++)
        {
            if(i == 2 || i == 7 || i == 8)
            {
                result += " " + digits[i];
            }
            else
            {
                result += digits[i];
            }
        }
        return result;
    }


    int CountDigits(String text)
    {
        int count = 0;
        for(int i = 0; i < text.length(); i++)
        {
            if((""+text.charAt(i)).matches("[0-9]+"))
            {
                count++;
            }
        }
        if(count < 0)
        {
            return -1;
        }
        else if(count >= 24)
        {
            return 24;
        }
        return count;
    }

    int[] CreateDigitsArray(String text)
    {
        int[] result;
        int digitCountedValue = CountDigits(text);
        if (digitCountedValue == -1)
        {
            result = new int[1];

            for(int i = 0; i < text.length(); i++)
            {
                if((""+text.charAt(i)).matches("[0-9]+"))
                {
                    result[0] = Integer.parseInt((""+text.charAt(i)));
                    break;
                }
            }
        }
        else
        {
            result = new int[digitCountedValue];
            int numberCount = 0;

            for(int i = 0; i < text.length(); i++)
            {
                if((""+text.charAt(i)).matches("[0-9]+"))
                {
                    result[numberCount] = Integer.parseInt((""+text.charAt(i)));

                    numberCount++;
                }


                if(numberCount == 24)
                {
                    break;
                }
            }
        }
        GlobalVariables.Log(context, "digits: "+Arrays.toString(result));
        return result;
    }
}

