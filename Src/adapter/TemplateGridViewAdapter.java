package com.example.mfusion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateViewNew;
import com.example.mfusion.model.MyTemplate;

import java.util.List;

public class TemplateGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<MyTemplate> templates;

    static class ViewHolder{
        TemplateViewNew templateView;
        TextView tvName;
    }

    public TemplateGridViewAdapter(Context context,List<MyTemplate> templates) {
        this.context=context;
        this.templates=templates;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vh=new ViewHolder();
            convertView=inflater.inflate(R.layout.template_gridview_view,parent,false);
            vh.templateView=(TemplateViewNew)convertView.findViewById(R.id.template_gridview_templateview);
            vh.tvName=(TextView)convertView.findViewById(R.id.template_gridview_tv_name);
            convertView.setTag(vh);
        }
        else{
            vh=(ViewHolder) convertView.getTag();
        }
        vh.templateView.setTemplate(templates.get(position));
//        vh.tvName.setText("Name");
        vh.tvName.setText(templates.get(position).getName());

        return convertView;
    }
}
