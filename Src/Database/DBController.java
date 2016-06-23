package com.example.mfusion.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import com.example.mfusion.Schedule.ScheduleClass;
import com.example.mfusion.ScheduleBlock.BlockItemClass;
import com.example.mfusion.ScheduleBlock.ScheduleBlockClass;
import com.example.mfusion.Template.TemplateDBclass;
import com.example.mfusion.TemplateComponent.TemcompoClass;

/**
 * Created by WANG on 6/8/2016.
 */
public class DBController extends SQLiteOpenHelper {
    private static final String DataBase_Name = "MfusionDataBase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Schedule";
    public static final String TABLE_NAME1 = "ScheduleBlock";
    public static final String TABLE_NAME2 = "TemplateCompo";
    public static final String TABLE_NAME3 = "Block_Item";
    public static final String TABLE_NAME4 = "System_Settings";
    public static final String TABLE_NAME5 = "Template";

    public DBController(Context context, String s, Object o, int i) {
        super(context, DataBase_Name, null, DATABASE_VERSION);//specify the name,version,object
        Log.e("DATABASE OPERATIONS", "Database opened...");
    }



    @Override
    public void onCreate(SQLiteDatabase db1) {
        db1.execSQL("CREATE TABLE " + ScheduleClass.NewSchedule.TABLE_NAME + "(" + ScheduleClass.NewSchedule.ScheduleId + " INTEGER PRIMARY KEY AUTOINCREMENT," + ScheduleClass.NewSchedule.scheName + " Text Default null," + ScheduleClass.NewSchedule.scheIdleItem + " INTEGER," + ScheduleClass.NewSchedule.schePlayMode + " INTEGER)");
        db1.execSQL("CREATE TABLE " + ScheduleBlockClass.NewScheduleBlock.TABLE_NAME + "(" + ScheduleBlockClass.NewScheduleBlock.BlockId + " INTEGER PRIMARY KEY AUTOINCREMENT," + ScheduleBlockClass.NewScheduleBlock.scheId + " INTEGER," + ScheduleBlockClass.NewScheduleBlock.bType + " INTEGER Default 0," + ScheduleBlockClass.NewScheduleBlock.bStartDate + " INTEGER," + ScheduleBlockClass.NewScheduleBlock.bEndDate + " TEXT," + ScheduleBlockClass.NewScheduleBlock.bStartTime + " TEXT," + ScheduleBlockClass.NewScheduleBlock.bEndTime + " TEXT," + ScheduleBlockClass.NewScheduleBlock.bRecurrence + " TEXT)");
        db1.execSQL("CREATE TABLE " + TemcompoClass.NewTemplateCompo.TABLE_NAME + "(" + TemcompoClass.NewTemplateCompo.componentID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemcompoClass.NewTemplateCompo.tempId + " INTEGER," + TemcompoClass.NewTemplateCompo.compName + " TEXT," + TemcompoClass.NewTemplateCompo.compType + " TEXT," + TemcompoClass.NewTemplateCompo.compX + " INTEGER," + TemcompoClass.NewTemplateCompo.compY + " INTEGER," + TemcompoClass.NewTemplateCompo.compWidth + " INTEGER," + TemcompoClass.NewTemplateCompo.compHeight + " INTEGER," + TemcompoClass.NewTemplateCompo.compZindex + " INTEGER," + TemcompoClass.NewTemplateCompo.compBackColor + " INTEGER," + TemcompoClass.NewTemplateCompo.compProperty + " TEXT)");
        db1.execSQL("CREATE TABLE " + BlockItemClass.NewBlockItem.TABLE_NAME + "(" + BlockItemClass.NewBlockItem.Id + " INTEGER PRIMARY KEY AUTOINCREMENT," + BlockItemClass.NewBlockItem.blockId + " INTEGER," + BlockItemClass.NewBlockItem.ItemId + " INTEGER)");
        db1.execSQL("CREATE TABLE System_Settings(ID INTEGER PRIMARY KEY AUTOINCREMENT,Display TEXT,Password TEXT,Shutdown INTEGER,Wakeup INTEGER, Autostart TEXT);");
        db1.execSQL("CREATE TABLE " + TemplateDBclass.NewTemplate.TABLE_NAME + "(" + TemplateDBclass.NewTemplate.tempId + " INTEGER PRIMARY KEY AUTOINCREMENT," + TemplateDBclass.NewTemplate.tempName + " Text," + TemplateDBclass.NewTemplate.tempWidth + " INTEGER," + TemplateDBclass.NewTemplate.tempHeight + " INTEGER," + TemplateDBclass.NewTemplate.tempBackColor + " INTEGER," + TemplateDBclass.NewTemplate.tempBackImage + " TEXT)");
        Log.e("DATABASE OPERATIONS", "Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {
        db1.execSQL("DROP TABLE IF EXISTS " + ScheduleClass.NewSchedule.TABLE_NAME);

    }


    public void addSche(String ScheduleId, String scheName, String scheIdleItem, String schePlayMode, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScheduleClass.NewSchedule.ScheduleId, ScheduleId);
        contentValues.put(ScheduleClass.NewSchedule.scheName, scheName);
        contentValues.put(ScheduleClass.NewSchedule.scheIdleItem, scheIdleItem);
        contentValues.put(ScheduleClass.NewSchedule.schePlayMode, schePlayMode);


        db.insert(ScheduleClass.NewSchedule.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
        Log.e("DATABASE OPERATIONS", "One row is inserted...");


    }//schedule

    public Cursor getSchedule(SQLiteDatabase db){


        Cursor cursor;
        String[] projections = {ScheduleClass.NewSchedule.ScheduleId, ScheduleClass.NewSchedule.scheName, ScheduleClass.NewSchedule.scheIdleItem, ScheduleClass.NewSchedule.schePlayMode};
        cursor= db.query(ScheduleClass.NewSchedule.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }//getSchedule-retrive data

    public void InsertScheBlock(String blockId, String scheId, String bType, String bStartDate, String bEndDate, String bStartTime, String bEndTime, String bRecurrence, SQLiteDatabase db2) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.BlockId, blockId);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.scheId, scheId);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bType, bType);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bStartDate, bStartDate);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bEndDate, bEndDate);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bStartTime, bStartTime);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bEndTime, bEndTime);
        contentValues.put(ScheduleBlockClass.NewScheduleBlock.bRecurrence, bRecurrence);


        db2.insert(ScheduleBlockClass.NewScheduleBlock.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
        Log.e("DATABASE OPERATIONS", "One row is inserted...");


    }//schedule block

    public Cursor getScheduleBlock(SQLiteDatabase db2){


        Cursor cursor1;
        String[] projections = {ScheduleBlockClass.NewScheduleBlock.BlockId, ScheduleBlockClass.NewScheduleBlock.scheId, ScheduleBlockClass.NewScheduleBlock.bType, ScheduleBlockClass.NewScheduleBlock.bStartDate, ScheduleBlockClass.NewScheduleBlock.bEndDate, ScheduleBlockClass.NewScheduleBlock.bStartTime, ScheduleBlockClass.NewScheduleBlock.bEndTime, ScheduleBlockClass.NewScheduleBlock.bRecurrence};
        cursor1= db2.query(ScheduleBlockClass.NewScheduleBlock.TABLE_NAME,projections,null,null,null,null,null);
        return cursor1;
    }//getScheduleBlock-retrive data

    public void addCompoinfo(String componentID, String tempID, String compName, String compType, String compX, String compY, String compWidth, String compHeight, String compZindex, String compBackColor, String compProperty, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TemcompoClass.NewTemplateCompo.componentID, componentID);
        contentValues.put(TemcompoClass.NewTemplateCompo.tempId, tempID);
        contentValues.put(TemcompoClass.NewTemplateCompo.compName, compName);
        contentValues.put(TemcompoClass.NewTemplateCompo.compType, compType);
        contentValues.put(TemcompoClass.NewTemplateCompo.compX, compX);
        contentValues.put(TemcompoClass.NewTemplateCompo.compY, compY);
        contentValues.put(TemcompoClass.NewTemplateCompo.compWidth, compWidth);
        contentValues.put(TemcompoClass.NewTemplateCompo.compHeight, compHeight);
        contentValues.put(TemcompoClass.NewTemplateCompo.compZindex, compZindex);
        contentValues.put(TemcompoClass.NewTemplateCompo.compBackColor, compBackColor);
        contentValues.put(TemcompoClass.NewTemplateCompo.compProperty, compProperty);


        db.insert(TemcompoClass.NewTemplateCompo.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
        Log.e("DATABASE OPERATIONS", "One row is inserted...");


    }//template component

    public Cursor getTemplateCompo(SQLiteDatabase db){


        Cursor cursor3;
        String[] projections = {TemcompoClass.NewTemplateCompo.componentID, TemcompoClass.NewTemplateCompo.tempId, TemcompoClass.NewTemplateCompo.compName, TemcompoClass.NewTemplateCompo.compType, TemcompoClass.NewTemplateCompo.compX, TemcompoClass.NewTemplateCompo.compY, TemcompoClass.NewTemplateCompo.compWidth, TemcompoClass.NewTemplateCompo.compHeight, TemcompoClass.NewTemplateCompo.compZindex, TemcompoClass.NewTemplateCompo.compBackColor, TemcompoClass.NewTemplateCompo.compProperty};
        cursor3= db.query(TemcompoClass.NewTemplateCompo.TABLE_NAME,projections,null,null,null,null,null);
        return cursor3;
    }//getSchedule-retrive data

    public void InsertBlockItem(String Id, String blockId, String ItemId, SQLiteDatabase db3) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(BlockItemClass.NewBlockItem.Id, Id);
        contentValues.put(BlockItemClass.NewBlockItem.blockId, blockId);
        contentValues.put(BlockItemClass.NewBlockItem.ItemId, ItemId);


        db3.insert(BlockItemClass.NewBlockItem.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
        Log.e("DATABASE OPERATIONS", "One row is inserted...");


    }//schedule block item

    public Cursor getBlockItem(SQLiteDatabase db){


        Cursor cursor4;
        String[] projections = {TemcompoClass.NewTemplateCompo.componentID, TemcompoClass.NewTemplateCompo.tempId, TemcompoClass.NewTemplateCompo.compName, TemcompoClass.NewTemplateCompo.compType, TemcompoClass.NewTemplateCompo.compX, TemcompoClass.NewTemplateCompo.compY, TemcompoClass.NewTemplateCompo.compWidth, TemcompoClass.NewTemplateCompo.compHeight, TemcompoClass.NewTemplateCompo.compZindex, TemcompoClass.NewTemplateCompo.compBackColor, TemcompoClass.NewTemplateCompo.compProperty};
        cursor4= db.query(TemcompoClass.NewTemplateCompo.TABLE_NAME,projections,null,null,null,null,null);
        return cursor4;
    }//getBlockItem(schedule)-retrive data

    public void insert_setting(String Display, String Password, String Shutdown, String Wakeup, String Autostart) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Display", Display);
        contentValues.put("Password", Password);
        contentValues.put("Shutdown", Shutdown);
        contentValues.put("Wakeup", Wakeup);
        contentValues.put("Autostart", Autostart);


        this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues);//insert value into the table

    }

    /*public void Update_IP(String old_IPAddress,String new_IPAddress){
        this.getWritableDatabase().execSQL("UPDATE Syetem_Settings SET IPAddress='"+new_IPAddress+"' WHERE IPAddress='"+old_IPAddress+"'");

    }*/

    public void list_setting(TextView textView) {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        textView.setText("");
        while (c.moveToNext()) {
            textView.append(c.getString(1) + "" + c.getString(2) + "" + c.getString(3) + "" + c.getString(4) + "" + c.getString(5) + "\n");
        }//ensure that the cursor will move from start to the end(read all the data)
    }


    public void delete_setting() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete FROM System_Settings");

    }//configuration-delete
    public String getSinlgeEntry()
    {
        String password2 = "";
        Cursor c=this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings",null);
        if (c != null) {
            c.moveToFirst();// move your cursor to first row
            // Loop through the cursor
            while (!c.isAfterLast()) {
                password2 = c.getString(c.getColumnIndex("Password")); // will fetch you the data
                c.moveToNext();
            }

            c.close();
        }

        else if(c.getCount()<1)
        {
            c.close();
            return "NO User Exit Password";
        }

        return password2;
    }

    public void InsertTemp(String tempId,String tempName,String tempWidth,String tempHeight, String tempBackColor,String tempBackImage, SQLiteDatabase db4) {



        ContentValues contentValues = new ContentValues();
        contentValues.put(TemplateDBclass.NewTemplate.tempId, tempId);
        contentValues.put(TemplateDBclass.NewTemplate.tempName, tempName);
        contentValues.put(TemplateDBclass.NewTemplate.tempWidth, tempWidth);
        contentValues.put(TemplateDBclass.NewTemplate.tempHeight, tempHeight);
        contentValues.put(TemplateDBclass.NewTemplate.tempBackColor, tempBackColor);
        contentValues.put(TemplateDBclass.NewTemplate.tempBackImage, tempBackImage);

        db4.insert(TemplateDBclass.NewTemplate.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
        Log.e("DATABASE OPERATIONS", "One row is inserted...");

    }//Template


    public Cursor getTemplate(SQLiteDatabase db4){


        Cursor cursor2;
        String[] projections = {TemplateDBclass.NewTemplate.tempId, TemplateDBclass.NewTemplate.tempName, TemplateDBclass.NewTemplate.tempWidth, TemplateDBclass.NewTemplate.tempHeight, TemplateDBclass.NewTemplate.tempBackColor, TemplateDBclass.NewTemplate.tempBackImage};
        cursor2= db4.query(TemplateDBclass.NewTemplate.TABLE_NAME,projections,null,null,null,null,null);
        return cursor2;
    }//getTemplate-retrive data



}
