package com.example.mydoes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class log_sign_Database extends SQLiteOpenHelper {
    public log_sign_Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {
        String qry1 = "create table users(signusername text,signpassword text)";
        sql.execSQL(qry1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void signup(String signusername, String signpassword) {
        ContentValues cv = new ContentValues();
        cv.put("signusername", signusername);
        cv.put("signpassword", signpassword);
        SQLiteDatabase dob = getWritableDatabase();
        dob.insert("users", null, cv);
        dob.close();
    }

    public int login(String signusername, String signpassword) {
        int result = 0;
        String str[] = new String[2];
        str[0] = signusername;
        str[1] = signpassword;
        SQLiteDatabase dob = getReadableDatabase();
        Cursor c = dob.rawQuery("select * from users where signusername=? and signpassword=?", str);

        if (c.moveToFirst()) {
            result = 1;
        }
        return result;
    }
}
