package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Base64;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;
import com.onlinehocam.ozel.ders.okul.AsyncTasks.RetrieveImageFromDirectoryAndSetToImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ServerHelper
{

    public static boolean isSignUpUserNameAlreadyInUse(String userName)
    {
        //TODO TESTED Method isSignUpUserNameAlreadyInUse
        String type = BackgroundWorker.KEY_isSignUpUserNameAlreadyInUse;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_1_USER_NAME, ""+userName).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUserRegistered(int userId, int userTypeID)
    {
        //TODO TESTED Method isUserRegistered
        String type = BackgroundWorker.KEY_isUserRegistered;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+userId,
                    DB.UsersTable.COL_5_USER_TYPE_ID, ""+userTypeID
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUserNameAndPasswordMatches(String userName, String password)
    {
        //TODO TESTED Method isUserNameAndPasswordMatches
        String type = BackgroundWorker.KEY_isUserNameAndPasswordMatches;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_1_USER_NAME, ""+userName,
                    DB.UsersTable.COL_2_PASSWORD, ""+password).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean RecordUserNameAndPasswordOnServer(String userName, String password, String passwordTrials, String uniqueGoogleUSERID, String userTypeID)
    {
        //TODO TESTED Method RecordUserNameAndPasswordOnServer
        String type = BackgroundWorker.KEY_RecordUserNameAndPasswordOnServer;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_1_USER_NAME, ""+userName,
                    DB.UsersTable.COL_2_PASSWORD, ""+password,
                    DB.UsersTable.COL_3_PASSWORD_TRIALS, ""+passwordTrials,
                    DB.UsersTable.COL_4_UNIQUE_GOOGLE_USER_ID, ""+uniqueGoogleUSERID,
                    DB.UsersTable.COL_5_USER_TYPE_ID, ""+userTypeID
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public static List<Integer> GetQuestionRequestIDsForTheGivenTutorID(int tutorID)
    {
        //Returns the QuestionRequestIDs for the given tutorID which are notAnsweredYet, pendingApproval, approved
        List<Integer> result = new ArrayList<Integer>() {  };
        // TODO TESTED Method GetQuestionRequestIDsForTheGivenTutorID
        String type = BackgroundWorker.KEY_GetQuestionRequestIDsForTheGivenTutorID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_2_TUTOR_ID, ""+tutorID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }




    public static List<Integer> GetQuestionRequestIDsForTheGivenStudentID(int studentID)
    {
        List<Integer> result = new ArrayList<Integer>() {  };
        // TODO TESTED Method GetQuestionRequestIDsForTheGivenStudentID
        String type = BackgroundWorker.KEY_GetQuestionRequestIDsForTheGivenStudentID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_1_STUDENT_ID, ""+studentID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }


    public static int GetUserIDByUserName(String USER_NAME) {
        //TODO TESTED Method GetUserIDByUserName
        String type = BackgroundWorker.KEY_GetUserIDByUserName;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_1_USER_NAME, ""+USER_NAME
            ).get();
            int result = Integer.parseInt(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1;
        }
    }



    public static String GetUserStatusByUserID(int userId) {
        //TODO TESTED Method GetUserStatusByUserID
        String result = "";
        int user_status_id = -1;
        String type = BackgroundWorker.KEY_GetUserStatusByUserID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+userId
            ).get();
            user_status_id = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            user_status_id = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            user_status_id = -1;
        }

        result = GlobalVariables.USER_STATUS_ARRAY[user_status_id];
        return result;
    }

    public static QuestionRequest GetQuestionRequestByQuestionRequestID(int questionRequestID, Context context)
    {
        //If connection failed also return null;

        //TODO TESTED Method GetQuestionRequestByQuestionRequestID

        QuestionRequest result = null;
        String[] resultRawArray;
        try
        {
            String type = BackgroundWorker.KEY_GetQuestionRequestByQuestionRequestID;
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            try
            {
                String resultString = backgroundWorker.execute(type,
                        DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
                ).get();

                GlobalVariables.Log(context, "resultString: "+ resultString);

                if(resultString.contains(DB.COLUMN_SEPARATOR))
                {
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
                        GlobalVariables.Log(context, "resultRawArrayData: "+i+": "+ resultRawArray[i]);
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
                        GlobalVariables.Log(context, "data: "+i+": "+ data[i]);
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
            catch (ExecutionException e)
            {
                e.printStackTrace();
                GlobalVariables.Log(context, "ExecutionException on ServerHelper.GetQuestionRequestByQuestionRequestID >>> questionRequestID: "+ questionRequestID + "   message: " +e.getMessage());
                return result;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                GlobalVariables.Log(context, "InterruptedException on ServerHelper.GetQuestionRequestByQuestionRequestID >>> questionRequestID: "+ questionRequestID + "   message: " +e.getMessage());
                return result;
            }
        }
        catch (Exception e)
        {
            GlobalVariables.Log(context, "Exception on ServerHelper.GetQuestionRequestByQuestionRequestID >>> questionRequestID: "+ questionRequestID + "   message: " +e.getMessage());
            return null;
        }
    }


    //EARNINGS & PAYMENTS
    public static boolean isTutorHasAnyActivePaymentRequest(int USER_ID)
    {
        //TODO TESTED isTutorHasAnyActivePaymentRequest
        boolean result = false;

        String type = BackgroundWorker.KEY_isTutorHasAnyActivePaymentRequest;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int payment_request_state_id_for_active_being_reviewed
                    = Arrays.asList(GlobalVariables.PAYMENT_REQUEST_STATE.values()).indexOf(GlobalVariables.PAYMENT_REQUEST_STATE.ACTIVE_BEING_REVIEWED) + 1;

            String resultString = backgroundWorker.execute(type,
                    DB.PaymentRequestsTable.COL_0_USER_ID, ""+USER_ID,
                    DB.PaymentRequestsTable.COL_2_PAYMENT_REQUEST_STATE_ID, ""+payment_request_state_id_for_active_being_reviewed
            ).get();
            result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);

        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    public static boolean isTutorHasAnyIBANNo(int USER_ID)
    {
        //TODO TESTED Method isTutorHasAnyIBANNo
        String type = BackgroundWorker.KEY_isTutorHasAnyIBANNo;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+USER_ID
            ).get();

            return !resultString.equals(DB.CONS_EMPTY);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMarketerHasAnyIBANNo(int USER_ID)
    {
        //TODO TESTED Method isTutorHasAnyIBANNo
        String type = BackgroundWorker.KEY_isMarketerHasAnyIBANNo;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+USER_ID
            ).get();

            return !resultString.equals(DB.CONS_EMPTY);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SubmitTutorIBANNo(int USER_ID, String IBANNo)
    {
        //TODO TESTED Method SubmitTutorIBANNo
        String type = BackgroundWorker.KEY_SubmitTutorIBANNo;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+USER_ID,
                    DB.TutorsTable.COL_15_IBAN_NO, ""+IBANNo
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SubmitMarketerIBANNo(int USER_ID, String IBANNo)
    {
        //TODO TESTED Method SubmitTutorIBANNo
        String type = BackgroundWorker.KEY_SubmitMarketerIBANNo;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+USER_ID,
                    DB.TutorsTable.COL_15_IBAN_NO, ""+IBANNo
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SendPaymentRequest(int userId, double requestedAmount)
    {
        //TODO TESTED Method SendPaymentRequest
        String type = BackgroundWorker.KEY_SendPaymentRequest;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PaymentRequestsTable.COL_0_USER_ID, ""+userId,
                    DB.PaymentRequestsTable.COL_1_REQUEST_DATE, ""+CommonUtils.GetDateTimeAsTSI(),
                    DB.PaymentRequestsTable.COL_3_REQUESTED_AMOUNT, ""+requestedAmount
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String[] GetFavoriteTutorsNames(int USER_ID)
    {
        //TODO TESTED Method GetFavoriteTutorsNames
        List<String> resultList = new ArrayList<>();
        String[] result = new String[] { };

        String type = BackgroundWorker.KEY_GetFavoriteTutorsNames;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.FavoriteTutorsTable.COL_0_STUDENT_ID, ""+USER_ID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    resultList.add(resultStringArray[i]);
                }
            }

            return resultList.toArray(result);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return resultList.toArray(result);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return resultList.toArray(result);
        }
    }


    public static boolean isQuestionRequestNotADuplicate(QuestionRequest questionRequest, int questionImageID, Context context)
    {
        //TODO TEST Method isQuestionRequestNotADuplicate
        String type = BackgroundWorker.KEY_isQuestionRequestNotADuplicate;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
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

            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_1_STUDENT_ID, ""+studentID,
                    DB.QuestionRequestsTable.COL_2_TUTOR_ID, ""+tutorID,
                    DB.QuestionRequestsTable.COL_5_QUESTION_IMAGE_ID, ""+questionImageID
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static int PostQuestionRequest(QuestionRequest questionRequest, Drawable questionImageDrawable, Context context)
    {
        //TODO TESTED Method PostQuestionRequest   -   return photoID

        try
        {
            String type = BackgroundWorker.KEY_PostQuestionRequest;
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            try
            {
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

                GlobalVariables.Log(context, "tutorID: "+tutorID + "  studentID: "+studentID);

                if((GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT) && studentID == -1) || tutorID == -1)
                {
                    return -1;
                }

                int questionImageID = -1;
                GlobalVariables.Log(context, "questionRequest.questionImageID: "+questionRequest.questionImageID);
                if(questionRequest.questionImageID == -1)
                {
                    questionImageID = GetQuestionImageIDAfterUploadingImage(questionImageDrawable, context);
                }
                else
                {
                    questionImageID = questionRequest.questionImageID;
                }


                GlobalVariables.Log(context, "questionImageID: "+questionImageID);

                if(questionImageID == -1)
                {
                    return -1;
                }

                /*if(questionRequest.lastDeliveryDate.isEmpty())
                {
                    questionRequest.lastDeliveryDate = "NULL";
                }

                if(questionRequest.dueDateForAcceptance.isEmpty())
                {
                    questionRequest.dueDateForAcceptance = "NULL";
                }*/

                String resultString = backgroundWorker.execute(type,
                        DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequest.questionRequestID,
                        DB.QuestionRequestsTable.COL_1_STUDENT_ID, ""+studentID,
                        DB.QuestionRequestsTable.COL_2_TUTOR_ID, ""+tutorID,
                        DB.QuestionRequestsTable.COL_3_CLASS_ID, ""+classIndex,
                        DB.QuestionRequestsTable.COL_4_LESSON_ID, ""+lessonIndex,
                        DB.QuestionRequestsTable.COL_5_QUESTION_IMAGE_ID, ""+questionImageID,
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
                ).get();

                GlobalVariables.Log(context, "on PostQuestionRequest >>> resultString: "+resultString);

                int result = -1;
                result = Integer.parseInt(resultString);

                return result;
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
                return -1;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return -1;
            }
        }
        catch (Exception ex)
        {
            return -1;
        }
    }


    public static Bitmap GetUserPhotoByQuestionRequestID(int questionRequestID)
    {
        //TODO TESTED Method GetUserPhotoByQuestionRequestID
        byte[] resultArray = null;
        Bitmap result = null;
        String type = BackgroundWorker.KEY_GetUserPhotoByQuestionRequestID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();

            if(resultString.equals(DB.CONS_EMPTY))
            {
                result = null;
            }
            else
            {
                resultArray = Base64.decode(resultString, Base64.DEFAULT);
                result = CommonUtils.ConvertByteArrayIntoBitmap(resultArray);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = null;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }


    public static int GetQuestionImageIDAfterUploadingImage(Drawable questionImageDrawable, Context context)
    {
        //TODO TESTED Method GetQuestionImageIDAfterUploadingImage
        int result = -1;
        if(questionImageDrawable == null)
        {
            return result;
        }
        String type = BackgroundWorker.KEY_GetQuestionImageIDAfterUploadingImage;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            Bitmap bitmap = ((BitmapDrawable)questionImageDrawable).getBitmap();

            byte[] bytes = CommonUtils.ConvertBitmapIntoByteArray(bitmap, GlobalVariables.MAXIMUM_IMAGE_SIZE_IN_BYTES, context);

            String resultString = backgroundWorker.execute(type,
                    DB.QuestionImagesTable.COL_1_IMAGE_DATA, Base64.encodeToString(bytes, Base64.DEFAULT) //Arrays.toString(bytes)
            ).get();

            result = Integer.parseInt(resultString);

            GlobalVariables.Log(context, "on GetQuestionImageIDAfterUploadingImage result: "+result +"   resultString: "+resultString);
            if(result != -1)
            {
                ServerHelper.UploadMiniQuestionImage(questionImageDrawable, result, context);
            }
        }
        catch (ExecutionException e)
        {
            result = -1;
        }
        catch (InterruptedException e)
        {
            result = -1;
        }

        return result;
    }

    public static boolean UploadMiniQuestionImage(Drawable questionImageDrawable, int questionImageID, Context context)
    {
        //TODO TESTED Method UploadMiniQuestionImage
        if(questionImageDrawable == null)
        {
            return false;
        }
        String type = BackgroundWorker.KEY_UploadMiniQuestionImage;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            Bitmap bitmap = ((BitmapDrawable)questionImageDrawable).getBitmap();

            byte[] bytes = CommonUtils.ConvertBitmapIntoByteArray(bitmap, GlobalVariables.MAXIMUM_MINI_IMAGE_SIZE_IN_BYTES, context);

            String resultString = backgroundWorker.execute(type,
                    DB.QuestionMiniImagesTable.COL_0_QUESTION_IMAGE_ID, ""+questionImageID,
                    DB.QuestionMiniImagesTable.COL_1_IMAGE_DATA, Base64.encodeToString(bytes, Base64.DEFAULT) //Arrays.toString(bytes)
            ).get();


            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);

            GlobalVariables.Log(context, "on GetQuestionImageIDAfterUploadingImage result: "+result +"   resultString: "+resultString);

            return result;
        }
        catch (ExecutionException e)
        {
            return false;
        }
        catch (InterruptedException e)
        {
            return false;
        }
    }


    public static int RegisterUserPhotoToServer(Context context, Drawable photoImage, Activity activity)
    {
        //TODO TESTED Method RegisterUserPhotoToServer
        int result = -1;
        if(photoImage == null)
        {
            return result;
        }
        String type = BackgroundWorker.KEY_RegisterUserPhotoToServer;
        BackgroundWorker backgroundWorker = new BackgroundWorker(activity, context);
        try
        {
            Bitmap bitmap = ((BitmapDrawable)photoImage).getBitmap();

            byte[] bytes = CommonUtils.ConvertBitmapIntoByteArray(bitmap, GlobalVariables.MAXIMUM_USER_PHOTO_SIZE_IN_BYTES, context);

            String resultString = backgroundWorker.execute(type,
                    DB.UserPhotosTable.COL_1_PHOTO_DATA, Base64.encodeToString(bytes, Base64.DEFAULT) //Arrays.toString(bytes)
            ).get();
            result = Integer.parseInt(resultString);
            if(result != -1)
            {
                ServerHelper.RegisterMiniUserPhotoToServer(photoImage, result, context);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = -1;
        }

        return result;
    }


    public static int UploadUserPhotoToDirectory(Drawable photoImage, Context context)
    {
        //TODO TEST Method UploadUserPhotoToDirectory
        String type = BackgroundWorker.KEY_UploadUserPhotoToDirectory;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        int result = -1;
        if(photoImage == null)
        {
            return result;
        }
        try
        {
            Bitmap bitmap = ((BitmapDrawable)photoImage).getBitmap();

            byte[] bytes = CommonUtils.ConvertBitmapIntoByteArray(bitmap, GlobalVariables.MAXIMUM_USER_PHOTO_SIZE_IN_BYTES, context);

            String randomPhotoDataAddressID = "" + GlobalVariables.USER_ID + UUID.randomUUID().toString();
            String imageDataStr = Base64.encodeToString(bytes, Base64.DEFAULT);



            String resultString = backgroundWorker.execute(type,
                    DB.UserPhotosDataTable.COL_1_PHOTO_DATA_ADDRESS_ID, randomPhotoDataAddressID,
                    DB.UserPhotosTable.COL_1_PHOTO_DATA, imageDataStr
            ).get();
            GlobalVariables.Log(context, resultString);
            result = Integer.parseInt(resultString);


            /*result = Integer.parseInt(resultString);

            if(result != -1)
            {
                ServerHelper.RegisterMiniUserPhotoToServer(photoImage, result, context);
            }*/
        }
        catch (Exception e)
        {
            GlobalVariables.Log(context, "EXCEPTION on ServerHelper.UploadUserPhotoToDirectory: "+ e.getMessage());
            result = -1;
        }

        return result;
    }



    public static boolean RegisterMiniUserPhotoToServer(Drawable photoImage, int userPhotoID, Context context)
    {
        //TODO TEST Method RegisterUserPhotoToServer
        if(photoImage == null)
        {
            return false;
        }
        String type = BackgroundWorker.KEY_RegisterMiniUserPhotoToServer;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            Bitmap bitmap = ((BitmapDrawable)photoImage).getBitmap();

            byte[] bytes = CommonUtils.ConvertBitmapIntoByteArray(bitmap, GlobalVariables.MAXIMUM_MINI_USER_PHOTO_IN_BYTES, context);

            String resultString = backgroundWorker.execute(type,
                    DB.UserMiniPhotosTable.COL_0_PHOTO_ID, ""+userPhotoID,
                    DB.UserMiniPhotosTable.COL_1_PHOTO_DATA, Base64.encodeToString(bytes, Base64.DEFAULT)
            ).get();
            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    private static Comment GetCommentByCommentID(String studentID_AND_CS_QuestionRequestID)
    {

        //TODO TESTED Method GetCommentByCommentID

        Comment result = null;
        int studentID = Integer.parseInt(studentID_AND_CS_QuestionRequestID.split(",")[0]);
        int questionRequestID = Integer.parseInt(studentID_AND_CS_QuestionRequestID.split(",")[1]);

        String[] resultRawArray;
        try
        {

            String type = BackgroundWorker.KEY_GetCommentByCommentID;
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            try
            {
                String resultString = backgroundWorker.execute(type,
                        DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID,
                        DB.QuestionRequestsTable.COL_1_STUDENT_ID, ""+studentID
                ).get();

                if(resultString.contains(DB.COLUMN_SEPARATOR))
                {
                    resultString = resultString.substring(0, resultString.length() - DB.COLUMN_SEPARATOR.length());
                    resultRawArray = resultString.split(DB.COLUMN_SEPARATOR);


                    int classIndex = -1;
                    int lessonIndex = -1;

                    classIndex = Integer.parseInt(resultRawArray[0]) -1;
                    lessonIndex = Integer.parseInt(resultRawArray[1]) -1;
                    String questionClass = LessonsAndClasses.CLASS_NAMES[classIndex];
                    String questionLesson = (String) GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex]).get(lessonIndex);
                    String studentName = resultRawArray[2];
                    String tutorName = resultRawArray[3];
                    String commentText = resultRawArray[4];
                    int tutorUserID = Integer.parseInt(resultRawArray[5]);
                    double rating = Double.parseDouble(resultRawArray[6]);


                    return new Comment(questionClass, questionLesson, studentName, tutorName, commentText, studentID, tutorUserID, questionRequestID, rating);
                }
                return result;
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
                return result;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return result;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
    }



    public static List<Comment> GetCommentListWithCommentIDsList(List<String> commentIdsList)
    {
        List<Comment> result = new ArrayList<>();


        //TODO Fill Method GetCommentListWithCommentIDsList
        for(int i = 0; i < commentIdsList.size(); i++)
        {
            result.add(ServerHelper.GetCommentByCommentID(commentIdsList.get(i)));
        }

        return result;
    }

    public static List<Integer> GetStudentFavoriteTutorIDsListByStudentUserID(int studentUserID)
    {
        List<Integer> result = new ArrayList<>();

        //TODO TESTED Method GetStudentFavoriteTutorIDsListByStudentUserID
        String type = BackgroundWorker.KEY_GetStudentFavoriteTutorIDsListByStudentUserID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.FavoriteTutorsTable.COL_0_STUDENT_ID, ""+studentUserID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }

    public static List<Integer> GetSuggestedTutorIDs()
    {
        //TODO Fill Method GetSuggestedTutorIDs
        List<Integer> result = new ArrayList<>();

        String type = BackgroundWorker.KEY_GetSuggestedTutorIDs;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+GlobalVariables.USER_ID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }



    public static List<String> GetSuggestedCommentIDs(int userID)
    {
        List<String> result = new ArrayList<>();

        //TODO TESTED Method GetSuggestedCommentIDs
        String type = BackgroundWorker.KEY_GetSuggestedCommentIDs;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_2_TUTOR_ID, ""+userID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(resultStringArray[i]);
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }

    public static boolean RemoveTutorFromFavorites(int studentUserID, int tutorUserID)
    {
        //TODO TESTED Method RemoveTutorFromFavorites
        String type = BackgroundWorker.KEY_RemoveTutorFromFavorites;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.FavoriteTutorsTable.COL_0_STUDENT_ID, ""+studentUserID,
                    DB.FavoriteTutorsTable.COL_1_TUTOR_ID, ""+tutorUserID
            ).get();
            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AddTutorToFavorites(int studentUserID, int tutorUserID)
    {
        //TODO TESTED Method AddTutorToFavorites
        String type = BackgroundWorker.KEY_AddTutorToFavorites;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.FavoriteTutorsTable.COL_0_STUDENT_ID, ""+studentUserID,
                    DB.FavoriteTutorsTable.COL_1_TUTOR_ID, ""+tutorUserID,
                    DB.FavoriteTutorsTable.COL_2_INSERT_DATE, CommonUtils.GetDateTimeAsTSI()
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public static Bitmap GetBitmapFromQuestionImageID(int questionImageID)
    {
        //TODO TESTED Method GetBitmapFromQuestionImageID
        byte[] resultArray = null;
        Bitmap result = null;
        String type = BackgroundWorker.KEY_GetBitmapFromQuestionImageID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionImagesTable.COL_0_QUESTION_IMAGE_ID, ""+questionImageID
            ).get();

            if(resultString.equals(DB.CONS_EMPTY))
            {
                result = null;
            }
            else
            {
                resultArray = Base64.decode(resultString, Base64.DEFAULT);
                result = CommonUtils.ConvertByteArrayIntoBitmap(resultArray);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = null;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }


    public static Bitmap GetMiniQuestionImageBitmapFromQuestionImageID(int questionImageID)
    {
        //TODO TESTED Method GetMiniQuestionImageBitmapFromQuestionImageID
        byte[] resultArray = null;
        Bitmap result = null;
        String type = BackgroundWorker.KEY_GetMiniBitmapFromQuestionImageID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionMiniImagesTable.COL_0_QUESTION_IMAGE_ID, ""+questionImageID
            ).get();

            if(resultString.equals(DB.CONS_EMPTY))
            {
                result = null;
            }
            else
            {
                resultArray = Base64.decode(resultString, Base64.DEFAULT);
                result = CommonUtils.ConvertByteArrayIntoBitmap(resultArray);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = null;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }


    public static Bitmap GetMiniUserPhotoBitmapFromQuestionImageID(int questionRequestID)
    {
        //TODO TESTED Method GetMiniUserPhotoBitmapFromQuestionImageID
        byte[] resultArray = null;
        Bitmap result = null;
        String type = BackgroundWorker.KEY_GetMiniUserPhotoBitmapFromQuestionImageID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();

            if(resultString.equals(DB.CONS_EMPTY))
            {
                result = null;
            }
            else
            {
                resultArray = Base64.decode(resultString, Base64.DEFAULT);
                result = CommonUtils.ConvertByteArrayIntoBitmap(resultArray);
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = null;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = null;
        }

        return result;
    }


    public static int GetQuestionImageIDByQuestionRequestID(int questionRequestID)
    {
        int result = -1;
        //TODO TESTED Method GetQuestionImageIDByQuestionRequestID
        String type = BackgroundWorker.KEY_GetQuestionImageIDByQuestionRequestID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            result = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            result = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    public static boolean RegisterStudentToServer(Student crStudent, Context context)
    {
        //TODO TESTED Method RegisterStudentToServer
        String type = BackgroundWorker.KEY_RegisterStudentToServer;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+crStudent.userID,
                    DB.StudentsTable.COL_1_GENDER, ""+crStudent.gender,
                    DB.StudentsTable.COL_2_PHOTO_ID, ""+crStudent.photoID,
                    DB.StudentsTable.COL_3_EDUCATIONAL_ATTAINMENT, ""+crStudent.educationalAttainmentID,
                    DB.StudentsTable.COL_4_LAST_SCHOOL_NAME, crStudent.lastSchool,
                    DB.StudentsTable.COL_5_EDUCATION_FIELD, crStudent.educationField,
                    DB.StudentsTable.COL_6_REAL_NAME, crStudent.name,
                    DB.StudentsTable.COL_7_REAL_SURNAME, crStudent.surname,
                    DB.StudentsTable.COL_8_BIRTH_DATE, crStudent.birthDate,
                    DB.StudentsTable.COL_9_CITY_OF_RESIDENCY, ""+crStudent.cityOfResidency,
                    DB.StudentsTable.COL_10_DISTRICT_OF_RESIDENCY, ""+crStudent.districtOfResidency,
                    DB.StudentsTable.COL_11_CITY_OF_REGISTRY, ""+crStudent.cityOfRegistry,
                    DB.StudentsTable.COL_12_DISTRICT_OF_REGISTRY, ""+crStudent.districtOfRegistry,
                    DB.StudentsTable.COL_13_PHONE_NUMBER, crStudent.phoneNumber,
                    DB.StudentsTable.COL_14_CLASS_SELECTIONS, crStudent.classSelections
            ).get();


            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean RegisterTutorToServer(Tutor crTutor, Context context, Activity activity)
    {
        //TODO TESTED Method RegisterUserToServer
        String type = BackgroundWorker.KEY_RegisterTutorToServer;
        BackgroundWorker backgroundWorker = new BackgroundWorker(activity, context);
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.TutorsTable.COL_0_USER_ID, ""+crTutor.userID,
                    DB.TutorsTable.COL_1_GENDER, ""+crTutor.gender,
                    DB.TutorsTable.COL_2_PHOTO_ID, ""+crTutor.photoID,
                    DB.TutorsTable.COL_3_AVERAGE_TUTOR_RATING, ""+-1.0,
                    DB.TutorsTable.COL_4_ANSWERS_DISPLAYED_TIMES, ""+0,
                    DB.TutorsTable.COL_5_LAST_SCHOOL_NAME, crTutor.lastSchoolName,
                    DB.TutorsTable.COL_6_EDUCATION_FIELD, crTutor.educationField,
                    DB.TutorsTable.COL_7_REAL_NAME, crTutor.realName,
                    DB.TutorsTable.COL_8_REAL_SURNAME, crTutor.realSurname,
                    DB.TutorsTable.COL_9_BIRTH_DATE, crTutor.birthDate,
                    DB.TutorsTable.COL_10_CITY_OF_RESIDENCY, ""+crTutor.cityOfResidency,
                    DB.TutorsTable.COL_11_DISTRICT_OF_RESIDENCY, ""+crTutor.districtOfResidency,
                    DB.TutorsTable.COL_12_CITY_OF_REGISTRY, ""+crTutor.cityOfRegistry,
                    DB.TutorsTable.COL_13_DISTRICT_OF_REGISTRY, ""+crTutor.districtOfRegistry,
                    DB.TutorsTable.COL_14_PHONE_NUMBER, crTutor.phoneNumber,
                    DB.TutorsTable.COL_15_IBAN_NO, crTutor.ibanNo,
                    DB.TutorsTable.COL_16_CLASS_SELECTIONS, crTutor.classSelections
            ).get();
            GlobalVariables.Log(context, resultString);
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static SolutionOnSale GetSolutionOnSaleByQuestionRequestID(int questionRequestID, Context context)
    {
        //TODO TESTED Method GetSolutionOnSaleByQuestionRequestID
        String[] resultRawArray;
        SolutionOnSale result = null;

        try
        {
            String type = BackgroundWorker.KEY_GetSolutionOnSaleByQuestionRequestID;
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            try
            {
                String resultString = backgroundWorker.execute(type,
                        DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
                ).get();

                GlobalVariables.Log(context, "resultString: "+resultString);

                if(resultString.contains(DB.COLUMN_SEPARATOR))
                {
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


                    List<String> stringListBookName = (List<String>) (GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[publisherIndex]));

                    result = new SolutionOnSale(LessonsAndClasses.CLASSES.values()[classIndex],
                            (String) GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex]).get(lessonIndex),
                            studentName, tutorName, questionRequestID, questionImageID, pageNo, appreciatedPrice, counterPriceDemand,
                            youtubeLinkID, LessonsAndClasses.PUBLISHER_NAMES[publisherIndex],
                            stringListBookName.get(bookIndex), lastDeliveryDate, dueDateForAcceptance,
                            ServerHelper.GetCommentIDsForTheSolutionOnSale(questionRequestID)
                    );
                }


                return result;
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
                return result;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return result;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private static List<String> GetCommentIDsForTheSolutionOnSale(int questionRequestID)
    {
        List<String> result = new ArrayList<>();
        List<Integer> resultStudentIDs = new ArrayList<>();

        //TODO TESTED Method GetCommentIDsForTheSolutionOnSale
        //CommentID = studentID_AND_CS_QuestionRequestID
        String type = BackgroundWorker.KEY_GetCommentIDsForTheSolutionOnSale;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.CommentsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    resultStudentIDs.add(Integer.parseInt(resultStringArray[i]));
                }
                String crCommentID_CS_String_Student_question_reuest_IDS = "";
                for(int i = 0; i < resultStudentIDs.size(); i++)
                {
                    crCommentID_CS_String_Student_question_reuest_IDS = ""+resultStudentIDs.get(i)+","+questionRequestID;
                    result.add(crCommentID_CS_String_Student_question_reuest_IDS);
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }

    public static List<Integer> GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart(int userId)
    {
        List<Integer> result = new ArrayList<>();

        //TODO TESTED Method GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart
        String type = BackgroundWorker.KEY_GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.SolutionsOnShoppingCartsTable.COL_0_STUDENT_ID, ""+userId
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }


    public static Double GetRecommendedDisplayPrice(int userId, int questionRequestID)
    {
        //TODO TESTED Method GetRecommendedDisplayPrice
        String type = BackgroundWorker.KEY_GetRecommendedDisplayPrice;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PurchasedSolutionsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.PurchasedSolutionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static Double GetAverageRatingForSolution(int questionRequestID)
    {
        //TODO TESTED Method GetAverageRatingForSolution
        String type = BackgroundWorker.KEY_GetAverageRatingForSolution;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.SolutionRatingsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static boolean RemoveSolutionFromCart(int userId, int questionRequestID)
    {
        //TODO TESTED Method RemoveSolutionFromCart
        String type = BackgroundWorker.KEY_RemoveSolutionFromCart;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.SolutionsOnShoppingCartsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.SolutionsOnShoppingCartsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AddSolutionToCart(int userId, int questionRequestID, double displayPrice)
    {
        //TODO TESTED Method AddSolutionToCart
        String type = BackgroundWorker.KEY_AddSolutionToCart;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.SolutionsOnShoppingCartsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.SolutionsOnShoppingCartsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.SolutionsOnShoppingCartsTable.COL_2_CART_ADDING_DATE, CommonUtils.GetDateTimeAsTSI(),
                    DB.SolutionsOnShoppingCartsTable.COL_3_PRICE, ""+displayPrice
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean RemoveFromPurchasedSolutions(int userId, int questionRequestID)
    {
        //TODO TESTED Method RemoveFromPurchasedSolutions
        String type = BackgroundWorker.KEY_RemoveFromPurchasedSolutions;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PurchasedSolutionsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.PurchasedSolutionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AppropriateThePrice(Context context, int userId, boolean isFree, GlobalVariables.PRODUCT purchasedProduct)
    {
        //TODO TESTED Method AppropriateThePrice
        String type = BackgroundWorker.KEY_AppropriateThePrice;

        int is_free_value = isFree ? 1 : 0;

        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int productID = Arrays.asList(GlobalVariables.PRODUCT.values()).indexOf(purchasedProduct) + 1;
            String resultString = backgroundWorker.execute(type,
                    DB.UnitsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.CONS_IS_FREE, ""+is_free_value,
                    DB.CONS_QUERY_PARAMETER_PRODUCT_ID, ""+productID
            ).get();

            GlobalVariables.Log(context, "ServerHelper.AppropriateThePrice >>> resultString: "+resultString);

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AddToPurchasedSolutions(int userId, int questionRequestID, double price)
    {
        //TODO TESTED Method AddToPurchasedSolutions
        String type = BackgroundWorker.KEY_AddToPurchasedSolutions;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PurchasedSolutionsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.PurchasedSolutionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.PurchasedSolutionsTable.COL_2_PURCHASE_DATE, CommonUtils.GetDateTimeAsTSI(),
                    DB.PurchasedSolutionsTable.COL_3_PRICE, ""+price
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static GlobalVariables.PURCHASE_RESPONSE TryPurchasingTheSolution(Context context, int userId, double displayPrice, int questionRequestID)
    {
        //TODO TESTED Method TryPurchasingTheSolution
        String type = BackgroundWorker.KEY_TryPurchasingTheSolution;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PurchasedSolutionsTable.COL_0_STUDENT_ID, ""+userId,
                    DB.PurchasedSolutionsTable.COL_3_PRICE, ""+displayPrice,
                    DB.PurchasedSolutionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            GlobalVariables.Log(context, "ServerHelper.TryPurchasingTheSolution >>> resultString: "+resultString);
            return TurnStringResponseInto_PURCHASE_RESPONSE(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
        }
    }

    public static List<Integer> GetQuestionRequestIDsForRecommendedSolutionsOnSale(int userId) {
        List<Integer> result = new ArrayList<>();

        //TODO Fill Method GetQuestionRequestIDsForRecommendedSolutionsOnSale
        String type = BackgroundWorker.KEY_GetQuestionRequestIDsForRecommendedSolutionsOnSale;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+userId
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
    }

    public static List<Integer> GetPurchasedSolutionsByStudentUserID(int userId) {
        List<Integer> result = new ArrayList<>();

        //TODO Fill Method GetQuestionRequestIDsForRecommendedSolutionsOnSale
        String type = BackgroundWorker.KEY_GetPurchasedSolutionsByStudentUserID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.StudentsTable.COL_0_USER_ID, ""+userId
            ).get();

            if(resultString.contains(DB.LINE_SEPARATOR))
            {
                resultString = resultString.substring(0, resultString.length() - DB.LINE_SEPARATOR.length());
                String[] resultStringArray = resultString.split(DB.LINE_SEPARATOR);

                for(int i = 0; i < resultStringArray.length;i++)
                {
                    result.add(Integer.parseInt(resultStringArray[i]));
                }
            }

            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return result;
        }
    }

    public static boolean UpdateQuestionRequestState(int questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE newState)
    {
        //TODO TESTED Method UpdateQuestionRequestState
        String type = BackgroundWorker.KEY_UpdateQuestionRequestState;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int newStateID = (Arrays.asList(GlobalVariables.QUESTION_REQUEST_STATE.values())).indexOf(newState) + 1;
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.QuestionRequestsTable.COL_14_QUESTION_REQUEST_STATE_ID, ""+newStateID
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SendObjection(int studentID, int questionRequestID, String objectionReason)
    {
        if(!ServerHelper.UpdateQuestionRequestState(questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_OBJECTED_BY_STUDENT))
        {
            return false;
        }

        //TODO TESTED CERTAINLY IN APP Method SendObjection AND CERTAINLY IN APP
        String type = BackgroundWorker.KEY_SendObjection;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int objectionReasonID = -1;
            if (objectionReason.startsWith(DB.KEY_OTHER_REASON))
            {
                objectionReasonID = Arrays.asList(GlobalVariables.questionRequestObjectionReasons).indexOf(DB.TR_KEY_OTHER_OBJECTION_REASON);
                ServerHelper.SendOtherObjection(studentID, questionRequestID, objectionReason);
            }
            else
            {
                objectionReasonID = Arrays.asList(GlobalVariables.questionRequestObjectionReasons).indexOf(objectionReason);
            }
            String resultString = backgroundWorker.execute(type,
                    DB.ObjectionsTable.COL_0_STUDENT_ID, ""+studentID,
                    DB.ObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.ObjectionsTable.COL_2_OBJECTION_DATE, CommonUtils.GetDateTimeAsTSI(),
                    DB.ObjectionsTable.COL_3_REASON_ID, ""+objectionReasonID
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean SendOtherObjection(int studentID, int questionRequestID, String objectionReason)
    {
        //TODO TESTED CERTAINLY IN APP Method SendOtherObjection AND CERTAINLY IN APP
        String type = BackgroundWorker.KEY_SendOtherObjection;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.OtherObjectionsTable.COL_0_STUDENT_ID, ""+studentID,
                    DB.OtherObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.OtherObjectionsTable.COL_2_OTHER_OBJECTION_TEXT, objectionReason
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SetObjectionFieldIsTutorObjectedToObjectionAsTrue(int questionRequestID)
    {
        //TODO TESTED CERTAINLY IN APP Method SetObjectionFieldIsTutorObjectedToObjectionAsTrue
        String type = BackgroundWorker.KEY_SetObjectionFieldIsTutorObjectedToObjectionAsTrue;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.ObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean GetObjectionFieldIsTutorObjectedToObjectionIfTrue(int questionRequestID)
    {
        int resultInt = 0;
        //TODO TESTED CERTAINLY IN APP Method GetObjectionFieldIsTutorObjectedToObjectionIfTrue
        String type = BackgroundWorker.KEY_GetObjectionFieldIsTutorObjectedToObjectionIfTrue;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.ObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            resultInt = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            resultInt = 0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            resultInt = 0;
        }

        boolean result = false;

        if (resultInt == 0)
        {
            result = false;
        }
        else if (resultInt == 1)
        {
            result = true;
        }

        return result;
    }

    public static String GetObjectionReason(int questionRequestID, Context context)
    {
        //TODO TESTED CERTAINLY IN APP Method GetObjectionReason you may also cancel the second parameter Context context out due to being redundant
        int resultInt = -1;
        String type = BackgroundWorker.KEY_GetObjectionReason;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.ObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            resultInt = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }

        String result = "";

        if(resultInt == -1)
        {
            result = DB.TR_KEY_OTHER_OBJECTION_REASON;
        }
        else
        {
            result = context.getString(GlobalVariables.questionRequestObjectionReasons[resultInt-1]);
        }

        return result;
    }

    public static boolean SendWithdrawalFromSolutionJob(int questionRequestID, String withdrawalExcuse, Context context)
    {
        if(!UpdateQuestionRequestState(questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_JOB_CANCELLED_BY_TUTOR))
        {
            return false;
        }

        //TODO TESTED Method SendWithdrawalFromSolutionJob
        String type = BackgroundWorker.KEY_SendWithdrawalFromSolutionJob;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int withdrawalExcuseResourceID = 5;
            for(int i = 0; i < GlobalVariables.questionRequestWithdrawalExcuses.length; i++) {
                GlobalVariables.Log(context, "SendWithdrawalFromSolutionJob >>> withdrawalExcuse: "+ withdrawalExcuse + "  i: "+i+ "  GlobalVariables.questionRequestWithdrawalExcuses[i]: "+GlobalVariables.questionRequestWithdrawalExcuses[i]);
                if ((context.getString(GlobalVariables.questionRequestWithdrawalExcuses[i])).equals(withdrawalExcuse)) {
                    GlobalVariables.Log(context, "SendWithdrawalFromSolutionJob >>> withdrawalExcuse TRUE i: "+i);
                    withdrawalExcuseResourceID = i;
                    break;
                }
            }
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.QuestionRequestsTable.COL_15_WITHDRAWAL_EXCUSE_ID, ""+ (withdrawalExcuseResourceID+1)
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean UpdateYoutubeSolutionLinkOfQuestion(int questionRequestID, String youtubeLink)
    {
        //TODO TESTED Method UpdateYoutubeSolutionLinkOfQuestion
        String type = BackgroundWorker.KEY_UpdateYoutubeSolutionLinkOfQuestion;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.QuestionRequestsTable.COL_9_SOLUTION_VIDEO_URL_ID, ""+youtubeLink
            ).get();

            boolean result = TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean SendAbuseReport(int studentID, int questionRequestID, String abuseReason)
    {
        if(!ServerHelper.UpdateQuestionRequestState(questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.REPORTED_BY_STUDENT))
        {
            return false;
        }

        //TODO TESTED Method SendAbuseReport
        String type = BackgroundWorker.KEY_SendAbuseReport;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            int abuseReasonID = -1;

            abuseReasonID = Arrays.asList(GlobalVariables.questionRequestReportAbuseReasons).indexOf(abuseReason);

            String resultString = backgroundWorker.execute(type,
                    DB.AbuseReportsTable.COL_0_STUDENT_ID, ""+studentID,
                    DB.AbuseReportsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID,
                    DB.AbuseReportsTable.COL_2_REPORT_DATE, CommonUtils.GetDateTimeAsTSI(),
                    DB.AbuseReportsTable.COL_3_REASON_ID, ""+abuseReasonID
            ).get();
            return TurnStringIntoBooleanWithStrictTrueDefinition(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String RetrieveObjectionResultResponse(int questionRequestID, Context context)
    {
        String result = context.getString(GlobalVariables.questionRequestObjectionResults[0]);
        //TODO TESTED Method RetrieveObjectionResultResponse
        int resultInt = -1;
        String type = BackgroundWorker.KEY_RetrieveObjectionResultResponse;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.ObjectionsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            resultInt = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }



        if(resultInt != -1)
        {
            result = context.getString(GlobalVariables.questionRequestObjectionResults[resultInt-1]);
        }

        return result;
    }

    public static String RetrieveAbuseReportResultResponse(int questionRequestID, Context context)
    {
        String result = context.getString(GlobalVariables.questionRequestAbuseReportResults[0]);
        //TODO TESTED Method RetrieveAbuseReportResultResponse
        int resultInt = -1;
        String type = BackgroundWorker.KEY_RetrieveAbuseReportResultResponse;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.AbuseReportsTable.COL_1_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            resultInt = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }



        if(resultInt != -1)
        {
            result = context.getString(GlobalVariables.questionRequestObjectionResults[resultInt-1]);
        }

        return result;
    }

    public static GlobalVariables.QUESTION_REQUEST_STATE GetQuestionRequestStateByQuestionRequestID(int questionRequestID)
    {
        GlobalVariables.QUESTION_REQUEST_STATE result = null;
        //TODO TESTED Method GetQuestionRequestStateByQuestionRequestID
        int resultInt = -1;
        String type = BackgroundWorker.KEY_GetQuestionRequestStateByQuestionRequestID;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.QuestionRequestsTable.COL_0_QUESTION_REQUEST_ID, ""+questionRequestID
            ).get();
            resultInt = Integer.parseInt(resultString);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            resultInt = -1;
        }



        if(resultInt != -1)
        {
            result = GlobalVariables.QUESTION_REQUEST_STATE.values()[resultInt-1];
        }

        return result;
    }


    public static double GetTotalPaymentsReceivedSoFar()
    {
        //TODO TESTED Method GetTotalPaymentsReceivedSoFar
        String type = BackgroundWorker.KEY_GetTotalPaymentsReceivedSoFar;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.PaymentRequestsTable.COL_0_USER_ID, ""+ GlobalVariables.USER_ID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static double GetEarningsFromQuestionRequests()
    {
        //TODO TESTED Method GetEarningsFromQuestionRequests
        String type = BackgroundWorker.KEY_GetEarningsFromQuestionRequests;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+ GlobalVariables.USER_ID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static double GetTotalEarningsFromSolutionDisplays(Context context)
    {
        //TODO TESTED Method GetTotalEarningsFromSolutionDisplays
        String type = BackgroundWorker.KEY_GetTotalEarningsFromSolutionDisplays;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+ GlobalVariables.USER_ID
            ).get();
            GlobalVariables.Log(context, resultString);
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static double GetEarningsFromSubscriptions()
    {
        //TODO TESTED Method GetEarningsFromQuestionRequests
        String type = BackgroundWorker.KEY_GetEarningsFromSubscriptions;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+ GlobalVariables.USER_ID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static double GetEarningsFromPackagesConsumption()
    {
        //TODO TESTED Method GetEarningsFromQuestionRequests
        String type = BackgroundWorker.KEY_GetEarningsFromPackagesConsumption;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        try
        {
            String resultString = backgroundWorker.execute(type,
                    DB.UsersTable.COL_0_USER_ID, ""+ GlobalVariables.USER_ID
            ).get();
            double result = TryParsingDoubleOrDefaultValue(resultString, -1.0);
            return result;
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            return -1.0;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return -1.0;
        }
    }

    public static Student GetStudentWithImageDataAddressIDAndGender(Context context, int studentID, String DIR)
    {
        String[] resultRawArray;
        Student result = null;

        try
        {
            GlobalVariables.Log(context, "on GetStudentWithImageDataAddressIDAndGender >>> entered: ");

            String type = BackgroundWorker.KEY_GetStudentWithImageDataAddressIDAndGender;
            BackgroundWorker backgroundWorker = new BackgroundWorker();
            try
            {
                String resultString = backgroundWorker.execute(type,
                        DB.StudentsTable.COL_0_USER_ID, ""+studentID,
                        DB.DIRECTORY, ""+ DIR
                ).get();

            GlobalVariables.Log(context, "on GetStudentWithImageDataAddressIDAndGender >>> resultString: "+resultString);
                if(resultString.contains(DB.COLUMN_SEPARATOR))
                {
                    resultString = resultString.substring(0, resultString.length() - DB.COLUMN_SEPARATOR.length());
                    resultRawArray = resultString.split(DB.COLUMN_SEPARATOR);

                    String imageDataAddressID = resultRawArray[0];
                    String gender = resultRawArray[1];

                    result = new Student(studentID, imageDataAddressID, gender);
                }


                return result;
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
                return result;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                return result;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
    }




    private static GlobalVariables.PURCHASE_RESPONSE TurnStringResponseInto_PURCHASE_RESPONSE(String resultString)
    {
        if(resultString == null || resultString.isEmpty())
        {
            return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
        }
        try
        {
            if(resultString.toLowerCase().contains("failed"))
            {
                return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
            }
            else if(resultString.toLowerCase().contains("already"))
            {
                return GlobalVariables.PURCHASE_RESPONSE.ALREADY_PURCHASED;
            }
            else if(resultString.toLowerCase().contains("insufficient"))
            {
                return GlobalVariables.PURCHASE_RESPONSE.INSUFFICIENT_BALANCE;
            }
            else if(resultString.toLowerCase().equals("purchasing"))
            {
                return GlobalVariables.PURCHASE_RESPONSE.PURCHASING;
            }
            else if(resultString.toLowerCase().equals("free_purchasing"))
            {
                return GlobalVariables.PURCHASE_RESPONSE.PURCHASING_FREE;
            }
        }
        catch (Exception ex)
        {
            return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
        }
        return GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED;
    }


    public static boolean TurnStringIntoBooleanWithStrictTrueDefinition(String resultString)
    {
        if(resultString == null)
        {
            return false;
        }
        try
        {
            if(resultString.toLowerCase().contains("success"))
            {
                return true;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
        return false;
    }

    private static boolean TurnStringIntoBooleanWithStrictFalseDefinition(String resultString, Context tempContext)
    {
        if(resultString == null)
        {
            return false;
        }
        try
        {
            if(resultString.toLowerCase().contains("failed"))
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    static double TryParsingDoubleOrDefaultValue(String strValue, double defaultValue)
    {
        double result = defaultValue;

        try
        {
            result = Double.parseDouble(strValue);
        }
        catch (Exception ex)
        {
            result = defaultValue;
        }
        finally
        {
            return result;
        }
    }
}
