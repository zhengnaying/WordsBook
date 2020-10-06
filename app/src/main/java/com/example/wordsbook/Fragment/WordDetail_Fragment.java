package com.example.wordsbook.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wordsbook.DB.WordsDB;
import com.example.wordsbook.R;


public class WordDetail_Fragment extends Fragment {

    private static final String TAG="Tag";
    public static final String ARG_ID = "id";

    private String mID;//单词主键
    private OnFragmentInteractionListener mListener;//本Fragment所在的Activity


    public WordDetail_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WordDetail_Fragment newInstance(String wordId) {
        WordDetail_Fragment fragment = new WordDetail_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID,wordId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            mID = getArguments().getString(ARG_ID);
    }

    private void YoudaoOpenAPI(String strWord){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_word_detail_, container, false);
        WordsDB wordsDB = new WordsDB();
        if (wordsDB != null && mID!=null){
            TextView textViewWord = (TextView)view.findViewById(R.id.word);
            TextView textViewMeaning = (TextView)view.findViewById(R.id.meaning);
            TextView textViewSample = (TextView) view.findViewById(R.id.sample);
        }

        return view;
    }

    public interface OnFragmentInteractionListener{
        public void onWordDetailClick(Uri uri);
    }
}