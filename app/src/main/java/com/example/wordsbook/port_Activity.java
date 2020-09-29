package com.example.wordsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class port_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    EditText word_input;
    TextView find,history,clear,meaning,result;
    view_History list_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_);
    }

    @Override
    public void onInit(int status) {

    }
}