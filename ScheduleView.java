package com.example.mfusion.Schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class ScheduleView extends View {

        private Paint myPaint;
        private Paint textPaint;


    ScheduleView(Context context, AttributeSet attrs){
        super(context);
        myPaint = new Paint();
        myPaint.setColor(Color.BLACK);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(3f);

        textPaint=new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50f);

    }
    protected void onDraw(Canvas canvas){
        //super.onDraw(canvas);

        Rect myRect=new Rect();
        myRect.set(canvas.getWidth()/50,0,canvas.getWidth(),canvas.getHeight()/8);

        Paint col1=new Paint();
        col1.setColor(Color.BLUE);
        col1.setStyle(Paint.Style.STROKE);

        canvas.drawRect(myRect,col1);//block1


        Rect myRect2=new Rect();
        myRect2.set(canvas.getWidth()/50,canvas.getHeight()/8,canvas.getWidth()/15*4,canvas.getHeight());

        Paint col2=new Paint();
        col2.setColor(Color.BLUE);
        col2.setStyle(Paint.Style.STROKE);

        canvas.drawRect(myRect2,col2);//block2


        Rect myRect3=new Rect();
        myRect3.set(canvas.getWidth()/15*4,canvas.getHeight()/8,canvas.getWidth(),canvas.getHeight());

        Paint col3=new Paint();
        col3.setColor(Color.BLUE);
        col3.setStyle(Paint.Style.STROKE);

        canvas.drawRect(myRect3,col3);//block3
    }


}