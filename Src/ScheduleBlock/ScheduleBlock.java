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

public class ScheduleBlock extends Activity {

    EditText SchB1, SchB2, SchB3, SchB4,SchB5,SchB6,SchB7,SchB8;
    Context context = this;
    DBController dbController;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_block_layout);

        SchB1 = (EditText) findViewById(R.id.SchB1);
        SchB2 = (EditText) findViewById(R.id.SchB2);
        SchB3 = (EditText) findViewById(R.id.SchB3);
        SchB4 = (EditText) findViewById(R.id.SchB4);

        SchB5 = (EditText) findViewById(R.id.SchB5);
        SchB6 = (EditText) findViewById(R.id.SchB6);
        SchB7 = (EditText) findViewById(R.id.SchB7);
        SchB8 = (EditText) findViewById(R.id.SchB8);


    }

    public void InsertScheBlock(View view) {
        String BlockId =  SchB1.getText().toString();
        String scheId =  SchB2.getText().toString();
        String bType=  SchB3.getText().toString();
        String bStartDate =  SchB4.getText().toString();
        String bEndDate =  SchB5.getText().toString();
        String bStartTime =  SchB6.getText().toString();
        String bEndTime =  SchB7.getText().toString();
        String bRecurrence =  SchB8.getText().toString();


        dbController= new DBController(context, "", null, 1);
        sqLiteDatabase = dbController.getWritableDatabase();
        dbController.InsertScheBlock(BlockId, scheId, bType, bStartDate,bEndDate,bStartTime,bEndTime,bRecurrence,sqLiteDatabase);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        dbController.close();
    }


    public void viewScheduleBlock(View view){
        Intent intent1 = new Intent(this,ScheduleBlockListActivity.class);
        startActivity(intent1);
    }

}
