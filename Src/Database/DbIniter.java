package com.example.mfusion.Database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class DbIniter {

    private final String TAG="DbIniter";

    private SQLiteDatabase db;
    public static final String APP_FOLDER="/MFusion/";
    public static final String DB_NAME="MfusionDataBase.db";
    public static final String TABLE_NAME_TEMPLATE="tamplate";
    public static final String TABLE_NAME_COMPONENT="component";


    private String sdPath;

    public DbIniter() {

        sdPath= Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        init();
    }

    private void init(){
        File appFolder=new File(sdPath+APP_FOLDER);
        if(!appFolder.exists())
            appFolder.mkdir();
        try{
            db= SQLiteDatabase.openDatabase(sdPath+APP_FOLDER+DB_NAME,null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception ex)
        {
            Log.e(TAG, "init: fail to open db"+ex.getMessage() );
        }
        if(db==null){
            db= SQLiteDatabase.openOrCreateDatabase(sdPath+APP_FOLDER+DB_NAME,null);
            String createTemplateSql="create table if not exists "+TABLE_NAME_TEMPLATE+" (id integer primary key autoincrement,name text)";
            String createComponentSql="create table if not exists "+TABLE_NAME_COMPONENT+" (id integer primary key autoincrement,left real,top real,right real,bottom real,tid integer,type integer, sourceUri text, sourceText text)";
            db.beginTransaction();
            db.execSQL(createComponentSql);
            db.execSQL(createTemplateSql);
            db.setTransactionSuccessful();
            db.endTransaction();


            String[] templates={"insert into "+TABLE_NAME_TEMPLATE+" values(null,'First Template')",
                    "insert into "+TABLE_NAME_TEMPLATE+" values(null,'Second Template')"};

            String[] components={"insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,0.5,1,0)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.5,0.5,1,1,0)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.5,0,1,0.5,0)",

                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,1,0.2,1)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0.2,0.5,1,1)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.5,0.2,1,1,1)",

                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,0.5,0.5,2)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.5,0,1,0.5,2)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0.5,0.5,1,2)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.5,0.5,1,1,2)",

                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,1,0.75,3)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0.75,1,1,3)",

                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,1,0.25,4)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0.25,0.3,0.75,4)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.3,0.25,1,0.75,4)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0.75,1,1,4)",

                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0,0,0.25,1,5)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.25,0,1,0.75,5)",
                    "insert into "+TABLE_NAME_COMPONENT+" (id,left,top,right,bottom,tid) values(null,0.25,0.75,1,1,5)",};



            db.beginTransaction();
            for(String current:templates)
                db.execSQL(current);
            for(String current:components)
                db.execSQL(current);
            db.setTransactionSuccessful();
            db.endTransaction();



        }



    }
}
