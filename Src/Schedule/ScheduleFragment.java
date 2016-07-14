package com.example.mfusion.Schedule;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateDAO;
import com.example.mfusion.adapter.TemplateListViewAdapter;
import com.example.mfusion.model.MyTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

    private ScheduleBlockView blockView;
    @BindView(R.id.tv_1) TextView tv1;
    @BindView(R.id.tv_2) TextView tv2;
    @BindView(R.id.tv_3) TextView tv3;
    @BindView(R.id.listview) ListView tList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);


        blockView = (ScheduleBlockView) rootView.findViewById(R.id.block);
        tList = (ListView) rootView.findViewById(R.id.listview);

//		setContentView(R.layout.fragment_schedule);

       ButterKnife.bind(this,rootView);

        TemplateDAO templateDAO = new TemplateDAO(getActivity());
        List<MyTemplate> templates = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            templates.add(templateDAO.getMyTemplateById(i));


            TemplateListViewAdapter adapter = new TemplateListViewAdapter(getActivity(), templates);
            tList.setAdapter(adapter);

            tList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String currentItem = String.valueOf(tList.getItemAtPosition(position));
                    View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                    ClipData.Item item = new ClipData.Item(currentItem);
                    ClipData data = new ClipData("desc", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                    view.startDrag(data, builder, null, 0);
                    return false;
                }


            });

         setUpDragListener();
        }

        return rootView;
    }

    private void setUpDragListener() {
        MyDragListener dragListener = new MyDragListener();

            tv1.setOnDragListener(dragListener);
            tv2.setOnDragListener(dragListener);
            tv3.setOnDragListener(dragListener);
    }


}



