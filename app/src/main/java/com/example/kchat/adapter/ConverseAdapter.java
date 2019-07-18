package com.example.kchat.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.ChatActivity;
import com.example.kchat.R;
import com.example.kchat.model.Converse;

import java.util.List;

public class ConverseAdapter extends RecyclerView.Adapter<ConverseAdapter.ViewHolder> {
    private List<Converse> mConverseList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View conView;
        ImageView conHead;
        TextView conName;
        TextView conMsg;
        TextView conTime;
        public ViewHolder(View view){
            super(view);
            conView = view;
            conHead = view.findViewById(R.id.image_con_head);
            conName = view.findViewById(R.id.text_con_name);
            conMsg = view.findViewById(R.id.text_con_message);
            conTime = view.findViewById(R.id.text_con_time);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Converse converse = mConverseList.get(position);
        holder.conTime.setText(converse.getTime());
        holder.conMsg.setText(converse.getMessage());
        holder.conName.setText(converse.getName());
        holder.conHead.setImageResource(converse.getImageId());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_converse, parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.conView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Converse converse = mConverseList.get(position);
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra("Name", converse.getName());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return mConverseList.size();
    }

    public ConverseAdapter(List<Converse> converseList){
        mConverseList = converseList;
    }
}
