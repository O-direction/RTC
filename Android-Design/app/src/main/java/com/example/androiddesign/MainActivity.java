package com.example.androiddesign;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.dialog.dialog.ConfirmDialog;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class MainActivity extends AppCompatActivity {

    private Button sign_in;
    private EditText username;
    private EditText password;
    private TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JMessageClient.init(this, true);

        sign_in = (Button) findViewById(R.id.sign_in);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        tv_register = (TextView)findViewById(R.id.tv_register);
        final ConfirmDialog confirmDialog = new ConfirmDialog(this);
        final MainActivity that = this ;

        Drawable D_username = getResources().getDrawable(R.drawable.username);
        D_username.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        username.setCompoundDrawables(D_username, null, null, null);//只放左边

        Drawable D_password = getResources().getDrawable(R.drawable.password);
        D_password.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        password.setCompoundDrawables(D_password, null, null, null);//只放左边

        sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                JMessageClient.login(username.getText().toString(), password.getText().toString(), new BasicCallback() {

                    @Override
                    public void gotResult(int i, String s) {
                        Log.d("Result",""+i);
                        if (i == 0) {

                            confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("登陆成功，即将跳转");
                            confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                                @Override
                                public void ok() {
                                    Intent intent = new Intent(that,HomePage.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void cancel() {
//                                    JMessageClient.logout();

                                }
                            });
                            confirmDialog.show();
                        } else {
                            confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg(""+s);
                            confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                                @Override
                                public void ok() {

                                }

                                @Override
                                public void cancel() {

                                }
                            });
                            confirmDialog.show();
                        }
                    }
                });
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, Register.class);
                startActivity(intent);
            }
        });
    }
}
