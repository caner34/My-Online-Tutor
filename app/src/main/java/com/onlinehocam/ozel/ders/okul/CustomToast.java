package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class CustomToast
{
    Activity activity;
    Context context;
    String message;
    ICON icon;
    int duration;
    int backgroundResCode;
    int textColorCode;
    int iconColorCode;

    public enum ICON
    {
        STAR
    }


    public enum BACKGROUND {
        FG_ORANGE_BG_GREEN(R.drawable.rounded_rectangle_toast_bg),
        FG_GREEN_BG_ORANGE(R.drawable.rounded_rectangle_toast_bg2);

        private int resID;

        BACKGROUND(int resID) {
            this.resID = resID;
        }

        public int getResID() {
            return resID;
        }
    }


    public CustomToast(Activity activity, Context context, String message, ICON icon, int duration, int backgroundResCode, int textColorCode, int iconColorCode) {
        this.activity = activity;
        this.context = context;
        this.message = message;
        this.icon = icon;
        this.duration = duration;
        this.backgroundResCode = backgroundResCode;
        this.textColorCode = textColorCode;
        this.iconColorCode = iconColorCode;


        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) activity.findViewById(R.id.toastRoot));
        layout.setBackgroundResource(backgroundResCode);
        layout.setPadding(20,20,20,20);
        ImageView imageViewIcon = layout.findViewById(R.id.imageViewToast);
        imageViewIcon.setColorFilter(iconColorCode);
        TextView textViewToast = layout.findViewById(R.id.textViewToast);
        textViewToast.setTextColor(textColorCode);
        if(icon == ICON.STAR)
        {
            imageViewIcon.setImageResource(R.drawable.ic_toasticon_stars_black_24dp);
        }
        textViewToast.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public CustomToast(Activity activity, Context context, String message, ICON icon, int duration) {
        this(activity, context, message, icon, duration, BACKGROUND.FG_ORANGE_BG_GREEN.getResID(), Color.WHITE, Color.WHITE);
    }

    public CustomToast(Activity activity, Context context, String message, int backgroundResCode) {
        this(activity, context, message, ICON.STAR, Toast.LENGTH_LONG, backgroundResCode, Color.WHITE, Color.WHITE);
    }

    public CustomToast(Activity activity, Context context, String message) {
        this(activity, context, message, ICON.STAR, Toast.LENGTH_LONG, BACKGROUND.FG_ORANGE_BG_GREEN.getResID(), Color.WHITE, Color.WHITE);
    }
}
