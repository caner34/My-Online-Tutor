package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MarketerCommissionLinkDisplayActivity extends AppCompatActivity {

    RadioGroup radioGroupCommissionLinkIDSelection;
    RadioButton radioButtonCommissionLinkUserName, radioButtonCommissionLinkIDGenerated;
    Button buttonDisplayGenerateCommissionLink;
    TextView textViewCommissionLink;
    Button buttonCopyToClipboard, buttonGenerateQRCode, buttonBackFromGenerateQRCode, buttonSaveGenerateQRCode;
    ImageView imageViewQRCode;
    LinearLayout linearLayoutAfterQRCodeDisplayedPanel;

    String isSelectedCLIDWithUserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_commission_link_display);

        InstantiateComponents();

        SetOnClickListeners();
    }

    private void InstantiateComponents()
    {
        radioGroupCommissionLinkIDSelection = findViewById(R.id.radioGroupCommissionLinkIDSelection);
        radioButtonCommissionLinkUserName = findViewById(R.id.radioButtonCommissionLinkUserName);
        radioButtonCommissionLinkIDGenerated = findViewById(R.id.radioButtonCommissionLinkIDGenerated);
        buttonDisplayGenerateCommissionLink = findViewById(R.id.buttonDisplayGenerateCommissionLink);
        textViewCommissionLink = findViewById(R.id.textViewCommissionLink);
        buttonCopyToClipboard = findViewById(R.id.buttonCopyToClipboard);
        buttonGenerateQRCode = findViewById(R.id.buttonGenerateQRCode);
        imageViewQRCode = findViewById(R.id.imageViewQRCode);
        buttonBackFromGenerateQRCode = findViewById(R.id.buttonBackFromGenerateQRCode);
        buttonSaveGenerateQRCode = findViewById(R.id.buttonSaveGenerateQRCode);
        linearLayoutAfterQRCodeDisplayedPanel = findViewById(R.id.linearLayoutAfterQRCodeDisplayedPanel);
    }

    private void SetOnClickListeners()
    {
        radioGroupCommissionLinkIDSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == radioButtonCommissionLinkUserName.getId())
                {
                    isSelectedCLIDWithUserName = "true";
                }
                else
                {
                    isSelectedCLIDWithUserName = "false";
                }
            }
        });
        buttonDisplayGenerateCommissionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayCommissionLink();
            }
        });

        buttonCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.CopyToClipboard(getApplicationContext(), textViewCommissionLink.getText().toString());
                new CustomToast(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), getString(R.string.marketer_commission_link_copied));
            }
        });
        buttonGenerateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageViewQRCode.setImageBitmap(QRCodeHelper.GenerateQRCode(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), textViewCommissionLink.getText().toString()));
                GenerateQRCode(imageViewQRCode);
            }
        });
        buttonBackFromGenerateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewQRCode.setVisibility(View.GONE);
                linearLayoutAfterQRCodeDisplayedPanel.setVisibility(View.GONE);
            }
        });
        buttonSaveGenerateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveGeneratedQRCode();
            }
        });
    }



    private void SaveGeneratedQRCode()
    {
        //Check if QR Code Exists
        if(imageViewQRCode.getVisibility() != View.VISIBLE || imageViewQRCode.getDrawable() == null)
        {
            new CustomToast(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), getString(R.string.marketer_warning_generate_qr_code_first));
        }

        //Check for Permission
        CommonUtils.isPermissionGranted(MarketerCommissionLinkDisplayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        GlobalVariables.AlertDialogDisplay(MarketerCommissionLinkDisplayActivity.this, path.getAbsolutePath());

        Bitmap bitmap = ((BitmapDrawable)imageViewQRCode.getDrawable()).getBitmap();
        String fileName;
        if(isSelectedCLIDWithUserName.equals("true"))
        {
            fileName = "online_hocam_qr_code_username" + GlobalVariables.USER_ID;
        }
        else
        {
            fileName = "online_hocam_qr_code_ref_id" + GlobalVariables.USER_ID;
        }
        File outputFile = new File(path, fileName+".jpg");



        /*
        if (!outputFile.exists())
        {
            outputFile.mkdirs();
        }*/

        try
        {
            OutputStream stream = null;
            stream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
        }
        catch (IOException e) // Catch the exception
        {
            new CustomToast(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), getString(R.string.marketer_warning_generate_qr_code_first));
        }

        Uri savedImageURI = Uri.parse(outputFile.getAbsolutePath());

        GlobalVariables.AlertDialogDisplay(MarketerCommissionLinkDisplayActivity.this, getString(R.string.marketer_qr_code_saved_successfully) + "\n\n" + savedImageURI.getPath());
    }

    private void DisplayCommissionLink()
    {
        if(!isSelectedCLIDWithUserName.isEmpty())
        {
            GenerateDisplayCommissionLink();
        }
        else
        {
            new CustomToast(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), getString(R.string.marketer_warning_no_link_code_type_selected));
        }
    }

    private void GenerateQRCode(ImageView imageViewQRCode)
    {
        if(!isSelectedCLIDWithUserName.isEmpty())
        {
            GenerateDisplayCommissionLink(imageViewQRCode, linearLayoutAfterQRCodeDisplayedPanel);
        }
        else
        {
            new CustomToast(MarketerCommissionLinkDisplayActivity.this, getApplicationContext(), getString(R.string.marketer_warning_no_link_code_type_selected));
        }
    }

    private void GenerateDisplayCommissionLink()
    {
        String generatedUUIDStr = UUID.randomUUID().toString();
        generatedUUIDStr = generatedUUIDStr.substring(0, Math.min(10, generatedUUIDStr.length()));
        AsyncTaskHelper.GenerateDisplayCommissionLink(this, getApplicationContext(), GlobalVariables.USER_ID, isSelectedCLIDWithUserName, textViewCommissionLink, generatedUUIDStr);
    }

    private void GenerateDisplayCommissionLink(ImageView imageViewQRCode, LinearLayout linearLayoutAfterQRCodeDisplayedPanel)
    {
        String generatedUUIDStr = UUID.randomUUID().toString();
        generatedUUIDStr = generatedUUIDStr.substring(0, Math.min(10, generatedUUIDStr.length()));
        AsyncTaskHelper.GenerateDisplayCommissionLink(this, getApplicationContext(), GlobalVariables.USER_ID, isSelectedCLIDWithUserName, textViewCommissionLink, generatedUUIDStr, imageViewQRCode, linearLayoutAfterQRCodeDisplayedPanel);
    }

}
