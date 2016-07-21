package com.example.ccei.httppractice;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by ccei on 2016-07-21.
 */
public class HttpAPIHelperHandler {
    private static final String DEBUG_TAG = "HttpAPIHelperHandler";

    //Java net API를 사용 한 서버 요청 처리

    public static ArrayList<BloodEntityObject> bloodJSONAllSelect(){
        ArrayList<BloodEntityObject> bloodEntityObjects = null;
        BufferedReader jsonStreamData = null;
        HttpURLConnection connection = null;

        try{
            connection = HttpConnectionManager.getHttpURLConnection(NetworkDefineConstant.SERVER_URL_JSON_ALL_SELECT);
            int responseCode = connection.getResponseCode();

            if(HttpURLConnection.HTTP_OK==responseCode){
                jsonStreamData = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuf = new StringBuilder();
                String jsonLine="";
                while((jsonLine = jsonStreamData.readLine()) != null){
                    jsonBuf.append(jsonLine);
                }
                bloodEntityObjects = ParseDataParseHandler.getJSONBloodRequestAllList(jsonBuf);
            }

        } catch (IOException e){
            Log.e("bloodJSONAllSelect()",e.toString());
        }finally {
            if(jsonStreamData != null){
                try{
                    jsonStreamData.close();
                }catch (IOException ioe){
                }
            }
            if(connection != null)connection.disconnect();
        }
        return bloodEntityObjects;


    }






    public static String insertBloodInfoJavaNetRequestToServer(BloodEntityObject bloodInfo) {

        //파싱되어 넘어온 결과 값을 리턴
        String bloodInsertResultValue = null;
        StringBuilder queryStringParams = new StringBuilder();
        //쿼리문자열을 조합한다
        queryStringParams
                .append("patientName=" + bloodInfo.patientName)
                .append("&bloodType=" + bloodInfo.bloodType)
                .append("&statusText=" + bloodInfo.statusText)
                .append("&donationType=" + bloodInfo.donationType)
                .append("&bloodValue=" + bloodInfo.bloodValue)
                .append("&hospital=" + bloodInfo.hospital)
                .append("&hospitalPhone=" + bloodInfo.hospitalPhone)
                .append("&relationText=" + bloodInfo.relationText)
                .append("&careName=" + bloodInfo.careName)
                .append("&carePhone=" + bloodInfo.carePhone);

        HttpURLConnection httpConnection = null;
        OutputStream toServer = null;
        BufferedReader fromServer = null;

        try {
            //서버와의 커넥션 객체를 얻어온다
            httpConnection = HttpConnectionManager.getHttpURLConnection(NetworkDefineConstant.SERVER_URL_BLOOD_INSERT);

            //서버와의 출력스트림을 연결한다
            toServer = httpConnection.getOutputStream();

            //쿼리 문자열을 보낸다
            toServer.write(queryStringParams.toString().getBytes("UTF-8"));

            toServer.flush();
            toServer.close();

            int responseCode = httpConnection.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                fromServer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
                StringBuilder jsonBuf = new StringBuilder();
                String line = "";

                while ((line = fromServer.readLine()) != null) {
                    jsonBuf.append(line);
                }

                JSONObject jsonObject = new JSONObject(jsonBuf.toString());
                bloodInsertResultValue = jsonObject.getString("result");
            } else {  //응답코드 200번대 아닐시
                //
            }

        } catch (Exception e) {
            Log.e(DEBUG_TAG, "insertBloodInfoJavaNetRequestToServer()에서 발생!");
        } finally {
            if (toServer != null) {
                try {
                    toServer.close();
                } catch (IOException ioe) {
                }
            }
            if (fromServer != null) {
                try {
                    fromServer.close();
                } catch (IOException ioe) {
                }
            }
            if (httpConnection != null) {
                httpConnection.disconnect();
            }

        }
        return bloodInsertResultValue;

    }
}





