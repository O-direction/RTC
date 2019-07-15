package com.example.androiddesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

public class InfomationActivity extends AppCompatActivity {
    private List<Information> infoList = new ArrayList<>();
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initInfo(); //获取个人信息
        InformationAdapter adapter = new InformationAdapter(InfomationActivity.this,
                R.layout.information_personal, infoList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
    private void initInfo(){
        userInfo = JMessageClient.getMyInfo();

        Information userId = new Information("账号", userInfo.getUserName());
        infoList.add(userId);
//        Information gender = new Information("性别", userInfo.getGender());
        Information nickName = new Information("昵称",userInfo.getNickname());
        infoList.add(nickName);
        Information address = new Information("地区", userInfo.getAddress());
        Information signature = new Information("个性签名", userInfo.getSignature());

        infoList.add(address);
        infoList.add(signature);
    }
}
