package com.example.mfusion.Schedule;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateDAO;
import com.example.mfusion.adapter.TemplateListViewAdapter;
import com.example.mfusion.model.MyTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

/*
    private ScheduleBlockView blockView;

    @BindView(R.id.tv_1) TextView tv1;
    @BindView(R.id.tv_2) TextView tv2;
    @BindView(R.id.tv_3) TextView tv3;
*/
    @BindView(R.id.listview) ListView tList;

    @BindView(R.id.monday) WeekDayLayout monday;
    @BindView(R.id.tuesday) WeekDayLayout tuesday;
    @BindView(R.id.wednesday) WeekDayLayout wednesday;
    @BindView(R.id.thursday) WeekDayLayout thursday;
    @BindView(R.id.friday) WeekDayLayout friday;
    @BindView(R.id.saturday) WeekDayLayout saturday;
    @BindView(R.id.sunday) WeekDayLayout sunday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);


        //blockView = (ScheduleBlockView) rootView.findViewById(R.id.block);
        tList = (ListView) rootView.findViewById(R.id.listview);

//		setContentView(R.layout.fragment_schedule);

        ButterKnife.bind(this,rootView);

        initweekday();

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
            /*
            tv1.setOnDragListener(dragListener);
            tv2.setOnDragListener(dragListener);
            tv3.setOnDragListener(dragListener);
            */
    }
    private void initweekday() {
        Resources res =getResources();
        String[] week_name = res.getStringArray(R.array.week_name);

        monday.SetTitle(week_name[0]);
        monday.SetDragListener();
        monday.SetOnclickListener();

        tuesday.SetTitle(week_name[1]);
        tuesday.SetDragListener();
        tuesday.SetOnclickListener();

        wednesday.SetTitle(week_name[2]);
        wednesday.SetDragListener();
        wednesday.SetOnclickListener();

        thursday.SetTitle(week_name[3]);
        thursday.SetDragListener();
        thursday.SetOnclickListener();

        friday.SetTitle(week_name[4]);
        friday.SetDragListener();
        friday.SetOnclickListener();

        saturday.SetTitle(week_name[5]);
        saturday.SetDragListener();
        saturday.SetOnclickListener();

        sunday.SetTitle(week_name[6]);
        sunday.SetDragListener();
        sunday.SetOnclickListener();
    }
}


