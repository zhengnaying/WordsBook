package com.example.wordsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.example.wordsbook.Fragment.WordDetail_Fragment;
import com.example.wordsbook.Fragment.WordItem_Fragment;

public class MainActivity extends AppCompatActivity implements WordDetail_Fragment.OnFragmentInteractionListener, WordItem_Fragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onWordDetailClick(Uri uri) {

    }

    @Override
    public void onWordItemClick(String id) {

    }

    @Override
    public void onDeleteDialog(String strId) {

    }

    @Override
    public void onUpdateDialog(String strId) {

    }
}