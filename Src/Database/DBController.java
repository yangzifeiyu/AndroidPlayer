package com.example.mfusion.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mfusion.R;

import java.io.File;

//import com.example.mfusion.TemplateComponent.TemcompoClass;

/**
 * Created by WANG on 6/8/2016.
 */
public class DBController extends SQLiteOpenHelper {
    private static final String DataBase_Name = "/mnt/sdcard/MFusion/MfusionDataBase.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "MfusionDataBase.db";
    private String sdPath;
    private SQLiteDatabase db;
    public static final String APP_FOLDER = "/MFusion/";
    public static final String TABLE_NAME_TEMPLATE = "tamplate";
    public static final String TABLE_NAME_COMPONENT = "component";
    public static final String TABLE_NAME_USER_SCREEN = "userscreen";
    public static final String TABLE_NAME_USER_SCREEN_COMPONENT = "userscreencomponent";
    private final String TAG = "DBController";

    public DBController(Context context, String s, Object o, int i) {
        super(context, DataBase_Name, null, DATABASE_VERSION);//specify the name,version,object
        Log.e("DATABASE OPERATIONS", "Database opened...");

        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        init();

    }


    @Override
    public void onCreate(SQLiteDatabase db1) {

        db1.execSQL("CREATE TABLE System_Settings(ID INTEGER PRIMARY KEY AUTOINCREMENT,Display TEXT,Password TEXT,Shutdown INTEGER,Wakeup INTEGER, Autostart TEXT);");

        Log.e("DATABASE OPERATIONS", "Table created...");
    }//OnCreate method-create configuration table

    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

    }


//    public void insert_setting(String Display, String Password, String Shutdown, String Wakeup, String Autostart) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("Display", Display);
//        contentValues.put("Password", Password);
//        contentValues.put("Shutdown", Shutdown);
//        contentValues.put("Wakeup", Wakeup);
//        contentValues.put("Autostart", Autostart);
//
//
//        this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues);//insert value into the table
//
//    }//insert setting

    public void insert_setting2(String Display, String Password, String Shutdown, String Wakeup, String Autostart) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Display", Display);
        contentValues.put("Password", Password);
        contentValues.put("Shutdown", Shutdown);
        contentValues.put("Wakeup", Wakeup);
        contentValues.put("Autostart", Autostart);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("Display", "Landscape");
        contentValues2.put("Password", "mfusion");
        contentValues2.put("Shutdown", "");
        contentValues2.put("Wakeup", "");
        contentValues2.put("Autostart", "No");

        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        if (c.getCount() < 1) {
            if (Password.equals("")) {
                this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues2);//insert value into the table
            } else if (Password != "") {
                this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues);//insert user set value into the table
            }

        } else if (c.getCount() == 1) {

            while (c.moveToNext()) {

                if (Password.equals("")) {
                    this.getWritableDatabase().execSQL("UPDATE System_Settings SET Display='" + Display + "',Password='mfusion',Shutdown='" + Shutdown + "',Wakeup='" + Wakeup + "', Autostart='" + Autostart + "' Where ID=1");
                } else if (Password != "") {

                    this.getWritableDatabase().execSQL("UPDATE System_Settings SET Display='" + Display + "',Password='" + Password + "',Shutdown='" + Shutdown + "',Wakeup='" + Wakeup + "', Autostart='" + Autostart + "' Where ID=1");
                }
            }
        }
        c.close();
    }//inserting of password to database and retreving it to match exit password

    public void insert_setting3() {
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("Display", "Landscape");
        contentValues2.put("Password", "mfusion");
        contentValues2.put("Shutdown", "");
        contentValues2.put("Wakeup", "");
        contentValues2.put("Autostart", "No");

        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        if (c.getCount() < 1) {
            this.getWritableDatabase().insertOrThrow("System_Settings", "", contentValues2);//insert value into the table
        }

        c.close();

    }//insert value into the configuration table


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


    public void list_setting6(TextView textView, TextView textView2, TextView text3, EditText editText, RadioGroup radioButton, RadioGroup radioButton2) {
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        textView.setText("");
        textView2.setText("");
        text3.setText("");
        editText.setText("");

        while (c.moveToNext()) {

            editText.setText(c.getString(2));
            text3.setText("Your Detail is : " + c.getString(1) + "," + c.getString(2) + "," + c.getString(3) + "," + c.getString(4) + "," + c.getString(5));
            textView.setText(c.getString(3));
            textView2.setText(c.getString(4));

            if (c.getString(1).equals("Landscape")) {
                radioButton.check(R.id.landscape);
            } else if (c.getString(1).equals("Portrait")) {
                radioButton.check(R.id.portrait);
            }


            if (c.getString(5).equals("Yes")) {
                radioButton2.check(R.id.yes);
            } else if (c.getString(5).equals("No")) {
                radioButton2.check(R.id.no);
            }

        }//ensure that the cursor will move from start to the end(read all the data)
    }//ensure that the cursor will move from start to the end(read all the data)

    //    public void delete_setting(String pa) {
//
////        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
////
////        sqLiteDatabase.execSQL("Delete FROM System_Settings");
//
//        Cursor c=this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings",null);
//        if (c != null) {
//            while (c.moveToLast()) {
//                this.getWritableDatabase().execSQL("Delete FROM System_Settings Where Password='" +pa+ "'");
//            }
//
//            c.close();
//        }
//
//    }//configuration-delete


    public String getSinlgeEntry() {
        String password2 = "";
        Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM System_Settings", null);
        if (c != null) {
            c.moveToFirst();// move your cursor to first row
            // Loop through the cursor
            while (!c.isAfterLast()) {
                password2 = c.getString(c.getColumnIndex("Password")); // will fetch you the data
                c.moveToNext();
            }

            c.close();
        } else if (c.getCount() < 1) {
            c.close();
            return "NO User Exit Password";
        }

        return password2;
    }

//    public void InsertPlaylist(String listName) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.beginTransaction();
//        ContentValues values;
//        try {
//            values = new ContentValues();
//            values.put("listName", listName);
//            db.insert(TABLE_NAME6, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//            db.close();
//        } //insert playlist method
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(PlaylistClass.NewPlaylist.listId, Id);
//        contentValues.put(PlaylistClass.NewPlaylist.listName,Name);
//
//        db6.insert(BlockItemClass.NewBlockItem.TABLE_NAME, null, contentValues);//put null to make sure the system will not insert a null row if the content is empty
//        Log.e("DATABASE OPERATIONS", "One row is inserted...");


//    public Cursor getPlaylist(SQLiteDatabase db6){
//
//
//        Cursor cursor6;
//        String[] projections = {PlaylistClass.NewPlaylist.listId, PlaylistClass.NewPlaylist.listName};
//        cursor6= db6.query(TemcompoClass.NewTemplateCompo.TABLE_NAME,projections,null,null,null,null,null);
//        return cursor6;
//    }//getBlockItem(schedule)-retrive data


    private void init() {
        File appFolder = new File(sdPath + APP_FOLDER);
        if (!appFolder.exists())
            appFolder.mkdir();
        try {
            db = SQLiteDatabase.openDatabase(sdPath + APP_FOLDER + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception ex) {
            Log.e(TAG, "init: fail to open db" + ex.getMessage());
        }
        if (db == null) {
            db = SQLiteDatabase.openOrCreateDatabase(sdPath + APP_FOLDER + DB_NAME, null);
            String createTemplateSql = "create table if not exists " + TABLE_NAME_TEMPLATE + " (id integer primary key autoincrement,name text)";
            String createComponentSql = "create table if not exists " + TABLE_NAME_COMPONENT + " (id integer primary key autoincrement,left real,top real,right real,bottom real,tid integer,type integer, sourceUri text, sourceText text)";
            String createUserScreenSql = "create table if not exists " + TABLE_NAME_USER_SCREEN + "(id integer primary key autoincrement,name text)";
            String createUserScreenComponentSql = "create table if not exists " + TABLE_NAME_USER_SCREEN_COMPONENT + "(id integer primary key autoincrement,left real,top real,right real,bottom real,tid integer,type integer,sourceUri text,sourceText text,fonttype text,fontsize real,fontcolor text,fontstyle integer)";
            db.beginTransaction();
            db.execSQL(createComponentSql);
            db.execSQL(createTemplateSql);
            db.execSQL(createUserScreenSql);
            db.execSQL(createUserScreenComponentSql);
            db.setTransactionSuccessful();
            db.endTransaction();


            String[] templates = {"insert into " + TABLE_NAME_TEMPLATE + " values(null,'First Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Second Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Third Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Forth Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Fifth Template')",
                    "insert into " + TABLE_NAME_TEMPLATE + " values(null,'Sixth Template')",};

            String[] components = {"insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,0.5,1,1)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.5,0.5,1,1,1)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.5,0,1,0.5,1)",

                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,1,0.2,2)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0.2,0.5,1,2)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.5,0.2,1,1,2)",

                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,0.5,0.5,3)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.5,0,1,0.5,3)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0.5,0.5,1,3)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.5,0.5,1,1,3)",

                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,1,0.75,4)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0.75,1,1,4)",

                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,1,0.25,5)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0.25,0.3,0.75,5)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.3,0.25,1,0.75,5)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0.75,1,1,5)",

                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0,0,0.25,1,6)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.25,0,1,0.75,6)",
                    "insert into " + TABLE_NAME_COMPONENT + " (id,left,top,right,bottom,tid) values(null,0.25,0.75,1,1,6)",};


            db.beginTransaction();
            for (String current : templates)
                db.execSQL(current);
            for (String current : components)
                db.execSQL(current);
            db.setTransactionSuccessful();
            db.endTransaction();


        }


    }


}//end of class


//    public Cursor getPlaylist(SQLiteDatabase db6){
//
//
//        Cursor cursor6;
//        String[] projections = {PlaylistClass.NewPlaylist.listId, PlaylistClass.NewPlaylist.listName};
//        cursor6= db6.query(TemcompoClass.NewTemplateCompo.TABLE_NAME,projections,null,null,null,null,null);
//        return cursor6;
//    }//getBlockItem(schedule)-retrive data


//    public ArrayList<String> getAllPlaylist() {
//        ArrayList<String> list = new ArrayList<String>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.beginTransaction();
//        try {
//            String selectQuery = "SELECT * FROM " + TABLE_NAME6;
//            Cursor cursor3 = db.rawQuery(selectQuery, null);
//            if (cursor3.getCount() > 0) {
//                while (cursor3.moveToNext()) {
//                    String listName = cursor3.getString(cursor3.getColumnIndex("listName"));
//                    list.add(listName);
//
//                }
//            }
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.endTransaction();
//            db.close();
//        }
//        return list;
//    }


