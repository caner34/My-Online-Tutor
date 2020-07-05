package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class TutorEarningsPaymentActivity extends AppCompatActivity {

    Activity activity;
    TextView textViewBalance, textViewTotalEarningsSoFar, textViewTotalPaymentsReceivedSoFar, textViewEarningsFromQuestionRequests, textViewTotalEarningsFromSolutionDisplays;
    Button buttonPaymentRequestOpener, buttonSubmitPaymentRequest, buttonAbortPaymentRequest;
    LinearLayout linearLayoutPaymentRequestSubmitPanel;
    EditText editTextPaymentRequestAmount;

    Boolean hasIBANNo;
    CustomIBANNoFormattingTextWatcher customIBANNoFormattingTextWatcher;

    double balance, totalPaymentsReceivedSoFar, earningsFromQuestionRequests, totalEarningsFromSolutionDisplays, totalEarningsSoFar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_earnings_payment);

        activity = this;

        InitializeComponents();

        SetOnClickListeners();

        RetrieveDataAndSet();
    }

    private void RetrieveDataAndSet()
    {
        totalEarningsSoFar = -1.0;

        totalPaymentsReceivedSoFar = ServerHelper.GetTotalPaymentsReceivedSoFar();
        earningsFromQuestionRequests = ServerHelper.GetEarningsFromQuestionRequests();
        totalEarningsFromSolutionDisplays = ServerHelper.GetTotalEarningsFromSolutionDisplays(getApplicationContext());

        if(totalPaymentsReceivedSoFar == -1.0)
        {
            textViewTotalPaymentsReceivedSoFar.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewTotalPaymentsReceivedSoFar.setText(GetArrangedStringOfTheDoubleValue(totalPaymentsReceivedSoFar));
        }

        if(earningsFromQuestionRequests == -1.0)
        {
            textViewEarningsFromQuestionRequests.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewEarningsFromQuestionRequests.setText(GetArrangedStringOfTheDoubleValue(earningsFromQuestionRequests));
        }

        if(totalEarningsFromSolutionDisplays == -1.0)
        {
            textViewTotalEarningsFromSolutionDisplays.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewTotalEarningsFromSolutionDisplays.setText(GetArrangedStringOfTheDoubleValue(totalEarningsFromSolutionDisplays));
        }


        if(earningsFromQuestionRequests != -1.0 && totalEarningsFromSolutionDisplays != -1.0)
        {
            totalEarningsSoFar = earningsFromQuestionRequests + totalEarningsFromSolutionDisplays;
            textViewTotalEarningsSoFar.setText(GetArrangedStringOfTheDoubleValue(totalEarningsSoFar));
        }
        else
        {
            textViewTotalEarningsSoFar.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }


        if(totalPaymentsReceivedSoFar != -1.0 && totalEarningsSoFar != -1.0)
        {
            balance = totalEarningsSoFar - totalPaymentsReceivedSoFar;
            textViewBalance.setText(GetArrangedStringOfTheDoubleValue(balance));
        }
        else
        {
            textViewBalance.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }

    }


    String GetArrangedStringOfTheDoubleValue(double value)
    {
        GlobalVariables.Log(getApplicationContext(), "value: "+value);
        double roundedValue = ((double)Math.round((value * (double) 100))) / (double) 100;
        String stringRoundedValue = "" + roundedValue;
        GlobalVariables.Log(getApplicationContext(), "stringRoundedValue: "+stringRoundedValue);
        GlobalVariables.Log(getApplicationContext(), "stringRoundedValue Array: "+ Arrays.toString((stringRoundedValue.toCharArray())));

        if(!stringRoundedValue.contains("."))
        {
            stringRoundedValue += ".00";
        }
        else if((roundedValue-((double) ((int)roundedValue)) == 0.0))
        {
            stringRoundedValue += "0";
        }
        return stringRoundedValue;
    }

    private void FormatTextField(EditText editText, boolean isIBANNOEditText)
    {
        if(isIBANNOEditText)
        {
            if(!editText.getTag().equals("hasListener"))
            {
                editText.setText("");
                editText.addTextChangedListener(customIBANNoFormattingTextWatcher);
                editText.setTag("hasListener");
            }
        }
        else
        {
            if(editText.getTag().equals("hasListener"))
            {
                editText.removeTextChangedListener(customIBANNoFormattingTextWatcher);
                editText.setTag("hasNOListener");
                editText.setText("");
            }
        }
    }

    private void SetOnClickListeners()
    {
        buttonPaymentRequestOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasIBANNo == null)
                {
                    //GlobalVariables.Log(getApplicationContext(), "GlobalVariables.USER_ID: "+GlobalVariables.USER_ID);
                    hasIBANNo = ServerHelper.isTutorHasAnyIBANNo(GlobalVariables.USER_ID);
                }
                if(hasIBANNo == true)
                {
                    SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.TRUE_CHIP);
                    ExpandPaymentRequestPanel();
                }
                else
                {
                    SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                    ExpandIBANNoEntryPanel();
                }
            }
        });
        buttonAbortPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                CollapsePaymentRequestPanel();
            }
        });
        buttonSubmitPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasIBANNo)   // Send Payment Request
                {
                    double requestedAmount = -1;
                    try
                    {
                        requestedAmount = Double.parseDouble (editTextPaymentRequestAmount.getText().toString().replace(",", "."));
                    }
                    catch (Exception ex)
                    {
                        SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_invalid_amount));
                        return;
                    }

                    if(totalPaymentsReceivedSoFar == -1.0 || totalEarningsSoFar == -1.0)
                    {
                        SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_some_data_not_available_now));
                        return;
                    }

                    if(balance <= 0.0)
                    {
                        SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_invalid_amount));
                        return;
                    }


                    if(requestedAmount > balance)
                    {
                        SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_amount_extends_your_balance));
                        return;
                    }


                    boolean responseOfIsTutorHasNoActivePaymentRequest = !ServerHelper.isTutorHasAnyActivePaymentRequest(GlobalVariables.USER_ID);
                    if(responseOfIsTutorHasNoActivePaymentRequest)
                    {
                        if(ServerHelper.SendPaymentRequest(GlobalVariables.USER_ID, requestedAmount))
                        {
                            SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.TRUE_CHIP);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_send_succesful));
                            CollapsePaymentRequestPanel();
                        }
                        else
                        {
                            SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.CONNECTION_FAILED);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_send_failed));
                        }
                    }
                    else
                    {
                        SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_already_sent));
                        CollapsePaymentRequestPanel();
                    }
                }
                else    // Send IBAN NO
                {
                    if(editTextPaymentRequestAmount.getText().toString().length() == 29)
                    {
                        //GlobalVariables.Log(getApplicationContext(), "USER_ID: "+GlobalVariables.USER_ID + " IBAN_NO: "+ editTextPaymentRequestAmount.getText().toString() );
                        if(ServerHelper.SubmitTutorIBANNo(GlobalVariables.USER_ID, editTextPaymentRequestAmount.getText().toString()))
                        {
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_iban_no_send_succesful));
                            CollapsePaymentRequestPanel();
                            hasIBANNo = true;
                        }
                        else
                        {
                            SoundHelper.PlayMediaPlayerSound(TutorEarningsPaymentActivity.this, SoundHelper.SOUNDS.CONNECTION_FAILED);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_iban_no_send_failed));
                        }
                    }
                    else
                    {
                        String warningMessage = getString(R.string.earnings_iban_no_not_adequate);
                        new CustomToast(TutorEarningsPaymentActivity.this, getApplicationContext(), warningMessage);
                    }
                }
            }
        });
    }

    private void ExpandIBANNoEntryPanel()
    {
        if(buttonPaymentRequestOpener.getVisibility() != View.GONE)
        {
            buttonPaymentRequestOpener.setVisibility(View.GONE);
            linearLayoutPaymentRequestSubmitPanel.setVisibility(View.VISIBLE);
            buttonSubmitPaymentRequest.setText(R.string.earnings_send_iban_no);
            editTextPaymentRequestAmount.setHint(R.string.earnings_enter_the_iban_no);
            FormatTextField(editTextPaymentRequestAmount, true);
        }
    }

    private void ExpandPaymentRequestPanel()
    {
        if(buttonPaymentRequestOpener.getVisibility() != View.GONE)
        {
            buttonPaymentRequestOpener.setVisibility(View.GONE);
            linearLayoutPaymentRequestSubmitPanel.setVisibility(View.VISIBLE);
            buttonSubmitPaymentRequest.setText(R.string.earnings_send_payment_request);
            editTextPaymentRequestAmount.setHint(R.string.earnings_enter_the_requested_amount);
            FormatTextField(editTextPaymentRequestAmount, false);
        }
    }

    private void CollapsePaymentRequestPanel()
    {
        if(buttonPaymentRequestOpener.getVisibility() == View.GONE)
        {
            buttonPaymentRequestOpener.setVisibility(View.VISIBLE);
            linearLayoutPaymentRequestSubmitPanel.setVisibility(View.GONE);
        }
    }

    private void InitializeComponents()
    {
        textViewBalance = findViewById(R.id.textViewBalance);
        textViewTotalEarningsSoFar = findViewById(R.id.textViewTotalEarningsSoFar);
        textViewTotalPaymentsReceivedSoFar = findViewById(R.id.textViewTotalPaymentsReceivedSoFar);
        textViewEarningsFromQuestionRequests = findViewById(R.id.textViewEarningsFromQuestionRequests);
        textViewTotalEarningsFromSolutionDisplays = findViewById(R.id.textViewTotalEarningsFromSolutionDisplays);

        buttonPaymentRequestOpener = findViewById(R.id.buttonPaymentRequestOpener);
        buttonSubmitPaymentRequest = findViewById(R.id.buttonSubmitPaymentRequest);
        buttonAbortPaymentRequest = findViewById(R.id.buttonAbortPaymentRequest);

        linearLayoutPaymentRequestSubmitPanel = findViewById(R.id.linearLayoutPaymentRequestSubmitPanel);

        editTextPaymentRequestAmount = findViewById(R.id.editTextPaymentRequestAmount);

        hasIBANNo = null;
        editTextPaymentRequestAmount.setTag("hasNOListener");
        customIBANNoFormattingTextWatcher = new CustomIBANNoFormattingTextWatcher(getApplicationContext(), editTextPaymentRequestAmount);
    }
}
