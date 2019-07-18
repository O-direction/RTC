package com.example.kchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.R;
import com.example.kchat.model.Information;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private List<Information> mInformationList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View infoView;
        ImageView iconImage;
        TextView fieldText;
        TextView infoText;
        public ViewHolder(View view){
            super(view);
            infoView = view;
            iconImage = view.findViewById(R.id.image_icon);
            fieldText = view.findViewById(R.id.text_field);
            infoText = view.findViewById(R.id.text_info);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_info, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.infoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019/7/17 点击事件
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Information information = mInformationList.get(position);
        holder.iconImage.setImageResource(information.getIcon());
        holder.fieldText.setText(information.getField());
        holder.infoText.setText(information.getInfo());
    }

    @Override
    public int getItemCount() {
        return mInformationList.size();
    }
    public InformationAdapter(List<Information> informationList){
        mInformationList = informationList;
    }
}
