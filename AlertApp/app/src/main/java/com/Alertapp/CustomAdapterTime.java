package com.Alertapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kusumasri on 4/4/17.
 */

public class CustomAdapterTime extends BaseAdapter {


    private Context context;
    private ArrayList<Timecondition> array_Time;

    @Override
    public int getCount() {
        return array_Time.size();
    }

    public CustomAdapterTime(Context context, ArrayList<Timecondition> array_Time) {
        this.array_Time = array_Time;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return array_Time.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v=View.inflate(context,R.layout.singlerowlistview,null);
       TextView tv_rulename=(TextView)v.findViewById(R.id.tv_rulename);
       TextView tv_ruledesc=(TextView)v.findViewById(R.id.tv_ruledesc);
       TextView tv_condition=(TextView)v.findViewById(R.id.tv_condition);
       ImageView imageview=(ImageView)v.findViewById(R.id.imageView);
       imageview.setImageResource(R.drawable.clock);
       tv_rulename.setText(array_Time.get(position).rule.getRulename());
       tv_ruledesc.setText(array_Time.get(position).rule.getRuledesc());
       tv_condition.setText(array_Time.get(position).getDate()+"-"+array_Time.get(position).getTime());
       v.setTag(array_Time.get(position).rule.getid());
       return v;

    }
}
