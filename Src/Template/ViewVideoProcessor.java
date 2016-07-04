package com.example.mfusion.Template;

import android.content.Context;
import android.widget.VideoView;

import com.example.mfusion.model.TemplateComponent;


public class ViewVideoProcessor extends ViewProcessor {
    public ViewVideoProcessor(Context context, TemplateComponent component) {
        super(context, component);
    }

    public VideoView generateView(){
        VideoView view=new VideoView(context);
        view.setLayoutParams(getParas());
        view.setVideoURI(component.getSourceUri());
        updateViewMap(view);
        view.start();
        return view;
    }
}
