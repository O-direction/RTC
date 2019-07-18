package com.example.kchat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kchat.collector.ActivityCollector;
import com.example.kchat.util.BaseActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class RegisterActivity extends BaseActivity {

    private Button btn_register;
    private EditText edit_userName;
    private EditText edit_password;
    private EditText edit_password_confirm;
    private String str_userName;
    private String str_password;
    private String str_password_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageView back = findViewById(R.id.image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit_userName = findViewById(R.id.edit_userName_register);
        edit_password = findViewById(R.id.edit_password_register);
        edit_password_confirm = findViewById(R.id.edit_password_confirm);
        btn_register = findViewById(R.id.button_register_commit);
        str_userName = edit_userName.getText().toString();
        str_password = edit_password.getText().toString();
        str_password_confirm = edit_password_confirm.getText().toString();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!str_password_confirm.equals(str_password)){
                    // TODO: 2019/7/16 可改进，提前判断是否密码错误
                    Toast.makeText(RegisterActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
                }else {
                    JMessageClient.register(edit_userName.getText().toString(), edit_password.getText().toString(), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if(i==0){
                                // TODO: 2019/7/16 进一步完善信息
//                                ActivityCollector.removeActivity(RegisterActivity.this);
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(RegisterActivity.this, "注册失败："+s, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
