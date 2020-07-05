package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class StudentPopularQuestionsDisplayActivity extends AppCompatActivity {


    TextView textViewActivityTitle;
    LinearLayout linearLayoutSolutionElementsFeed;
    ProgressBar progressBarSolutionElementLoading;
    Button buttonShowMoreSolutionOnSale;
    TextView textViewShowMoreSolutionOnSaleMessage;
    ScrollView scrollViewForSolutions;

    public LinearLayout linearLayoutMainProgressBar;

    List<Integer> questionRequestIDsForRecommendedSolutionsOnSale;
    List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart;

    CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_popular_questions_display);

        InitializeComponents();

        LoadNewCustomSolutionOnSaleLinearLayoutFeedContent();

        SetOnClickListeners();
    }


    private void InitializeComponents()
    {
        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutSolutionElementsFeed = findViewById(R.id.linearLayoutSolutionElementsFeed);
        progressBarSolutionElementLoading = findViewById(R.id.progressBarSolutionElementLoading);
        buttonShowMoreSolutionOnSale = findViewById(R.id.buttonShowMoreSolutionOnSale);
        textViewShowMoreSolutionOnSaleMessage = findViewById(R.id.textViewShowMoreSolutionOnSaleMessage);
        scrollViewForSolutions = findViewById(R.id.scrollViewForSolutions);

        questionRequestIDsForSolutionsOnSaleOnStudentCart = ServerHelper.GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart(GlobalVariables.USER_ID);

        questionRequestIDsForRecommendedSolutionsOnSale = ServerHelper.GetQuestionRequestIDsForRecommendedSolutionsOnSale(GlobalVariables.USER_ID);

    }


    private void SetOnClickListeners()
    {
        buttonShowMoreSolutionOnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNewCustomSolutionOnSaleLinearLayoutFeedContent();
            }
        });
    }

    private void LoadNewCustomSolutionOnSaleLinearLayoutFeedContent()
    {
        if(questionRequestIDsForRecommendedSolutionsOnSale.isEmpty())
        {
            textViewShowMoreSolutionOnSaleMessage.setText(R.string.popular_recommended_no_solutions_popular);
            return;
        }
        if(lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent == null)
        {
            lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent = new CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent(StudentPopularQuestionsDisplayActivity.this, getApplicationContext(), questionRequestIDsForRecommendedSolutionsOnSale);
            linearLayoutSolutionElementsFeed.addView(lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent);
        }
        else
        {
            if(questionRequestIDsForRecommendedSolutionsOnSale.size() > (GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY * (lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent.stepNo+1)) )
            {
                linearLayoutSolutionElementsFeed.removeView(lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent);
                lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent = new CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent(StudentPopularQuestionsDisplayActivity.this, getApplicationContext(), questionRequestIDsForRecommendedSolutionsOnSale, lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent.stepNo + 1, lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent.customSolutionOnSaleForPopularRecommendedLinearLayouts);
                linearLayoutSolutionElementsFeed.addView(lastCustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent);
            }
            else
            {
                textViewShowMoreSolutionOnSaleMessage.setText(R.string.shopping_cart_no_more_solutions_on_cart);
                // scroll to bottom in order to make second line of the textView visible
                scrollViewForSolutions.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollViewForSolutions.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }
    }


    public boolean ExtendWithNewComments(LinearLayout customSolutionOnSaleLinearLayoutChild, SolutionOnSale crSolutionOnSale)
    {
        //CustomTutorLinearLayout crCustomTutorLinearLayout = GetCustomSolutionOnSaleLinearLayoutByQuestionRequestID(questionRequestID);
        //LinearLayout crLinearLayoutCommentsFeed = crCustomTutorLinearLayout.findViewById(R.id.linearLayoutCommentsFeed);
        return crSolutionOnSale.customSolutionCommentInjector.ExtendWithNewComments(customSolutionOnSaleLinearLayoutChild);
    }




}
