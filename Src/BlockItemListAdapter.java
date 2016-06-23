package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.ScheduleBlock.BlockItemDataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 6/17/2016.
 */
public class BlockItemListAdapter extends ArrayAdapter {
    List list4 = new ArrayList();


    public BlockItemListAdapter(Context context, int resource) { super(context, resource); }



    static  class LayoutHandler4 { TextView Id, blockId, ItemId;}
    @Override
    public void add(Object object) {
        super.add(object);
        list4.add(object);
    }

    @Override
    public int getCount() {
        return list4.size();

    }

    @Override
    public Object getItem(int position) {
        return list4.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row4 = convertView;
        LayoutHandler4 layoutHandler4;

        if(row4 == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row4 = layoutInflater.inflate(R.layout.schedule_block_row_layout,parent,false);
            layoutHandler4 = new LayoutHandler4();
            layoutHandler4.Id = (TextView)row4.findViewById(R.id.text_Id);
            layoutHandler4.blockId = (TextView)row4.findViewById(R.id.text_block_id);
            layoutHandler4.ItemId = (TextView)row4.findViewById(R.id.text_Item_id);
            
            row4.setTag(layoutHandler4);

        }
        else {
            layoutHandler4 = (LayoutHandler4) row4.getTag();


        }



        BlockItemDataProvider blockItemDataProvider = (BlockItemDataProvider)this.getItem(position);
        layoutHandler4.Id.setText(blockItemDataProvider.getId());
        layoutHandler4.blockId.setText(blockItemDataProvider.getBlockId());
        layoutHandler4.ItemId.setText(blockItemDataProvider.getItemId());


        return row4;
    }












}
