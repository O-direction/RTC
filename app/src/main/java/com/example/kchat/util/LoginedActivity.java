package com.example.kchat.util;

import cn.jpush.im.android.api.JMessageClient;

public class LoginedActivity extends BaseActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.logout();
    }
}
