package com.example.mfusion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mfusion.Utility.ScreenUtil;

public class ImageUtil {
    public static Bitmap resizeImage(Context context, int drawableId, int percentageOfScreenWidth){
        ScreenUtil su=new ScreenUtil(context);
        int targetSize=(int) Math.round(1.0*su.getWidth()*percentageOfScreenWidth/100);
        Bitmap bmp= BitmapFactory.decodeResource(context.getResources(),drawableId);
        Bitmap resizedBmp= Bitmap.createScaledBitmap(bmp,targetSize,targetSize,false);

        return resizedBmp;
    }
    public static Bitmap resizeImage(Context context, Bitmap bmp, int percentageOfScreenWidth){
        ScreenUtil su=new ScreenUtil(context);
        int targetSize=(int) Math.round(1.0*su.getWidth()*percentageOfScreenWidth/100);
        Bitmap resizedBmp= Bitmap.createScaledBitmap(bmp,targetSize,targetSize,false);

        return resizedBmp;
    }
}
