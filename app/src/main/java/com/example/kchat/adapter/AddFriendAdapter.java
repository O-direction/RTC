package com.example.kchat.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.AddFriendActivity;
import com.example.kchat.FriendInfoActivity;
import com.example.kchat.MainActivity;
import com.example.kchat.R;
import com.example.kchat.model.Friend;

import java.util.List;

import static java.security.AccessController.getContext;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {
    private List<Friend> mFriendList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View friendView;
        ImageView friendImage;
        TextView friendName;
        public ViewHolder(View view){
            super(view);
            friendView = view;
            friendImage = view.findViewById(R.id.image_friend);
            friendName = view.findViewById(R.id.text_friend_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_friend, parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.friendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Friend friend = mFriendList.get(position);
                Intent intent = new Intent(view.getContext(), FriendInfoActivity.class);
                intent.putExtra("userName", friend.getName());
                intent.putExtra("type", 0);
                view.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.friendImage.setImageResource(friend.getImageId());
        holder.friendName.setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
    public AddFriendAdapter(List<Friend> friendList){
        mFriendList = friendList;
    }
}
