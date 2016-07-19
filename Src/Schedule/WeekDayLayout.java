package com.example.mfusion.Schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mfusion.R;

/**
 * Created by MIN RUI on 7/17/2016.
 */

public class  WeekDayLayout extends RelativeLayout {
    private TextView weekday_title;
    private TextView weekday_time;
    public WeekDayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.weekday_layout, this);
        weekday_title = (TextView) findViewById(R.id.weekday_name);
        weekday_time = (TextView) findViewById(R.id.weekday_time);
    }

    public void SetTitle(String str) {
        weekday_title.setText(str);
    }

    public void SetDragListener() {
        MyDragListener dragListener = new MyDragListener();
        weekday_time.setOnDragListener(dragListener);
    }

    public void SetOnclickListener() {
        MyOnClickListener onClickListener=new MyOnClickListener();
        weekday_time.setOnClickListener(onClickListener);
    }
}
