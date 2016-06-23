package com.example.mfusion.Template;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import java.util.ArrayList;

/**
 * Created by JCYYYY on 2016/5/25.
 */
public class TemplateView extends View {

    private Context context;

    private int viewWidth;
    private int viewHeight;

    private int areaClicked;

    private float touchX;
    private float touchY;


    private ArrayList<TemplateComponent> components;

    private Paint myPaint;
    private Paint textPaint;

    private final String TAG="TemplateView";


    public TemplateView(final Context context, AttributeSet attrs) {
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



//        this.setOnLongClickListener(new OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.i(TAG, "onLongClick: long click listener triggered");
//                Log.i(TAG, "onLongClick: area clicked="+whichArea(touchX,touchY));
//                Intent goShow=new Intent(context,ShowTemplateActivity.class);
//                context.startActivity(goShow);
//                return false;
//            }
//        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        viewWidth=getWidth();
        viewHeight=getHeight();
    }

    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        for (TemplateComponent component : components) {
            //component = getSizedComponent(component);
            component.setParentWidth(viewWidth);
            component.setParentHeight(viewHeight);
            canvas.drawRect(component.getResolvedLeft(), component.getResolvedTop(), component.getResolvedRight(), component.getResolvedBottom(), myPaint);
//            canvas.drawText(component.getSourceText(),component.getResolvedLeft()+70,component.getResolvedTop()+70,textPaint);
        }


    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;

    }

    private int whichArea(float x, float y) {
        for (int i = 0; i < components.size(); i++) {
            TemplateComponent current = components.get(i);
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


//    private TemplateComponent getSizedComponent(TemplateComponent component) {
//        //TemplateComponent sized=new TemplateComponent(component.getLeft() * viewWidth, component.getTop() * viewHeight, component.getRight() * viewWidth, component.getBottom() * viewHeight);
//        component.setLeft(component.getLeft() * viewWidth);
//        component.setTop(component.getTop() * viewHeight);
//        component.setRight(component.getRight() * viewWidth);
//        component.setBottom(component.getBottom() * viewHeight);
//        return component;
//    }

    public void addComponent(TemplateComponent component) {
        //component=getSizedComponent(component);

        components.add(component);
        invalidate();
        requestLayout();
    }

    public void clearComponent() {
        components.clear();
        invalidate();
        requestLayout();
    }

    public void addTemplate(MyTemplate template) {
        ArrayList<TemplateComponent> componentList = template.getList();
        for (TemplateComponent current : componentList)
        {
            //current=getSizedComponent(current);

            components.add(current);
        }
        invalidate();
        requestLayout();
    }

    public void setComponentDetail(int position,int type, Uri srcUri,String srcStr){
        TemplateComponent current=components.get(position);
        current.setType(type);
        current.setSourceUri(srcUri);
        current.setSourceText(srcStr);
        //components.set(position,current);
        invalidate();
        requestLayout();
    }

    public TemplateComponent getComponentById(int id){
        return components.get(id);
    }

    public ArrayList<TemplateComponent> getComponentList(){
        return components;
    }


}
