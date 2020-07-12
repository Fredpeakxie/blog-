package com.example.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MyApplication;
import com.example.blog.R;
import com.example.nettools.NetThread;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 此类最好改为loginActivity
 * 只需修改manifist中的 filter
 * 在进入主页（MainActivity）之前判断是否登陆过
 *      有 并且身份有效 则进入主页面
 *      else 进入login 页面
 */
public class LoginActivity extends Activity {
    private Button btnLogin ,btnJumpRegister;
    private EditText etUsername,etPassword;
    private String respMsg;
    private static final int LOGIN_S = 0x01;
    private static final int LOGIN_F = 0x02;

    private static class MyHandler extends Handler {
        private WeakReference<LoginActivity> loginActivity;
        MyHandler(WeakReference<LoginActivity> loginActivity){
            this.loginActivity = loginActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case 0x01:
                    //获取线程中取出的消息
                    String respMsg = (String) msg.obj;
                    Toast.makeText(loginActivity.get(),"登录成功",Toast.LENGTH_SHORT).show();
                    //改变application中的登录状态
                    MyApplication application = (MyApplication)loginActivity.get().getApplication();
                    application.setLogin(true);
                    loginActivity.get().userInit();
                    //服务端返回的UserId
                    int userId = Integer.parseInt(respMsg);
                    application.getUser().setUserId(userId);
                    //登录成功 跳转到 正式页面
                    Intent intent = new Intent(loginActivity.get(),MainActivity.class);
                    loginActivity.get().startActivity(intent);
                    break;
                case 0x02:
                    Toast.makeText(loginActivity.get(),"用户名或密码出错",Toast.LENGTH_LONG).show();
                    break;
                case 0x03:
                    Toast.makeText(loginActivity.get(),"服务器无响应",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
    }

    private void setView(){
        Log.d(TAG, "setView: "+Thread.currentThread().getName());
        etUsername = findViewById(R.id.edit_username_login);
        etPassword = findViewById(R.id.edit_password_login);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view ->{
            String usernameS = etUsername.getText().toString();
            String passwordS = etPassword.getText().toString();
            //用户名或密码为空 提醒重新输入
            if(usernameS==null){
                Toast.makeText(this,"输入用户名为空",Toast.LENGTH_SHORT).show();
                return;
            }
            if(usernameS==null){
                Toast.makeText(this,"输入密码为空",Toast.LENGTH_SHORT).show();
                return;
            }
            //向服务器发送登录请求
            NetThread.Login(usernameS,passwordS,handler);
            //登录成功 跳转到 正式页面
            //登录失败 弹出 dialog 用户名或密码错误
        });
        btnJumpRegister = findViewById(R.id.btnJ_register);
        btnJumpRegister.setOnClickListener(view->{
            //跳转到注册页面
            Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(it);
        });
    }

    private void userInit(){
        MyApplication application = (MyApplication)getApplication();
        boolean login = application.isLogin();
        NetThread.UserInit(application);
//        application.likeArticles.addAll();
    }
}
