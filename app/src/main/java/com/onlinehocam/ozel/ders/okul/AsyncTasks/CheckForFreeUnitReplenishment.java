package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.StudentHomePageActivity;

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

class CheckForFreeUnitReplenishment  extends AsyncTask<String, Void, String> {

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";

    Activity activity;
    Context context;
    LinearLayout linearLayoutFreeUnitReplenishmentPanel;
    TextView freeUnitCoinNumber;
    TextView replenishmentTimeRemaining;

    String resultString;


    public CheckForFreeUnitReplenishment(Activity activity, Context context, LinearLayout linearLayoutFreeUnitReplenishmentPanel, TextView freeUnitCoinNumber, TextView replenishmentTimeRemaining) {
        this.activity = activity;
        this.context = context;
        this.linearLayoutFreeUnitReplenishmentPanel = linearLayoutFreeUnitReplenishmentPanel;
        this.freeUnitCoinNumber = freeUnitCoinNumber;
        this.replenishmentTimeRemaining = replenishmentTimeRemaining;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

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

        FREE_REPLENISHMENT_CHECK_RESPONSE_STATE replenishmentResponseState = TurnResultStringIntoReplenishmentResponseState(resultString);

        GlobalVariables.Log(context, "AsyncTaskHelper.CheckForFreeUnitReplenishment() >>> onPostExecute >>> resultString: "+resultString);

        if(freeUnitCoinNumber.getText().toString().isEmpty())
        {
            freeUnitCoinNumber.setText(""+Integer.parseInt(resultString.split(";")[0]));
            linearLayoutFreeUnitReplenishmentPanel.setVisibility(View.VISIBLE);
        }

        if(replenishmentResponseState == FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.HAS_PURCHASED_UNITS)
        {
            linearLayoutFreeUnitReplenishmentPanel.setVisibility(View.INVISIBLE);
            ((StudentHomePageActivity)activity).StopReplenishmentCounter();
            freeUnitCoinNumber.setText(""+Integer.parseInt(resultString.split(";")[0]));
        }
        else if(replenishmentResponseState == FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.MAX_REPLENISHMENT_REACHED)
        {
            linearLayoutFreeUnitReplenishmentPanel.setVisibility(View.INVISIBLE);
            ((StudentHomePageActivity)activity).StopReplenishmentCounter();
            freeUnitCoinNumber.setText(""+Integer.parseInt(resultString.split(";")[0]));
        }
        else if(replenishmentResponseState == FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.REPLENISHMENT_NOT_YET)
        {
            if(linearLayoutFreeUnitReplenishmentPanel.getVisibility() != View.VISIBLE)
            {
                linearLayoutFreeUnitReplenishmentPanel.setVisibility(View.VISIBLE);
            }
            if ( ((StudentHomePageActivity)activity).isCounterActive == null || !((StudentHomePageActivity)activity).isCounterActive.equals(new Boolean(true)))
            {
                ActivateCounter();
            }
        }
        else if(replenishmentResponseState == FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.REPLENISHMENT_SUCCESSFUL)
        {
            if(AsyncTaskHelper.TIMES_THE_REPLENISHMENT_FAILED != 0)
            {
                AsyncTaskHelper.TIMES_THE_REPLENISHMENT_FAILED = 0;
            }
            freeUnitCoinNumber.setText(""+Integer.parseInt(resultString.split(";")[0]));
            if ( ((StudentHomePageActivity)activity).isCounterActive == null || !((StudentHomePageActivity)activity).isCounterActive.equals(new Boolean(true)))
            {
                ActivateCounter();
            }
        }
        else if(replenishmentResponseState == FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.REPLENISHMENT_FAILED)
        {
            AsyncTaskHelper.TIMES_THE_REPLENISHMENT_FAILED++;
            ((StudentHomePageActivity)activity).remainingSecondsForCheck += (int)(Math.pow((double) 2, (double) AsyncTaskHelper.TIMES_THE_REPLENISHMENT_FAILED) * AsyncTaskHelper.CONS_REPLENISHMENT_COUNTER_REMAINING_TIME_DELAY_ADDITION_IN_CASE_OF_REPLENISHMENT_FAILED);
            if ( ((StudentHomePageActivity)activity).isCounterActive == null || !((StudentHomePageActivity)activity).isCounterActive.equals(new Boolean(true)))
            {
                ActivateCounter();
            }
        }
    }

    void ActivateCounter()
    {
        ((StudentHomePageActivity)activity).isCounterActive = true;
        ((StudentHomePageActivity)activity).freeUnitReplenishmentTimeInSeconds = (int)(Double.parseDouble(resultString.split(";")[3]) * 60);
        ((StudentHomePageActivity)activity).remainingSecondsForCheck
                = ((StudentHomePageActivity)activity).freeUnitReplenishmentTimeInSeconds
                - (Integer.parseInt(resultString.split(";")[2]) % ((StudentHomePageActivity)activity).freeUnitReplenishmentTimeInSeconds ) + AsyncTaskHelper.CONS_REPLENISHMENT_COUNTER_REMAINING_TIME_USUAL_DELAY_ADDITION;

        freeUnitCoinNumber.setText(""+Integer.parseInt(resultString.split(";")[0]));
        ((StudentHomePageActivity)activity).StartReplenishmentCounter();
    }

    String[] FREE_REPLENISHMENT_CHECK_RESPONSE_TERMS = new String[]
    {
            "has purchased units",
            "max replenishment reached",
            "replenishment not yet",
            "replenishment successful",
            "replenishment failed"
    };

    private FREE_REPLENISHMENT_CHECK_RESPONSE_STATE TurnResultStringIntoReplenishmentResponseState(String resultString)
    {
        for(int i = 0; i < FREE_REPLENISHMENT_CHECK_RESPONSE_TERMS.length; i++)
        {
            if(resultString.contains(FREE_REPLENISHMENT_CHECK_RESPONSE_TERMS[i]))
            {
                return FREE_REPLENISHMENT_CHECK_RESPONSE_STATE.values()[i];
            }
        }
        return null;
    }

    enum FREE_REPLENISHMENT_CHECK_RESPONSE_STATE
    {
        HAS_PURCHASED_UNITS,
        MAX_REPLENISHMENT_REACHED,
        REPLENISHMENT_NOT_YET,
        REPLENISHMENT_SUCCESSFUL,
        REPLENISHMENT_FAILED
    }
}