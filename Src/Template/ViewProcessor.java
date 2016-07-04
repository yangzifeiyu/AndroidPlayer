package com.example.mfusion.Template;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mfusion.model.TemplateComponent;

import java.util.HashMap;


public class ViewProcessor {
    protected TemplateComponent component;
    protected Context context;
    private static HashMap<Integer,View> viewMap=new HashMap<>();

    public ViewProcessor(Context context,TemplateComponent component) {
        this.component = component;
        this.context=context;
    }

    protected FrameLayout.LayoutParams getParas(){
        int width=component.getResolvedRight()-component.getResolvedLeft();
        int height=component.getResolvedBottom()-component.getResolvedTop();
        FrameLayout.LayoutParams paras=new FrameLayout.LayoutParams(width,height);
        paras.setMargins(component.getResolvedLeft(),component.getResolvedTop(),component.getResolvedRight(),component.getResolvedBottom());
        return paras;
    }
    protected void updateViewMap(View view){
        int mapKey=component.getId();
        viewMap.put(mapKey,view);
    }

    /**
     * get view object at the same location
     * @return View, if view does not exist return null
     */
    public View getOldView(){
        View view=viewMap.get(component.getId());
        return view;
     }


}
