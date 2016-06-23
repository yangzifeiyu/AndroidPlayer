
package com.example.mfusion.ScheduleBlock;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;
import com.example.mfusion.adapter.ScheduleBlockListAdapter;

/**
 * Created by WANG on 6/14/2016.
 */

public class ScheduleBlockListActivity extends Activity {

    ListView listView1;
    SQLiteDatabase sqLiteDatabase1;
    DBController dbController;
    Cursor cursor1;
    ScheduleBlockListAdapter scheduleBlockListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_block_row_layout);

        listView1 = (ListView) findViewById(R.id.ScheduleBlock_listView);//scheduleBlock

        scheduleBlockListAdapter = new ScheduleBlockListAdapter(getApplicationContext(), R.layout.schedule_block_row_layout);

        listView1.setAdapter(scheduleBlockListAdapter);

        dbController = new DBController(getApplicationContext(), "", null, 1);
        sqLiteDatabase1 = dbController.getReadableDatabase();
        cursor1 = dbController.getScheduleBlock(sqLiteDatabase1);
        if (cursor1.moveToFirst()) {
            do {

                String blockId, scheId, bType, bStartDate, bEndDate, bStartTime, bEndTime, bRecurrence;

                blockId = cursor1.getString(0);
                scheId = cursor1.getString(1);
                bType = cursor1.getString(2);
                bStartDate = cursor1.getString(3);
                bEndDate = cursor1.getString(4);
                bStartTime = cursor1.getString(5);
                bEndTime = cursor1.getString(6);
                bRecurrence = cursor1.getString(7);

                ScheduleBlockDataProvider blockDataProvider = new ScheduleBlockDataProvider(blockId, scheId, bType, bStartDate, bEndDate, bStartTime, bEndTime, bRecurrence);
                scheduleBlockListAdapter.add(blockDataProvider);

            } while (cursor1.moveToNext());
        }


    }
}



