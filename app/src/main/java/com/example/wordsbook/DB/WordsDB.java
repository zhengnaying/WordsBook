package com.example.wordsbook.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wordsbook.ui.main.GUID;
import com.example.wordsbook.ui.main.Words;
import com.example.wordsbook.ui.main.WordsApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WordsDB {
    private static final String TAG = "Tag";
    private static Words_DB_Helper db_Helper;
    private static WordsDB instance= new WordsDB();
    public static WordsDB getWordsDB() {
        return WordsDB.instance;
    }
    private WordsDB(){
        if(db_Helper == null)
            db_Helper = new Words_DB_Helper(WordsApplication.getContext());
    }

   public void close(){
        if(db_Helper != null){
            db_Helper.close();
        }
   }
    //获得单个单词的全部信息
   public Words.WordDecription getSingleWord(String id){
       SQLiteDatabase db  = db_Helper.getReadableDatabase();
       String sql = "select * from words where _ID=?";
       Cursor cursor = db.rawQuery(sql,new String[]{id});
       if(cursor.moveToNext()){
           Words.WordDecription item =  new Words.WordDecription(cursor.getString(cursor.getColumnIndex(Words.Word._ID)),
                   cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)),
                   cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING)),
                   cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE)));
           return item;
       }
       return null;
   }

    //得到全部单词列表
    public ArrayList<Map<String,String>> getAllWords(){
        if (db_Helper == null) {
            Log.v(TAG, "WordsDB::getAllWords()");
            return null;
        }
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        String[] projection  = { Words.Word._ID,Words.Word.COLUMN_NAME_WORD };
        //排序
        String sortOrder = Words.Word.COLUMN_NAME_WORD + " DESC";

        Cursor c = db.query(Words.Word.TABLE_NAME,projection,null,null,null,null,sortOrder);
        return ConvertCursorWordList(c);
    }

    //将游标转化为单词列表
    public ArrayList<Map<String,String>> ConvertCursorWordList(Cursor cursor){
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while(cursor.moveToNext()){
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word._ID,String.valueOf(cursor.getColumnIndex(Words.Word._ID)));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD)));
            result.add(map);
        }
     return  result;
    }

    //使用Sql语句插入单词
    public void InsertUserSql(String strWord, String strMeaning, String strSample) {
        String sql = "insert into  words(_id,word,meaning,sample) values(?,?,?,?)";
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        db.execSQL(sql,new String[]{GUID.getGUID(),strWord,strMeaning,strSample});
    }

    //使用insert方法增加单词
    public void Insert(String strWord, String strMeaning, String strSample){
      SQLiteDatabase db = db_Helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Words.Word._ID,GUID.getGUID());
        values.put(Words.Word.COLUMN_NAME_WORD,strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING,strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE,strSample);
        long newRowId;
        newRowId = db.insert(Words.Word.TABLE_NAME,null,values);
    }

    //使用Sql语句删除单词
    public void DeleteUseSql(String strId) {
        String sql = "delete from words where _id= '" + strId + "'";
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        db.execSQL(sql);
    }

    //删除单词
    public void Delete(String strId){
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};
        db.delete(Words.Word.TABLE_NAME,selection,selectionArgs);
    }
    //使用Sql语句更新单词
    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample){
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        String sql = "update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql,new String[]{strWord,strMeaning,strSample,strId});
    }

    //使用方法更新
    public void Update(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = db_Helper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, strWord);
        values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

        String selection = Words.Word._ID + " = ?";
        String[] selectionArgs = {strId};

        int count = db.update(
                Words.Word.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //使用Sql语句查找
    public ArrayList<Map<String, String>> SearchUseSql(String strWordSearch){
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        String sql = "select * from words where word like ? order by word desc";
        Cursor c = db.rawQuery(sql, new String[]{"%" + strWordSearch + "%"});
        return ConvertCursorWordList(c);
    }

    //使用query方法查找
    public ArrayList<Map<String, String>> Search(String strWordSearch){
        SQLiteDatabase db = db_Helper.getReadableDatabase();
        String[] projection = {Words.Word._ID, Words.Word.COLUMN_NAME_WORD};
        String sortOrder = Words.Word._ID + " DESC";
        String selection = Words.Word._ID + " LIKE ?";
        String[] selectionArgs = {"%" + strWordSearch + "%"};
        Cursor c = db.query(Words.Word.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        return ConvertCursorWordList(c);
    }
}
