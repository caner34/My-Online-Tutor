package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TutorHomePageActivity extends AppCompatActivity {

    Button buttonDisplayQuestionRequests, buttonPostSolutions, buttonEarnings;
    ImageView imageViewTutorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_home_page);

        InitializeComponents();

        SetOnClickListeners();

        SetTutorIconRandomly();

        //GlobalVariables.Log(getApplicationContext(), GlobalVariables.USER_NAME+" - "+GlobalVariables.USER_ID);
    }

    private void InitializeComponents()
    {
        buttonDisplayQuestionRequests = findViewById(R.id.buttonDisplayQuestionRequests);
        buttonPostSolutions = findViewById(R.id.buttonPostSolutions);
        buttonEarnings = findViewById(R.id.buttonEarnings);
        imageViewTutorIcon = findViewById(R.id.imageViewTutorIcon);
    }

    private void SetOnClickListeners()
    {
        buttonDisplayQuestionRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(TutorHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), TutorQuestionRequestDisplayActivity.class);
                startActivity(i);
            }
        });

        buttonPostSolutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(TutorHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), TutorSolutionPostActivity.class);
                startActivity(i);
            }
        });

        buttonEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(TutorHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), TutorEarningsPaymentActivity.class);
                startActivity(i);
            }
        });

        imageViewTutorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchTutorIcon();
            }
        });
    }

    private void SetTutorIconRandomly()
    {
        int[] tutorImageIds = new int[] { R.drawable.tutor_1, R.drawable.tutor_2 };
        int crResID = tutorImageIds[GlobalVariables.r.nextInt(tutorImageIds.length)];
        imageViewTutorIcon.setTag(crResID);
        imageViewTutorIcon.setImageResource(crResID);
    }

    private void SwitchTutorIcon()
    {
        int[] tutorImageIds = new int[] { R.drawable.tutor_1, R.drawable.tutor_2 };
        int crResID = tutorImageIds[0];
        if(imageViewTutorIcon.getTag().equals(tutorImageIds[0]))
        {
            crResID = tutorImageIds[1];
        }
        else
        {
            crResID = tutorImageIds[0];
        }
        imageViewTutorIcon.setTag(crResID);
        imageViewTutorIcon.setImageResource(crResID);
    }
}
