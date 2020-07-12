package com.example.ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fred.activity.NetActivity;
import com.fred.activity.RequestActivity;

public class MainActivity extends Activity {
    private Button btnNet,btnRequest,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        btnNet = findViewById(R.id.btn_net);
        btnNet.setOnClickListener(view ->{
            Intent it = new Intent(MainActivity.this, NetActivity.class);
            startActivity(it);
        });
        btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(view ->{
            Intent it = new Intent(MainActivity.this, RequestActivity.class);
            startActivity(it);
        });
//        btnLogin = findViewById(R.id.btn_login);
//        btnLogin.setOnClickListener(view->{
//            Intent it = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(it);
//        });
    }

}
