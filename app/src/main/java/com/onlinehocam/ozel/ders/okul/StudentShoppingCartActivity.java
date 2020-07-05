package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;


public class StudentShoppingCartActivity extends AppCompatActivity {

    LinearLayout linearLayoutMyShoppingCartElementsFeed;
    ProgressBar progressBarCartElementLoading;
    Button buttonShowMoreSolutionOnSale;
    TextView textViewShowMoreSolutionOnSaleMessage;
    ScrollView scrollViewForSolutions;

    public LinearLayout linearLayoutMainProgressBar;

    List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart;

    CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_shopping_cart);

        InitializeComponents();

        LoadNewCustomSolutionOnSaleLinearLayoutFeedContent();

        SetOnClickListeners();
    }


    private void InitializeComponents()
    {
        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutMyShoppingCartElementsFeed = findViewById(R.id.linearLayoutMyShoppingCartElementsFeed);
        progressBarCartElementLoading = findViewById(R.id.progressBarCartElementLoading);
        buttonShowMoreSolutionOnSale = findViewById(R.id.buttonShowMoreSolutionOnSale);
        textViewShowMoreSolutionOnSaleMessage = findViewById(R.id.textViewShowMoreSolutionOnSaleMessage);
        scrollViewForSolutions = findViewById(R.id.scrollViewForSolutions);

        questionRequestIDsForSolutionsOnSaleOnStudentCart = ServerHelper.GetQuestionRequestIDsForSolutionsOnSaleOnStudentCart(GlobalVariables.USER_ID);
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
        if(questionRequestIDsForSolutionsOnSaleOnStudentCart.isEmpty())
        {
            textViewShowMoreSolutionOnSaleMessage.setText(getString(R.string.shopping_cart_no_solutions_on_cart) + "\nÇözümleri görüntülemek ya da sepetinize eklemek için ana menüden "+getString(R.string.student_home_popular_questions)+" seçeneğine tıklayınız.");
            return;
        }
        if(lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent == null)
        {
            lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent = new CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent(StudentShoppingCartActivity.this, getApplicationContext(), questionRequestIDsForSolutionsOnSaleOnStudentCart);
            linearLayoutMyShoppingCartElementsFeed.addView(lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent);
        }
        else
        {
            if(questionRequestIDsForSolutionsOnSaleOnStudentCart.size() > (GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY * (lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent.stepNo+1)) )
            {
                linearLayoutMyShoppingCartElementsFeed.removeView(lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent);
                lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent = new CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent(StudentShoppingCartActivity.this, getApplicationContext(), questionRequestIDsForSolutionsOnSaleOnStudentCart, lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent.stepNo + 1, lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent.customSolutionOnSaleForShoppingCartLinearLayouts);
                linearLayoutMyShoppingCartElementsFeed.addView(lastCustomSolutionOnSaleLinearLayoutShoppingCartFeedContent);
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
