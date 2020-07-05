package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomPurchasedSolutionsLinearLayoutStudentPurchasedSolutionsFeedContent extends LinearLayout
{
    Activity activity;
    Context context;
    List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart;
    int stepNo;
    List<CustomPurchasedSolutionsLinearLayout> customPurchasedSolutionsLinearLayouts;


    public CustomPurchasedSolutionsLinearLayoutStudentPurchasedSolutionsFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart) {
        this(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart, 1, null);
    }


    public CustomPurchasedSolutionsLinearLayoutStudentPurchasedSolutionsFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart, int stepNo, List<CustomPurchasedSolutionsLinearLayout> parameterCustomPurchasedSolutionsLinearLayout) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.questionRequestIDsForSolutionsOnSaleOnStudentCart = questionRequestIDsForSolutionsOnSaleOnStudentCart;
        this.stepNo = stepNo;
        if(parameterCustomPurchasedSolutionsLinearLayout == null)
        {
            this.customPurchasedSolutionsLinearLayouts = new ArrayList<>();
        }
        else
        {
            this.customPurchasedSolutionsLinearLayouts = parameterCustomPurchasedSolutionsLinearLayout;
        }

        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);

        //GlobalVariables.Log(context, "adding Start No: "+Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY) + " ading End: "+Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()));


        for(int i = Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY); i < Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()) ; i++)
        {
            CustomPurchasedSolutionsLinearLayout crCustomPurchasedSolutionsLinearLayout = new CustomPurchasedSolutionsLinearLayout(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart.get(i));
            SetBorderToLayout(crCustomPurchasedSolutionsLinearLayout, 3, Color.RED);
            customPurchasedSolutionsLinearLayouts.add(crCustomPurchasedSolutionsLinearLayout);
        }

        // Displays MAX 40 customSolutionOnSaleLinearLayout due to 4 written below
        for(int i = Math.max(0, customPurchasedSolutionsLinearLayouts.size()-(GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY*4)); i < customPurchasedSolutionsLinearLayouts.size(); i++)
        {
            if(customPurchasedSolutionsLinearLayouts.get(i).getParent() != null)
            {
                ((CustomPurchasedSolutionsLinearLayoutStudentPurchasedSolutionsFeedContent) customPurchasedSolutionsLinearLayouts.get(i).getParent()).removeView(customPurchasedSolutionsLinearLayouts.get(i));
            }
            this.addView(customPurchasedSolutionsLinearLayouts.get(i));
        }
    }


    void SetBorderToLayout(CustomPurchasedSolutionsLinearLayout linearLayout, int strokeTickness, int strokeColorCode)
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
