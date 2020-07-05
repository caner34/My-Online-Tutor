package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

public class QRCodeHelper
{
    public static Bitmap GenerateQRCode(Activity activity, Context context, String data)
    {
        try
        {
            QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, GetSuitableSmallerDimension(activity));
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);

            Bitmap bitmap = qrgEncoder.getBitmap();
            return bitmap;
        }
        catch (Exception ex)
        {
            //GlobalVariables.Log(context, "EXCEPTION in GenerateQRCode: "+ex.getMessage());
            return null;
        }
    }

    private static int GetSuitableSmallerDimension(Activity activity)
    {
        WindowManager manager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;
        return smallerDimension;
    }
}
