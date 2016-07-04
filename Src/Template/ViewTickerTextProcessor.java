package com.example.mfusion.Template;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.mfusion.model.TemplateComponent;


public class ViewTickerTextProcessor extends ViewProcessor {


    public ViewTickerTextProcessor(Context context, TemplateComponent component) {
        super(context, component);
    }

    public TextView generateView(){
        TextView textView=new TextView(context);
        textView.setLayoutParams(getParas());
        textView.setText(component.getSourceText());

        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setSelected(true);
        textView.setSingleLine(true);
        updateViewMap(textView);
        return textView;
    }


}
