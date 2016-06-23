package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 6/15/2016.
 */
public class TemplateListDataAdapter extends ArrayAdapter{

    List list2 = new ArrayList();

    public TemplateListDataAdapter(Context context, int resource) {super(context, resource);}


    static class LayoutHandler2 {
        TextView tempId, tempName, tempWidth, tempHeight, tempBackColor, tempBackImage;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list2.add(object);
    }

    @Override
    public int getCount() {
        return list2.size();

    }

    @Override
    public Object getItem(int position) {
        return list2.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row2 = convertView;
        LayoutHandler2 layoutHandler2;

        if (row2 == null) {
            LayoutInflater layoutInflater2 = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row2 = layoutInflater2.inflate(R.layout.template_row_layout, parent, false);
            layoutHandler2 = new LayoutHandler2();
            layoutHandler2.tempId = (TextView) row2.findViewById(R.id.text_Id);
            layoutHandler2.tempName = (TextView) row2.findViewById(R.id.text_temp_name);
            layoutHandler2.tempWidth = (TextView) row2.findViewById(R.id.text_temp_width);
            layoutHandler2.tempHeight = (TextView) row2.findViewById(R.id.text_temp_height);
            layoutHandler2.tempBackColor = (TextView) row2.findViewById(R.id.text_temp_back_image);
            layoutHandler2.tempBackImage = (TextView) row2.findViewById(R.id.text_temp_back_color);
            row2.setTag(layoutHandler2);

        } else {

            layoutHandler2 = (LayoutHandler2) row2.getTag();


        }


        TemplateDataProvider templateDataProvider = (TemplateDataProvider) this.getItem(position);

        layoutHandler2.tempId.setText(templateDataProvider.getTempId());
        layoutHandler2.tempName.setText(templateDataProvider.getTempName());
        layoutHandler2.tempWidth.setText(templateDataProvider.getTempWidth());
        layoutHandler2.tempHeight.setText(templateDataProvider.getTempHeight());
        layoutHandler2.tempBackColor.setText(templateDataProvider.getTempBackColor());
        layoutHandler2.tempBackImage.setText(templateDataProvider.getTempBackImage());

        return row2;
    }








}
