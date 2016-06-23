package com.example.mfusion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogFragment extends Fragment {

    TextView logst;
    Button load,clear;
    private static final int MSG_LogList = 0;

    private static final String processId = Integer.toString(android.os.Process.myPid());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_log, container, false);
        load = (Button) rootView.findViewById(R.id.btnReload);
        clear = (Button) rootView.findViewById(R.id.btnClear);

               /* try {
                    StringBuffer sb = new StringBuffer();
                    String logPath = Environment.getExternalStorageDirectory().getPath() + "/Log/" + "log.txt";
                    BufferedReader br = new BufferedReader(new FileReader(new File(logPath)));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    TextView tv = (TextView)rootView.findViewById(R.id.tvLog);
                    tv.setText(sb.toString());
                } catch (Exception e) {
                    // TODO: handle exception
                }*/

        load.setOnClickListener(new View.OnClickListener() {
            EditText logcat = (EditText) rootView.findViewById(R.id.etLog);
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

               try {
                  // Process process = Runtime.getRuntime().exec(new String[]{"logcat", "-d","-s","*:v"});
                   Process process = Runtime.getRuntime().exec("logcat -d *:I" );
                    //Process process = Runtime.getRuntime().exec("logcat -d");

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    StringBuilder log=new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        log.append(line+"\n");
                    }

                 // logcat.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                   logcat.setMovementMethod(new ScrollingMovementMethod());
                   logcat.setText(log.toString());

                } catch (IOException e) {

                   logcat.setText("ERROR GETTING DATA");
                }



            }
        });

        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                try {
                    Process process = Runtime.getRuntime().exec("logcat -c");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    StringBuilder log=new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        log.append(line+"\n");
                    }
                    EditText logcat = (EditText) rootView.findViewById(R.id.etLog);
                    // tv.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    logcat.setMovementMethod(new ScrollingMovementMethod());
                    logcat.setText(log.toString());
                } catch (IOException e) {
                }

            }
        });



        return rootView;
    }


}
