package com.example.kchat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kchat.util.BaseActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginActivity extends BaseActivity {
    
    private Button btn_login;
    private Button btn_register;
    private EditText edit_userName;
    private EditText edit_password;
    private String str_userName;
    private String str_password;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        JMessageClient.init(LoginActivity.this, true);
        edit_userName = findViewById(R.id.text_userName);
        edit_password = findViewById(R.id.text_password);
        btn_login = findViewById(R.id.button_login);
        btn_register = findViewById(R.id.button_register);
        str_userName = edit_userName.getText().toString();
        str_password = edit_password.getText().toString();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                // TODO: 2019/7/16 改进：将注册信息传给登录框
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JMessageClient.login(edit_userName.getText().toString(), edit_password.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if(i==0){
                            Toast.makeText(LoginActivity.this, "登录成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userName", edit_userName.getText().toString());
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败："+s,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
