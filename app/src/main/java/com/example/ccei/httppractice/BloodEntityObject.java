package com.example.ccei.httppractice;

/**
 * Created by ccei on 2016-07-21.
 */
public class BloodEntityObject {

    public String patientName;
    public String bloodType; // 혈액형
    public String statusText;
    public String donationType; //타입
    public String bloodValue;  //혈액수량
    public String hospital;  // 병원
    public String hospitalPhone; // 병원전화번호
    public String relationText;  // 환자와의 관계
    public String careName; // 환자보호자
    public String carePhone; // 환자보호자 전화번호
    public int bloodId; // DB PK값
    public String insertDate;


    public BloodEntityObject() {}

    public BloodEntityObject(String patientName, String bloodType, String statusText, String donationType, String bloodValue,
                             String hospital, String hospitalPhone, String relationText, String careName, String carePhone,
                             int bloodId, String insertDate) {
        super();
        this.patientName = patientName;
        this.bloodType = bloodType;
        this.statusText = statusText;
        this.donationType = donationType;
        this.bloodValue = bloodValue;
        this.hospital = hospital;
        this.hospitalPhone = hospitalPhone;
        this.relationText = relationText;
        this.careName = careName;
        this.carePhone = carePhone;
        this.bloodId = bloodId;
        this.insertDate = insertDate;
    }
}
