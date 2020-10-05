package com.example.wordsbook;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Objects;


public class left_Fragment extends Fragment implements TextToSpeech.OnInitListener{

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
    View view;
    private TextToSpeech textToSpeech;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_left_, container, false);
        textToSpeech = new TextToSpeech(getActivity(),this);
        initView();
        initData();
        initListener();
        list_view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0,0,0,"删除该条");
                menu.add(0,1,0,"修改该条");
            }
        });
        return view;
    }

    private void initListener() {
        //清除历史记录
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHistory();
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                insertHistory(word_input.getText().toString().trim());
            }
        });//搜索保存搜索记录
        word_input.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //保存搜索记录
                    insertHistory(word_input.getText().toString().trim());
                }
                return false;
            }
        });


    }

    private void insertHistory(String insert) {
    }

    private void deleteHistory() {
    }

    //获取历史记录
    private void initData() {
        db_helper = new Words_DB_Helper(this.getActivity());
        db_history = new Words_DB_History(this.getActivity());
        cursor = db_history.getReadableDatabase().rawQuery("select * from words_history ", null);
        adapter =  new SimpleCursorAdapter(this.getActivity(),android.R.layout.simple_list_item_1,cursor,new String[]{"word_history"}, new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        list_view.setAdapter(adapter);
    }

    private void initView() {
        word_input = view.findViewById(R.id.word);
        find = view.findViewById(R.id.find);
        history = view.findViewById(R.id.history);
        clear = view.findViewById(R.id.clear);
        list_view = view.findViewById(R.id.listView);
        meaning=view.findViewById(R.id.meaning);
        result=view.findViewById(R.id.result);
    }

    @Override
    public void onInit(int status) {

    }
}