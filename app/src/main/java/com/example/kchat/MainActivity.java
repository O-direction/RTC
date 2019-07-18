package com.example.kchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kchat.fragment.ChatFragment;
import com.example.kchat.fragment.FriendFragment;
import com.example.kchat.fragment.MeFragment;
import com.example.kchat.util.BaseActivity;
import com.example.kchat.util.LoginedActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hb.dialog.dialog.LoadingDialog;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.LinkedList;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.api.BasicCallback;

public class MainActivity extends LoginedActivity {
    private BottomNavigationView bottomNavView;
    private TextView friends;
    private TextView talk;
    private TextView me;
    private ImageView add;
    private Context context;

    private ListView list_friend;
    private LoadingDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JMessageClient.registerEventReceiver(this);
        replaceFragment(new ChatFragment());
        add = findViewById(R.id.iamge_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddFriendActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        bottomNavView = findViewById(R.id.view_bottom_nav);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.item_chat:
                        replaceFragment(new ChatFragment());
                        return true;
                    case R.id.item_friend:
                        replaceFragment(new FriendFragment());
                        return true;
                    case R.id.item_me:
                        replaceFragment(new MeFragment());
                        return true;
                }
                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_layout_main, fragment);
        transaction.commit();
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
                                            replaceFragment(new FriendFragment());
                                        } else {
                                            Toast.makeText(getApplicationContext(),""+responseMessage,Toast.LENGTH_LONG).show();
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

