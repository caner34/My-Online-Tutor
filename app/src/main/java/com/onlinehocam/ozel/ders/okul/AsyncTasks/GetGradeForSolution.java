package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.CustomToast;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.ServerHelper;
import com.onlinehocam.ozel.ders.okul.SoundHelper;
import com.onlinehocam.ozel.ders.okul.StudentPurchasedSolutionsActivity;

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

public class GetGradeForSolution extends AsyncTask<String, Void, String> {

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";

    Activity activity;
    Context context;
    LinearLayout linearLayoutMainProgressBar;
    float rating;
    int userId;
    int questionRequestID;
    Button buttonGradeSolutions;
    RatingBar ratingBar;
    TextView texViewYourRating;
    boolean isToDisplayProgressBarOnPreExecute = false;
    boolean isToDismissProgressBarOnPostExecute = false;


    String resultString;


    public GetGradeForSolution(Activity activity, Context context, Button buttonGradeSolutions, RatingBar ratingBar, TextView texViewYourRating, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.activity = activity;
        this.context = context;
        this.linearLayoutMainProgressBar = ((StudentPurchasedSolutionsActivity)activity).linearLayoutMainProgressBar;
        this.buttonGradeSolutions = buttonGradeSolutions;
        this.ratingBar = ratingBar;
        this.texViewYourRating = texViewYourRating;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
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
        if (isToDisplayProgressBarOnPreExecute)
        {
            linearLayoutMainProgressBar.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onPostExecute(String result) {

        GlobalVariables.Log(context, "GetGradeForSolution >>> resultString: "+resultString);

        double rating = -1.0;
        try
        {
            rating = Double.parseDouble(resultString);

            if(rating == -1.0) // not rated yet
            {
                buttonGradeSolutions.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
            }
            else if(rating == -1.5)
            {
                // do nothing due to error

                //buttonGradeSolutions.setVisibility(View.GONE);
                //ratingBar.setVisibility(View.GONE);
            }
            else
            {
                ratingBar.setRating(((float)rating));
                ratingBar.setEnabled(false);
                texViewYourRating.setVisibility(View.VISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception ex)
        {
            // do nothing due to error
        }



        if (isToDismissProgressBarOnPostExecute)
        {
            linearLayoutMainProgressBar.setVisibility(View.GONE);
        }
    }
}