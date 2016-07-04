package com.example.mfusion;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 1B15182 on 30/6/2016 0030.
 */
public class WeatherReader {
    private static final String TAG = "WeatherReader";
    private static final String address="https://api.heweather.com/x3/weather";
    private String city;
    private static final String APP_KEY="093c5dd41cba4dd79e384db01a6b4dc9";
    private WeatherJsonWrapper wrapper;
    private Context context;
    private WeatherReaderListener listener;

    public WeatherReader(Context context,String city,WeatherReaderListener listener) {
        this.city = city;
        this.context=context;
        this.listener=listener;
    }

    public void call(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.add("city",city);
        params.add("key",APP_KEY);

        client.get(context, address, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonResponse=new String(responseBody);
                Gson gson=new Gson();
                wrapper=gson.fromJson(jsonResponse,WeatherJsonWrapper.class);
                Log.i(TAG, "onSuccess: "+wrapper.info[0].now.tmp);
                listener.onReady(wrapper);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailure: request fail",error );
            }
        });
    }

    public interface WeatherReaderListener{
        void onReady(WeatherJsonWrapper wrapper);
    }


}
