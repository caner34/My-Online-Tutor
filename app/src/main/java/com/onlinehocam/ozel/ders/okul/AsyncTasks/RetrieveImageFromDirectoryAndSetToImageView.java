package com.onlinehocam.ozel.ders.okul.AsyncTasks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onlinehocam.ozel.ders.okul.CustomPurchasedSolutionsLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomQuestionAnswerLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomSolutionOnSaleForPopularRecommendedLinearLayout;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.QuestionRequest;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.ServerHelper;
import com.onlinehocam.ozel.ders.okul.SolutionOnSale;
import com.onlinehocam.ozel.ders.okul.Tutor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class RetrieveImageFromDirectoryAndSetToImageView extends AsyncTask<String, Void, Void>
{

    public static final String DIR_USER_PHOTOS = "user-photos";
    public static final String DIR_MINI_USER_PHOTOS = "mini-user-photos";
    public static final String DIR_QUESTION_IMAGES = "question-images";

    LinearLayout linearLayoutMainProgressBar;
    LinearLayout linearLayoutMainMenu;
    LinearLayout linearLayoutMainPanelMyFavoriteTutors;
    Activity activity;
    Context context;
    ImageView imageView;
    String DIR;
    String imageDataAddressID;
    Tutor tutor;
    boolean isToDisplayProgressBarOnPreExecute = false;
    boolean isToDismissProgressBarOnPostExecute = false;
    ImageView imageViewPhoto;

    Bitmap bitmap;

    SolutionOnSale solutionOnSale;
    CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout;

    CustomPurchasedSolutionsLinearLayout customPurchasedSolutionsLinearLayout;

    QuestionRequest questionRequest;
    CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout;

    public RetrieveImageFromDirectoryAndSetToImageView(Context context, ImageView imageView, String DIR, String photoDataAddressID) {
        this.context = context;
        this.imageView = imageView;
        this.DIR = DIR;
        this.imageDataAddressID = photoDataAddressID;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.solutionOnSale = solutionOnSale;
        this.customSolutionOnSaleForPopularRecommendedLinearLayout = customSolutionOnSaleForPopularRecommendedLinearLayout;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomPurchasedSolutionsLinearLayout customPurchasedSolutionsLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.solutionOnSale = solutionOnSale;
        this.customPurchasedSolutionsLinearLayout = customPurchasedSolutionsLinearLayout;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, QuestionRequest questionRequest, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.questionRequest = questionRequest;
        this.customQuestionAnswerLinearLayout = customQuestionAnswerLinearLayout;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(Context context, ImageView imageView, String DIR, String photoDataAddressID, Tutor tutor) {
        this.context = context;
        this.imageView = imageView;
        this.DIR = DIR;
        this.imageDataAddressID = photoDataAddressID;
        this.tutor = tutor;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, Activity activity, Context context, String imageDataAddressID, Tutor tutor, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.tutor = tutor;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    public RetrieveImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutMainMenu, LinearLayout linearLayoutMainPanelMyFavoriteTutors, Activity activity, Context context, String imageDataAddressID, Tutor tutor, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute) {
        this.linearLayoutMainProgressBar = linearLayoutMainProgressBar;
        this.linearLayoutMainMenu = linearLayoutMainMenu;
        this.linearLayoutMainPanelMyFavoriteTutors = linearLayoutMainPanelMyFavoriteTutors;
        this.activity = activity;
        this.context = context;
        this.DIR = DIR;
        this.imageDataAddressID = imageDataAddressID;
        this.tutor = tutor;
        this.isToDisplayProgressBarOnPreExecute = isToDisplayProgressBarOnPreExecute;
        this.isToDismissProgressBarOnPostExecute = isToDismissProgressBarOnPostExecute;
    }

    @Override
    protected Void doInBackground(String... strings)
    {
        String URL_BASE = "http://134.209.234.180/OHFileUpload";
        String EXTENSION = "jpg";

        try
        {
            if(imageDataAddressID.isEmpty())
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
        if(linearLayoutMainMenu != null && linearLayoutMainPanelMyFavoriteTutors != null)
        {
            linearLayoutMainMenu.setVisibility(View.GONE);
            linearLayoutMainPanelMyFavoriteTutors.setVisibility(View.GONE);
        }

        if(isToDisplayProgressBarOnPreExecute)
            linearLayoutMainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if(activity == null)  // It means we need to set the image to imageView
        {
            if (tutor == null)
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
                    if(tutor.gender.equals(GlobalVariables.GENDER_MALE))
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
            if (tutor == null)
            {
                if(customSolutionOnSaleForPopularRecommendedLinearLayout != null)
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.question_display_watch_solution), context.getString(R.string.constant_ok), solutionOnSale, customSolutionOnSaleForPopularRecommendedLinearLayout, bitmap);
                }
                else if(customPurchasedSolutionsLinearLayout != null)
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.question_display_watch_solution), context.getString(R.string.constant_ok), solutionOnSale, customPurchasedSolutionsLinearLayout, bitmap);
                }
                else if(customQuestionAnswerLinearLayout != null)
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.question_display_watch_solution), context.getString(R.string.constant_ok), questionRequest, customQuestionAnswerLinearLayout, bitmap);
                }
                else
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false, "", "", context.getString(R.string.question_display_watch_solution), context.getString(R.string.constant_ok), bitmap);
                }
            }
            else
            {
                if(bitmap != null)
                {
                    GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"","", context.getString(R.string.constant_ok), "", bitmap);
                }
                else
                {
                    if(tutor.gender.equals(GlobalVariables.GENDER_MALE))
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


        if(linearLayoutMainMenu != null && linearLayoutMainPanelMyFavoriteTutors != null)
        {
            linearLayoutMainMenu.setVisibility(View.VISIBLE);
            linearLayoutMainPanelMyFavoriteTutors.setVisibility(View.VISIBLE);
        }
    }
}
