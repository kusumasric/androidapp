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

public class RuleAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Rule> rules;

    @Override
    public int getCount() {
        return rules.size();
    }

    public RuleAdapter(Context context, ArrayList<Rule> rules) {
        this.rules = rules;
        this.context = context;
    }

    @Override
    public Object getItem(int position) {
        return rules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v=View.inflate(context,R.layout.activity_home_listview_singlerow,null);
       TextView tv_rulename=(TextView)v.findViewById(R.id.tv_rulename);
       TextView tv_ruledesc=(TextView)v.findViewById(R.id.tv_ruledesc);
       TextView tv_condition=(TextView)v.findViewById(R.id.tv_condition);
       ImageView imageview=(ImageView)v.findViewById(R.id.imageView);

       tv_rulename.setText(rules.get(position).getRulename());
       tv_ruledesc.setText(rules.get(position).getRuledesc());
       Basecondition basecondition=rules.get(position).getBaseconditionobj();
       if(basecondition instanceof WeatherCondition ) {
           imageview.setImageResource(R.drawable.weatherimage);
           tv_condition.setText(((WeatherCondition) basecondition).getMinTemp()+" \u2109" + "-" + ((WeatherCondition) basecondition).getMaxTemp()+" \u2109");
       }
       if(basecondition instanceof LocationCondition) {
           imageview.setImageResource(R.drawable.transperantlocation);
           tv_condition.setText(((LocationCondition) basecondition).getLocation());
       }
       if(basecondition instanceof TimeCondition) {
           imageview.setImageResource(R.drawable.transperantclock);
           tv_condition.setText(((TimeCondition) basecondition).getDatetime());
       }
       v.setTag(rules.get(position).getid());
       return v;
    }
}
