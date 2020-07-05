package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentsTutorDiscoveryActivity extends AppCompatActivity {

    CustomTutorDiscoveryFeedInjector customTutorDiscoveryFeedInjector;
    CustomTutorDiscoveryFeedInjector favoriteTutorsInjector;
    LinearLayout linearLayoutTitleTutorsFeed, linearLayoutTitleMyFavoriteTutors, linearLayoutTutorsDiscoveryListPanel, linearLayoutFavoriteTutorsListPanel;
    LinearLayout linearLayoutTutorsFeed;
    LinearLayout linearLayoutFavoriteTutorsFeed;
    ConstraintLayout constraintLayoutMain;
    public List<Integer> tutorsIDs;
    public List<Tutor> tutorsList;
    public List<CustomTutorLinearLayout> tutorDisplayLinearLayouts;
    public List<Integer> usersFavoriteTutorIDsList;
    public List<Tutor> favoriteTutorsList;
    public List<CustomTutorLinearLayout> favoriteTutorDisplayLinearLayouts;

    ProgressBar progressBarLoading;
    Button buttonShowMoreTutor;
    TextView textViewShowMoreTutorMessage;

    ProgressBar progressBarFavoriteLoading;
    Button buttonShowMoreFavoriteTutor;
    TextView textViewShowMoreFavoriteTutorMessage;

    public LinearLayout linearLayoutMainProgressBar, linearLayoutMainMenu, linearLayoutMainPanelMyFavoriteTutors;


    enum SCREEN_STATE
    {
        DISCOVERY,
        FAVORITE
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_tutor_discovery);

        InstantiateComponents();

        SetOnClickListeners();

        customTutorDiscoveryFeedInjector = new CustomTutorDiscoveryFeedInjector(this, getApplicationContext(), linearLayoutTutorsFeed);
    }

    private void SetOnClickListeners()
    {
        linearLayoutTitleTutorsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expand(linearLayoutTutorsDiscoveryListPanel, linearLayoutTitleTutorsFeed, SCREEN_STATE.DISCOVERY);
            }
        });
        linearLayoutTitleMyFavoriteTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expand(linearLayoutFavoriteTutorsListPanel, linearLayoutTitleMyFavoriteTutors, SCREEN_STATE.FAVORITE);
            }
        });

        buttonShowMoreTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTutorDiscoveryFeedInjector.ExtendWithNewTutors();
            }
        });

        buttonShowMoreFavoriteTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteTutorsInjector.ExtendWithNewTutors();
            }
        });
    }

    public boolean ExtendWithNewComments(CustomSolutionCommentInjector customSolutionCommentInjector)
    {
        CustomTutorLinearLayout crCustomTutorLinearLayout = GetCustomTutorLinearLayoutByTutorID(customSolutionCommentInjector.tutorID);
        //LinearLayout crLinearLayoutCommentsFeed = crCustomTutorLinearLayout.findViewById(R.id.linearLayoutCommentsFeed);
        return customSolutionCommentInjector.ExtendWithNewComments(crCustomTutorLinearLayout);
    }



    private CustomTutorLinearLayout GetCustomTutorLinearLayoutByTutorID(int tutorID)
    {
        for(int i = 0; i < linearLayoutTutorsFeed.getChildCount(); i++)
        {
            if(((CustomTutorLinearLayout)linearLayoutTutorsFeed.getChildAt(i)).tutorID == tutorID)
            {
                return ((CustomTutorLinearLayout)linearLayoutTutorsFeed.getChildAt(i));
            }
        }
        return null;
    }

    private void InstantiateComponents()
    {
        constraintLayoutMain = findViewById(R.id.constraintLayoutMain);

        linearLayoutTitleTutorsFeed = findViewById(R.id.linearLayoutTitleTutorsFeed);
        linearLayoutTitleMyFavoriteTutors = findViewById(R.id.linearLayoutTitleMyFavoriteTutors);
        linearLayoutTutorsDiscoveryListPanel = findViewById(R.id.linearLayoutTutorsDiscoveryListPanel);
        linearLayoutFavoriteTutorsListPanel = findViewById(R.id.linearLayoutFavoriteTutorsListPanel);

        linearLayoutTutorsFeed = findViewById(R.id.linearLayoutTutorsFeed);
        linearLayoutFavoriteTutorsFeed = findViewById(R.id.linearLayoutFavoriteTutorsFeed);

        progressBarLoading = findViewById(R.id.progressBarLoading);
        buttonShowMoreTutor = findViewById(R.id.buttonShowMoreTutor);
        textViewShowMoreTutorMessage = findViewById(R.id.textViewShowMoreTutorMessage);

        progressBarFavoriteLoading = findViewById(R.id.progressBarFavoriteLoading);
        buttonShowMoreFavoriteTutor = findViewById(R.id.buttonShowMoreFavoriteTutor);
        textViewShowMoreFavoriteTutorMessage = findViewById(R.id.textViewShowMoreFavoriteTutorMessage);

        if(tutorsIDs == null)
        {
            tutorsIDs = ServerHelper.GetSuggestedTutorIDs();
            tutorsList = new ArrayList<>();
            tutorDisplayLinearLayouts =new ArrayList<>();
        }
        if(usersFavoriteTutorIDsList == null)
        {
            usersFavoriteTutorIDsList = ServerHelper.GetStudentFavoriteTutorIDsListByStudentUserID(GlobalVariables.USER_ID);
            favoriteTutorsList = new ArrayList<>();
            favoriteTutorDisplayLinearLayouts =new ArrayList<>();
        }

        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutMainMenu = findViewById(R.id.linearLayoutMainMenu);
        linearLayoutMainPanelMyFavoriteTutors = findViewById(R.id.linearLayoutMainPanelMyFavoriteTutors);
    }


    private void Expand(LinearLayout crFinalGroupOfQuestionRequestsLinearLayout, LinearLayout crFinalTitleLinearLayout, SCREEN_STATE screen_state)
    {
        int stepNoHeritage = 0;
        if(screen_state == SCREEN_STATE.DISCOVERY)
        {
            if(customTutorDiscoveryFeedInjector != null)
            {
                stepNoHeritage = customTutorDiscoveryFeedInjector.stepNo;
            }
            else
            {
                stepNoHeritage = 0;
            }
        }
        else
        {
            if(favoriteTutorsInjector != null)
            {
                stepNoHeritage = favoriteTutorsInjector.stepNo;
            }
            else
            {
                stepNoHeritage = 0;
            }
        }
        //GlobalVariables.Log(getApplicationContext(), "on Expand >>> at the very beginning of expand A stepNo: "+customTutorDiscoveryFeedInjector.stepNo);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec( ((LinearLayout)crFinalGroupOfQuestionRequestsLinearLayout.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(60300, View.MeasureSpec.AT_MOST);
        crFinalGroupOfQuestionRequestsLinearLayout.measure(widthSpec, heightSpec);

        //GlobalVariables.Log(getApplicationContext(), "on Expand >>> at the very beginning of expand B stepNo: "+customTutorDiscoveryFeedInjector.stepNo);

        //Switch Group Indicator Arrow Icon
        if(linearLayoutFavoriteTutorsListPanel.getVisibility() == View.VISIBLE)
        {
            //Expand First, Collapse Second
            ((TextView)linearLayoutTitleTutorsFeed.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorTheme2));
            ((TextView)linearLayoutTitleMyFavoriteTutors.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorTheme3));
            //set Visibility
            linearLayoutFavoriteTutorsListPanel.setVisibility(View.GONE);
            linearLayoutTutorsDiscoveryListPanel.setVisibility(View.VISIBLE);
            //GlobalVariables.Log(getApplicationContext(), "on Expand 1 >>> before Collapse stepNo: "+customTutorDiscoveryFeedInjector.stepNo);
            Collapse(linearLayoutFavoriteTutorsListPanel, linearLayoutTitleMyFavoriteTutors);


            //GlobalVariables.Log(getApplicationContext(), "on Expand 1 >>> before stepNo: "+customTutorDiscoveryFeedInjector.stepNo);
            SwitchPanel(SCREEN_STATE.DISCOVERY, stepNoHeritage);

            //GlobalVariables.Log(getApplicationContext(), "on Expand 1 >>> after stepNo: "+customTutorDiscoveryFeedInjector.stepNo);
        }
        else
        {

            //Expand Second, Collapse First
            ((TextView)linearLayoutTitleMyFavoriteTutors.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorTheme2));
            ((TextView)linearLayoutTitleTutorsFeed.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorTheme3));

            //set Visibility
            linearLayoutTutorsDiscoveryListPanel.setVisibility(View.GONE);
            linearLayoutFavoriteTutorsListPanel.setVisibility(View.VISIBLE);
            Collapse(linearLayoutTutorsDiscoveryListPanel, linearLayoutTitleTutorsFeed);


            SwitchPanel(SCREEN_STATE.FAVORITE, stepNoHeritage);
        }

        ValueAnimator mAnimator = slideAnimator(0, crFinalGroupOfQuestionRequestsLinearLayout.getMeasuredHeight()  + 20, crFinalGroupOfQuestionRequestsLinearLayout);
        mAnimator.start();
    }

    /*private void SwitchPanelTitleFormat(SCREEN_STATE screen_state)
    {
        TextView tvTutorDiscoveryTitle = linearLayoutTitleTutorsFeed.findViewById(R.id.textViewTitleTutorsFeed);
        TextView tvFavoriteTutorTitle = linearLayoutTitleMyFavoriteTutors.findViewById(R.id.textViewTitleMyFavoriteTutors);
        if(screen_state == SCREEN_STATE.DISCOVERY)
        {
            tvTutorDiscoveryTitle.setTextColor(getResources().getColor(R.color.colorTheme2));
            tvFavoriteTutorTitle.setTextColor(getResources().getColor(R.color.colorTheme7));
        }
        else
        {
            tvTutorDiscoveryTitle.setTextColor(getResources().getColor(R.color.colorTheme7));
            tvFavoriteTutorTitle.setTextColor(getResources().getColor(R.color.colorTheme2));
        }
    }*/

    private void SwitchPanel(SCREEN_STATE newSTATE, int stepNo)
    {
        //GlobalVariables.Log(getApplicationContext(), "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector >>> stepNo: "+stepNo);
        View newView;
        if(newSTATE == SCREEN_STATE.DISCOVERY)
        {
            newView = getLayoutInflater().inflate(R.layout.activity_students_tutor_discovery, null);
            setContentView(newView);

            //GlobalVariables.Log(getApplicationContext(), "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector before InstantiateComponents >>> stepNo: "+stepNo);
            InstantiateComponents();

            //GlobalVariables.Log(getApplicationContext(), "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector after InstantiateComponents >>> stepNo: "+stepNo);
            //GlobalVariables.Log(getApplicationContext(), "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector after second Set >>> stepNo: "+stepNo);

            SetOnClickListeners();
            //GlobalVariables.Log(getApplicationContext(), "on CONSTRUCTOR CustomTutorDiscoveryFeedInjector after SetOnClickListeners >>> stepNo: "+stepNo);

            customTutorDiscoveryFeedInjector = new CustomTutorDiscoveryFeedInjector(this, getApplicationContext(), linearLayoutTutorsFeed, stepNo);
        }
        else if (newSTATE == SCREEN_STATE.FAVORITE)
        {
            //GlobalVariables.Log(getApplicationContext(), "on SwitchPanel view 2 before >>> stepNo: "+stepNo);
            newView = getLayoutInflater().inflate(R.layout.activity_students_tutor_discovery_2, null);
            setContentView(newView);

            //GlobalVariables.Log(getApplicationContext(), "on SwitchPanel view 2 after >>> stepNo: "+stepNo);

            InstantiateComponents();

            SetOnClickListeners();

            if(favoriteTutorsInjector == null)    // First Time
            {
                //GlobalVariables.Log(getApplicationContext(), "on SwitchPanel view 2  >>> (favoriteTutorsInjector == null)    // First Time");
                favoriteTutorsInjector = new CustomTutorDiscoveryFeedInjector(this, getApplicationContext(), linearLayoutFavoriteTutorsFeed, usersFavoriteTutorIDsList);
            }
            else    // Refilling
            {
                //GlobalVariables.Log(getApplicationContext(), "on SwitchPanel view 2  >>> (favoriteTutorsInjector != null)    // Refilling");
                favoriteTutorsInjector = new CustomTutorDiscoveryFeedInjector(this, getApplicationContext(), linearLayoutFavoriteTutorsFeed, usersFavoriteTutorIDsList, stepNo);
            }
        }
    }


    private void Collapse(LinearLayout crFinalGroupOfQuestionRequestsLinearLayout, LinearLayout crFinalTitleLinearLayout)
    {
        int finalHeight = crFinalGroupOfQuestionRequestsLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, crFinalGroupOfQuestionRequestsLinearLayout);

        //Switch Group Indicator Arrow Icon

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


}
