package com.example.mfusion.Schedule;

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

public class Schedule extends Activity {

    EditText sche1, sche2, sche3, sche4;
    Context context = this;
    DBController dbController;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);


        sche1 = (EditText) findViewById(R.id.sche1);
        sche2 = (EditText) findViewById(R.id.sche2);
        sche3 = (EditText) findViewById(R.id.sche3);
        sche4 = (EditText) findViewById(R.id.sche4);


    }




    public void addSche(View view) {
        String ScheduleId = sche1.getText().toString();
        String scheName = sche2.getText().toString();
        String scheIdleItem = sche3.getText().toString();
        String schePlayMode = sche4.getText().toString();


        dbController= new DBController(context, "", null, 1);
        sqLiteDatabase = dbController.getWritableDatabase();
        dbController.addSche(ScheduleId, scheName, scheIdleItem, schePlayMode, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        dbController.close();
    }

    public void viewSchedule(View view){
        Intent intent = new Intent(this,ScheduleDataListActivity.class);
        startActivity(intent);
    }













}

