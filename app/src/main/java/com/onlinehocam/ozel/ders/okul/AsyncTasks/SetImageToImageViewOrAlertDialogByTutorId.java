package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class SetImageToImageViewOrAlertDialogByTutorId  extends AsyncTask<String,Void,String > {
    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";


    LinearLayout linearLayoutMainProgressBar;
    Activity activity;
    Context context;
    int tutorID;
    ImageView imageView;
    String imageDataAddressID;
    String DIR;
    boolean isToDisplayProgressBarOnPreExecute = false;
    boolean isToDismissProgressBarOnPostExecute = false;


    /*String[] booleanQueryPhpFileNames = new String[]{
            KEY_RemoveTutorFromFavorites,
            KEY_AddTutorToFavorites
    };*/

    public SetImageToImageViewOrAlertDialogByTutorId(Context context, int tutorID, ImageView imageView, String DIR) {
        this.context = context;
        this.tutorID = tutorID;
        this.imageView = imageView;
        this.DIR = DIR;
    }

    public SetImageToImageViewOrAlertDialogByTutorId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int tutorID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.activity = activity;
        this.context = context;
        this.tutorID = tutorID;
        this.DIR = DIR;
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
        imageDataAddressID = Execute_BooleanQuery(type, params);
        return imageDataAddressID;
    }

    private String Execute_BooleanQuery(String type, String... params)
    {
        String crUrl = KEY_URL_START + type + KEY_URL_END;
        try
        {
            URL url = new URL(crUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
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

            while ((line = bufferedReader.readLine()) != null)
            {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return "failed due to MalformedURLException";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "failed due to IOException";
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    static String GetPostDataFromParams(String encoding, String... params)
    {
        StringBuilder sb = new StringBuilder();
        String crLine = "";


        for(int i = 0; i < ((params.length-1)/2); i++)
        {
            crLine = "";
            try
            {
                if(i != 0)
                {
                    crLine = "&";
                }
                crLine += URLEncoder.encode(params[1 + 2*i], encoding)+"="+URLEncoder.encode(params[1 + 2*i + 1], encoding);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return "failedPostData";
            }

            sb.append(crLine);
        }

        return sb.toString();
    }

    @Override
    protected void onPreExecute() {
        if(isToDisplayProgressBarOnPreExecute)
            linearLayoutMainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(photoDataAddressId);
        //GlobalVariables.AlertDialogDisplay(activity, photoDataAddressId);

        if(imageView != null) // It means we need to set the image to imageView
        {
            if(!result.isEmpty())
            {
                if(DIR.equals(RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS))
                {
                    AsyncTaskHelper.RetrieveTutorPhotoFromDirectoryAndSetToImageView(context, imageView, imageDataAddressID, DIR);
                }
                else if(DIR.equals(RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES))
                {
                    AsyncTaskHelper.RetrieveQuestionImageFromDirectoryAndSetToImageView(context, imageView, imageDataAddressID, DIR);
                }
            }
        }
        else if(activity != null) // It means we need to display the image on Alert Dialog
        {
            if(!result.isEmpty())
            {
                if(DIR.equals(RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS))
                {
                    AsyncTaskHelper.RetrieveTutorPhotoFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, activity, context, imageDataAddressID, DIR, false, true);
                }
                else if(DIR.equals(RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES))
                {
                    AsyncTaskHelper.RetrieveQuestionImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, activity, context, imageDataAddressID, DIR, false, true);
                }
            }
        }

        if(isToDismissProgressBarOnPostExecute)
            linearLayoutMainProgressBar.setVisibility(View.GONE);
    }
}

