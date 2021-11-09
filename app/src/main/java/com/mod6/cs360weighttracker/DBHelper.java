package com.mod6.cs360weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //Credentials Table
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";

    //Daily Weight Table
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_GOAL = "Goal";
    public static final String COLUMN_CALORIC_INTAKE = "CalorieIntake";
    public static final String COLUMN_CALORIC_EXPENDITURE = "CalorieLost";


    public DBHelper(@Nullable Context context) {
        super(context, "userName.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";

        MyDB.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(MyDB);

    }

    public boolean addOne(Users users){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, users.getUsername());
        cv.put(COLUMN_USER_PASSWORD, users.getPassword());

        MyDB.insert(USER_TABLE, null, cv);

        return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ?", new String[] {username});
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ? and user_password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    public void createDailyTable(String userName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String USER_WEIGHT_TABLE = "Weight_Table_" + userName;

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_WEIGHT_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_WEIGHT + " INTEGER, " + COLUMN_GOAL + " INTEGER, "
                + COLUMN_CALORIC_INTAKE + " INTEGER, " + COLUMN_CALORIC_EXPENDITURE + " INTEGER"
                + ")";

        MyDB.execSQL(createTableStatement);
    }


}

