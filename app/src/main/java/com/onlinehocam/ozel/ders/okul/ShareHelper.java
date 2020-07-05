package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import java.util.List;

public class ShareHelper
{

    public enum SOCIAL_MEDIA_PLATFORMS
    {
        WHATSAPP,
        TWITTER,
        INSTAGRAM,
        FACEBOOK,
        GMAIL,
        SNAPCHAT,
        OTHER
    }

    public static boolean isPackageExisted(Activity activity, String targetPackage){
        PackageManager pm= activity.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void ShareViaWhatsApp(Activity activity, Context context, String data)
    {
        PackageManager pm = activity.getPackageManager();
        try
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            i.setPackage("com.whatsapp");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(Intent.EXTRA_TEXT, data);
            activity.startActivity(Intent.createChooser(i, context.getString(R.string.marketer_share_reference_link)));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            new CustomToast(activity, context,  context.getString(R.string.marketer_share_warning_whatsapp_not_installed));
        }
    }


    public static void ShareViaTwitter(Activity activity, Context context, String data)
    {

        try
        {
            Intent shareIntent;

            if(isPackageExisted(activity, "com.twitter.android"))
            {
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setClassName("com.twitter.android",
                        "com.twitter.android.PostActivity");
                shareIntent.setType("text/*");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, data);
                activity.startActivity(shareIntent);
            }
            else
            {
                String tweetUrl = "https://twitter.com/intent/tweet?text=" + data;
                Uri uri = Uri.parse(tweetUrl);
                shareIntent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(shareIntent);
            }
        }
        catch (Exception e)
        {
            new CustomToast(activity, context,  context.getString(R.string.marketer_share_warning_twitter_cannot_be_launched));
        }
    }

    public static void ShareViaInstagram(Activity activity, Context context, String data)
    {
        PackageManager pm = activity.getPackageManager();
        try
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");

            //Checks if package exists or not
            PackageInfo info = pm.getPackageInfo("com.instagram.android", PackageManager.GET_META_DATA);

            i.setPackage("com.instagram.android");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(Intent.EXTRA_TEXT, data);
            activity.startActivity(Intent.createChooser(i, context.getString(R.string.marketer_share_reference_link)));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            new CustomToast(activity, context,  context.getString(R.string.marketer_share_warning_instagram_not_installed));
        }
    }

    public static void ShareViaFacebook(Activity activity, Context context, String data)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, data);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = activity.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + data;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        activity.startActivity(intent);
    }

    public static void ShareViaGmail(Activity activity, Context context, String data)
    {
        PackageManager pm = activity.getPackageManager();
        try
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");

            //Checks if package exists or not
            PackageInfo info = pm.getPackageInfo("com.google.android.gm", PackageManager.GET_META_DATA);

            i.setPackage("com.google.android.gm");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(Intent.EXTRA_TEXT, data);
            activity.startActivity(Intent.createChooser(i, context.getString(R.string.marketer_share_reference_link)));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            new CustomToast(activity, context,  context.getString(R.string.marketer_share_warning_gmail_not_installed));
        }
    }

    public static void ShareViaSnapchat(Activity activity, Context context, String data)
    {
        PackageManager pm = activity.getPackageManager();
        try
        {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");

            //Checks if package exists or not
            PackageInfo info = pm.getPackageInfo("com.snapchat.android", PackageManager.GET_META_DATA);

            i.setPackage("com.snapchat.android");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(Intent.EXTRA_TEXT, data);
            activity.startActivity(Intent.createChooser(i, context.getString(R.string.marketer_share_reference_link)));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            new CustomToast(activity, context,  context.getString(R.string.marketer_share_warning_snapchat_not_installed));
        }
    }

    public static void ShareViaOther(Activity activity, Context context, String data)
    {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, data);

        activity.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.marketer_share_reference_link)));
    }


}
