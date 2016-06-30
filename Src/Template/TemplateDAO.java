package com.example.mfusion.Template;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.mfusion.Database.DbIniter;
import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import java.util.ArrayList;


public class TemplateDAO {
    private static final String TAG = "TemplateDAO";

    private SQLiteDatabase db;
    private String sdPath;

    private Context context;

    public TemplateDAO(Context context) {
        this.context=context;
        sdPath= Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        db=SQLiteDatabase.openOrCreateDatabase(sdPath+ DbIniter.APP_FOLDER+ DbIniter.DB_NAME,null);
    }

    public  MyTemplate getMyTemplateById(int id) {
        String selectComponent = "select left,top,right,bottom,id from " + DbIniter.TABLE_NAME_COMPONENT + " where tid=" + id;
        Cursor result = db.rawQuery(selectComponent, null);
        ArrayList<TemplateComponent> componentList = new ArrayList<>();

        while (result.moveToNext()) {
            float left = result.getFloat(0);
            float top = result.getFloat(1);
            float right = result.getFloat(2);
            float bottom = result.getFloat(3);
            TemplateComponent currentComponent = new TemplateComponent(context,left, top, right, bottom);
            currentComponent.setTid(result.getInt(4));
            componentList.add(currentComponent);
        }
        MyTemplate myTemplate = new MyTemplate();
        myTemplate.setList(componentList);
        myTemplate.setId(id);
        return myTemplate;

    }//getTemplateByID

    public void updateMyTemplete(MyTemplate updatedTemplate) {
        ArrayList<TemplateComponent> components = updatedTemplate.getList();
        for (TemplateComponent component : components) {
            String uriStr = "";
            if (component.getSourceUri() != null)
                uriStr = component.getSourceUri().toString();
            String updateComponent = "update " + DbIniter.TABLE_NAME_COMPONENT + " set left= " + component.getLeft() + ",top= " + component.getTop() + ",right=" + component.getRight() + ",bottom= " + component.getBottom() + ",type=" + component.getType() + ",sourceUri= '" + uriStr + "',sourceText= '" + component.getSourceText() + "' where id=" + component.getTid();
            Log.i(TAG, "updateMyTemplete: update component sql=" + updateComponent);
            db.beginTransaction();
            db.execSQL(updateComponent);
            db.setTransactionSuccessful();
            db.endTransaction();
        }



    }// UpdateTemplate(component)

    public void AddMyTemplate(MyTemplate AddTemplate) {
        ArrayList<TemplateComponent> components = AddTemplate.getList();
        for (TemplateComponent component : components) {
            String uriStr = "";
            if (component.getSourceUri() != null)
                uriStr = component.getSourceUri().toString();
            String addComponent = "Insert " + DbIniter.TABLE_NAME_COMPONENT + " set left= " + component.getLeft() + ",top= " + component.getTop() + ",right=" + component.getRight() + ",bottom= " + component.getBottom() + ",type=" + component.getType() + ",sourceUri= '" + uriStr + "',sourceText= '" + component.getSourceText() + "' where id=" + component.getTid();
            Log.i(TAG, "AddMyTemplete: Insert component sql=" + addComponent);
            db.beginTransaction();
            db.execSQL(addComponent);
            db.setTransactionSuccessful();
            db.endTransaction();
        }



    }//AddTemplate( for now)

    public void DeleteMyTemplate(MyTemplate AddTemplate) {
        ArrayList<TemplateComponent> components = AddTemplate.getList();
        for (TemplateComponent component : components) {
            String uriStr = "";
            if (component.getSourceUri() != null)
                uriStr = component.getSourceUri().toString();
            String DeleteComponent = "D " + DbIniter.TABLE_NAME_COMPONENT + " set left= " + component.getLeft() + ",top= " + component.getTop() + ",right=" + component.getRight() + ",bottom= " + component.getBottom() + ",type=" + component.getType() + ",sourceUri= '" + uriStr + "',sourceText= '" + component.getSourceText() + "' where id=" + component.getTid();
            Log.i(TAG, "DeleteMyTemplete: Delete component sql=" + DeleteComponent);
            db.beginTransaction();
            db.execSQL(DeleteComponent);
            db.setTransactionSuccessful();
            db.endTransaction();
        }



    }//DeleteTemplate( for now)
}
