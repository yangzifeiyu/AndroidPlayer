package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.TemplateComponent.TemplateCompoDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 6/15/2016.
 */
public class TemplateCompoListAdapter extends ArrayAdapter {
    List list3 = new ArrayList();


    public TemplateCompoListAdapter(Context context, int resource) { super(context, resource); }



    static  class LayoutHandler3{ TextView componentID, tempId, compName, compType, compX, compY, compWidth, compHeight, compZindex, compBackColor, compProperty; }
    @Override
    public void add(Object object) {
        super.add(object);
        list3.add(object);
    }

    @Override
    public int getCount() {
        return list3.size();

    }

    @Override
    public Object getItem(int position) {
        return list3.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row3 = convertView;
        LayoutHandler3 layoutHandler3;

        if(row3 == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row3 = layoutInflater.inflate(R.layout.template_compo_row_layout,parent,false);
            layoutHandler3 = new LayoutHandler3();
            layoutHandler3.componentID = (TextView)row3.findViewById(R.id.text_Component_ID);
            layoutHandler3.tempId = (TextView)row3.findViewById(R.id.text_temp_id);
            layoutHandler3.compName = (TextView)row3.findViewById(R.id.text_comp_name);
            layoutHandler3.compType = (TextView)row3.findViewById(R.id.text_comp_type);
            layoutHandler3.compX = (TextView)row3.findViewById(R.id.text_comp_x);
            layoutHandler3.compY = (TextView)row3.findViewById(R.id.text_comp_y);
            layoutHandler3.compWidth = (TextView)row3.findViewById(R.id.text_comp_width);
            layoutHandler3.compHeight = (TextView)row3.findViewById(R.id.text_comp_height);
            layoutHandler3.compZindex = (TextView)row3.findViewById(R.id.text_comp_zindex);
            layoutHandler3.compBackColor = (TextView)row3.findViewById(R.id.text_comp_back_color);
            layoutHandler3.compProperty = (TextView)row3.findViewById(R.id.text_comp_property);
            row3.setTag(layoutHandler3);

        }
        else {
            layoutHandler3 = (LayoutHandler3) row3.getTag();


        }



        TemplateCompoDataProvider templateCompoDataProvider = (TemplateCompoDataProvider) this.getItem(position);
        layoutHandler3.componentID.setText(templateCompoDataProvider.getComponentID());
        layoutHandler3.tempId.setText(templateCompoDataProvider.getTempId());
        layoutHandler3.compName.setText(templateCompoDataProvider.getCompName());
        layoutHandler3.compType.setText(templateCompoDataProvider.getCompType());
        layoutHandler3.compX.setText(templateCompoDataProvider.getCompX());
        layoutHandler3.compY.setText(templateCompoDataProvider.getCompY());
        layoutHandler3.compWidth.setText(templateCompoDataProvider.getCompWidth());
        layoutHandler3.compHeight.setText(templateCompoDataProvider.getCompHeight());
        layoutHandler3.compZindex.setText(templateCompoDataProvider.getCompZindex());
        layoutHandler3.compBackColor.setText(templateCompoDataProvider.getCompBackColor());
        layoutHandler3.compProperty.setText(templateCompoDataProvider.getCompProperty());

        return row3;
    }
}
