package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.util.List;

public class CustomSolutionOnSaleForShoppingCartLinearLayout extends LinearLayout
{
    Activity activity;
    Context context;
    int questionRequestID;
    SolutionOnSale crSolutionOnSale;
    TextView textViewVideoIcon, textViewQuestionIcon;
    LinearLayout linearLayoutQuestionIcon, linearLayoutVideoIcon;



    public CustomSolutionOnSaleForShoppingCartLinearLayout(final Activity activity, final Context context, int questionRequestID) {
        super(context);



        this.activity = activity;
        this.context = context;
        this.questionRequestID = questionRequestID;


        this.setOrientation(LinearLayout.VERTICAL);

        TextView textViewClass, textViewLesson, textViewPublisher, textViewBookName, textViewPageNo, textViewDisplayPrice, textViewReplierTutorName, textViewRating;
        ImageView imageViewQuestionImage, imageViewYoutube, imageViewPhoto;
        Button buttonAddToCart, buttonShowComments, buttonHideComments;
        TextView textViewCommentsWarning;
        ProgressBar progressBarCommentWaiting;
        final LinearLayout linearLayoutCommentsFeed;
        crSolutionOnSale = ServerHelper.GetSolutionOnSaleByQuestionRequestID(questionRequestID, context);
        crSolutionOnSale.displayPrice = ServerHelper.GetRecommendedDisplayPrice(GlobalVariables.USER_ID, questionRequestID);
        Double averageRating = ServerHelper.GetAverageRatingForSolution(questionRequestID);
        String averageRatingStr = "N/A";
        if(averageRating != -1.0)
        {
            averageRatingStr = "" + averageRating;
        }


        final LinearLayout child = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_solutions_feed_element, null);
        textViewClass = child.findViewById(R.id.textViewClass);
        textViewLesson = child.findViewById(R.id.textViewLesson);
        textViewPublisher = child.findViewById(R.id.textViewPublisher);
        textViewBookName = child.findViewById(R.id.textViewBookName);
        textViewPageNo = child.findViewById(R.id.textViewPageNo);
        textViewDisplayPrice = child.findViewById(R.id.textViewDisplayPrice);
        textViewReplierTutorName = child.findViewById(R.id.textViewReplierTutorName);
        textViewRating = child.findViewById(R.id.textViewRating);

        buttonAddToCart = child.findViewById(R.id.buttonAddToFavorites);
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

        textViewClass.setText(""+crSolutionOnSale.questionClass);
        textViewLesson.setText(crSolutionOnSale.questionLesson);
        textViewPublisher.setText(crSolutionOnSale.publisher);
        textViewBookName.setText(crSolutionOnSale.bookName);
        textViewPageNo.setText(""+crSolutionOnSale.pageNo);
        textViewDisplayPrice.setText(""+crSolutionOnSale.displayPrice);
        textViewReplierTutorName.setText(crSolutionOnSale.demandedTutorName);
        textViewRating.setText(averageRatingStr);


        int[] imageDrawableResourceIDs = new int[] { R.drawable.question_mark, R.drawable.yks_q, R.drawable.yks_q2 };
        //imageViewQuestionImage.setImageResource(imageDrawableResourceIDs[GlobalVariables.r.nextInt(imageDrawableResourceIDs.length)]);

        //imageViewQuestionImage.setImageBitmap(ServerHelper.GetMiniQuestionImageBitmapFromQuestionImageID(crSolutionOnSale.questionImageID));
        AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(context, crSolutionOnSale.questionRequestID, imageViewQuestionImage);
        //imageViewPhoto.setImageBitmap(ServerHelper.GetMiniUserPhotoBitmapFromQuestionImageID(crSolutionOnSale.questionRequestID));
        AsyncTaskHelper.DisplayTutorPhotoOnAlertDialogByQuestionRequestId(context, crSolutionOnSale.questionRequestID, imageViewPhoto);
        imageViewPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",crSolutionOnSale.questionClass + " - " +crSolutionOnSale.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetUserPhotoByQuestionRequestID(crSolutionOnSale.questionRequestID));
                AsyncTaskHelper.DisplayTutorPhotoOnAlertDialogByQuestionRequestId(activity, context, ((StudentShoppingCartActivity)activity).linearLayoutMainProgressBar, crSolutionOnSale.questionRequestID, true, false);
            }
        });

        //ADD TO CART BUTTON
        final SolutionOnSale fS = crSolutionOnSale;
        final Context fContext = context;
        final Button finalButtonAddToCart = buttonAddToCart;

                //set button text
        if(((StudentShoppingCartActivity)activity).questionRequestIDsForSolutionsOnSaleOnStudentCart.contains((Integer) (fS.questionRequestID)))
        {
            finalButtonAddToCart.setText(R.string.tutor_discovery_remove_from_favorites);
        }
        else
        {
            finalButtonAddToCart.setText(R.string.shopping_cart_add_to_cart);
        }

                //set button onClickListener
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((StudentShoppingCartActivity)activity).questionRequestIDsForSolutionsOnSaleOnStudentCart.contains((Integer) (fS.questionRequestID)))
                {
                    if(ServerHelper.RemoveSolutionFromCart(GlobalVariables.USER_ID, fS.questionRequestID))
                    {
                        GlobalVariables.Toast(fContext, fContext.getString(R.string.solution_removed_from_your_cart_succesfully));
                        ((StudentShoppingCartActivity)(activity)).questionRequestIDsForSolutionsOnSaleOnStudentCart.remove((Integer) (fS.questionRequestID));
                        finalButtonAddToCart.setText(R.string.shopping_cart_add_to_cart);
                    }
                    else
                    {
                        GlobalVariables.Toast(fContext, fContext.getString(R.string.solution_failed_to_be_removed_from_your_cart));
                    }
                }
                else
                {
                    if(ServerHelper.AddSolutionToCart(GlobalVariables.USER_ID, fS.questionRequestID, fS.displayPrice))
                    {
                        GlobalVariables.Toast(fContext, fContext.getString(R.string.solution_added_to_your_cart_succesfully));
                        ((StudentShoppingCartActivity)(activity)).questionRequestIDsForSolutionsOnSaleOnStudentCart.add((Integer) (fS.questionRequestID));
                        finalButtonAddToCart.setText(R.string.tutor_discovery_remove_from_favorites);
                    }
                    else
                    {
                        GlobalVariables.Toast(fContext, fContext.getString(R.string.solution_failed_to_be_added_to_your_cart));
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
        buttonShowComments.setOnClickListener(new View.OnClickListener() {
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

                            if(!((StudentShoppingCartActivity)fActivity).ExtendWithNewComments(child, crSolutionOnSale))
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
                    if(!((StudentShoppingCartActivity)fActivity).ExtendWithNewComments(child, crSolutionOnSale))
                    {
                        fTextViewCommentsWarning.setVisibility(View.VISIBLE);
                        fTextViewCommentsWarning.setText(R.string.tutor_discovery_no_more_comment);
                    }
                }
            }
        });

        buttonHideComments.setOnClickListener(new View.OnClickListener() {
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
                //GlobalVariables.Log(context, "on linearLayoutQuestionIcon.setOnClickListener >>> crSolutionOnSale.questionImageID: "+crSolutionOnSale.questionImageID);
                AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((StudentShoppingCartActivity)activity).linearLayoutMainProgressBar, crSolutionOnSale.questionRequestID, true, false);
                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",crSolutionOnSale.questionClass + " - " +crSolutionOnSale.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetBitmapFromQuestionImageID(crSolutionOnSale.questionImageID));
            }
        });


        linearLayoutVideoIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.PURCHASE_RESPONSE response = ServerHelper.TryPurchasingTheSolution(context, GlobalVariables.USER_ID, crSolutionOnSale.displayPrice, crSolutionOnSale.questionRequestID);
                if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING)
                {
                    PurchaseSolution(GlobalVariables.USER_ID, false, crSolutionOnSale.questionRequestID, crSolutionOnSale.questionAnswerVideoURL);
                }
                else if(response == GlobalVariables.PURCHASE_RESPONSE.PURCHASING_FREE)
                {
                    PurchaseSolution(GlobalVariables.USER_ID, true, crSolutionOnSale.questionRequestID, crSolutionOnSale.questionAnswerVideoURL);
                }
                else if(response == GlobalVariables.PURCHASE_RESPONSE.CONNECTION_FAILED)
                {
                    GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
                }
                else if(response == GlobalVariables.PURCHASE_RESPONSE.ALREADY_PURCHASED)
                {
                    ServerHelper.AddToPurchasedSolutions(GlobalVariables.USER_ID, crSolutionOnSale.questionRequestID, crSolutionOnSale.displayPrice);
                    CommonUtils.DisplayYoutubeSolutionVideo(crSolutionOnSale.questionAnswerVideoURL, context);
                }
                else if(response == GlobalVariables.PURCHASE_RESPONSE.INSUFFICIENT_BALANCE)
                {
                    GlobalVariables.AlertDialogDisplay(activity, context.getString(R.string.shopping_cart_insufficient_balance) + " (₺"+crSolutionOnSale.displayPrice+")");
                }
            }
        });

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 30, 10, 0);
        this.setLayoutParams(lp);

        this.addView(child);
    }

    private void PurchaseSolution(int userId, boolean isFree, int questionRequestID, String solutionVideoURL)
    {
        double displayPrice = isFree ? 1 : 0.5;
        if(ServerHelper.AddToPurchasedSolutions(userId, questionRequestID, displayPrice))
        {
            if(ServerHelper.AppropriateThePrice(context, userId, isFree, GlobalVariables.PRODUCT.SOLUTION))
            {
                SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.PURCHASE);
                ServerHelper.RemoveSolutionFromCart(userId, questionRequestID);
                ((StudentShoppingCartActivity)activity).questionRequestIDsForSolutionsOnSaleOnStudentCart.remove((Integer) questionRequestID);
                CommonUtils.DisplayYoutubeSolutionVideo(solutionVideoURL, context);
            }
            else
            {
                ServerHelper.RemoveFromPurchasedSolutions(userId, questionRequestID);
                GlobalVariables.AlertDialogDisplay(context, context.getString(R.string.shopping_cart_failed_to_purchase_please_try_again));
            }
        }
    }


}
