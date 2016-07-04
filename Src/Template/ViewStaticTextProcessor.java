package com.example.mfusion.Template;

import android.content.Context;
import android.widget.TextView;

import com.example.mfusion.model.TemplateComponent;

import org.w3c.dom.Text;


public class ViewStaticTextProcessor extends ViewProcessor {
    public ViewStaticTextProcessor(Context context, TemplateComponent component) {
        super(context, component);
    }
    public TextView generateView(){
        TextView view=new TextView(context);

        view.setLayoutParams(getParas());
        view.setText(component.getSourceText());
        updateViewMap(view);
        return view;
    }
}
