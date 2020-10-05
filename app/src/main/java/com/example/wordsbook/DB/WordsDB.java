package com.example.wordsbook.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wordsbook.GUID;
import com.example.wordsbook.Words;
import com.example.wordsbook.WordsApplication;

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

    }


}
