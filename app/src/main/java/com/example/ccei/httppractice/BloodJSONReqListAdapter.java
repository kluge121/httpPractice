package com.example.ccei.httppractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by coco on 2016-07-22.
 */
public class BloodJSONReqListAdapter extends ArrayAdapter<BloodEntityObject> {



    public BloodJSONReqListAdapter(Context context, List<BloodEntityObject> objects){
        super(context,0,objects);
    }



    LayoutInflater inflater = LayoutInflater.from(getContext());

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;
        ViewHolder holder;

        if(convertView == null){
            itemView = inflater.inflate(R.layout.item_reqlist,null);
            holder = new ViewHolder();
            holder.mTvContent=(TextView)itemView.findViewById(R.id.requestcontent);
            holder.mTvCurrent=(TextView)itemView.findViewById(R.id.requestcurrent);
            holder.mTvWriteTime=(TextView)itemView.findViewById(R.id.writetime);
            holder.mImgDetail=(ImageView) itemView.findViewById(R.id.imgdetail);

            itemView.setTag(holder);
        }else{
            itemView = convertView;
            holder = (ViewHolder) itemView.getTag();
        }
        BloodEntityObject bloodData =getItem(position);

        holder.mImgDetail.setBackgroundResource(R.drawable.listbtnred);
        holder.mImgDetail.setImageResource(R.drawable.listbtnred);

        holder.mTvCurrent.setText("현재 상황 :" + bloodData.bloodType + "," + bloodData.bloodValue + " 개 필요");
        holder.mTvWriteTime.setText(bloodData.insertDate);
        String sTime = bloodData.insertDate.substring(0,16);
        holder.mTvWriteTime.setText(sTime);

        return  itemView;
    }




    public class ViewHolder{
        TextView mTvContent;
        TextView mTvCurrent;
        TextView mTvWriteTime;
        ImageView mImgDetail;
    }
}
