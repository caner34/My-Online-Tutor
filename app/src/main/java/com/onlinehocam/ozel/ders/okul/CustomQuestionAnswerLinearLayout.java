package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;
import com.onlinehocam.ozel.ders.okul.AsyncTasks.SetAndHandleGetQuestionRequestByQuestionRequestID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CustomQuestionAnswerLinearLayout extends LinearLayout
{
    Activity activity;
    Context context;
    int questionRequestID;
    public QuestionRequest crQuestionRequest;
    TextView textViewDemandingStudentName;
    TextView textViewVideoIcon, textViewQuestionIcon;
    LinearLayout linearLayoutQuestionIcon, linearLayoutVideoIcon;
    ImageView imageViewQuestionIconImage;

    String objectionReason, withdrawalExcuse, abuseReason;
    private static final String YOUTUBE_LINK_FIRST_32 = "https://www.youtube.com/watch?v=";

    CustomQuestionAnswerLinearLayout customQuestionAnswerLinearLayout;

    enum PROPERTY_INPUT_TYPE
    {
        STRING,
        INTEGER
    }

    public CustomQuestionAnswerLinearLayout(Activity activity, Context context, int questionRequestID) {
        super(context);

        customQuestionAnswerLinearLayout = this;

        this.activity = activity;
        this.context = context;
        this.questionRequestID = questionRequestID;

        // In case it is needed
        objectionReason = "";
        withdrawalExcuse = "";


        this.setOrientation(LinearLayout.VERTICAL);

        List<TextView> textViewsToFormat = new ArrayList<>();

        crQuestionRequest = ServerHelper.GetQuestionRequestByQuestionRequestID(questionRequestID, context);

        /*if(!crSolutionOnSale.publisher.isEmpty())
        {
            textViewPublisher.setText(context.getString(R.string.question_requests_publisher) + " " + crSolutionOnSale.publisher);
            this.addView(textViewPublisher);
            textViewsToFormat.add(textViewPublisher);
        }*/

        AsyncTaskHelper.SetAndHandleGetQuestionRequestByQuestionRequestID(context, questionRequestID, this);
    }

    public void FillAndHandleTheRestOfTheOperationsAfterQuestionRequestRetrieval()
    {
        TextView textViewPublisher, textViewBookName, textViewPageNo, textViewAppreciatedPrice, textViewLastDeliveryDate, textViewDueDateForAcceptance, textViewQuestionRequestState, textViewDemandedTutorName;
        TextView textViewClassName = new TextView(context);
        TextView textViewLessonName = new TextView(context);
        imageViewQuestionIconImage = new ImageView(context);
        ImageView imageViewAnswerVideoIconImage = new ImageView(context);

        GlobalVariables.Log(context, "on CustomQuestionAnswerLinearLayout >>> crQuestionRequest.questionRequestID: "+crQuestionRequest.questionRequestID);

        textViewClassName.setText(context.getString(R.string.question_requests_class) + " " + LessonsAndClasses.CLASS_NAMES[Arrays.asList(LessonsAndClasses.CLASSES.values()).indexOf(crQuestionRequest.questionClass)]);
        textViewLessonName.setText(context.getString(R.string.question_requests_lesson) + " " + crQuestionRequest.questionLesson);


        AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(context, questionRequestID, imageViewQuestionIconImage);
        imageViewAnswerVideoIconImage.setImageResource(R.drawable.youtube_icon);

        linearLayoutQuestionIcon = new LinearLayout(context);
        linearLayoutVideoIcon = new LinearLayout(context);
        linearLayoutQuestionIcon.setOrientation(LinearLayout.VERTICAL);
        linearLayoutVideoIcon.setOrientation(LinearLayout.VERTICAL);
        textViewVideoIcon = new TextView(context);
        textViewQuestionIcon = new TextView(context);
        FormatTextView(textViewVideoIcon,12f, context.getString(R.string.question_display_watch_solution));
        FormatTextView(textViewQuestionIcon,12f, context.getString(R.string.question_display_browse_question));
        //textViewQuestionIcon.setTypeface();
        linearLayoutQuestionIcon.addView(imageViewQuestionIconImage);
        linearLayoutQuestionIcon.addView(textViewQuestionIcon);
        linearLayoutVideoIcon.addView(imageViewAnswerVideoIconImage);
        linearLayoutVideoIcon.addView(textViewVideoIcon);


        FormatTextView(textViewClassName);
        FormatTextView(textViewLessonName);

        textViewClassName.setPadding(0,12,0,0);

        this.addView(textViewClassName);
        this.addView(textViewLessonName);


        InstantiateOptionalTextView(textViewPublisher = new TextView(context), crQuestionRequest.publisher, context.getString(R.string.question_requests_publisher));
        InstantiateOptionalTextView(textViewBookName = new TextView(context), crQuestionRequest.bookName, context.getString(R.string.question_requests_book_name));
        InstantiateOptionalTextView(textViewPageNo = new TextView(context), crQuestionRequest.pageNo, context.getString(R.string.question_requests_page_no));
        InstantiateOptionalTextView(textViewDemandingStudentName = new TextView(context), crQuestionRequest.demandingStudentName, context.getString(R.string.question_requests_questioner));
        InstantiateOptionalTextView(textViewDemandedTutorName = new TextView(context), crQuestionRequest.demandedTutorName, context.getString(R.string.question_requests_replier));
        InstantiateOptionalTextView(textViewAppreciatedPrice = new TextView(context), crQuestionRequest.appreciatedPrice, context.getString(R.string.question_requests_appreciated_price));
        InstantiateOptionalTextView(textViewLastDeliveryDate = new TextView(context), crQuestionRequest.lastDeliveryDate, context.getString(R.string.question_requests_last_delivery_date));
        InstantiateOptionalTextView(textViewDueDateForAcceptance = new TextView(context), crQuestionRequest.dueDateForAcceptance, context.getString(R.string.question_due_date_for_acceptance));
        InstantiateOptionalTextView(textViewQuestionRequestState = new TextView(context),
                "\n"+ context.getString(GlobalVariables.QUESTION_REQUEST_STATE_NAME_FOR_TUTOR_RESOURCE_IDS[(Arrays.asList((GlobalVariables.QUESTION_REQUEST_STATE[])(GlobalVariables.QUESTION_REQUEST_STATE.values()))).indexOf(crQuestionRequest.questionRequestState)]),
                context.getString(R.string.question_requests_state));


/*
        if(!crSolutionOnSale.demandingStudentName.isEmpty())
        {
            textViewDemandingStudentName = new TextView(context);
            textViewDemandingStudentName.setText(context.getString(R.string.question_requests_questioner) + " " + crSolutionOnSale.demandingStudentName+" "+xList+"-"+questionNo);
            this.addView(textViewDemandingStudentName);
            textViewsToFormat.add(textViewDemandingStudentName);
        }

        if(!crSolutionOnSale.demandedTutorName.isEmpty())
        {
            textViewDemandedTutorName = new TextView(context);
            textViewDemandedTutorName.setText(context.getString(R.string.question_requests_replier) + " " + crSolutionOnSale.demandedTutorName);
            this.addView(textViewDemandedTutorName);
            textViewsToFormat.add(textViewDemandedTutorName);
        }

        FormatTexts(textViewsToFormat);*/

        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

        linearLayoutQuestionIcon.setLayoutParams(imageViewParams);
        linearLayoutVideoIcon.setLayoutParams(imageViewParams);

        imageViewQuestionIconImage.setPadding(6,6,6,6);
        imageViewAnswerVideoIconImage.setPadding(6,6,6,6);

        imageViewQuestionIconImage.setAdjustViewBounds(true);
        imageViewAnswerVideoIconImage.setAdjustViewBounds(true);

        LinearLayout linearLayoutButtons = new LinearLayout(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        linearLayoutButtons.setLayoutParams(lp);

        linearLayoutButtons.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutButtons.setGravity(Gravity.CENTER);
        linearLayoutButtons.setWeightSum(2f);
        linearLayoutButtons.addView(linearLayoutQuestionIcon);
        linearLayoutButtons.addView(linearLayoutVideoIcon);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(10, 30, 10, 0);
        this.setLayoutParams(lp2);

        ApplyModificationsForDifferentQuestionRequestStatusCategories(crQuestionRequest.questionRequestState);

        this.addView(linearLayoutButtons);
    }

    public void InstantiateOptionalTextView(TextView tv, String crQuestionRequestProperty, String propertyStringTag)
    {
        /*if(propertyStringTag == context.getString(R.string.question_requests_publisher))
        {
            tv.setPadding(0,12,0,0);
        }*/
        if(!crQuestionRequestProperty.isEmpty())
        {
            tv.setText(propertyStringTag + " " + crQuestionRequestProperty);
            tv.setGravity(Gravity.CENTER);
            this.addView(tv);
            FormatTextView(tv);
        }
    }

    public void InstantiateOptionalTextView(TextView tv, int crQuestionRequestProperty, String propertyStringTag)
    {
        if(crQuestionRequestProperty != -1)
        {
            tv.setText(propertyStringTag + " " + crQuestionRequestProperty);
            this.addView(tv);
            FormatTextView(tv);
        }
    }

    public void InstantiateOptionalTextView(TextView tv, double crQuestionRequestProperty, String propertyStringTag)
    {
        if(crQuestionRequestProperty != -1f)
        {
            tv.setText(propertyStringTag + " ₺" + crQuestionRequestProperty);
            this.addView(tv);
            FormatTextView(tv);
        }
    }



/*
    void FormatTexts(List<TextView> textViewsToFormat)
    {
        for(TextView tv : textViewsToFormat)
        {
            FormatTextView(tv);
        }
    }*/


    void FormatTextView(TextView tv, float textSize)
    {
        tv.setTextColor(Color.BLACK);
        tv.setBottom(5);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 5);
        lp.gravity = Gravity.CENTER;
        tv.setLayoutParams(lp);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }


    void FormatTextView(TextView tv, float textSize, String text)
    {
        FormatTextView(tv, 18f);
        tv.setText(text);
    }


    void FormatTextView(TextView tv)
    {
        FormatTextView(tv, 18f);
    }


    private void ActivateSetOnClickListenerForQuestionImageDisplay()
    {
        linearLayoutQuestionIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",crQuestionRequest.questionClass + " - " +crQuestionRequest.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetBitmapFromQuestionImageID(ServerHelper.GetQuestionImageIDByQuestionRequestID(crQuestionRequest.questionRequestID)));

                SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.DISPLAY_QUESTION_IMAGE);

                if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_TUTOR))
                {
                    AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((TutorQuestionRequestDisplayActivity)activity).linearLayoutMainProgressBar, crQuestionRequest, customQuestionAnswerLinearLayout, questionRequestID, true, false);
                    //AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((TutorQuestionRequestDisplayActivity)activity).linearLayoutMainProgressBar, questionRequestID, true, false);
                }
                else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT))
                {
                    AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((StudentQuestionRequestDisplayActivity)activity).linearLayoutMainProgressBar, crQuestionRequest, customQuestionAnswerLinearLayout, questionRequestID, true, false);
                    //AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((StudentQuestionRequestDisplayActivity)activity).linearLayoutMainProgressBar, questionRequestID, true, false);
                }
            }
        });
    }


/*
    public void PurchaseSolution(int userId, boolean isFree, int questionRequestID, String solutionVideoURL)
    {
        double displayPrice = isFree ? 1 : 0.5;
        if(ServerHelper.AddToPurchasedSolutions(userId, questionRequestID, displayPrice))
        {
            if(ServerHelper.AppropriateThePrice(context, userId, isFree, GlobalVariables.PRODUCT.SOLUTION))
            {
                ServerHelper.RemoveSolutionFromCart(userId, questionRequestID);
                //((StudentQuestionRequestDisplayActivity)activity).questionRequestIDsForSolutionsOnSaleOnStudentCart.remove((Integer) questionRequestID);
                DisplayYoutubeSolutionVideo(solutionVideoURL);
            }
            else
            {
                ServerHelper.RemoveFromPurchasedSolutions(userId, questionRequestID);
                GlobalVariables.AlertDialogDisplay(context, context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
            }
        }
    }*/

    public void DisplayYoutubeSolutionVideo(String solutionVideoID)
    {
        //TODO INVESTIGATE IF THIS METHOD without YOUTUBE API MAKES THE YOUTUBE CHANNELS SUSPENDED OR NOT
        //Note: Beware when you are using this method, YouTube may suspend your channel due to spam, this happened two times with me
        //https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + solutionVideoID));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DB.YOUTUBE_LINK_INITIAL + solutionVideoID));
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private void ActivateSetOnClickListenerForSolutionVideoDisplay(boolean isVideoExistent)
    {
        final boolean isVideoExistentFinal = isVideoExistent;
        linearLayoutVideoIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVideoExistentFinal)
                {
                    CommonUtils.DisplayYoutubeSolutionVideo(crQuestionRequest.solutionVideoUrlID, context);
                }
                else
                {
                    new CustomToast(activity, context, context.getString(R.string.question_requests_reply_video_is_not_submited_yet));
                }
            }
        });
    }



    private void ApplyModificationsForDifferentQuestionRequestStatusCategories(GlobalVariables.QUESTION_REQUEST_STATE questionRequestState)
    {
        if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_TUTOR))
        {
            ApplyModificationsOnQuestionRequestDisplayForTutors(questionRequestState);
        }
        else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT))
        {
            ApplyModificationsOnQuestionRequestDisplayForStudents(questionRequestState);
        }
    }
    private void ApplyModificationsOnQuestionRequestDisplayForTutors(GlobalVariables.QUESTION_REQUEST_STATE questionRequestState)
    {
        if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_TUTOR_ACCEPTANCE)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfPendingForTutorAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_CANCELED_BY_STUDENT)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestCancelledByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_REJECTED_BY_TUTOR)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestRejectedByTutor();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_ACCEPTANCE)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfTimedOutForTutorAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestAcceptedByTutorAndStudentPendingForTutorsSolution();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_STUDENT_ACCEPTANCE)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfPendingForStudentAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.ACCEPTED_BY_STUDENT)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfAcceptedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_OBJECTED_BY_STUDENT)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfSolutionObjectedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REPORTED_BY_STUDENT)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfReportedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_JOB_CANCELLED_BY_TUTOR)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfSolutionJobCancelledByTutor();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_SOLUTION)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfTimedOutForTutorSolution();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.DIRECTLY_POSTED_BY_TUTOR)
        {
            ExecuteModificationsForTutorsQRDisplay_InCaseOfDirectlyPostedByTutor();
        }
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfPendingForTutorAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        LinearLayout linearLayoutRequestAcceptOrReject = new LinearLayout(context);

        linearLayoutRequestAcceptOrReject.setWeightSum(2f);
        linearLayoutRequestAcceptOrReject.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        linearLayoutRequestAcceptOrReject.setLayoutParams(lp);
        linearLayoutRequestAcceptOrReject.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
        lpChild.setMargins(12,9,12,3);

        Button buttonAccept = new Button(context);
        buttonAccept.setText(R.string.question_requests_display_accept);
        FormatButton(buttonAccept);
        buttonAccept.setLayoutParams(lpChild);
        Button buttonReject = new Button(context);
        buttonReject.setText(R.string.question_requests_display_reject);
        FormatButton(buttonReject);
        buttonReject.setLayoutParams(lpChild);

        buttonAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecuteTutorActionAcceptQuestionRequest();
            }
        });
        buttonReject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecuteTutorActionRejectQuestionRequest();
            }
        });

        linearLayoutRequestAcceptOrReject.addView(buttonAccept);
        linearLayoutRequestAcceptOrReject.addView(buttonReject);
        this.addView(linearLayoutRequestAcceptOrReject);
    }


    private void ExecuteTutorActionRejectQuestionRequest()
    {
        if(ServerHelper.UpdateQuestionRequestState(crQuestionRequest.questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_REJECTED_BY_TUTOR))
        {
            new CustomToast(activity, context, context.getText(R.string.question_requests_REQUEST_REJECTED_BY_TUTOR_successfully).toString());
            RemoveQuestionRequestLinearLayoutFromParent();
        }
        else
        {
            GlobalVariables.AlertDialogDisplay(activity, context.getText(R.string.question_requests_REQUEST_REJECTED_BY_TUTOR_failed).toString());
        }
    }

    private void ExecuteTutorActionAcceptQuestionRequest()
    {
        if(ServerHelper.UpdateQuestionRequestState(crQuestionRequest.questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION))
        {
            GlobalVariables.AlertDialogDisplay(activity,context.getText(R.string.question_requests_REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION_successfully1).toString()
                    + " \"" + context.getString(R.string.main_tutor_display_question_requests) + "\" "
                    + context.getText(R.string.question_requests_REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION_successfully2) + " \""
                    + context.getString(R.string.question_requests_pending_for_answer_questions) + "\" "
                    + context.getText(R.string.question_requests_REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION_successfully3) +" ("
                    + context.getString(R.string.question_due_date_for_acceptance).substring(0, context.getString(R.string.question_due_date_for_acceptance).length()-1) + ") "
                    + context.getText(R.string.question_requests_REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION_successfully4) );
            RemoveQuestionRequestLinearLayoutFromParent();
        }
        else
        {
            GlobalVariables.AlertDialogDisplay(activity, context.getText(R.string.question_requests_REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION_failed).toString());
        }
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestCancelledByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestRejectedByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfTimedOutForTutorAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfRequestAcceptedByTutorAndStudentPendingForTutorsSolution()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        LayoutParams lpChild = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpChild.setMargins(12,9,12,3);
        lpChild.gravity = Gravity.CENTER;

        final TextView textViewPasteHint = new TextView(context);
        textViewPasteHint.setText(R.string.post_solution_click_to_paste_video_link);
        textViewPasteHint.setTextColor(Color.BLACK);
        textViewPasteHint.setGravity(Gravity.CENTER);
        FormatTextView(textViewPasteHint);
        textViewPasteHint.setVisibility(GONE);

        final EditText editTextSolutionVideoLink = new EditText(context);
        editTextSolutionVideoLink.setHint(R.string.post_solution_please_enter_video_link);
        editTextSolutionVideoLink.addTextChangedListener(new CustomYoutubeLinkFormattingTextWatcher(context, editTextSolutionVideoLink));
        editTextSolutionVideoLink.setTextColor(Color.BLACK);
        editTextSolutionVideoLink.setVisibility(GONE);


        final Button buttonSendSolutionVideoLink = new Button(context);
        buttonSendSolutionVideoLink.setText(R.string.question_requests_post_video_link);
        FormatButton(buttonSendSolutionVideoLink);
        buttonSendSolutionVideoLink.setLayoutParams(lpChild);

        linearLayoutVideoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewPasteHint.getVisibility() == VISIBLE)
                {
                    PastCopiedYoutubeLinkToEditText(editTextSolutionVideoLink);
                }
                else
                {
                    new CustomToast(activity, context, context.getString(R.string.question_requests_click_post_video_link_button_first1)
                            + context.getString(R.string.question_requests_post_video_link)
                            + context.getString(R.string.question_requests_click_post_video_link_button_first2));
                }
            }
        });

        buttonSendSolutionVideoLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSendSolutionVideoLink.getText().equals(context.getString(R.string.question_requests_post_video_link)))
                {
                    textViewPasteHint.setVisibility(VISIBLE);
                    editTextSolutionVideoLink.setVisibility(VISIBLE);
                    editTextSolutionVideoLink.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editTextSolutionVideoLink, InputMethodManager.SHOW_IMPLICIT);
                    buttonSendSolutionVideoLink.setText(R.string.question_requests_post_video_link_now);
                }
                else
                {
                    String youtubeLink = GetYoutubeVideoLinkLastPart(editTextSolutionVideoLink.getText().toString());
                    GlobalVariables.Log(context, "youtubeLink: "+youtubeLink);
                    if(youtubeLink.length() == 11)
                    {
                        if(ServerHelper.UpdateYoutubeSolutionLinkOfQuestion(crQuestionRequest.questionRequestID, youtubeLink))
                        {
                            GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_video_link_sent_successfully));
                            RemoveQuestionRequestLinearLayoutFromParent();
                        }
                        else
                        {
                            GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_video_link_sent_failed));
                        }
                    }
                    else
                    {
                        GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_fill_video_link_please));
                    }
                }
            }
        });

        textViewPasteHint.setLayoutParams(lpChild);
        editTextSolutionVideoLink.setLayoutParams(lpChild);
        buttonSendSolutionVideoLink.setLayoutParams(lpChild);


        final TextView textViewWithdrawalExcusesSubTitle = new TextView(context);
        textViewWithdrawalExcusesSubTitle.setTextColor(Color.BLACK);
        textViewWithdrawalExcusesSubTitle.setText(R.string.question_requests_your_withdrawal_excuse);
        textViewWithdrawalExcusesSubTitle.setGravity(Gravity.CENTER);
        final RadioGroup rgWithdrawalExcuses = new RadioGroup(context);
        rgWithdrawalExcuses.setVisibility(GONE);

        for(int i = 0; i < GlobalVariables.questionRequestWithdrawalExcuses.length; i++)
        {
            RadioButton crRadioButton = (RadioButton) LayoutInflater.from(activity).inflate(R.layout.simple_radio_button, null);
            crRadioButton.setId(106757+i);
            crRadioButton.setText(GlobalVariables.questionRequestWithdrawalExcuses[i]);
            rgWithdrawalExcuses.addView(crRadioButton);
        }

        rgWithdrawalExcuses.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selectedRadioButtonText = ((RadioButton)findViewById(rgWithdrawalExcuses.getCheckedRadioButtonId())).getText().toString();
                withdrawalExcuse = selectedRadioButtonText;
            }
        });

        final Button buttonWithdraw = new Button(context);
        buttonWithdraw.setText(R.string.question_requests_withdrawal_from_tutoring);
        FormatButton(buttonWithdraw);
        buttonWithdraw.setLayoutParams(lpChild);

        buttonWithdraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonWithdraw.getText().toString().equals(context.getString(R.string.question_requests_withdrawal_from_tutoring)))
                {
                    ExecuteTutorActionOpenListWithDrawFromSolutionExcuses(buttonWithdraw, rgWithdrawalExcuses);
                }
                else
                {
                    if(withdrawalExcuse.isEmpty())
                    {
                        new CustomToast(activity, context, context.getString(R.string.question_requests_warning_no_withdrawal_excuse_selected));
                    }
                    else
                    {
                        ExecuteTutorActionSendWithdrawalFromSolution(buttonWithdraw, rgWithdrawalExcuses);
                    }
                }
            }
        });

        this.addView(textViewPasteHint);
        this.addView(editTextSolutionVideoLink);
        this.addView(buttonSendSolutionVideoLink);
        this.addView(rgWithdrawalExcuses);
        this.addView(buttonWithdraw);

    }

    private void PastCopiedYoutubeLinkToEditText(EditText editTextYoutubeLink)
    {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null)
        {
            String copiedText = clipData.getItemAt(0).getText().toString();
            String youtubeLinkToBePasted = "";
            String linkLastPart = GetYoutubeVideoLinkLastPart(copiedText);
            if(!linkLastPart.isEmpty())
            {
                youtubeLinkToBePasted = YOUTUBE_LINK_FIRST_32 + linkLastPart;
            }

            if( !youtubeLinkToBePasted.isEmpty())
            {
                editTextYoutubeLink.setText(youtubeLinkToBePasted);
                new CustomToast(activity, context, context.getString(R.string.post_solution_video_link_pasted));
            }
            else
            {
                GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.post_solution_invalid_video_link_on_clipboard));
            }
        }
        else
        {
            new CustomToast(activity, context, context.getString(R.string.post_solution_no_text_on_clipboard_for_video_link));
        }
    }

    private String GetYoutubeVideoLinkLastPart(String copiedText)
    {
        copiedText = copiedText.trim();
        String result = "";
        if(copiedText.contains("v=") && (copiedText.split("v=").length > 1  && (copiedText.split("v=")[1]).length() == 11  ))
        {
            result = (copiedText.split("v=")[1]);
        }
        else if (copiedText.contains("=") && (copiedText.split("=").length > 1 && (copiedText.split("=")[1]).length() == 11 )  )
        {
            result = (copiedText.split("=")[1]);
        }
        else if (copiedText.length() == 11)
        {
            result = copiedText;
        }
        else if (copiedText.contains("youtu.be") && (copiedText.split(".be/").length > 1 ) && (copiedText.split(".be/")[1].length() == 11) )
        {
            result = copiedText.split(".be/")[1];
        }

        if(result.isEmpty())
        {
            return result;
        }
        return result;
    }


    private void ExecuteTutorActionSendWithdrawalFromSolution(Button buttonWithdraw, RadioGroup rgWithdrawalExcuses)
    {
        if(ServerHelper.SendWithdrawalFromSolutionJob(crQuestionRequest.questionRequestID, withdrawalExcuse, context))
        {
            buttonWithdraw.setVisibility(GONE);
            rgWithdrawalExcuses.setVisibility(GONE);
            RemoveQuestionRequestLinearLayoutFromParent();
            GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_withdrew_successfully));
        }
        else
        {
            GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_withdrew_failed));
        }
    }

    private void ExecuteTutorActionOpenListWithDrawFromSolutionExcuses(Button buttonWithdraw, RadioGroup rgWithdrawalExcuses)
    {
        buttonWithdraw.setText(R.string.question_requests_say_pardon_and_withdraw);
        rgWithdrawalExcuses.setVisibility(VISIBLE);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfPendingForStudentAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfAcceptedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfSolutionObjectedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);

        if(ServerHelper.GetObjectionFieldIsTutorObjectedToObjectionIfTrue(crQuestionRequest.questionRequestID))
        {
            return;
        }

        LinearLayout linearLayoutRequestObjectToObjection = new LinearLayout(context);

        linearLayoutRequestObjectToObjection.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        linearLayoutRequestObjectToObjection.setLayoutParams(lp);
        linearLayoutRequestObjectToObjection.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpChild.setMargins(12,9,12,3);

        TextView textViewObjectionReason = new TextView(context);
        textViewObjectionReason.setGravity(Gravity.CENTER);
        FormatTextView(textViewObjectionReason, 18f);
        objectionReason = ServerHelper.GetObjectionReason(crQuestionRequest.questionRequestID, context);
        if(objectionReason.equals(DB.CONS_EMPTY))
        {
            objectionReason = "";
        }
        if (objectionReason.startsWith(DB.KEY_OTHER_REASON))
        {
            objectionReason = DB.TR_KEY_OTHER_OBJECTION_REASON;
        }
        textViewObjectionReason.setText(context.getText(R.string.question_requests_students_objection_reason_subtitle)+ "\n" + objectionReason);


        final Button buttonObjectToObjection = new Button(context);
        buttonObjectToObjection.setText(R.string.question_requests_display_object_to_objection);
        FormatButton(buttonObjectToObjection);
        buttonObjectToObjection.setLayoutParams(lpChild);


        buttonObjectToObjection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecuteTutorActionObjectToObjection(crQuestionRequest.questionRequestID);
            }
        });

        linearLayoutRequestObjectToObjection.addView(textViewObjectionReason);
        linearLayoutRequestObjectToObjection.addView(buttonObjectToObjection);
        this.addView(linearLayoutRequestObjectToObjection);
    }

    private void ExecuteTutorActionObjectToObjection(int questionRequestID)
    {
        if(ServerHelper.SetObjectionFieldIsTutorObjectedToObjectionAsTrue(questionRequestID))
        {
            new CustomToast(activity, context, context.getString(R.string.question_requests_ojected_to_objection_successfully));
            RemoveQuestionRequestLinearLayoutFromParent();
        }
        else
        {
            new CustomToast(activity, context, context.getString(R.string.question_requests_ojected_to_objection_failed));
        }
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfReportedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfSolutionJobCancelledByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfTimedOutForTutorSolution()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);
    }

    private void ExecuteModificationsForTutorsQRDisplay_InCaseOfDirectlyPostedByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }



    private void ApplyModificationsOnQuestionRequestDisplayForStudents(GlobalVariables.QUESTION_REQUEST_STATE questionRequestState)
    {
        if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_TUTOR_ACCEPTANCE)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfPendingForTutorAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_CANCELED_BY_STUDENT)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestCancelledByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_REJECTED_BY_TUTOR)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestRejectedByTutor();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_ACCEPTANCE)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfTimedOutForTutorAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestAcceptedByTutorAndStudentPendingForTutorsSolution();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_STUDENT_ACCEPTANCE)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfPendingForStudentAcceptance();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.ACCEPTED_BY_STUDENT)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfAcceptedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_OBJECTED_BY_STUDENT)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfSolutionObjectedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REPORTED_BY_STUDENT)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfReportedByStudent();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_JOB_CANCELLED_BY_TUTOR)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfSolutionJobCancelledByTutor();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_SOLUTION)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfTimedOutForTutorSolution();
        }
        else if(questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.DIRECTLY_POSTED_BY_TUTOR)
        {
            ExecuteModificationsForStudentsQRDisplay_InCaseOfDirectlyPostedByTutor();
        }
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfPendingForTutorAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //TODO add buttons Withdraw from Question Request and Offer to Another Tutor At the Same time
        SetupWithdrawFromQuestionRequestComponents();
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void SetupWithdrawFromQuestionRequestComponents()
    {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        lp.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpChild.setMargins(12,9,12,3);
        lpChild.gravity = Gravity.CENTER;

        LinearLayout linearLayoutWithdrawQR = new LinearLayout(context);
        linearLayoutWithdrawQR.setOrientation(LinearLayout.VERTICAL);
        linearLayoutWithdrawQR.setLayoutParams(lp);
        linearLayoutWithdrawQR.setGravity(Gravity.CENTER);

        Button buttonWithdrawQR = new Button(context);
        buttonWithdrawQR.setText(R.string.question_requests_withdraw_question_request);
        FormatButton(buttonWithdrawQR);
        buttonWithdrawQR.setLayoutParams(lpChild);

        buttonWithdrawQR.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.QUESTION_REQUEST_STATE crState = ServerHelper.GetQuestionRequestStateByQuestionRequestID(crQuestionRequest.questionRequestID);
                if (crState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_TUTOR_ACCEPTANCE)
                {
                    if(ServerHelper.UpdateQuestionRequestState(crQuestionRequest.questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_CANCELED_BY_STUDENT))
                    {
                        GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_request_cancelled_succesfully));
                        RemoveQuestionRequestLinearLayoutFromParent();
                    }
                    else
                    {
                        GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_request_cancelled_failed));
                    }
                }
                else
                {
                    String crStateStatusMessageForStudent = context.getString(GlobalVariables.QUESTION_REQUEST_STATE_NAME_FOR_STUDENT_RESOURCE_IDS[(Arrays.asList(GlobalVariables.QUESTION_REQUEST_STATE.values())).indexOf(crState)]);
                    GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_cannot_withdraw_due_to_following_reason)+" "+crStateStatusMessageForStudent);
                }
            }
        });

        linearLayoutWithdrawQR.addView(buttonWithdrawQR);
        this.addView(linearLayoutWithdrawQR);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestCancelledByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //Offer the Same Question Again
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestRejectedByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //Offer the Same Question to Another Tutor
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents()
    {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        lp.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpChild.setMargins(12,9,12,3);
        lpChild.gravity = Gravity.CENTER;

        LinearLayout linearLayoutRequestTheSameQuestionFromAnotherTutorAgain = new LinearLayout(context);
        linearLayoutRequestTheSameQuestionFromAnotherTutorAgain.setOrientation(LinearLayout.VERTICAL);
        linearLayoutRequestTheSameQuestionFromAnotherTutorAgain.setLayoutParams(lp);
        linearLayoutRequestTheSameQuestionFromAnotherTutorAgain.setGravity(Gravity.CENTER);

        Button buttonRequestTheSameQuestionFromAnotherTutorAgain = new Button(context);
        buttonRequestTheSameQuestionFromAnotherTutorAgain.setText(R.string.question_requests_request_the_question_again);
        FormatButton(buttonRequestTheSameQuestionFromAnotherTutorAgain);
        buttonRequestTheSameQuestionFromAnotherTutorAgain.setLayoutParams(lpChild);

        buttonRequestTheSameQuestionFromAnotherTutorAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.QUESTION_REQUEST_STATE crQRSTATE = ServerHelper.GetQuestionRequestStateByQuestionRequestID(crQuestionRequest.questionRequestID);
                if(crQRSTATE != GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION)
                {
                    RequestTheSameQuestionFromAnotherTutorAgain();
                }
                else
                {
                    WarnThatThereIsAlreadyAnotherTutorWorkingOnTheSolution();
                }
            }
        });

        linearLayoutRequestTheSameQuestionFromAnotherTutorAgain.addView(buttonRequestTheSameQuestionFromAnotherTutorAgain);
        this.addView(linearLayoutRequestTheSameQuestionFromAnotherTutorAgain);
    }

    private void WarnThatThereIsAlreadyAnotherTutorWorkingOnTheSolution()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        //alertDialog.setMessage(message);
        String message = activity.getString(R.string.question_requests_warning_already_an_tutor_solving);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View crView = LayoutInflater.from(activity).inflate(R.layout.custom_alert_dialog_view, null);
        ((TextView)crView.findViewById(R.id.texViewMessage)).setText(message);
        TextView buttonPositive = crView.findViewById(R.id.texViewButtonPositive);
        LinearLayout linearLayoutButtonNegative = crView.findViewById(R.id.linearLayoutButtonNegative);
        linearLayoutButtonNegative.setVisibility(VISIBLE);
        TextView buttonNegative = crView.findViewById(R.id.texViewButtonNegative);
        buttonPositive.setText(activity.getString(R.string.constant_yes));
        buttonNegative.setText(activity.getString(R.string.constant_no));
        final AlertDialog fDialog = alertDialog;
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestTheSameQuestionFromAnotherTutorAgain();
            }
        });
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fDialog.dismiss();
            }
        });
        alertDialog.setView(crView);
        alertDialog.show();
    }

    private void RequestTheSameQuestionFromAnotherTutorAgain()
    {
        Intent i = new Intent(activity, StudentPostQuestionActivity.class);
        i.putExtra("questionRequestID", crQuestionRequest.questionRequestID);
        i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfTimedOutForTutorAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //Offer the Same Question to Another Tutor
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfRequestAcceptedByTutorAndStudentPendingForTutorsSolution()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //do nothing
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfPendingForStudentAcceptance()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(6, 6, 6, 6);
        lp.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams lpChild = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lpChild.setMargins(12,9,12,3);
        lpChild.gravity = Gravity.CENTER;

        //Objection Feature
        SetupAcceptSolutionFeature(lp, lpChild);
        SetupObjectionFeature(lp, lpChild);
        SetupReportAbuseFeature(lp, lpChild);
    }

    private void SetupReportAbuseFeature(LayoutParams lp, LayoutParams lpChild)
    {
        final LinearLayout linearLayoutAbuseReport = new LinearLayout(context);
        linearLayoutAbuseReport.setOrientation(LinearLayout.VERTICAL);

        linearLayoutAbuseReport.setLayoutParams(lp);
        linearLayoutAbuseReport.setGravity(Gravity.CENTER);

        final LinearLayout linearLayoutReportAbuseReasons = new LinearLayout(context);
        linearLayoutReportAbuseReasons.setOrientation(LinearLayout.VERTICAL);
        linearLayoutReportAbuseReasons.setGravity(Gravity.CENTER);
        linearLayoutReportAbuseReasons.setLayoutParams(lp);
        final TextView textViewReportAbuseReasonsSubTitle = new TextView(context);
        textViewReportAbuseReasonsSubTitle.setTextColor(Color.BLACK);
        textViewReportAbuseReasonsSubTitle.setText(R.string.question_requests_abuse_reason_report_abuse_reasons);
        textViewReportAbuseReasonsSubTitle.setGravity(Gravity.CENTER);
        final RadioGroup rgReportAbuseReasons = new RadioGroup(context);
        final TextView textViewEnterDetails = new TextView(context);
        FormatTextView(textViewEnterDetails);
        textViewEnterDetails.setText(R.string.tutor_discovery_enter_abuse_report_details);
        final EditText editTextDetailsReportAbuseReason = new EditText(context);
        textViewEnterDetails.setVisibility(GONE);
        editTextDetailsReportAbuseReason.setVisibility(GONE);
        editTextDetailsReportAbuseReason.setTextColor(Color.BLACK);

        for(int i = 0; i < GlobalVariables.questionRequestReportAbuseReasons.length; i++)
        {
            RadioButton crRadioButton = (RadioButton) LayoutInflater.from(activity).inflate(R.layout.simple_radio_button, null);
            crRadioButton.setId(23745+i);
            crRadioButton.setText(GlobalVariables.questionRequestReportAbuseReasons[i]);
            rgReportAbuseReasons.addView(crRadioButton);
        }

        rgReportAbuseReasons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selectedRadioButtonText = ((RadioButton)findViewById(rgReportAbuseReasons.getCheckedRadioButtonId())).getText().toString();

                abuseReason = selectedRadioButtonText;
                editTextDetailsReportAbuseReason.setVisibility(VISIBLE);
                textViewEnterDetails.setVisibility(VISIBLE);
                editTextDetailsReportAbuseReason.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editTextDetailsReportAbuseReason, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        final Button buttonReportAbuse = new Button(context);
        buttonReportAbuse.setText(R.string.question_requests_report);
        FormatButton(buttonReportAbuse);
        buttonReportAbuse.setLayoutParams(lpChild);

        buttonReportAbuse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonReportAbuse.getText().toString().equals(context.getString(R.string.question_requests_report)))
                {
                    ExecuteTutorActionExtendReportAbuseSubMenu(buttonReportAbuse, linearLayoutReportAbuseReasons);
                }
                else
                {
                    if(!editTextDetailsReportAbuseReason.getText().toString().isEmpty())
                    {
                        abuseReason += ":Details:" + editTextDetailsReportAbuseReason.getText().toString();
                    }
                    ExecuteTutorActionSendAbuseReport(linearLayoutAbuseReport);
                }
            }
        });

        textViewReportAbuseReasonsSubTitle.setLayoutParams(lpChild);
        rgReportAbuseReasons.setLayoutParams(lpChild);
        editTextDetailsReportAbuseReason.setLayoutParams(lpChild);

        linearLayoutReportAbuseReasons.addView(textViewReportAbuseReasonsSubTitle);
        linearLayoutReportAbuseReasons.addView(rgReportAbuseReasons);
        linearLayoutReportAbuseReasons.addView(textViewEnterDetails);
        linearLayoutReportAbuseReasons.addView(editTextDetailsReportAbuseReason);
        linearLayoutReportAbuseReasons.setVisibility(GONE);

        linearLayoutAbuseReport.addView(linearLayoutReportAbuseReasons);
        linearLayoutAbuseReport.addView(buttonReportAbuse);
        this.addView(linearLayoutAbuseReport);
    }

    private void ExecuteTutorActionSendAbuseReport(LinearLayout linearLayoutAbuseReport)
    {
        GlobalVariables.Log(context, "abuseReason: "+abuseReason  );
        if(abuseReason.isEmpty())
        {
            new CustomToast(activity, context, context.getString(R.string.question_requests_please_select_an_abuse_reason));
        }
        else
        {
            if(ServerHelper.SendAbuseReport(GlobalVariables.USER_ID, crQuestionRequest.questionRequestID, abuseReason))
            {
                GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_abuse_report_sent_successfully));
                linearLayoutAbuseReport.setVisibility(GONE);
                RemoveQuestionRequestLinearLayoutFromParent();
            }
            else
            {
                GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_abuse_report_sent_failed));
            }
        }
    }

    private void ExecuteTutorActionExtendReportAbuseSubMenu(Button buttonReportAbuse, LinearLayout linearLayoutReportAbuseReasons)
    {
        buttonReportAbuse.setText(R.string.question_requests_report_abuse);
        linearLayoutReportAbuseReasons.setVisibility(VISIBLE);
    }

    private void SetupAcceptSolutionFeature(LinearLayout.LayoutParams lp, LinearLayout.LayoutParams lpChild)
    {
        final LinearLayout linearLayoutAcceptSolution = new LinearLayout(context);
        linearLayoutAcceptSolution.setOrientation(LinearLayout.VERTICAL);
        linearLayoutAcceptSolution.setLayoutParams(lp);
        linearLayoutAcceptSolution.setGravity(Gravity.CENTER);

        Button buttonAcceptSolution = new Button(context);
        buttonAcceptSolution.setText(R.string.question_requests_accept_solution);
        FormatButton(buttonAcceptSolution);
        buttonAcceptSolution.setLayoutParams(lpChild);

        buttonAcceptSolution.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServerHelper.UpdateQuestionRequestState(crQuestionRequest.questionRequestID, GlobalVariables.QUESTION_REQUEST_STATE.ACCEPTED_BY_STUDENT))
                {
                    new CustomToast(activity, context, context.getString(R.string.question_requests_accepted_solution_successfully));
                    RemoveQuestionRequestLinearLayoutFromParent();
                }
                else
                {
                    GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_accepted_solution_failed));
                }
            }
        });

        linearLayoutAcceptSolution.addView(buttonAcceptSolution);
        this.addView(linearLayoutAcceptSolution);
    }

    private void SetupObjectionFeature(LinearLayout.LayoutParams lp, LinearLayout.LayoutParams lpChild)
    {
        final LinearLayout linearLayoutSolutionObjection = new LinearLayout(context);
        linearLayoutSolutionObjection.setOrientation(LinearLayout.VERTICAL);

        linearLayoutSolutionObjection.setLayoutParams(lp);
        linearLayoutSolutionObjection.setGravity(Gravity.CENTER);

        final LinearLayout linearLayoutSolutionObjectionReasons = new LinearLayout(context);
        linearLayoutSolutionObjectionReasons.setOrientation(LinearLayout.VERTICAL);
        linearLayoutSolutionObjectionReasons.setGravity(Gravity.CENTER);
        linearLayoutSolutionObjectionReasons.setLayoutParams(lp);
        final TextView textViewObjectionReasonsSubTitle = new TextView(context);
        textViewObjectionReasonsSubTitle.setTextColor(Color.BLACK);
        textViewObjectionReasonsSubTitle.setText(R.string.question_requests_objection_reason_objection_reasons);
        textViewObjectionReasonsSubTitle.setGravity(Gravity.CENTER);
        final RadioGroup rgObjectionReasons = new RadioGroup(context);
        final EditText editTextOtherObjectionReason = new EditText(context);
        editTextOtherObjectionReason.setVisibility(GONE);
        editTextOtherObjectionReason.setTextColor(Color.BLACK);

        for(int i = 0; i < GlobalVariables.questionRequestObjectionReasons.length; i++)
        {
            RadioButton crRadioButton = (RadioButton) LayoutInflater.from(activity).inflate(R.layout.simple_radio_button, null);
            crRadioButton.setId(105234+i);
            crRadioButton.setText(GlobalVariables.questionRequestObjectionReasons[i]);
            rgObjectionReasons.addView(crRadioButton);
        }

        rgObjectionReasons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                GlobalVariables.Log(context, "checkedId: "+checkedId  );  //+ "   "+context.getString(GlobalVariables.questionRequestObjectionReasons[checkedId-1]));
                String selectedRadioButtonText = ((RadioButton)findViewById(rgObjectionReasons.getCheckedRadioButtonId())).getText().toString();
                if(selectedRadioButtonText != context.getString(GlobalVariables.questionRequestObjectionReasons[GlobalVariables.questionRequestObjectionReasons.length-1]))
                {
                    objectionReason = selectedRadioButtonText;
                    if(editTextOtherObjectionReason.getVisibility() == VISIBLE)
                    {
                        editTextOtherObjectionReason.setVisibility(GONE);
                    }
                }
                else
                {
                    GlobalVariables.Log(context, "rgObjectionReasons OTHER before   step 1 a    objectionReason: "+objectionReason);
                    objectionReason = DB.KEY_OTHER_REASON;
                    GlobalVariables.Log(context, "rgObjectionReasons OTHER after    step 1 b    objectionReason: "+objectionReason);
                    editTextOtherObjectionReason.setVisibility(VISIBLE);
                    editTextOtherObjectionReason.requestFocus();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editTextOtherObjectionReason, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        final Button buttonObject = new Button(context);
        buttonObject.setText(R.string.question_requests_display_object);
        FormatButton(buttonObject);
        buttonObject.setLayoutParams(lpChild);

        buttonObject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonObject.getText().toString().equals(context.getString(R.string.question_requests_display_object)))
                {
                    GlobalVariables.Log(context, "buttonObject.setOnClickListener first");
                    ExecuteTutorActionExtendObjectSolutionSubMenu(buttonObject, linearLayoutSolutionObjectionReasons);
                }
                else
                {
                    GlobalVariables.Log(context, "buttonObject.setOnClickListener second");
                    if(objectionReason.equals(DB.KEY_OTHER_REASON))
                    {
                        if(!editTextOtherObjectionReason.getText().toString().isEmpty())
                        {
                            if(editTextOtherObjectionReason.getText().toString().length() >= 290)
                            {
                                new CustomToast(activity, context, context.getString(R.string.question_requests_objection_warning_other_objection_too_long));
                                return;
                            }
                            objectionReason += ": " + editTextOtherObjectionReason.getText().toString();
                            GlobalVariables.Log(context, "rgObjectionReasons OTHER step 2 a   objectionReason: "+objectionReason);
                        }
                        else
                        {
                            objectionReason = "";
                            GlobalVariables.Log(context, "rgObjectionReasons OTHER step 2 b   objectionReason: "+objectionReason);
                        }
                    }
                    ExecuteTutorActionSendObjectionForSolution(linearLayoutSolutionObjection);
                }
            }
        });

        textViewObjectionReasonsSubTitle.setLayoutParams(lpChild);
        rgObjectionReasons.setLayoutParams(lpChild);
        editTextOtherObjectionReason.setLayoutParams(lpChild);

        linearLayoutSolutionObjectionReasons.addView(textViewObjectionReasonsSubTitle);
        linearLayoutSolutionObjectionReasons.addView(rgObjectionReasons);
        linearLayoutSolutionObjectionReasons.addView(editTextOtherObjectionReason);
        linearLayoutSolutionObjectionReasons.setVisibility(GONE);

        linearLayoutSolutionObjection.addView(linearLayoutSolutionObjectionReasons);
        linearLayoutSolutionObjection.addView(buttonObject);
        this.addView(linearLayoutSolutionObjection);
    }

    private void ExecuteTutorActionSendObjectionForSolution(LinearLayout linearLayoutRequestObjection)
    {
        GlobalVariables.Log(context, "rgObjectionReasons OTHER step 3.1   objectionReason: "+objectionReason);
        if(objectionReason.isEmpty())
        {
            GlobalVariables.Log(context, "rgObjectionReasons OTHER step 3.2a   objectionReason: "+objectionReason);
            //objectionReason = DB.KEY_OTHER_REASON;
            new CustomToast(activity, context, context.getString(R.string.question_requests_please_select_a_objection_reason));
        }
        else
        {
            GlobalVariables.Log(context, "rgObjectionReasons OTHER step 3.2b   objectionReason: "+objectionReason);
            if(ServerHelper.SendObjection(GlobalVariables.USER_ID, crQuestionRequest.questionRequestID, objectionReason))
            {
                new CustomToast(activity, context, context.getString(R.string.question_requests_objection_sent_successfully));
                linearLayoutRequestObjection.setVisibility(GONE);
                RemoveQuestionRequestLinearLayoutFromParent();
            }
            else
            {
                GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.question_requests_objection_sent_failed));
            }
        }
    }

    private void ExecuteTutorActionExtendObjectSolutionSubMenu(Button buttonObject, LinearLayout linearLayoutRequestObjectionReasons)
    {
        GlobalVariables.Log(context, "ExecuteTutorActionExtendObjectSolutionSubMenu entered");
        buttonObject.setText(R.string.question_requests_display_send_objection);
        linearLayoutRequestObjectionReasons.setVisibility(VISIBLE);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfAcceptedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfSolutionObjectedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);

        String objectionResponse = ServerHelper.RetrieveObjectionResultResponse(crQuestionRequest.questionRequestID, context);
        LinearLayout linearLayoutObjectionResult = new LinearLayout(context);
        linearLayoutObjectionResult.setOrientation(LinearLayout.VERTICAL);

        TextView textViewObjectionResultSubTitle = new TextView(context);
        textViewObjectionResultSubTitle.setPadding(12,6,12,0);
        textViewObjectionResultSubTitle.setText(R.string.question_requests_objection_result_subtitle);
        FormatTextView(textViewObjectionResultSubTitle);
        TextView textViewObjectionResult = new TextView(context);
        textViewObjectionResult.setGravity(Gravity.CENTER);
        textViewObjectionResult.setPadding(12,0,12,6);
        textViewObjectionResult.setText(objectionResponse);
        FormatTextView(textViewObjectionResult);

        linearLayoutObjectionResult.addView(textViewObjectionResultSubTitle);
        linearLayoutObjectionResult.addView(textViewObjectionResult);
        this.addView(linearLayoutObjectionResult);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfReportedByStudent()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        String abuseReportResponse = ServerHelper.RetrieveAbuseReportResultResponse(crQuestionRequest.questionRequestID, context);
        LinearLayout linearLayoutAbuseReportResult = new LinearLayout(context);
        linearLayoutAbuseReportResult.setOrientation(LinearLayout.VERTICAL);

        TextView textViewAbuseReportResultSubTitle = new TextView(context);
        textViewAbuseReportResultSubTitle.setPadding(12,6,12,0);
        textViewAbuseReportResultSubTitle.setText(R.string.question_requests_abuse_report_result_subtitle);
        FormatTextView(textViewAbuseReportResultSubTitle);
        TextView textViewAbuseReportResult = new TextView(context);
        textViewAbuseReportResult.setGravity(Gravity.CENTER);
        textViewAbuseReportResult.setPadding(12,0,12,6);
        textViewAbuseReportResult.setText(abuseReportResponse);
        FormatTextView(textViewAbuseReportResult);

        linearLayoutAbuseReportResult.addView(textViewAbuseReportResultSubTitle);
        linearLayoutAbuseReportResult.addView(textViewAbuseReportResult);
        this.addView(linearLayoutAbuseReportResult);
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfSolutionJobCancelledByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //Offer the Same Question to Another Tutor
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfTimedOutForTutorSolution()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(false);

        //Offer the Same Question to Another Tutor
        SetUpRequestTheSameQuestionFromAnotherTutorAgainComponents();
    }

    private void ExecuteModificationsForStudentsQRDisplay_InCaseOfDirectlyPostedByTutor()
    {
        ActivateSetOnClickListenerForQuestionImageDisplay();
        ActivateSetOnClickListenerForSolutionVideoDisplay(true);
    }

    private void FormatButton(Button button)
    {
        if (Build.VERSION.SDK_INT < 23)
        {
            button.setTextAppearance(context, R.style.textStyleA_Title);
        }
        else
        {
            button.setTextAppearance(R.style.textStyleA_Title);
        }
        button.setPadding(14, 6, 14,6);
        button.setGravity(Gravity.CENTER);
        button.setTextSize(18f);
        button.setBackgroundResource(R.drawable.button_shape_1_selector);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setShadowLayer(10f, 2f, 2f, Color.parseColor("#2D3C6E"));
    }

    private void RemoveQuestionRequestLinearLayoutFromParent()
    {
        LinearLayout parent = ((LinearLayout)(this.getParent()));
        parent.removeView(this);
        if(parent.getChildCount() == 0)
        {
            TextView tvMessage = new TextView(context);
            if (Build.VERSION.SDK_INT < 23)
            {
                tvMessage.setTextAppearance(context, R.style.textStyleA_Vertical);
            }
            else
            {
                tvMessage.setTextAppearance(R.style.textStyleA_Vertical);
            }
            tvMessage.setGravity(Gravity.CENTER);
            tvMessage.setText(R.string.question_requests_display_no_question_request_in_this_category);
            tvMessage.setTextColor(Color.parseColor("#d65b07"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,16,0,0);
            tvMessage.setTextSize(28f);
            parent.addView(tvMessage);
        }
    }


}
