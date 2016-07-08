package com.example.mfusion;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.mfusion.Template.ViewImageProcessor;
import com.example.mfusion.Template.ViewStaticTextProcessor;
import com.example.mfusion.Template.ViewTickerTextProcessor;
import com.example.mfusion.Template.ViewVideoProcessor;
import com.example.mfusion.Utility.ScreenUtil;
import com.example.mfusion.model.TemplateComponent;

public class ShowScreenActivity extends Activity {
     private final String TAG="ShowScreenActivity";
    private String lineSeparator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_screen);
        lineSeparator=System.getProperty("line.separator");
        ArrayList<TemplateComponent> list=getIntent().getBundleExtra("bundle").getParcelableArrayList("list");
        Log.i(TAG, "onCreate: list got from intent size="+list.size());

        FrameLayout layout=(FrameLayout)findViewById(R.id.show_screen_layout);

        for(TemplateComponent current : list){
            ScreenUtil su=new ScreenUtil(this);
            current.setParentHeight(su.getHeight());
            current.setParentWidth(su.getWidth());

//            int currentWidth=current.getResolvedRight()-current.getResolvedLeft();
//            int currentHeight=current.getResolvedBottom()-current.getResolvedTop();
//
//            FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(currentWidth,currentHeight);
//            params.setMargins(current.getResolvedLeft(),current.getResolvedTop(),current.getResolvedRight(),current.getResolvedBottom());




            switch (current.getType()){
                case TemplateComponent.TYPE_IMAGE:
//                    ImageView imgView=new ImageView(this);
//                    //imgView.setBackgroundColor(Color.BLUE);
//                    imgView.setLayoutParams(params);
////                   imgView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    imgView.setImageURI(current.getSourceUri());
//                    layout.addView(imgView);
                    new ViewImageProcessor(this,current).addOrUpdateLayout(layout);

                    break;
                case TemplateComponent.TYPE_TEXT:
//                    TextView textView=new TextView(this);
//                    //textView.setBackgroundColor(Color.RED);
//                    textView.setLayoutParams(params);
//
//                    textView.setText(current.getSourceText());
//                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                    textView.setSelected(true);
//                    textView.setSingleLine(true);
//                    layout.addView(textView);
                    new ViewTickerTextProcessor(this,current).addOrUpdateLayout(layout);
                    break;
                case TemplateComponent.TYPE_STATIC_TEXT:
//                    TextView staticTextView=new TextView(this);
//                    //textView.setBackgroundColor(Color.RED);
//                    staticTextView.setLayoutParams(params);
//
//                    staticTextView.setText(current.getSourceText());
//                    layout.addView(staticTextView);
                    new ViewStaticTextProcessor(this,current).addOrUpdateLayout(layout);
                    break;
                case TemplateComponent.TYPE_VIDEO:
//                    VideoView videoView=new VideoView(this);
//                    //videoView.setBackgroundColor(Color.YELLOW);
//
//                    videoView.setLayoutParams(params);
//                    videoView.setVideoURI(current.getSourceUri());
//                    videoView.start();
//                    layout.addView(videoView);
                    new ViewVideoProcessor(this,current).addOrUpdateLayout(layout);
                    break;
                case TemplateComponent.TYPE_DATE:

                    ViewStaticTextProcessor tickerTextProcessor=new ViewStaticTextProcessor(this,current);
                    tickerTextProcessor.addOrUpdateLayout(layout);
                    final TextView tvDate=(TextView) tickerTextProcessor.getOldView();
                    CountDownTimer timer=new CountDownTimer(1000,1000) {
                        @Override
                        public void onTick(long l) {
                            tvDate.setText(new SimpleDateFormat("MMM dd yyyy "+lineSeparator+"h:mm:ss a").format(new Date()));
                        }

                        @Override
                        public void onFinish() {
                            this.start();
                        }
                    };
                    timer.start();

                    break;
            }
        }
    }
}
