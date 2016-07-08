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
import java.util.List;

public class TemplateDAO {
    private static final String TAG = "TemplateDAO";

    private SQLiteDatabase db;
    private String sdPath;

    private Context context;

    public TemplateDAO(Context context) {
        this.context=context;
        sdPath= Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        db=SQLiteDatabase.openOrCreateDatabase(sdPath+ DbIniter.APP_FOLDER+ DbIniter.DB_NAME,null);
    }  // storage path

    public MyTemplate getMyTemplateById(int id) {
        String selectComponent = "select left,top,right,bottom,tid,id from " + DbIniter.TABLE_NAME_COMPONENT + " where tid=" + id;
        Cursor result = db.rawQuery(selectComponent, null);
        ArrayList<TemplateComponent> componentList = new ArrayList<>();

        while (result.moveToNext()) {
            float left = result.getFloat(0);
            float top = result.getFloat(1);
            float right = result.getFloat(2);
            float bottom = result.getFloat(3);
            TemplateComponent currentComponent = new TemplateComponent(context,left, top, right, bottom);
            currentComponent.setTid(result.getInt(4));
            currentComponent.setId(result.getInt(5));
            componentList.add(currentComponent);
        }  //getTemplateById()，int id
       
        
        
        String selectTemplateNameSql="select name from "+DbIniter.TABLE_NAME_TEMPLATE+" where id="+id;//select statement
        Cursor nameResult=db.rawQuery(selectTemplateNameSql,null);
        nameResult.moveToLast();
        String templateName=nameResult.getString(0);

        MyTemplate myTemplate = new MyTemplate();
        myTemplate.setList(componentList);
        myTemplate.setId(id);
        myTemplate.setName(templateName);
        return myTemplate;

    }
    public List<MyTemplate> getAllTemplate(){
        int largestId=getLargestTemplateId();
        List<MyTemplate> list=new ArrayList<>();
        for(int i=1;i<=largestId;i++){
            list.add(getMyTemplateById(i));
        }
        return list;
    }//list all template method

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
        }//update all template, UpdateTemplate()





    }

    public void saveTemplate(MyTemplate newTemplate){
        String sql="insert into "+DbIniter.TABLE_NAME_TEMPLATE+" values(null,'"+newTemplate.getName()+"')";
        Log.i(TAG, "saveTemplate: create new template sql="+sql);
        db.beginTransaction();
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
        int lastestId=getLargestTemplateId();
        List<TemplateComponent> components=newTemplate.getList();
        for(TemplateComponent current:components){
            String insertComponentSql="insert into "+DbIniter.TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,"+current.getLeft()+","+current.getTop()+","+current.getRight()+","+current.getBottom()+","+lastestId+")";//insert statement
            Log.i(TAG, "saveTemplate: insert component sql="+insertComponentSql);
            db.beginTransaction();
            db.execSQL(insertComponentSql);
            db.setTransactionSuccessful();
            db.endTransaction();
        }// for loop


    }// AddTemplate()
    
    private int getLargestTemplateId(){
        String sql="select id from "+DbIniter.TABLE_NAME_TEMPLATE+" order by id desc limit 1";
        Cursor result=db.rawQuery(sql,null);
        result.moveToLast();
        int largestId=result.getInt(0);
        result.close();
        Log.i(TAG, "getLargestTemplateId: result="+largestId);
        return largestId;
    }



    public void createNewUserScreen(MyTemplate template){
        List<TemplateComponent> components=template.getList();
        String createScreenSql="insert into "+ DbIniter.TABLE_NAME_USER_SCREEN+" values(null,'"+template.getName()+"')";
        Log.i(TAG, "createNewUserScreen: createScreenSql="+createScreenSql);
        db.beginTransaction();
        db.execSQL(createScreenSql);
        db.setTransactionSuccessful();
        db.endTransaction();

        for(TemplateComponent current:components){
            String uriStr="";
            if(current.getSourceUri()!=null)
                uriStr=current.getSourceUri().toString();

            String createUserScreenComponentSql="insert into "+ DbIniter.TABLE_NAME_USER_SCREEN_COMPONENT+" values(null,"+current.getLeft()+","+current.getTop()+","+current.getRight()+","+current.getBottom()+","+template.getId()+","+current.getType()+",'"+uriStr+"','"+current.getSourceText().replace("'","''")+"','"+current.getFontType()+"',"+current.getFontSize()+",'"+current.getFontColor()+"',"+current.getFontStyle()+")";

            Log.i(TAG, "createNewUserScreen: createUserScreenComponentSql="+createUserScreenComponentSql);
            db.beginTransaction();
            db.execSQL(createUserScreenComponentSql);
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }



}
