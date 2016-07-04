package com.example.mfusion;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;

/**
 * Created by JCYYYY on 2016/6/30.
 */
public class WeatherActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json="{\"HeWeather data service 3.0\":[{\"basic\":{\"city\":\"Singapore\",\"cnty\":\"Singapore\",\"id\":\"SG1880252\",\"lat\":\"1.28967\",\"lon\":\"103.850067\",\"update\":{\"loc\":\"2016-06-28 11:51\",\"utc\":\"2016-06-28 03:51\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"07:02\",\"ss\":\"19:14\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"305\",\"txt_d\":\"Partly Cloudy\",\"txt_n\":\"Light Rain\"},\"date\":\"2016-06-28\",\"hum\":\"63\",\"pcpn\":\"0.3\",\"pop\":\"40\",\"pres\":\"1009\",\"tmp\":{\"max\":\"34\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"181\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:02\",\"ss\":\"19:14\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"100\",\"txt_d\":\"Partly Cloudy\",\"txt_n\":\"Sunny，Clear\"},\"date\":\"2016-06-29\",\"hum\":\"60\",\"pcpn\":\"1.3\",\"pop\":\"58\",\"pres\":\"1010\",\"tmp\":{\"max\":\"36\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"185\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:02\",\"ss\":\"19:14\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"Sunny，Clear\",\"txt_n\":\"Sunny，Clear\"},\"date\":\"2016-06-30\",\"hum\":\"63\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1009\",\"tmp\":{\"max\":\"37\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"182\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:03\",\"ss\":\"19:15\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"Sunny，Clear\",\"txt_n\":\"Sunny，Clear\"},\"date\":\"2016-07-01\",\"hum\":\"62\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1009\",\"tmp\":{\"max\":\"38\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"178\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:03\",\"ss\":\"19:15\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"103\",\"txt_d\":\"Partly Cloudy\",\"txt_n\":\"Partly Cloudy\"},\"date\":\"2016-07-02\",\"hum\":\"63\",\"pcpn\":\"0.2\",\"pop\":\"38\",\"pres\":\"1009\",\"tmp\":{\"max\":\"35\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"164\",\"dir\":\"SSE\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:03\",\"ss\":\"19:15\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"100\",\"txt_d\":\"Partly Cloudy\",\"txt_n\":\"Sunny，Clear\"},\"date\":\"2016-07-03\",\"hum\":\"64\",\"pcpn\":\"1.8\",\"pop\":\"55\",\"pres\":\"1010\",\"tmp\":{\"max\":\"36\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"162\",\"dir\":\"SSE\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"sr\":\"07:03\",\"ss\":\"19:15\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"Sunny，Clear\",\"txt_n\":\"Sunny，Clear\"},\"date\":\"2016-07-04\",\"hum\":\"59\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1009\",\"tmp\":{\"max\":\"38\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"167\",\"dir\":\"SSE\",\"sc\":\"1-2\",\"spd\":\"9\"}}],\"hourly_forecast\":[{\"date\":\"2016-06-28 13:00\",\"hum\":\"65\",\"pop\":\"1\",\"pres\":\"1010\",\"tmp\":\"34\",\"wind\":{\"deg\":\"176\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"15\"}},{\"date\":\"2016-06-28 16:00\",\"hum\":\"63\",\"pop\":\"0\",\"pres\":\"1008\",\"tmp\":\"34\",\"wind\":{\"deg\":\"180\",\"dir\":\"S\",\"sc\":\"1-2\",\"spd\":\"15\"}},{\"date\":\"2016-06-28 19:00\",\"hum\":\"71\",\"pop\":\"0\",\"pres\":\"1008\",\"tmp\":\"32\",\"wind\":{\"deg\":\"155\",\"dir\":\"SSE\",\"sc\":\"1-2\",\"spd\":\"15\"}},{\"date\":\"2016-06-28 22:00\",\"hum\":\"79\",\"pop\":\"27\",\"pres\":\"1010\",\"tmp\":\"30\",\"wind\":{\"deg\":\"125\",\"dir\":\"SE\",\"sc\":\"1-2\",\"spd\":\"14\"}}],\"now\":{\"cond\":{\"code\":\"103\",\"txt\":\"Partly Cloudy\"},\"fl\":\"37\",\"hum\":\"59\",\"pcpn\":\"0.0\",\"pres\":\"1010\",\"tmp\":\"32\",\"vis\":\"10\",\"wind\":{\"deg\":\"200\",\"dir\":\"SSW\",\"sc\":\"1-2\",\"spd\":\"9\"}},\"status\":\"ok\"}]}";
        Gson gson=new Gson();

        WeatherJsonWrapper wrapper=gson.fromJson(json,WeatherJsonWrapper.class);
    }
}
