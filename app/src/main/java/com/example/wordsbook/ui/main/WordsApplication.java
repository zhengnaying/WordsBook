package com.example.wordsbook.ui.main;

import android.app.Application;
import android.content.Context;

//方便获取context
public class WordsApplication extends Application {
    public   static Context context;
    public   static Context getContext(){
        return WordsApplication.context;
    }

    public void onCreate(){
        super.onCreate();
        WordsApplication.context = getApplicationContext();

    }
}
