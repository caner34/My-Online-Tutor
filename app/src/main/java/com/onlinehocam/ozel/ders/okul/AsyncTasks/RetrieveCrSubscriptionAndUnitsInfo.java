package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RetrieveCrSubscriptionAndUnitsInfo  extends AsyncTask<String, Void, String> {

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";

    Activity activity;
    Context context;
    LinearLayout linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, linearLayoutQuestionRequestUnitsInfoSubPanel, linearLayoutQuestionRequestUnitsFree;
    TextView textViewSubscriptionInfo, textViewQuestionRequestUnitsFree, textViewQuestionRequestUnitsPurchased;


    String resultString;



    public RetrieveCrSubscriptionAndUnitsInfo(Activity activity, Context context, LinearLayout linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, LinearLayout linearLayoutQuestionRequestUnitsInfoSubPanel, LinearLayout linearLayoutQuestionRequestUnitsFree, TextView textViewSubscriptionInfo, TextView textViewQuestionRequestUnitsFree, TextView textViewQuestionRequestUnitsPurchased) {
        this.activity = activity;
        this.context = context;
        this.linearLayoutRemainingUnitsAndSubscriptionsInfoPanel = linearLayoutRemainingUnitsAndSubscriptionsInfoPanel;
        this.linearLayoutQuestionRequestUnitsInfoSubPanel = linearLayoutQuestionRequestUnitsInfoSubPanel;
        this.linearLayoutQuestionRequestUnitsFree = linearLayoutQuestionRequestUnitsFree;
        this.textViewSubscriptionInfo = textViewSubscriptionInfo;
        this.textViewQuestionRequestUnitsFree = textViewQuestionRequestUnitsFree;
        this.textViewQuestionRequestUnitsPurchased = textViewQuestionRequestUnitsPurchased;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        /*if (type.equals(KEY_AddTutorToFavorites))
        {
            return Execute_BooleanQuery(type, params);
        }*/
        resultString = Execute_BooleanQuery(type, params);
        return resultString;
    }

    private String Execute_BooleanQuery(String type, String... params) {
        String crUrl = KEY_URL_START + type + KEY_URL_END;
        try {
            URL url = new URL(crUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, KEY_ENCODING_OUTPUT));


            String post_data = GetPostDataFromParams(KEY_ENCODING_OUTPUT, params);

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, KEY_ENCODING_INPUT));

            String result = "";
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "failed due to MalformedURLException";
        } catch (IOException e) {
            e.printStackTrace();
            return "failed due to IOException";
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    static String GetPostDataFromParams(String encoding, String... params) {
        StringBuilder sb = new StringBuilder();
        String crLine = "";


        for (int i = 0; i < ((params.length - 1) / 2); i++) {
            crLine = "";
            try {
                if (i != 0) {
                    crLine = "&";
                }
                crLine += URLEncoder.encode(params[1 + 2 * i], encoding) + "=" + URLEncoder.encode(params[1 + 2 * i + 1], encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "failedPostData";
            }

            sb.append(crLine);
        }

        return sb.toString();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

        final String SEPERATOR = ";";

        if(resultString.isEmpty())
        {
            DisplayUnableToDisplayUnitAndSubscriptionInfoMessage();
            return;
        }

        if(resultString.contains(SEPERATOR))
        {
            if(resultString.split(SEPERATOR).length == 4)
            {
                String question_request_units_str = resultString.split(SEPERATOR)[0];
                String free_question_request_units_str = resultString.split(SEPERATOR)[1];
                int question_request_units = TryParsingStringToInt(question_request_units_str);
                int free_question_request_units = TryParsingStringToInt(free_question_request_units_str);
                if(question_request_units == -1 || free_question_request_units == -1)
                {
                    DisplayUnableToDisplayUnitAndSubscriptionInfoMessage();
                    return;
                }

                String expirationDate_Str = resultString.split(SEPERATOR)[2];
                String remainingTime_Str = resultString.split(SEPERATOR)[3];
                DisplayUnitAndSubscriptionInfo(question_request_units, free_question_request_units, expirationDate_Str, remainingTime_Str);
            }
            else
            {
                DisplayUnableToDisplayUnitAndSubscriptionInfoMessage();
                return;
            }
        }
        else
        {
            DisplayUnableToDisplayUnitAndSubscriptionInfoMessage();
            return;
        }
    }

    int TryParsingStringToInt(String numberStr)
    {
        try
        {
            int result = Integer.parseInt(numberStr);
            return result;
        }
        catch (Exception ex)
        {
            return -1;
        }
    }

    void DisplayUnableToDisplayUnitAndSubscriptionInfoMessage()
    {
        textViewSubscriptionInfo.setText(R.string.student_purchase_unable_to_retrieve_your_unit_and_subscription_data);
    }

    void DisplayUnitAndSubscriptionInfo(int question_request_units, int free_question_request_units, String expirationDate_Str, String remainingTime_Str)
    {
        linearLayoutQuestionRequestUnitsInfoSubPanel.setVisibility(View.VISIBLE);
        if(question_request_units != 0)
        {
            linearLayoutQuestionRequestUnitsFree.setVisibility(View.GONE);
        }

        textViewQuestionRequestUnitsFree.setText(context.getString(R.string.student_purchase_free_question_request_units) + ": " + free_question_request_units);
        textViewQuestionRequestUnitsPurchased.setText(context.getString(R.string.student_purchase_question_request_units) + ": " + question_request_units);

        String remainingTimeMessage = context.getString(R.string.student_purchase_remaining_time_for_subscription);
        String expirationTimeMessage = context.getString(R.string.student_purchase_expiration_time_for_subscription) + " " + expirationDate_Str;

        final String TAG_EXPIRED = "expired";
        final String TAG_NO_SUBSCRIPTION_YET = "no_subscription_yet";

        if(expirationDate_Str.equals(TAG_NO_SUBSCRIPTION_YET))
        {
            textViewSubscriptionInfo.setText(R.string.student_purchase_no_subscription);
            return;
        }

        if(remainingTime_Str.equals(TAG_EXPIRED))
        {
            textViewSubscriptionInfo.setText(R.string.student_purchase_subscription_expired);
            return;
        }

        if(remainingTime_Str.equals(TAG_EXPIRED))
        {
            textViewSubscriptionInfo.setText(R.string.student_purchase_no_subscription);
            return;
        }

        if(remainingTime_Str.contains(" ") && remainingTime_Str.split(" ").length==2
                && (remainingTime_Str.split(" ")[0]).contains("-") && (remainingTime_Str.split(" ")[0]).split("-").length == 3
                && (remainingTime_Str.split(" ")[1]).contains(":") && (remainingTime_Str.split(" ")[1]).split(":").length == 3)
        {
            List<Integer> remainingTimeComponents = new ArrayList<>();
            String[] timeIntervalTypeNames = new String[]{ "yıl", "ay", "gün", "saat", "dakika", "saniye" };
            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[0]).split("-")[0] ));
            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[0]).split("-")[1] ));
            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[0]).split("-")[2] ));

            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[1]).split(":")[0] ));
            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[1]).split(":")[1] ));
            remainingTimeComponents.add(Integer.parseInt( (remainingTime_Str.split(" ")[1]).split(":")[2] ));

            boolean isRemainingTimeVerbalizationStarted = false;

            for (int i = 0; i < remainingTimeComponents.size(); i++)
            {
                if(!(remainingTimeComponents.get(i)==0 && !isRemainingTimeVerbalizationStarted))
                {
                    if(isRemainingTimeVerbalizationStarted)
                    {
                        remainingTimeMessage += ", ";
                    }
                    remainingTimeMessage += " " + remainingTimeComponents.get(i) + " " + timeIntervalTypeNames[i];
                }
            }

            textViewSubscriptionInfo.setText(remainingTimeMessage + "\n" + expirationTimeMessage);
        }
        else
        {
            textViewSubscriptionInfo.setText(expirationTimeMessage);
        }
    }
}