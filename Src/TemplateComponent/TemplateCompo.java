package com.example.mfusion.TemplateComponent;

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

public class TemplateCompo extends Activity {
    EditText tem1, tem2, tem3, tem4, tem5, tem6, tem7, tem8, tem9, tem10, tem11;
    Context context = this;
    DBController dbController;
    SQLiteDatabase sqLiteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_compo_layout);


        tem1 = (EditText) findViewById(R.id.sche1);
        tem2 = (EditText) findViewById(R.id.sche2);
        tem3 = (EditText) findViewById(R.id.sche3);
        tem4 = (EditText) findViewById(R.id.sche4);
        tem5 = (EditText) findViewById(R.id.tem5);
        tem6 = (EditText) findViewById(R.id.tem6);
        tem7 = (EditText) findViewById(R.id.tem7);
        tem8 = (EditText) findViewById(R.id.tem8);
        tem9 = (EditText) findViewById(R.id.tem9);
        tem10 = (EditText) findViewById(R.id.tem10);
        tem11 = (EditText) findViewById(R.id.tem11);

    }






    public void addCompoinfo(View view) {
        String componentID = tem1.getText().toString();
        String tempId = tem2.getText().toString();
        String compName = tem3.getText().toString();
        String compType = tem4.getText().toString();
        String compX = tem5.getText().toString();
        String compY = tem6.getText().toString();
        String compWidth = tem7.getText().toString();
        String compHeight = tem8.getText().toString();
        String compZindex = tem9.getText().toString();
        String compBackColor = tem10.getText().toString();
        String compProperty = tem11.getText().toString();

        dbController = new DBController(context, "", null, 1);
        sqLiteDatabase = dbController.getWritableDatabase();
        dbController.addCompoinfo(componentID, tempId, compName, compType, compX, compY, compWidth, compHeight, compZindex, compBackColor, compProperty, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
        dbController.close();
    }

    public void viewTemplateCompo(View view){
        Intent intent4 = new Intent(this,TemplateCompoListActivity.class);
        startActivity(intent4);
    }


}