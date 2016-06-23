package com.example.mfusion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mfusion.Utility.ScreenUtil;
import com.example.mfusion.model.TemplateComponent;

import java.util.ArrayList;



public class  ShowScreenActivity extends Activity {
     private final String TAG="ShowScreenActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_screen);

        ArrayList<TemplateComponent> list=getIntent().getBundleExtra("bundle").getParcelableArrayList("list");
        Log.i(TAG, "onCreate: list got from intent size="+list.size());

        RelativeLayout layout=(RelativeLayout)findViewById(R.id.show_screen_layout);

        for(TemplateComponent current : list){
            ScreenUtil su=new ScreenUtil(this);
            current.setParentHeight(su.getHeight());
            current.setParentWidth(su.getWidth());

            int currentWidth=current.getResolvedRight()-current.getResolvedLeft();
            int currentHeight=current.getResolvedBottom()-current.getResolvedTop();

            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(currentWidth,currentHeight);
            params.setMargins(current.getResolvedLeft(),current.getResolvedTop(),current.getResolvedRight(),current.getResolvedBottom());

            switch (current.getType()){
                case TemplateComponent.TYPE_IMAGE:
                    ImageView imgView=new ImageView(this);
                    //imgView.setBackgroundColor(Color.BLUE);
                    imgView.setLayoutParams(params);
                    imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imgView.setImageURI(current.getSourceUri());
                    layout.addView(imgView);

                    break;
                case TemplateComponent.TYPE_TEXT:
                    TextView textView=new TextView(this);
                    //textView.setBackgroundColor(Color.RED);
                    textView.setLayoutParams(params);

                    textView.setText(current.getSourceText());
                    layout.addView(textView);
                    break;
                case TemplateComponent.TYPE_VIDEO:
                    VideoView videoView=new VideoView(this);
                    //videoView.setBackgroundColor(Color.YELLOW);
                    videoView.setLayoutParams(params);
                    videoView.setVideoURI(current.getSourceUri());
                    videoView.start();
                    layout.addView(videoView);
                    break;
            }
        }
    }
}
