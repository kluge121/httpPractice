package com.example.ccei.httppractice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AndroidHTTPActivity extends AppCompatActivity {

    private EditText mPatientName;
    private EditText mHospital;
    private EditText mHospitalPhone;
    private EditText mCareName;
    private EditText mCarePhone;
    private EditText mBloodvalue;

    private Spinner spBlood; // 혈액형 스피너
    private Spinner spStatus; //현재 상황 스피너
    private Spinner spDonation; // 헌혈요청방식
    private Spinner spRelation; // 환자와의 관계

    private ArrayAdapter<CharSequence> adapterBlood;
    private ArrayAdapter<CharSequence> adapterStatus;
    private ArrayAdapter<CharSequence> adapterDonation;
    private ArrayAdapter<CharSequence> adapterRelation;

    private String responseResultValue;  //응답에 대한 결과 (OK or FALL)

    private BloodEntityObject entityObject;
    private ImageView allSelectedView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_blood_insert_layout);


        //입력한 정보를 담을 엔티티 객체
        if (entityObject == null) {
            entityObject = new BloodEntityObject();
        }
        mPatientName = (EditText) findViewById(R.id.patientname);

        spBlood = (Spinner) findViewById(R.id.bloodtype);
        adapterBlood = ArrayAdapter.createFromResource(this, R.array.blood_array_item, android.R.layout.simple_spinner_item);
        adapterBlood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBlood.setAdapter(adapterBlood);


        spStatus = (Spinner) findViewById(R.id.statustype);
        adapterStatus = ArrayAdapter.createFromResource(this, R.array.statustype_array_item, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapterStatus);


        spDonation = (Spinner) findViewById(R.id.donationtype);
        adapterDonation = ArrayAdapter.createFromResource(this, R.array.donationtype_array_item, android.R.layout.simple_spinner_item);
        adapterDonation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        spDonation.setAdapter(adapterDonation);

        mBloodvalue = (EditText) findViewById(R.id.bloodvalue);
        mHospital = (EditText) findViewById(R.id.hospital);
        mHospitalPhone = (EditText) findViewById(R.id.hospitalphone);

        spRelation = (Spinner) findViewById(R.id.relation);
        adapterRelation = ArrayAdapter.createFromResource(this, R.array.relation_array_item, android.R.layout.simple_spinner_item);
        adapterRelation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ;
        spRelation.setAdapter(adapterRelation);

        mCareName = (EditText) findViewById(R.id.carename);
        mCarePhone = (EditText) findViewById(R.id.carephone);


        spBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityObject.bloodType = (adapterBlood.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
                entityObject.bloodType=(adapterBlood.getItem(0).toString());
            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityObject.statusText = (adapterStatus.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
                entityObject.statusText=(adapterStatus.getItem(0).toString());
            }
        });

        spDonation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityObject.donationType = (adapterDonation.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
                entityObject.donationType=(adapterDonation.getItem(0).toString());
            }
        });

        spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityObject.relationText = (adapterRelation.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
                entityObject.relationText=(adapterRelation.getItem(0).toString());
            }
        });

    }

    public void okInsertClick(View v) {
        //EditText 내용 입력
        entityObject.patientName = mPatientName.getText().toString();
        entityObject.hospital = mHospital.getText().toString();
        entityObject.hospitalPhone = mCarePhone.getText().toString();
        entityObject.careName = mCareName.getText().toString();
        entityObject.carePhone = mCarePhone.getText().toString();
        entityObject.bloodValue = mBloodvalue.getText().toString();

        //유효성 check

        if (entityObject.patientName == null || entityObject.patientName.length() <= 0) {
            Toast.makeText(getApplicationContext(), "환자명을 입력 해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            mPatientName.requestFocus();
            return;
        } else if (entityObject.hospital == null || entityObject.hospital.length() <= 0) {
            Toast.makeText(getApplicationContext(), "병원명을 입력 해 주시기 바랍니다", Toast.LENGTH_SHORT).show();
            mHospital.requestFocus();
            return;
        } else if (entityObject.hospitalPhone == null || entityObject.hospitalPhone.length() <= 0) {
            Toast.makeText(getApplicationContext(), "병원 전화번호를 입력 해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            mHospitalPhone.requestFocus();
        } else if (entityObject.careName == null || entityObject.careName.length() <= 0) {
            Toast.makeText(getApplicationContext(), "보호자 성명을 다시 입력 해 주시기 바랍니다.", Toast.LENGTH_SHORT);
            mCareName.requestFocus();
        } else if (entityObject.carePhone == null || entityObject.carePhone.length() <= 0) {
            Toast.makeText(getApplicationContext(), "보호자 전화번호를 다시 입력 해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            mCarePhone.requestFocus();
        } else if (entityObject.bloodValue == null || entityObject.bloodValue.length() <= 0) {
            Toast.makeText(getApplicationContext(), "혈액량을 입력 해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            mBloodvalue.requestFocus();
        } else {
            //이상이 없을 시 서버로 저장을 요구(백그라운드 동작)
            new AsyncBloodInsert().execute(entityObject);
        }
    }


    //긴급헌혈 요청 작성 취소 버튼 click
    public void cancelClick(View v) {

        mPatientName.setText("");
        mBloodvalue.setText("");
        mCarePhone.setText("");
        mHospital.setText("");
        mCareName.setText("");
        mHospitalPhone.setText("");
    }


    public class AsyncBloodInsert extends AsyncTask<BloodEntityObject, Integer, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = progressDialog.show(AndroidHTTPActivity.this, "서버입력중", "잠시만 기다려 주십시오", true);
        }

        @Override
        protected String doInBackground(BloodEntityObject... bloodInfo) {
            //java Net API를 이용한 통신

            responseResultValue = HttpAPIHelperHandler.insertBloodInfoJavaNetRequestToServer(bloodInfo[0]);

            return responseResultValue;
        }


        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if (result != null) {
                if (result.equalsIgnoreCase("OK")) {
                    showDialog(NetworkDefineConstant.BLOOD_INSERT_DIALOG_OK, null);
                } else {
                    showDialog(NetworkDefineConstant.BLOOD_INSERT_DIALOG_FAIL, null);
                }
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("message", "입력 중 문제 발생[디버깅]!");
                showDialog(NetworkDefineConstant.BLOOD_INSERT_DIALOG_FAIL, bundle);
            }
        }
    }



    @Override
    protected Dialog onCreateDialog(int id, Bundle bundle) {
        final AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("헌혈 요청 입력");
        Dialog bloodDialog = null;
        switch (id) {
            case NetworkDefineConstant.BLOOD_INSERT_DIALOG_OK: {
                alerDialog.setMessage("해당 정보를 입력하셨습니다");
                alerDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                bloodDialog = alerDialog.create();
                return bloodDialog;
            }
            case NetworkDefineConstant.BLOOD_INSERT_DIALOG_FAIL: {
                if (bundle != null) {
                    alerDialog.setMessage(bundle.getString("message"));
                } else {
                    alerDialog.setMessage("입력실패");
                }
                alerDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                bloodDialog = alerDialog.create();
                return bloodDialog;
            }
        }
        return super.onCreateDialog(id, bundle);
    }

        protected void onPause() {
            super.onPause();
            //연결 풀을 닫는다
            //HttpConnectionManager.shutdownHttpClient();
        }



    }
























