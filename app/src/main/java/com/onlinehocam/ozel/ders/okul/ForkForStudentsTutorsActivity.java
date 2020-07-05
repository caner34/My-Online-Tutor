package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class ForkForStudentsTutorsActivity extends AppCompatActivity {


    LinearLayout linearLayoutTutor, linearLayoutStudent, linearLayoutMarketer;
    ImageView imageViewMarketerIcon;
    boolean isToCreateANewAccount;

    /*ImageView imageViewPhoto;
    ImageView imageViewPhotoOther;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_for_students_tutors);

        //Instantiate DatabaseHelper
        GlobalVariables.InstantiateSQLiteDatabase(getApplicationContext());

        isToCreateANewAccount = getIntent().getBooleanExtra("isToCreateANewAccount", false);

        //TODO Delete The Line Below After Tests are Completed
        //GlobalVariables.myDb.ReCreateTables(GlobalVariables.db);

        InstantiateComponents();

        RoundMarketerImage();

        CheckIfFirstLogin();

        SetOnClickListeners();

        //imageViewPhotoOther = findViewById(R.id.imageViewTutorIcon);
    }

    private void RoundMarketerImage()
    {
        Bitmap marketerIconBitmap = ((BitmapDrawable)imageViewMarketerIcon.getDrawable()).getBitmap();
        imageViewMarketerIcon.setImageBitmap(CommonUtils.GetCircleCroppedBitmap(CommonUtils.CreateSquaredBitmap(marketerIconBitmap)));
    }

    private void CheckIfFirstLogin()
    {
        GlobalVariables.r = new Random();
        isAutoSignIn();
        GlobalVariables.InstantiateLessonsAndClasses();

        if(isToFirstLogin() || isToCreateANewAccount)
        {
            //make student - tutor selection buttons visible
            linearLayoutStudent.setVisibility(LinearLayout.VISIBLE);
            linearLayoutTutor.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), SignUpLoginActivity.class);
            startActivity(i);
        }
    }

    private boolean isAutoSignIn()
    {
        int autoSignInValue = GlobalVariables.myDb.RetrieveGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_AUTO_SIGN_IN, GlobalVariables.db);
        GlobalVariables.IS_AUTO_SIGN_IN = (autoSignInValue != 0);
        boolean result = GlobalVariables.IS_AUTO_SIGN_IN;

        return result;
    }

    private boolean isToFirstLogin()
    {
        boolean result = !GlobalVariables.myDb.RetrieveGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_IS_TO_FIRST_LOGIN, GlobalVariables.db).equals("false");

        return result;
    }

    private void SetOnClickListeners()
    {
        linearLayoutTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.USER_STATUS = GlobalVariables.KEY_USER_STATUS_TUTOR;
                Intent i = new Intent(getApplicationContext(), SignUpLoginActivity.class);
                startActivity(i);
            }
        });
        linearLayoutStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.USER_STATUS = GlobalVariables.KEY_USER_STATUS_STUDENT;
                Intent i = new Intent(getApplicationContext(), SignUpLoginActivity.class);
                startActivity(i);
            }
        });
        linearLayoutMarketer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.USER_STATUS = GlobalVariables.KEY_USER_STATUS_MARKETER;
                Intent i = new Intent(getApplicationContext(), SignUpLoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void InstantiateComponents()
    {
        linearLayoutStudent = findViewById(R.id.linearLayoutStudent);
        linearLayoutTutor = findViewById(R.id.linearLayoutTutor);
        linearLayoutMarketer = findViewById(R.id.linearLayoutMarketer);
        imageViewMarketerIcon = findViewById(R.id.imageViewMarketerIcon);
    }
}
