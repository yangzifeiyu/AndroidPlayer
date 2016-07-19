package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateViewNew;
import com.example.mfusion.model.MyTemplate;

import java.util.ArrayList;
import java.util.List;

public class TemplateGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<MyTemplate> templates;
    private List<Integer> selectedList;



    private boolean editingMode;

    static class ViewHolder{
        TemplateViewNew templateView;
        TextView tvName;
        CheckBox cb;
    }

    public TemplateGridViewAdapter(Context context,List<MyTemplate> templates) {
        this.context=context;
        this.templates=templates;
        selectedList=new ArrayList<>();
        editingMode=false;
    }

    @Override
    public int getCount() {
        return templates.size();
    }

    @Override
    public Object getItem(int position) {
        return templates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return templates.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.template_gridview_view,parent,false);
            vh.templateView=(TemplateViewNew)convertView.findViewById(R.id.template_gridview_templateview);
            vh.tvName=(TextView)convertView.findViewById(R.id.template_gridview_tv_name);
            vh.cb=(CheckBox)convertView.findViewById(R.id.template_gridview_cb);
            convertView.setTag(vh);
        }
        else{
            vh=(ViewHolder) convertView.getTag();
        }
        vh.templateView.setTemplate(templates.get(position));
//        vh.tvName.setText("Name");
        vh.tvName.setText(templates.get(position).getName());
        vh.cb.setFocusable(false);
        vh.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    selectedList.add(templates.get(position).getId());
                else
                    selectedList.remove(templates.get(position).getId());
            }
        });
        if(!editingMode)
            vh.cb.setVisibility(View.INVISIBLE);

        return convertView;
    }

    public boolean isEditingMode() {
        return editingMode;
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
    }

    public List<Integer> getSelectedList() {
        return selectedList;
    }
}
