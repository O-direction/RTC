package com.example.kchat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.R;
import com.example.kchat.adapter.FriendAdapter;
import com.example.kchat.model.Friend;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendFragment extends Fragment {
    private List<Friend> friendList = new ArrayList<>();
    private FriendAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_friend);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration((new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)));
    }

    public void initList(){
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if(i==0){
                    if(list==null){
                        Toast.makeText(getContext(),"好友列表为空", Toast.LENGTH_SHORT).show();
                    }
                    for (UserInfo userInfo:list){
                        Friend friend = new Friend(userInfo.getUserName(), R.drawable.ic_launcher_background);
                        friendList.add(friend);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getContext(),"获取好友列表失败："+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
