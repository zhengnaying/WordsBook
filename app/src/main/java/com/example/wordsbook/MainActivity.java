package com.example.wordsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.wordsbook.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            Intent intent=new Intent();
            intent.setClass(MainActivity.this, port_Activity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        else{
            Intent intent =new Intent();
            intent.setClass(MainActivity.this, land_Activity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }
}