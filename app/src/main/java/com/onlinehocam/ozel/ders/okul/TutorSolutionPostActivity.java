package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class TutorSolutionPostActivity extends AppCompatActivity
{
    ImageView imageViewPhoto, buttonTakePicture, buttonImportPicture, imageViewYoutube;
    EditText editTextPageNo, editTextYoutubeLink;
    Spinner spinnerClass, spinnerLesson, spinnerPublisher, spinnerBookName;
    public Button buttonPostQuestionRequest;

    ArrayAdapter arrayAdapterClass, arrayAdapterLesson, arrayAdapterPublisher, arrayAdapterBookName, arrayAdapterTutorToRequest;
    EditText editTextOtherPublisher, editTextOtherBookName;
    LinearLayout linearLayoutPanelOtherPublisher, linearLayoutPanelOtherBookName;

    String className = "";
    String lessonName = "";
    String publisherName = "";
    String bookName = "";
    String youtubeLink = "";
    int pageNo = -1;

    public boolean isSolutionPostedSuccessfully = false;
    int photoID = -1;

    public LinearLayout linearLayoutMainProgressBar, linearLayoutButtonPostQuestionRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_solution_post);

        InstantiateComponents();

        FormatEditText();

        HideKeyboard();

        HandleSpinners();

        SetOnClickListeners();
    }

    private void FormatEditText()
    {
        editTextYoutubeLink.addTextChangedListener(new CustomYoutubeLinkFormattingTextWatcher(getApplicationContext(), editTextYoutubeLink));
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

    private void HandleSpinners()
    {
        HandleSpinnerClassAndLessons();
        HandleSpinnerPublisherAndBookName();
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

                    arrayAdapterBookName = new ArrayAdapter(TutorSolutionPostActivity.this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerPublisher,
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
                    publisherName = LessonsAndClasses.PUBLISHER_NAMES[position-1];
                    List<String> stringList = (List<String>) (GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[position-1]));
                    String[] stringArray = new String[stringList.size()];
                    (stringList).toArray(stringArray);
                    String[] stringArrayEnlarged = GetSpinnerStringArrayWithHeaderTitle(spinnerBookName, stringArray );
                    arrayAdapterBookName = new ArrayAdapter(TutorSolutionPostActivity.this, android.R.layout.simple_spinner_item, stringArrayEnlarged );
                    spinnerBookName.setAdapter(arrayAdapterBookName);

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
                    if(editTextOtherBookName == null)
                    {
                        linearLayoutPanelOtherBookName.setVisibility(View.VISIBLE);
                        editTextOtherBookName = findViewById(R.id.editTextOtherPublisher);
                    }
                }
                else
                {
                    bookName = GlobalVariables.lessonsAndClasses.mapBookNames.get(LessonsAndClasses.PUBLISHERS.values()[Arrays.asList(LessonsAndClasses.PUBLISHER_NAMES).indexOf(publisherName)]).get(position-1);

                    if(editTextOtherBookName != null)
                    {
                        linearLayoutPanelOtherBookName.setVisibility(View.GONE);
                        editTextOtherBookName = null;
                    }
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

                    arrayAdapterLesson = new ArrayAdapter(TutorSolutionPostActivity.this, android.R.layout.simple_spinner_item, GetSpinnerStringArrayWithHeaderTitle(spinnerClass,
                            new String[]{spinnerLesson.getPrompt().toString()} ));
                    spinnerLesson.setAdapter(arrayAdapterLesson);
                }
                else
                {
                    className = LessonsAndClasses.CLASS_NAMES[position-1];
                    //GlobalVariables.Log(getApplicationContext(), "GlobalVariables.lessonsAndClasses==null" + (GlobalVariables.lessonsAndClasses==null) );
                    List<String> stringList = (List<String>) (GlobalVariables.lessonsAndClasses.mapLessons.get(LessonsAndClasses.CLASSES.values()[position-1]));
                    String[] stringArray = new String[stringList.size()];
                    (stringList).toArray(stringArray);
                    String[] stringArrayEnlarged = GetSpinnerStringArrayWithHeaderTitle(spinnerLesson, stringArray );
                    arrayAdapterLesson = new ArrayAdapter(TutorSolutionPostActivity.this, android.R.layout.simple_spinner_item, stringArrayEnlarged );
                    spinnerLesson.setAdapter(arrayAdapterLesson);
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

    private void SetOnClickListeners()
    {
        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageViewPhoto.getTag().toString().equals(getString(R.string.constant_unknown)))
                {
                    new CustomToast(TutorSolutionPostActivity.this, getApplicationContext(), getString(R.string.post_question_please_upload_question_photo));
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
                    new CustomToast(TutorSolutionPostActivity.this, getApplicationContext(), getString(R.string.permission_check_camera_permission_is_needed));
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
                    new CustomToast(TutorSolutionPostActivity.this, getApplicationContext(), getString(R.string.permission_check_read_external_storage_permission_is_needed));
                }
            }
        });
        imageViewYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PastCopiedYoutubeLinkToEditText();
            }
        });
        buttonPostQuestionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSolutionPostedSuccessfully)
                {
                    CheckIfFieldsFilledAndExecutePostingQuestionRequestIfNeeded();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), TutorHomePageActivity.class));
                }
            }
        });
    }

    private void PastCopiedYoutubeLinkToEditText()
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null)
        {
            String copiedText = clipData.getItemAt(0).getText().toString();
            String youtubeLinkToBePasted = GetYoutubeVideoLinkLastPart(copiedText);
            if( !youtubeLinkToBePasted.isEmpty())
            {
                editTextYoutubeLink.setText(youtubeLinkToBePasted);
                new CustomToast(TutorSolutionPostActivity.this, getApplicationContext(), getString(R.string.post_solution_video_link_pasted));
            }
            else
            {
                GlobalVariables.AlertDialogDisplay(TutorSolutionPostActivity.this, getString(R.string.post_solution_invalid_video_link_on_clipboard));
            }
        }
        else
        {
            new CustomToast(TutorSolutionPostActivity.this, getApplicationContext(), getString(R.string.post_solution_no_text_on_clipboard_for_video_link));
        }
    }

    private String GetYoutubeVideoLinkLastPart(String copiedText)
    {
        String first32 = "https://www.youtube.com/watch?v=";
        copiedText = copiedText.trim();
        String result = "";
        if(copiedText.contains("v=") && (copiedText.split("v=").length > 1  && (copiedText.split("v=")[1]).length() == 11  ))
        {
            result = (copiedText.split("v=")[1]);
        }
        else if (copiedText.contains("=") && (copiedText.split("=").length > 1 && (copiedText.split("=")[1]).length() == 11 )  )
        {
            result = (copiedText.split("=")[1]);
        }
        else if (copiedText.length() == 11)
        {
            result = copiedText;
        }
        else if (copiedText.contains("youtu.be") && (copiedText.split(".be/").length > 1 ) && (copiedText.split(".be/")[1].length() == 11) )
        {
            result = copiedText.split(".be/")[1];
        }

        if(result.isEmpty())
        {
            return result;
        }
        return first32 + result;
    }

    private void CheckIfFieldsFilledAndExecutePostingQuestionRequestIfNeeded()
    {
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
        else if((youtubeLink = editTextYoutubeLink.getText().toString()).isEmpty())
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_solution_warning_fill_solution_video_youtube_link));
        }
        else
        {
            //GlobalVariables.Log(getApplicationContext(),"tutorSolutionPosting before youtubeLink: "+youtubeLink);
            if(youtubeLink.isEmpty())
            {
                youtubeLink = DB.CONS_EMPTY;
            }
            else
            {
                youtubeLink = youtubeLink.split("v=")[1];
            }
            //GlobalVariables.Log(getApplicationContext(),"tutorSolutionPosting after youtubeLink: "+youtubeLink);
            SendQuestionRequest();
        }
    }


    private void SendQuestionRequest()
    {
        QuestionRequest crQuestionRequest = CreateQuestionRequestBasedOnTheInfoInFields();
        //photoID = ServerHelper.PostQuestionRequest(crQuestionRequest, imageViewPhoto.getDrawable(), getApplicationContext());

        Bitmap imageBitmap = ((BitmapDrawable)(imageViewPhoto.getDrawable())).getBitmap();
        String imageDataAddressID = "" + GlobalVariables.USER_ID + (""+(crQuestionRequest.pageNo)).substring(0, Math.min(3, (""+(crQuestionRequest.pageNo)).length())) + UUID.randomUUID().toString();
        AsyncTaskHelper.UploadQuestionImageToDirectory(this, getApplicationContext(), crQuestionRequest, imageBitmap, imageDataAddressID, linearLayoutMainProgressBar
                , linearLayoutButtonPostQuestionRequest, true, false);

        /*
        //GlobalVariables.Log(getApplicationContext(), "on SendQuestionRequest on photoID: "+photoID);
        if(photoID != -1)
        {
            GlobalVariables.AlertDialogDisplay(TutorSolutionPostActivity.this, getString(R.string.post_question_response_succesful));
            isSolutionPostedSuccessfully = true;
            buttonPostQuestionRequest.setText(R.string.menu_main_manu);
        }
        else
        {
            new CustomToast(this, getApplicationContext(), getString(R.string.post_question_response_failed));
            isSolutionPostedSuccessfully = false;
        }*/
    }


    private QuestionRequest CreateQuestionRequestBasedOnTheInfoInFields()
    {
        QuestionRequest result = new QuestionRequest(LessonsAndClasses.CLASSES.values()[(Arrays.asList(LessonsAndClasses.CLASS_NAMES)).indexOf(className)], lessonName,
                "", GlobalVariables.USER_NAME,  -1, photoID, pageNo, -1.0, -1.0, youtubeLink,
                publisherName, bookName, "" /*CommonUtils.GetDateAs_YYYY_MM_DD()*/, "", GlobalVariables.QUESTION_REQUEST_STATE.DIRECTLY_POSTED_BY_TUTOR, DB.CONS_EMPTY);
        return  result;
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
                if(true)
                {
                    thePic = FlippedImage(thePic);
                }
                imageViewPhoto.setImageBitmap(thePic);
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
        imageViewYoutube = findViewById(R.id.imageViewYoutube);

        editTextPageNo = (EditText) findViewById(R.id.editTextPageNo);
        editTextYoutubeLink = (EditText) findViewById(R.id.editTextYoutubeLink);

        spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        spinnerLesson = (Spinner) findViewById(R.id.spinnerLesson);
        spinnerPublisher = (Spinner) findViewById(R.id.spinnerPublisher);
        spinnerBookName = (Spinner) findViewById(R.id.spinnerBookName);

        linearLayoutPanelOtherPublisher = findViewById(R.id.linearLayoutPanelOtherPublisher);
        linearLayoutPanelOtherBookName = findViewById(R.id.linearLayoutPanelOtherBookName);

        buttonPostQuestionRequest = findViewById(R.id.buttonPostQuestionRequest);

        linearLayoutMainProgressBar = findViewById(R.id.linearLayoutMainProgressBar);
        linearLayoutButtonPostQuestionRequest = findViewById(R.id.linearLayoutButtonPostQuestionRequest);
    }




    //Hide Keyboard
    public void HideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }
    
    
}
