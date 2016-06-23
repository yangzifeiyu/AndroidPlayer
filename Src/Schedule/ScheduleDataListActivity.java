package com.example.mfusion.Schedule;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;
import com.example.mfusion.adapter.ScheduleListDataAdapter;

public class ScheduleDataListActivity extends Activity {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DBController dbController;
    Cursor cursor;
    ScheduleListDataAdapter scheduleListDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_data_list_layout);
        listView = (ListView) findViewById(R.id.list_view);//schedule

        scheduleListDataAdapter = new ScheduleListDataAdapter(getApplicationContext(), R.layout.schedule_row_layout);

        listView.setAdapter(scheduleListDataAdapter);





        dbController = new DBController(getApplicationContext(), "", null, 1);
        sqLiteDatabase = dbController.getReadableDatabase();
        cursor = dbController.getSchedule(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String ScheduleId, scheName, scheIdleItem, schePlayMode;
                ScheduleId = cursor.getString(0);
                scheName = cursor.getString(1);
                scheIdleItem = cursor.getString(2);
                schePlayMode = cursor.getString(3);
                ScheduleDataProvider dataProvider = new ScheduleDataProvider(ScheduleId, scheName, scheIdleItem, schePlayMode);
                scheduleListDataAdapter.add(dataProvider);

            } while (cursor.moveToNext());
        }


    }
}
