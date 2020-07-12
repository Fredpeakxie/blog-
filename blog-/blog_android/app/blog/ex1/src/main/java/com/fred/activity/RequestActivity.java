package com.fred.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.ex1.R;
import com.fred.testPost.PostUtils;

public class RequestActivity extends AppCompatActivity {
    private TextView tvResp;
    private static String respMsg = "";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    tvResp.setText(respMsg);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        setView();
        new Thread(){
            @Override
            public void run() {
                 respMsg = PostUtils.LoginByPost("fred","123456");
                 handler.sendEmptyMessage(0x01);
            }
        }.start();
    }

    private void setView(){
        tvResp = findViewById(R.id.text_response);
    }
}
