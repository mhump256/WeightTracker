package com.mod6.cs360weighttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UsernameDBHelperTest extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";

    public UsernameDBHelperTest(@Nullable Context context) {
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

    }

    public boolean addOne(Users users){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, users.getUsername());
        cv.put(COLUMN_USER_PASSWORD, users.getPassword());

        long insert = MyDB.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }else {
        return true;}
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ?", new String[] {username});

        if(cursor.getCount()>0)
            return true;

        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USER_TABLE where user_name = ? and user_password = ?", new String[]{username, password});

        if (cursor.getCount() > 0)
            return true;

        else
            return false;
    }
}

