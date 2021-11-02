package com.mod6.cs360weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";


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

        long insert = MyDB.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ?", new String[] {username});
        cursor.close();  //close
        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ? and user_password = ?", new String[]{username, password});
        cursor.close();         //close
        return cursor.getCount() > 0;
    }

    public void createDailyTable(String userName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String USER_TABLE = "Weight_Table_" + userName;
        String COLUMN_DATE = "Date";
        String COLUMN_WEIGHT = "Weight";

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "("
                + COLUMN_DATE + " TEXT, " + COLUMN_WEIGHT + " INTEGER"
                + ")";

        MyDB.execSQL(createTableStatement);
    }


}

