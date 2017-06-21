package com.example.msi.grab;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by MSI on 2017-04-19.
 */

public class MonthItemView extends AppCompatTextView {

    private MonthItem item;

    public MonthItemView(Context context) {
        super(context);
        init();
    }

    public MonthItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        setBackgroundColor(Color.WHITE);
    }

    public MonthItem getItem() {
        return item;
    }

    public void setItem(MonthItem item) {
        this.item = item;

        int day = item.getDay();
        if (day!= 0) {
            setText(String.valueOf(day));
        } else {
            setText("");
        }
    }

//    public String getTextActCnt(){
//        return this.textActCnt;
//    }
//    /**
//     * 부가 설명에 표시할 글 입력
//     * @param string
//     */
//    public void setTextActCnt(String string){
//        this.textActCnt = string;
//    }

}
