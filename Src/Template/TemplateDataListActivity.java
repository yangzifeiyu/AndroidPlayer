package com.example.mfusion.Template;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;
import com.example.mfusion.adapter.TemplateListDataAdapter;

public class TemplateDataListActivity extends Activity {
    ListView listView2;
    SQLiteDatabase sqLiteDatabase2;
    DBController dbController;
    Cursor cursor2;
    TemplateListDataAdapter templateListDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_data_list_layout);

        listView2 = (ListView) findViewById(R.id.Template_listView);//Template

        templateListDataAdapter = new TemplateListDataAdapter(getApplicationContext(), R.layout.template_row_layout);

        listView2.setAdapter(templateListDataAdapter);


        dbController = new DBController(getApplicationContext(), "", null, 1);
        sqLiteDatabase2 = dbController.getReadableDatabase();
        cursor2 = dbController.getTemplate(sqLiteDatabase2);
        if (cursor2.moveToFirst()) {
            do {

                String tempId, tempName, tempWidth, tempHeight, tempBackColor, tempBackImage;
                tempId = cursor2.getString(0);
                tempName = cursor2.getString(1);
                tempWidth = cursor2.getString(2);
                tempHeight = cursor2.getString(3);
                tempBackColor = cursor2.getString(4);
                tempBackImage = cursor2.getString(5);
               TemplateDataProvider templateDataProvider = new TemplateDataProvider(tempId, tempName, tempWidth, tempHeight, tempBackColor, tempBackImage);
                templateListDataAdapter.add(templateDataProvider);

            } while (cursor2.moveToNext());
        }


    }
}
