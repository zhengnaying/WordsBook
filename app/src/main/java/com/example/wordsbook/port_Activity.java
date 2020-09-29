package com.example.wordsbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.add_words:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                final View add_view = LayoutInflater.from(port_Activity.this).inflate(R.layout.add_dialog,null);
                dialog.setTitle("添加单词");
                dialog.setView(add_view);
                dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText word_Text = add_view.findViewById(R.id.word);
                        EditText pronunciation_Text = add_view.findViewById(R.id.pronunciation);
                        EditText meaning_Text = add_view.findViewById(R.id.meaning);
                        EditText sample_Text = add_view.findViewById(R.id.sample);
                        String word=word_Text.getText().toString();
                        String pronunciation=pronunciation_Text.getText().toString();
                        String meaning=meaning_Text.getText().toString();
                        String sample=sample_Text.getText().toString();
                        dictionary_db=db_helper.getWritableDatabase();
                        dictionary_db.execSQL("insert into words values (null,?,?,?,?)",new String[]{word,pronunciation,meaning,sample});
                        dictionary_db.close();
                        Toast.makeText(port_Activity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.show();
                break;
            case(R.id.help):
                Toast.makeText(this,"这是帮助键"，Toast.LENGTH_SHORT).show();;
                break;

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port_);
    }

    @Override
    public void onInit(int status) {

    }
}