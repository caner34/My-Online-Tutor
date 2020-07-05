package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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

public class BackgroundWorker extends AsyncTask<String,Void,String>
{

    /*
    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "iso-8859-1";*/

    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";


    Context context;
    Activity activity;


    public static String KEY_GetTutorPhotoDataAddressIdByQuestionRequestId = "get_tutor_photo_data_address_id_by_question_request_id";
    public static String KEY_GetQuestionImageDataAddressIdByQuestionRequestId = "get_question_image_data_address_id_by_question_request_id";
    public static String KEY_GetQuestionImageDataAddressIdByTutorId = "get_tutor_photo_data_address_id_by_tutor_id";
    public static String KEY_GetTutorByTutorID = "get_tutor_by_tutor_id";
    public static String KEY_InsertSelectedClassIDForTutor = "insert_selected_class_id_for_tutor";
    public static String KEY_UploadUserPhotoToDirectory = "upload_user_photo_to_directory";
    public static String KEY_UploadMiniUserPhotoToDirectory = "upload_mini_user_photo_to_directory";
    public static String KEY_UploadQuestionImageToDirectory = "upload_question_image_to_directory";
    public static String KEY_GetQuestionRequestIDsForRecommendedSolutionsOnSale = "get_question_request_ids_for_recommended_solutions_on_sale";
    public static String KEY_GetPurchasedSolutionsByStudentUserID = "get_purchased_solutions_by_student_user_id";
    public static String KEY_GetStudentWithImageDataAddressIDAndGender = "get_student_with_image_data_address_id_and_gender";
    public static String KEY_CheckForFreeUnitReplenishment = "check_for_free_unit_replenishment";
    public static String KEY_GenerateDisplayCommissionLink = "generate_display_commission_link";
    public static String KEY_TryUpdatingReferrerIdForUser = "try_updating_referrer_id_for_user";
    public static String KEY_RetrieveCrSubscriptionAndUnitsInfo = "get_unit_datas_and_subscription_expiration_date";
    public static String KEY_SendGradeForSolution = "send_grade_for_solutions";
    public static String KEY_GetGradeForSolution = "get_grade_for_solutions";


    public static String KEY_isSignUpUserNameAlreadyInUse = "is_sign_up_user_name_already_in_use";
    public static String KEY_isUserRegistered = "is_user_registered";
    public static String KEY_isUserNameAndPasswordMatches = "is_user_name_and_password_matches";
    public static String KEY_RecordUserNameAndPasswordOnServer = "record_user_name_and_password_on_server";
    public static String KEY_GetQuestionRequestIDsForTheGivenStudentID = "get_question_request_ids_for_the_given_student_id";
    public static String KEY_GetQuestionRequestIDsForTheGivenTutorID = "get_question_request_ids_for_the_given_tutor_id";
    public static String KEY_GetUserIDByUserName = "get_user_id_by_user_name";
    public static String KEY_GetUserStatusByUserID = "get_user_status_by_user_id";
    public static String KEY_GetQuestionRequestByQuestionRequestID = "get_question_request_by_question_request_id";
    public static String KEY_AddTutorToFavorites = "add_tutor_to_favorites";
    public static String KEY_RegisterUserPhotoToServer = "register_user_photo_to_server";
    public static String KEY_RegisterMiniUserPhotoToServer = "register_mini_user_photo_to_server";
    public static String KEY_GetUserPhotoByQuestionRequestID = "get_user_photo_by_question_request_id";
    public static String KEY_GetBitmapFromQuestionImageID = "get_bitmap_from_question_image_id";
    public static String KEY_GetMiniBitmapFromQuestionImageID = "get_mini_bitmap_from_question_image_id";
    public static String KEY_GetMiniUserPhotoBitmapFromQuestionImageID = "get_mini_user_photo_bitmap_from_question_image_id";
    public static String KEY_GetQuestionImageIDByQuestionRequestID = "get_question_image_id_by_question_request_id";
    public static String KEY_RegisterStudentToServer = "register_student_to_server";
    public static String KEY_RegisterTutorToServer = "register_tutor_to_server";
    public static String KEY_RegisterMarketerToServer = "register_marketer_to_server";
    public static String KEY_GetSolutionOnSaleByQuestionRequestID = "get_solution_on_sale_by_question_request_id";
    public static String KEY_GetCommentIDsForTheSolutionOnSale = "get_comment_ids_for_the_solution_on_sale";
    public static String KEY_GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart = "get_question_request_ids_for_solutions_for_sale_on_student_cart";
    public static String KEY_GetRecommendedDisplayPrice = "get_recommended_display_price";
    public static String KEY_GetAverageRatingForSolution = "get_average_rating_for_solution";
    public static String KEY_RemoveSolutionFromCart = "remove_solution_from_cart";
    public static String KEY_AddSolutionToCart = "add_solution_to_cart";
    public static String KEY_RemoveFromPurchasedSolutions = "remove_from_purchased_solutions";
    public static String KEY_AppropriateThePrice = "appropriate_the_price";
    public static String KEY_AddToPurchasedSolutions = "add_to_purchased_solutions";
    public static String KEY_TryPurchasingTheSolution = "try_purchasing_the_solution";
    public static String KEY_UpdateQuestionRequestState = "update_question_request_state";
    public static String KEY_SendObjection = "send_objection";
    public static String KEY_SendOtherObjection = "send_other_objection";
    public static String KEY_SetObjectionFieldIsTutorObjectedToObjectionAsTrue = "set_objection_field_is_tutor_objected_to_objection_as_true";
    public static String KEY_GetObjectionFieldIsTutorObjectedToObjectionIfTrue = "get_objection_field_is_tutor_objected_to_objection_if_true";
    public static String KEY_GetObjectionReason = "get_objection_reason";
    public static String KEY_SendWithdrawalFromSolutionJob = "send_withdrawal_from_solution_job";
    public static String KEY_UpdateYoutubeSolutionLinkOfQuestion = "update_youtube_solution_link_of_question";
    public static String KEY_SendAbuseReport = "send_abuse_report";
    public static String KEY_RetrieveObjectionResultResponse = "retrieve_objection_result_response";
    public static String KEY_RetrieveAbuseReportResultResponse = "retrieve_abuse_report_result_response";
    public static String KEY_GetQuestionRequestStateByQuestionRequestID = "get_question_request_state_by_question_request_id";
    public static String KEY_RemoveTutorFromFavorites = "remove_tutor_from_favorites";
    public static String KEY_isTutorHasAnyIBANNo = "is_tutor_has_any_iban_no";
    public static String KEY_SubmitTutorIBANNo = "submit_tutor_iban_no";
    public static String KEY_SendPaymentRequest = "send_payment_request";
    public static String KEY_GetFavoriteTutorsNames = "get_favorite_tutors_names";
    public static String KEY_isQuestionRequestNotADuplicate = "is_question_request_not_a_duplicate";
    public static String KEY_PostQuestionRequest = "post_question_request";
    public static String KEY_GetQuestionImageIDAfterUploadingImage = "get_question_image_id_after_uploading_image";
    public static String KEY_UploadMiniQuestionImage = "upload_mini_question_image";
    public static String KEY_GetCommentByCommentID = "get_comment_by_comment_id";
    public static String KEY_GetStudentFavoriteTutorIDsListByStudentUserID = "get_student_favorite_tutor_ids_list_by_student_user_id";
    public static String KEY_GetSuggestedTutorIDs = "get_suggested_tutor_ids";
    public static String KEY_GetSuggestedCommentIDs = "get_suggested_comment_ids";
    public static String KEY_isTutorHasAnyActivePaymentRequest = "is_tutor_has_any_active_payment_request";
    public static String KEY_GetTotalPaymentsReceivedSoFar = "get_total_payments_received_so_far";
    public static String KEY_GetEarningsFromQuestionRequests = "get_earnings_from_question_requests";
    public static String KEY_GetTotalEarningsFromSolutionDisplays = "get_earnings_from_solution_displays";
    public static String KEY_GetEarningsFromSubscriptions = "get_marketer_earnings_from_subscriptions";
    public static String KEY_GetEarningsFromPackagesConsumption = "get_marketer_earnings_from_packages_consumption";
    public static String KEY_SubmitMarketerIBANNo = "submit_marketer_iban_no";
    public static String KEY_isMarketerHasAnyIBANNo = "is_marketer_has_any_iban_no";

    /*String[] booleanQueryPhpFileNames = new String[]{
            KEY_RemoveTutorFromFavorites,
            KEY_AddTutorToFavorites
    };*/

    public BackgroundWorker(Activity activity, Context context)
    {
        this.context = context;
        this.activity = activity;
    }

    public BackgroundWorker()
    {

    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        /*if (type.equals(KEY_AddTutorToFavorites))
        {
            return Execute_BooleanQuery(type, params);
        }*/
        return Execute_BooleanQuery(type, params);
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
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(result);
        //GlobalVariables.AlertDialogDisplay(activity, result);

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
