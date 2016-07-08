package com.example.mfusion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Type;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mfusion.model.TemplateComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTextPropertyActivity extends Activity {
    private static final String TAG = "EditTextPropertyActivit";

    @BindView(R.id.edit_text_prop_rb_pypats)RadioButton rbFontTypePypats;
    @BindView(R.id.edit_text_prop_rb_vtks)RadioButton rbFontTypeVtks;
    @BindView(R.id.edit_text_prop_tv_demo)TextView tvDemo;
    @BindView(R.id.edit_text_prop_rbg)RadioGroup rbgFontType;
    @BindView(R.id.edit_text_prop_size_seek_bar)SeekBar sizeSeekBar;
    @BindView(R.id.edit_text_prop_sw_bold)Switch swBold;
    @BindView(R.id.edit_text_prop_sw_italic)Switch swItalic;
    @BindView(R.id.edit_text_prop_et_color)EditText etColor;
    @BindView(R.id.edit_text_prop_btn_confirm)Button btnConfirm;

    private String text;
    private String fontType;
    public static final int STYLE_BOLD=1;
    public static final int STYLE_ITALIC=2;
    public static final int STYLE_BOLD_ITALIC=STYLE_BOLD+STYLE_ITALIC;
    private float fontSize;
    private boolean bold;
    private boolean italic;
    private int color;

    private Typeface pypats;
    private Typeface vtks;

    private TemplateComponent editingComponent;
    private int fontStyle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_text_property);
        ButterKnife.bind(this);
        //get editing component from intent
        editingComponent=(TemplateComponent) getIntent().getExtras().get("component");
        //init text view
        if(editingComponent.getSourceText()!=null)
        {
            text=editingComponent.getSourceText();
            tvDemo.setText(text);
        }
        else{
            text="Your Text Here";
            tvDemo.setText(text);
        }

        initVariables();
        setUpRadioButton();
        setUpRbg();
        setUpSeekBar();
        setUpSwitches();
        setUpColorEt();

    }
    private void initVariables(){
        fontSize=20;

        fontType=null;
        fontStyle=Typeface.NORMAL;
        color=Color.BLACK;
    }

    private void setUpColorEt(){
        etColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    color=Color.parseColor(""+s);
                }
                catch (Exception ex){
                    Log.e(TAG, "onTextChanged: color parsing error",ex );
                }
                if(color<0)
                    tvDemo.setTextColor(color);

                    
                
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setUpRadioButton(){
        pypats = Typeface.createFromAsset(getAssets(),"Pypats.ttf");
        vtks = Typeface.createFromAsset(getAssets(),"Vtks Blank.ttf");
        rbFontTypePypats.setTypeface(pypats);
        rbFontTypeVtks.setTypeface(vtks);
    }
    private void setUpRbg(){
        rbgFontType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.edit_text_prop_rb_pypats:
                        fontType="Pypats.ttf";
                        tvDemo.setTypeface(pypats);
                        break;
                    case R.id.edit_text_prop_rb_vtks:
                        fontType="Vtks Blank.ttf";
                        tvDemo.setTypeface(vtks);
                        break;
                    case R.id.edit_text_prop_rb_default:
                        fontType=null;
                        tvDemo.setTypeface(null);
                        break;
                }
            }
        });
    }
    private void setUpSeekBar(){
        sizeSeekBar.setProgress(20);
        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDemo.setTextSize((float)progress);
                fontSize=(int)progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setUpSwitches(){
        swBold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bold=isChecked;
                setDemoStyle();
            }
        });

        swItalic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                italic=isChecked;
                setDemoStyle();
            }
        });
    }

    private void setDemoStyle(){
        if(!(bold||italic)) {
            tvDemo.setTypeface(Typeface.create(tvDemo.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
            fontStyle = Typeface.NORMAL;
        }
        else{
            if(bold&&italic){
                fontStyle= Typeface.BOLD_ITALIC;

            }

            else{
                if(bold)
                    fontStyle=Typeface.BOLD;
                if(italic)
                    fontStyle=Typeface.ITALIC;
            }
            tvDemo.setTypeface(tvDemo.getTypeface(),fontStyle);

        }






    }
    @OnClick(R.id.edit_text_prop_tv_demo)
    void editText(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Enter your text");
        final EditText editText=new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text=editText.getText().toString();
                tvDemo.setText(text);
            }
        });
        builder.show();
    }

    @OnClick(R.id.edit_text_prop_btn_confirm)
    void confirm(){


        editingComponent.setSourceText(text);
        editingComponent.setFontColor(color);
        editingComponent.setFontStyle(fontStyle);
        editingComponent.setFontSize(fontSize);
        editingComponent.setFontType(fontType);
        Intent returnIntent=new Intent();
        returnIntent.putExtra("edited",editingComponent);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
