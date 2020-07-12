package com.example.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.blog.R;

public class MainActivity extends Activity {
    private Button btnLogin ,btnJumpRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
    }

    private void setView(){
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view ->{
            //向服务器发送登录请求
            //登录成功 跳转到 正式页面
            //登录失败 弹出 dialog 用户名或密码错误
        });
        btnJumpRegister = findViewById(R.id.btnJ_register);
        btnJumpRegister.setOnClickListener(view->{
            //跳转到注册页面
            Intent it = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(it);
        });
    }
}
