package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

public class StudentPurchaseActivity extends AppCompatActivity {

    LinearLayout linearLayoutMainSelection, linearLayoutMainDisplay;
    LinearLayout linearLayoutPurchaseItemNo0, linearLayoutPurchaseItemNo1, linearLayoutPurchaseItemNo2, linearLayoutPurchaseItemNo3, linearLayoutPurchaseItemNo4, linearLayoutPurchaseItemNo5, linearLayoutPurchaseItemNo6;
    ImageView imageViewPurchaseItemNo0, imageViewPurchaseItemNo1, imageViewPurchaseItemNo2, imageViewPurchaseItemNo3, imageViewPurchaseItemNo4, imageViewPurchaseItemNo5, imageViewPurchaseItemNo6;
    ImageView crImageViewPurchaseItem;
    TextView crTextViewPurchaseItem;
    Button buttonBuyNow, buttonBack;
    Animation animation_rotate0, animation_rotate1, animation_rotate2, animation_rotate3;

    LinearLayout linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, linearLayoutQuestionRequestUnitsInfoSubPanel, linearLayoutSubscriptionInfo, linearLayoutQuestionRequestUnitsFree, linearLayoutQuestionRequestUnitsPurchased;
    TextView textViewSubscriptionInfo, textViewQuestionRequestUnitsFree, textViewQuestionRequestUnitsPurchased;

    String crProductText = "";
    String crProductID = "";
    ImageView crClickedImageView;
    String[] purchaseItemStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_purchase);

        InitializeComponents();

        SetOnClickListeners();

        RetrieveCrSubscriptionAndUnitsInfo();
    }

    private void RetrieveCrSubscriptionAndUnitsInfo()
    {
        AsyncTaskHelper.RetrieveCrSubscriptionAndUnitsInfo(StudentPurchaseActivity.this, getApplicationContext(), GlobalVariables.USER_ID, linearLayoutRemainingUnitsAndSubscriptionsInfoPanel, linearLayoutQuestionRequestUnitsInfoSubPanel, linearLayoutQuestionRequestUnitsFree, textViewSubscriptionInfo, textViewQuestionRequestUnitsFree, textViewQuestionRequestUnitsPurchased);
    }

    private void InitializeComponents()
    {
        purchaseItemStringArray = new String[] { getString(R.string.purchase_unlimited_monthly_subscription), "10₺", "25₺", "50₺", "75₺", "100₺", "125₺" };

        linearLayoutMainSelection = findViewById(R.id.linearLayoutMainSelection);
        linearLayoutMainDisplay = findViewById(R.id.linearLayoutMainDisplay);

        linearLayoutPurchaseItemNo0 = findViewById(R.id.linearLayoutPurchaseItemNo0);
        linearLayoutPurchaseItemNo1 = findViewById(R.id.linearLayoutPurchaseItemNo1);
        linearLayoutPurchaseItemNo2 = findViewById(R.id.linearLayoutPurchaseItemNo2);
        linearLayoutPurchaseItemNo3 = findViewById(R.id.linearLayoutPurchaseItemNo3);
        linearLayoutPurchaseItemNo4 = findViewById(R.id.linearLayoutPurchaseItemNo4);
        linearLayoutPurchaseItemNo5 = findViewById(R.id.linearLayoutPurchaseItemNo5);
        linearLayoutPurchaseItemNo6 = findViewById(R.id.linearLayoutPurchaseItemNo6);

        imageViewPurchaseItemNo0 = findViewById(R.id.imageViewPurchaseItemNo0);
        imageViewPurchaseItemNo1 = findViewById(R.id.imageViewPurchaseItemNo1);
        imageViewPurchaseItemNo2 = findViewById(R.id.imageViewPurchaseItemNo2);
        imageViewPurchaseItemNo3 = findViewById(R.id.imageViewPurchaseItemNo3);
        imageViewPurchaseItemNo4 = findViewById(R.id.imageViewPurchaseItemNo4);
        imageViewPurchaseItemNo5 = findViewById(R.id.imageViewPurchaseItemNo5);
        imageViewPurchaseItemNo6 = findViewById(R.id.imageViewPurchaseItemNo6);


        linearLayoutRemainingUnitsAndSubscriptionsInfoPanel = findViewById(R.id.linearLayoutRemainingUnitsAndSubscriptionsInfoPanel);
        linearLayoutQuestionRequestUnitsInfoSubPanel = findViewById(R.id.linearLayoutQuestionRequestUnitsInfoSubPanel);
        linearLayoutSubscriptionInfo = findViewById(R.id.linearLayoutSubscriptionInfo);
        linearLayoutQuestionRequestUnitsFree = findViewById(R.id.linearLayoutQuestionRequestUnitsFree);
        linearLayoutQuestionRequestUnitsPurchased = findViewById(R.id.linearLayoutQuestionRequestUnitsPurchased);

        textViewSubscriptionInfo = findViewById(R.id.textViewSubscriptionInfo);
        textViewQuestionRequestUnitsFree = findViewById(R.id.textViewQuestionRequestUnitsFree);
        textViewQuestionRequestUnitsPurchased = findViewById(R.id.textViewQuestionRequestUnitsPurchased);


        crImageViewPurchaseItem = findViewById(R.id.crImageViewPurchaseItem);
        crTextViewPurchaseItem = findViewById(R.id.crTextViewPurchaseItem);
        buttonBuyNow = findViewById(R.id.buttonBuyNow);
        buttonBack = findViewById(R.id.buttonBack);

        animation_rotate0 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_rotate0);
        animation_rotate1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_rotate1);
        animation_rotate2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_rotate2);
        animation_rotate3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_rotate3);

        animation_rotate0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                crClickedImageView.startAnimation(animation_rotate1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation_rotate1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                crClickedImageView.startAnimation(animation_rotate2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation_rotate2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                crClickedImageView.startAnimation(animation_rotate3);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation_rotate3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SetupPurchaseItemDisplay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void SetOnClickListeners()
    {
        linearLayoutPurchaseItemNo0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo0;
                crProductText = purchaseItemStringArray[0];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo1;
                crProductText = purchaseItemStringArray[1];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo2;
                crProductText = purchaseItemStringArray[2];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo3;
                crProductText = purchaseItemStringArray[3];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo4;
                crProductText = purchaseItemStringArray[4];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo5;
                crProductText = purchaseItemStringArray[5];
                ExecutePurchaseItemClicked();
            }
        });
        linearLayoutPurchaseItemNo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crClickedImageView = imageViewPurchaseItemNo6;
                crProductText = purchaseItemStringArray[6];
                ExecutePurchaseItemClicked();
            }
        });

        buttonBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyProduct(crProductID);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutMainDisplay.setVisibility(View.GONE);
                linearLayoutMainSelection.setVisibility(View.VISIBLE);
            }
        });
    }

    private void ExecutePurchaseItemClicked()
    {
        ExecuteAnimation(crClickedImageView);
    }

    private void ExecuteAnimation(ImageView crImageView)
    {
        crImageView.startAnimation(animation_rotate0);
    }

    private void SetupPurchaseItemDisplay()
    {
        linearLayoutMainSelection.setVisibility(View.GONE);
        linearLayoutMainDisplay.setVisibility(View.VISIBLE);

        crImageViewPurchaseItem.setImageDrawable(crClickedImageView.getDrawable());
        crTextViewPurchaseItem.setText(crProductText);
    }

    private void BuyProduct(String crProductID)
    {
        //TODO FILL PURCHASING METHOD
    }


}
