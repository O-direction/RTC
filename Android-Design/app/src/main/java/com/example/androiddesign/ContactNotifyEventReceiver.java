package com.example.androiddesign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hb.dialog.myDialog.MyAlertDialog;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.api.BasicCallback;

public class ContactNotifyEventReceiver extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
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
