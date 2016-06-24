package com.example.mfusion;

import android.app.Application;

import com.facebook.stetho.Stetho;


/**
 * Created by JCYYYY on 2016/6/24.
 */
public class MyApplication extends Application {



    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
