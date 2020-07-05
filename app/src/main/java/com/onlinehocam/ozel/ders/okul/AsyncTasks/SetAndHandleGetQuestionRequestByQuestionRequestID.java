package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.onlinehocam.ozel.ders.okul.CustomQuestionAnswerLinearLayout;
import com.onlinehocam.ozel.ders.okul.DB;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.LessonsAndClasses;
import com.onlinehocam.ozel.ders.okul.QuestionRequest;
import com.onlinehocam.ozel.ders.okul.StudentPostQuestionActivity;

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
import java.util.List;

public class SetAndHandleGetQuestionRequestByQuestionRequestID extends AsyncTask<String, Void, Void>
{

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";

    LinearLayout linearLayoutMainProgressBar;
    Activity activity;
    Context context;
    int questionRequestID;

    CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout;

    boolean isToDisplayProgressBarOnPreExecute = false;
    boolean isToDismissProgressBarOnPostExecute = false;

    String resultString;

    public SetAndHandleGetQuestionRequestByQuestionRequestID(Context context, int questionRequestID, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout) {
        this.context = context;
        this.questionRequestID = questionRequestID;
        this.customQuestionAnswerLinearLayout = customQuestionAnswerLinearLayout;
    }


    public SetAndHandleGetQuestionRequestByQuestionRequestID(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int questionRequestID, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.activity = activity;
        this.context = context;
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.questionRequestID = questionRequestID;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
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
        if(isToDisplayProgressBarOnPreExecute)
            linearLayoutMainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(customQuestionAnswerLinearLayout != null)
        {
            customQuestionAnswerLinearLayout.crQuestionRequest = ConvertResultStringIntoQuestionRequest();
            customQuestionAnswerLinearLayout.FillAndHandleTheRestOfTheOperationsAfterQuestionRequestRetrieval();
        }
        else if(activity != null)// in StudentPostQuestionActivity
        {
            //GlobalVariables.Log(context, "else if(activity != null)// in StudentPostQuestionActivity >>> if block entered");
            ((StudentPostQuestionActivity)(activity)).inheritedQuestionRequest = ConvertResultStringIntoQuestionRequest();
            //GlobalVariables.Log(context, "((StudentPostQuestionActivity)(activity)).inheritedQuestionRequest: "+(((StudentPostQuestionActivity)(activity)).inheritedQuestionRequest).toString());
            ((StudentPostQuestionActivity)(activity)).HandleTheRestInCaseRePostQuestionAction();
            ((StudentPostQuestionActivity)(activity)).linearLayoutButtonPostQuestionRequest.setVisibility(View.VISIBLE);
        }

        if(isToDismissProgressBarOnPostExecute)
            linearLayoutMainProgressBar.setVisibility(View.GONE);
    }

    QuestionRequest ConvertResultStringIntoQuestionRequest()
    {
        QuestionRequest result = null;
        try
        {
            if(!resultString.isEmpty() && resultString.contains(DB.COLUMN_SEPARATOR))
            {
                String[] resultRawArray;
                resultString = resultString.substring(0, resultString.length() - DB.COLUMN_SEPARATOR.length());
                resultRawArray = resultString.split(DB.COLUMN_SEPARATOR);


                int classIndex = -1;
                int lessonIndex = -1;
                String studentName = "";
                String tutorName = "";
                int questionImageID = -1;
                int pageNo = 0;
                double appreciatedPrice = 0.5;
                double counterPriceDemand = -1.0;
                String youtubeLinkID = "";
                int publisherIndex = -1;
                int bookIndex = -1;
                String lastDeliveryDate = "";
                String dueDateForAcceptance = "";
                int questionRequestStateID = -1;
                int withdrawalExcuseIndex = -1;
                String withdrawalExcuse = "";


                for (int i = 0; i < resultRawArray.length; i++)
                {
                    //GlobalVariables.Log(context, "resultRawArrayData: "+i+": "+ resultRawArray[i]);
                }

                studentName = resultRawArray[1];
                tutorName = resultRawArray[2];
                classIndex = Integer.parseInt(resultRawArray[3]) -1;
                lessonIndex = Integer.parseInt(resultRawArray[4]) -1;
                questionImageID = Integer.parseInt(resultRawArray[5]);
                pageNo = Integer.parseInt(resultRawArray[6]);
                appreciatedPrice = Double.parseDouble(resultRawArray[7]);
                counterPriceDemand = Double.parseDouble(resultRawArray[8]);
                youtubeLinkID = resultRawArray[9];
                publisherIndex = Integer.parseInt(resultRawArray[10]) -1;
                lastDeliveryDate = resultRawArray[11];
                dueDateForAcceptance = resultRawArray[12];
                bookIndex = Integer.parseInt(resultRawArray[13]) -1;
                questionRequestStateID = Integer.parseInt(resultRawArray[14]);
                withdrawalExcuseIndex = Integer.parseInt(resultRawArray[15]);
                if(withdrawalExcuseIndex == -1)
                {
                    withdrawalExcuse = "";
                }
                else
                {
                    withdrawalExcuseIndex--;
                    withdrawalExcuse = context.getString(GlobalVariables.questionRequestWithdrawalExcuses[withdrawalExcuseIndex]);
                }



                GlobalVariables.QUESTION_REQUEST_STATE questionRequestState = null;

                if(questionRequestStateID != -1)
                {
                    questionRequestState = GlobalVariables.QUESTION_REQUEST_STATE.values()[questionRequestStateID-1];
                }
                List<String> stringListBookName = (List<String>) (GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[publisherIndex]));



                String[] data = new String[] { ""+LessonsAndClasses.CLASSES.values()[classIndex],
                        (String) GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex]).get(lessonIndex),
                        studentName, tutorName, ""+questionRequestID, ""+questionImageID, ""+pageNo, ""+appreciatedPrice, ""+counterPriceDemand,
                        youtubeLinkID, ""+LessonsAndClasses.PUBLISHER_NAMES[publisherIndex],
                        stringListBookName.get(bookIndex), lastDeliveryDate, dueDateForAcceptance, ""+questionRequestState, withdrawalExcuse };


                for (int i = 0; i < data.length; i++)
                {
                    //GlobalVariables.Log(context, "data: "+i+": "+ data[i]);
                }

                result = new QuestionRequest(LessonsAndClasses.CLASSES.values()[classIndex],
                        (String) GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex]).get(lessonIndex),
                        studentName, tutorName, questionRequestID, questionImageID, pageNo, appreciatedPrice, counterPriceDemand,
                        youtubeLinkID, LessonsAndClasses.PUBLISHER_NAMES[publisherIndex],
                        stringListBookName.get(bookIndex), lastDeliveryDate, dueDateForAcceptance, questionRequestState, withdrawalExcuse
                );
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //GlobalVariables.Log(context, "Exception on AsyncTasks.SetAndHandleGetQuestionRequestByQuestionRequestID >>> questionRequestID: "+ questionRequestID + "   message: " +e.getMessage());
            return result;
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
}
