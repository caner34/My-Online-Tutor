package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class StudentPostQuestionActivity extends AppCompatActivity {


    ImageView imageViewPhoto, buttonTakePicture, buttonImportPicture;
    EditText editTextPageNo, editTextAppreciatedPrice, editTextLastDateOfDelivery, editTextDueDateForAcceptance;
    Spinner spinnerClass, spinnerLesson, spinnerPublisher, spinnerBookName, spinnerTutorToRequest;
    public Button buttonPostQuestionRequest;

    Boolean isEditTextDueDateForAcceptanceHasFocus = false;
    Boolean isEditTextLastDateOfDeliveryHasFocus = false;
    EditText[] editTextsDate;
    Boolean[] booleansDate;

    ArrayAdapter arrayAdapterClass, arrayAdapterLesson, arrayAdapterPublisher, arrayAdapterBookName, arrayAdapterTutorToRequest;
    EditText editTextOtherPublisher, editTextOtherBookName;
    LinearLayout linearLayoutPanelOtherPublisher, linearLayoutPanelOtherBookName;

    String className = "";
    String lessonName = "";
    String publisherName = "";
    String bookName = "";
    int pageNo = -1;
    String tutorNameToRequest = "";
    double appreciatedPrice = -1.0;
    String lastDateOfDelivery = "";
    String dueDateForAcceptance = "";

    public boolean isQuestionRequestPostedSuccessfully = false;
    int photoID = -1;


    int questionRequestID;
    boolean isQuestionOriginalNotRequestAgain = true;
    public QuestionRequest inheritedQuestionRequest;
    public LinearLayout linearLayoutMainProgressBar, linearLayoutButtonPostQuestionRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_post_question);

        questionRequestID = getIntent().getIntExtra("questionRequestID", -1);


        InstantiateComponents();

        InitializeArrays();

        HideKeyboard();

        HandleSpinners();

        InitializeDatePickers();

        SetOnClickListeners();

        CheckAndTakeActionIfNeededForQuestionRequestAgain();
    }


    private void CheckAndTakeActionIfNeededForQuestionRequestAgain()
    {
        if(questionRequestID != -1)
        {
            isQuestionOriginalNotRequestAgain = false;
            linearLayoutButtonPostQuestionRequest.setVisibility(View.GONE);

            //inheritedQuestionRequest = ServerHelper.GetQuestionRequestByQuestionRequestID(questionRequestID, getApplicationContext());
            AsyncTaskHelper.SetAndHandleGetQuestionRequestByQuestionRequestID(this, getApplicationContext(), linearLayoutMainProgressBar, questionRequestID, true, true);
        }
    }

    public void HandleTheRestInCaseRePostQuestionAction()
    {GlobalVariables.Log(getApplicationContext(), "inheritedQuestionRequest: "+inheritedQuestionRequest);
        if(inheritedQuestionRequest == null)
        {
            new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.question_requests_warning_an_error_occured));
            startActivity(new Intent(StudentPostQuestionActivity.this, StudentQuestionRequestDisplayActivity.class));
        }

        photoID = inheritedQuestionRequest.questionImageID;
        //Bitmap questionImageBitmap = ServerHelper.GetBitmapFromQuestionImageID(photoID);
        AsyncTaskHelper.DisplayQuestionImageOnAlertDialogByQuestionRequestId(getApplicationContext(), inheritedQuestionRequest.questionRequestID, imageViewPhoto);
        /*if(questionImageBitmap == null)
        {
            new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.question_requests_warning_an_error_occured));
            startActivity(new Intent(StudentPostQuestionActivity.this, StudentQuestionRequestDisplayActivity.class));
        }

        imageViewPhoto.setImageBitmap(questionImageBitmap);*/
        imageViewPhoto.setTag(getString(R.string.constant_set));
        className = ConvertClassEnumToClassNameString(inheritedQuestionRequest.questionClass);
        lessonName = inheritedQuestionRequest.questionLesson;
        publisherName = inheritedQuestionRequest.publisher;
        bookName = inheritedQuestionRequest.bookName;
        pageNo = inheritedQuestionRequest.pageNo;

        //disable components

        int classIndex = GetPositionOfTheElement(className, LessonsAndClasses.CLASS_NAMES);
        if(classIndex == -1)
        {
            className = "";
        }
        else
        {
            spinnerClass.setSelection(classIndex);
            spinnerClass.setEnabled(false);

            List<String> stringList = (List<String>) (GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[classIndex-1]));
            String[] stringArrayLessons = new String[stringList.size()];
            (stringList).toArray(stringArrayLessons);
            final int lessonIndex = GetPositionOfTheElement(lessonName, stringArrayLessons);
            //GlobalVariables.Log(getApplicationContext(), "lessonIndex: "+lessonIndex);
            if(lessonIndex == -1)
            {
                lessonName = "";
            }
            else
            {
                CreateAndSetArrayAdapterLessonsByClassSpinnerSelectionPosition(classIndex);
                spinnerLesson.setSelection(lessonIndex, true);
                spinnerLesson.setEnabled(false);
            }
        }

        int publisherIndex = GetPositionOfTheElement(publisherName, LessonsAndClasses.PUBLISHER_NAMES);
        //GlobalVariables.Log(getApplicationContext(), "publisherIndex: "+publisherIndex);
        if(publisherIndex == -1 || publisherIndex == LessonsAndClasses.PUBLISHER_NAMES.length)
        {
            publisherIndex = LessonsAndClasses.PUBLISHER_NAMES.length;
            spinnerPublisher.setSelection(publisherIndex);
            spinnerPublisher.setEnabled(false);

            if(editTextOtherPublisher == null)
            {
                linearLayoutPanelOtherPublisher.setVisibility(View.VISIBLE);
                editTextOtherPublisher = findViewById(R.id.editTextOtherPublisher);
            }

            editTextOtherPublisher.setText(publisherName);
            editTextOtherPublisher.setEnabled(false);
        }
        else if(publisherIndex == LessonsAndClasses.PUBLISHER_NAMES.length - 1)
        {
            //publisherIndex = LessonsAndClasses.PUBLISHER_NAMES.length;
            spinnerPublisher.setSelection(publisherIndex);
            spinnerPublisher.setEnabled(false);

            if(editTextOtherPublisher == null)
            {
                linearLayoutPanelOtherPublisher.setVisibility(View.VISIBLE);
                editTextOtherPublisher = findViewById(R.id.editTextOtherPublisher);
            }

            editTextOtherPublisher.setText(publisherName);
            editTextOtherPublisher.setEnabled(false);
        }
        else
        {
            spinnerPublisher.setSelection(publisherIndex);
            spinnerPublisher.setEnabled(false);
        }



        //set book name UI components
        CreateAndSetArrayAdapterBookNameByPublisherSpinnerSelectionPosition(publisherIndex);

        List<String> stringListBookName = (List<String>) (GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[publisherIndex-1]));
        String[] stringArrayBookName = new String[stringListBookName.size()];
        (stringListBookName).toArray(stringArrayBookName);
        int bookIndex = arrayAdapterBookName.getPosition(bookName);        //GetPositionOfTheElement(bookName, stringArrayBookName);
        //GlobalVariables.Log(getApplicationContext(), "bookIndex: "+bookIndex+"  bookName: "+bookName + "  stringArrayBookName  >>>  "+Arrays.toString(stringArrayBookName));
        if(bookIndex == -1)
        {
            bookIndex = arrayAdapterBookName.getCount()-1;
            spinnerBookName.setSelection(bookIndex);
            spinnerBookName.setEnabled(false);

            if(editTextOtherBookName == null)
            {
                //GlobalVariables.Log(getApplicationContext(),"book Other step2: ");
                linearLayoutPanelOtherBookName.setVisibility(View.VISIBLE);
                editTextOtherBookName = findViewById(R.id.editTextOtherBookName);

                editTextOtherBookName.setText(bookName);
                editTextOtherBookName.setEnabled(false);
                //GlobalVariables.Log(getApplicationContext(),"book Other step2 b: ");
            }
            else
            {
                //GlobalVariables.Log(getApplicationContext(),"book Other step3: ");
                editTextOtherBookName.setText(bookName);
                editTextOtherBookName.setEnabled(false);
                //GlobalVariables.Log(getApplicationContext(),"book Other step3 b: ");
            }

        }
        else
        {
            int getPosResult = arrayAdapterBookName.getPosition(bookName);
            spinnerBookName.setSelection(getPosResult, false);    //spinnerBookName.setSelection(bookIndex);
            spinnerBookName.setEnabled(false);
        }



        editTextPageNo.setText(""+pageNo);
        editTextPageNo.setEnabled(false);

        editTextAppreciatedPrice.setText(""+inheritedQuestionRequest.appreciatedPrice);
        editTextAppreciatedPrice.requestFocus();





        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.post_question_question_photo_already_exists));
            }
        });
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.post_question_question_photo_already_exists));
            }
        });
        buttonImportPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.post_question_question_photo_already_exists));
            }
        });
    }

    private String ConvertClassEnumToClassNameString(LessonsAndClasses.CLASSES questionClass)
    {
        String result = "";

        for(int i = 0; i < LessonsAndClasses.CLASSES.values().length; i++)
        {
            if(LessonsAndClasses.CLASSES.values()[i] == questionClass)
            {
                result = LessonsAndClasses.CLASS_NAMES[i];
                return result;
            }
        }

        return result;
    }

    private int GetPositionOfTheElement(String value, String[] array)
    {
        int defaultResult = -1;
        for (int i = 0; i < array.length; i++)
        {
            if(((String)array[i]).equals(value))
            {
                return i+1;
            }
        }
        return defaultResult;
    }

    private void ReturnIfNotOriginal()
    {
        if(!isQuestionOriginalNotRequestAgain)
        {
            return;
        }
    }

    private void InitializeArrays()
    {
        editTextsDate = new EditText[] { editTextDueDateForAcceptance, editTextLastDateOfDelivery };
        booleansDate = new Boolean[] { isEditTextDueDateForAcceptanceHasFocus, isEditTextLastDateOfDeliveryHasFocus };
    }

    private String[] GetSpinnerStringArrayWithHeaderTitle(Spinner s, String[] originalArray)
    {
        String[] result = new String[originalArray.length+1];
        result[0] = s.getPrompt().toString();
        for(int i = 0; i < originalArray.length; i++)
        {
            result[i+1] = originalArray[i];
        }
        return result;
    }

    private String[] GetSpinnerStringArrayWithBottomItems(String[] originalArray, String[] bottomItemsArray)
    {
        String[] result = new String[originalArray.length + bottomItemsArray.length];

        for(int i = 0; i < originalArray.length; i++)
        {
            result[i] = originalArray[i];
        }
        for(int i = 0; i < bottomItemsArray.length; i++)
        {
            result[i + originalArray.length] = bottomItemsArray[i];
        }
        return result;
    }

    private void HandleSpinners()
    {
        HandleSpinnerClassAndLessons();
        HandleSpinnerPublisherAndBookName();
        HandleSpinnerTutorToRequest();
    }

    private void HandleSpinnerPublisherAndBookName()
    {
        arrayAdapterPublisher = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerPublisher, LessonsAndClasses.PUBLISHER_NAMES));

        spinnerPublisher.setAdapter(arrayAdapterPublisher);
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    publisherName = "";

                    arrayAdapterBookName = new ArrayAdapter(StudentPostQuestionActivity.this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerPublisher,
                            new String[]{spinnerBookName.getPrompt().toString()} ));
                    spinnerBookName.setAdapter(arrayAdapterBookName);

                    if(editTextOtherPublisher != null)
                    {
                        linearLayoutPanelOtherPublisher.setVisibility(View.GONE);
                        editTextOtherPublisher = null;
                    }
                }
                else
                {
                    if(questionRequestID == -1)
                    {
                        CreateAndSetArrayAdapterBookNameByPublisherSpinnerSelectionPosition(position);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBookName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    bookName = "";

                    if(editTextOtherBookName != null)
                    {
                        linearLayoutPanelOtherBookName.setVisibility(View.GONE);
                        editTextOtherBookName = null;
                    }
                }
                else if(position == (GlobalVariables.lessonsAndClasses.mapBookNames.get( LessonsAndClasses.PUBLISHERS.values()[ Arrays.asList(LessonsAndClasses.PUBLISHER_NAMES).indexOf(publisherName) ] )).size() - 1 + 1)
                {
                    if(questionRequestID == -1)
                    {
                        CreateAndHandleOtherBookComponentsInCaseOtherPublisher();
                    }
                }
                else
                {
                    if(questionRequestID == -1)
                    {
                        CreateAndHandleOtherBookComponentsInCaseUnknownPublisher(position);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CreateAndHandleOtherBookComponentsInCaseUnknownPublisher(int position)
    {
        bookName = GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[Arrays.asList(LessonsAndClasses.PUBLISHER_NAMES).indexOf(publisherName)]).get(position-1);

        //GlobalVariables.Log(getApplicationContext(),"book Other step1.1 b: ");
        if(editTextOtherBookName != null)
        {
            //GlobalVariables.Log(getApplicationContext(),"book Other step1.2 b: ");
            linearLayoutPanelOtherBookName.setVisibility(View.GONE);
            editTextOtherBookName = null;
        }
    }

    private void CreateAndHandleOtherBookComponentsInCaseOtherPublisher()
    {
        //GlobalVariables.Log(getApplicationContext(),"book Other step1.1 a: ");
        if(editTextOtherBookName == null)
        {
            //GlobalVariables.Log(getApplicationContext(),"book Other step1.2 a: ");
            linearLayoutPanelOtherBookName.setVisibility(View.VISIBLE);
            editTextOtherBookName = findViewById(R.id.editTextOtherBookName);
        }
    }

    private void CreateAndSetArrayAdapterBookNameByPublisherSpinnerSelectionPosition(int position)
    {
        publisherName = LessonsAndClasses.PUBLISHER_NAMES[position-1];
        List<String> stringList = (List<String>) (GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[position-1]));
        String[] stringArray = new String[stringList.size()];
        (stringList).toArray(stringArray);
        String[] stringArrayEnlarged = GetSpinnerStringArrayWithHeaderTitle(spinnerBookName, stringArray );
        arrayAdapterBookName = new ArrayAdapter(StudentPostQuestionActivity.this, android.R.layout.simple_spinner_item, stringArrayEnlarged );
        spinnerBookName.setAdapter(arrayAdapterBookName);
        arrayAdapterBookName.notifyDataSetChanged();

        if(position == LessonsAndClasses.PUBLISHER_NAMES.length - 1 + 1)
        {
            if(editTextOtherPublisher == null)
            {
                linearLayoutPanelOtherPublisher.setVisibility(View.VISIBLE);
                editTextOtherPublisher = findViewById(R.id.editTextOtherPublisher);
            }
        }
        else
        {
            if(editTextOtherPublisher != null)
            {
                linearLayoutPanelOtherPublisher.setVisibility(View.GONE);
                editTextOtherPublisher = null;
            }
        }
    }

    private void HandleSpinnerTutorToRequest()
    {
        final String[] favoriteTutorNames = ServerHelper.GetFavoriteTutorsNames(GlobalVariables.USER_ID);
        arrayAdapterTutorToRequest = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerTutorToRequest,  favoriteTutorNames));
        spinnerTutorToRequest.setAdapter(arrayAdapterTutorToRequest);
        spinnerTutorToRequest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    tutorNameToRequest = "";
                }
                else
                {
                    tutorNameToRequest = favoriteTutorNames[position-1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void HandleSpinnerClassAndLessons()
    {
        arrayAdapterClass = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerClass, LessonsAndClasses.CLASS_NAMES));

        spinnerClass.setAdapter(arrayAdapterClass);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    className = "";

                    arrayAdapterLesson = new ArrayAdapter(StudentPostQuestionActivity.this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerClass,
                            new String[]{spinnerLesson.getPrompt().toString()} ));
                    spinnerLesson.setAdapter(arrayAdapterLesson);
                }
                else
                {
                    if(questionRequestID == -1)
                    {
                        CreateAndSetArrayAdapterLessonsByClassSpinnerSelectionPosition(position);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLesson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    lessonName = "";
                }
                else
                {
                    lessonName = GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[Arrays.asList(LessonsAndClasses.CLASS_NAMES).indexOf(className)]).get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void CreateAndSetArrayAdapterLessonsByClassSpinnerSelectionPosition(int position)
    {
        className = LessonsAndClasses.CLASS_NAMES[position-1];
        List<String> stringList = (List<String>) (GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[position-1]));
        String[] stringArray = new String[stringList.size()];
        (stringList).toArray(stringArray);
        String[] stringArrayEnlarged = GetSpinnerStringArrayWithHeaderTitle(spinnerLesson, stringArray );
        arrayAdapterLesson = new ArrayAdapter(StudentPostQuestionActivity.this, android.R.layout.simple_spinner_item, stringArrayEnlarged );
        spinnerLesson.setAdapter(arrayAdapterLesson);
        //GlobalVariables.Log(getApplicationContext(), "spinnerLesson.count A:" +spinnerLesson.getAdapter().getCount());
    }

    private void SetOnClickListeners()
    {
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewPhoto.getTag().toString().equals(getString(R.string.constant_unknown)))
                {
                    new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.post_question_please_upload_question_photo));
                }
            }
        });
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted(Manifest.permission.CAMERA))
                {
                    TakePhoto();
                }
                else
                {
                    new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.permission_check_camera_permission_is_needed));
                }
            }
        });
        buttonImportPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                    ImportPhoto();
                }
                else
                {
                    new CustomToast(StudentPostQuestionActivity.this, getApplicationContext(), getString(R.string.permission_check_read_external_storage_permission_is_needed));
                }
            }
        });
        buttonPostQuestionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isQuestionRequestPostedSuccessfully)
                {
                    CheckIfFieldsFilledAndExecutePostingQuestionRequestIfNeeded();
                }
                else
                {
                    SoundHelper.PlayMediaPlayerSound(StudentPostQuestionActivity.this, SoundHelper.SOUNDS.MENU_CLICK);
                    startActivity(new Intent(getApplicationContext(), StudentHomePageActivity.class));
                }
            }
        });
    }

    private void CheckIfFieldsFilledAndExecutePostingQuestionRequestIfNeeded()
    {
        boolean isSendingRequest = false;
        if(imageViewPhoto.getTag().toString().equals(getString(R.string.constant_unknown)))
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_question_image));
        }
        else if(className.isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_class_name));
        }
        else if(lessonName.isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_lesson_name));
        }
        else if(publisherName.isEmpty() && editTextOtherPublisher != null && ((publisherName = editTextOtherPublisher.getText().toString()).isEmpty()) )
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_publisher_name));
        }
        else if(bookName.isEmpty() && (editTextOtherBookName == null || ((bookName = editTextOtherBookName.getText().toString()).isEmpty()))  )
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_book_name));
        }
        else if(!bookName.equals(LessonsAndClasses.UNKNOWN) && (editTextPageNo.getText().toString().isEmpty() || (pageNo = Integer.parseInt(editTextPageNo.getText().toString())) == -1)  )
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_page_no));
        }
        else if(tutorNameToRequest.isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_tutor_name_to_request));
        }
        else if( editTextAppreciatedPrice.getText().toString().isEmpty() ||  (appreciatedPrice = Double.parseDouble(editTextAppreciatedPrice.getText().toString())) == -1.0)
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_appreciated_price));
        }
        else if( appreciatedPrice < GlobalVariables.MIN_APPRECIATED_PRICE)
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_appreciated_price_below_min) + " (₺"+GlobalVariables.MIN_APPRECIATED_PRICE+"0)");
        }
        else if((dueDateForAcceptance = CommonUtils.ConvertDate_DD_MM_YYYY_INTO_YYYY_MM_DD(editTextDueDateForAcceptance.getText().toString(), getApplicationContext())).isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_due_date_of_acceptance));
        }
        else if((lastDateOfDelivery = CommonUtils.ConvertDate_DD_MM_YYYY_INTO_YYYY_MM_DD(editTextLastDateOfDelivery.getText().toString(), getApplicationContext())).isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_fill_last_date_of_delivery));
        }
        else if(!isFirstDateBeforeSecondDate(dueDateForAcceptance, lastDateOfDelivery))
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_warning_last_date_of_delivery_is_not_after_due_date_of_acceptance));
        }
        else
        {
            isSendingRequest = true;
            SendQuestionRequest();
        }
        if(!isSendingRequest)
        {
            SoundHelper.PlayMediaPlayerSound(StudentPostQuestionActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
        }
    }

    private boolean isFirstDateBeforeSecondDate(String firstDateStr, String secondDateStr)
    {
        // DAY, MONTH, YEAR
        int[] firstDateValues = new int[3];
        int[] secondDateValues = new int[3];

        String[] firstDateStrValues = firstDateStr.split(GlobalVariables.DATE_SEPERATOR_DASH);
        String[] secondDateStrValues = secondDateStr.split(GlobalVariables.DATE_SEPERATOR_DASH);

        //fill arrays
        for(int i = 0; i < firstDateValues.length; i++)
        {
            firstDateValues[i] = Integer.parseInt(firstDateStrValues[i]);
            secondDateValues[i] = Integer.parseInt(secondDateStrValues[i]);
        }

        //compare
        for(int i = 0; i < firstDateValues.length; i++)
        {
            if( firstDateValues[firstDateValues.length-1-i] < secondDateValues[firstDateValues.length-1-i] )
            {
                return true;
            }
        }

        return false;
    }

    private void SendQuestionRequest()
    {
        QuestionRequest crQuestionRequest = CreateQuestionRequestBasedOnTheInfoInFields();
        if(isQuestionOriginalNotRequestAgain)
        {
            //photoID = ServerHelper.PostQuestionRequest(crQuestionRequest, imageViewPhoto.getDrawable(), getApplicationContext());

            Bitmap imageBitmap = ((BitmapDrawable)(imageViewPhoto.getDrawable())).getBitmap();
            String imageDataAddressID = "" + GlobalVariables.USER_ID + (""+(crQuestionRequest.pageNo)).substring(0, Math.min(3, (""+(crQuestionRequest.pageNo)).length())) + UUID.randomUUID().toString();
            AsyncTaskHelper.UploadQuestionImageToDirectory(this, getApplicationContext(), crQuestionRequest, imageBitmap, imageDataAddressID, linearLayoutMainProgressBar
                    , linearLayoutButtonPostQuestionRequest, true, false);
        }
        else
        {
            boolean isQuestionRequestNotADuplicate = ServerHelper.isQuestionRequestNotADuplicate(crQuestionRequest, photoID, getApplicationContext());
            if(isQuestionRequestNotADuplicate)
            {
                //photoID = ServerHelper.PostQuestionRequest(crQuestionRequest, imageViewPhoto.getDrawable(), getApplicationContext());
                crQuestionRequest.questionImageID = inheritedQuestionRequest.questionImageID;

                Bitmap imageBitmap = ((BitmapDrawable)(imageViewPhoto.getDrawable())).getBitmap();
                String imageDataAddressID = "" + GlobalVariables.USER_ID + (""+(crQuestionRequest.pageNo)).substring(0, Math.min(3, (""+(crQuestionRequest.pageNo)).length())) + UUID.randomUUID().toString();
                AsyncTaskHelper.UploadQuestionImageToDirectory(this, getApplicationContext(), crQuestionRequest, imageBitmap, imageDataAddressID, linearLayoutMainProgressBar
                        , linearLayoutButtonPostQuestionRequest, true, false);
            }
            else
            {
                photoID = -2;
            }
        }


        if(  photoID == -2  )
        {
            SoundHelper.PlayMediaPlayerSound(StudentPostQuestionActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
            GlobalVariables.AlertDialogDisplay(this, getString(R.string.post_question_response_already_requested_before));
            isQuestionRequestPostedSuccessfully = false;
            photoID = inheritedQuestionRequest.questionImageID;
        }
        /*else if(photoID == -1)
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_response_failed));
            isQuestionRequestPostedSuccessfully = false;
        }
        else
        {
            GlobalVariables.AlertDialogDisplay(StudentPostQuestionActivity.this, getString(R.string.post_question_response_succesful));
            isQuestionRequestPostedSuccessfully = true;
            buttonPostQuestionRequest.setText(R.string.menu_main_manu);
        }*/
    }

    private QuestionRequest CreateQuestionRequestBasedOnTheInfoInFields()
    {
        QuestionRequest result = new QuestionRequest(LessonsAndClasses.CLASSES.values()[(Arrays.asList(LessonsAndClasses.CLASS_NAMES)).indexOf(className)], lessonName,
                GlobalVariables.USER_NAME, tutorNameToRequest, -1, photoID, pageNo, appreciatedPrice, -1.0, DB.CONS_EMPTY,
                publisherName, bookName, lastDateOfDelivery, dueDateForAcceptance, GlobalVariables.QUESTION_REQUEST_STATE.PENDING_FOR_TUTOR_ACCEPTANCE, DB.CONS_EMPTY);
        return  result;
    }


    void InitializeDatePickers()
    {
        for(int i = 0; i < editTextsDate.length; i++)
        {
            final int iFinal = i;
            //Date Of Birth Picker
            editTextsDate[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus)
                    {
                        PopUpDateOfBirthSelectionDialog(iFinal);
                    }
                    else
                    {
                        booleansDate[iFinal] = false;
                    }
                }
            });

            editTextsDate[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(booleansDate[iFinal])
                    {
                        PopUpDateOfBirthSelectionDialog(iFinal);
                    }
                }
            });
        }
    }



    void PopUpDateOfBirthSelectionDialog(int i)
    {

        booleansDate[i] = true;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        final int iFinal = i;

        DatePickerDialog dialog = new DatePickerDialog(StudentPostQuestionActivity.this,
                android.R.style.Theme_Material_Light_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        editTextsDate[iFinal].setText(date);
                    }
                }, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }




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




    private void InstantiateComponents()
    {
        buttonTakePicture = (ImageView) findViewById(R.id.buttonTakePicture);
        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        buttonImportPicture = (ImageView) findViewById(R.id.buttonImportPicture);

        editTextPageNo = (EditText) findViewById(R.id.editTextPageNo);
        editTextAppreciatedPrice = (EditText) findViewById(R.id.editTextAppreciatedPrice);
        editTextDueDateForAcceptance = (EditText) findViewById(R.id.editTextDueDateForAcceptance);
        editTextLastDateOfDelivery = (EditText) findViewById(R.id.editTextLastDateOfDelivery);

        spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        spinnerLesson = (Spinner) findViewById(R.id.spinnerLesson);
        spinnerPublisher = (Spinner) findViewById(R.id.spinnerPublisher);
        spinnerBookName = (Spinner) findViewById(R.id.spinnerBookName);
        spinnerTutorToRequest = (Spinner) findViewById(R.id.spinnerTutorToRequest);

        linearLayoutPanelOtherPublisher = findViewById(R.id.linearLayoutPanelOtherPublisher);
        linearLayoutPanelOtherBookName = findViewById(R.id.linearLayoutPanelOtherBookName);

        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutButtonPostQuestionRequest = findViewById(R.id.linearLayoutButtonPostQuestionRequest);

        buttonPostQuestionRequest = findViewById(R.id.buttonPostQuestionRequest);
    }




    //Hide Keyboard
    public void HideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
}
