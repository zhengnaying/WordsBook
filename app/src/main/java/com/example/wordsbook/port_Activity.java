package com.example.wordsbook;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class port_Activity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    EditText word_input;
    TextView find,history,clear,meaning,result;
    view_History list_view;
    Words_DB_Helper db_helper;
    Words_DB_History db_history;
    SQLiteDatabase dictionary_db, history_db;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    String del_word="";
    String change_word = "";
    String sample = "";
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_);
    }

    @Override
    public void onInit(int status) {

    }
}