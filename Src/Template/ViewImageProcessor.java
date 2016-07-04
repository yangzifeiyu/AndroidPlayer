package com.example.mfusion.Template;

import android.content.Context;
import android.widget.ImageView;

import com.example.mfusion.model.TemplateComponent;


public class ViewImageProcessor extends ViewProcessor {


    public ViewImageProcessor(Context context, TemplateComponent component) {
        super(context, component);
    }

    public ImageView generateImageView(){
        ImageView view=new ImageView(context);
        view.setLayoutParams(getParas());
        view.setImageURI(component.getSourceUri());
        updateViewMap(view);
        return view;
    }
}
