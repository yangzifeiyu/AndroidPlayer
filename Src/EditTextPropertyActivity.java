package com.example.mfusion;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    private String fontType;
    private float fontSize;
    private boolean bold;
    private boolean italic;
    private int color;

    private Typeface pypats;
    private Typeface vtks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_text_property);
        ButterKnife.bind(this);

        setUpRadioButton();
        setUpRbg();
        setUpSeekBar();
        setUpSwitches();
        setUpColorEt();

    }
    private void initVariables(){

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
                        fontType="Pypats";
                        tvDemo.setTypeface(pypats);
                        break;
                    case R.id.edit_text_prop_rb_vtks:
                        fontType="Vtks Blank";
                        tvDemo.setTypeface(vtks);
                        break;
                    case R.id.edit_text_prop_rb_default:
                        fontType="Default";
                        tvDemo.setTypeface(null);
                        break;
                }
            }
        });
    }
    private void setUpSeekBar(){
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

        if(bold&&italic)
            tvDemo.setTypeface(tvDemo.getTypeface(),Typeface.BOLD_ITALIC);
        else{
            if(bold)
                tvDemo.setTypeface(tvDemo.getTypeface(),Typeface.BOLD);
            if(italic)
                tvDemo.setTypeface(tvDemo.getTypeface(),Typeface.ITALIC);
        }
        if(!(bold||italic))
            tvDemo.setTypeface(Typeface.create(tvDemo.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);


    }
}
