package com.example.wordsbook.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Words_DB_History extends SQLiteOpenHelper {
    private String CREATE_HISTORY_TABLE="create table words_history(_id integer primary key autoincrement,word_history varchar(200))";
    public Words_DB_History(@Nullable Context context) {
        super(context,"record_db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HISTORY_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
