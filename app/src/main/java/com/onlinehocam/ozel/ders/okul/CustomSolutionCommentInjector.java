package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomSolutionCommentInjector
{
    Activity activity;
    Context context;
    int tutorID;
    List<String> suggestedCommentIDs;
    ProgressBar progressBarCommentWaiting;

    int stepNo;
    List<Comment> commentsList;
    List<Comment> additionalCommentsList;
    List<LinearLayout> commentDisplayLinearLayouts;
    List<LinearLayout> additionalCommentDisplayLinearLayouts;



    public CustomSolutionCommentInjector(Activity activity, Context context, int tutorID, List<String> suggestedCommentIDs, ProgressBar progressBarCommentWaiting) {
        this.activity = activity;
        this.context = context;
        this.tutorID = tutorID;
        this.suggestedCommentIDs = suggestedCommentIDs;
        this.progressBarCommentWaiting = progressBarCommentWaiting;

        stepNo = 0;
        commentsList = new ArrayList<>();
        additionalCommentsList = new ArrayList<>();
        commentDisplayLinearLayouts = new ArrayList<>();
        additionalCommentDisplayLinearLayouts = new ArrayList<>();
    }

    private LinearLayout GetCommentLinearLayoutByCommentObject(Comment c)
    {
        LinearLayout result = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.custom_solution_comment_feed_element, null);

        TextView textViewRating = result.findViewById(R.id.textViewRating);
        TextView textViewMessage = result.findViewById(R.id.textViewMessage);
        TextView textViewClassLesson = result.findViewById(R.id.textViewClassLesson);
        TextView textViewCommentatorStudent = result.findViewById(R.id.textViewCommentatorStudent);
        TextView buttonDisplayQuestion = result.findViewById(R.id.buttonDisplayQuestion);

        textViewRating.setText("" + c.rating);
        textViewClassLesson.setText(context.getString(R.string.question_requests_class)+" "+c.questionClass+"\n"
                +context.getString(R.string.question_requests_lesson)+" "+c.questionLesson);
        textViewMessage.setText(c.commentText);
        textViewCommentatorStudent.setText(c.studentName);

        final Comment finalC = c;

        buttonDisplayQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GlobalVariables.AlertDialogQuestionDisplay(activity, true, false,"",finalC.questionClass + " - " +finalC.questionLesson, context.getString(R.string.constant_ok), "", ServerHelper.GetBitmapFromQuestionImageID(ServerHelper.GetQuestionImageIDByQuestionRequestID(finalC.questionRequestID)));
                AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(activity, context, ((StudentPopularQuestionsDisplayActivity)activity).linearLayoutMainProgressBar, finalC.questionRequestID, true, false);
            }
        });

        SetBorderToLayout(result, 4, Color.BLACK);


        result.setPadding(8,18,8,18);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = 10;
        result.setLayoutParams(lp);

        return result;
    }


    void SetBorderToLayout(LinearLayout linearLayout, int strokeTickness, int strokeColorCode)
    {
        //use a GradientDrawable with only one color set, to make it a solid color
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        border.setStroke(strokeTickness, strokeColorCode); //black border with full opacity
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(border);
        } else {
            linearLayout.setBackground(border);
        }
    }


    void InsertAdditionalLinearLayoutsForAdditionalComments(LinearLayout linearLayoutCommentsFeed)
    {
        if(stepNo>3) // Max 40 Comments Displayed
        {
            for (int i = 0; i < Math.min(commentDisplayLinearLayouts.size(), GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY); i++)
            {
                linearLayoutCommentsFeed.removeView(commentDisplayLinearLayouts.get(i));
            }
            for (int i = 0; i < Math.min(commentDisplayLinearLayouts.size(), GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY); i++)
            {
                commentDisplayLinearLayouts.remove(commentDisplayLinearLayouts.get(0));
            }
        }
        for (int i = 0; i < additionalCommentDisplayLinearLayouts.size(); i++)
        {
            linearLayoutCommentsFeed.addView(additionalCommentDisplayLinearLayouts.get(i));
        }
        stepNo++;

        linearLayoutCommentsFeed.invalidate();
    }



    private List<String> GetCommentIdsForThisStep(List<String> suggestedCommentIDs)
    {
        List<String> result = new ArrayList<>();
        for(int i = GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY * stepNo ; i < Math.min(GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY * (stepNo+1), suggestedCommentIDs.size()); i++)
        {
            result.add(suggestedCommentIDs.get(i));
        }
        return result;
    }




    private List<LinearLayout> GetCommentDisplayLinearLayoutsListWithCommentObjectList(List<Comment> additionalCommentsList)
    {
        List<LinearLayout> result = new ArrayList<>();

        for (int i = 0; i < additionalCommentsList.size(); i++)
        {
            result.add(GetCommentLinearLayoutByCommentObject(additionalCommentsList.get(i)));
        }

        return result;
    }

    public boolean ExtendWithNewComments(CustomTutorLinearLayout crCustomTutorLinearLayout)
    {
        LinearLayout linearLayoutCommentsFeed = crCustomTutorLinearLayout.findViewById(R.id.linearLayoutCommentsFeed);
        progressBarCommentWaiting.setVisibility(View.VISIBLE);


        if(suggestedCommentIDs.size() - GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY * stepNo > 0)
        {
            additionalCommentsList = ServerHelper.GetCommentListWithCommentIDsList(GetCommentIdsForThisStep(suggestedCommentIDs));
            for (int i = 0; i < additionalCommentsList.size(); i++)
            {
                commentsList.add(additionalCommentsList.get(i));
            }
            additionalCommentDisplayLinearLayouts = GetCommentDisplayLinearLayoutsListWithCommentObjectList(additionalCommentsList);
            for (int i = 0; i < additionalCommentDisplayLinearLayouts.size(); i++)
            {
                commentDisplayLinearLayouts.add(additionalCommentDisplayLinearLayouts.get(i));
            }

            InsertAdditionalLinearLayoutsForAdditionalComments(linearLayoutCommentsFeed);
        }
        else
        {
            progressBarCommentWaiting.setVisibility(View.GONE);
            return false;
        }

        progressBarCommentWaiting.setVisibility(View.GONE);
        return true;
    }

    public boolean ExtendWithNewComments(LinearLayout customSolutionOnSaleLinearLayoutChild)
    {
        LinearLayout linearLayoutCommentsFeed = customSolutionOnSaleLinearLayoutChild.findViewById(R.id.linearLayoutCommentsFeed);
        progressBarCommentWaiting.setVisibility(View.VISIBLE);


        if(suggestedCommentIDs.size() - GlobalVariables.COMMENTS_AT_A_TIME_TO_DISPLAY * stepNo > 0)
        {
            additionalCommentsList = ServerHelper.GetCommentListWithCommentIDsList(GetCommentIdsForThisStep(suggestedCommentIDs));
            for (int i = 0; i < additionalCommentsList.size(); i++)
            {
                commentsList.add(additionalCommentsList.get(i));
            }

            List<String> crSuggestedCommentIDs = GetCommentIdsForThisStep(suggestedCommentIDs);

            String cQRIDs = "";

            for(String id : crSuggestedCommentIDs)
            {
                cQRIDs += id + ", ";
            }
            GlobalVariables.Log(context, cQRIDs);

            additionalCommentDisplayLinearLayouts = GetCommentDisplayLinearLayoutsListWithCommentObjectList(additionalCommentsList);
            for (int i = 0; i < additionalCommentDisplayLinearLayouts.size(); i++)
            {
                commentDisplayLinearLayouts.add(additionalCommentDisplayLinearLayouts.get(i));
            }

            InsertAdditionalLinearLayoutsForAdditionalComments(linearLayoutCommentsFeed);
        }
        else
        {
            progressBarCommentWaiting.setVisibility(View.GONE);
            return false;
        }

        progressBarCommentWaiting.setVisibility(View.GONE);
        return true;
    }
}
