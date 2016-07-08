package com.example.mfusion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import com.example.mfusion.Template.TemplateDAO;
import com.example.mfusion.Template.TemplateViewNew;
import com.example.mfusion.Template.ViewImageProcessor;
import com.example.mfusion.Template.ViewStaticTextProcessor;
import com.example.mfusion.Template.ViewTickerTextProcessor;
import com.example.mfusion.Template.ViewVideoProcessor;
import com.example.mfusion.Utility.ImageUtil;
import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowTemplateActivity extends Activity {

    private final String TAG="ShowTemplateActivity";
    private static final int RESIZE_PERCENTAGE=5;
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
    //@BindView(R.id.imgSaveAs)ImageView imgSaveAs;
    @BindView(R.id.show_template_img_adjust)ImageView imgAdjust;
    //@BindView(R.id.show_template_btn_edit_size)Button btnEditSize;
    private boolean editMode;
    private boolean imgClick;

    private HashMap<Integer,View> viewRefMap;

    private String weatherCity;
    private String lineSeparator;
    private Handler dateHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_template);
        ButterKnife.bind(this);
//        imgEdit.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.edit_btn,RESIZE_PERCENTAGE));
//        imgPreview.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.preview_btn,RESIZE_PERCENTAGE));
//        imgSave.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.save_template,RESIZE_PERCENTAGE));
        //imgSaveAs.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.save_as,RESIZE_PERCENTAGE));
        //imgDelete.setImageBitmap(ImageUtil.resizeImage(this,R.drawable.delete_btn,RESIZE_PERCENTAGE));

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
//                Log.i(TAG, "onClick: templateview onclick triggered, clicked area="+templateView.getAreaSelectedIndex());
                //templateView.setComponentDetail(1,1,null,"abc");
                AlertDialog.Builder builder=new AlertDialog.Builder(ShowTemplateActivity.this);
                builder.setTitle("Select Component");
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

                                Intent goEditTextProp = new Intent(ShowTemplateActivity.this, EditTextPropertyActivity.class);
                                TemplateComponent selected=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
                                selected.setType(TemplateComponent.TYPE_TEXT);
                                goEditTextProp.putExtra("component",selected );
                                startActivityForResult(goEditTextProp, EDIT_TEXT_PROP);

                                break;
                            case 2:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PICK_IMAGE);

                                break;
                            case 3:

                                long date = System.currentTimeMillis();
                                final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd yyyy "+lineSeparator+"h:mm:ss a");
                                String dateString=simpleDateFormat.format(date);


                                templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(), TemplateComponent.TYPE_DATE,null,dateString);
                                ViewStaticTextProcessor staticTextProcessor=new ViewStaticTextProcessor(ShowTemplateActivity.this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
                                staticTextProcessor.addOrUpdateLayout(frameLayout);
                                final TextView dateView=(TextView) staticTextProcessor.getOldView();
                                dateHandler = new Handler(){
                                    @Override
                                    public void handleMessage(Message msg) {
                                        super.handleMessage(msg);
                                        if(msg.arg1==1)
                                            dateView.setText(simpleDateFormat.format(new Date()));
                                    }
                                };
                                new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        while(true){
                                            Message msg=dateHandler.obtainMessage();
                                            msg.arg1=1;
                                            dateHandler.sendMessage(msg);
                                            try {
                                                sleep(1000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }.start();



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
                                                staticTextProcessor.addOrUpdateLayout(frameLayout);
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
                                rssEditText.setText("http://rss.cnn.com/rss/edition.rss");
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
                        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();


            }
        });
    }

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
            imageProcessor.addOrUpdateLayout(frameLayout);
        }

        if (requestCode == PICK_VIDEO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Log.e(TAG, "onActivityResult: pick video error" );
                return;
            }
            videoUri= data.getData();
            templateViewNew.setComponentDetail(templateViewNew.getAreaTouched(),TemplateComponent.TYPE_VIDEO,videoUri,"Video Uri "+videoUri.getPath());
            ViewVideoProcessor videoProcessor=new ViewVideoProcessor(this,templateViewNew.getComponentById(templateViewNew.getAreaTouched()));
            videoProcessor.addOrUpdateLayout(frameLayout);
//            if(templateViewNew.getComponentById(templateViewNew.getAreaTouched()).getType()==TemplateComponent.TYPE_VIDEO){
//                setVideo();
//            }

        }

        if(requestCode==EDIT_TEXT_PROP&&resultCode==RESULT_OK){
            if(data!=null){
                TemplateComponent editedComponent=(TemplateComponent) data.getExtras().get("edited");
                templateViewNew.setComponent(templateViewNew.getAreaTouched(),editedComponent);
                ViewTickerTextProcessor tickerTextProcessor=new ViewTickerTextProcessor(this,editedComponent);
                tickerTextProcessor.addOrUpdateLayout(frameLayout);
            }
        }
    }

    @OnClick(R.id.show_template_img_adjust)
    void editSize(){
        if(templateViewNew.getAreaSelectedIndex()>-1){

            String info="Canvas width : "+templateViewNew.getViewWidth()+lineSeparator+"Canvas height : "+templateViewNew.getViewHeight();
            AlertDialog.Builder setSizeDialog=new AlertDialog.Builder(this);
            setSizeDialog.setTitle("Set Component Size");
            LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View dialogView=inflater.inflate(R.layout.edit_component_size_dialog_view,null);
            TextView tvInfo=(TextView)dialogView.findViewById(R.id.edit_component_size_dialog_tv_info);
            final EditText etX=(EditText) dialogView.findViewById(R.id.edit_component_size_dialog_et_x);
            final EditText etY=(EditText)dialogView.findViewById(R.id.edit_component_size_dialog_et_y);
            final EditText etWidth=(EditText)dialogView.findViewById(R.id.edit_component_size_dialog_et_width);
            final EditText etHeight=(EditText)dialogView.findViewById(R.id.edit_component_size_dialog_et_height);
            tvInfo.setText(info);
            setSizeDialog.setView(dialogView);
            setSizeDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    float x=Float.valueOf(etX.getText().toString());
                    float y=Float.valueOf(etY.getText().toString());
                    float width=Float.valueOf(etWidth.getText().toString());
                    float height=Float.valueOf(etHeight.getText().toString());
                    templateViewNew.setSelectedComponentSize(x,y,width,height);
                }
            });

            setSizeDialog.show();
        }
        else{
            Toast.makeText(ShowTemplateActivity.this, "Please select one component to edit first", Toast.LENGTH_SHORT).show();
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

            TemplateComponent selected=templateViewNew.getComponentById(templateViewNew.getAreaTouched());
            selected.setType(TemplateComponent.TYPE_TEXT);
            selected.setSourceText(s);
            Intent goEdit=new Intent(ShowTemplateActivity.this,EditTextPropertyActivity.class);
            goEdit.putExtra("component",selected);
            startActivityForResult(goEdit,EDIT_TEXT_PROP);

        }
    }
}
