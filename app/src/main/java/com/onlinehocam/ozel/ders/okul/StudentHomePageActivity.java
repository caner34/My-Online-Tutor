package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;
import com.onlinehocam.ozel.ders.okul.AsyncTasks.RetrieveImageFromDirectoryAndSetToImageView;

public class StudentHomePageActivity extends AppCompatActivity
{

    Button buttonTutorDiscovery, buttonPurchase, buttonTutorPreferences, buttonPopularQuestions, buttonShoppingCart, buttonPostQuestionRequest, buttonQuestionRequestDisplay;
    ImageView imageViewTutorIcon, imageViewUserPopUpMenu;
    LinearLayout linearLayoutUserPopUpMenu, linearLayoutFreeUnitReplenishmentPanel;
    TextView freeUnitCoinNumber, replenishmentTimeRemaining;

    public Boolean isCounterActive = null;
    public Integer remainingSecondsForCheck = 1000;
    public Integer freeUnitReplenishmentTimeInSeconds = 100000;
    Handler m_handler;
    Runnable m_handlerTask;

    boolean isActivityNewlyCreated = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        InitializeComponents();

        DisplayReplenishmentNotificationOnFirstHomePageLunch();

        SetOnClickListeners();

        SetTutorIconRandomly();

        CheckFreeUnitReplenishment();
    }

    @SuppressLint("ResourceType")
    private void DisplayReplenishmentNotificationOnFirstHomePageLunch()
    {
        boolean isFirstHomePageLunch = getIntent().getBooleanExtra("isFirstHomePageLunch", false);
        if(isFirstHomePageLunch)
        {
            GlobalVariables.AlertDialogDisplay(StudentHomePageActivity.this, getString(R.string.replenishment_notification), getString(R.string.welcome));
        }
    }

    public void StartReplenishmentCounter()
    {
        isCounterActive = true;
        m_handler = new Handler();
        m_handlerTask = new Runnable()
        {
            @Override
            public void run() {
                if(isCounterActive)
                {
                    if(remainingSecondsForCheck>0)
                    {
                        replenishmentTimeRemaining.setText(ConvertSecondsToTimeDisplay(remainingSecondsForCheck));
                        remainingSecondsForCheck--;
                        if(remainingSecondsForCheck <= 0)
                        {
                            CheckFreeUnitReplenishment();
                            remainingSecondsForCheck = freeUnitReplenishmentTimeInSeconds;
                        }
                    }
                    m_handler.postDelayed(m_handlerTask, 1000);
                }
            }
        };
        m_handlerTask.run();
    }

    private String ConvertSecondsToTimeDisplay(int remainingSecondsForCheck)
    {
        GlobalVariables.Log(getApplicationContext(), "on ConvertSecondsToTimeDisplay >>> remainingSecondsForCheck: "+remainingSecondsForCheck);
        int secondsReminder = remainingSecondsForCheck % 60;
        remainingSecondsForCheck -= secondsReminder;
        int minutesReminder = (remainingSecondsForCheck % 3600) / 60;
        remainingSecondsForCheck -= minutesReminder * 60;
        int hoursRemainder = remainingSecondsForCheck / 3600;

        GlobalVariables.Log(getApplicationContext(), "on ConvertSecondsToTimeDisplay >>> secondsReminder: "+secondsReminder + "  minutesReminder: " + minutesReminder + "  hoursRemainder: "+hoursRemainder);

        String secondsReminderStr = "" + secondsReminder;
        String minutesReminderStr = "" + minutesReminder;
        String hoursRemainingStr = "" + hoursRemainder;
        if((""+secondsReminder).length() == 1)
        {
            secondsReminderStr = "0"+secondsReminder;
        }
        if((""+minutesReminder).length() == 1)
        {
            minutesReminderStr = "0"+minutesReminder;
        }
        if((""+hoursRemainder).length() == 1)
        {
            hoursRemainingStr = "0"+hoursRemainder;
        }


        GlobalVariables.Log(getApplicationContext(), "on ConvertSecondsToTimeDisplay >>> result: "+ hoursRemainingStr + ":" + minutesReminderStr + ":" + secondsReminderStr);


        return hoursRemainingStr + ":" + minutesReminderStr + ":" + secondsReminderStr;
    }

    public void StopReplenishmentCounter()
    {
        isCounterActive = false;
        if(m_handler != null)
        {
            m_handler.removeCallbacks(m_handlerTask);
        }
    }

    private void CheckFreeUnitReplenishment()
    {
        AsyncTaskHelper.CheckForFreeUnitReplenishment(StudentHomePageActivity.this, getApplicationContext(), GlobalVariables.USER_ID, linearLayoutFreeUnitReplenishmentPanel, freeUnitCoinNumber, replenishmentTimeRemaining);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isActivityNewlyCreated)
        {
            CheckFreeUnitReplenishment();
        }
    }

    private void InitializeComponents()
    {
        isActivityNewlyCreated = false;

        buttonTutorDiscovery = findViewById(R.id.buttonTutorDiscovery);
        buttonPurchase = findViewById(R.id.buttonPurchase);
        //buttonTutorPreferences = findViewById(R.id.buttonTutorPreferences);
        buttonPopularQuestions = findViewById(R.id.buttonPopularQuestions);
        buttonShoppingCart = findViewById(R.id.buttonShoppingCart);
        buttonPostQuestionRequest = findViewById(R.id.buttonPostQuestionRequest);
        buttonQuestionRequestDisplay = findViewById(R.id.buttonQuestionRequestDisplay);

        imageViewTutorIcon = findViewById(R.id.imageViewTutorIcon);
        linearLayoutUserPopUpMenu = findViewById(R.id.linearLayoutUserPopUpMenu);
        imageViewUserPopUpMenu = findViewById(R.id.imageViewUserPopUpMenu);


        linearLayoutFreeUnitReplenishmentPanel = findViewById(R.id.linearLayoutFreeUnitReplenishmentPanel);
        freeUnitCoinNumber = findViewById(R.id.freeUnitCoinNumber);
        replenishmentTimeRemaining = findViewById(R.id.replenishmentTimeRemaining);
        AsyncTaskHelper.GetStudentWithImageDataAddressIDAndGender(getApplicationContext(), imageViewUserPopUpMenu, GlobalVariables.USER_ID, RetrieveImageFromDirectoryAndSetToImageView.DIR_MINI_USER_PHOTOS);
    }

    private void SetOnClickListeners()
    {
        buttonTutorDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentsTutorDiscoveryActivity.class);
                startActivity(i);
            }
        });

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentPurchaseActivity.class);
                startActivity(i);
            }
        });

        /*
        buttonTutorPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PreferencesActivity.class);
                startActivity(i);
            }
        });*/

        buttonPopularQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentPopularQuestionsDisplayActivity.class);
                startActivity(i);
            }
        });

        buttonShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentShoppingCartActivity.class);
                startActivity(i);
            }
        });

        buttonPostQuestionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentPostQuestionActivity.class);
                startActivity(i);
            }
        });

        buttonQuestionRequestDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                Intent i = new Intent(getApplicationContext(), StudentQuestionRequestDisplayActivity.class);
                startActivity(i);
            }
        });

        imageViewTutorIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                SwitchTutorIcon();
            }
        });

        linearLayoutUserPopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                DisplayPopUpMenu();
            }
        });
    }

    private void DisplayPopUpMenu()
    {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), linearLayoutUserPopUpMenu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_student, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SoundHelper.PlayMediaPlayerSound(StudentHomePageActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                if(item.getTitle().toString().equals(getString(R.string.menu_user_profile)))
                {
                    //TODO fill menu_user_profile ACTIVITY
                }
                else if(item.getTitle().toString().equals(getString(R.string.menu_purchased_solutions)))
                {
                    startActivity(new Intent(StudentHomePageActivity.this, StudentPurchasedSolutionsActivity.class));
                }
                else if(item.getTitle().toString().equals(getString(R.string.menu_balance)))
                {
                    startActivity(new Intent(StudentHomePageActivity.this, StudentPurchaseActivity.class));
                }
                else if(item.getTitle().toString().equals(getString(R.string.menu_log_out)))
                {
                    //TODO fill menu_log_out action
                }
                return true;
            }
        });

        popupMenu.show();

        //AlertDialog.Builder popupMenu = new AlertDialog.Builder(this);
        //popupMenu.setTitle(GlobalVariables.USER_NAME);
        //LayoutInflater inflater = this.getLayoutInflater();
        //View popupMenuLayout = inflater.inflate(R.layout.popup_menu_student_layout, null);
        //popupMenu.setView(popupMenuLayout);
        //popupMenu.show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StopReplenishmentCounter();
    }

}
