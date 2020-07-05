package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent extends LinearLayout
{
    Activity activity;
    Context context;
    List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart;
    int stepNo;
    List<CustomSolutionOnSaleForShoppingCartLinearLayout> customSolutionOnSaleForShoppingCartLinearLayouts;


    public CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart) {
        this(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart, 1, null);
    }


    public CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent(Activity activity, Context context, List<Integer> questionRequestIDsForSolutionsOnSaleOnStudentCart, int stepNo, List<CustomSolutionOnSaleForShoppingCartLinearLayout> crCustomSolutionOnSaleForShoppingCartLinearLayouts) {
        super(context);
        this.activity = activity;
        this.context = context;
        this.questionRequestIDsForSolutionsOnSaleOnStudentCart = questionRequestIDsForSolutionsOnSaleOnStudentCart;
        this.stepNo = stepNo;
        if(crCustomSolutionOnSaleForShoppingCartLinearLayouts == null)
        {
            this.customSolutionOnSaleForShoppingCartLinearLayouts = new ArrayList<>();
        }
        else
        {
            this.customSolutionOnSaleForShoppingCartLinearLayouts = crCustomSolutionOnSaleForShoppingCartLinearLayouts;
        }

        this.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(lp);

        //GlobalVariables.Log(context, "adding Start No: "+Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY) + " ading End: "+Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()));


        for(int i = Math.max(0, (stepNo - 1) * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY); i < Math.min(stepNo * GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY, questionRequestIDsForSolutionsOnSaleOnStudentCart.size()) ; i++)
        {
            CustomSolutionOnSaleForShoppingCartLinearLayout crCustomSolutionOnSaleForShoppingCartLinearLayout = new CustomSolutionOnSaleForShoppingCartLinearLayout(activity, context, questionRequestIDsForSolutionsOnSaleOnStudentCart.get(i));
            SetBorderToLayout(crCustomSolutionOnSaleForShoppingCartLinearLayout, 3, Color.RED);
            customSolutionOnSaleForShoppingCartLinearLayouts.add(crCustomSolutionOnSaleForShoppingCartLinearLayout);
        }

        // Displays MAX 40 customSolutionOnSaleLinearLayout due to 4 written below
        for(int i = Math.max(0, customSolutionOnSaleForShoppingCartLinearLayouts.size()-(GlobalVariables.SOLUTIONS_ON_AT_A_TIME_TO_DISPLAY*4)); i < customSolutionOnSaleForShoppingCartLinearLayouts.size(); i++)
        {
            if(customSolutionOnSaleForShoppingCartLinearLayouts.get(i).getParent() != null)
            {
                ((CustomSolutionOnSaleLinearLayoutShoppingCartFeedContent) customSolutionOnSaleForShoppingCartLinearLayouts.get(i).getParent()).removeView(customSolutionOnSaleForShoppingCartLinearLayouts.get(i));
            }
            this.addView(customSolutionOnSaleForShoppingCartLinearLayouts.get(i));
        }
    }


    void SetBorderToLayout(CustomSolutionOnSaleForShoppingCartLinearLayout linearLayout, int strokeTickness, int strokeColorCode)
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
