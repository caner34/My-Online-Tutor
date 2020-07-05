package com.onlinehocam.ozel.ders.okul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "GlobalDB.db";
    public static final int DATABASE_VERSION = 9;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static abstract class TableGlobalVariables implements BaseColumns
    {
        public static final String TABLE_NAME = "data_global_variables";
        public static final String COL_0_VARIABLE_KEY = "variable_key";
        public static final String COL_1_VARIABLE_VALUE = "variable_value";

        public static final String KEY_IS_TO_FIRST_LOGIN = "is_to_first_login";
        public static final String KEY_LAST_LUNCH_TIME = "last_lunch_time";
        public static final String KEY_USER_STATUS = "user_status";
        public static final String KEY_USER_NAME = "user_name";
    }

    public static abstract class TableGlobalIntegerVariables implements BaseColumns
    {
        public static final String TABLE_NAME = "data_global_integer_variables";
        public static final String COL_0_VARIABLE_KEY = "variable_key";
        public static final String COL_1_VARIABLE_VALUE = "variable_value";


        public static final String KEY_USER_AUTO_SIGN_IN = "user_auto_sign_in";
        public static final String KEY_USER_ID = "user_id";
        public static final String KEY_ORIGINAL_QUESTIONS_ASKED = "original_questions_asked";
        public static final String KEY_DAYS_OPENED = "days_opened";
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i("messageCategory", "DatabaseHelper onCreate Entered : "); // delete afterwards
        CreateAllTables(db);
        CreateAllVariablesEmpty(db);
        //Log.i("messageCategory", "DatabaseHelper onCreate: "); // delete afterwards
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DropAllTables(db);
        onCreate(db);
    }

    public void ReCreateTables(SQLiteDatabase db)
    {
        DropAllTables(db);
        CreateAllTables(db);
        CreateAllVariablesEmpty(db);
    }

    public void DropAllTables(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TableGlobalVariables.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TableGlobalIntegerVariables.TABLE_NAME);
    }

    public void CreateAllTables(SQLiteDatabase db)
    {
        db.execSQL("create table " + TableGlobalVariables.TABLE_NAME   + " (" + TableGlobalVariables.COL_0_VARIABLE_KEY   + " TEXT PRIMARY KEY," + TableGlobalVariables.COL_1_VARIABLE_VALUE + " TEXT)");
        db.execSQL("create table " + TableGlobalIntegerVariables.TABLE_NAME   + " (" + TableGlobalIntegerVariables.COL_0_VARIABLE_KEY   + " TEXT PRIMARY KEY," + TableGlobalIntegerVariables.COL_1_VARIABLE_VALUE + " INTEGER)");
    }

    public void CreateAllVariablesEmpty(SQLiteDatabase db)
    {
        String firtLunchDateTimeStr = "";

        DateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd hh:mm:ss");
        Date crDate = Calendar.getInstance().getTime();
        firtLunchDateTimeStr = dateFormat.format(crDate);

        InsertDataTableGlobalVariables(TableGlobalVariables.KEY_IS_TO_FIRST_LOGIN, "true", db);
        InsertDataTableGlobalVariables(TableGlobalVariables.KEY_LAST_LUNCH_TIME, firtLunchDateTimeStr, db);
        InsertDataTableGlobalVariables(TableGlobalVariables.KEY_USER_STATUS, "", db);
        InsertDataTableGlobalVariables(TableGlobalVariables.KEY_USER_NAME, "", db);


        InsertDataTableGlobalIntegerVariables(TableGlobalIntegerVariables.KEY_USER_ID, -1, db);
        InsertDataTableGlobalIntegerVariables(TableGlobalIntegerVariables.KEY_DAYS_OPENED, 1, db);
        InsertDataTableGlobalIntegerVariables(TableGlobalIntegerVariables.KEY_ORIGINAL_QUESTIONS_ASKED, 0, db);
        InsertDataTableGlobalIntegerVariables(TableGlobalIntegerVariables.KEY_USER_AUTO_SIGN_IN, 0, db);
    }






    // START Of TableGlobalIntegerVariables.TABLE_NAME


    public boolean isTableGlobalIntegerVariableEntryExistent(String globalIntegerVariableKEY, int defaultValue, SQLiteDatabase db)
    {
        try
        {
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent entered: "); // delete afterwards
            String whereclause = TableGlobalVariables.COL_0_VARIABLE_KEY + "=?";
            String[] whereargs = new String[]{String.valueOf(globalIntegerVariableKEY)};
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent before cursor query: "); // delete afterwards
            Cursor cursor = db.query(TableGlobalVariables.TABLE_NAME,null,whereclause,whereargs,null,null,null);
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent after cursor query: "); // delete afterwards
            if(cursor.getCount() <= 0)
            {
                cursor.close();
                //Log.i("messageCategory", "DatabaseHelper before isReviewAlreadySuggestedEntryExistent Insert"); // delete afterwards
                if(defaultValue != -1)
                {
                    boolean isEmptyInsertedSuccessfully = InsertDataTableGlobalIntegerVariables(globalIntegerVariableKEY, defaultValue, db);
                }
                return false;
            }
            cursor.close();
            return true;
        }
        catch(Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent EXCEPTION: "+ex.getMessage());
            return false;
        }
    }


    public boolean InsertDataTableGlobalIntegerVariables(String key, int value, SQLiteDatabase db) {
        long result = -1;
        try
        {
            //Log.i("messageCategory", "DatabaseHelper InsertDataTableGlobalIntegerVariables entered: "); // delete afterwards
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGlobalIntegerVariables.COL_0_VARIABLE_KEY, key);
            contentValues.put(TableGlobalIntegerVariables.COL_1_VARIABLE_VALUE, value);

            result = db.insert(TableGlobalIntegerVariables.TABLE_NAME, null, contentValues);
            //Log.i("messageCategory", "DatabaseHelper InsertDataTableGlobalIntegerVariables result: "+result); // delete afterwards
        }
        catch (Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper InsertDataTableGlobalIntegerVariables EXCEPTION: "+ex.getMessage());
        }
        if(result == -1)
            return false;
        else
            return true;
    }


    public int RetrieveGlobalIntegerVariables(String key, SQLiteDatabase db)
    {
        //Log.i("messageCategory", "DatabaseHelper retrive entered: "); // delete afterwards
        int result = -1;
        String whereclause = TableGlobalIntegerVariables.COL_0_VARIABLE_KEY + "=?";
        String[] whereargs = new String[]{key};
        Cursor csr = db.query(TableGlobalIntegerVariables.TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            result = csr.getInt(csr.getColumnIndex(TableGlobalIntegerVariables.COL_1_VARIABLE_VALUE));
        }
        return result;
    }


    public boolean UpdateDataTableGlobalIntegerVariables(String key, int value, SQLiteDatabase db) {
        int result = -1;
        try
        {
            //Log.i("messageCategory", "DatabaseHelper Update DataTableGlobalIntegerVariables entered: "); // delete afterwards
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGlobalIntegerVariables.COL_1_VARIABLE_VALUE, value);
            result = db.update(TableGlobalIntegerVariables.TABLE_NAME,contentValues ,TableGlobalIntegerVariables.COL_0_VARIABLE_KEY + "= ?", new String[] {""+key});
        }
        catch (Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper Update DataTableGlobalIntegerVariables EXCEPTION: "+ex.getMessage());
        }
        if(result == -1)
            return false;
        else
            return true;
    }

    // END Of TableGlobalIntegerVariables.TABLE_NAME




    // START Of TableGlobalVariables.TABLE_NAME


    public boolean isTableGlobalVariableEntryExistent(String globalVariableKEY, String defaultValue, SQLiteDatabase db)
    {
        try
        {
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent entered: "); // delete afterwards
            String whereclause = TableGlobalVariables.COL_0_VARIABLE_KEY + "=?";
            String[] whereargs = new String[]{String.valueOf(globalVariableKEY)};
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent before cursor query: "); // delete afterwards
            Cursor cursor = db.query(TableGlobalVariables.TABLE_NAME,null,whereclause,whereargs,null,null,null);
            //Log.i("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent after cursor query: "); // delete afterwards
            if(cursor.getCount() <= 0)
            {
                cursor.close();
                //Log.i("messageCategory", "DatabaseHelper before isReviewAlreadySuggestedEntryExistent Insert"); // delete afterwards
                if(!defaultValue.equals("NULL"))
                {
                    boolean isEmptyInsertedSuccessfully = InsertDataTableGlobalVariables(globalVariableKEY, defaultValue, db);
                }
                return false;
            }
            cursor.close();
            return true;
        }
        catch(Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper isReviewAlreadySuggestedEntryExistent EXCEPTION: "+ex.getMessage());
            return false;
        }
    }



    public boolean InsertDataTableGlobalVariables(String key, String value, SQLiteDatabase db) {
        long result = -1;
        try
        {
            //Log.i("messageCategory", "DatabaseHelper InsertDataTableGlobalVariables entered: "); // delete afterwards
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGlobalVariables.COL_0_VARIABLE_KEY, key);
            contentValues.put(TableGlobalVariables.COL_1_VARIABLE_VALUE, value);

            result = db.insert(TableGlobalVariables.TABLE_NAME, null, contentValues);
            //Log.i("messageCategory", "DatabaseHelper InsertDataTableGlobalVariables result: "+result); // delete afterwards
        }
        catch (Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper InsertDataTableGlobalVariables EXCEPTION: "+ex.getMessage());
        }
        if(result == -1)
            return false;
        else
            return true;
    }


    public String RetrieveGlobalVariables(String key, SQLiteDatabase db)
    {
        //Log.i("messageCategory", "DatabaseHelper retrive entered: "); // delete afterwards
        String result = "";
        String whereclause = TableGlobalVariables.COL_0_VARIABLE_KEY + "=?";
        String[] whereargs = new String[]{key};
        Cursor csr = db.query(TableGlobalVariables.TABLE_NAME,null,whereclause,whereargs,null,null,null);
        if (csr.moveToFirst()) {
            result = csr.getString(csr.getColumnIndex(TableGlobalVariables.COL_1_VARIABLE_VALUE));
        }
        return result;
    }


    public boolean UpdateDataTableGlobalVariables(String key, String value, SQLiteDatabase db) {
        int result = -1;
        try
        {
            //Log.i("messageCategory", "DatabaseHelper Update DataTableGlobalVariables entered: "); // delete afterwards
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableGlobalVariables.COL_1_VARIABLE_VALUE, value);
            result = db.update(TableGlobalVariables.TABLE_NAME,contentValues ,TableGlobalVariables.COL_0_VARIABLE_KEY + "= ?", new String[] {""+key});
        }
        catch (Exception ex)
        {
            //Log.e("messageCategory", "DatabaseHelper Update DataTableGlobalVariables EXCEPTION: "+ex.getMessage());
        }
        if(result == -1)
            return false;
        else
            return true;
    }

    // END Of TableGlobalVariables.TABLE_NAME



}
