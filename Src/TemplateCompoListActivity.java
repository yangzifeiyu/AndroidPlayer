package com.example.mfusion.TemplateComponent;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;
import com.example.mfusion.adapter.TemplateCompoListAdapter;

public class TemplateCompoListActivity extends Activity {

    ListView listView3;
    SQLiteDatabase sqLiteDatabase3;
    DBController dbController;
    Cursor cursor3;
    TemplateCompoListAdapter templateCompoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_compo_list_layout);

        listView3 = (ListView) findViewById(R.id.TemplateCompo_listView);//scheduleBlock

        templateCompoListAdapter = new TemplateCompoListAdapter(getApplicationContext(), R.layout.template_compo_row_layout);

        listView3.setAdapter(templateCompoListAdapter);

        dbController = new DBController(getApplicationContext(), "", null, 1);
        sqLiteDatabase3 = dbController.getReadableDatabase();
        cursor3 = dbController.getTemplateCompo(sqLiteDatabase3);
        if (cursor3.moveToFirst()) {
            do {

                String componentID, tempID, compName, compType, compX, compY, compWidth, compHeight, compZindex, compBackColor, compProperty;

                componentID = cursor3.getString(0);
                tempID = cursor3.getString(1);
                compName = cursor3.getString(2);
                compType = cursor3.getString(3);
                compX = cursor3.getString(4);
                compY = cursor3.getString(5);
                compWidth = cursor3.getString(6);
                compHeight = cursor3.getString(7);
                compZindex = cursor3.getString(8);
                compBackColor = cursor3.getString(9);
                compProperty = cursor3.getString(10);


                TemplateCompoDataProvider templateCompoDataProvider = new TemplateCompoDataProvider(componentID, tempID, compName, compType, compX, compY, compWidth, compHeight, compZindex, compBackColor, compProperty);
                templateCompoListAdapter.add(templateCompoDataProvider);

            } while (cursor3.moveToNext());
        }


    }
}



