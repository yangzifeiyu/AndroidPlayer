package com.example.mfusion.ScheduleBlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mfusion.Database.DBController;
import com.example.mfusion.R;

public class BlockItem extends Activity {
    EditText BI1, BI2, BI3;
    Context context = this;
    DBController dbController;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_item_layout);

        BI1 = (EditText) findViewById(R.id.BI1);
        BI2 = (EditText) findViewById(R.id.BI2);
        BI3 = (EditText) findViewById(R.id.BI3);


    }

    public void InsertBlockItem(View view) {
        String Id =  BI1.getText().toString();
        String blockId =  BI2.getText().toString();
        String ItemId =  BI3.getText().toString();


        dbController = new DBController(context, "", null, 1);
        sqLiteDatabase = dbController.getWritableDatabase();
        dbController.InsertBlockItem(Id, blockId, ItemId, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        dbController.close();
    }
    public void viewBlockItem(View view){
        Intent intent4 = new Intent(this,BlockItemListActivity.class);
        startActivity(intent4);
    }
}
