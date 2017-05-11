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

public class CustomAdapterLocation extends BaseAdapter {


    private Context context;
    private ArrayList<Locationcondition> location_condition;

    @Override
    public int getCount() {
        return location_condition.size();
    }

    public CustomAdapterLocation(Context context, ArrayList<Locationcondition> location_condition) {
        this.location_condition = location_condition;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return location_condition.get(position);
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
       imageview.setImageResource(R.drawable.location);
       tv_rulename.setText(location_condition.get(position).rule.getRulename());
       tv_ruledesc.setText(location_condition.get(position).rule.getRuledesc());
       tv_condition.setText(location_condition.get(position).getLocation());
       v.setTag(location_condition.get(position).rule.getid());
       return v;
    }
}
