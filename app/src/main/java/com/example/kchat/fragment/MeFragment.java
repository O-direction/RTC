package com.example.kchat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kchat.FriendInfoActivity;
import com.example.kchat.LoginActivity;
import com.example.kchat.R;
import com.example.kchat.adapter.InformationAdapter;
import com.example.kchat.model.Information;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class MeFragment extends Fragment {
    private List<Information> infoList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JMessageClient.init(getContext());
        Button button = getActivity().findViewById(R.id.button_exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JMessageClient.logout();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getActivity().getIntent();
        final String userName = intent.getStringExtra("userName");
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i==0){
                    initInformation(userInfo);
                    RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_info_me);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    InformationAdapter adapter = new InformationAdapter(infoList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration((new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)));
                }else {
                    Toast.makeText(getContext(), "error:"+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initInformation(UserInfo userInfo){
        Information userNames = new Information("账号", userInfo.getUserName(),R.drawable.icon_qq);
        Information nickName = new Information("昵称", userInfo.getNickname(), R.drawable.icon_me);
        Information address = new Information("地区", userInfo.getAddress(), R.drawable.icon_address);
        infoList.add(userNames);
        infoList.add(nickName);
        infoList.add(address);
    }
}
