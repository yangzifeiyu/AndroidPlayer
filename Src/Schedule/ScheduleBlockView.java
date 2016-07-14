package com.example.mfusion.Schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ScheduleBlockView extends View {

    public ScheduleBlockView(Context context) {
        super(context);
    }

    public ScheduleBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Rect myRect1 = new Rect();
        myRect1.set(0, 0, canvas.getWidth(), canvas.getHeight() / 8);

        Paint col1 = new Paint();
        col1.setColor(Color.BLACK);
        col1.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Timeline",0,(canvas.getHeight()/8)-10,col1);

        canvas.drawRect(myRect1, col1);//Timeline


        Rect myRect2 = new Rect();
        myRect2.set(0, canvas.getHeight() / 8, canvas.getWidth(), canvas.getHeight() / 4);

        Paint col2 = new Paint();
        col2.setColor(Color.BLACK);
        col2.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Monday",0,(canvas.getHeight()/4)-10,col1);

        canvas.drawRect(myRect2, col2);//blockMon


        Rect myRect3 = new Rect();
        myRect3.set(0, canvas.getHeight() / 4, canvas.getWidth(), canvas.getHeight() / 8 * 3);

        Paint col3 = new Paint();
        col3.setColor(Color.BLACK);
        col3.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Tuesday",0,(canvas.getHeight()/8*3)-10,col1);

        canvas.drawRect(myRect3, col3);//blockTue


        Rect myRect4 = new Rect();
        myRect4.set(0, canvas.getHeight() / 8 * 3, canvas.getWidth(), canvas.getHeight() / 2);

        Paint col4 = new Paint();
        col4.setColor(Color.BLACK);
        col4.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Wednesday",0,(canvas.getHeight()/2)-10,col1);

        canvas.drawRect(myRect4, col4);//blockWed


        Rect myRect5 = new Rect();
        myRect5.set(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 8 * 5);

        Paint col5 = new Paint();
        col5.setColor(Color.BLACK);
        col5.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Thursday",0,(canvas.getHeight()/8*5)-10,col1);

        canvas.drawRect(myRect5, col5);//blockThu


        Rect myRect6 = new Rect();
        myRect6.set(0, canvas.getHeight() / 8 * 5, canvas.getWidth(), canvas.getHeight() / 4 * 3);

        Paint col6 = new Paint();
        col6.setColor(Color.BLACK);
        col6.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Friday",0,(canvas.getHeight()/4*3)-10,col1);

        canvas.drawRect(myRect6, col6);//blockFri


        Rect myRect7 = new Rect();
        myRect7.set(0, canvas.getHeight() / 4 * 3, canvas.getWidth(), canvas.getHeight() / 8 * 7);

        Paint col7 = new Paint();
        col7.setColor(Color.BLACK);
        col7.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Saturdy",0,(canvas.getHeight()/8*7)-10,col1);

        canvas.drawRect(myRect7, col7);//blockSat


        Rect myRect8 = new Rect();
        myRect8.set(0, canvas.getHeight() / 8 * 7, canvas.getWidth(), canvas.getHeight());

        Paint col8 = new Paint();
        col8.setColor(Color.BLACK);
        col8.setStyle(Paint.Style.STROKE);
        col1.setTextSize(30);
        canvas.drawText("Sunday",0,canvas.getHeight()-10,col1);

        canvas.drawRect(myRect8, col8);//blockSun
    }

}


