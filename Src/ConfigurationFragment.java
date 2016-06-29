package com.example.mfusion;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mfusion.Database.DBController;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConfigurationFragment extends Fragment {
    ViewPager viewPager;
    Button Continue, Exit, Check, Delete, Sets, Setw;
    EditText pass;
    TextView status, tvtime, tvwtime;

    private RadioGroup radioGroup, radioGroup2;
    private RadioButton Landscape, Portrait, Yes, No;

    TimePickerDialog timePickerDialog;

    DBController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        controller = new DBController(getActivity(), "", null, 1);

        View rootView = inflater.inflate(R.layout.fragment_configuration, container, false);

        radioGroup = (RadioGroup) rootView.findViewById(R.id.myRadioGroup);
        radioGroup2 = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        Portrait = (RadioButton) rootView.findViewById(R.id.portrait);
        Landscape = (RadioButton) rootView.findViewById(R.id.landscape);
        Yes = (RadioButton) rootView.findViewById(R.id.yes);
        No = (RadioButton) rootView.findViewById(R.id.no);

        Continue = (Button) rootView.findViewById(R.id.btnContinue);
        Check = (Button) rootView.findViewById(R.id.btnCheck);
        Sets = (Button) rootView.findViewById(R.id.btnShutdown);
        Setw = (Button) rootView.findViewById(R.id.btnWakeup);

        Delete = (Button) rootView.findViewById(R.id.btnDelete);
        status = (TextView) rootView.findViewById(R.id.txtStatusPa);
        tvtime = (TextView) rootView.findViewById(R.id.tvTime);
        tvwtime = (TextView) rootView.findViewById(R.id.tvW);


        pass = (EditText) rootView.findViewById(R.id.etPassword);

        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);

        Landscape.setChecked(true);
        Portrait.setChecked(false);
        No.setChecked(true);
        Yes.setChecked(false);

        Continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                continues();
            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                delete();

            }
        });


        Sets.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                openTimePickerDialog(true);


            }
        });

        Setw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                openTimePickerDialog2(true);


            }
        });


        Check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                check();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.landscape) {
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                    Toast.makeText(getActivity(), "Landscape",
//                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.portrait) {

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    Toast.makeText(getActivity(), "Portrait",
//                            Toast.LENGTH_SHORT).show();

                }

            }

        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yes) {
//                    Toast.makeText(getActivity(), "Auto Start Enable",
//                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.no) {

//                    Toast.makeText(getActivity(), "Auto Start Disable",
//                            Toast.LENGTH_SHORT).show();

                }

            }

        });


        retd();//method for retreving value from database and set the result at the textview or radiobutton

        return rootView;


    }//oncreate

    private void retd() {


        controller.list_setting6(tvtime,tvwtime,pass,radioGroup,radioGroup2);//Called DBController method listsettings6 to invoke database


    }


    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,12})";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }//valid password


    private void openTimePickerDialog(boolean is24r) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Shutdown Time");

        timePickerDialog.show();


    }

    private void openTimePickerDialog2(boolean is24r) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener2,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Set Wakeup Time");

        timePickerDialog.show();


    }

    OnTimeSetListener onTimeSetListener
            = new OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            tvtime.setText(String.format("%2d:%02d", hourOfDay, minute));
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);

        }
    };//ontimeset

    OnTimeSetListener onTimeSetListener2
            = new OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();
            tvwtime.setText(String.format("%2d:%02d", hourOfDay, minute));
            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm2(calSet);

        }
    };//ontimeset2

    private void setAlarm(Calendar targetCal) {

       /* tvtime.setText("Shutdown is set@ " + targetCal.getTime() + "\n");

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);*/
    }//alarmshut

    private void setAlarm2(Calendar targetCal) {

        /*tvtime.setText("Wakeup is set@ " + targetCal.getTime() + "\n");

        Intent intent = new Intent(getActivity(), AlarmReceiver2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);*/
    }//alarmwake

    private void check() {
        String pa = pass.getText().toString().trim();

        if (isValidPassword(pa) && pa != null) {
            status.setText("Password Accepted");
        } else {
            status.setText("Password not Accepted must be min6,max12,0-9,a-z or A-Z");
        }

    }

    private void delete() {
        try {
            controller.delete_setting();
            tvtime.setText("");
            tvwtime.setText("");
            pass.setText("");
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

        } catch (SQLiteException E) {
            Toast.makeText(getActivity(), "ERROR DELETING", Toast.LENGTH_LONG).show();
        }
    }

    private void continues() {


        String dis = ((RadioButton) getActivity().findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString().trim();
        String pa = pass.getText().toString().trim();
        String shut = tvtime.getText().toString().trim();
        String wake = tvwtime.getText().toString().trim();
        String auto = ((RadioButton) getActivity().findViewById(radioGroup2.getCheckedRadioButtonId())).getText().toString().trim();


        if (pa.isEmpty() && dis != null && auto != null) {
            controller.insert_setting(dis, pa, shut, wake, auto);
            Toast.makeText(getActivity(), "INSERTED WITH NO PASSWORD", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(1);
        } else if (pa != null) {

            if (isValidPassword(pa)) {
                controller.insert_setting(dis, pa, shut, wake, auto);
                Toast.makeText(getActivity(), "INSERTED WITH PASSWORD", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
            }

            if (!isValidPassword(pa)) {
                Toast.makeText(getActivity(), "Password must be min6,max12,0-9,a-z or A-Z =)", Toast.LENGTH_SHORT).show();
            }


        }

    }//continue


}//clase

