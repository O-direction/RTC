package com.example.androiddesign;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.LinkedList;

public class FriendsAdapter extends BaseAdapter {

    private LinkedList<Friends> data;
    private Context context;

    public FriendsAdapter(LinkedList<Friends> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
       return data.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_friend, parent, false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.txt_aName = (TextView) convertView.findViewById(R.id.friend_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_icon.setBackgroundResource(data.get(position).getIcon());
        holder.txt_aName.setText(data.get(position).getName());
        return convertView;
    }
    private class ViewHolder{
        ImageView img_icon;
        TextView txt_aName;
    }

}

