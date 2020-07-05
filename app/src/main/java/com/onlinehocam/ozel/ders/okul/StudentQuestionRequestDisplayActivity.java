package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentQuestionRequestDisplayActivity extends AppCompatActivity {

    List<Integer> questionRequestIDsListAllInOne;
    List<int[]> questionRequestIDArraysList;
    Map<Integer, List<LinearLayout>> mapOfLinearLayoutLists;
    /*
    int[] notAnsweredYetQuestionRequests;
    int[] pendingForAnswerQuestions;
    int[] pendingApprovalAnswers;
    int[] approvedAnswers;
    int[] rejectedAnswers;
    int[] questionsYouRejected;*/

    LinearLayout expandableListViewRequestedQuestions;
    List<String> questionRequestCategories;
    Map<String, LinearLayout> mapQuestionCategories;
    Map<String, List<LinearLayout>> mapQuestionRequests;

    LinearLayout linearLayoutMainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question_request_display);

        InstantiateComponents();

        InstantiateQuestionRequestIDArrays();

        FillData();

        ActivateInjection();
    }


    private void ActivateInjection()
    {
        new CustomExpandableListLayoutInjector(getApplicationContext(), expandableListViewRequestedQuestions, questionRequestCategories, mapQuestionCategories, mapQuestionRequests);
    }

    private void InstantiateQuestionRequestIDArrays()
    {
        questionRequestIDsListAllInOne = ServerHelper.GetQuestionRequestIDsForTheGivenStudentID(GlobalVariables.USER_ID);
        /*notAnsweredYetQuestionRequests = questionRequestIDArraysList.get(0);
        pendingApprovalAnswers = questionRequestIDArraysList.get(1);
        pendingForAnswerQuestions = questionRequestIDArraysList.get(2);
        approvedAnswers = questionRequestIDArraysList.get(3);
        rejectedAnswers = questionRequestIDArraysList.get(4);
        questionsYouRejected = questionRequestIDArraysList.get(5);*/
    }

    private void InstantiateComponents()
    {
        expandableListViewRequestedQuestions = findViewById(R.id.expandableListViewRequestedQuestions);

        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
    }

    private void FillData()
    {
        questionRequestCategories = new ArrayList<>();
        mapQuestionCategories = new HashMap<>();
        mapQuestionRequests = new HashMap<>();

        for(int i = 0; i < GlobalVariables.questionRequestStatusAccordingToStudents.length; i++)
        {
            questionRequestCategories.add(getString(GlobalVariables.questionRequestStatusAccordingToStudents[i]));
        }


        mapOfLinearLayoutLists = new HashMap<>();



        List<LinearLayout> linearLayoutsListAllInOne = new ArrayList<>();

        List<LinearLayout> linearLayoutNotAnsweredYetQuestionRequests = new ArrayList<>();
        List<LinearLayout> linearLayoutPendingForAnswerQuestions = new ArrayList<>();
        List<LinearLayout> linearLayoutPendingApprovalAnswers = new ArrayList<>();
        List<LinearLayout> linearLayoutApprovedAnswers = new ArrayList<>();
        List<LinearLayout> linearLayoutRejectedAnswers = new ArrayList<>();
        List<LinearLayout> linearLayoutQuestionsYouRejected = new ArrayList<>();
        List<LinearLayout> linearLayoutCanceledOrTimedOut = new ArrayList<>();


        mapOfLinearLayoutLists.put(0, linearLayoutNotAnsweredYetQuestionRequests);
        mapOfLinearLayoutLists.put(1, linearLayoutPendingForAnswerQuestions);
        mapOfLinearLayoutLists.put(2, linearLayoutPendingApprovalAnswers);
        mapOfLinearLayoutLists.put(3, linearLayoutApprovedAnswers);
        mapOfLinearLayoutLists.put(4, linearLayoutRejectedAnswers);
        mapOfLinearLayoutLists.put(5, linearLayoutQuestionsYouRejected);
        mapOfLinearLayoutLists.put(6, linearLayoutCanceledOrTimedOut);


        for(int i = 0; i < questionRequestIDsListAllInOne.size(); i++)
        {
            CustomQuestionAnswerLinearLayout crCustomQuestionAnswerLinearLayout = new CustomQuestionAnswerLinearLayout(this, getApplicationContext(), questionRequestIDsListAllInOne.get(i));
            DistributeQuestionAnswerLinearLayoutBasedOnItsQuestionRequestState(crCustomQuestionAnswerLinearLayout);
        }



        mapQuestionCategories.put(questionRequestCategories.get(0), GetCrCategoryLinearLayout(questionRequestCategories.get(0)));
        mapQuestionCategories.put(questionRequestCategories.get(1), GetCrCategoryLinearLayout(questionRequestCategories.get(1)));
        mapQuestionCategories.put(questionRequestCategories.get(2), GetCrCategoryLinearLayout(questionRequestCategories.get(2)));
        mapQuestionCategories.put(questionRequestCategories.get(3), GetCrCategoryLinearLayout(questionRequestCategories.get(3)));
        mapQuestionCategories.put(questionRequestCategories.get(4), GetCrCategoryLinearLayout(questionRequestCategories.get(4)));
        mapQuestionCategories.put(questionRequestCategories.get(5), GetCrCategoryLinearLayout(questionRequestCategories.get(5)));
        mapQuestionCategories.put(questionRequestCategories.get(6), GetCrCategoryLinearLayout(questionRequestCategories.get(6)));


        mapQuestionRequests.put(questionRequestCategories.get(0), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(0)));
        mapQuestionRequests.put(questionRequestCategories.get(1), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(1)));
        mapQuestionRequests.put(questionRequestCategories.get(2), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(2)));
        mapQuestionRequests.put(questionRequestCategories.get(3), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(3)));
        mapQuestionRequests.put(questionRequestCategories.get(4), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(4)));
        mapQuestionRequests.put(questionRequestCategories.get(5), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(5)));
        mapQuestionRequests.put(questionRequestCategories.get(6), (List<LinearLayout>) mapOfLinearLayoutLists.get(new Integer(6)));


/*
        mapQuestionRequests.put(questionRequestCategories.get(0), linearLayoutNotAnsweredYetQuestionRequests);
        mapQuestionRequests.put(questionRequestCategories.get(1), linearLayoutPendingApprovalAnswers);
        mapQuestionRequests.put(questionRequestCategories.get(2), linearLayoutPendingForAnswerQuestions);
        mapQuestionRequests.put(questionRequestCategories.get(3), linearLayoutApprovedAnswers);
        mapQuestionRequests.put(questionRequestCategories.get(4), linearLayoutRejectedAnswers);
        mapQuestionRequests.put(questionRequestCategories.get(5), linearLayoutQuestionsYouRejected);*/



    }


    private void DistributeQuestionAnswerLinearLayoutBasedOnItsQuestionRequestState(CustomQuestionAnswerLinearLayout crCustomQuestionAnswerLinearLayout)
    {
        if(crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_TUTOR_ACCEPTANCE)
        {
            mapOfLinearLayoutLists.get(0).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_ACCEPTED_BY_TUTOR_AND_STUDENT_PENDING_FOR_TUTORS_SOLUTION)
        {
            mapOfLinearLayoutLists.get(1).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_STUDENT_ACCEPTANCE)
        {
            mapOfLinearLayoutLists.get(2).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.ACCEPTED_BY_STUDENT)
        {
            mapOfLinearLayoutLists.get(3).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_OBJECTED_BY_STUDENT ||
                crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REPORTED_BY_STUDENT)
        {
            mapOfLinearLayoutLists.get(4).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_REJECTED_BY_TUTOR ||
                crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.SOLUTION_JOB_CANCELLED_BY_TUTOR)
        {
            mapOfLinearLayoutLists.get(5).add(crCustomQuestionAnswerLinearLayout);
        }
        else if (crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.REQUEST_CANCELED_BY_STUDENT ||
                crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_SOLUTION ||
                crCustomQuestionAnswerLinearLayout.crQuestionRequest.questionRequestState == GlobalVariables.QUESTION_REQUEST_STATE.TIMED_OUT_FOR_TUTOR_ACCEPTANCE)
        {
            mapOfLinearLayoutLists.get(6).add(crCustomQuestionAnswerLinearLayout);
        }
    }




    LinearLayout GetCrCategoryLinearLayout(String title)
    {
        LinearLayout result = new LinearLayout(getApplicationContext());
        result.setWeightSum(8);
        result.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = new TextView(getApplicationContext());
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        SetStyleToTextView(tv, R.style.textStyleA_Vertical);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(R.drawable.group_indicator_down);

        LinearLayout.LayoutParams lpMain = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpText = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 7f);
        LinearLayout.LayoutParams lpIcon = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        result.setLayoutParams(lpMain);
        tv.setLayoutParams(lpText);
        iv.setLayoutParams(lpIcon);

        result.addView(tv);
        result.addView(iv);

        return result;
    }

    void SetStyleToTextView(TextView tv, int styleResID)
    {
        if (Build.VERSION.SDK_INT < 23)
        {
            tv.setTextAppearance(getApplicationContext(), styleResID);
        }
        else
        {
            tv.setTextAppearance(styleResID);
        }
    }


}
