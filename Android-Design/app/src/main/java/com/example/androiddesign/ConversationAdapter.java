package com.example.androiddesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ConversationAdapter extends BaseAdapter {

    private LinkedList<HomeConversation> data;
    private Context context;

    public ConversationAdapter(LinkedList<HomeConversation> data, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.conversation_list, parent, false);
            holder = new ViewHolder();
            holder.con_img_icon = (ImageView) convertView.findViewById(R.id.con_img_icon);
            holder.txt_aName = (TextView) convertView.findViewById(R.id.con_friend_name);
            holder.txt_message = (TextView) convertView.findViewById(R.id.con_message);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.con_img_icon.setBackgroundResource(data.get(position).getIcon());
        holder.txt_aName.setText(data.get(position).getName());
        holder.txt_message.setText(data.get(position).getMessage());
        return convertView;
    }
    private class ViewHolder{
        ImageView con_img_icon;
        TextView txt_aName;
        TextView txt_message;
    }
}
