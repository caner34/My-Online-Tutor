package com.onlinehocam.ozel.ders.okul;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomExpandableListLayoutInjector
{

    public Context context;
    LinearLayout expandableListViewRequestedQuestions;
    public List<String> questionRequestCategories;
    public Map<String, LinearLayout> mapQuestionCategories;
    public Map<String, LinearLayout> mapGroupOfQuestionRequests;
    public Map<String, List<LinearLayout>> mapQuestionRequests;

    public CustomExpandableListLayoutInjector(Context context, LinearLayout expandableListViewRequestedQuestions, List<String> questionRequestCategories, Map<String, LinearLayout> mapQuestionCategories, Map<String, List<LinearLayout>> mapQuestionRequests) {
        this.context = context;
        this.expandableListViewRequestedQuestions = expandableListViewRequestedQuestions;
        this.questionRequestCategories = questionRequestCategories;
        this.mapQuestionCategories = mapQuestionCategories;
        this.mapQuestionRequests = mapQuestionRequests;

        InstantiateMapForGroupOfQuestionRequests();

        InjectData();
    }

    private void InstantiateMapForGroupOfQuestionRequests()
    {
        mapGroupOfQuestionRequests = new HashMap<>();

        LinearLayout crGroupOfQuestionRequestsLinearLayout;

        for(int i = 0; i < questionRequestCategories.size(); i++)
        {
            crGroupOfQuestionRequestsLinearLayout = new LinearLayout(context);
            crGroupOfQuestionRequestsLinearLayout.setOrientation(LinearLayout.VERTICAL);

            List<LinearLayout> crListOfQuestionRequests = (List<LinearLayout>) mapQuestionRequests.get(questionRequestCategories.get(i));
            for(int y = 0; y < crListOfQuestionRequests.size(); y++)
            {
                crGroupOfQuestionRequestsLinearLayout.addView(crListOfQuestionRequests.get(y));
            }

            //If CrCategory is Empty, Then Display A TextViewMessage Telling That
            if(crListOfQuestionRequests.isEmpty())
            {
                LinearLayout crLinearLayoutQuestionCategories = mapQuestionCategories.get(questionRequestCategories.get(i));
                InsertTextViewMessageTellingNoElementInThisCategory(crGroupOfQuestionRequestsLinearLayout, crLinearLayoutQuestionCategories);
            }


            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            crGroupOfQuestionRequestsLinearLayout.setLayoutParams(lp);

            mapGroupOfQuestionRequests.put(questionRequestCategories.get(i), crGroupOfQuestionRequestsLinearLayout);
        }

        for(int i = 0; i < questionRequestCategories.size(); i++)
        {
            List<LinearLayout> crListOfQuestionRequestsLinearLayout = mapQuestionRequests.get(questionRequestCategories.get(i));
            LinearLayout crQuestionRequestLinearLayout;
            for(int y = 0; y < crListOfQuestionRequestsLinearLayout.size(); y++)
            {
                crQuestionRequestLinearLayout = crListOfQuestionRequestsLinearLayout.get(y);
                SetBorderToLayout(crQuestionRequestLinearLayout, 2, Color.RED);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void InsertTextViewMessageTellingNoElementInThisCategory(LinearLayout crGroupOfQuestionRequestsLinearLayout, LinearLayout crLinearLayoutQuestionCategories)
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
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,16,0,0);
        tvMessage.setTextSize(28f);
        crGroupOfQuestionRequestsLinearLayout.addView(tvMessage);

        ((TextView)crLinearLayoutQuestionCategories.getChildAt(0)).setTextColor(Color.parseColor("#789971"));
    }

    public void InjectData()
    {
        LinearLayout crTitleLinearLayout, crGroupOfQuestionRequestsLinearLayout;
        for(int i = 0; i < questionRequestCategories.size(); i++)
        {
            crTitleLinearLayout = (LinearLayout) mapQuestionCategories.get(questionRequestCategories.get(i));
            crTitleLinearLayout.setGravity(Gravity.CENTER);
            SetBorderToLayout(crTitleLinearLayout, 2, Color.BLACK, 24f);
            expandableListViewRequestedQuestions.addView(crTitleLinearLayout);

            crGroupOfQuestionRequestsLinearLayout = (LinearLayout) mapGroupOfQuestionRequests.get(questionRequestCategories.get(i));
            crGroupOfQuestionRequestsLinearLayout.setVisibility(View.GONE);
            //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            //crGroupOfQuestionRequestsLinearLayout.setLayoutParams(lp);
            SetBorderToLayout(crGroupOfQuestionRequestsLinearLayout, 2, Color.GREEN, 10f);
            expandableListViewRequestedQuestions.addView(crGroupOfQuestionRequestsLinearLayout);



            final LinearLayout crFinalGroupOfQuestionRequestsLinearLayout = crGroupOfQuestionRequestsLinearLayout;
            final LinearLayout crFinalTitleLinearLayout = crTitleLinearLayout;
            final int iFinal = i;

            crTitleLinearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (crFinalGroupOfQuestionRequestsLinearLayout.getVisibility()==View.GONE)
                    {
                        Expand(crFinalGroupOfQuestionRequestsLinearLayout, crFinalTitleLinearLayout, iFinal);
                    }
                    else
                    {
                        Collapse(crFinalGroupOfQuestionRequestsLinearLayout, crFinalTitleLinearLayout, iFinal);
                    }
                }
            });
        }
    }

    private void Expand(LinearLayout crFinalGroupOfQuestionRequestsLinearLayout, LinearLayout crFinalTitleLinearLayout, int groupID)
    {
        //set Visible
        crFinalGroupOfQuestionRequestsLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec( ((LinearLayout)crFinalGroupOfQuestionRequestsLinearLayout.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        //TODO BECAUSE YOU SET MAX HEIGHT TO 60300 pixels you shold probably limit the number of Question Requests to max. 50 while collapsing; otherwise when you expand again rest of the question requests beyond 50th will not be displayed
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(60300, View.MeasureSpec.AT_MOST);
        crFinalGroupOfQuestionRequestsLinearLayout.measure(widthSpec, heightSpec);

        //Switch Group Indicator Arrow Icon
        ((ImageView)crFinalTitleLinearLayout.getChildAt(1)).setImageResource(R.drawable.group_indicator_up);

        LinearLayout crCheckedTitleLinearLayout, crCheckedGroupOfQuestionRequestsLinearLayout;
        for(int i = 0; i < questionRequestCategories.size(); i++)
        {
            crCheckedGroupOfQuestionRequestsLinearLayout = (LinearLayout) mapGroupOfQuestionRequests.get(questionRequestCategories.get(i));
            crCheckedTitleLinearLayout = (LinearLayout) mapQuestionCategories.get(questionRequestCategories.get(i));
            if(i != groupID)
            {
                if (crCheckedGroupOfQuestionRequestsLinearLayout.getVisibility() != View.GONE)
                {
                    Collapse(crCheckedGroupOfQuestionRequestsLinearLayout, crCheckedTitleLinearLayout, i);
                }
            }
        }

        ValueAnimator mAnimator = slideAnimator(0, crFinalGroupOfQuestionRequestsLinearLayout.getMeasuredHeight()  + 20, crFinalGroupOfQuestionRequestsLinearLayout);
        mAnimator.start();

    }

    private void Collapse(LinearLayout crFinalGroupOfQuestionRequestsLinearLayout, LinearLayout crFinalTitleLinearLayout, int groupID)
    {
        int finalHeight = crFinalGroupOfQuestionRequestsLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, crFinalGroupOfQuestionRequestsLinearLayout);

        //Switch Group Indicator Arrow Icon
        ((ImageView)crFinalTitleLinearLayout.getChildAt(1)).setImageResource(R.drawable.group_indicator_down);

        final LinearLayout crDoubleFinalQuestionRequestsLinearLayout = crFinalGroupOfQuestionRequestsLinearLayout;

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                crDoubleFinalQuestionRequestsLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, LinearLayout crFinalQuestionRequestsLinearLayout)
    {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        final LinearLayout crDoubleFinalQuestionRequestsLinearLayout = crFinalQuestionRequestsLinearLayout;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = crDoubleFinalQuestionRequestsLinearLayout.getLayoutParams();
                layoutParams.height = value;
                crDoubleFinalQuestionRequestsLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }



    void SetBorderToLayout(LinearLayout linearLayout, int strokeThickness, int strokeColorCode, float radius)
    {
        //use a GradientDrawable with only one color set, to make it a solid color
        GradientDrawable border = new GradientDrawable();
        border.setColor(0xFFFFFFFF); //white background
        if(radius != 0f)
        {
            border.setCornerRadius(radius);
        }
        border.setStroke(strokeThickness, strokeColorCode); //black border with full opacity
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
        {
            linearLayout.setBackgroundDrawable(border);
        }
        else
        {
            linearLayout.setBackground(border);
        }
    }

    void SetBorderToLayout(LinearLayout linearLayout, int strokeThickness, int strokeColorCode)
    {
        SetBorderToLayout(linearLayout, strokeThickness, strokeColorCode, 0f);
    }


}
