package com.example.wordsbook.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.wordsbook.Words;

public class Words_DB_Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="wordsDB";
    private static final int DATABASE_VERSION = 1;
   //建表sql
    private static final String SQL_CREATE_DATABASE = "CREATE TABLE " + Words.Word.TABLE_NAME+ "(" + Words.Word._ID + "VARCHAR(32) PRIMARY KEY NOT NULL," +
            Words.Word.COLUMN_NAME_WORD +"TEXT UNIQUE NOT NULL,"+
            Words.Word.COLUMN_NAME_MEANING +"TEXT," +
            Words.Word.COLUMN_NAME_SAMPLE +"TEXT)";
   //删表sql
   private static final String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Words.Word.TABLE_NAME;

    public Words_DB_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
