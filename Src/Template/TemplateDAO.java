package com.example.mfusion.Template;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.mfusion.Database.DbIniter;
import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import java.util.ArrayList;

/**
 * Created by JCYYYY on 2016/5/31.
 */
public class TemplateDAO {

    private SQLiteDatabase db;
    private String sdPath;

    private Context context;

    public TemplateDAO(Context context) {
        this.context=context;
        sdPath= Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        db=SQLiteDatabase.openOrCreateDatabase(sdPath+DbIniter.APP_FOLDER+ DbIniter.DB_NAME,null);
    }

    public MyTemplate getMyTemplateById(int id) {
        String selectComponent = "select left,top,right,bottom from " + DbIniter.TABLE_NAME_COMPONENT + " where tid=" + id;
        Cursor result = db.rawQuery(selectComponent, null);
        ArrayList<TemplateComponent> componentList = new ArrayList<>();

        while (result.moveToNext()) {
            float left = result.getFloat(0);
            float top = result.getFloat(1);
            float right = result.getFloat(2);
            float bottom = result.getFloat(3);
            TemplateComponent currentComponent = new TemplateComponent(context,left, top, right, bottom);
            componentList.add(currentComponent);
        }
        MyTemplate myTemplate = new MyTemplate();
        myTemplate.setList(componentList);
        myTemplate.setId(id);
        return myTemplate;
    }

}
