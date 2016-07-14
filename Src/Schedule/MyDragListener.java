package com.example.mfusion.Schedule;

import android.content.ClipData;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

public class MyDragListener implements View.OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action=event.getAction();
        switch (action){
            case DragEvent.ACTION_DRAG_STARTED:
                v.setBackgroundColor(Color.BLUE);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(Color.GREEN);
                break;
            case DragEvent.ACTION_DROP:
                v.setBackgroundColor(Color.TRANSPARENT);


                ClipData data=event.getClipData();
                ClipData.Item item=data.getItemAt(0);
                String strData= String.valueOf(item.getText());
                TextView tv=(TextView)v;
                tv.setText(strData);


                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundColor(Color.BLUE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundColor(Color.TRANSPARENT);
                break;

        }
        return true;
    }
}


