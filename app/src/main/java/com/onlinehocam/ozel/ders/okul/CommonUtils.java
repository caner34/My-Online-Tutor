package com.onlinehocam.ozel.ders.okul;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Spinner;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CommonUtils
{
    public static String[] GetSpinnerStringArrayWithHeaderTitle(Spinner s, List<String> originalList, String additionalWarning)
    {

        List<String> result = new ArrayList<>();

        result.add(s.getPrompt().toString());

        if(!additionalWarning.equals(""))
        {
            result.add(additionalWarning);
        }

        for(int i = 0; i < originalList.size(); i++)
        {
            result.add(originalList.get(i));
        }
        return ((String[])result.toArray(new String[result.size()]));
    }


    public static boolean DoubleTryParse(Context c,String string) {
        try
        {
            Double d = Double.parseDouble(string);
        }
        catch (Exception ex)
        {
            GlobalVariables.Log(c, "EXCEPTION: "+ex.getMessage());
            return false;
        }
        return true;
    }


    public static String GetDateTimeAsTSI()
    {
        String result = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));

        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date crDate = null;
        try
        {
            crDate = localDateFormat.parse( simpleDateFormat.format(new Date()) );
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = formatter.format(crDate);


        return result;
    }



    public static String GetDateAs_YYYY_MM_DD()
    {
        String result = GetDateTimeAsTSI().split(" ")[0];
        return result;
    }


    public static byte[] ConvertBitmapIntoByteArray(Bitmap bitmap, int maxImageSizeInBytes, Context context)
    {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        byte[] result = blob.toByteArray();

        GlobalVariables.Log(context, "blob.length: "+result.length);

        if(result.length > maxImageSizeInBytes)
        {
            Bitmap scaledBitmap = GetImageBitmapInAdequateDimensions(bitmap, result.length, maxImageSizeInBytes);
            return ConvertBitmapIntoByteArray(scaledBitmap, maxImageSizeInBytes, context);
        }

        GlobalVariables.Log(context, "ByteArrayOutputStream result.length"+result.length);
        return result;
    }

    public static byte[] ConvertBitmapIntoByteArray(Bitmap bitmap, Context context)
    {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /* Ignored for PNGs */, blob);
        byte[] result = blob.toByteArray();

        return result;
    }


    public static Bitmap ConvertByteArrayIntoBitmap(byte[] blobByteArray)
    {
        Bitmap result = null;

        result = BitmapFactory.decodeByteArray(blobByteArray, 0, blobByteArray.length);

        return result;
    }

    public static String ConvertDate_DD_MM_YYYY_INTO_YYYY_MM_DD(String date_dd_mm_yyyy, Context context)
    {
        if(date_dd_mm_yyyy.isEmpty())
        {
            return date_dd_mm_yyyy;
        }

        String result = "";
        String[] data = date_dd_mm_yyyy.split("/");
        result = data[2] + "-" +data[1] + "-" + data[0];
        return result;
    }

    public static Bitmap GetImageBitmapInAdequateDimensions(Bitmap originalBitmap, int originalBytesLength, int maxImageSizeInBytes)
    {
        int originalWidth = originalBitmap.getWidth();
        int originalHeight = originalBitmap.getHeight();

        int newWidth = -1;
        int newHeight = -1;

        double newToOriginalRatio = Math.sqrt( ((double) maxImageSizeInBytes / (double) originalBytesLength) );
        newWidth = (int)((double)originalWidth * newToOriginalRatio);
        newHeight = (int)((double)originalHeight * newToOriginalRatio);

        Bitmap result = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);

        OutputStream outputStream = new ByteArrayOutputStream();

        result.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        return result;
    }



    public static void DisplayYoutubeSolutionVideo(String solutionVideoID, Context context)
    {
        //TODO INVESTIGATE IF THIS METHOD without YOUTUBE API MAKES THE YOUTUBE CHANNELS SUSPENDED OR NOT
        //Note: Beware when you are using this method, YouTube may suspend your channel due to spam, this happened two times with me
        //https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + solutionVideoID));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + solutionVideoID));
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static double GetTimeDifferenceBetweenTimeInSeconds(Date lastDate, Date initialDate, Context context)
    {
        double result = 0.0;
        result = ((double)( (int) ( (lastDate.getTime()-initialDate.getTime()) * 100 ) )) / (double)100000;

        GlobalVariables.Log(context, "Difference Between 2 times in seconds: "+result);
        return result;
    }



    public static Date GetCrDate()
    {
        Date crDate = null;
        try
        {
            crDate = new Date();
            return crDate;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    //compression

    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    public static Bitmap GetThumbnail(Bitmap originalBitmap, Context context) throws FileNotFoundException, IOException
    {
        InputStream input = Bitmap2InputStream(originalBitmap);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 300) ? (originalSize / 300) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = GetPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = Bitmap2InputStream(originalBitmap);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

/*
    public static Bitmap GetThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException
    {
        InputStream input = context.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 300) ? (originalSize / 300) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = GetPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }
*/

    private static int GetPowerOfTwoForSampleRatio(double ratio)
    {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

    public static File CompressFile(File file, Context context)
    {
        try
        {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE)
            {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);


            GlobalVariables.Log(context, "length Of File AFTER compression: "+(file.length()/1024) + " KB");
            return file;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static File ConvertDrawableToFile(Bitmap bitmap, Context context)
    {
        String fileName = "tempImageFile.jpg";
        File file = new File(context.getCacheDir(), fileName);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bos);
        byte[] bitmapData = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            fos.write(bitmapData);
            fos.flush();

            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        GlobalVariables.Log(context, "length Of File BEFORE compression: "+(file.length()/1024) + " KB");
        return file;
    }


    public static File ConvertDrawableToFile(Drawable drawable, Context context)
    {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return ConvertDrawableToFile(bitmap, context);
    }


    public static byte[] ConvertBitmapToCompressedBytesArray(Bitmap photoBitmap, Context context)
    {
        File compressedFile = CommonUtils.CompressFile( CommonUtils.ConvertDrawableToFile(photoBitmap, context) , context);

        byte[] bytes = new byte[(int) compressedFile.length()];

        try
        {
            FileInputStream fis = new FileInputStream(compressedFile);
            fis.read(bytes); //read file into bytes[]
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return bytes;
    }


    public static byte[] ConvertBitmapToCompressedThumbnailBytesArray(Bitmap photoBitmap, Context context)
    {
        File compressedFile = null;
        try {
            compressedFile = CommonUtils.CompressFile( CommonUtils.ConvertDrawableToFile(GetThumbnail(photoBitmap, context), context) , context);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte[] bytes = new byte[(int) compressedFile.length()];

        try
        {
            FileInputStream fis = new FileInputStream(compressedFile);
            fis.read(bytes); //read file into bytes[]
            fis.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return bytes;
    }

    public static Bitmap GetCircleCroppedBitmap(Bitmap bitmap) {
        if(bitmap == null)
        {
            return bitmap;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    public static Bitmap CreateSquaredBitmap(Bitmap bitmap) {
        int dim = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap result = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, (dim - bitmap.getWidth()) / 2, (dim - bitmap.getHeight()) / 2, null);

        return result;
    }


    public static void CopyToClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }



    public static boolean isPermissionGranted(Activity activity, String permissionStr)
    {
        if (ContextCompat.checkSelfPermission(activity, permissionStr) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, new String[] { permissionStr }, 0);
            return false;
        }
        else
        {
            return true;
        }
    }


    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState))
        {
            return true;
        }
        return false;
    }

}
