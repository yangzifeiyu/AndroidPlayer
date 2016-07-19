package com.example.mfusion.Schedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by MIN RUI on 18/7/2016.
 */
public class MyOnClickListener implements View.OnClickListener{


    @Override
    public void onClick(View v) {

        AlertDialog.Builder setSizeDialog=new AlertDialog.Builder(v.getContext());
        setSizeDialog.setTitle("Set Play Duration");
//        LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View dialogView=inflater.inflate(R.layout.edit_play_duration_dialog_view,null);
//        setSizeDialog.setView(dialogView);
        setSizeDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        setSizeDialog.show();
//        Toast.makeText(v.getContext(),"Edit time", Toast.LENGTH_LONG).show();



    }

}
