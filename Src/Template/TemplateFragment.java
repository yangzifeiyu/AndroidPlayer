package com.example.mfusion.Template;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mfusion.EditTextPropertyActivity;
import com.example.mfusion.R;
import com.example.mfusion.ShowTemplateActivity;
import com.example.mfusion.model.MyTemplate;

import java.util.ArrayList;


public class TemplateFragment extends Fragment implements View.OnClickListener{

	private TemplateViewNew templateViewNew;

	private ImageView btnNext;
	private ImageView btnPrevious;
	private ViewPager viewPager;
	private static int currentPosition;
	private ArrayList<MyTemplate> templates;

	private TemplateDAO tDAO;
	private static final String TAG="TemplateFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, final ViewGroup container,
							 Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_template, container, false);


		templateViewNew = (TemplateViewNew) rootView.findViewById(R.id.templateView);
		btnNext = (ImageView) rootView.findViewById(R.id.btnNext);
		btnPrevious = (ImageView) rootView.findViewById(R.id.btnPrevious);

		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);

		tDAO = new TemplateDAO(getActivity());

		templates = new ArrayList<>();
		templates.add(tDAO.getMyTemplateById(0));
		templates.add(tDAO.getMyTemplateById(1));
		templates.add(tDAO.getMyTemplateById(2));
		templates.add(tDAO.getMyTemplateById(3));
		templates.add(tDAO.getMyTemplateById(4));
		templates.add(tDAO.getMyTemplateById(5));
//		templates.add(new MyTemplate(new TemplateComponent(0,0,0.5f,1), new TemplateComponent(0.5f,0.5f,1,1), new TemplateComponent(0.5f,0,1,0.5f)));
//		templates.add(new MyTemplate(new TemplateComponent(0,0,1,0.2f),new TemplateComponent(0,0.2f,0.5f,1),new TemplateComponent(0.5f,0.2f,1,1)));
//		templates.add(new MyTemplate(new TemplateComponent(0,0,0.5f,0.5f),new TemplateComponent(0.5f,0,1,0.5f),new TemplateComponent(0,0.5f,0.5f,1),new TemplateComponent(0.5f,0.5f,1,1)));
//
//		currentPosition=0;
//		templateView.addTemplate(templates.get(currentPosition));

		templateViewNew.setBackgroundColor(Color.LTGRAY);

		btnPrevious.setOnClickListener(this);
		btnNext.setOnClickListener(this);

//		templateView.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent goShow=new Intent(container.getContext(),ShowTemplateActivity.class);
//				goShow.putExtra("tid",templates.get(currentPosition).getId());
//				startActivity(goShow);
//				return true;
//			}
//		});

		templateViewNew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goShow=new Intent(container.getContext(),ShowTemplateActivity.class);
				goShow.putExtra("tid",templates.get(currentPosition).getId());
				startActivity(goShow);
				return;
			}
		});

		templateViewNew.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Intent goEditTextProp=new Intent(getActivity(),EditTextPropertyActivity.class);


				startActivity(goEditTextProp);

				return false;
			}
		});

//		templateView.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				Intent goTest=new Intent(container.getContext(), TestActivity.class);
//				goTest.putExtra("tid",templates.get(currentPosition).getId());
//				startActivity(goTest);
//				return false;
//			}
//		});


//		templateView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.i(TAG, "onClick: triggered");
//			}
//		});

		return rootView;

	}







    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentPosition=0;
        templateViewNew.setTemplate(templates.get(currentPosition));
    }

    public void onClick(View v) {
		switch (v.getId()){
			case R.id.btnNext:
				//templateView.clearComponent();
				currentPosition++;
				checkCounter();
				templateViewNew.setTemplate(templates.get(currentPosition));

				break;
			case R.id.btnPrevious:
				//templateView.clearComponent();
				currentPosition--;
				checkCounter();
				templateViewNew.setTemplate(templates.get(currentPosition));
				break;
		}

	}

	private void checkCounter() {
		int maxIndex=templates.size()-1;
		if (currentPosition>maxIndex)
			currentPosition=0;
		if (currentPosition<0) {
			currentPosition = maxIndex;
		}
	}

}
