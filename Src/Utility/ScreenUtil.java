package com.example.mfusion.Utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by 1B15182 on 5/5/2016.
 */
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
