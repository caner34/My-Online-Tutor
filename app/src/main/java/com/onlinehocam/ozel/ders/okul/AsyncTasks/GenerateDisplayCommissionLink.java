package com.onlinehocam.ozel.ders.okul.AsyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinehocam.ozel.ders.okul.CustomToast;
import com.onlinehocam.ozel.ders.okul.MarketerHomePageActivity;
import com.onlinehocam.ozel.ders.okul.MarketerShareAppLinkOnSocialMediaActivity;
import com.onlinehocam.ozel.ders.okul.QRCodeHelper;
import com.onlinehocam.ozel.ders.okul.R;
import com.onlinehocam.ozel.ders.okul.ShareHelper;
import com.onlinehocam.ozel.ders.okul.SoundHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GenerateDisplayCommissionLink extends AsyncTask<String, Void, Void>
{

    Activity activity;
    Context context;
    int userId;
    String isSelectedCLinkIDWithUserName;
    TextView textViewCommissionLink;
    ImageView imageViewQRCode;
    LinearLayout linearLayoutAfterQRCodeDisplayedPanel;
    ShareHelper.SOCIAL_MEDIA_PLATFORMS social_media_platforms;
    String link;


    String KEY_ENCODING_OUTPUT = "UTF-8";
    String KEY_ENCODING_INPUT = "UTF-8";

    String KEY_URL_START = "http://134.209.234.180/";
    String KEY_URL_END = ".php";


    String resultString;


    public GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, TextView textViewCommissionLink) {
        this.activity = activity;
        this.context = context;
        this.userId = userId;
        this.isSelectedCLinkIDWithUserName = isSelectedCLinkIDWithUserName;
        this.textViewCommissionLink = textViewCommissionLink;
    }

    public GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, String link) {
        this.activity = activity;
        this.context = context;
        this.userId = userId;
        this.isSelectedCLinkIDWithUserName = isSelectedCLinkIDWithUserName;
        this.link = link;
    }

    public GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, TextView textViewCommissionLink, ImageView imageViewQRCode, LinearLayout linearLayoutAfterQRCodeDisplayedPanel) {
        this.activity = activity;
        this.context = context;
        this.userId = userId;
        this.isSelectedCLinkIDWithUserName = isSelectedCLinkIDWithUserName;
        this.textViewCommissionLink = textViewCommissionLink;
        this.imageViewQRCode = imageViewQRCode;
        this.linearLayoutAfterQRCodeDisplayedPanel = linearLayoutAfterQRCodeDisplayedPanel;
    }

    public GenerateDisplayCommissionLink(Activity activity, Context context, int userId, String isSelectedCLinkIDWithUserName, ShareHelper.SOCIAL_MEDIA_PLATFORMS social_media_platforms)
    {
        this.activity = activity;
        this.context = context;
        this.userId = userId;
        this.isSelectedCLinkIDWithUserName = isSelectedCLinkIDWithUserName;
        this.social_media_platforms = social_media_platforms;
    }

    @Override
    protected Void doInBackground(String... strings)
    {
        String type = strings[0];
        /*if (type.equals(KEY_AddTutorToFavorites))
        {
            return Execute_BooleanQuery(type, params);
        }*/
        resultString = Execute_BooleanQuery(type, strings);
        return null;
    }


    private String Execute_BooleanQuery(String type, String... params)
    {
        String crUrl = KEY_URL_START + type + KEY_URL_END;
        try
        {
            URL url = new URL(crUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, KEY_ENCODING_OUTPUT));


            String post_data = GetPostDataFromParams(KEY_ENCODING_OUTPUT, params);

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, KEY_ENCODING_INPUT));

            String result = "";
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
            {
                result += line;
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return "failed due to MalformedURLException";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "failed due to IOException";
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    static String GetPostDataFromParams(String encoding, String... params)
    {
        StringBuilder sb = new StringBuilder();
        String crLine = "";


        for(int i = 0; i < ((params.length-1)/2); i++)
        {
            crLine = "";
            try
            {
                if(i != 0)
                {
                    crLine = "&";
                }
                crLine += URLEncoder.encode(params[1 + 2*i], encoding)+"="+URLEncoder.encode(params[1 + 2*i + 1], encoding);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return "failedPostData";
            }

            sb.append(crLine);
        }

        return sb.toString();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(resultString.isEmpty())
        {
            SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.NOT_AVAILABLE);
            if(imageViewQRCode != null)
            {
                imageViewQRCode.setVisibility(View.GONE);
                imageViewQRCode.setImageBitmap(null);
                textViewCommissionLink.setText("");
                new CustomToast(activity, context, context.getString(R.string.marketer_warning_qr_code_cannot_be_generated_now));
            }
            else if(textViewCommissionLink != null)
            {
                textViewCommissionLink.setText("");
                new CustomToast(activity, context, context.getString(R.string.marketer_warning_link_cannot_be_displayed_now));
            }
            else if(social_media_platforms != null)
            {
                new CustomToast(activity, context, context.getString(R.string.marketer_warning_link_cannot_be_shared_now));
            }
            else if(link != null)
            {
                new CustomToast(activity, context, context.getString(R.string.marketer_warning_link_cannot_be_shared_now));
            }
        }
        else
        {
            if(imageViewQRCode != null)
            {
                textViewCommissionLink.setText(resultString);
                imageViewQRCode.setVisibility(View.VISIBLE);
                imageViewQRCode.setImageBitmap(QRCodeHelper.GenerateQRCode(activity, context, resultString));
                linearLayoutAfterQRCodeDisplayedPanel.setVisibility(View.VISIBLE);
            }
            else if(textViewCommissionLink != null)
            {
                textViewCommissionLink.setText(resultString);
            }
            else if(social_media_platforms != null)
            {
                String dataToShare = ((MarketerShareAppLinkOnSocialMediaActivity)activity).editTextShareMessage.getText().toString();

                SoundHelper.PlayMediaPlayerSound(activity, SoundHelper.SOUNDS.SHARE);

                if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.WHATSAPP))
                {
                    ShareHelper.ShareViaWhatsApp(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.TWITTER))
                {
                    ShareHelper.ShareViaTwitter(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.INSTAGRAM))
                {
                    ShareHelper.ShareViaInstagram(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.FACEBOOK))
                {
                    ShareHelper.ShareViaFacebook(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.GMAIL))
                {
                    ShareHelper.ShareViaGmail(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.SNAPCHAT))
                {
                    ShareHelper.ShareViaSnapchat(activity, context, dataToShare);
                }
                else if(social_media_platforms.equals(ShareHelper.SOCIAL_MEDIA_PLATFORMS.OTHER))
                {
                    ShareHelper.ShareViaOther(activity, context, dataToShare);
                }
            }
            else if(link != null)
            {
                ((MarketerShareAppLinkOnSocialMediaActivity)activity).link = resultString;
                ((MarketerShareAppLinkOnSocialMediaActivity)activity).editTextShareMessage.setText(((MarketerShareAppLinkOnSocialMediaActivity)activity).readyMessage + "\n\n" + ((MarketerShareAppLinkOnSocialMediaActivity)activity).link);
            }
        }
    }
}
