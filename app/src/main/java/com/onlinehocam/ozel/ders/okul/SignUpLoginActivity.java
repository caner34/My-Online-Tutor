package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.AsyncTasks.AsyncTaskHelper;

public class SignUpLoginActivity extends AppCompatActivity {

    TextView textViewTitle, textViewUpperMessage, textViewLowerMessage, textViewAlreadyHaveAnAccount, textViewIForgetMyPassword, textViewCreateANewAccount;
    Button buttonSignUpLogin;
    EditText editTextUserName, editTextPassword;
    CheckBox checkBoxAutoSignIn;
    boolean isAlreadyHaveAnAccount;

    static final String RESPONSE_PASSWORD_ADEQUATE = "password_adequate";
    static final String SYMBOLIC_USER_NAME_ALREADY_HAVE_AN_ACCOUNT = "AlreadyHaveAnAccount";

    boolean isSignUp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_login);


        isAlreadyHaveAnAccount = getIntent().getBooleanExtra("isAlreadyHaveAnAccount" ,false);
        if(isAlreadyHaveAnAccount)
        {
            isSignUp = false;
        }

        if(GlobalVariables.IS_AUTO_SIGN_IN)
        {
            ExecuteLoginGranted(false);
        }
        else
        {
            if(GlobalVariables.USER_NAME.equals(SYMBOLIC_USER_NAME_ALREADY_HAVE_AN_ACCOUNT))
            {
                isSignUp = true;
            }
            else
            {
                isSignUp = GlobalVariables.myDb.RetrieveGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.db).isEmpty();
            }
            if(isAlreadyHaveAnAccount)
            {
                isSignUp = false;
            }

            InstantiateComponents();

            SetOnClickListeners();
        }

    }

    private void SetOnClickListeners()
    {
        buttonSignUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(SignUpLoginActivity.this, SoundHelper.SOUNDS.CLICK1);
                if(isSignUp)
                {
                    ExecuteSignUpButtonClickedAction();
                }
                else
                {
                    ExecuteLogInButtonClickedAction();
                }
            }
        });

        if(isSignUp)
        {
            textViewIForgetMyPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            textViewAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.USER_NAME = SYMBOLIC_USER_NAME_ALREADY_HAVE_AN_ACCOUNT;
                    Intent i = new Intent(getApplicationContext(), SignUpLoginActivity.class);
                    i.putExtra("isAlreadyHaveAnAccount", true);
                    startActivity(i);
                }
            });
        }
        else
        {
            textViewCreateANewAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalVariables.USER_NAME = SYMBOLIC_USER_NAME_ALREADY_HAVE_AN_ACCOUNT;
                    Intent i = new Intent(getApplicationContext(), ForkForStudentsTutorsActivity.class);
                    i.putExtra("isToCreateANewAccount", true);
                    startActivity(i);
                }
            });
        }
    }

    private void ExecuteSignUpButtonClickedAction()
    {
        if(ServerHelper.isSignUpUserNameAlreadyInUse(editTextUserName.getText().toString()))
        {
            textViewLowerMessage.setText(R.string.user_username_in_use);
            return;
        }

        String passwordSuitabilityResponse = GetPasswordSuitabilityResponse();
        if(!passwordSuitabilityResponse.equals(RESPONSE_PASSWORD_ADEQUATE))
        {
            textViewLowerMessage.setText(passwordSuitabilityResponse);
            editTextPassword.setText("");
        }
        else
        {
            textViewLowerMessage.setText("");
            textViewUpperMessage.setText(getString(R.string.sign_up_account_is_being_created));

            ExecuteSignUpAllowed();
        }
    }



    void TryUpdatingReferrerIDForUser()
    {
        if(!GlobalVariables.USER_INSTALL_REFERRAL_CAMPAIGN_NAME.isEmpty())
        {
            AsyncTaskHelper.TryUpdatingReferrerIdForUser(SignUpLoginActivity.this, getApplicationContext(), GlobalVariables.USER_INSTALL_REFERRAL_CAMPAIGN_NAME, GlobalVariables.USER_ID);
        }
    }

    private void ExecuteSignUpAllowed()
    {
        if(!ServerHelper.RecordUserNameAndPasswordOnServer(editTextUserName.getText().toString(), editTextPassword.getText().toString(), GlobalVariables.USER_PASSWORD_TRIALS, "", ""+GlobalVariables.GetUserTypeIDByUserStatusString(GlobalVariables.USER_STATUS)))
        {
            textViewUpperMessage.setText("");
            textViewLowerMessage.setText(R.string.user_something_went_wrong);
            return;
        }
        GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, editTextUserName.getText().toString(), GlobalVariables.db);
        GlobalVariables.USER_NAME = editTextUserName.getText().toString();
        GlobalVariables.USER_ID = ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME);
        TryUpdatingReferrerIDForUser();
        GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_IS_TO_FIRST_LOGIN, "false", GlobalVariables.db);


        if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_TUTOR))
        {
            startActivity(new Intent(getApplicationContext(), TutorRegisterActivity.class));
        }
        else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT))
        {
            startActivity(new Intent(getApplicationContext(), StudentRegisterActivity.class));
        }
        else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_MARKETER))
        {
            startActivity(new Intent(getApplicationContext(), MarketerRegisterActivity.class));
        }
    }

    private void ExecuteLogInButtonClickedAction()
    {
        if(!ServerHelper.isSignUpUserNameAlreadyInUse(editTextUserName.getText().toString()))
        {
            textViewLowerMessage.setText(R.string.user_wrong_username);
            return;
        }

        if(!isPasswordCorrectForTheGivenUserName())
        {
            textViewLowerMessage.setText(R.string.user_wrong_password);
            return;
        }

        ExecuteLoginGranted(true);
    }

    private boolean isPasswordCorrectForTheGivenUserName()
    {
        return ServerHelper.isUserNameAndPasswordMatches(editTextUserName.getText().toString(), editTextPassword.getText().toString());
    }

    private String GetPasswordSuitabilityResponse()
    {
        try
        {
            String passwordSuitabilityResponse = getString(R.string.user_password_not_suitable);
            String crPassword = editTextPassword.getText().toString();

            if(GlobalVariables.USER_PASSWORD_TRIALS.equals(""))
            {
                GlobalVariables.USER_PASSWORD_TRIALS += crPassword;
            }
            else
            {
                GlobalVariables.USER_PASSWORD_TRIALS += ";" + crPassword;
            }

            PasswordLevelCheck pc = new PasswordLevelCheck();

            if(pc.isPasswordContainsSemiColon(crPassword))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_cant_contain_semicolon);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isContainsNumber(crPassword))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_must_include_numbers);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isContainsLetter(crPassword))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_must_include_letters);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isContainsBothLowerAndUpperCaseLetters(crPassword))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_must_include_both_letter_cases);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isContainsSymbol(crPassword))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_must_include_symbols);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isNumberOfDifferentCharsSufficient(crPassword, 5))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_must_include_unique_different_chars);
                return passwordSuitabilityResponse;
            }
            else if (!pc.isPasswordLengthSufficient(crPassword, 11))
            {
                passwordSuitabilityResponse = getString(R.string.sign_up_password_is_short);
                return passwordSuitabilityResponse;
            }
            else
            {
                return RESPONSE_PASSWORD_ADEQUATE;
            }
        }
        catch (Exception ex)
        {
            return getString(R.string.user_something_went_wrong);
        }
    }

    private class PasswordLevelCheck
    {

        boolean isPasswordContainsSemiColon(String password)
        {
            return password.contains(";");
        }

        boolean isContainsNumber(String password)
        {
            for (int i =0; i < password.length(); i++)
            {
                if( (""+password.charAt(i)).matches("[0-9]{1}") )
                {
                    return true;
                }
            }
            return false;
        }

        boolean isContainsLetter(String password)
        {
            for (int i =0; i < password.length(); i++)
            {
                if( (""+password.charAt(i)).matches("[a-zA-Z]{1}") )
                {
                    return true;
                }
            }
            return false;
        }

        boolean isContainsBothLowerAndUpperCaseLetters(String password)
        {
            boolean isContainsLowerCase = false;
            boolean isContainsUpperCase = false;

            for (int i =0; i < password.length(); i++)
            {
                if( !isContainsLowerCase && (""+password.charAt(i)).matches("[a-z]{1}") )
                {
                    isContainsLowerCase = true;
                }
                if( !isContainsUpperCase && (""+password.charAt(i)).matches("[A-Z]{1}") )
                {
                    isContainsUpperCase = true;
                }
                if(isContainsLowerCase && isContainsUpperCase)
                {
                    return true;
                }
            }
            return false;
        }

        boolean isContainsSymbol(String password)
        {
            for (int i =0; i < password.length(); i++)
            {
                if(  !(""+password.charAt(i)).matches("[a-zA-Z_0-9]{1}") )
                {
                    return true;
                }
            }
            return false;
        }

        boolean isNumberOfDifferentCharsSufficient(String password, int minUniqueCharsNeeded)
        {
            String uniqueCharSeq = "";
            for(int i = 0; i < password.length(); i++)
            {
                if(!uniqueCharSeq.contains(""+password.charAt(i)))
                {
                    uniqueCharSeq += password.charAt(i);
                }
            }
            return uniqueCharSeq.length() >= minUniqueCharsNeeded;
        }

        boolean isPasswordLengthSufficient(String password, int minLengthNeeded)
        {
            return password.length() >= minLengthNeeded;
        }


    }

    private void ExecuteLoginGranted(boolean isToCheckLoginCheckBox)
    {
        //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 0 USER_ID: "+GlobalVariables.USER_ID);

        if(isToCheckLoginCheckBox)
        {
            if(GlobalVariables.IS_AUTO_SIGN_IN != checkBoxAutoSignIn.isChecked())
            {
                GlobalVariables.IS_AUTO_SIGN_IN = checkBoxAutoSignIn.isChecked();
                int autoSıgnInValue = 0;
                if(GlobalVariables.IS_AUTO_SIGN_IN)
                {
                    autoSıgnInValue = 1;
                }

                GlobalVariables.myDb.UpdateDataTableGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_AUTO_SIGN_IN, autoSıgnInValue, GlobalVariables.db);
            }


            //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 1 USER_ID: "+GlobalVariables.USER_ID);

            if(GlobalVariables.USER_NAME == SYMBOLIC_USER_NAME_ALREADY_HAVE_AN_ACCOUNT)
            {
                //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 1b 1 USER_ID: "+GlobalVariables.USER_ID);
                GlobalVariables.USER_NAME = editTextUserName.getText().toString();
                //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 1b 2 USER_Name: "+GlobalVariables.USER_NAME);
                //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 1b 2b USER_ID SERVER RESPONSE: "+  (ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME))  );
                GlobalVariables.USER_ID = ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME);
                //GlobalVariables.Log(getApplicationContext(), "  before SET 1 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
                GlobalVariables.USER_STATUS = ServerHelper.GetUserStatusByUserID(GlobalVariables.USER_ID);
                GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, editTextUserName.getText().toString(), GlobalVariables.db);
                //GlobalVariables.Log(getApplicationContext(), "  before SET 1 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
                GlobalVariables.myDb.UpdateDataTableGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_ID, GlobalVariables.USER_ID, GlobalVariables.db);
                //GlobalVariables.Log(getApplicationContext(), "  after SET 1 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
                GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_STATUS, GlobalVariables.USER_STATUS, GlobalVariables.db);
                //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 1b 3 USER_ID: "+GlobalVariables.USER_ID);
            }
            else
            {
                GlobalVariables.USER_NAME = editTextUserName.getText().toString();
                GlobalVariables.USER_ID = ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME);
                GlobalVariables.USER_STATUS = ServerHelper.GetUserStatusByUserID(GlobalVariables.USER_ID);
                if(!GlobalVariables.USER_NAME.equals( GlobalVariables.myDb.RetrieveGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.db) ))
                {
                    GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.USER_NAME, GlobalVariables.db);
                    GlobalVariables.myDb.UpdateDataTableGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_ID, GlobalVariables.USER_ID, GlobalVariables.db);
                    GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_STATUS, GlobalVariables.USER_STATUS, GlobalVariables.db);
                }
            }

            //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 2 USER_ID: "+GlobalVariables.USER_ID);

            //GlobalVariables.Log(getApplicationContext(), "GlobalVariables.USER_ID: "+GlobalVariables.USER_ID);
        }
        else
        {
            //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 2b USER_ID: "+GlobalVariables.USER_ID);
            SetUserNameIDAndStatusIntoSQLiteDB();
        }


        //GlobalVariables.Log(getApplicationContext(), "  ExecuteLoginGranted Step 3 USER_ID: "+GlobalVariables.USER_ID);

        if(!GlobalVariables.USER_NAME.equals(editTextUserName.getText().toString()))
        {
            GlobalVariables.USER_NAME = editTextUserName.getText().toString();
            GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.USER_NAME, GlobalVariables.db);
            GlobalVariables.USER_ID = ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME);
            //GlobalVariables.Log(getApplicationContext(), "  before SET 2 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
            GlobalVariables.myDb.UpdateDataTableGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_ID, GlobalVariables.USER_ID, GlobalVariables.db);
            //GlobalVariables.Log(getApplicationContext(), "  after SET 2 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
            //GlobalVariables.Log(getApplicationContext(), "updated USER_ID: " + GlobalVariables.USER_ID + "   updated USER_NAME: "+GlobalVariables.USER_NAME);
        }

        if(isAlreadyHaveAnAccount)
        {
            boolean isToFirstLogin = !GlobalVariables.myDb.RetrieveGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_IS_TO_FIRST_LOGIN, GlobalVariables.db).equals("false");
            if(isToFirstLogin)
            {
                GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_IS_TO_FIRST_LOGIN, "false", GlobalVariables.db);
            }
        }

        if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_TUTOR))
        {
            if(!ServerHelper.isUserRegistered(GlobalVariables.USER_ID, GlobalVariables.GetUserTypeIDByUserStatusString(GlobalVariables.KEY_USER_STATUS_TUTOR)))
            {
                startActivity(new Intent(getApplicationContext(), TutorRegisterActivity.class));
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), TutorHomePageActivity.class));
            }
        }
        else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_STUDENT))
        {
            if(!ServerHelper.isUserRegistered(GlobalVariables.USER_ID, GlobalVariables.GetUserTypeIDByUserStatusString(GlobalVariables.KEY_USER_STATUS_STUDENT)))
            {
                startActivity(new Intent(getApplicationContext(), StudentRegisterActivity.class));
            }
            else
            {
                startActivity(new Intent(getApplicationContext(), StudentHomePageActivity.class));
            }
        }
        else if(GlobalVariables.USER_STATUS.equals(GlobalVariables.KEY_USER_STATUS_MARKETER))
        {
            if(!ServerHelper.isUserRegistered(GlobalVariables.USER_ID, GlobalVariables.GetUserTypeIDByUserStatusString(GlobalVariables.KEY_USER_STATUS_MARKETER)))
            {
                startActivity(new Intent(getApplicationContext(), MarketerRegisterActivity.class));
            }
            else
            {
                    startActivity(new Intent(getApplicationContext(), MarketerHomePageActivity.class));
            }
        }
    }


    private void SetUserNameIDAndStatusIntoSQLiteDB()
    {
        String userNameOnSqliteDB = GlobalVariables.myDb.RetrieveGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.db);
        if(userNameOnSqliteDB.equals(editTextUserName.getText().toString()))
        {
            return;
        }
        GlobalVariables.USER_NAME = editTextUserName.getText().toString();
        GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_NAME, GlobalVariables.USER_NAME, GlobalVariables.db);

        GlobalVariables.USER_ID = ServerHelper.GetUserIDByUserName(GlobalVariables.USER_NAME);

        //GlobalVariables.Log(getApplicationContext(), "  before SET 3 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);
        GlobalVariables.myDb.UpdateDataTableGlobalIntegerVariables(DatabaseHelper.TableGlobalIntegerVariables.KEY_USER_ID, GlobalVariables.USER_ID, GlobalVariables.db);
        //GlobalVariables.Log(getApplicationContext(), "  after SET 3 toSqliteDB USER_ID: "+GlobalVariables.USER_ID);


        GlobalVariables.USER_STATUS = ServerHelper.GetUserStatusByUserID(GlobalVariables.USER_ID);
        GlobalVariables.myDb.UpdateDataTableGlobalVariables(DatabaseHelper.TableGlobalVariables.KEY_USER_STATUS, GlobalVariables.USER_STATUS, GlobalVariables.db);
    }


    private void InstantiateComponents()
    {
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewUpperMessage = findViewById(R.id.textViewUpperMessage);
        textViewLowerMessage = findViewById(R.id.textViewLowerMessage);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonSignUpLogin = findViewById(R.id.buttonSignUpLogin);

        if (!isSignUp)
        {
            //Rename title and button
            textViewTitle.setText(R.string.user_login_title);
            buttonSignUpLogin.setText(R.string.user_login_title);

            //Create And Insert checkBoxAutoSignIn
            checkBoxAutoSignIn = new CheckBox(SignUpLoginActivity.this);
            checkBoxAutoSignIn.setText(getString(R.string.user_auto_sign_in));
            checkBoxAutoSignIn.setChecked(GlobalVariables.IS_AUTO_SIGN_IN);
            LinearLayout linearLayoutCheckBoxAutoSignIn = findViewById(R.id.linearLayoutCheckBoxAutoSignIn);
            linearLayoutCheckBoxAutoSignIn.addView(checkBoxAutoSignIn);

            LinearLayout linearLayoutOtherOptionsOnLogin = findViewById(R.id.linearLayoutOtherOptionsOnLogin);
            linearLayoutOtherOptionsOnLogin.setVisibility(LinearLayout.VISIBLE);

            textViewCreateANewAccount = findViewById(R.id.textViewCreateANewAccount);
        }
        else
        {
            LinearLayout linearLayoutSignUpProblems = findViewById(R.id.linearLayoutSignUpProblems);
            linearLayoutSignUpProblems.setVisibility(LinearLayout.VISIBLE);

            textViewAlreadyHaveAnAccount = findViewById(R.id.textViewAlreadyHaveAnAccount);
            textViewIForgetMyPassword = findViewById(R.id.textViewIForgetMyPassword);
        }
    }


}
