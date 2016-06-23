package com.example.mfusion.Template;

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

public class TemplateDB extends Activity {
    EditText temp1, temp2, temp3, temp4, temp5, temp6;
    Context context = this;
    DBController dbController;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_db_layout);


        temp1 = (EditText) findViewById(R.id.temp1);
        temp2 = (EditText) findViewById(R.id.temp2);
        temp3 = (EditText) findViewById(R.id.temp3);
        temp4 = (EditText) findViewById(R.id.temp4);
        temp5 = (EditText) findViewById(R.id.temp5);
        temp6 = (EditText) findViewById(R.id.temp6);

    }

    public void InsertTemp(View view) {
        String tempId = temp1.getText().toString();
        String tempName = temp2.getText().toString();
        String tempWidth = temp3.getText().toString();
        String tempHeight = temp4.getText().toString();
        String tempBackColor = temp5.getText().toString();
        String tempBackImage = temp6.getText().toString();


        dbController = new DBController(context, "", null, 1);
        sqLiteDatabase = dbController.getWritableDatabase();
        dbController.InsertTemp(tempId,tempName,tempWidth,tempHeight,tempBackColor,tempBackImage, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        dbController.close();
    }

    public void viewTemplate(View view){
        Intent intent2 = new Intent(this,TemplateDataListActivity.class);
        startActivity(intent2);
    }


}
