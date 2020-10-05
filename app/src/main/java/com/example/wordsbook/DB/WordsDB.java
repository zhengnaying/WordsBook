package com.example.wordsbook.DB;

import com.example.wordsbook.Words;
import com.example.wordsbook.WordsApplication;


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
}
