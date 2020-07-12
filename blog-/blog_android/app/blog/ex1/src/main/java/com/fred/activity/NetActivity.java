package com.fred.activity;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex1.R;
import com.fred.testNet.GetData;

public class NetActivity extends Activity {
    private Button btn1,btn2,btn3;
    private TextView txtshow;
    private ImageView imgPic;
    private WebView webView;
    private ScrollView scroll;
    private Bitmap bitmap;
    private String detail = "";
    private boolean flag = false;
    private final static String PIC_URL = "https://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
    private final  static String HTML_URL = "http://www.baidu.com";

    private  Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0x001:
                    hideAllWidget();
                    imgPic.setVisibility(View.VISIBLE);
                    imgPic.setImageBitmap(bitmap);
                    Toast.makeText(NetActivity.this, "图片加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    hideAllWidget();
                    scroll.setVisibility(View.VISIBLE);
                    txtshow.setText(detail);
                    Toast.makeText(NetActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    hideAllWidget();
                    webView.setVisibility(View.VISIBLE);
                    webView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
                    Toast.makeText(NetActivity.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        setViews();
    }

    private void setViews() {
        txtshow = findViewById(R.id.txtshow);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        webView =  findViewById(R.id.webView);
        scroll = findViewById(R.id.scroll);
        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(view ->
                new Thread() {
                    public void run() {
                        try {
                            byte[] data = GetData.getImage(PIC_URL);
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        } catch (Exception e) {
                            e.printStackTrace();
                            e.getMessage();
                        }
                        handler.sendEmptyMessage(0x001);
                    }
                }.start()
        );
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(view ->
                new Thread() {
                    public void run() {
                        try {
                            detail = GetData.getHtml(HTML_URL);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x002);
                    }
                }.start()
        );
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(view -> {
            if (detail.equals("")) {
                Toast.makeText(NetActivity.this, "先请求HTML先嘛~", Toast.LENGTH_SHORT).show();
            } else {
                handler.sendEmptyMessage(0x003);
            }
        });
    }

    private void hideAllWidget() {
        imgPic.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }

}
