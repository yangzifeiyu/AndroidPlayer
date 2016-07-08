package com.example.mfusion.Template;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfusion.R;
import com.example.mfusion.model.MyTemplate;
import com.example.mfusion.model.TemplateComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewTemplateActivity extends Activity {
    private final String lineSeparator=System.getProperty("line.separator");

    private TemplateDAO templateDAO;

    @BindView(R.id.add_template_img_add)ImageView imgAdd;
    @BindView(R.id.add_template_img_minus)ImageView imgMinus;
    @BindView(R.id.add_template_img_adjust)ImageView imgAdjust;
    @BindView(R.id.add_template_img_save)ImageView imgSave;
    @BindView(R.id.add_template_template_view)TemplateViewNew templateView;

    private MyTemplate newTemplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_new_template);
        ButterKnife.bind(this);
        templateDAO=new TemplateDAO(this);

        newTemplate=new MyTemplate();
        templateView.setTemplate(newTemplate);
        templateView.setEditMode(true);
        templateView.setBackgroundColor(Color.LTGRAY);

    }

    @OnClick(R.id.add_template_img_add)
    void addComponent(){
        templateView.addNewComponent();
    }
    @OnClick(R.id.add_template_img_minus)
    void removeSelectedComponent(){
        if(!templateView.removeSelectedComponent()){
            Toast.makeText(AddNewTemplateActivity.this, "Please select a component to remove", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.add_template_img_adjust)
    void showAdjustDialog(){
        if(templateView.getAreaSelectedIndex()>-1){
            TemplateComponent selected=templateView.getComponentById(templateView.getAreaSelectedIndex());
            int canvasWidth=templateView.getViewWidth();
            int canvasHeight=templateView.getViewHeight();
            String info="Canvas width : "+canvasWidth+lineSeparator+"Canvas height : "+canvasHeight;
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
            etX.setText(String.valueOf(selected.getLeft()*canvasWidth));
            etY.setText(String.valueOf(selected.getTop()*canvasHeight));
            etWidth.setText(String.valueOf((selected.getRight()-selected.getLeft())*canvasWidth));
            etHeight.setText(String.valueOf((selected.getBottom()-selected.getTop())*canvasHeight));
            setSizeDialog.setView(dialogView);
            setSizeDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    float x=Float.valueOf(etX.getText().toString());
                    float y=Float.valueOf(etY.getText().toString());
                    float width=Float.valueOf(etWidth.getText().toString());
                    float height=Float.valueOf(etHeight.getText().toString());
                    templateView.setSelectedComponentSize(x,y,width,height);
                }
            });

            setSizeDialog.show();
        }
        else{
            Toast.makeText(AddNewTemplateActivity.this, "Please select one component to edit first", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.add_template_img_save)
    void saveTemplate(){
        AlertDialog.Builder saveDialog=new AlertDialog.Builder(this);
        saveDialog.setTitle("Give it a name");
        final EditText etSaveName=new EditText(this);
        saveDialog.setView(etSaveName);
        saveDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=etSaveName.getText().toString();
                newTemplate.setName(name);
                templateDAO.saveTemplate(newTemplate);
                finish();
            }
        });
        saveDialog.show();
    }

}
