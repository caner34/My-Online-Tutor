package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.onlinehocam.ozel.ders.okul.BackgroundWorker;
import com.onlinehocam.ozel.ders.okul.CommonUtils;
import com.onlinehocam.ozel.ders.okul.CustomPurchasedSolutionsLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomQuestionAnswerLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomSolutionOnSaleForPopularRecommendedLinearLayout;
import com.onlinehocam.ozel.ders.okul.CustomToast;
import com.onlinehocam.ozel.ders.okul.CustomTutorDiscoveryFeedInjector;
import com.onlinehocam.ozel.ders.okul.DB;
import com.onlinehocam.ozel.ders.okul.GlobalVariables;
import com.onlinehocam.ozel.ders.okul.LessonsAndClasses;
import com.onlinehocam.ozel.ders.okul.Marketer;
import com.onlinehocam.ozel.ders.okul.MarketerCommissionLinkDisplayActivity;
import com.onlinehocam.ozel.ders.okul.QuestionRequest;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.ServerHelper;
import com.onlinehocam.ozel.ders.okul.ShareHelper;
import com.onlinehocam.ozel.ders.okul.SolutionOnSale;
import com.onlinehocam.ozel.ders.okul.SoundHelper;
import com.onlinehocam.ozel.ders.okul.Student;
import com.onlinehocam.ozel.ders.okul.Tutor;
import com.onlinehocam.ozel.ders.okul.TutorSolutionPostActivity;

import java.util.Arrays;

public class AsyncTaskHelper
{

    public static void RetrieveTutorPhotoFromDirectoryAndSetToImageView(Context context, ImageView imageView, String photoDataAddressID, String DIR)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(context, imageView, DIR, photoDataAddressID);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveStudentPhotoFromDirectoryAndSetToImageView(Context context, ImageView imageView, String DIR, Student student)
    {
        RetrieveStudentPhotoFromDirectoryAndSetToImageView worker
                = new RetrieveStudentPhotoFromDirectoryAndSetToImageView(context, imageView, DIR, student);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void GetStudentWithImageDataAddressIDAndGender(Context context, ImageView imageView, int studentID, String DIR)
    {
        String type = BackgroundWorker.KEY_GetStudentWithImageDataAddressIDAndGender;
        GetStudentWithImageDataAddressIDAndGender worker
                = new GetStudentWithImageDataAddressIDAndGender(context, imageView, studentID, DIR);
        try
        {
            worker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+studentID,
                    DB.DIRECTORY, ""+ DIR
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.GetStudentWithImageDataAddressIDAndGender: "+ e.getMessage());
        }
    }

    public static void RetrieveTutorPhotoFromDirectoryAndSetToImageView(Context context, ImageView imageView, String photoDataAddressID, Tutor t, String DIR)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(context, imageView, DIR, photoDataAddressID, t);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveTutorPhotoFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, activity, context, imageDataAddressID, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveTutorPhotoFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutMainMenu, LinearLayout linearLayoutMainPanelMyFavoriteTutors, Activity activity, Context context, String imageDataAddressID, Tutor tutor, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, linearLayoutMainMenu, linearLayoutMainPanelMyFavoriteTutors, activity, context, imageDataAddressID, tutor, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveQuestionImageFromDirectoryAndSetToImageView(Context context, ImageView imageView, String photoDataAddressID, String DIR)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(context, imageView, DIR, photoDataAddressID);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveQuestionImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, activity, context, imageDataAddressID, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveQuestionImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, solutionOnSale, customSolutionOnSaleForPopularRecommendedLinearLayout, activity, context, imageDataAddressID, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveQuestionImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomPurchasedSolutionsLinearLayout customPurchasedSolutionsLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, solutionOnSale, customPurchasedSolutionsLinearLayout, activity, context, imageDataAddressID, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void RetrieveQuestionImageFromDirectoryAndSetToImageView(LinearLayout linearLayoutMainProgressBar, QuestionRequest questionRequest, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout, Activity activity, Context context, String imageDataAddressID, String DIR, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        RetrieveImageFromDirectoryAndSetToImageView worker
                = new RetrieveImageFromDirectoryAndSetToImageView(linearLayoutMainProgressBar, questionRequest, customQuestionAnswerLinearLayout, activity, context, imageDataAddressID, DIR, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute();
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.RetrieveImageFromDirectoryAndSetToImageView: "+ e.getMessage());
        }
    }

    public static void DisplayTutorPhotoOnAlertDialogByQuestionRequestId(Context context, int questionRequestId, ImageView imageView)
    {
        String type = BackgroundWorker.KEY_GetTutorPhotoDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(context, questionRequestId, imageView, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayQuestionImageOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayQuestionImageOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, solutionOnSale, customSolutionOnSaleForPopularRecommendedLinearLayout, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayQuestionImageOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomPurchasedSolutionsLinearLayout customPurchasedSolutionsLinearLayout, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, solutionOnSale, customPurchasedSolutionsLinearLayout, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayQuestionImageOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, QuestionRequest questionRequest, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, questionRequest, customQuestionAnswerLinearLayout, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }


/*
    public static void DisplayTutorPhotoOnAlertDialogByTutorID(Context context, int tutorID, ImageView imageView)
    {
        String type = BackgroundWorker.KEY_GetTutorPhotoDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByTutorId worker
                = new SetImageToImageViewOrAlertDialogByTutorId(context, tutorID, imageView, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS);
        try
        {
            worker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+tutorID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void SetImageToImageViewOrAlertDialogByTutorId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int tutorID, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByTutorId;
        SetImageToImageViewOrAlertDialogByTutorId worker
                = new SetImageToImageViewOrAlertDialogByTutorId(activity, context, linearLayoutMainProgressBar, tutorID, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+tutorID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }
*/


    public static void DisplayTutorPhotoOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetTutorPhotoDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayTutorPhotoOnAlertDialogByQuestionRequestId(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, SolutionOnSale solutionOnSale, CustomSolutionOnSaleForPopularRecommendedLinearLayout customSolutionOnSaleForPopularRecommendedLinearLayout, int questionRequestId, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_GetTutorPhotoDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(activity, context, linearLayoutMainProgressBar, solutionOnSale, customSolutionOnSaleForPopularRecommendedLinearLayout, questionRequestId, RetrieveImageFromDirectoryAndSetToImageView.DIR_USER_PHOTOS, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void DisplayQuestionImageOnAlertDialogByQuestionRequestId(Context context, int questionRequestId, ImageView imageView)
    {
        String type = BackgroundWorker.KEY_GetQuestionImageDataAddressIdByQuestionRequestId;
        SetImageToImageViewOrAlertDialogByQuestionRequestId worker
                = new SetImageToImageViewOrAlertDialogByQuestionRequestId(context, questionRequestId, imageView, RetrieveImageFromDirectoryAndSetToImageView.DIR_QUESTION_IMAGES);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }





    public static void SetAndHandleGetQuestionRequestByQuestionRequestID(Context context, int questionRequestId, CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout)
    {String type = BackgroundWorker.KEY_GetQuestionRequestByQuestionRequestID;
        SetAndHandleGetQuestionRequestByQuestionRequestID worker
                = new SetAndHandleGetQuestionRequestByQuestionRequestID(context, questionRequestId, customQuestionAnswerLinearLayout);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestId
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }

    public static void SetAndHandleGetQuestionRequestByQuestionRequestID(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, int questionRequestID, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {String type = BackgroundWorker.KEY_GetQuestionRequestByQuestionRequestID;
        SetAndHandleGetQuestionRequestByQuestionRequestID worker
                = new SetAndHandleGetQuestionRequestByQuestionRequestID(activity, context, linearLayoutMainProgressBar, questionRequestID, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId: "+ e.getMessage());
        }
    }



    public static void SetHandleAndDisplayGetTutorByTutorID(Activity activity, Context context, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutMainMenu, LinearLayout linearLayoutMainPanelMyFavoriteTutors, int tutorID, CustomTutorDiscoveryFeedInjector customTutorDiscoveryFeedInjector)
    {
        String type = BackgroundWorker.KEY_GetTutorByTutorID;
        SetHandleAndDisplayGetTutorByTutorID worker
                = new SetHandleAndDisplayGetTutorByTutorID(activity, context, linearLayoutMainProgressBar, linearLayoutMainMenu, linearLayoutMainPanelMyFavoriteTutors, tutorID, customTutorDiscoveryFeedInjector);
        try
        {
            worker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+tutorID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.SetHandleAndDisplayGetTutorByTutorID: "+ e.getMessage());
        }
    }



    public static void InsertSelectedClassIDForTutor(Context context, int tutorID, int selectedClassID)
    {
        String type = BackgroundWorker.KEY_InsertSelectedClassIDForTutor;
        InsertSelectedClassIDForTutor worker
                = new InsertSelectedClassIDForTutor(context, tutorID, selectedClassID);
        try
        {
            worker.execute(type,
                    DB.TutorClassSelectionsTable.COL_0_USER_ID, ""+tutorID,
                    DB.TutorClassSelectionsTable.COL_1_SELECTED_CLASS_ID, ""+selectedClassID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.SetHandleAndDisplayGetTutorByTutorID: "+ e.getMessage());
        }
    }

    public static void UploadUserPhotoToDirectory(Activity activity, Context context, Tutor tutor, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadUserPhotoToDirectory;
        UploadUserPhotoToDirectory worker
                = new UploadUserPhotoToDirectory(activity, context, tutor, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserPhotosDataTable.COL_0_PHOTO_ID, ""+tutor.photoID,
                    DB.UserPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void UploadUserPhotoToDirectory(Activity activity, Context context, Student student, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadUserPhotoToDirectory;
        UploadUserPhotoToDirectory worker
                = new UploadUserPhotoToDirectory(activity, context, student, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                //GlobalVariables.Log(context, "on UploadMiniUserPhotoToDirectory >>> (photoBitmap == null): true");
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    //GlobalVariables.Log(context, "on UploadMiniUserPhotoToDirectory >>> (bytes == null): true");
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserPhotosDataTable.COL_0_PHOTO_ID, ""+student.photoID,
                    DB.UserPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void UploadUserPhotoToDirectory(Activity activity, Context context, Marketer marketer, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadUserPhotoToDirectory;
        UploadUserPhotoToDirectory worker
                = new UploadUserPhotoToDirectory(activity, context, marketer, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserPhotosDataTable.COL_0_PHOTO_ID, ""+marketer.photoID,
                    DB.UserPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }


    public static void UploadMiniUserPhotoToDirectory(Activity activity, Context context, Tutor tutor, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadMiniUserPhotoToDirectory;
        UploadMiniUserPhotoToDirectory worker
                = new UploadMiniUserPhotoToDirectory(activity, context, tutor, photoBitmap, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedThumbnailBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserMiniPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserMiniPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }


    public static void UploadMiniUserPhotoToDirectory(Activity activity, Context context, Marketer marketer, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadMiniUserPhotoToDirectory;
        UploadMiniUserPhotoToDirectory worker
                = new UploadMiniUserPhotoToDirectory(activity, context, marketer, photoBitmap, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedThumbnailBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserMiniPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserMiniPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void UploadMiniUserPhotoToDirectory(Activity activity, Context context, Student student, Bitmap photoBitmap, String photo_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadMiniUserPhotoToDirectory;
        UploadMiniUserPhotoToDirectory worker
                = new UploadMiniUserPhotoToDirectory(activity, context, student, photoBitmap, photo_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String photo_data;
            if(photoBitmap == null)
            {
                //GlobalVariables.Log(context, "on UploadMiniUserPhotoToDirectory >>> (photoBitmap == null): true");
                photo_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapToCompressedThumbnailBytesArray(photoBitmap, context);
                if(bytes == null)
                {
                    //GlobalVariables.Log(context, "on UploadMiniUserPhotoToDirectory >>> (bytes == null): true");
                    photo_data = "";
                }
                else
                {
                    photo_data = Base64.encodeToString(bytes, Base64.DEFAULT);
                }
            }
            worker.execute(type,
                    DB.UserMiniPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, ""+photo_data_address_id,
                    DB.UserMiniPhotosTable.COL_1_PHOTO_DATA, ""+photo_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void UploadQuestionImageToDirectory(Activity activity, Context context, QuestionRequest questionRequest, Bitmap imageBitmap, String image_data_address_id, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonPostQuestionRequest, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_UploadQuestionImageToDirectory;
        UploadQuestionImageToDirectory worker
                = new UploadQuestionImageToDirectory(activity, context, questionRequest, image_data_address_id, linearLayoutMainProgressBar, linearLayoutButtonPostQuestionRequest, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            byte[] bytes;
            String image_data;
            /*
            if(imageBitmap == null)
            {
                image_data = "";
            }
            else
            {
                bytes = CommonUtils.ConvertBitmapIntoByteArray(imageBitmap, GlobalVariables.MAXIMUM_IMAGE_SIZE_IN_BYTES, context);
                image_data = Base64.encodeToString(bytes, Base64.DEFAULT);
            }*/


            bytes = CommonUtils.ConvertBitmapToCompressedBytesArray(imageBitmap, context);
            if(bytes == null)
            {
                image_data = "";
            }
            else
            {
                image_data = Base64.encodeToString(bytes, Base64.DEFAULT);
            }

            worker.execute(type,
                    DB.QuestionImagesDataTable.COL_1_IMAGE_DATA_ADDRESS_ID, ""+image_data_address_id,
                    DB.QuestionImagesTable.COL_1_IMAGE_DATA, ""+image_data
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadQuestionImageToDirectory: "+ e.getMessage());
        }
    }


    public static void RegisterUserToServer(Activity activity, Context context, Student student, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_RegisterStudentToServer;
        RegisterUserToServer worker
                = new RegisterUserToServer(activity, context, student, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+student.userID,
                    DB.StudentsTable.COL_1_GENDER, ""+student.gender,
                    DB.StudentsTable.COL_2_PHOTO_ID, ""+student.photoID,
                    DB.StudentsTable.COL_3_EDUCATIONAL_ATTAINMENT, ""+student.educationalAttainmentID,
                    DB.StudentsTable.COL_4_LAST_SCHOOL_NAME, student.lastSchool,
                    DB.StudentsTable.COL_5_EDUCATION_FIELD, student.educationField,
                    DB.StudentsTable.COL_6_REAL_NAME, student.name,
                    DB.StudentsTable.COL_7_REAL_SURNAME, student.surname,
                    DB.StudentsTable.COL_8_BIRTH_DATE, student.birthDate,
                    DB.StudentsTable.COL_9_CITY_OF_RESIDENCY, ""+student.cityOfResidency,
                    DB.StudentsTable.COL_10_DISTRICT_OF_RESIDENCY, ""+student.districtOfResidency,
                    DB.StudentsTable.COL_11_CITY_OF_REGISTRY, ""+student.cityOfRegistry,
                    DB.StudentsTable.COL_12_DISTRICT_OF_REGISTRY, ""+student.districtOfRegistry,
                    DB.StudentsTable.COL_13_PHONE_NUMBER, student.phoneNumber,
                    DB.StudentsTable.COL_14_CLASS_SELECTIONS, student.classSelections
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }



    public static void RegisterUserToServer(Activity activity, Context context, Tutor tutor, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_RegisterTutorToServer;
        RegisterUserToServer worker
                = new RegisterUserToServer(activity, context, tutor, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+tutor.userID,
                    DB.TutorsTable.COL_1_GENDER, ""+tutor.gender,
                    DB.TutorsTable.COL_2_PHOTO_ID, ""+tutor.photoID,
                    DB.TutorsTable.COL_3_AVERAGE_TUTOR_RATING, ""+-1.0,
                    DB.TutorsTable.COL_4_ANSWERS_DISPLAYED_TIMES, ""+0,
                    DB.TutorsTable.COL_5_LAST_SCHOOL_NAME, tutor.lastSchoolName,
                    DB.TutorsTable.COL_6_EDUCATION_FIELD, tutor.educationField,
                    DB.TutorsTable.COL_7_REAL_NAME, tutor.realName,
                    DB.TutorsTable.COL_8_REAL_SURNAME, tutor.realSurname,
                    DB.TutorsTable.COL_9_BIRTH_DATE, tutor.birthDate,
                    DB.TutorsTable.COL_10_CITY_OF_RESIDENCY, ""+tutor.cityOfResidency,
                    DB.TutorsTable.COL_11_DISTRICT_OF_RESIDENCY, ""+tutor.districtOfResidency,
                    DB.TutorsTable.COL_12_CITY_OF_REGISTRY, ""+tutor.cityOfRegistry,
                    DB.TutorsTable.COL_13_DISTRICT_OF_REGISTRY, ""+tutor.districtOfRegistry,
                    DB.TutorsTable.COL_14_PHONE_NUMBER, tutor.phoneNumber,
                    DB.TutorsTable.COL_15_IBAN_NO, tutor.ibanNo,
                    DB.TutorsTable.COL_16_CLASS_SELECTIONS, tutor.classSelections
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void RegisterUserToServer(Activity activity, Context context, Marketer marketer, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonRegisterUser, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        String type = BackgroundWorker.KEY_RegisterMarketerToServer;
        RegisterUserToServer worker
                = new RegisterUserToServer(activity, context, marketer, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);
        try
        {
            worker.execute(type,
                    DB.MarketersTable.COL_0_USER_ID, ""+marketer.userID,
                    DB.MarketersTable.COL_1_GENDER, ""+marketer.gender,
                    DB.MarketersTable.COL_2_PHOTO_ID, ""+marketer.photoID,
                    DB.MarketersTable.COL_3_LAST_SCHOOL_NAME, marketer.lastSchoolName,
                    DB.MarketersTable.COL_4_EDUCATION_FIELD, marketer.educationField,
                    DB.MarketersTable.COL_5_REAL_NAME, marketer.realName,
                    DB.MarketersTable.COL_6_REAL_SURNAME, marketer.realSurname,
                    DB.MarketersTable.COL_7_BIRTH_DATE, marketer.birthDate,
                    DB.MarketersTable.COL_8_CITY_OF_RESIDENCY, ""+marketer.cityOfResidency,
                    DB.MarketersTable.COL_9_DISTRICT_OF_RESIDENCY, ""+marketer.districtOfResidency,
                    DB.MarketersTable.COL_10_CITY_OF_REGISTRY, ""+marketer.cityOfRegistry,
                    DB.MarketersTable.COL_11_DISTRICT_OF_REGISTRY, ""+marketer.districtOfRegistry,
                    DB.MarketersTable.COL_12_PHONE_NUMBER, marketer.phoneNumber,
                    DB.MarketersTable.COL_13_IBAN_NO, marketer.ibanNo,
                    DB.MarketersTable.COL_14_MARKETING_FIELD_SELECTIONS, marketer.marketingFieldSelections
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
        }
    }

    public static void PostQuestionRequest(Activity activity, Context context, QuestionRequest questionRequest, LinearLayout linearLayoutMainProgressBar, LinearLayout linearLayoutButtonPostQuestionRequest, boolean isToDisplayProgressBarOnPreExecute, boolean isToDismissProgressBarOnPostExecute)
    {
        try
        {
            String type = BackgroundWorker.KEY_PostQuestionRequest;
            PostQuestionRequest backgroundWorker = new PostQuestionRequest(activity, context, questionRequest, linearLayoutMainProgressBar, linearLayoutButtonPostQuestionRequest, isToDisplayProgressBarOnPreExecute, isToDismissProgressBarOnPostExecute);

            int classIndex = -1;
            int lessonIndex = -1;
            int pageNo = 0;
            int publisherIndex = -1;
            int bookIndex = -1;
            int questionRequestStateID = -1;

            classIndex = Arrays.asList(LessonsAndClasses.CLASSES.values()).indexOf(questionRequest.questionClass) +1;
            lessonIndex = GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex-1]).indexOf(questionRequest.questionLesson) +1;
            pageNo = questionRequest.pageNo;
            publisherIndex = Arrays.asList(LessonsAndClasses.PUBLISHER_NAMES).indexOf(questionRequest.publisher) +1;
            bookIndex = GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[publisherIndex-1]).indexOf(questionRequest.bookName) +1;
            questionRequestStateID = Arrays.asList(GlobalVariables.QUESTION_REQUEST_STATE.values()).indexOf(questionRequest.questionRequestState) +1;


            int studentID = -1;
            int tutorID = -1;

            if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_TUTOR))
            {
                tutorID = GlobalVariables.USER_ID;
                studentID = ServerHelper.GetUserIDByUserName(questionRequest.demandingStudentName);
            }
            else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT))
            {
                tutorID = ServerHelper.GetUserIDByUserName(questionRequest.demandedTutorName);
                studentID = GlobalVariables.USER_ID;
            }

            //GlobalVariables.Log(context, "tutorID: "+tutorID + "  studentID: "+studentID);

            if((GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT) && studentID == -1) || tutorID == -1)
            {
                SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.CONNECTION_FAILED);
                new CustomToast(activity, context, context.getString(R.string.post_question_response_failed));
                ((TutorSolutionPostActivity)activity).isSolutionPostedSuccessfully = false;

                linearLayoutMainProgressBar.setVisibility(View.GONE);
                linearLayoutButtonPostQuestionRequest.setVisibility(View.VISIBLE);
                return;
            }


            //GlobalVariables.Log(context, "questionRequest.questionImageID: "+questionRequest.questionImageID);



            backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequest.questionRequestID,
                    DB.QuestionRequestsTable.COL_1_STUDENT_ID, ""+studentID,
                    DB.QuestionRequestsTable.COL_2_TUTOR_ID, ""+tutorID,
                    DB.QuestionRequestsTable.COL_3_CLASS_ID, ""+classIndex,
                    DB.QuestionRequestsTable.COL_4_LESSON_ID, ""+lessonIndex,
                    DB.QuestionRequestsTable.COL_5_QUESTION_IMAGE_ID, ""+questionRequest.questionImageID,
                    DB.QuestionRequestsTable.COL_6_PAGE_NO, ""+pageNo,
                    DB.QuestionRequestsTable.COL_7_APPRECIATED_PRICE, ""+questionRequest.appreciatedPrice,
                    DB.QuestionRequestsTable.COL_8_COUNTER_PRICE_DEMAND, ""+0.0,
                    DB.QuestionRequestsTable.COL_9_SOLUTION_VIDEO_URL_ID, ""+questionRequest.solutionVideoUrlID,
                    DB.QuestionRequestsTable.COL_10_PUBLISHER_ID, ""+publisherIndex,
                    DB.QuestionRequestsTable.COL_11_BOOK_ID, ""+bookIndex,
                    DB.QuestionRequestsTable.COL_12_LAST_DELIVERY_DATE, ""+questionRequest.lastDeliveryDate,
                    DB.QuestionRequestsTable.COL_13_DUE_DATE_FOR_ACCEPTANCE, ""+ questionRequest.dueDateForAcceptance,
                    DB.QuestionRequestsTable.COL_14_QUESTION_REQUEST_STATE_ID, ""+questionRequestStateID,
                    DB.QuestionRequestsTable.COL_15_WITHDRAWAL_EXCUSE_ID, ""+-1
            );
        }
        catch (Exception ex)
        {
            //GlobalVariables.Log(context, "EXCEPTION on PostQuestionRequest.AsyncTaskHelper >>> "+ex.getMessage());
        }
    }


    public static int TIMES_THE_REPLENISHMENT_FAILED = 0;
    public static final int CONS_REPLENISHMENT_COUNTER_REMAINING_TIME_USUAL_DELAY_ADDITION = 0;
    public static final int CONS_REPLENISHMENT_COUNTER_REMAINING_TIME_DELAY_ADDITION_IN_CASE_OF_REPLENISHMENT_FAILED = 60;
    public static void CheckForFreeUnitReplenishment(Activity activity, Context context, int studentID, LinearLayout linearLayoutFreeUnitReplenishmentPanel, TextView freeUnitCoinNumber, TextView replenishmentTimeRemaining)
    {
        String type = BackgroundWorker.KEY_CheckForFreeUnitReplenishment;
        CheckForFreeUnitReplenishment worker
                = new CheckForFreeUnitReplenishment(activity, context, linearLayoutFreeUnitReplenishmentPanel, freeUnitCoinNumber, replenishmentTimeRemaining);
        try
        {
            worker.execute(type,
                    DB.UnitsTable.COL_0_STUDENT_ID, ""+studentID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.CheckFreeUnitReplenishmentOnServer: "+ e.getMessage());
        }
    }

    public static void GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, ShareHelper.SOCIAL_MEDIA_PLATFORMS social_media_platforms, String generatedUUIDStr)
    {
        String type = BackgroundWorker.KEY_GenerateDisplayCommissionLink;
        GenerateDisplayCommissionLink worker
                = new GenerateDisplayCommissionLink(activity, context, userId, isSelectedCLinkIDWithUserName, social_media_platforms);
        try
        {
            worker.execute(type,
                    DB.MarketersTable.COL_0_USER_ID, ""+userId,
                    DB.isSelectedCLinkIDWithUserName, ""+isSelectedCLinkIDWithUserName,
                    DB.generatedMarketerReferenceID, ""+generatedUUIDStr
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.CheckFreeUnitReplenishmentOnServer: "+ e.getMessage());
        }
    }

    public static void GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, TextView textViewCommissionLink, String generatedUUIDStr)
    {
        String type = BackgroundWorker.KEY_GenerateDisplayCommissionLink;
        GenerateDisplayCommissionLink worker
                = new GenerateDisplayCommissionLink(activity, context, userId, isSelectedCLinkIDWithUserName, textViewCommissionLink);
        try
        {
            worker.execute(type,
                    DB.MarketersTable.COL_0_USER_ID, ""+userId,
                    DB.isSelectedCLinkIDWithUserName, ""+isSelectedCLinkIDWithUserName,
                    DB.generatedMarketerReferenceID, ""+generatedUUIDStr
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.CheckFreeUnitReplenishmentOnServer: "+ e.getMessage());
        }
    }

    public static void GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, String link, String generatedUUIDStr)
    {
        String type = BackgroundWorker.KEY_GenerateDisplayCommissionLink;
        GenerateDisplayCommissionLink worker
                = new GenerateDisplayCommissionLink(activity, context, userId, isSelectedCLinkIDWithUserName, link);
        try
        {
            worker.execute(type,
                    DB.MarketersTable.COL_0_USER_ID, ""+userId,
                    DB.isSelectedCLinkIDWithUserName, ""+isSelectedCLinkIDWithUserName,
                    DB.generatedMarketerReferenceID, ""+generatedUUIDStr
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.CheckFreeUnitReplenishmentOnServer: "+ e.getMessage());
        }
    }

    public static void GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, TextView textViewCommissionLink, String generatedUUIDStr, ImageView imageViewQRCode, LinearLayout linearLayoutAfterQRCodeDisplayedPanel)
    {
        String type = BackgroundWorker.KEY_GenerateDisplayCommissionLink;
        GenerateDisplayCommissionLink worker
                = new GenerateDisplayCommissionLink(activity, context, userId, isSelectedCLinkIDWithUserName, textViewCommissionLink, imageViewQRCode, linearLayoutAfterQRCodeDisplayedPanel);
        try
        {
            worker.execute(type,
                    DB.MarketersTable.COL_0_USER_ID, ""+userId,
                    DB.isSelectedCLinkIDWithUserName, ""+isSelectedCLinkIDWithUserName,
                    DB.generatedMarketerReferenceID, ""+generatedUUIDStr
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.CheckFreeUnitReplenishmentOnServer: "+ e.getMessage());
        }
    }

    public static void TryUpdatingReferrerIdForUser(Activity activity, Context context, String referrerCampaignID, int userID)
    {
        String type = BackgroundWorker.KEY_TryUpdatingReferrerIdForUser;
        TryUpdatingReferrerIdForUser worker
                = new TryUpdatingReferrerIdForUser(activity, context);
        try
        {
            worker.execute(type,
                    DB.UsersTable.COL_6_REFERRER_CAMPAIGN_ID, ""+referrerCampaignID,
                    DB.UsersTable.COL_0_USER_ID, ""+userID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.TryUpdatingReferrerIdForUser: "+ e.getMessage());
        }
    }

    public static void RetrieveCrSubscriptionAndUnitsInfo(Activity activity, Context context, int studentID, LinearLayout linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, LinearLayout linearLayoutQuestionRequestUnitsInfoSubPanel, LinearLayout linearLayoutQuestionRequestUnitsFree, TextView textViewSubscriptionInfo, TextView textViewQuestionRequestUnitsFree, TextView textViewQuestionRequestUnitsPurchased)
    {
        String type = BackgroundWorker.KEY_RetrieveCrSubscriptionAndUnitsInfo;
        RetrieveCrSubscriptionAndUnitsInfo worker
                = new RetrieveCrSubscriptionAndUnitsInfo(activity, context, linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, linearLayoutQuestionRequestUnitsInfoSubPanel, linearLayoutQuestionRequestUnitsFree, textViewSubscriptionInfo, textViewQuestionRequestUnitsFree, textViewQuestionRequestUnitsPurchased);
        try
        {
            worker.execute(type,
                    DB.UnitsTable.COL_0_STUDENT_ID, ""+studentID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.TryUpdatingReferrerIdForUser: "+ e.getMessage());
        }
    }

    public static void SendGradeForSolution(Activity activity, Context context, float rating, int userId, int questionRequestID, Button buttonGradeSolutions, RatingBar ratingBar, EditText editTextStudentComment)
    {
        String type = BackgroundWorker.KEY_SendGradeForSolution;
        SendGradeForSolution worker
                = new SendGradeForSolution(activity, context, buttonGradeSolutions, ratingBar, editTextStudentComment, true, true);
        try
        {
            worker.execute(type,
                    DB.SolutionRatingsTable.COL_0_COMMENTER_STUDENT_ID, ""+userId,
                    DB.SolutionRatingsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.SolutionRatingsTable.COL_2_RATING, ""+((double)rating),
                    DB.CommentsTable.COL_2_COMMENT_TEXT, ""+editTextStudentComment.getText().toString()
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.TryUpdatingReferrerIdForUser: "+ e.getMessage());
        }
    }

    public static void GetGradeForSolution(Activity activity, Context context, int userId, int questionRequestID, Button buttonGradeSolutions, RatingBar ratingBar, TextView texViewYourRating)
    {
        String type = BackgroundWorker.KEY_GetGradeForSolution;
        GetGradeForSolution worker
                = new GetGradeForSolution(activity, context, buttonGradeSolutions, ratingBar, texViewYourRating, false, false);
        try
        {
            worker.execute(type,
                    DB.SolutionRatingsTable.COL_0_COMMENTER_STUDENT_ID, ""+userId,
                    DB.SolutionRatingsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            );
        }
        catch (Exception e)
        {
            //GlobalVariables.Log(context, "EXCEPTION on AsyncTaskHelper.TryUpdatingReferrerIdForUser: "+ e.getMessage());
        }
    }
}
