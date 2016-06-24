package com.example.mfusion.ScheduleBlock;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;
import com.example.mfusion.adapter.BlockItemListAdapter;

public class BlockItemListActivity extends Activity {
    ListView listView4;
    SQLiteDatabase sqLiteDatabase4;
    DBController dbController;
    Cursor cursor4;
    BlockItemListAdapter blockItemListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_item_list_layout);


        listView4 = (ListView) findViewById(R.id.BlockItem_listView);//scheduleBlock

        blockItemListAdapter = new BlockItemListAdapter(getApplicationContext(), R.layout.block_item_row_layout);

        listView4.setAdapter(blockItemListAdapter);

        dbController = new DBController(getApplicationContext(), "", null, 1);
        sqLiteDatabase4 = dbController.getReadableDatabase();
        cursor4 = dbController.getBlockItem(sqLiteDatabase4);
        if (cursor4.moveToFirst()) {
            do {

                String Id, blockId, ItemId;

                Id = cursor4.getString(0);
                blockId = cursor4.getString(1);
                ItemId = cursor4.getString(2);


                BlockItemDataProvider blockItemDataProvider = new BlockItemDataProvider(Id, blockId, ItemId);
                blockItemListAdapter.add(blockItemDataProvider);

            } while (cursor4.moveToNext());
        }


    }
}



