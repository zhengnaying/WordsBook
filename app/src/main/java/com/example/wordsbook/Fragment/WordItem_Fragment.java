package com.example.wordsbook.Fragment;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordsbook.DB.WordsDB;
import com.example.wordsbook.R;
import com.example.wordsbook.ui.main.Words;

import java.util.ArrayList;
import java.util.Map;

public class WordItem_Fragment extends ListFragment {
    private static final String TAG = "Tag";

    //判断是否为横屏
    private boolean currentIsLand;
    private  OnFragmentInteractionListener mListener;

    public static WordItem_Fragment newInstance(){
        WordItem_Fragment fragment =  new WordItem_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener=(OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //刷新单词列表
        refreshWordsList();

    }

    public WordItem_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater,container,savedInstanceState);
        //为列表注册上下文菜单
        assert view != null;
        ListView mListview = (ListView)view.findViewById(android.R.id.list);
        registerForContextMenu(mListview);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TextView textId = null;
        TextView textWord = null;
        TextView textMeaning = null;
        TextView textSample = null;

        AdapterView.AdapterContextMenuInfo info = null;
        View itemView = null;

        switch (item.getItemId()){

        }

    }

    //更新单词列表，从数据库中找到所有单词，然后在列表中显示出来
    public void refreshWordsList(){
        WordsDB wordsDB = WordsDB.getWordsDB();
        if(wordsDB!= null){
            ArrayList<Map<String,String>> items = wordsDB.getAllWords();
            SimpleAdapter adapter = new SimpleAdapter(getActivity(),items,R.layout.item,new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},new int[]{R.id.textId, R.id.textViewWord});
            setListAdapter(adapter);
        }
    }

    //更新单词列表，从数据库中找到同strWord向匹配的单词，然后在列表中显示出来
    public void refreshWordsList(String strWord){
        WordsDB wordsDB = WordsDB.getWordsDB();
        if(wordsDB!= null){
            ArrayList<Map<String,String>> items = wordsDB.SearchUseSql(strWord);
            if(items.size()>0){
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),items,R.layout.item,new String[]{Words.Word._ID,Words.Word.COLUMN_NAME_WORD},
                        new int[]{R.id.textId,R.id.textViewWord});
                setListAdapter(adapter);
            }else
                Toast.makeText(getActivity(),"Not found", Toast.LENGTH_LONG).show();

        }
    }



    public void onListItemClick(ListView l,View v ,int position,long id){
        super.onListItemClick(l,v,position,id);

        if(mListener!= null){
            //通知Fragment所在的Activity，用户单击了列表的position项
            TextView textView = (TextView)v.findViewById(R.id.textId);
            if(textView!= null){
                //将单词ID传过去
                mListener.onWordItemClick(textView.getText().toString());
            }
        }
    }
    /**
 * Fragment所在的Activity必须实现该接口，通过该接口Fragment和Activity可以进行通信
 */

    public interface OnFragmentInteractionListener{
        public void onWordItemClick(String id);
        public void onDeleteDialog(String strId);
        public void onUpdateDialog(String strId);
    }
}