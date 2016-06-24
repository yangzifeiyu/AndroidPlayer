package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Schedule.ScheduleDataProvider;

import java.util.ArrayList;
import java.util.List;


public class ScheduleListDataAdapter extends ArrayAdapter {
    List list = new ArrayList();


    public ScheduleListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView ScheduleId, scheName, scheIdleItem, schePlayMode;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.schedule_row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.ScheduleId = (TextView) row.findViewById(R.id.text_schedule_id);
            layoutHandler.scheName = (TextView) row.findViewById(R.id.text_schedule_name);
            layoutHandler.scheIdleItem = (TextView) row.findViewById(R.id.text_schedule_item);
            layoutHandler.schePlayMode = (TextView) row.findViewById(R.id.text_schedule_playmode);
            row.setTag(layoutHandler);

        } else {
            layoutHandler = (LayoutHandler) row.getTag();


        }
        ScheduleDataProvider dataProvider = (ScheduleDataProvider) this.getItem(position);
        layoutHandler.ScheduleId.setText(dataProvider.getScheduleId());
        layoutHandler.scheName.setText(dataProvider.getScheName());
        layoutHandler.scheIdleItem.setText(dataProvider.getScheIdleItem());
        layoutHandler.schePlayMode.setText(dataProvider.getSchePlayMode());


        return row;
    }
}
