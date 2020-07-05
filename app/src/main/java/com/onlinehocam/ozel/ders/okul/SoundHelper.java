package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.media.MediaPlayer;


public class SoundHelper
{

    public enum SOUNDS
    {
        CONNECTION_FAILED(R.raw.connection_glass_tinkle),
        CLICK1(R.raw.sound_click_62),
        FALSE_BELL(R.raw.sound_false_bell_40),
        TRUE_CHIP(R.raw.chip_true),
        DISPLAY_QUESTION_IMAGE(R.raw.question_image_display_bubbles),
        PURCHASE(R.raw.purchase),
        MENU_CLICK(R.raw.menu_click),
        SHARE(R.raw.share),
        NOT_AVAILABLE(R.raw.not_available)
        ;



        private int numVal;
        SOUNDS(int numVal) { this.numVal = numVal; }
        public int getNumVal() { return numVal; }
    }

    public static void PlayMediaPlayerSound(Activity activity, int soundResourceID)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(activity, soundResourceID);
        mediaPlayer.setVolume(1f, 1f);
        mediaPlayer.start();
    }

    public static void PlayMediaPlayerSound(Activity activity, SOUNDS soundEnum)
    {
        PlayMediaPlayerSound(activity, soundEnum.getNumVal());
    }
}
