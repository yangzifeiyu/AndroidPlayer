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

import java.util.ArrayList;

import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

public class TemplateViewNew extends View {
    private static final String TAG = "TemplateViewNew";
    private static final int EDITING_LEFT=1;
    private static final int EDITING_TOP=2;
    private static final int EDITING_RIGHT=3;
    private static final int EDITING_BOTTOM=4;
    private static final int EDITING_CENTER=5;
    private static final int EDITING_INVALID=99;

    private static final int BOUNDARY_OFFSET=50;

    private static final int TOUCH_TIME_THRESHOLD=100;
    
    private Paint boundaryPaint;
    private Paint textPaint;
    private Paint boundaryPaintSelected;

    private int areaTouched;
    private int areaSelected;
    private boolean touched;

    private Context context;

    private ArrayList<TemplateComponent> components;

    private int viewWidth;
    private int viewHeight;



    private boolean editMode;
    private long touchTime;
    private long startTime;

    private float shiftParaXToLeft;
    private float shiftParaXToRight;
    private float shiftParaYToTop;
    private float shiftParaYToBottom;


    public TemplateViewNew(Context context) {
        super(context);
    }
    public int getAreaTouched(){return areaTouched;}

    public TemplateViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        boundaryPaint = new Paint();
        boundaryPaint.setColor(Color.BLACK);
        boundaryPaint.setStyle(Paint.Style.STROKE);
        boundaryPaint.setStrokeWidth(3f);


        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50f);

        boundaryPaintSelected=new Paint();
        boundaryPaintSelected.setColor(Color.RED);
        boundaryPaintSelected.setStyle(Paint.Style.STROKE);
        boundaryPaintSelected.setStrokeWidth(6f);

        areaSelected =-1;

        editMode=false;

        this.context=context;

    }

    public TemplateViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        areaTouched=whichArea(event.getX(),event.getY());

        if(editMode){
            int action=event.getAction();
            float x=event.getX();
            float y=event.getY();

            switch (action){
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "onTouchEvent: action down fired");
                    //record shift paras for shifting action
                    if(areaSelected >=0){
                        recordShiftParasInPercentage(x,y);
                    }

                    //record action down time to check if the touch is longTouch or shortTouch
                    startTime =System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(areaSelected >=0&&touched){

                        int editingBoundary=getEditingBoundary(x,y);
                        editComponent(editingBoundary,x,y);

                        Log.i(TAG, "onTouchEvent: action move editing boundary="+editingBoundary);
                    }

                    Log.i(TAG, "onTouchEvent: action move fired");
                    break;
                case MotionEvent.ACTION_UP:
                    //check long touch or short touch, if long touch, do not change selection state
                    //if short touch, change selection state
                    long endTime=System.currentTimeMillis();
                    touchTime =endTime- startTime;
                    Log.i(TAG, "onTouchEvent: touchTIme="+ touchTime);
                    if(touchTime <TOUCH_TIME_THRESHOLD)
                        touched = !touched;
                    areaSelected =whichArea(event.getX(),event.getY());
                    break;
            }




            invalidate();

            Log.i(TAG, "onTouchEvent: touched="+touched);
            Log.i(TAG, "onTouchEvent: areaSelected="+ areaSelected);

            return true;
        }




        else
            return super.onTouchEvent(event);
    }
    public int getAreaSelected(){
        return areaSelected;
    }

    private void recordShiftParasInPercentage(float x,float y){
        TemplateComponent current=components.get(areaSelected);
        float percentageX=x/viewWidth;
        float percentageY=y/viewHeight;
        float left=current.getLeft();
        float top=current.getTop();
        float right=current.getRight();
        float bottom=current.getBottom();

        shiftParaXToLeft=percentageX-left;
        shiftParaXToRight=right-percentageX;
        shiftParaYToTop=percentageY-top;
        shiftParaYToBottom=bottom-percentageY;
    }

    private void editComponent(int editingBoundary,float x,float y){
        float percentageX=x/viewWidth;
        float percentageY=y/viewHeight;
        TemplateComponent editingComponent=components.get(areaSelected);


        switch (editingBoundary){
            case EDITING_TOP:
                editingComponent.setTop(percentageY);
                break;
            case EDITING_BOTTOM:
                editingComponent.setBottom(percentageY);
                break;
            case EDITING_LEFT:
                editingComponent.setLeft(percentageX);
                break;
            case EDITING_RIGHT:
                editingComponent.setRight(percentageX);
                break;
            case EDITING_CENTER:
                editingComponent.setLeft(percentageX-shiftParaXToLeft);
                editingComponent.setTop(percentageY-shiftParaYToTop);
                editingComponent.setRight(percentageX+shiftParaXToRight);
                editingComponent.setBottom(percentageY+shiftParaYToBottom);
                break;
        }

    }



    private int getEditingBoundary(float x,float y){
        TemplateComponent editingComponent=components.get(areaSelected);
        float left=editingComponent.getLeft()*viewWidth;
        float top=editingComponent.getTop()*viewHeight;
        float right=editingComponent.getRight()*viewWidth;
        float bottom=editingComponent.getBottom()*viewHeight;

        int editingBoundary=EDITING_INVALID;


        int centerCounter=0;

        //check ediding top and bottom
        if(x>left+BOUNDARY_OFFSET&&x<right-BOUNDARY_OFFSET){
            if(y>top-BOUNDARY_OFFSET&&y<top+BOUNDARY_OFFSET)
                editingBoundary=EDITING_TOP;
            else if(y>bottom-BOUNDARY_OFFSET&&y<bottom+BOUNDARY_OFFSET)
                editingBoundary=EDITING_BOTTOM;
            centerCounter++;
        }

        //check editing left and right
        if(y>top+BOUNDARY_OFFSET&&y<bottom-BOUNDARY_OFFSET){
            if(x>left-BOUNDARY_OFFSET&&x<left+BOUNDARY_OFFSET)
                editingBoundary=EDITING_LEFT;
            else if(x>right-BOUNDARY_OFFSET&&x<right+BOUNDARY_OFFSET)
                editingBoundary=EDITING_RIGHT;
            centerCounter++;
        }

        if(centerCounter==2)
            editingBoundary=EDITING_CENTER;

        return editingBoundary;


    }



    public void setTemplate(MyTemplate template){
        components=template.getList();
        
        requestLayout();
        invalidate();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: fired");
        viewWidth=w;
        viewHeight=h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: fired");
        Log.i(TAG, "onDraw: areaSelected="+ areaSelected);


        for(int i=0;i<components.size();i++){
            TemplateComponent component=components.get(i);
            Paint currentPaint=boundaryPaint;
            if(touched&&(i== areaSelected))
                currentPaint=boundaryPaintSelected;

            canvas.drawRect(component.getLeft()*viewWidth,component.getTop()*viewHeight,component.getRight()*viewWidth,component.getBottom()*viewHeight,currentPaint);
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setComponentDetail(int position, int type, Uri srcUri, String srcStr){
        TemplateComponent current=components.get(position);
        current.setType(type);
        current.setSourceUri(srcUri);
        current.setSourceText(srcStr);
        //components.set(position,current);
        invalidate();
        requestLayout();
    }

    /**
     *
     * @param id
     * @return a template component whose parent size has been set
     */
    public TemplateComponent getComponentById(int id){
        TemplateComponent current=components.get(id);
        current.setParentWidth(viewWidth);
        current.setParentHeight(viewHeight);
        return current;
    }

    public ArrayList<TemplateComponent> getComponentList(){
        return components;
    }

}
