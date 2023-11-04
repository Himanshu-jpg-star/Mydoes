package com.example.mydoes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mydoes.model.MyDoesModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "MYDOES_DATABASE";
    private static final String TABLE_NAME = "MYDOES_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT, STATUS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(MyDoesModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateTask(int id, String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<MyDoesModel> getAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        List<MyDoesModel> modelList = new ArrayList<>();
        db.beginTransaction();

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                int idIndex = cursor.getColumnIndex(COL_1);
                int taskIndex = cursor.getColumnIndex(COL_2);
                int statusIndex = cursor.getColumnIndex(COL_3);

                while (cursor.moveToNext()) {
                    MyDoesModel task = new MyDoesModel();

                    // Check if the column index is valid before accessing it
                    if (idIndex != -1) {
                        task.setId(cursor.getInt(idIndex));
                    }

                    if (taskIndex != -1) {
                        task.setTask(cursor.getString(taskIndex));
                    }

                    if (statusIndex != -1) {
                        task.setStatus(cursor.getInt(statusIndex));
                    }

                    modelList.add(task);
                }
            }
        } finally {
            db.endTransaction();

            if (cursor != null) {
                cursor.close();
            }

            db.close();
        }

        return modelList;
    }
}
