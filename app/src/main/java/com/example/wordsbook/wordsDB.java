package com.example.wordsbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class wordsDB extends SQLiteOpenHelper {

    private String SQL_CREATE_DATABASE="create table words ("+
            "_id integer primary key autoincrement,"+
            "word TEXT,"+
            "pronunciation TEXT,"
            + "meaning TEXT,"+
            "sample TEXT)";
    private Context mContext;

    public wordsDB(@Nullable Context context) {
        super(context, "dictionary_db", null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DATABASE);
        db.execSQL("CREATE TABLE EnglishWords (word TEXT,pronunciation TEXT,meaning TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
