package com.example.wordsbook;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

//方便获取context
public class WordBook extends Application {
    private  static Context context;
    private  static Context getContext(){
        return WordBook.context;
    }

    public void onCreate(){
        super.onCreate();
        WordBook.context = getApplicationContext();

    }
}