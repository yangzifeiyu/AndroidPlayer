package com.example.mfusion;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mfusion.New.Home;
import com.example.mfusion.adapter.TabsPagerAdapter;
import com.example.mfusion.Database.DBController;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private static final String TAG = "DialogActivity";
    private static final int DLG_EXAMPLE1 = 0;
    private static final int TEXT_ID = 0;
    private String m_Text = "";
    SQLiteDatabase db1;
    DBController controller;

    // Tab titles
    private String[] tabs = {"Configuration", "Template", "Schedule", "About", "Log"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new DBController(MainActivity.this, "", null, 1);

        //DbIniter db=new DbIniter();
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);

        actionBar = getActionBar();


        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);


        actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setHomeButtonEnabled(false);


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        new Handler().postDelayed(new Runnable(){
            public void run() {
                test();
            }
        }, 5*1000);



    }//oncreate

    public void test()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

// set title
        alertDialogBuilder.setTitle("MediaFusion Ultimate Lite");

// set dialog message
        alertDialogBuilder.setMessage("Download our pro now edition to try more!").setCancelable(false);

// create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

// show it

        alertDialog.show();

// After some action


        new Handler().postDelayed(new Runnable(){
            public void run() {
                alertDialog.dismiss();
            }
        }, 5*1000);

    }


    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Password");
            builder.setMessage("Enter Default or User set Password:");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String value = input.getText().toString().trim();
                    String storedPassword = controller.getSinlgeEntry();


                    if (value.equals(storedPassword)) {

                        Toast.makeText(MainActivity.this, "User Exit Successfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();

                    } else if (value != storedPassword) {
                        Toast.makeText(MainActivity.this, "Password Wrong", Toast.LENGTH_LONG).show();
                    }


                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

            return true;
        }//keycode back

        return super.onKeyDown(keyCode, event);
    }//onkeydownbooean

}//class
