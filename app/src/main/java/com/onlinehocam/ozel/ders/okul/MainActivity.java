package com.onlinehocam.ozel.ders.okul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.android.gms.measurement.AppMeasurementInstallReferrerReceiver;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private final Executor backgroundExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInstallReferrer();

        DirectlyGoToForkActivity();
    }

    private void DirectlyGoToForkActivity()
    {
        Intent i = new Intent(getApplicationContext(), ForkForStudentsTutorsActivity.class);
        startActivity(i);
    }


    // TODO: Change this to use whatever preferences are appropriate. The install referrer should
    // only be sent to the receiver once.
    private final String prefKey = "checkedInstallReferrer";

    void checkInstallReferrer() {
        if (getPreferences(MODE_PRIVATE).getBoolean(prefKey, false)) {
            return;
        }

        final InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this).build();
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.getInstallReferrerFromClient(referrerClient);
            }
        });
    }

    void getInstallReferrerFromClient(final InstallReferrerClient referrerClient) {

        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;
                        try
                        {
                            response = referrerClient.getInstallReferrer();
                        }
                        catch (RemoteException e)
                        {
                            e.printStackTrace();
                            return;
                        }
                        final String referrerUrl = response.getInstallReferrer();

                        String campaignName = GetReferralLinkPart(referrerUrl, "campaign");

                        GlobalVariables.USER_INSTALL_REFERRAL_CAMPAIGN_NAME = campaignName;

                        // TODO: If you're using GTM, call trackInstallReferrerForGTM instead.
                        trackInstallReferrerForGTM(referrerUrl);


                        // Only check this once.
                        getPreferences(MODE_PRIVATE).edit().putBoolean(prefKey, true).commit();

                        // End the connection
                        referrerClient.endConnection();

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {

            }
        });
    }

    private String GetReferralLinkPart(String referrerUrl, String utmTypeString)
    {
        String referralPartOfTheLink = "";
        if(referrerUrl.contains("&referrer="))
        {
            referralPartOfTheLink = referrerUrl.split("&referrer=")[1];
        }
        else
        {
            referralPartOfTheLink = referrerUrl;
        }
        String[] rawUtmParts = referralPartOfTheLink.split("utm_");

        String result = "";
        for(int i = 0; i < rawUtmParts.length; i++)
        {
            if(rawUtmParts[i].contains(utmTypeString))
            {
                if((rawUtmParts[i]).contains("%3D"))
                {
                    result = ((rawUtmParts[i]).split("%3D"))[1];
                }
                else if((rawUtmParts[i]).contains("="))
                {
                    result = ((rawUtmParts[i]).split("="))[1];
                }
                break;
            }
        }
        return result;
    }

    /*
    // Tracker for Classic GA (call this if you are using Classic GA only)
    private void trackInstallReferrer(final String referrerUrl) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                CampaignTrackingReceiver receiver = new CampaignTrackingReceiver();
                Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
                intent.putExtra("referrer", referrerUrl);
                receiver.onReceive(getApplicationContext(), intent);
            }
        });
    }*/


    // Tracker for GTM + Classic GA (call this if you are using GTM + Classic GA only)
    private void trackInstallReferrerForGTM(final String referrerUrl) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AppMeasurementInstallReferrerReceiver receiver = new AppMeasurementInstallReferrerReceiver();
                Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
                intent.putExtra("referrer", referrerUrl);
                receiver.onReceive(getApplicationContext(), intent);
            }
        });
    }

}
