package com.example.wordsbook.Fragment;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

    }

    public WordItem_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater,container,savedInstanceState);
        //为列表注册上下文菜单
        ListView mListview = (ListView)view.findViewById(android.R.id.list);
        registerForContextMenu(mListview);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
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

    /**
 * Fragment所在的Activity必须实现该接口，通过该接口Fragment和Activity可以进行通信
 */

    public interface OnFragmentInteractionListener{
        public void onWordItemInteractionListener(String id);
        public void onDeleteDialog(String strId);
        public void onUpdateDialog(String strId);
    }
}