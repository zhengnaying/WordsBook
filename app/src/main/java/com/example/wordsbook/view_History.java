package com.example.wordsbook;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class view_History extends ListView {
    public view_History(Context context) {
        super(context);
    }
    public view_History(Context context, AttributeSet attr){
        super(context,attr);
    }
    public view_History(Context context, AttributeSet attr,int def){
        super(context,attr,def);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
