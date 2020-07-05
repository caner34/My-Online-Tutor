package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.CustomQuestionAnswerLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomTutorDiscoveryFeedInjector;
import com.onlinehocam.ozel.ders.okul.CustomTutorLinearLayout;
import com.onlinehocam.ozel.ders.okul.DB;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.LessonsAndClasses;
import com.onlinehocam.ozel.ders.okul.QuestionRequest;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.StudentPostQuestionActivity;
import com.onlinehocam.ozel.ders.okul.Tutor;

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
import java.util.Arrays;
import java.util.List;

public class SetHandleAndDisplayGetTutorByTutorID extends AsyncTask<String, Void, Void>
{

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";

    Activity activity;
    LinearLayout linearLayoutMainProgressBar;
    LinearLayout linearLayoutMainMenu;
    LinearLayout linearLayoutMainPanelMyFavoriteTutors;
    Context context;
    int tutorID;
    public CustomTutorDiscoveryFeedInjector customTutorDiscoveryFeedInjector;

    String resultString;

    String photo_data_address_id;
    ImageView imageViewTutorPhoto;


    public SetHandleAndDisplayGetTutorByTutorID(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutMainMenu, LinearLayout linearLayoutMainPanelMyFavoriteTutors, int tutorID, CustomTutorDiscoveryFeedInjector customTutorDiscoveryFeedInjector) {
        this.activity = activity;
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.linearLayoutMainMenu = linearLayoutMainMenu;
        this.linearLayoutMainPanelMyFavoriteTutors = linearLayoutMainPanelMyFavoriteTutors;
        this.context = context;
        this.tutorID = tutorID;
        this.customTutorDiscoveryFeedInjector = customTutorDiscoveryFeedInjector;
    }

    @Override
    protected Void doInBackground(String... params)
    {
        String type = params[0];
        resultString =  Execute_BooleanQuery(type, params);

        return null;
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
    protected void onPreExecute() {

    }


    @Override
    protected void onPostExecute(Void aVoid) {
        Tutor tutor = GetTutorByResultString(resultString);

        //GlobalVariables.Log(context, "on SetHandleAndDisplayGetTutorByTutorID.java >>> resultString: "+resultString);

        CustomTutorLinearLayout customTutorLinearLayout = customTutorDiscoveryFeedInjector.GetCustomTutorLinearLayoutByTutorIDFromList(tutorID);
        UpdateTutorData(tutor, customTutorLinearLayout);

        AsyncTaskHelper.RetrieveTutorPhotoFromDirectoryAndSetToImageView(context, imageViewTutorPhoto, photo_data_address_id, tutor, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS);
        final Tutor fTutor = tutor;
        imageViewTutorPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskHelper.RetrieveTutorPhotoFromDirectoryAndSetToImageView(linearLayoutMainProgressBar,linearLayoutMainMenu, linearLayoutMainPanelMyFavoriteTutors, activity, context, photo_data_address_id, fTutor, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS, true, true);
            }
        });
    }

    private void UpdateTutorData(Tutor t, CustomTutorLinearLayout customTutorLinearLayout)
    {
        UpdateTutorObjectInList(t);

        LinearLayout child = (LinearLayout) (customTutorLinearLayout.getChildAt(0));
        ImageView imageViewPhoto = (ImageView) (child.findViewById(R.id.imageViewPhoto));
        TextView textViewTutorName = child.findViewById(R.id.textViewTutorName);
        TextView textViewRating = child.findViewById(R.id.textViewRating);
        TextView textViewLastSchool = child.findViewById(R.id.textViewLastSchool);
        final LinearLayout linearLayoutCommentsFeed = child.findViewById(R.id.linearLayoutCommentsFeed);
        TextView textViewEducationField = child.findViewById(R.id.textViewEducationField);
        Button buttonAddToFavorites = child.findViewById(R.id.buttonAddToFavorites);
        final Button buttonShowComments = child.findViewById(R.id.buttonShowComments);
        final Button buttonHideComments = child.findViewById(R.id.buttonHideComments);
        final TextView textViewCommentsWarning = child.findViewById(R.id.textViewCommentsWarning);
        final ProgressBar progressBarCommentWaiting = child.findViewById(R.id.progressBarCommentWaiting);
        boolean isFavorite = false;

        //final CustomTutorLinearLayout result = new CustomTutorLinearLayout(context, child, t.userID);

        imageViewTutorPhoto = imageViewPhoto;


        textViewTutorName.setText(t.userName);
        textViewRating.setText("" + t.tutorRating);
        if(t.tutorRating == -1.0)
        {
            textViewRating.setText("N/A");
        }
        textViewLastSchool.setText(t.lastSchoolName);
        textViewEducationField.setText(t.educationField);

        buttonAddToFavorites.setEnabled(true);
        buttonShowComments.setEnabled(true);

        //Handle The Button Add To Favorites
        if(customTutorDiscoveryFeedInjector.usersFavoriteTutorIDsList.contains(t.userID))
        {
            isFavorite = true;
        }
        else
        {
            isFavorite = false;
        }
    }

    private void UpdateTutorObjectInList(Tutor t)
    {
        //GlobalVariables.Log(context, "on SetHandleAndDisplayGetTutorByTutorID.java in UpdateTutorObjectInList t.userID: "+t.userID);
        String tutorIDs = "";
        for( Tutor crT : customTutorDiscoveryFeedInjector.tutorsList)
        {
            tutorIDs += ""+crT.userID+", ";
        }
        //GlobalVariables.Log(context, "on SetHandleAndDisplayGetTutorByTutorID.java in UpdateTutorObjectInList customTutorDiscoveryFeedInjector.tutorsList.size(): "+customTutorDiscoveryFeedInjector.tutorsList.size() + "   tutorIDs: "+tutorIDs);
        Tutor tutorInList = null;
        for(int i = 0; i < customTutorDiscoveryFeedInjector.tutorsList.size(); i++)
        {
            if(customTutorDiscoveryFeedInjector.tutorsList.get(i).userID == t.userID)
            {
                tutorInList = customTutorDiscoveryFeedInjector.tutorsList.get(i);
            }
        }


        tutorInList.userID = t.userID;
        tutorInList.userName = t.userName;
        tutorInList.gender = t.gender;
        tutorInList.photoID = t.photoID;
        tutorInList.tutorRating = t.tutorRating;
        tutorInList.answersDisplayedTimes = t.answersDisplayedTimes;
        tutorInList.lastSchoolName = t.lastSchoolName;
        tutorInList.educationField = t.educationField;
        tutorInList.isFavorite = t.isFavorite;
    }

    private Tutor GetTutorByResultString(String resultString) {
        Tutor result = null;
        String[] resultRawArray;
        if (!resultString.isEmpty() && resultString.contains(DB.COLUMN_SEPARATOR)) {
            resultString = resultString.substring(0, resultString.length() - DB.COLUMN_SEPARATOR.length());
            resultRawArray = resultString.split(DB.COLUMN_SEPARATOR);

            int userID;
            String userName;
            String gender;
            int photoID;
            Bitmap photoImage;
            double tutorRating;
            int answersDisplayedTimes;
            String lastSchoolName;
            String educationField;
            int[] mostRecentCommentIDsArray;
            int[] allCommentIDsArray;
            boolean isFavorite;
            String classSelection;


            //GlobalVariables.Log(context, "resultString: "+ resultString);
            //GlobalVariables.Log(context, "resultRawArray: "+ Arrays.toString(resultRawArray));
            userID = Integer.parseInt(resultRawArray[0]);
            userName = resultRawArray[1];
            gender = resultRawArray[2];
            photoID = Integer.parseInt(resultRawArray[3]);
            photoImage = null;
            tutorRating = Double.parseDouble(resultRawArray[4]);
            answersDisplayedTimes = Integer.parseInt(resultRawArray[5]);
            lastSchoolName = resultRawArray[6];
            educationField = resultRawArray[7];
            classSelection = resultRawArray[8];
            mostRecentCommentIDsArray = new int[0];
            allCommentIDsArray = new int[0];
            isFavorite = customTutorDiscoveryFeedInjector.usersFavoriteTutorIDsList.contains(userID);

            photo_data_address_id = resultRawArray[9];
            if(photo_data_address_id.equals(DB.CONS_EMPTY))
            {
                photo_data_address_id = "";
            }


            //TODO fill an async method that fills mostRecentCommentIDsArray and allCommentIDsArray on the Tutor object
            // in customTutorDiscoveryFeedInjector.tutorsList


            result = new Tutor(userID, userName, gender, photoID, photoImage, tutorRating, answersDisplayedTimes, lastSchoolName, educationField, mostRecentCommentIDsArray, allCommentIDsArray, isFavorite, classSelection);
        }
        return result;
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

}
