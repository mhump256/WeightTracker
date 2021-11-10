package com.mod6.cs360weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WeightHistoryDatabase extends SQLiteOpenHelper {

    public static final String WEIGHT_TABLE = "WEIGHT_TABLE";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_DATE= "date";
    public static final String COLUMN_USER_WEIGHT = "weight";


    public WeightHistoryDatabase(@Nullable Context context) {
        super(context, "userWeight.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase WDB) {

        String createTableStatement = "CREATE TABLE " + WEIGHT_TABLE + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_DATE + " INTEGER,"
                + COLUMN_USER_WEIGHT + " INTEGER" + ")";

        WDB.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase WDB, int oldVersion, int newVersion) {
        WDB.execSQL("DROP TABLE IF EXISTS " + WEIGHT_TABLE);

        onCreate(WDB);
    }

}
