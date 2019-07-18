package com.example.kchat;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kchat.adapter.InformationAdapter;
import com.example.kchat.model.Information;
import com.example.kchat.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class FriendInfoActivity extends BaseActivity {
    private List<Information> infoList = new ArrayList<>();
    private static final String TAG = "FriendInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ImageView back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        final String userName = intent.getStringExtra("userName");
        final int type = intent.getIntExtra("type", -1);
        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i==0){
                    initInformation(userInfo);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view_info);
//                    if (type==1){
//                        recyclerView = findViewById(R.id.recycler_view_info_1);
//                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(FriendInfoActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    InformationAdapter adapter = new InformationAdapter(infoList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration((new DividerItemDecoration(FriendInfoActivity.this,DividerItemDecoration.VERTICAL)));
                }else {
                    Toast.makeText(FriendInfoActivity.this, "error:"+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button = findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactManager.sendInvitationRequest(userName, "", "hello", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if(i==0){
                            Toast.makeText(FriendInfoActivity.this, "好友申请成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(FriendInfoActivity.this, "error:"+s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Button btn_send = findViewById(R.id.button_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FriendInfoActivity.this, ChatActivity.class);
                intent1.putExtra("Name", userName);
                startActivity(intent1);
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
