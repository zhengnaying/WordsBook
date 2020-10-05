package com.example.wordsbook.ui.main;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    public void onCreate(){
        super.onCreate();
        context= context.getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}

