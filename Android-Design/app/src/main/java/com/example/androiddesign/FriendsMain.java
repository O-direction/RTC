package com.example.androiddesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.dialog.LoadingDialog;
import com.hb.dialog.myDialog.MyAlertDialog;
import com.hb.dialog.myDialog.MyAlertInputDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class FriendsMain extends AppCompatActivity {

    private TextView friends;
    private TextView talk;
    private TextView me;
    private LinkedList<Friends> data = null;
    private Context context;
    private FriendsAdapter friendsAdapter = null;
    private ListView list_friend;
    private LoadingDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        JMessageClient.init(this, true);
        JMessageClient.registerEventReceiver(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        function();
    }


    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void function(){

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

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FriendsMain.this, HomePage.class);
                startActivity(i);
            }
        });

        context = FriendsMain.this;
        list_friend = (ListView) findViewById(R.id.list_friend);
        data = new LinkedList<Friends>();
        data.add(new Friends(R.drawable.new_friend_portrait,"新的朋友"));

        friendsAdapter = new FriendsAdapter(data, context);
        list_friend.setAdapter(friendsAdapter);
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
        friendsAdapter.notifyDataSetChanged();


        list_friend.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(position == 0){
                    final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(FriendsMain.this).builder()
                            .setTitle("请输入对方的用户名")
                            .setEditText("");
                    myAlertInputDialog.setPositiveButton("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String res = myAlertInputDialog.getResult();
                            myAlertInputDialog.dismiss();
                            ContactManager.sendInvitationRequest(res,"", "hello", new BasicCallback(){
                                @Override
                                public void gotResult(int i, String s) {
                                    if(i==0){
                                        Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myAlertInputDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"取消",Toast.LENGTH_LONG).show();
                        }
                    });
                    myAlertInputDialog.show();
                }else{
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Friends click_friend = (Friends)friendsAdapter.getItem(position);
                            Intent i = new Intent(FriendsMain.this, Communication.class);
                            i.putExtra("Name", click_friend.getName());
                            startActivity(i);
                        }
                    });
                }

            }
        });
    }

    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        final String fromUsername = event.getFromUsername();
        final String appkey = event.getfromUserAppKey();

        switch (event.getType()) {
            case invite_received://收到好友邀请
                MyAlertDialog myAlertDialog = new MyAlertDialog(this).builder()
                        .setTitle("收到来自"+fromUsername+"的好友请求")
                        .setMsg(reason)
                        .setPositiveButton("接受", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ContactManager.acceptInvitation(fromUsername, appkey, new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (0 == responseCode) {
                                            Toast.makeText(getApplicationContext(),"接受成功", Toast.LENGTH_LONG).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(),""+responseMessage,Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("拒绝", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ContactManager.declineInvitation(fromUsername, appkey, "sorry~", new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (0 == responseCode) {
                                            Toast.makeText(getApplicationContext(),"拒绝成功",Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(),""+responseMessage,Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                myAlertDialog.show();
                break;
            case invite_accepted://对方接收了你的好友邀请
                Toast.makeText(getApplicationContext(),"你们已经成为好友",Toast.LENGTH_LONG).show();
                break;
            case invite_declined://对方拒绝了你的好友邀请
                Toast.makeText(getApplicationContext(),"对方拒绝了你的好友请求",Toast.LENGTH_LONG).show();
                break;
            case contact_deleted://对方将你从好友中删除
                Toast.makeText(getApplicationContext(),"对方从联系列表中将你删除了",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
