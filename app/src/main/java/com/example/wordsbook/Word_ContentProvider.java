package com.example.wordsbook;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.wordsbook.DB.Words_DB_Helper;

public class Word_ContentProvider extends ContentProvider {
    public static final int MULTIPLE_WORDS= 1;
    public static final int SINGLE_WORD= 2;
    Words_DB_Helper db_helper;

    private static final UriMatcher urimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        urimatcher.addURI(Words.AUTHORITY,Words.Word.PATH_SINGLE,SINGLE_WORD);
        urimatcher.addURI(Words.AUTHORITY,Words.Word.PATH_MULTIPLE,MULTIPLE_WORDS);
    }
    public Word_ContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = db_helper.getReadableDatabase();
        int count = 0;
        switch (urimatcher.match(uri)){
            case MULTIPLE_WORDS:
                count = db.delete(Words.Word.TABLE_NAME,selection,selectionArgs);
                break;
            case SINGLE_WORD:
                String whereClause = Words.Word._ID+ "=" + uri.getPathSegments().get(1);
                count = db.delete(Words.Word.TABLE_NAME,whereClause,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("UnknownURi"+uri);
        }
        //通知ContentResolver数据已经发生改变
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

   //返回uri对应数据的MIME类型
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (urimatcher.match(uri)){
            case MULTIPLE_WORDS:  //多条数据记录
                return Words.Word.MINE_TYPE_MULTIPLE;
            case SINGLE_WORD: //单条数据记录
                return Words.Word.MINE_TYPE_SINGLE;
            default:throw new UnsupportedOperationException("UnknownURi"+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = db_helper.getReadableDatabase();
        long id = db.insert(Words.Word.TABLE_NAME,null,values);
        if(id>0){
            //在已有的uri后面添加id
            Uri newUri = ContentUris.withAppendedId(Words.Word.CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(newUri,null);
            return newUri;
        }
        throw new UnsupportedOperationException("UnknownURi"+uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = db_helper.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Words.Word.TABLE_NAME);
        switch (urimatcher.match(uri)){
            case MULTIPLE_WORDS:
                return db.query(Words.Word.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            case SINGLE_WORD:
                qb.appendWhere(Words.Word._ID + "=" +uri.getPathSegments().get(1));
                return qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
            default:throw new UnsupportedOperationException("UnknownURi"+uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = db_helper.getReadableDatabase();
        int count = 0;
        switch (urimatcher.match(uri)){
            case MULTIPLE_WORDS:
                count=  db.update(Words.Word.TABLE_NAME,values,selection,selectionArgs);
                break;
            case SINGLE_WORD:
                String segment = uri.getPathSegments().get(1);
                count = db.update(Words.Word.TABLE_NAME,values,Words.Word._ID+"="+ segment,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("UnknownURi"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
