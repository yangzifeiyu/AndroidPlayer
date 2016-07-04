package com.example.mfusion;

import com.google.gson.annotations.SerializedName;

public class WeatherJsonWrapper {

    @SerializedName("HeWeather data service 3.0")
    Info[] info;
    static class Info{
        Basic basic;
        static class Basic{
            String city;
            String cnty;
            String id;
        }

        String status;

        Now now;
        static class Now{
            Cond cond;
            static class Cond{
                String code;
                String txt;
            }
            String fl;
            String hum;
            String pcpn;
            String pres;
            String tmp;
            String vis;
            Wind wind;
            static class Wind{
                String deg;
                String dir;
            }
        }
    }



}
