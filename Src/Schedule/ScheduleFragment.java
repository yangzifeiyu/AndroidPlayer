package com.example.mfusion.Schedule;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mfusion.R;
import com.example.mfusion.Template.TemplateDAO;
import com.example.mfusion.adapter.TemplateListViewAdapter;
import com.example.mfusion.model.MyTemplate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ScheduleFragment extends Fragment {

	private ScheduleBlockView blockView;
	private View mDrapView;
	private GestureDetector mGestureDetector;
	ListView tList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

		blockView = (ScheduleBlockView) rootView.findViewById(R.id.block);
		tList = (ListView) rootView.findViewById(R.id.listview);

		mGestureDetector = new GestureDetector(getActivity(), new DrapGestureListener());

//		setContentView(R.layout.fragment_schedule);

		ButterKnife.bind(getActivity());

		TemplateDAO templateDAO = new TemplateDAO(getActivity());
		List<MyTemplate> templates = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			templates.add(templateDAO.getMyTemplateById(i));


			TemplateListViewAdapter adapter = new TemplateListViewAdapter(getActivity(), templates);
			tList.setAdapter(adapter);
		}


		tList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
				tList.getItemAtPosition(myItemInt);
				if (myItemInt == 0) {

					Toast.makeText(getActivity(), "Selected 1", Toast.LENGTH_LONG).show();

				}

				else  if (myItemInt == 1) {
					Toast.makeText(getActivity(), "Selected 2", Toast.LENGTH_LONG).show();
				}

				else  if (myItemInt == 2) {
					Toast.makeText(getActivity(), "Selected 3", Toast.LENGTH_LONG).show();
				}
				else  if (myItemInt == 3) {
					Toast.makeText(getActivity(), "Selected 4", Toast.LENGTH_LONG).show();
				}
				else  if (myItemInt == 4) {
					Toast.makeText(getActivity(), "Selected 5", Toast.LENGTH_LONG).show();
				}
				else  if (myItemInt == 5) {
					Toast.makeText(getActivity(), "Selected 6", Toast.LENGTH_LONG).show();
				}



			}
		});

		return rootView;
	}



	private void bindDrapListener(int id) {
		View v = mDrapView.findViewById(R.id.listview);
		v.setOnTouchListener(mOnTouchListener);
		v.setOnDragListener(mOnDragListener);
	}

	private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			mDrapView = v;

			if (mGestureDetector.onTouchEvent(event))
				return true;

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_UP:

					break;
			}

			return false;
		}
	};

	private View.OnDragListener mOnDragListener = new View.OnDragListener() {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					v.setAlpha(0.5F);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					v.setAlpha(1F);
					break;
				case DragEvent.ACTION_DROP:
					View view = (View) event.getLocalState();
					for (int i = 0, j = tList.getChildCount(); i < j; i++) {
						if (tList.getChildAt(i) == v) {
							tList.removeView(view);
							tList.addView(view, i);
							break;
						}
					}
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					v.setAlpha(1F);
				default:
					break;
			}
			return true;
		}
	};

	private class DrapGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			super.onLongPress(e);
			ClipData data = ClipData.newPlainText("", "");
			MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(
					mDrapView);
			mDrapView.startDrag(data, shadowBuilder, mDrapView, 0);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

	private class MyDragShadowBuilder extends View.DragShadowBuilder {

		private final WeakReference<View> mView;

		public MyDragShadowBuilder(View view) {
			super(view);
			mView = new WeakReference<View>(view);
		}

		@Override
		public void onDrawShadow(Canvas canvas) {
			canvas.scale(1.5F, 1.5F);
			super.onDrawShadow(canvas);
		}

		@Override
		public void onProvideShadowMetrics(Point shadowSize,
										   Point shadowTouchPoint) {
			// super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);

			final View view = mView.get();
			if (view != null) {
				shadowSize.set((int) (view.getWidth() * 1.5F),
						(int) (view.getHeight() * 1.5F));
				shadowTouchPoint.set(shadowSize.x / 2, shadowSize.y / 2);
			} else {
				// Log.e(View.VIEW_LOG_TAG,
				// "Asked for drag thumb metrics but no view");
			}
		}
	}
}

