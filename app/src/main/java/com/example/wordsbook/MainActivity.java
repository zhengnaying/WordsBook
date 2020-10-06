package com.example.wordsbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.wordsbook.Fragment.WordDetail_Fragment;
import com.example.wordsbook.Fragment.WordItem_Fragment;

public class MainActivity extends AppCompatActivity implements WordDetail_Fragment.OnFragmentInteractionListener, WordItem_Fragment.OnFragmentInteractionListener {

    private static final String TAG = "Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


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

    private void RefreshWordItemFragment(String searchword) {
    }

    private void InsertDialog() {
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