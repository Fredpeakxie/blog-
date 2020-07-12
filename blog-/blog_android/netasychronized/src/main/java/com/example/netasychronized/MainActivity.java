package com.example.netasychronized;


import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Message msg = new Message();
        msg.obj = 1;
    }
}
