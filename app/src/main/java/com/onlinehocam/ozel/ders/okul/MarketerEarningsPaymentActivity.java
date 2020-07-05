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

public class MarketerEarningsPaymentActivity extends AppCompatActivity {

    Activity activity;
    TextView textViewBalance, textViewTotalEarningsSoFar, textViewTotalPaymentsReceivedSoFar, textViewEarningsFromSubscriptions, textViewTotalEarningsFromPackagesConsumption;
    Button buttonPaymentRequestOpener, buttonSubmitPaymentRequest, buttonAbortPaymentRequest;
    LinearLayout linearLayoutPaymentRequestSubmitPanel;
    EditText editTextPaymentRequestAmount;

    Boolean hasIBANNo;
    CustomIBANNoFormattingTextWatcher customIBANNoFormattingTextWatcher;

    double balance, totalPaymentsReceivedSoFar, earningsFromSubscriptions, totalEarningsFromPackagesConsumption, totalEarningsSoFar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketer_earnings_payment);

        activity = this;

        InitializeComponents();

        SetOnClickListeners();

        RetrieveDataAndSet();
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

    private void RetrieveDataAndSet()
    {
        totalEarningsSoFar = -1.0;

        totalPaymentsReceivedSoFar = ServerHelper.GetTotalPaymentsReceivedSoFar();
        earningsFromSubscriptions = ServerHelper.GetEarningsFromSubscriptions();
        totalEarningsFromPackagesConsumption = ServerHelper.GetEarningsFromPackagesConsumption();

        if(totalPaymentsReceivedSoFar == -1.0)
        {
            textViewTotalPaymentsReceivedSoFar.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewTotalPaymentsReceivedSoFar.setText(GetArrangedStringOfTheDoubleValue(totalPaymentsReceivedSoFar));
        }

        if(earningsFromSubscriptions == -1.0)
        {
            textViewEarningsFromSubscriptions.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewEarningsFromSubscriptions.setText(GetArrangedStringOfTheDoubleValue(earningsFromSubscriptions));
        }

        if(totalEarningsFromPackagesConsumption == -1.0)
        {
            textViewTotalEarningsFromPackagesConsumption.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }
        else
        {
            textViewTotalEarningsFromPackagesConsumption.setText(GetArrangedStringOfTheDoubleValue(totalEarningsFromPackagesConsumption));
        }


        if(earningsFromSubscriptions != -1.0 && totalEarningsFromPackagesConsumption != -1.0)
        {
            totalEarningsSoFar = earningsFromSubscriptions + totalEarningsFromPackagesConsumption;
            textViewTotalEarningsSoFar.setText(GetArrangedStringOfTheDoubleValue(totalEarningsSoFar));
        }
        else
        {
            textViewTotalEarningsSoFar.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }


        if(totalPaymentsReceivedSoFar != -1.0 && totalEarningsSoFar != -1.0)
        {
            balance = totalEarningsSoFar - totalPaymentsReceivedSoFar;
            balance = ((double)(Math.round(balance*100)) / 100);
            textViewBalance.setText(GetArrangedStringOfTheDoubleValue(balance));
        }
        else
        {
            textViewBalance.setText(DB.CONS_CANNOT_BE_DISPLAYED_NOW_TR);
        }

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
                    hasIBANNo = ServerHelper.isMarketerHasAnyIBANNo(GlobalVariables.USER_ID);
                }
                if(hasIBANNo == true)
                {
                    SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.TRUE_CHIP);
                    ExpandPaymentRequestPanel();
                }
                else
                {
                    SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                    ExpandIBANNoEntryPanel();
                }
            }
        });
        buttonAbortPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
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
                        GlobalVariables.Log(getApplicationContext(), "requestedAmount: "+requestedAmount);
                    }
                    catch (Exception ex)
                    {
                        SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_invalid_amount));
                        return;
                    }


                    if(totalPaymentsReceivedSoFar == -1.0 || totalEarningsSoFar == -1.0)
                    {
                        SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_some_data_not_available_now));
                        return;
                    }

                    if(balance <= 0.0)
                    {
                        SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_invalid_amount));
                        return;
                    }


                    if(requestedAmount > balance)
                    {
                        SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_amount_extends_your_balance));
                        return;
                    }



                    boolean responseOfIsTutorHasNoActivePaymentRequest = !ServerHelper.isTutorHasAnyActivePaymentRequest(GlobalVariables.USER_ID);
                    if(responseOfIsTutorHasNoActivePaymentRequest)
                    {
                        if(ServerHelper.SendPaymentRequest(GlobalVariables.USER_ID, requestedAmount))
                        {
                            SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.TRUE_CHIP);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_send_succesful));
                            CollapsePaymentRequestPanel();
                        }
                        else
                        {
                            SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.CONNECTION_FAILED);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_send_failed));
                        }
                    }
                    else
                    {
                        SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.FALSE_BELL);
                        new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_payment_request_already_sent));
                        CollapsePaymentRequestPanel();
                    }
                }
                else    // Send IBAN NO
                {
                    if(editTextPaymentRequestAmount.getText().toString().length() == 29)
                    {
                        //GlobalVariables.Log(getApplicationContext(), "USER_ID: "+GlobalVariables.USER_ID + " IBAN_NO: "+ editTextPaymentRequestAmount.getText().toString() );
                        if(ServerHelper.SubmitMarketerIBANNo(GlobalVariables.USER_ID, editTextPaymentRequestAmount.getText().toString()))
                        {
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_iban_no_send_succesful));
                            CollapsePaymentRequestPanel();
                            hasIBANNo = true;
                        }
                        else
                        {
                            SoundHelper.PlayMediaPlayerSound(MarketerEarningsPaymentActivity.this, SoundHelper.SOUNDS.CONNECTION_FAILED);
                            new CustomToast(activity, getApplicationContext(), getString(R.string.earnings_iban_no_send_failed));
                        }
                    }
                    else
                    {
                        String warningMessage = getString(R.string.earnings_iban_no_not_adequate);
                        new CustomToast(MarketerEarningsPaymentActivity.this, getApplicationContext(), warningMessage);
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
        textViewEarningsFromSubscriptions = findViewById(R.id.textViewEarningsFromSubscriptions);
        textViewTotalEarningsFromPackagesConsumption = findViewById(R.id.textViewTotalEarningsFromPackagesConsumption);

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
