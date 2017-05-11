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

public class CustomAdapterWeather extends BaseAdapter {


    private Context context;
    private ArrayList<WeatherCondition> array_weather;

    @Override
    public int getCount() {
        return array_weather.size();
    }

    public CustomAdapterWeather(Context context, ArrayList<WeatherCondition> weatherconditions) {
        this.array_weather = weatherconditions;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return array_weather.get(position);
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
       imageview.setImageResource(R.drawable.images);
       tv_rulename.setText(array_weather.get(position).rule.getRulename());
       tv_ruledesc.setText(array_weather.get(position).rule.getRuledesc());
       tv_condition.setText(array_weather.get(position).getMintemp()+"-"+array_weather.get(position).getMaxtemp()+" \u2103");
       v.setTag(array_weather.get(position).rule.getid());
       return v;
    }
}
