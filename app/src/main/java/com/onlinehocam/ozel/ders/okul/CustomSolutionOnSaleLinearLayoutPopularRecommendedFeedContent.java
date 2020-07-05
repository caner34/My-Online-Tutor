package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent extends LinearLayout
{
    Activity activity;
    Context context;
    List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart;
    int stepNo;
    List<CustomSolutionOnSaleForPopularRecommendedLinearLayout> customSolutionOnSaleForPopularRecommendedLinearLayouts;


    public CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart) {
        this(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart, 1, null);
    }


    public CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart, int stepNo, List<CustomSolutionOnSaleForPopularRecommendedLinearLayout> parameterCustomSolutionOnSaleForPopularRecommendedLinearLayout) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.questionRequestIDsForSolutionsOnSaleOnStudentCart = questionRequestIDsForSolutionsOnSaleOnStudentCart;
        this.stepNo = stepNo;
        if(parameterCustomSolutionOnSaleForPopularRecommendedLinearLayout == null)
        {
            this.customSolutionOnSaleForPopularRecommendedLinearLayouts = new ArrayList<>();
        }
        else
        {
            this.customSolutionOnSaleForPopularRecommendedLinearLayouts = parameterCustomSolutionOnSaleForPopularRecommendedLinearLayout;
        }

        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);

        //GlobalVariables.Log(context, "adding Start No: "+Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY) + " ading End: "+Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()));


        for(int i = Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY); i < Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()) ; i++)
        {
            CustomSolutionOnSaleForPopularRecommendedLinearLayout crCustomSolutionOnSaleForPopularRecommendedLinearLayout = new CustomSolutionOnSaleForPopularRecommendedLinearLayout(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart.get(i));
            SetBorderToLayout(crCustomSolutionOnSaleForPopularRecommendedLinearLayout, 3, Color.RED);
            customSolutionOnSaleForPopularRecommendedLinearLayouts.add(crCustomSolutionOnSaleForPopularRecommendedLinearLayout);
        }

        // Displays MAX 40 customSolutionOnSaleLinearLayout due to 4 written below
        for(int i = Math.max(0, customSolutionOnSaleForPopularRecommendedLinearLayouts.size()-(GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY*4)); i < customSolutionOnSaleForPopularRecommendedLinearLayouts.size(); i++)
        {
            if(customSolutionOnSaleForPopularRecommendedLinearLayouts.get(i).getParent() != null)
            {
                ((CustomSolutionOnSaleLinearLayoutPopularRecommendedFeedContent) customSolutionOnSaleForPopularRecommendedLinearLayouts.get(i).getParent()).removeView(customSolutionOnSaleForPopularRecommendedLinearLayouts.get(i));
            }
            this.addView(customSolutionOnSaleForPopularRecommendedLinearLayouts.get(i));
        }
    }


    void SetBorderToLayout(CustomSolutionOnSaleForPopularRecommendedLinearLayout linearLayout, int strokeTickness, int strokeColorCode)
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
}
