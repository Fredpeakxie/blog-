package com.example.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.blog.R;
import com.example.nettools.HttpCommunication;
import com.example.nettools.NetThread;

import java.lang.ref.WeakReference;

public class RegisterActivity extends Activity {
    private EditText regUsername,regPassword,regEmail;
    private Button btnRegister;
    private String respMsg;
    private static final int LOGIN_S = 0x01;
    private static final int LOGIN_F = 0x02;

    /**
     * myHandler 执行消息传递时 使用static类 防止内存泄漏 多次创建
     * 机制 在用户执行Activity时 使用 this创建MyHandler对象
     * MyHandler 对象内  registerActivity = RegisterActivity.this（在类内部时  成员方法使用this语句 代表该类对象 ）
     *
     */
    private static class MyHandler extends Handler {
        private WeakReference<RegisterActivity> registerActivity;
        MyHandler(WeakReference<RegisterActivity> registerActivity){
            this.registerActivity = registerActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    //当这边收到msg时代表 用户接受服务端数据完成
                    String respMsg = (String)msg.obj;
                    //全部匹配成功 由这边发送给服务器 验证用户名是否已存在 这边接收服务器消息
                    if(respMsg.equals("注册成功")){
                        //不存在 注册成功 跳转到登录界面
                        Intent intent = new Intent(registerActivity.get(),LoginActivity.class);
                        registerActivity.get().startActivity(intent);
                    }
                case 0x02:
                    Toast.makeText(registerActivity.get(),"服务器无响应",Toast.LENGTH_SHORT);
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView(){
        regUsername = findViewById(R.id.reg_username_register);
        regPassword = findViewById(R.id.reg_password_register);
        regEmail = findViewById(R.id.reg_email_register);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(view ->{
            String usernameS = regUsername.getText().toString();
            String passwordS = regPassword.getText().toString();
            String emailS = regEmail.getText().toString();
            boolean userB = usernameS.matches("^[a-zA-Z]\\w{5,17}$");
            boolean passB = passwordS.matches("^[a-zA-Z]\\w{5,17}$");
            boolean emailB = emailS.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
//            正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
            //可考虑更换为dialog
            if(!userB){
                Toast.makeText(RegisterActivity.this,"用户名以字母开头由6-18位的数字或字母下划线组成",Toast.LENGTH_LONG).show();
                return;
            }
            if(!passB){
                Toast.makeText(RegisterActivity.this,"密码以字母开头由6-18位的数字或字母下划线组成",Toast.LENGTH_LONG).show();
                return;
            }
            if(!emailB){
                Toast.makeText(RegisterActivity.this,"邮箱格式不正确",Toast.LENGTH_LONG).show();
                return;
            }
            NetThread.Register(usernameS,passwordS,emailS,handler);
            //全部匹配成功 由这边发送给服务器 验证用户名是否已存在 这边接收服务器消息
            //存在 用户更换用户名
            //不存在 注册成功 跳转到登录界面
        });
    }
}
