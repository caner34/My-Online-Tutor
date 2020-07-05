package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MarketerHomePageActivity extends AppCompatActivity {

    Button buttonDisplaySpecialCommissionLink, buttonShareLinkOnSocialMedia, buttonEarnings;
    ImageView imageViewTutorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_home_page);

        InstantiateComponents();

        SetOnClickListeners();

        SetTutorIconRandomly();
    }

    private void SetOnClickListeners()
    {
        buttonDisplaySpecialCommissionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(MarketerHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), MarketerCommissionLinkDisplayActivity.class);
                startActivity(i);
            }
        });
        buttonShareLinkOnSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(MarketerHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), MarketerShareAppLinkOnSocialMediaActivity.class);
                startActivity(i);
            }
        });
        buttonEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(MarketerHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), MarketerEarningsPaymentActivity.class);
                startActivity(i);
            }
        });

        imageViewTutorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(MarketerHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                SwitchTutorIcon();
            }
        });
    }

    private void InstantiateComponents()
    {
        buttonDisplaySpecialCommissionLink = findViewById(R.id.buttonDisplaySpecialCommissionLink);
        buttonShareLinkOnSocialMedia = findViewById(R.id.buttonShareLinkOnSocialMedia);
        buttonEarnings = findViewById(R.id.buttonEarnings);
        imageViewTutorIcon = findViewById(R.id.imageViewTutorIcon);
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
