package com.onlinehocam.ozel.ders.okul;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.Selection;
import android.widget.EditText;
import java.util.Arrays;


public class CustomPhoneNumberFormattingTextWatcher extends PhoneNumberFormattingTextWatcher
{
    Context context;
    EditText editText;
    boolean isChecking;
    String previousEditTextString;

    public CustomPhoneNumberFormattingTextWatcher(Context context, EditText editText) {
        super();
        this.context = context;
        this.editText = editText;
        this.isChecking = false;
        previousEditTextString = "";
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        if(isChecking)
        {
            isChecking = false;
            return;
        }
        else
        {

        }

        String newS = s.toString();

        String first5 = "+90 (";

        String text = s.toString();
        int length = text.length();
        int[] digits;

        try
        {
            if(length == 1)
            {
                newS = first5 + text;

                isChecking = true;
                editText.setText(newS);
                int position = newS.length();
                Editable etext = editText.getText();
                Selection.setSelection(etext, position);

            }
            else
            {
                if(newS.length() == 6)
                {
                    return;
                }
                digits = CreateDigitsArray(text);
                newS = FillDigitsAsPatternedString(digits, first5);
                int position = newS.length();

                isChecking = true;
                editText.setText(newS);
                Editable etext = editText.getText();
                Selection.setSelection(etext, position);
            }
        }
        catch (Exception ex)
        {
            //GlobalVariables.Log(context, "EXCEPTION IN CustomPhoneNumberFormattingTextWatcher: " + ex.getMessage());
        }
    }

    private String FillDigitsAsPatternedString(int[] digits, String first5)
    {
        String result = first5;

        for (int i = 0; i < digits.length; i++)
        {

            if(i == 0 || i == 1 || i == 2 || i == 4 || i == 5 || i == 7 || i == 9)
            {
                result += digits[i];
            }
            else if(i == 3)
            {
                result += ") " + digits[i];
            }
            else if(i == 6 || i == 8)
            {
                result += " " + digits[i];
            }
        }
        return result;
    }


    int CountDigitsExceptAreaCode(String text)
    {
        int count = 0;
        for(int i = 0; i < text.length(); i++)
        {
            if((""+text.charAt(i)).matches("[0-9]+"))
            {
                count++;
            }
        }
        count -= 2;
        if(count < 0)
        {
            return -1;
        }
        else if(count >= 10)
        {
            return 10;
        }
        return count;
    }

    int[] CreateDigitsArray(String text)
    {
        int[] result;
        int digitCountedValue = CountDigitsExceptAreaCode(text);
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
                    if(numberCount >1)
                    result[numberCount-2] = Integer.parseInt((""+text.charAt(i)));

                    numberCount++;
                }


                if(numberCount == 12)
                {
                    break;
                }
            }

        }

        return result;
    }
}

