package com.example.androiddesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendsMain extends AppCompatActivity {

    private TextView friends;
    private TextView talk;
    private TextView me;
    private List<Friends> data = null;
    private Context context;
    private FriendsAdapter friendsAdapter = null;
    private ListView list_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        JMessageClient.init(this, true);

        talk = (TextView)findViewById(R.id.talk);
        friends = (TextView)findViewById(R.id.friends);
        me = (TextView)findViewById(R.id.me);

        Drawable D_talk = getResources().getDrawable(R.drawable.talk);
        D_talk.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        talk.setCompoundDrawables(null, D_talk, null, null);

        Drawable D_friends = getResources().getDrawable(R.drawable.friends);
        D_friends.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        friends.setCompoundDrawables(null, D_friends, null, null);

        Drawable D_me = getResources().getDrawable(R.drawable.me);
        D_me.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        me.setCompoundDrawables(null, D_me, null, null);


        context = FriendsMain.this;
        list_friend = (ListView) findViewById(R.id.list_friend);
        data = new LinkedList<Friends>();
        data.add(new Friends(R.drawable.new_friend_portrait,"新的朋友"));

        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                if (0 == responseCode) {
                    for (UserInfo userinfo:userInfoList
                         ) {
                        data.add(new Friends(R.drawable.head_portrait,userinfo.getUserName()));
                    }
                } else {
                    Toast.makeText(getApplicationContext(),""+responseMessage,Toast.LENGTH_LONG).show();
                }
            }
        });


        friendsAdapter = new FriendsAdapter((LinkedList<Friends>) data, context);
        list_friend.setAdapter(friendsAdapter);

        list_friend.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(FriendsMain.this, ContactNotifyEventReceiver.class);
                    startActivity(i);
                }else{
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }
        });

    }
}
