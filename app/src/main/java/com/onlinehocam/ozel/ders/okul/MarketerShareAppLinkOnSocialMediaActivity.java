package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.util.Arrays;
import java.util.UUID;

public class MarketerShareAppLinkOnSocialMediaActivity extends AppCompatActivity {


    ImageView imageViewWhatsApp, imageViewTwitter, imageViewInstagram, imageViewFacebook, imageViewGmail, imageViewSnapchat, imageViewShareViaOther;
    RadioGroup radioGroupReferenceLinks;
    RadioButton radioButtonCommissionLinkUserName, radioButtonCommissionLinkIDGenerated;
    CheckBox checkBoxIsToShareMessages;
    LinearLayout linearLayoutShareMessages;
    Spinner spinnerShareMessages;
    ArrayAdapter arrayAdapterShareMessages;
    public EditText editTextShareMessage;

    public String link;
    public String readyMessage;
    private Boolean isSelectedCLIDWithUserName;

    String[] shareMessages = new String[]
            {
                    "Müthiş bir uygulama!",
                    "Öğrenmek için bire bir..."
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_share_app_link_on_social_media);

        InstantiateComponents();

        SetOnClickListeners();

        HandleTheSpinner();

        SetOtherListeners();

        HideKeyboard();
    }

    private void HandleTheSpinner()
    {
        arrayAdapterShareMessages = new ArrayAdapter(this, R.layout.custom_spinner, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerShareMessages, Arrays.asList(shareMessages), ""));
        spinnerShareMessages.setAdapter(arrayAdapterShareMessages);
        spinnerShareMessages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    readyMessage = "";
                }
                else
                {
                    readyMessage = shareMessages[position - 1];
                }
                editTextShareMessage.setText(readyMessage + "\n\n" + link);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String GetIsSelectedCLIDWithUserNameStr(Boolean isSelectedCLIDWithUserName)
    {
        if(!isLinkRetrieved())
        {
            return null;
        }
        String isSelectedCLIDWithUserNameStr = "";
        if(isSelectedCLIDWithUserName.equals(new Boolean(true)))
        {
            isSelectedCLIDWithUserNameStr = "true";
        }
        else if(isSelectedCLIDWithUserName.equals(new Boolean(false)))
        {
            isSelectedCLIDWithUserNameStr = "false";
        }
        return isSelectedCLIDWithUserNameStr;
    }

    private void GenerateDisplayCommissionLink()
    {
        String isSelectedCLIDWithUserNameStr = GetIsSelectedCLIDWithUserNameStr(isSelectedCLIDWithUserName);
        GlobalVariables.Log(getApplicationContext(), "in GenerateDisplayCommissionLink >>> result isSelectedCLIDWithUserNameStr: "+isSelectedCLIDWithUserNameStr);
        String generatedUUIDStr = UUID.randomUUID().toString();
        generatedUUIDStr = generatedUUIDStr.substring(0, Math.min(10, generatedUUIDStr.length()));
        AsyncTaskHelper.GenerateDisplayCommissionLink(this, getApplicationContext(), GlobalVariables.USER_ID, isSelectedCLIDWithUserNameStr, link, generatedUUIDStr);
    }


    private void SetOtherListeners()
    {
        radioGroupReferenceLinks.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == radioButtonCommissionLinkUserName.getId())
                {
                    GlobalVariables.Log(getApplicationContext(), "1st Radio Button Clicked");
                    isSelectedCLIDWithUserName = true;
                }
                else
                {
                    GlobalVariables.Log(getApplicationContext(), "2st Radio Button Clicked");
                    isSelectedCLIDWithUserName = false;
                }
                GenerateDisplayCommissionLink();
            }
        });

        checkBoxIsToShareMessages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    linearLayoutShareMessages.setVisibility(View.VISIBLE);
                }
                else
                {
                    linearLayoutShareMessages.setVisibility(View.GONE);
                }
            }
        });
    }

    private void InstantiateComponents()
    {
        isSelectedCLIDWithUserName = null;
        link = "";
        readyMessage = "";

        imageViewWhatsApp = findViewById(R.id.imageViewWhatsApp);
        imageViewTwitter = findViewById(R.id.imageViewTwitter);
        imageViewInstagram = findViewById(R.id.imageViewInstagram);
        imageViewFacebook = findViewById(R.id.imageViewFacebook);
        imageViewGmail = findViewById(R.id.imageViewGmail);
        imageViewSnapchat = findViewById(R.id.imageViewSnapchat);
        imageViewShareViaOther = findViewById(R.id.imageViewShareViaOther);
        radioGroupReferenceLinks = findViewById(R.id.radioGroupReferenceLinks);
        radioButtonCommissionLinkUserName = findViewById(R.id.radioButtonCommissionLinkUserName);
        radioButtonCommissionLinkIDGenerated = findViewById(R.id.radioButtonCommissionLinkIDGenerated);
        checkBoxIsToShareMessages = findViewById(R.id.checkBoxIsToShareMessages);
        linearLayoutShareMessages = findViewById(R.id.linearLayoutShareMessages);
        spinnerShareMessages = findViewById(R.id.spinnerShareMessages);
        editTextShareMessage = findViewById(R.id.editTextShareMessage);
    }

    private boolean isLinkRetrieved()
    {
        if(isSelectedCLIDWithUserName == null)
        {
            new CustomToast(MarketerShareAppLinkOnSocialMediaActivity.this, getApplicationContext(), getString(R.string.marketer_warning_no_link_code_type_selected));
            return false;
        }
        return true;
    }

    private void SetOnClickListeners()
    {
        imageViewWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.WHATSAPP);
            }
        });
        imageViewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.TWITTER);
            }
        });
        imageViewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.INSTAGRAM);
            }
        });
        imageViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.FACEBOOK);
            }
        });
        imageViewGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.GMAIL);
            }
        });
        imageViewSnapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.SNAPCHAT);
            }
        });
        imageViewShareViaOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS.OTHER);
            }
        });
    }

    private void TrySharing(ShareHelper.SOCIAL_MEDIA_PLATFORMS socialMediaPlatform)
    {
        String isSelectedCLinkIDWithUserName = GetIsSelectedCLIDWithUserNameStr(isSelectedCLIDWithUserName);
        if(isSelectedCLinkIDWithUserName == null)
        {
            SoundHelper.PlayMediaPlayerSound(MarketerShareAppLinkOnSocialMediaActivity.this, SoundHelper.SOUNDS.NOT_AVAILABLE);
            return;
        }
        ShareOnSocialMedia(isSelectedCLinkIDWithUserName, socialMediaPlatform);
    }


    private void ShareOnSocialMedia(String isSelectedCLinkIDWithUserName, ShareHelper.SOCIAL_MEDIA_PLATFORMS social_media_platforms)
    {
        String generatedUUIDStr = UUID.randomUUID().toString();
        generatedUUIDStr = generatedUUIDStr.substring(0, Math.min(10, generatedUUIDStr.length()));
        AsyncTaskHelper.GenerateDisplayCommissionLink(this, getApplicationContext(), GlobalVariables.USER_ID, isSelectedCLinkIDWithUserName, social_media_platforms, generatedUUIDStr);
    }



    //Hide Keyboard
    public void HideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

}
