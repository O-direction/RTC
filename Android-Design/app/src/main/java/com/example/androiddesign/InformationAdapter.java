package com.example.androiddesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InformationAdapter extends ArrayAdapter<Information> {
    private int resourceId;
    public InformationAdapter(Context context, int textViewResourceId, List<Information> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Information info = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,
                parent, false);
        TextView filed = (TextView) view.findViewById(R.id.info_field);
        TextView text = (TextView) view.findViewById(R.id.info_text);
        filed.setText(info.getField());
        text.setText(info.getInfo());
        return view;
    }
}
