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

public class CustomAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Rule> rules;

    @Override
    public int getCount() {
        return rules.size();
    }

    public CustomAdapter(Context context,ArrayList<Rule> rules) {
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
       View v=View.inflate(context,R.layout.singlerowlistview,null);
       TextView tv_rulename=(TextView)v.findViewById(R.id.tv_rulename);
       TextView tv_ruledesc=(TextView)v.findViewById(R.id.tv_ruledesc);
       ImageView imageview=(ImageView)v.findViewById(R.id.imageView);
       imageview.setImageResource(R.drawable.images);
       tv_rulename.setText(rules.get(position).getRulename());
       tv_ruledesc.setText(rules.get(position).getRuledesc());
       v.setTag(rules.get(position).getid());
       return v;
    }
}
