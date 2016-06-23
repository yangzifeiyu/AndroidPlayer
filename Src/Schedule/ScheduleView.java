package com.example.mfusion.Schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mfusion.model.ScheduleComponent;

import java.util.ArrayList;


/**
 * Created by JCYYYY on 2016/6/20.
 */
public class ScheduleView extends View{

    private Context context;

    private int viewWidth;
    private int viewHeight;

    private int areaClicked;

    private ArrayList<ScheduleComponent> components;

    private Paint myPaint;
    private Paint textPaint;

    private final String TAG="ScheduleView";

    public ScheduleView(Context context) {
        super(context);
    }

    public ScheduleView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        myPaint = new Paint();
        myPaint.setColor(Color.BLACK);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(3f);


        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50f);

        components = new ArrayList<>();

        this.context = context;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        viewWidth=getWidth();
        viewHeight=getHeight();
    }

    protected void onDraw(Canvas canvas) {

        for (ScheduleComponent component : components) {
            component.setParentWidth(viewWidth);
            component.setParentHeight(viewHeight);
            canvas.drawRect(component.getResolvedLeft(), component.getResolvedTop(), component.getResolvedRight(), component.getResolvedBottom(), myPaint);

        }


    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

    }

    private int whichArea(float x, float y) {
        for (int i = 0; i < components.size(); i++) {
            ScheduleComponent current = components.get(i);
            float left = current.getResolvedLeft();
            float top = current.getResolvedTop();
            float right = current.getResolvedRight();
            float bottom = current.getResolvedBottom();
            if (x > left && x < right && y > top && y < bottom)
                return i;
        }
        return -1;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "onTouchEvent: touched area="+whichArea(event.getX(),event.getY()));
        areaClicked=whichArea(event.getX(),event.getY());
        return super.onTouchEvent(event);

    }

    public int getAreaClicked(){
        return areaClicked;
    }

//    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
}
