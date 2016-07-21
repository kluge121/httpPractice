package com.example.ccei.httppractice;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by coco on 2016-07-22.
 */
public class ParseDataParseHandler {

    public static ArrayList<BloodEntityObject> getJSONBloodRequestAllList(StringBuilder buf){

        ArrayList<BloodEntityObject> jsonAllList = null;
        JSONArray jsonArray = null;

        try{
            jsonAllList = new ArrayList<BloodEntityObject>();
            jsonArray = new JSONArray(buf.toString());
            int jsonObSize =jsonArray.length();
            for(int i = 0; i<jsonObSize; i++){
                BloodEntityObject entity = new BloodEntityObject();

                JSONObject jData = jsonArray.getJSONObject(i);
                entity.bloodId = jData.getInt("bloodId");
                entity.patientName = jData.getString("patientName");
                entity.bloodType = jData.getString("bloodType");
                entity.statusText = jData.getString("statusText");
                entity.donationType = jData.getString("donationType");
                entity.bloodValue = jData.getString("bloodValue");
                entity.hospital = jData.getString("hospital");
                entity.hospitalPhone = jData.getString("hospitalPhone");
                entity.relationText = jData.getString("relationText");
                entity.careName = jData.getString("careName");
                entity.carePhone = jData.getString("carePhone");
                entity.insertDate = jData.getString("insertDate");

                jsonAllList.add(entity);
            }
        }catch (JSONException je){
            Log.e("RequestAllList","JSON파싱 중 에러발생", je);
        }
        return  jsonAllList;
    }

}
