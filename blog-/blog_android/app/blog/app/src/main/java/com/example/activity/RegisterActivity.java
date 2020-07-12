package com.example.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blog.R;

public class RegisterActivity extends Activity {
    private EditText regUsername,regPassword,regEmail;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();
    }

    private void setView(){
        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);
        regEmail = findViewById(R.id.reg_email);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(view ->{
            String username = regUsername.getText().toString();
            String password = regPassword.getText().toString();
            String email = regEmail.getText().toString();
            boolean userB = username.matches("^[a-zA-Z]\\w{5,17}$");
            boolean passB = password.matches("^[a-zA-Z]\\w{5,17}$");
            boolean emailB = email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
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
            //全部匹配成功 由这边发送给服务器 验证用户名是否已存在 这边接收服务器消息
            //存在 用户更换用户名
            //不存在 注册成功 跳转到登录界面
        });
    }
}
