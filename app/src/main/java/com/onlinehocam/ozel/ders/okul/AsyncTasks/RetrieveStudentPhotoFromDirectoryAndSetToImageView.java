package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onlinehocam.ozel.ders.okul.CommonUtils;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.Student;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class RetrieveStudentPhotoFromDirectoryAndSetToImageView extends AsyncTask<String, Void, Void>
{

    public static final String DIR_USER_PHOTOS = "user-photos";
    public static final String DIR_MINI_USER_PHOTOS = "mini-user-photos";
    public static final String DIR_QUESTION_IMAGES = "question-images";

    LinearLayout linearLayoutMainProgressBar;
    Activity activity;
    Context context;
    ImageView imageView;
    String DIR;
    String imageDataAddressID;
    Student student;
    boolean isToDisplayProgressBarOnPreExecute = false;
    boolean isToDismissProgressBarOnPostExecute = false;

    Bitmap bitmap;


    public RetrieveStudentPhotoFromDirectoryAndSetToImageView(Context context, ImageView imageView, String DIR, Student student) {
        this.context = context;
        this.imageView = imageView;
        this.DIR = DIR;
        this.student = student;
    }

    public RetrieveStudentPhotoFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, Activity activity, Context context, Student student, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.student = student;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }


    @Override
    protected Void doInBackground(String... strings)
    {
        //GlobalVariables.Log(context, "on RetrieveStudentPhotoFromDirectoryAndSetToImageView >>> isStudentNull: "+(student==null));
        if(student!=null)
        {
            imageDataAddressID = student.imageDataAddressID;
            //GlobalVariables.Log(context, "on RetrieveStudentPhotoFromDirectoryAndSetToImageView >>> student.gender: " + student.gender + " student.imageDataAddressID: " + student.imageDataAddressID);
        }
        String URL_BASE = "http://134.209.234.180/OHFileUpload";
        String EXTENSION = "jpg";

        try
        {
            if( !(student != null && !student.imageDataAddressID.isEmpty()) )
            {
                this.bitmap = null;
            }
            else
            {
                URL url = new URL(URL_BASE + "/" + DIR + "/" + imageDataAddressID + "." + EXTENSION);
                //GlobalVariables.Log(context, "url: "+url);
                URLConnection connection = url.openConnection();
                connection.connect();
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                this.bitmap = bitmap;
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            this.bitmap = null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.bitmap = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.bitmap = null;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        if(isToDisplayProgressBarOnPreExecute)
            linearLayoutMainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(activity == null)  // It means we need to set the image to imageView
        {
            bitmap = CommonUtils.GetCircleCroppedBitmap(CommonUtils.CreateSquaredBitmap(bitmap));
            if (student == null)
            {
                imageView.setImageBitmap(bitmap);
            }
            else
            {
                if(bitmap != null)
                {
                    imageView.setImageBitmap(bitmap);
                }
                else
                {
                    if(student.gender.equals(GlobalVariables.GENDER_MALE))
                    {
                        imageView.setImageResource(R.drawable.male_template);
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.female_template);
                    }
                }
            }
        }
        else if(activity != null) // It means we need to display the image on Alert Dialog
        {
            if (student == null)
            {
                GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.constant_ok), "", bitmap);
            }
            else
            {
                if(bitmap != null)
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.constant_ok), "", bitmap);
                }
                else
                {
                    if(student.gender.equals(GlobalVariables.GENDER_MALE))
                    {
                        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.male_template);
                    }
                    else
                    {
                        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.female_template);
                    }
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.constant_ok), "", bitmap);
                }
            }
        }



        if(isToDismissProgressBarOnPostExecute)
            linearLayoutMainProgressBar.setVisibility(View.GONE);
    }
}
