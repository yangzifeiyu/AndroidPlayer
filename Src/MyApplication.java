package com.example.mfusion;

import android.app.Application;

import com.example.mfusion.Database.DBController;
//import com.example.mfusion.Database.DbIniter;
import com.facebook.stetho.Stetho;


/**
 * Created by JCYYYY on 2016/6/24.
 */
public class MyApplication extends Application {



    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        //new DbIniter();

        new DBController(this, null, null, 0);

    }
}
