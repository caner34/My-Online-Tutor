package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.util.Arrays;
import java.util.List;

public class CustomPurchasedSolutionsLinearLayout extends LinearLayout
{
    Activity activity;
    Context context;
    int questionRequestID;
    SolutionOnSale crSolutionOnSale;
    TextView textViewVideoIcon, textViewQuestionIcon;
    LinearLayout linearLayoutQuestionIcon, linearLayoutVideoIcon;



    public CustomPurchasedSolutionsLinearLayout(final Activity activity, final Context context, int questionRequestID) {
        super(context);

        //GlobalVariables.Log(context, "questionRequestID >>> for getting crSolutionOnSale questionRequestID: "+questionRequestID);

        this.activity = activity;
        this.context = context;
        this.questionRequestID = questionRequestID;

        final CustomPurchasedSolutionsLinearLayout crCustomSolutionOnSaleForPopularRecommendedLinearLayout = this;

        this.setOrientation(LinearLayout.VERTICAL);

        TextView textViewClass, textViewLesson, textViewPublisher, textViewBookName, textViewPageNo, textViewDisplayPrice, textViewReplierTutorName, textViewRating;
        ImageView imageViewQuestionImage, imageViewYoutube, imageViewPhoto;
        final Button buttonGradeSolutions, buttonShowComments, buttonHideComments;
        final RatingBar ratingBar;
        TextView texViewYourRating;
        TextView textViewCommentsWarning;
        ProgressBar progressBarCommentWaiting;
        final EditText editTextStudentComment;
        final LinearLayout linearLayoutCommentsFeed;
        crSolutionOnSale = ServerHelper.GetSolutionOnSaleByQuestionRequestID(questionRequestID, context);
        crSolutionOnSale.displayPrice = ServerHelper.GetRecommendedDisplayPrice(GlobalVariables.USER_ID, questionRequestID);
        Double averageRating = ServerHelper.GetAverageRatingForSolution(questionRequestID);
        String averageRatingStr = "N/A";
        if(averageRating != -1.0)
        {
            averageRatingStr = "" + averageRating;
        }


        final LinearLayout child = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_purchased_solutions_feed_element, null);
        textViewClass = child.findViewById(R.id.textViewClass);
        textViewLesson = child.findViewById(R.id.textViewLesson);
        textViewPublisher = child.findViewById(R.id.textViewPublisher);
        textViewBookName = child.findViewById(R.id.textViewBookName);
        textViewPageNo = child.findViewById(R.id.textViewPageNo);
        textViewDisplayPrice = child.findViewById(R.id.textViewDisplayPrice);
        textViewReplierTutorName = child.findViewById(R.id.textViewReplierTutorName);
        textViewRating = child.findViewById(R.id.textViewRating);

        buttonGradeSolutions = child.findViewById(R.id.buttonGradeSolutions);
        ratingBar = child.findViewById(R.id.ratingBar);
        editTextStudentComment = child.findViewById(R.id.editTextStudentComment);
        texViewYourRating = child.findViewById(R.id.texViewYourRating);
        buttonShowComments = child.findViewById(R.id.buttonShowComments);
        buttonHideComments = child.findViewById(R.id.buttonHideComments);

        linearLayoutQuestionIcon = child.findViewById(R.id.linearLayoutQuestionIcon);
        linearLayoutVideoIcon = child.findViewById(R.id.linearLayoutVideoIcon);

        imageViewQuestionImage = child.findViewById(R.id.imageViewQuestionImage);
        imageViewYoutube = child.findViewById(R.id.imageViewYoutube);

        imageViewPhoto = child.findViewById(R.id.imageViewPhoto);

        textViewCommentsWarning = child.findViewById(R.id.textViewCommentsWarning);
        progressBarCommentWaiting = child.findViewById(R.id.progressBarCommentWaiting);
        linearLayoutCommentsFeed = child.findViewById(R.id.linearLayoutCommentsFeed);

        String className = LessonsAndClasses.CLASS_NAMES[Arrays.asList(LessonsAndClasses.CLASSES.values()).indexOf(crSolutionOnSale.questionClass)];
        textViewClass.setText(className);
        textViewLesson.setText(crSolutionOnSale.questionLesson);
        textViewPublisher.setText(crSolutionOnSale.publisher);
        textViewBookName.setText(crSolutionOnSale.bookName);
        textViewPageNo.setText(""+crSolutionOnSale.pageNo);
        textViewDisplayPrice.setText(""+crSolutionOnSale.displayPrice);
        textViewReplierTutorName.setText(crSolutionOnSale.demandedTutorName);
        textViewRating.setText(averageRatingStr);

        final EditText finalEditTextStudentComment = editTextStudentComment;

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser && rating > 0.0f)
                {
                    finalEditTextStudentComment.setVisibility(VISIBLE);
                }
            }
        });

        editTextStudentComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextStudentComment.getText().toString().length() == 0)
                {
                    buttonGradeSolutions.setText(R.string.menu_purchased_solutions_grade_solution);
                }
                else
                {
                    buttonGradeSolutions.setText(R.string.menu_purchased_solutions_grade_and_comment_solution);
                }

                if(editTextStudentComment.getText().toString().length() > 150)
                {
                    editTextStudentComment.setText(editTextStudentComment.getText().toString().substring(0, 148));
                    editTextStudentComment.setSelection(editTextStudentComment.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }) ;


        //Grade BUTTON
        final SolutionOnSale fS = crSolutionOnSale;
        final Context fContext = context;
        final Button finalButtonGradeSolutions = buttonGradeSolutions;
        final RatingBar finalRatingBar = ratingBar;


        AsyncTaskHelper.GetGradeForSolution(activity,context, GlobalVariables.USER_ID, crSolutionOnSale.questionRequestID, finalButtonGradeSolutions, finalRatingBar, texViewYourRating);

        AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(context, crSolutionOnSale.questionRequestID, imageViewQuestionImage);
        AsyncTaskHelper.DisplayTutorPhotoOnAlertDialogByQuestionRequestId(context, crSolutionOnSale.questionRequestID, imageViewPhoto);
        imageViewPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",crSolutionOnSale.questionClass + " - " +crSolutionOnSale.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetUserPhotoByQuestionRequestID(crSolutionOnSale.questionRequestID));
                if(activity instanceof StudentShoppingCartActivity)
                {
                    AsyncTaskHelper.DisplayTutorPhotoOnAlertDialogByQuestionRequestId(activity, context, ((StudentShoppingCartActivity)activity).linearLayoutMainProgressBar, crSolutionOnSale.questionRequestID, true, false);
                }
                else if(activity instanceof StudentPurchasedSolutionsActivity)
                {
                    AsyncTaskHelper.DisplayTutorPhotoOnAlertDialogByQuestionRequestId(activity, context, ((StudentPurchasedSolutionsActivity)activity).linearLayoutMainProgressBar, crSolutionOnSale.questionRequestID, true, false);
                }
            }
        });




        //set button onClickListener

        finalButtonGradeSolutions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalRatingBar.getVisibility() == GONE)
                {
                    finalRatingBar.setVisibility(VISIBLE);
                    finalButtonGradeSolutions.setText(R.string.menu_purchased_solutions_send_solution_grade);
                }
                else
                {
                    if (finalRatingBar.getRating() == 0.0f)
                    {
                        new CustomToast(activity, context, context.getString(R.string.menu_purchased_solutions_warning_select_solution_grade_first));
                    }
                    else
                    {
                        if(finalEditTextStudentComment.getText().toString().length() > 150)
                        {
                            new CustomToast(activity, context, context.getString(R.string.menu_purchased_solutions_warning_comment_max_size_150));
                        }
                        else
                        {
                            buttonGradeSolutions.setEnabled(false);
                            AsyncTaskHelper.SendGradeForSolution(activity, context, finalRatingBar.getRating(), GlobalVariables.USER_ID, crSolutionOnSale.questionRequestID, buttonGradeSolutions, finalRatingBar, finalEditTextStudentComment);
                        }

                    }
                }
            }
        });


        final Activity fActivity = activity;
        final TextView fTextViewCommentsWarning = textViewCommentsWarning;
        final Button fButtonHideComments = buttonHideComments;
        final Button fButtonShowComments = buttonShowComments;
        final ProgressBar fProgressBarCommentWaiting = progressBarCommentWaiting;

        final List<String> suggestedCommentIDs = crSolutionOnSale.commentIDs;
        buttonShowComments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutCommentsFeed.getVisibility() == View.GONE)
                {
                    linearLayoutCommentsFeed.setVisibility(View.VISIBLE);
                }

                if(!crSolutionOnSale.isShowCommentTriggered)
                {
                    if(suggestedCommentIDs.isEmpty())
                    {
                        fTextViewCommentsWarning.setVisibility(View.VISIBLE);
                        fTextViewCommentsWarning.setText(R.string.tutor_discovery_no_comment);
                    }
                    else
                    {
                        if(crSolutionOnSale.customSolutionCommentInjector == null)
                        {
                            crSolutionOnSale.customSolutionCommentInjector = new CustomSolutionCommentInjector(activity, fContext, -1, suggestedCommentIDs, fProgressBarCommentWaiting);
                            fButtonHideComments.setVisibility(View.VISIBLE);
                            fButtonShowComments.setText(R.string.tutor_discovery_show_more_comments);
                            crSolutionOnSale.isShowCommentTriggered = true;

                            if(!((StudentPurchasedSolutionsActivity)fActivity).ExtendWithNewComments(child, crSolutionOnSale))
                            {
                                fTextViewCommentsWarning.setVisibility(View.VISIBLE);
                                fTextViewCommentsWarning.setText(R.string.tutor_discovery_no_more_comment);
                            }
                        }
                        else
                        {
                            fButtonHideComments.setVisibility(View.VISIBLE);
                            linearLayoutCommentsFeed.setVisibility(View.VISIBLE);
                            fButtonShowComments.setText(R.string.tutor_discovery_show_more_comments);
                            crSolutionOnSale.isShowCommentTriggered = true;
                        }
                    }
                }
                else
                {
                    if(!((StudentPurchasedSolutionsActivity)fActivity).ExtendWithNewComments(child, crSolutionOnSale))
                    {
                        fTextViewCommentsWarning.setVisibility(View.VISIBLE);
                        fTextViewCommentsWarning.setText(R.string.tutor_discovery_no_more_comment);
                    }
                }
            }
        });

        buttonHideComments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fButtonHideComments.setVisibility(View.GONE);
                if(fTextViewCommentsWarning.getVisibility() == View.VISIBLE)
                {
                    fTextViewCommentsWarning.setVisibility(View.GONE);
                }
                /*for(int i = 0; i < linearLayoutCommentsFeed.getChildCount(); i++)
                {
                    linearLayoutCommentsFeed.removeView(linearLayoutCommentsFeed.getChildAt(0));
                }*/
                linearLayoutCommentsFeed.setVisibility(View.GONE);
                fButtonShowComments.setText(R.string.tutor_discovery_show_comments);
                crSolutionOnSale.isShowCommentTriggered = false;
            }
        });

        linearLayoutQuestionIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((StudentPurchasedSolutionsActivity)activity).linearLayoutMainProgressBar, crSolutionOnSale, crCustomSolutionOnSaleForPopularRecommendedLinearLayout, crSolutionOnSale.questionRequestID, true, false);
                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",crSolutionOnSale.questionClass + " - " +crSolutionOnSale.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetBitmapFromQuestionImageID(crSolutionOnSale.questionImageID));
            }
        });

        linearLayoutVideoIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayYoutubeSolutionVideo(crSolutionOnSale.questionAnswerVideoURL);
            }
        });


        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 30, 10, 0);
        this.setLayoutParams(lp);

        this.addView(child);
    }

    /*
    public void PurchaseSolution(int userId, boolean isFree, int questionRequestID, String solutionVideoURL)
    {
        double displayPrice = isFree ? 1 : 0.5;
        if(ServerHelper.AddToPurchasedSolutions(userId, questionRequestID, displayPrice))
        {
            if(ServerHelper.AppropriateThePrice(context, userId, isFree, GlobalVariables.PRODUCT.SOLUTION))
            {
                SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.PURCHASE);
                ServerHelper.RemoveSolutionFromCart(userId, questionRequestID);
                ((StudentPurchasedSolutionsActivity)activity).questionRequestIDsForSolutionsOnSaleOnStudentCart.remove((Integer) questionRequestID);
                DisplayYoutubeSolutionVideo(solutionVideoURL);
            }
            else
            {
                ServerHelper.RemoveFromPurchasedSolutions(userId, questionRequestID);
                GlobalVariables.AlertDialogDisplay(context, context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
            }
        }
    }
*/

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


}
