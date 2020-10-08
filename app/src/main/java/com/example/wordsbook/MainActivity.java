package com.example.wordsbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.wordsbook.DB.WordsDB;
import com.example.wordsbook.Fragment.WordDetail_Fragment;
import com.example.wordsbook.Fragment.WordItem_Fragment;
import com.example.wordsbook.ui.main.Words;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements WordDetail_Fragment.OnFragmentInteractionListener, WordItem_Fragment.OnFragmentInteractionListener {

    private static final String TAG = "Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // 这条表示加载菜单文件，第一个参数表示通过那个资源文件来创建菜单
        // 第二个表示将菜单传入那个对象中。这里我们用Menu传入menu
        // 这条语句一般系统帮我们创建好
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    // 菜单的监听方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert:
                InsertDialog();
                break;
            case R.id.action_search:
                SearchDialog();
                break;
            case R.id.action_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Help");
                builder.setMessage("这是帮助键！");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            default:
                break;
        }
        return true;

    }


    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchword = ((EditText)tableLayout.findViewById(R.id.searchword)).getText().toString();
                        //单词已经插入到数据库，更新显示列表
                        RefreshWordItemFragment(searchword);
                    }
                })
        //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .create()//创建对话框
                .show();//显示对话框

    }

    /**
     * 更新单词列表
     */
    private void RefreshWordItemFragment() {

        ((WordItem_Fragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.wordslist))).refreshWordsList();

    }

    private void RefreshWordItemFragment(String strWord){
        ((WordItem_Fragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.wordslist))).refreshWordsList(strWord);
    }


    //新增对话框
    private void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.add_word, null);
        new AlertDialog.Builder(this)
                .setTitle("新增单词")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strWord = ((EditText) tableLayout.findViewById(R.id.addword)).getText().toString();
                        String strMeaning = ((EditText) tableLayout.findViewById(R.id.addmeaning)).getText().toString();
                        String strSample = ((EditText) tableLayout.findViewById(R.id.addsample)).getText().toString();
                        WordsDB wordsDB=WordsDB.getWordsDB();
                        wordsDB.Insert(strWord, strMeaning, strSample);
                        RefreshWordItemFragment();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

    /**
     * 当用户在单词列表Fragment中单击某个单词时回调此函数
     * 判断如果横屏的话，则需要在右侧单词详细Fragment中显示
     */
    @Override
    public void onWordItemClick(String id) {

        if(isLand()) {//横屏的话则在右侧的WordDetailFragment中显示单词详细信息
            ChangeWordDetailFragment(id);
        }else{
            Intent intent = new Intent(MainActivity.this,WordDetailActivity.class);
            intent.putExtra(WordDetail_Fragment.ARG_ID, id);
            startActivity(intent);
        }

    }
    //是否是横屏
    private boolean isLand(){

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    private void ChangeWordDetailFragment(String id){
        Bundle arguments = new Bundle();
        arguments.putString(WordDetail_Fragment.ARG_ID, id);
        Log.v(TAG, id);

        WordDetail_Fragment fragment = new WordDetail_Fragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().replace(R.id.worddetail, fragment).commit();
    }

    @Override
    public void onDeleteDialog(String strId) {
        DeleteDialog(strId);
    }

    private void DeleteDialog(final String strId) {
        new AlertDialog.Builder(this).setTitle("删除单词").setMessage("是否真的删除单词?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WordsDB wordsDB=WordsDB.getWordsDB();
                wordsDB.DeleteUseSql(strId);

                //单词已经删除，更新显示列表
                RefreshWordItemFragment();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    @Override
    public void onUpdateDialog(String strId) {
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null && strId != null) {


            Words.WordDecription item = wordsDB.getSingleWord(strId);
            if (item != null) {
                UpdateDialog(strId, item.word, item.meaning, item.sample);
            }

        }

    }

    private void UpdateDialog(final String strId,final String word,final String meaning,final String sample) {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.add_word, null);
        ((EditText) tableLayout.findViewById(R.id.addword)).setText(word);
        ((EditText) tableLayout.findViewById(R.id.addmeaning)).setText(meaning);
        ((EditText) tableLayout.findViewById(R.id.addsample)).setText(sample);
        new AlertDialog.Builder(this)
                .setTitle("修改单词")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewWord = ((EditText) tableLayout.findViewById(R.id.addword)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.addmeaning)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.addsample)).getText().toString();

                        WordsDB wordsDB=WordsDB.getWordsDB();
                        wordsDB.UpdateUseSql(strId, strNewWord, strNewMeaning, strNewSample);

                        //单词已经更新，更新显示列表
                        RefreshWordItemFragment();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WordsDB wordsDB=WordsDB.getWordsDB();
        if (wordsDB != null)
            wordsDB.close();

    }

    @Override
    public void onWordDetailClick(Uri uri) {

    }
}