package com.example.mfusion.Template;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mfusion.EditTextPropertyActivity;
import com.example.mfusion.R;
import com.example.mfusion.ShowTemplateActivity;
import com.example.mfusion.adapter.TemplateGridViewAdapter;
import com.example.mfusion.model.MyTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TemplateFragment extends Fragment{

	private TemplateViewNew templateViewNew;

	private ImageView btnNext;
	private ImageView btnPrevious;
	private ViewPager viewPager;
	private static int currentPosition;
	private List<MyTemplate> templates;

	private TemplateDAO tDAO;
	private static final String TAG="TemplateFragment";

	@BindView(R.id.template_list_gridview)GridView gvList;
	@BindView(R.id.template_list_img_new_template)ImageView imgNewTemplate;
	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_test_template_list, container, false);
		ButterKnife.bind(this,rootView);

		templateViewNew = (TemplateViewNew) rootView.findViewById(R.id.templateView);
//		btnNext = (ImageView) rootView.findViewById(R.id.btnNext);
//		btnPrevious = (ImageView) rootView.findViewById(R.id.btnPrevious);

		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);

		tDAO = new TemplateDAO(getActivity());

        updateAdapter();

		gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent goShow= new Intent(container.getContext(),ShowTemplateActivity.class);
				goShow.putExtra("tid",templates.get(i).getId());
				startActivity(goShow);

			}
		});



		return rootView;

	}
    private void updateAdapter(){
        templates=tDAO.getAllTemplate();


        TemplateGridViewAdapter adapter=new TemplateGridViewAdapter(getActivity(),templates);
        gvList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    @OnClick(R.id.template_list_img_new_template)
	void newTemplate(){
        Intent goAddTemplate=new Intent(getActivity(),AddNewTemplateActivity.class);
        startActivity(goAddTemplate);
	}










}
