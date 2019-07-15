package com.example.androiddesign;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.myDialog.MyAlertInputDialog;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.api.ContactManager.sendInvitationRequest;

public class HomePage extends AppCompatActivity {

    private ImageView add_friend;
    private TextView friends;
    private TextView talk;
    private TextView me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        JMessageClient.init(this, true);

        add_friend = (ImageView)findViewById(R.id.add_friend);
        talk = (TextView)findViewById(R.id.talk);
        friends = (TextView)findViewById(R.id.friends);
        me = (TextView)findViewById(R.id.me);
        final HomePage that = this;

        Drawable D_talk = getResources().getDrawable(R.drawable.talk);
        D_talk.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        talk.setCompoundDrawables(null, D_talk, null, null);

        Drawable D_friends = getResources().getDrawable(R.drawable.friends);
        D_friends.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        friends.setCompoundDrawables(null, D_friends, null, null);

        Drawable D_me = getResources().getDrawable(R.drawable.me);
        D_me.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        me.setCompoundDrawables(null, D_me, null, null);


        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyAlertInputDialog myAlertInputDialog = new MyAlertInputDialog(that).builder()
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
            }
        });
        friends.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, FriendsMain.class);
                startActivity(intent);
            }
        });

    }


}
