package com.example.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.MyApplication;
import com.example.bean.UserDetail;
import com.example.blog.R;
import com.example.nettools.HttpCommunication;
import com.example.nettools.NetThread;

import java.lang.ref.WeakReference;

public class UserDetailActivity extends Activity {
    private EditText etUserNickname,etUserIntroduction;
    private Button btnFinish;
    private UserDetail userDetail;

    private static class MyHandler extends Handler {
        private WeakReference<UserDetailActivity> userDetailActivity;
        MyHandler(WeakReference<UserDetailActivity> userDetailActivity){
            this.userDetailActivity = userDetailActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            UserDetailActivity uda = userDetailActivity.get();
            switch (msg.what){
                case 0x01:
                    //其实最好将这些锁住 另一线程访问时 禁止这些组件的更改
                    String nickname = uda.etUserNickname.getText().toString();
                    String introduction = uda.etUserIntroduction.getText().toString();
                    uda.userDetail.setNickname(nickname);
                    uda.userDetail.setNickname(introduction);
                    Intent it = new Intent(uda, MainActivity.class);
                    it.putExtra("id",2);
                    uda.startActivity(it);

                    break;
                case 0x02:
                    Toast.makeText(uda,"向服务器提交失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setView();
    }

    private void setView(){
        userDetail = ((MyApplication) getApplication()).getUserDetail();
        etUserNickname = findViewById(R.id.ud_a_et_user_nickname);
        etUserNickname.setText(userDetail.getNickname());
        etUserIntroduction = findViewById(R.id.ud_a_et_user_introduction);
        etUserIntroduction.setText(userDetail.getIntroduction());

        btnFinish = findViewById(R.id.ud_a_btn_edit);
        btnFinish.setOnClickListener((v)->{
            String nickname = etUserNickname.getText().toString();
            String introduction = etUserIntroduction.getText().toString();
            NetThread.SetUserDetail(userDetail.getUserId(),nickname,introduction,handler);
        });
    }
}


//取出来自SetFragment的数据
//        Bundle bundle = getIntent().getExtras();
//        String nickname = bundle.getString("nickname");
//        String introduction = bundle.getString("introduction");
//        etUserNickname.setText(nickname);
//        etUserIntroduction.setText(introduction);
