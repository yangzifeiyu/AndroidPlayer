package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.ScheduleBlock.ScheduleBlockDataProvider;

import java.util.ArrayList;
import java.util.List;


public class ScheduleBlockListAdapter extends ArrayAdapter {
    List list1 = new ArrayList();


    public ScheduleBlockListAdapter(Context context, int resource) { super(context, resource); }



    static  class LayoutHandler1{ TextView blockId, scheId, bType, bStartDate, bEndDate, bStartTime, bEndTime, bRecurrence; }
    @Override
    public void add(Object object) {
        super.add(object);
        list1.add(object);
    }

    @Override
    public int getCount() {
        return list1.size();

    }

    @Override
    public Object getItem(int position) {
        return list1.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row1 = convertView;
        LayoutHandler1 layoutHandler1;

        if(row1 == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row1 = layoutInflater.inflate(R.layout.schedule_block_row_layout,parent,false);
            layoutHandler1 = new LayoutHandler1();
            layoutHandler1.blockId = (TextView)row1.findViewById(R.id.text_Block_Id);
            layoutHandler1.scheId = (TextView)row1.findViewById(R.id.text_sche_id);
            layoutHandler1.bType = (TextView)row1.findViewById(R.id.text_b_type);
            layoutHandler1.bStartDate = (TextView)row1.findViewById(R.id.text_b_start_date);
            layoutHandler1.bEndDate = (TextView)row1.findViewById(R.id.text_b_end_date);
            layoutHandler1.bStartTime = (TextView)row1.findViewById(R.id.text_b_start_time);
            layoutHandler1.bEndTime = (TextView)row1.findViewById(R.id.text_b_end_time);
            layoutHandler1.bRecurrence = (TextView)row1.findViewById(R.id.text_b_recurrence);
            row1.setTag(layoutHandler1);

        }
        else {
            layoutHandler1 = (LayoutHandler1) row1.getTag();


        }



        ScheduleBlockDataProvider blockDataProvider = (ScheduleBlockDataProvider)this.getItem(position);
        layoutHandler1.blockId.setText(blockDataProvider.getblockId());
        layoutHandler1.scheId.setText(blockDataProvider.getScheId());
        layoutHandler1.bType.setText(blockDataProvider.getType());
        layoutHandler1.bStartDate.setText(blockDataProvider.getStartDate());
        layoutHandler1.bEndDate.setText(blockDataProvider.getEndDate());
        layoutHandler1.bStartTime.setText(blockDataProvider.getStartTime());
        layoutHandler1.bEndTime.setText(blockDataProvider.getEndTime());
        layoutHandler1.bRecurrence.setText(blockDataProvider.getRecurrence());

        return row1;
    }
}
