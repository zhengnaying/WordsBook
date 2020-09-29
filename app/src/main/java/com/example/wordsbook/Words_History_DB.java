package com.example.wordsbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Words_History_DB extends SQLiteOpenHelper {
    private String CREATE_AGO_TABLE="create table words_ago(_id integer primary key autoincrement,word_ago varchar(200))";
    public Words_History_DB(@Nullable Context context) {
        super(context,"record_db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AGO_TABLE);
        Log.d("#DB","WordAgo");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
