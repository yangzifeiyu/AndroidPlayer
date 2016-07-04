package com.example.mfusion.Schedule;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mfusion.R;
import com.example.mfusion.Utility.ScreenUtil;
import com.example.mfusion.model.TemplateComponent;

import java.util.ArrayList;

public class PlaySignage extends Activity {

    private final String TAG="PlaySignage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_signage);

        ArrayList<TemplateComponent> list=getIntent().getBundleExtra("bundle").getParcelableArrayList("list");
        Log.i(TAG, "onCreate: list got from intent size="+list.size());

        FrameLayout layout=(FrameLayout)findViewById(R.id.play_signage_layout);

        for(TemplateComponent current : list){
            ScreenUtil su=new ScreenUtil(this);
            current.setParentHeight(su.getHeight());
            current.setParentWidth(su.getWidth());

            int currentWidth=current.getResolvedRight()-current.getResolvedLeft();
            int currentHeight=current.getResolvedBottom()-current.getResolvedTop();

            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(currentWidth,currentHeight);
            params.setMargins(current.getResolvedLeft(),current.getResolvedTop(),current.getResolvedRight(),current.getResolvedBottom());




            switch (current.getType()){
                case TemplateComponent.TYPE_IMAGE:
                    ImageView imgView=new ImageView(this);
                    //imgView.setBackgroundColor(Color.BLUE);
                    imgView.setLayoutParams(params);
//                   imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imgView.setImageURI(current.getSourceUri());
                    layout.addView(imgView);

                    break;
                case TemplateComponent.TYPE_TEXT:
                    TextView textView=new TextView(this);
                    //textView.setBackgroundColor(Color.RED);
                    textView.setLayoutParams(params);

                    textView.setText(current.getSourceText());
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textView.setSelected(true);
                    textView.setSingleLine(true);
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