package com.example.ccei.httppractice;

import android.util.Log;

import org.apache.http.params.HttpParams;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ccei on 2016-07-21.
 */
public class HttpConnectionManager {

    private  static final String DEBUG_TAG = "HttpConnectionManager";
    private  static HttpParams httpInitParams;


    public static synchronized HttpURLConnection getHttpURLConnection(String targetURL){
        HttpURLConnection httpConnection = null;

        try {
            URL url = new URL(targetURL);

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setConnectTimeout(15000);
            //httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }catch (Exception e){
            Log.e("DEBUG_TAG","getHttpURLConnection()==에러 발생==",e);

        }
        return httpConnection;
    }

}
