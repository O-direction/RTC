package com.example.androiddesign;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hb.dialog.dialog.ConfirmDialog;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class Register extends AppCompatActivity {

    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_password_again;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        JMessageClient.init(this,true);

        reg_username = (EditText)findViewById(R.id.reg_username);
        reg_password = (EditText)findViewById(R.id.reg_password);
        reg_password_again = (EditText)findViewById(R.id.reg_password_again);
        btn_register = (Button)findViewById(R.id.btn_register);
        final ConfirmDialog confirmDialog = new ConfirmDialog(this);

        Drawable D_username = getResources().getDrawable(R.drawable.username);
        D_username.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        reg_username.setCompoundDrawables(D_username, null, null, null);//只放左边

        Drawable D_password = getResources().getDrawable(R.drawable.password);
        D_password.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        reg_password.setCompoundDrawables(D_password, null, null, null);//只放左边
        reg_password_again.setCompoundDrawables(D_password, null, null, null);//只放左边

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JMessageClient.register(reg_username.getText().toString(), reg_password.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if(i==0){
                            confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg("注册成功，即将跳转");
                            confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                                @Override
                                public void ok() {

                                }

                                @Override
                                public void cancel() {

                                }
                            });
                            confirmDialog.show();


                        }else{
                            confirmDialog.setLogoImg(R.mipmap.dialog_notice).setMsg(""+s);
                            confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
                                @Override
                                public void ok() {
                                    finish();
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
    }
}
