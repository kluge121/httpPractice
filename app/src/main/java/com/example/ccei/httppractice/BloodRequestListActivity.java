package com.example.ccei.httppractice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by coco on 2016-07-21.
 */
public class BloodRequestListActivity extends Activity {

    ImageButton bloodListReqBtn;
    ArrayList<BloodRequestListActivity> mData;
    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqlist);

        bloodListReqBtn = (ImageButton) findViewById(R.id.btnList);
        bloodListReqBtn.setBackgroundResource(R.drawable.listbtnpressed);

        //요청목록선택  Spinner
        Spinner sp = (Spinner) findViewById(R.id.listspinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.reqlist_array_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                new AsyncBloodJSONList().execute("jsonlist");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        list = (ListView) findViewById(R.id.reqlist);

        bloodListReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncBloodJSONList().execute("jsonlist");
            }
        });
    }

    public class AsyncBloodJSONList extends AsyncTask<String,Integer,ArrayList<BloodEntityObject>>{

        ProgressDialog dialog;

        @Override
        protected ArrayList<BloodEntityObject> doInBackground(String... params) {

            return HttpAPIHelperHandler.bloodJSONAllSelect();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(BloodRequestListActivity.this,"","잠시만 기다려주세요",true);
        }

        @Override
        protected void onPostExecute(ArrayList<BloodEntityObject> result) {
           dialog.dismiss();

            if(result != null && result.size()>0){
                BloodJSONReqListAdapter bloodListAdapter = new BloodJSONReqListAdapter(BloodRequestListActivity.this,result);

                bloodListAdapter.notifyDataSetChanged();
                list.setAdapter(bloodListAdapter);
            }
        }
    }
}































