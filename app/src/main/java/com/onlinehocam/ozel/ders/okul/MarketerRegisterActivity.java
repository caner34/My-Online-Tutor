package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MarketerRegisterActivity extends AppCompatActivity {

    EditText editTextTutorName, editTextTutorSurname, editTextBirthDate, editTextPhoneNumber, editTextLastSchool, editTextDepartment, editTextIbanNo;
    ImageView imageViewPhoto, buttonTakePhoto, buttonImportPhoto;
    RadioButton radioButtonGenderMale,radioButtonGenderFemale;
    RadioGroup radioGroupGenderSelection;
    CheckBox checkboxIReadAndAcceptUserAgreement, checkboxNoIBANDeclarationYet;
    Spinner spinnerEducationalAttainment, spinnerCityOfResidency, spinnerDistrictOfResidency, spinnerCityOfRegistry, spinnerDistrictOfRegistry;
    ArrayAdapter arrayAdapterEducationalAttainment, arrayAdapterCityOfResidency, arrayAdapterDistrictOfResidency, arrayAdapterCityOfRegistry, arrayAdapterDistrictOfRegistry;
    public Button buttonRegisterUser;
    TextView textViewReadUserAgreement;


    LinearLayout linearLayoutMainProgressBar, linearLayoutButtonRegisterUser;

    Boolean isEditTextBirthDateForAcceptanceHasFocus = false;
    boolean isPhotoUploaded = false;

    CheckBox[] checkBoxesLessonsToPick;

    List<String> cities;
    List<List<String>> districts;

    //INFO Fields
    String name, surname, gender, birthDate, phoneNumber, lastSchool, educationField, marketingFieldSelections;
    int userID = -1;
    int photoID = -1;
    boolean isAgreementChecked;
    Drawable photoImage;
    int educationalAttainment = -1;
    int cityOfResidency = -1; int districtOfResidency = -1; int cityOfRegistry = -1; int districtOfRegistry = -1;

    public boolean isQuestionRequestPostedSuccessfully = false;
    String IBANNo = "";



    int[] marketingFieldsResourceIds = new int[]
            {
                    R.string.marketing_field_digital_marketing,
                    R.string.marketing_field_acquaintances,
                    R.string.marketing_field_schools,
                    R.string.marketing_field_districts,
                    R.string.marketing_field_reference_method,
                    R.string.marketing_field_distributorship
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_register);

        InitializeComponents();

        HandleSpinners();

        HideKeyboard();

        SetOnClickListeners();

        InitializeDatePickers();
    }

    private void HandleSpinners()
    {
        HandleSpinnersOnPlaceOfResidency();
        HandleSpinnersOnPlaceOfRegistry();
        HandleSpinnerEducationalAttainment();
    }

    private void HandleSpinnerEducationalAttainment()
    {
        arrayAdapterEducationalAttainment = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerCityOfResidency, Arrays.asList(GlobalVariables.educationAttainments), ""));
        spinnerEducationalAttainment.setAdapter(arrayAdapterEducationalAttainment);
        spinnerEducationalAttainment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    educationalAttainment = -1;
                }
                else
                {
                    educationalAttainment = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void HandleSpinnersOnPlaceOfResidency()
    {
        arrayAdapterCityOfResidency = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerCityOfResidency, cities, ""));
        spinnerCityOfResidency.setAdapter(arrayAdapterCityOfResidency);

        arrayAdapterDistrictOfResidency = new ArrayAdapter(this, android.R.layout.simple_spinner_item,  CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerDistrictOfResidency, new ArrayList<String>(){}, getString(R.string.register_please_select_city_first)) );
        spinnerDistrictOfResidency.setAdapter(arrayAdapterDistrictOfResidency);

        spinnerCityOfResidency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    cityOfResidency = -1;
                    arrayAdapterDistrictOfResidency = new ArrayAdapter(MarketerRegisterActivity.this, android.R.layout.simple_spinner_item, new String[]{ getString(R.string.register_please_select_city_first) });
                    spinnerDistrictOfResidency.setAdapter(arrayAdapterDistrictOfResidency);
                }
                else
                {
                    cityOfResidency = position;
                    List<String> crDistricts = GetDistrictList(position-1);

                    arrayAdapterDistrictOfResidency = new ArrayAdapter(MarketerRegisterActivity.this, android.R.layout.simple_spinner_item, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerDistrictOfResidency, crDistricts, "") );
                    spinnerDistrictOfResidency.setAdapter(arrayAdapterDistrictOfResidency);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDistrictOfResidency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    districtOfResidency = -1;
                }
                else
                {
                    districtOfResidency = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void HandleSpinnersOnPlaceOfRegistry()
    {
        arrayAdapterCityOfRegistry = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerCityOfRegistry, cities, ""));
        spinnerCityOfRegistry.setAdapter(arrayAdapterCityOfRegistry);

        arrayAdapterDistrictOfRegistry = new ArrayAdapter(this, android.R.layout.simple_spinner_item,  CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerDistrictOfRegistry, new ArrayList<String>(){}, getString(R.string.register_please_select_city_first)) );
        spinnerDistrictOfRegistry.setAdapter(arrayAdapterDistrictOfRegistry);

        spinnerCityOfRegistry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    cityOfRegistry = -1;
                    arrayAdapterDistrictOfRegistry = new ArrayAdapter(MarketerRegisterActivity.this, android.R.layout.simple_spinner_item, new String[]{ getString(R.string.register_please_select_city_first) });
                    spinnerDistrictOfRegistry.setAdapter(arrayAdapterDistrictOfRegistry);
                }
                else
                {
                    cityOfRegistry = position;
                    List<String> crDistricts = GetDistrictList(position-1);

                    arrayAdapterDistrictOfRegistry = new ArrayAdapter(MarketerRegisterActivity.this, android.R.layout.simple_spinner_item, CommonUtils.GetSpinnerStringArrayWithHeaderTitle(spinnerDistrictOfRegistry, crDistricts, "") );
                    spinnerDistrictOfRegistry.setAdapter(arrayAdapterDistrictOfRegistry);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDistrictOfRegistry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    districtOfRegistry = -1;
                }
                else
                {
                    districtOfRegistry = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void InitializeComponents()
    {
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextTutorName = findViewById(R.id.editTextTutorName);
        editTextTutorSurname = findViewById(R.id.editTextTutorSurname);
        editTextLastSchool = findViewById(R.id.editTextLastSchool);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        editTextIbanNo = findViewById(R.id.editTextIbanNo);

        FormatSomeEditTextFields();

        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        buttonImportPhoto = findViewById(R.id.buttonImportPhoto);

        radioButtonGenderFemale = findViewById(R.id.radioButtonGenderFemale);
        radioButtonGenderMale = findViewById(R.id.radioButtonGenderMale);
        radioGroupGenderSelection = findViewById(R.id.radioGroupGenderSelection);

        spinnerEducationalAttainment = findViewById(R.id.spinnerEducationalAttainment);
        spinnerCityOfResidency = findViewById(R.id.spinnerCityOfResidency);
        spinnerDistrictOfResidency = findViewById(R.id.spinnerDistrictOfResidency);
        spinnerCityOfRegistry = findViewById(R.id.spinnerCityOfRegistry);
        spinnerDistrictOfRegistry = findViewById(R.id.spinnerDistrictOfRegistry);

        checkboxNoIBANDeclarationYet = findViewById(R.id.checkboxNoIBANDeclarationYet);

        checkboxIReadAndAcceptUserAgreement = findViewById(R.id.checkboxIreadAndAcceptUserAgreement);
        buttonRegisterUser = findViewById(R.id.buttonRegisterUser);
        textViewReadUserAgreement = findViewById(R.id.textViewReadUserAgreement);

        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutButtonRegisterUser = findViewById(R.id.linearLayoutButtonRegisterUser);

        isAgreementChecked = false;

        CreateAndInsertCheckBoxesLessonsToPick();

        LoadCityDistrictData();


    }

    private void CreateAndInsertCheckBoxesLessonsToPick()
    {
        LinearLayout linearLayoutMarketingFieldPanel = findViewById(R.id.linearLayoutMarketingFieldPanel);
        checkBoxesLessonsToPick = new CheckBox[marketingFieldsResourceIds.length];
        for(int i = 0; i < checkBoxesLessonsToPick.length; i++)
        {
            CheckBox crCheckBox = new CheckBox(this);
            crCheckBox.setText(getString(marketingFieldsResourceIds[i]));
            checkBoxesLessonsToPick[i]= crCheckBox;
            linearLayoutMarketingFieldPanel.addView(crCheckBox);
        }
    }

    private void LoadCityDistrictData()
    {
        cities = new ArrayList<>();
        districts = new ArrayList<>();
        String[] mainInfo = GlobalVariables.CITIES_AND_DISTRICTS_COMMA_AND_PERCENT_SIGN_SEPERATED_STRING.split(";");

        String[] localInfo = new String[0];
        String[] districtInfo = new String[0];
        for(int i = 0; i < mainInfo.length; i++)
        {
            List<String> crDistrictList = new ArrayList<>();
            localInfo = (mainInfo[i]).split("%");
            cities.add(localInfo[0]);
        }
    }

    private List<String> GetDistrictList(int i)
    {
        String[] mainInfo = GlobalVariables.CITIES_AND_DISTRICTS_COMMA_AND_PERCENT_SIGN_SEPERATED_STRING.split(";");

        String[] localInfo = new String[0];
        String[] districtInfo = new String[0];
        List<String> crDistrictList = new ArrayList<>();
        localInfo = (mainInfo[i]).split("%");
        districtInfo = (localInfo[1]).split(",");
        for(int d = 0; d < districtInfo.length; d++)
        {
            crDistrictList.add(districtInfo[d]);
        }

        return crDistrictList;
    }


    private void SetOnClickListeners()
    {
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewPhoto.getTag().toString().equals(getString(R.string.constant_unknown)))
                {
                    new CustomToast(MarketerRegisterActivity.this, getApplicationContext(), getString(R.string.post_question_please_upload_question_photo));
                }
            }
        });
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted(Manifest.permission.CAMERA))
                {
                    TakePhoto();
                }
                else
                {
                    new CustomToast(MarketerRegisterActivity.this, getApplicationContext(), getString(R.string.permission_check_camera_permission_is_needed));
                }
            }
        });
        buttonImportPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    ImportPhoto();
                }
                else
                {
                    new CustomToast(MarketerRegisterActivity.this, getApplicationContext(), getString(R.string.permission_check_read_external_storage_permission_is_needed));
                }
            }
        });
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditTextBirthDateForAcceptanceHasFocus)
                {
                    PopUpDateOfBirthSelectionDialog();
                }
            }
        });

        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isQuestionRequestPostedSuccessfully)
                {
                    CheckIfFieldsFilledAndExecuteRegisteringUserIfNeeded();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), MarketerHomePageActivity.class));
                }
            }
        });

        radioGroupGenderSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(!isPhotoUploaded)
                {
                    if(checkedId == radioButtonGenderFemale.getId())
                    {
                        imageViewPhoto.setImageResource(R.drawable.female_template);
                    }
                    else
                    {
                        imageViewPhoto.setImageResource(R.drawable.male_template);
                    }
                }
            }
        });

        textViewReadUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.AlertDialogDisplay(MarketerRegisterActivity.this, GlobalVariables.MARKETER_USER_AGREEMENT);
            }
        });
    }

    private void CheckIfFieldsFilledAndExecuteRegisteringUserIfNeeded()
    {
        name = editTextTutorName.getText().toString();
        surname = editTextTutorSurname.getText().toString();
        gender = "";

        if(radioGroupGenderSelection.getCheckedRadioButtonId() != -1)
        {
            if(radioGroupGenderSelection.getCheckedRadioButtonId() == radioButtonGenderMale.getId())
            {
                gender = "male";
            }
            else
            {
                gender = "female";
            }
        }
        birthDate = editTextBirthDate.getText().toString();
        PhoneNumberChecker pnc = new PhoneNumberChecker();
        pnc.isPhoneNumberToBeSet(editTextPhoneNumber);
        lastSchool = editTextLastSchool.getText().toString();
        educationField = editTextDepartment.getText().toString();
        marketingFieldSelections = GetMarketingFields();


        if(editTextIbanNo.getText().toString().length() == 29)
        {
            IBANNo = editTextIbanNo.getText().toString();
        }
        else
        {
            IBANNo = DB.CONS_EMPTY;
        }

        if(!isPhotoUploaded)
        {
            photoImage = null;
        }
        else
        {
            photoImage = imageViewPhoto.getDrawable();
        }

        String warningMessage = "";
        if(name.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_your_name)).substring(0,getString(R.string.register_your_name).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(surname.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_your_surname)).substring(0,getString(R.string.register_your_surname).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(gender.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_your_gender)).substring(0,getString(R.string.register_your_gender).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(birthDate.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_your_birthdate)).substring(0,getString(R.string.register_your_birthdate).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(phoneNumber.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_your_phone_number)).substring(0,getString(R.string.register_your_phone_number).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(educationalAttainment == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_educational_attainment)).substring(0,getString(R.string.register_educational_attainment).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(lastSchool.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_last_school)).substring(0,getString(R.string.register_last_school).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(educationField.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_last_department)).substring(0,getString(R.string.register_last_department).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(marketingFieldSelections.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_marketer_preferred_marketing_fields_selections_for_marketing)).substring(0,getString(R.string.register_marketer_preferred_marketing_fields_selections_for_marketing).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(cityOfResidency == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_city_of_residency)).substring(0,getString(R.string.register_city_of_residency).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(districtOfResidency == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_district_of_residency)).substring(0,getString(R.string.register_district_of_residency).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(cityOfRegistry == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_city_of_registry)).substring(0,getString(R.string.register_city_of_registry).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(districtOfRegistry == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_district_of_registry)).substring(0,getString(R.string.register_district_of_registry).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(districtOfRegistry == -1)
        {
            warningMessage = getString(R.string.register_warning_please) + " " + (getString(R.string.register_district_of_registry)).substring(0,getString(R.string.register_district_of_registry).length()-1) + " " + getString(R.string.fill_the_field);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(!checkboxNoIBANDeclarationYet.isChecked() && IBANNo.isEmpty())
        {
            warningMessage = getString(R.string.register_warning_enter_iban_no_or_select_later);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else if(!checkboxIReadAndAcceptUserAgreement.isChecked())
        {
            warningMessage = getString(R.string.register_warning_please_check_user_agreement);
            new CustomToast(this, getApplicationContext(), warningMessage);
        }
        else
        {
            RegisterUser();
        }
    }

    private String GetMarketingFields()
    {
        String result = "";
        for (int i = 0; i < checkBoxesLessonsToPick.length; i++)
        {
            if(checkBoxesLessonsToPick[i].isChecked())
            {
                int crClassID = GetMarketingFieldIDByMarketingFieldName(checkBoxesLessonsToPick[i].getText().toString());

                if(crClassID != -1)
                {
                    if(!result.isEmpty())
                    {
                        result += ",";
                    }
                    result += crClassID;
                }
            }
        }
        //GlobalVariables.Log(getApplicationContext(), result);
        return result;
    }

    private int GetMarketingFieldIDByMarketingFieldName(String marketingFieldName)
    {
        int result = -1;
        for(int i = 0; i < marketingFieldsResourceIds.length; i++)
        {
            if(getString(marketingFieldsResourceIds[i]).equals(marketingFieldName))
            {
                return i+1;
            }
        }
        return result;
    }

    class PhoneNumberChecker
    {

        private boolean isPhoneNumberToBeSet(EditText editTextPhoneNumber)
        {
            if(editTextPhoneNumber.getText().toString().length() != 19)
            {
                phoneNumber = "";
                return false;
            }
            else
            {
                int[] digits = CreateDigitsArray(editTextPhoneNumber.getText().toString());
                String phoneNumberStr = "";
                for(int i = 0; i < digits.length; i++)
                {
                    phoneNumberStr += digits[i];
                }
                phoneNumber = phoneNumberStr;
                return true;
            }
        }

        int CountDigitsExceptAreaCode(String text)
        {
            int count = 0;
            for(int i = 0; i < text.length(); i++)
            {
                if((""+text.charAt(i)).matches("[0-9]+"))
                {
                    count++;
                }
            }
            count -= 2;
            if(count < 0)
            {
                return -1;
            }
            else if(count >= 10)
            {
                return 10;
            }
            return count;
        }

        int[] CreateDigitsArray(String text)
        {
            int[] result;
            int digitCountedValue = CountDigitsExceptAreaCode(text);
            if (digitCountedValue == -1)
            {
                result = new int[1];

                for(int i = 0; i < text.length(); i++)
                {
                    if((""+text.charAt(i)).matches("[0-9]+"))
                    {
                        result[0] = Integer.parseInt((""+text.charAt(i)));
                        break;
                    }
                }
            }
            else
            {
                result = new int[digitCountedValue];
                int numberCount = 0;

                for(int i = 0; i < text.length(); i++)
                {
                    if((""+text.charAt(i)).matches("[0-9]+"))
                    {
                        if(numberCount >1)
                            result[numberCount-2] = Integer.parseInt((""+text.charAt(i)));

                        numberCount++;
                    }


                    if(numberCount == 12)
                    {
                        break;
                    }
                }

            }

            return result;
        }
    }

    private void RegisterUser()
    {
        userID = GlobalVariables.USER_ID;
        //ServerHelper.GetUserIDByUserName(this, getApplicationContext(), GlobalVariables.USER_NAME);
        if(userID == -1)
        {
            //TODO with this error user needs to be uninstall and reinstall the app, improve the situation by asking acceptance with alertDialog and reCreate the SQLite DB and than go to Lunch or SignUp Activity for user to start a fresh SignUp And Register
            SoundHelper.PlayMediaPlayerSound(MarketerRegisterActivity.this, SoundHelper.SOUNDS.CONNECTION_FAILED);
            GlobalVariables.AlertDialogDisplay(this, getString(R.string.register_warning_error_getting_user_name));
            return;
        }

        Bitmap bitmap;
        if(isPhotoUploaded)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) photoImage;
            bitmap = bitmapDrawable.getBitmap();
        }
        else
        {
            bitmap = null;
        }
        String photoDataAddressID = "" + GlobalVariables.USER_ID + UUID.randomUUID().toString();

        Marketer crMarketer = CreateMarketerObjectBasedOnTheInfoInFields();

        AsyncTaskHelper.UploadMiniUserPhotoToDirectory(MarketerRegisterActivity.this, getApplicationContext(), crMarketer, bitmap,
                photoDataAddressID, linearLayoutMainProgressBar, linearLayoutButtonRegisterUser,true, false);
    }

    private Marketer CreateMarketerObjectBasedOnTheInfoInFields()
    {
        Marketer result = new Marketer(name, surname, gender, birthDate, educationalAttainment, lastSchool, educationField, cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry, phoneNumber, userID, photoID, IBANNo, marketingFieldSelections);
        return result;
    }


    private void FormatSomeEditTextFields()
    {
        editTextPhoneNumber.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(getApplicationContext(), editTextPhoneNumber));
        editTextIbanNo.addTextChangedListener(new CustomIBANNoFormattingTextWatcher(getApplicationContext(), editTextIbanNo));
    }


    void InitializeDatePickers()
    {
        //Date Of Birth Picker
        editTextBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    PopUpDateOfBirthSelectionDialog();
                }
                else
                {
                    isEditTextBirthDateForAcceptanceHasFocus = false;
                }
            }
        });

        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditTextBirthDateForAcceptanceHasFocus)
                {
                    PopUpDateOfBirthSelectionDialog();
                }
            }
        });
    }


    void PopUpDateOfBirthSelectionDialog()
    {
        isEditTextBirthDateForAcceptanceHasFocus = true;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 18;
        int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(MarketerRegisterActivity.this,
                android.R.style.Theme_Material_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String date = year + "-" + month  + "-" + dayOfMonth;
                        editTextBirthDate.setText(date);
                    }
                }, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }



    //PHOTO IMPORT OR TAKE


    private void ImportPhoto()
    {
        OpenFileDialogAndGetPhotoPath();
    }




    boolean isPermissionGranted(String permissionStr)
    {
        if (ContextCompat.checkSelfPermission(this, permissionStr) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { permissionStr }, 0);
            return false;
        }
        else
        {
            return true;
        }
    }


    private void SetImportedPhotoToImageView(Uri imageUri)  {

        Bitmap bitmap = null;
        try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                ImageDecoder.createSource(this.getContentResolver(), imageUri);
            }
            else
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        imageViewPhoto.setImageBitmap(bitmap);
        imageViewPhoto.setTag(getString(R.string.constant_set));
        isPhotoUploaded = true;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

    private void OpenFileDialogAndGetPhotoPath()
    {
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
    }

    private void TakePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // To Take Photo
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bitmap thePic = data.getParcelableExtra("data");
                imageViewPhoto.setImageBitmap(FlippedImage(thePic));
                imageViewPhoto.setTag(getString(R.string.constant_set));
                isPhotoUploaded = true;
            }
        }

        // To Import Photo
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedFileUri = data.getData(); //The uri with the location of the file
            SetImportedPhotoToImageView(selectedFileUri);
        }
    }

    public static Bitmap FlippedImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();

        matrix.preScale(-1.0f, 1.0f);

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }







    //Hide Keyboard
    public void HideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }


}
