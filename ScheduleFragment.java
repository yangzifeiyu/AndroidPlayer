package com.example.mfusion.Schedule;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfusion.R;

import java.util.Calendar;

public class ScheduleFragment extends Fragment {

//	private ScheduleView scheduleView;
//	private ArrayList<MySchedule> schedule;
//	private static int currentPosition;
	Calendar calendar = Calendar.getInstance();
	TextView show;
	ImageButton play,save,saveas,delete;
	Spinner listsp;
	Spinner modesp;
	ArrayAdapter<CharSequence> adapter1,adapter2;
	private int mYear, mMonth, mDay;
//	DatabaseHelper myDb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

//		scheduleView=(ScheduleView) rootView.findViewById(R.id.scheduleView);
//		schedule=new ArrayList<>();


		show = (TextView) rootView.findViewById(R.id.tvshow);

		Button weekbtn=(Button) rootView.findViewById(R.id.week_btn);
		weekbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				mYear = calendar.get(Calendar.YEAR);
				mMonth = calendar.get(Calendar.MONTH);
				mDay = calendar.get(Calendar.DAY_OF_MONTH);

				// Launch Date Picker Dialog
				DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
										  int monthOfYear, int dayOfMonth) {
						// Display Selected date in textbox
						show.setText("Select date is: " + dayOfMonth+ "/" + monthOfYear + "/" + year);

					}
				}, mYear, mMonth, mDay);
				dpd.show();

			}
		});

		modesp=(Spinner) rootView.findViewById(R.id.sp_mode);
		adapter1=ArrayAdapter.createFromResource(getActivity(),R.array.mode_names,android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		modesp.setAdapter(adapter1);
		modesp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(getActivity(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		} );

		listsp=(Spinner) rootView.findViewById(R.id.sp_list);
		adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.list_names,android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listsp.setAdapter(adapter2);
		listsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//				ArrayList<String> playlist=new ArrayList<>();
//				displaysp = (Spinner) view.findViewById(R.id.sp_display);
//				SQLiteDatabase sqldb = ScheduleController.openDataBase();,
//				Cursor cr = sqldb.rawQuery("select Name from employee",null);
//				if (cr.moveToFirst()) {
//					do {
//						playlist.add(cr.getString(0).toString());
//						Toast.makeText(this,cr.getString(0).toString(),Toast.LENGTH_LONG).show();
//						ArrayAdapter <String> a= new ArrayAdapter(this, android.R.layout.simple_spinner_item,playlist);
//						a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//						displaysp.setAdapter(a);
//					} while (cr.moveToNext());
//
//				}

				Toast.makeText(getActivity(),parent.getItemAtPosition(position)+" is selected",Toast.LENGTH_LONG).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		} );


		play=(ImageButton)rootView.findViewById(R.id.ib_play);
		play.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		});

		save=(ImageButton)rootView.findViewById(R.id.ib_save);
		save.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		});

		saveas=(ImageButton)rootView.findViewById(R.id.ib_saveas);
		saveas.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		});

		delete=(ImageButton)rootView.findViewById(R.id.ib_delete);
		delete.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {

			}
		});

		return rootView;
	}


	public void onClick(View v) {

	}
}
