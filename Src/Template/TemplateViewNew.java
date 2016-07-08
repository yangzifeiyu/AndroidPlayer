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
    private final String lineSeparator=System.getProperty("line.separator");
    private static final int EDITING_LEFT=1;
    private static final int EDITING_TOP=2;
    private static final int EDITING_RIGHT=3;
    private static final int EDITING_BOTTOM=4;
    private static final int EDITING_CENTER=5;
    private static final int EDITING_INVALID=99;

    private static final int BOUNDARY_OFFSET=50;

    private static final int TOUCH_TIME_THRESHOLD=100;
    
    private Paint boundaryPaint;
    private Paint sizeValueTextPaint;
    private Paint boundaryPaintSelected;
    private Paint sizeValueOverlapBoxPaint;

    private static final float SIZE_VALUE_TEXT=50f;
    private static final float SIZE_VALUE_TEXT_SPACE=10f;



    private int areaTouched;
    private int areaSelectedIndex;
    private boolean areaSelected;

    private boolean fingerOnScreen;

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


        sizeValueTextPaint = new Paint();
        sizeValueTextPaint.setColor(Color.BLACK);
        sizeValueTextPaint.setTextSize(SIZE_VALUE_TEXT);

        boundaryPaintSelected=new Paint();
        boundaryPaintSelected.setColor(Color.RED);
        boundaryPaintSelected.setStyle(Paint.Style.STROKE);
        boundaryPaintSelected.setStrokeWidth(6f);

        sizeValueOverlapBoxPaint =new Paint();
        sizeValueOverlapBoxPaint.setColor(Color.parseColor("#00ccff"));




        areaSelectedIndex =-1;

        editMode=false;
        fingerOnScreen=false;

        this.context=context;
        components=new ArrayList<>();

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
                    fingerOnScreen=true;
                    //record shift paras for shifting action
                    if(areaSelectedIndex >=0){
                        recordShiftParasInPercentage(x,y);
                    }

                    //record action down time to check if the touch is longTouch or shortTouch
                    startTime =System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(areaSelectedIndex >=0&& areaSelected){

                        int editingBoundary=getEditingBoundary(x,y);
                        editComponent(editingBoundary,x,y);

                        Log.i(TAG, "onTouchEvent: action move editing boundary="+editingBoundary);
                    }

                    Log.i(TAG, "onTouchEvent: action move fired");
                    break;
                case MotionEvent.ACTION_UP:
                    fingerOnScreen=false;
                    //check long touch or short touch, if long touch, do not change selection state
                    //if short touch, change selection state
                    long endTime=System.currentTimeMillis();
                    touchTime =endTime- startTime;
                    Log.i(TAG, "onTouchEvent: touchTIme="+ touchTime);
                    if(touchTime <TOUCH_TIME_THRESHOLD)
                    {
                        //if it is a short touch toggle area selected boolean
                        areaSelected = !areaSelected;
                        //if this is a selection action, calculate area selected index and store
                        //if this is a deselection action, clear selected index
                        if(areaSelected)
                            areaSelectedIndex =whichArea(event.getX(),event.getY());
                        else
                            areaSelectedIndex=-1;
                    }

                    break;
            }




            invalidate();

            Log.i(TAG, "onTouchEvent: areaSelected="+ areaSelected);
            Log.i(TAG, "onTouchEvent: areaSelectedIndex="+ areaSelectedIndex);

            return true;
        }




        else
            return super.onTouchEvent(event);
    }
    public int getAreaSelectedIndex(){
        return areaSelectedIndex;
    }

    private void recordShiftParasInPercentage(float x,float y){
        TemplateComponent current=components.get(areaSelectedIndex);
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
        TemplateComponent editingComponent=components.get(areaSelectedIndex);


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
        TemplateComponent editingComponent=components.get(areaSelectedIndex);
        float left=editingComponent.getLeft()*viewWidth;
        float top=editingComponent.getTop()*viewHeight;
        float right=editingComponent.getRight()*viewWidth;
        float bottom=editingComponent.getBottom()*viewHeight;

        int editingBoundary=EDITING_INVALID;


        int centerCounter=0;

        //check editing top and bottom
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
            float left = current.getLeft()*viewWidth;
            float top = current.getTop()*viewHeight;
            float right = current.getRight()*viewWidth;
            float bottom = current.getBottom()*viewHeight;
            if (x > left && x < right && y > top && y < bottom)
                return i;
        }
        return -1;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width=widthSize;
        int height=heightSize;
        if(heightMode==MeasureSpec.UNSPECIFIED){
            height=width/16*9;
        }


        setMeasuredDimension(width,height);

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
        Log.i(TAG, "onDraw: areaSelectedIndex="+ areaSelectedIndex);


        //draw component rects
        for(int i=0;i<components.size();i++){
            TemplateComponent component=components.get(i);
            Paint currentPaint=boundaryPaint;
            if(areaSelected &&(i== areaSelectedIndex))
                currentPaint=boundaryPaintSelected;

            canvas.drawRect(component.getLeft()*viewWidth,component.getTop()*viewHeight,component.getRight()*viewWidth,component.getBottom()*viewHeight,currentPaint);
        }

        //draw size value box and value
        if(fingerOnScreen&&areaSelectedIndex>-1){
            TemplateComponent selectedComponent=components.get(areaSelectedIndex);

            float x=selectedComponent.getLeft()*viewWidth;
            float y=selectedComponent.getTop()*viewHeight;
            float width=(selectedComponent.getRight()-selectedComponent.getLeft())*viewWidth;
            float height=(selectedComponent.getBottom()-selectedComponent.getTop())*viewHeight;
            String valueText="X = "+x+lineSeparator+"Y = "+y+lineSeparator+"Width = "+width+lineSeparator+" Height = "+height;
            canvas.drawRect(50,50,550,350, sizeValueOverlapBoxPaint);
            canvas.drawText("X = "+x,70,100, sizeValueTextPaint);
            canvas.drawText("Y = "+y,70,100+SIZE_VALUE_TEXT+SIZE_VALUE_TEXT_SPACE,sizeValueTextPaint);
            canvas.drawText("Width = "+width,70,100+2*SIZE_VALUE_TEXT+2*SIZE_VALUE_TEXT_SPACE,sizeValueTextPaint);
            canvas.drawText("Height = "+height,70,100+3*SIZE_VALUE_TEXT+3*SIZE_VALUE_TEXT_SPACE,sizeValueTextPaint);
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
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

    public void setComponent(int id,TemplateComponent component){
        components.set(id,component);
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
    public boolean setSelectedComponentSize(float x,float y,float width,float height){
        if(areaSelectedIndex<0)
            return false;
        else{
            TemplateComponent selected=components.get(areaSelectedIndex);
            selected.setLeft(x/viewWidth);
            selected.setTop(y/viewHeight);
            selected.setRight(x/viewWidth+width/viewWidth);
            selected.setBottom(y/viewHeight+height/viewHeight);
            invalidate();
            return true;

        }
    }

    public void addNewComponent(){
        TemplateComponent newComponent=new TemplateComponent(context,0.25f,0.25f,0.75f,0.75f);
        components.add(newComponent);
        invalidate();
    }
    public boolean removeSelectedComponent(){
        if(areaSelectedIndex<0)
            return false;
        else{
            components.remove(areaSelectedIndex);
            areaSelectedIndex=-1;
            invalidate();
            return true;
        }

    }

    public ArrayList<TemplateComponent> getComponentList(){
        return components;
    }

}
