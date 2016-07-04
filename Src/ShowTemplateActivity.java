package com.example.mfusion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import com.example.mfusion.Template.TemplateDAO;
import com.example.mfusion.Template.TemplateViewNew;
import com.example.mfusion.Template.ViewImageProcessor;
import com.example.mfusion.Template.ViewProcessor;
import com.example.mfusion.Template.ViewStaticTextProcessor;
import com.example.mfusion.Template.ViewTickerTextProcessor;
import com.example.mfusion.Template.ViewVideoProcessor;
import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowTemplateActivity extends Activity {

    private final String TAG="ShowTemplateActivity";
    private static final int RESIZE_PERCENTAGE=6;
    private final int PICK_IMAGE=1;
    private final int PICK_VIDEO=2;
    private final int EDIT_TEXT_PROP=3;
    private TemplateViewNew templateViewNew;
    private TemplateDAO db;
    private Uri imgUri;
    private Uri videoUri;

    private FrameLayout frameLayout;
    @BindView(R.id.imgPreview) ImageView imgPreview;
    @BindView(R.id.imgEdit) ImageView imgEdit;
    @BindView(R.id.imgSave) ImageView imgSave;
    @BindView(R.id.imgDelete)ImageView imgDelete;
    @BindView(R.id.imgSaveAs)ImageView imgSaveAs;

    private boolean editMode;
    private boolean imgClick;

    private HashMap<Integer,View> viewRefMap;

    private String weatherCity;
    private String lineSeparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_template);
        ButterKnife.bind(this);
        imgEdit.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.edit_btn,RESIZE_PERCENTAGE));
        imgPreview.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.preview_btn,RESIZE_PERCENTAGE));
        imgSave.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.save,RESIZE_PERCENTAGE));
        imgSaveAs.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.save_as,RESIZE_PERCENTAGE));
        imgDelete.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.delete_btn,RESIZE_PERCENTAGE));

        templateViewNew=(TemplateViewNew) findViewById(R.id.show_template_my_view);
        frameLayout=(FrameLayout)findViewById(R.id.show_template_frame_layout);
//        imgPreview=(ImageView)findViewById(R.id.imgPreview);
//        imgEdit=(ImageView)findViewById(R.id.imgEdit);
//        imgSave=(ImageView)findViewById(R.id.imgSave);
        db=new TemplateDAO(this);
        lineSeparator=System.getProperty("line.separator");

        viewRefMap=new HashMap<>();

        int tid=getIntent().getExtras().getInt("tid");
        final MyTemplate selectedTemplate=db.getMyTemplateById(tid);
        templateViewNew.setTemplate(selectedTemplate);
        templateViewNew.setBackgroundColor(Color.LTGRAY);

        imgClick = true;

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgClick){
                    Toast.makeText(getApplicationContext(), "Adjust the template size", Toast.LENGTH_LONG).show();
                    templateViewNew.setEditMode(true);
                    imgClick=false;

                }else {
                    Toast.makeText(getApplicationContext(), "Adding components", Toast.LENGTH_LONG).show();
                    templateViewNew.setEditMode(false);
                    imgClick=true;
                }

            }
        });


        templateViewNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Log.i(TAG, "onClick: templateview onclick triggered, clicked area="+templateView.getAreaSelected());
                //templateView.setComponentDetail(1,1,null,"abc");
                AlertDialog.Builder builder=new AlertDialog.Builder(ShowTemplateActivity.this);
                builder.setTitle("Select content for Area "+templateViewNew.getAreaTouched());
                builder.setItems(new String[]{"Video","Ticker Text","Image","Date & Time","Weather","RSS"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        switch (which){
                            case 0:
                                Intent intentVideo = new Intent(Intent.ACTION_GET_CONTENT);
                                intentVideo.setType("video/*");
                                startActivityForResult(intentVideo, PICK_VIDEO);

                                break;
                            case 1:
                                AlertDialog.Builder textDialogBuilder=new AlertDialog.Builder(ShowTemplateActivity.this);
                                textDialogBuilder.setTitle("Enter your text");
                                final EditText editText=new EditText(ShowTemplateActivity.this);
                                textDialogBuilder.setView(editText);
                                textDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent goEditTextProp=new Intent(ShowTemplateActivity.this,EditTextPropertyActivity.class);
                                        goEditTextProp.putExtra("component",templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
                                        startActivityForResult(goEditTextProp,EDIT_TEXT_PROP);

                                        templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(), TemplateComponent.TYPE_TEXT,null,editText.getText().toString());
                                        ViewTickerTextProcessor tickerTextProcessor=new ViewTickerTextProcessor(ShowTemplateActivity.this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
                                        if(tickerTextProcessor.getOldView()!=null)
                                            frameLayout.removeView(tickerTextProcessor.getOldView());
                                        frameLayout.addView(tickerTextProcessor.generateView());
                                    }
                                });
                                textDialogBuilder.show();

                                break;
                            case 2:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PICK_IMAGE);

                                break;
                            case 3:
                                final TextView dateView=new TextView(ShowTemplateActivity.this);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM MM dd \r\nyyyy h:mm a");
                                String dateString=simpleDateFormat.format(date);
                                dateView.setText(dateString);

                                templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(), TemplateComponent.TYPE_TEXT,null,dateView.getText().toString());
                                ViewStaticTextProcessor staticTextProcessor=new ViewStaticTextProcessor(ShowTemplateActivity.this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
                                if(staticTextProcessor.getOldView()!=null)
                                    frameLayout.removeView(staticTextProcessor.getOldView());
                                frameLayout.addView(staticTextProcessor.generateView());



                                break;

                            case  4:
                                AlertDialog.Builder weatherBuilder=new AlertDialog.Builder(ShowTemplateActivity.this);
                                weatherBuilder.setTitle("Enter your city");
                                final EditText etWeather=new EditText(ShowTemplateActivity.this);
                                etWeather.setText("singapore");
                                weatherBuilder.setView(etWeather);
                                weatherBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        WeatherReader reader=new WeatherReader(ShowTemplateActivity.this, etWeather.getText().toString(), new WeatherReader.WeatherReaderListener() {
                                            @Override
                                            public void onReady(WeatherJsonWrapper wrapper) {
                                                String weatherInfo="Temperature :"+wrapper.info[0].now.tmp+" Celsius"+lineSeparator+"Humidity :"+wrapper.info[0].now.hum+lineSeparator
                                                        +"Weather :"+wrapper.info[0].now.cond.txt+lineSeparator
                                                        +"Wind :"+wrapper.info[0].now.wind.deg+lineSeparator;
                                                templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(),TemplateComponent.TYPE_STATIC_TEXT,null,weatherInfo);
                                                ViewStaticTextProcessor staticTextProcessor=new ViewStaticTextProcessor(ShowTemplateActivity.this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
                                                if(staticTextProcessor.getOldView()!=null)
                                                    frameLayout.removeView(staticTextProcessor.getOldView());
                                                frameLayout.addView(staticTextProcessor.generateView());
                                            }
                                        });
                                        reader.call();
                                    }
                                });
                                weatherBuilder.show();

                                break;
                            case 5:
                                AlertDialog.Builder rssAddrDialogBuilder=new AlertDialog.Builder(ShowTemplateActivity.this);
                                rssAddrDialogBuilder.setTitle("Enter RSS Feed addresss");
                                final EditText rssEditText=new EditText(ShowTemplateActivity.this);
//                                rssEditText.setText("http://rss.cnn.com/rss/edition.rss");
                                rssAddrDialogBuilder.setView(rssEditText);
                                rssAddrDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String address=rssEditText.getText().toString();

                                        new RssWorker().execute(address);

                                    }
                                });
                                rssAddrDialogBuilder.show();

                                break;


                        }
                    }
                });
                builder.show();

            }
        });


        imgPreview.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent goShowScreen = new Intent(ShowTemplateActivity.this, ShowScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list",templateViewNew.getComponentList());
                goShowScreen.putExtra("bundle", bundle);
                startActivity(goShowScreen);
                return;
            }



        });

        imgSave.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(ShowTemplateActivity.this);
                builder.setTitle("Name Your Screen");
                final EditText etScreenName=new EditText(ShowTemplateActivity.this);
                builder.setView(etScreenName);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedTemplate.setName(etScreenName.getText().toString());
                        db.createNewUserScreen(selectedTemplate);
                    }
                });
                builder.show();


            }
        });







    }

//    private FrameLayout.LayoutParams getParas(TemplateComponent currentComponent){
//        int width=currentComponent.getResolvedRight()-currentComponent.getResolvedLeft();
//        int height=currentComponent.getResolvedBottom()-currentComponent.getResolvedTop();
//        FrameLayout.LayoutParams paras=new FrameLayout.LayoutParams(width,height);
//        paras.setMargins(currentComponent.getResolvedLeft(),currentComponent.getResolvedTop(),currentComponent.getResolvedRight(),currentComponent.getResolvedBottom());
//        return paras;
//    }

//    private void setStaticText(){
//        TemplateComponent component=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
//        TextView textView=new TextView(this);
//        textView.setLayoutParams(getParas(component));
//        textView.setText(component.getSourceText());
//        frameLayout.addView(textView);
//        removePreviousViewIfExist();
//        viewRefMap.put(templateViewNew.getAreaTouched(),textView);
//
//    }

//    private void setVideo(){
//        VideoView videoView=new VideoView(ShowTemplateActivity.this);
//        TemplateComponent currentComponent=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
//        int width=currentComponent.getResolvedRight()-currentComponent.getResolvedLeft();
//        int height=currentComponent.getResolvedBottom()-currentComponent.getResolvedTop();
//        FrameLayout.LayoutParams paras=new FrameLayout.LayoutParams(width,height);
//        paras.setMargins(currentComponent.getResolvedLeft(),currentComponent.getResolvedTop(),currentComponent.getResolvedRight(),currentComponent.getResolvedBottom());
//        videoView.setLayoutParams(paras);
//        videoView.setVideoURI(currentComponent.getSourceUri());
//        frameLayout.addView(videoView);
//
//        removePreviousViewIfExist();
//        viewRefMap.put(templateViewNew.getAreaTouched(),videoView);
//        videoView.start();
//    }

//    private void setImage(){
//        ImageView imageView=new ImageView(ShowTemplateActivity.this);
//        TemplateComponent currentComponent=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
//        int width=currentComponent.getResolvedRight()-currentComponent.getResolvedLeft();
//        int height=currentComponent.getResolvedBottom()-currentComponent.getResolvedTop();
//        FrameLayout.LayoutParams paras=new FrameLayout.LayoutParams(width,height);
//        paras.setMargins(currentComponent.getResolvedLeft(),currentComponent.getResolvedTop(),currentComponent.getResolvedRight(),currentComponent.getResolvedBottom());
//        imageView.setLayoutParams(paras);
//        imageView.setImageURI(currentComponent.getSourceUri());
//        frameLayout.addView(imageView);
//
//        removePreviousViewIfExist();
//        viewRefMap.put(templateViewNew.getAreaTouched(),imageView);
//
//    }
//    private void setText(){
//        TextView textView=new TextView(ShowTemplateActivity.this);
//        TemplateComponent currentComponent=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
//        int width=currentComponent.getResolvedRight()-currentComponent.getResolvedLeft();
//        int height=currentComponent.getResolvedBottom()-currentComponent.getResolvedTop();
//        FrameLayout.LayoutParams paras=new FrameLayout.LayoutParams(width,height);
//        paras.setMargins(currentComponent.getResolvedLeft(),currentComponent.getResolvedTop(),currentComponent.getResolvedRight(),currentComponent.getResolvedBottom());
//        textView.setLayoutParams(paras);
//        textView.setText(currentComponent.getSourceText());
//        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        textView.setSelected(true);
//        textView.setSingleLine(true);
//        frameLayout.addView(textView);
//
//        removePreviousViewIfExist();
//        viewRefMap.put(templateViewNew.getAreaTouched(),textView);
//
//
//    }

//    private void removePreviousViewIfExist(){
//        TemplateComponent current=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
//        if(current.getSourceUri()!=null||current.getSourceText()!=null)
//            frameLayout.removeView(viewRefMap.get(templateViewNew.getAreaTouched()));
//    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Log.e(TAG, "onActivityResult: pick image error" );
                return;
            }
            imgUri = data.getData();
            templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(),TemplateComponent.TYPE_IMAGE,imgUri,"Image Uri "+imgUri.getPath());
            ViewImageProcessor imageProcessor=new ViewImageProcessor(this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
            if(imageProcessor.getOldView()!=null)
                frameLayout.removeView(imageProcessor.getOldView());
            frameLayout.addView(imageProcessor.generateImageView());
//            if (templateViewNew.getComponentById(templateViewNew.getAreaTouched()).getType()==TemplateComponent.TYPE_IMAGE){
//                setImage();
//            }

        }

        if (requestCode == PICK_VIDEO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Log.e(TAG, "onActivityResult: pick video error" );
                return;
            }
            videoUri= data.getData();
            templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(),TemplateComponent.TYPE_VIDEO,videoUri,"Video Uri "+videoUri.getPath());
            ViewVideoProcessor videoProcessor=new ViewVideoProcessor(this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
            if(videoProcessor.getOldView()!=null)
                frameLayout.removeView(videoProcessor.getOldView());
            frameLayout.addView(videoProcessor.generateView());
//            if(templateViewNew.getComponentById(templateViewNew.getAreaTouched()).getType()==TemplateComponent.TYPE_VIDEO){
//                setVideo();
//            }

        }
    }

    class RssWorker extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb=new StringBuilder();
            try {
                InputStream inputStream=new URL(params[0]).openConnection().getInputStream();
                sb=new StringBuilder();
                Feed feed= EarlParser.parseOrThrow(inputStream,0);
                Log.i(TAG, "Processing feed: " + feed.getTitle());
                for (Item item : feed.getItems()) {
                    String title = item.getTitle();
                    sb.append(item.getDescription());
                    sb.append("      ");
                    Log.i(TAG, "Item title: " + (title == null ? "N/A" : title));
                    Log.i(TAG, "Item content: "+item.getDescription());
                }
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ",e );
            } catch (DataFormatException e) {
                Log.e(TAG, "doInBackground: ",e );
            } catch (XmlPullParserException e) {
                Log.e(TAG, "doInBackground: ",e );
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(),TemplateComponent.TYPE_TEXT,null,s);
            ViewTickerTextProcessor tickerTextProcessor=new ViewTickerTextProcessor(ShowTemplateActivity.this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
            if(tickerTextProcessor.getOldView()!=null)
                frameLayout.removeView(tickerTextProcessor.getOldView());
            frameLayout.addView(tickerTextProcessor.generateView());

        }
    }
}
