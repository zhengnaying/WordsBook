package com.example.wordsbook;

import android.net.Uri;
import android.provider.BaseColumns;

public class Words {
    public static class  WordItem{
        public String id,word;
        public WordItem(String id ,String word){
            this.id =id;
            this.word = word;
        }
        public String toString(){
            return word;
        }

    }
    public static class WordDecription{
        public String id,word,meaning,sample;
        public WordDecription(String id,String word,String meaning,String sample){
            this.id = id;
            this.meaning = meaning;
            this.sample = sample;
            this.word = word;
        }
    }


    public static abstract class Word implements BaseColumns {
        public static final  String TABLE_NAME = "words";   //表名
        public static final String COLUMN_NAME_WORD = "word";   //字段：单词
        public static final String COLUMN_NAME_MEANING = "meaning"; //字段： 单词含义
        public static final String COLUMN_NAME_SAMPLE = "sample"; //字段： 单词示例

        //MIME类型
        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
        public static final String MINE_ITEM = "vnd.example.wordsbook.Words";

        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MINE_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MINE_ITEM;

        public static final String PATH_SINGLE = "words/#";//单条数据的路径
        public static final String PATH_MULTIPLE = "words";//多条数据的路径



    }
}
