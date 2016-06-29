package com.example.mfusion.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Schedule.ScheduleClass;
import com.example.mfusion.ScheduleBlock.BlockItemClass;
import com.example.mfusion.ScheduleBlock.ScheduleBlockClass;
import com.example.mfusion.Template.TemplateDBclass;
import com.example.mfusion.TemplateComponent.TemcompoClass;

import java.io.File;

/**
 * Created by WANG on 6/8/2016.
 */
public class DBController extends SQLiteOpenHelper {
    public static final String DataBase_Name = "MfusionDataBase.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db4;
    public static final String APP_FOLDER = "/databases/";
    private String sdPath;

    private final String TAG = "DBController";
    public static final String TABLE_NAME_COMPONENT = "component";
    public static final String TABLE_NAME_TEMPLATE = "templates";


    public DBController(Context context, String s, Object o, int i) {
        super(context, DataBase_Name, null, DATABASE_VERSION);//specify the name,version,object
        Log.e("DATABASE OPERATIONS", "Database opened...");

        sdPath= "/data/data/com.example.mfusion";
        init();
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

        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        if(c.getCount()<1)
        {
            this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues);//insert value into the table
        }
        else if(c.getCount()==1)
        {
            while (c.moveToNext()) {
                this.getWritableDatabase().execSQL("UPDATE System_Settings SET Display='"+Display+"',Password='"+Password+"',Shutdown='"+Shutdown+"',Wakeup='"+Wakeup+"', Autostart='"+Autostart+"'");
            }
        }
        c.close();
    }

 /*public void Update_IP(String old_IPAddress,String new_IPAddress){
        this.getWritableDatabase().execSQL("UPDATE Syetem_Settings SET IPAddress='"+new_IPAddress+"' WHERE IPAddress='"+old_IPAddress+"'");
 this.getWritableDatabase().execSQL("UPDATE System_Settings SET Display='"+Display+"',Wakeup='"+Wakeup+"' WHERE Display='"+Display+"'");
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
    }//get single entry

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



    public void list_setting6(TextView textView, TextView textView2, EditText editText, RadioGroup radioButton, RadioGroup radioButton2) {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        textView.setText("");
        textView2.setText("");
        editText.setText("");

        while (c.moveToNext()) {

            editText.setText(c.getString(2));
            textView.setText(c.getString(3));
            textView2.setText(c.getString(4));

            if (c.getString(1).equals("Landscape"))
            {
                radioButton.check(R.id.landscape);
            }

            else if(c.getString(1).equals("Portrait"))
            {
                radioButton.check(R.id.portrait);
            }


            if (c.getString(5).equals("Yes"))
            {
                radioButton2.check(R.id.yes);
            }

            else if(c.getString(5).equals("No"))
            {
                radioButton2.check(R.id.no);
            }

        }//ensure that the cursor will move from start to the end(read all the data)
    }

    private void init() {
        File appFolder = new File(sdPath + APP_FOLDER);
        if (!appFolder.exists())
            appFolder.mkdir();
        try {
            db4 = SQLiteDatabase.openDatabase(sdPath + APP_FOLDER + DataBase_Name, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception ex) {
            Log.e(TAG, "init: fail to open db" + ex.getMessage());
        }
        if (db4 == null) {

            db4 = SQLiteDatabase.openOrCreateDatabase(sdPath + APP_FOLDER + DataBase_Name, null);

            String createTemplateSql = "create table if not exists " + TABLE_NAME_TEMPLATE + " (id integer primary key autoincrement,name text)";
            String createComponentSql = "create table if not exists " + TABLE_NAME_COMPONENT + " (id integer primary key autoincrement,left real,top real,right real,bottom real,tid integer)";
            db4.beginTransaction();
            db4.execSQL(createComponentSql);
            db4.execSQL(createTemplateSql);
            db4.setTransactionSuccessful();
            db4.endTransaction();


            String[] templates = {"insert into " + TABLE_NAME_TEMPLATE + " values(null,'First Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Second Template')"};

            String[] component = {"insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,0.5,1,0)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.5,0.5,1,1,0)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.5,0,1,0.5,0)",

                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,1,0.2,1)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0.2,0.5,1,1)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.5,0.2,1,1,1)",

                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,0.5,0.5,2)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.5,0,1,0.5,2)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0.5,0.5,1,2)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.5,0.5,1,1,2)",

                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,1,0.75,3)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0.75,1,1,3)",

                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,1,0.25,4)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0.25,0.3,0.75,4)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.3,0.25,1,0.75,4)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0.75,1,1,4)",

                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0,0,0.25,1,5)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.25,0,1,0.75,5)",
                    "insert into " + TABLE_NAME_COMPONENT + " values(null,0.25,0.75,1,1,5)",};

            db4.beginTransaction();
            for (String current : templates)
                db4.execSQL(current);
            for (String current : component)
                db4.execSQL(current);
            db4.setTransactionSuccessful();
            db4.endTransaction();

        }


}
}
