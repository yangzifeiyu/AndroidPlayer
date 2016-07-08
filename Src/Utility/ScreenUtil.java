package com.example.mfusion.Utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class ScreenUtil {
    private Context context;
    private DisplayMetrics dm;


    public ScreenUtil(Context context) {
        this.context=context;
        dm=new DisplayMetrics();
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public int getWidth(){
        return dm.widthPixels;
    }

    public int getHeight(){
        return dm.heightPixels;
    }

}
