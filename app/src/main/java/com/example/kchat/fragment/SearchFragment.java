package com.example.kchat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.AddFriendActivity;
import com.example.kchat.R;
import com.example.kchat.adapter.AddFriendAdapter;
import com.example.kchat.adapter.FriendAdapter;
import com.example.kchat.model.Friend;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Friend> friendList = new ArrayList<>();
    private AddFriendAdapter adapter;
    private Button btn_search;
    private EditText text_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_view_aim);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        text_name = getActivity().findViewById(R.id.edit_aim_name);
        btn_search = getActivity().findViewById(R.id.button_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JMessageClient.getUserInfo(text_name.getText().toString(), new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if(i==0){
                            friendList.clear();
                            Friend friend = new Friend(userInfo.getUserName(), R.drawable.ic_launcher_background);
                            friendList.add(friend);
                            adapter = new AddFriendAdapter(friendList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addItemDecoration((new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)));
                        }else {
                            Toast.makeText(getContext(), "error："+s,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
