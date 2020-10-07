package com.example.wordsbook;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordsbook.Fragment.WordDetail_Fragment;

public class WordDetailActivity extends AppCompatActivity implements WordDetail_Fragment.OnFragmentInteractionListener {

    @Override
    public void onWordDetailClick(Uri uri) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果是横屏的话直接退出
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        if(savedInstanceState == null){
            WordDetail_Fragment detail_fragment = new WordDetail_Fragment();
            detail_fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content,detail_fragment)
                    .commit();
        }


    }
}
