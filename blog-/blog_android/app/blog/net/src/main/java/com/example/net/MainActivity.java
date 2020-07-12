package com.example.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
    private ImageView show;
    private Bitmap bitmap;
    private Context mContext  = MainActivity.this;

    static class MyHandler extends Handler{
        private WeakReference<MainActivity> mainActivity;
        MyHandler(WeakReference<MainActivity> mainActivity){
            this.mainActivity = mainActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0x123){
                mainActivity.get().show.setImageBitmap(mainActivity.get().bitmap);
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = findViewById(R.id.show);
        new Thread(){
            @Override
            public void run() {
                try{
//                    URL url = new URL("http://img10.360buyimg.com/n0"
//                            + "/jfs/t15760/240/1818365159/368378/350e622b/"
//                            + "5a60cbaeN0ecb487a.jpg");
                    URL url = new URL("https://i0.hdslb.com/bfs/album/0c8c3687eaa1e13613942ee234eac96b3a1cd2c1.jpg");
//                    URL url = new URL("https://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg");
                    URLConnection urlConnection = url.openConnection();
                    InputStream is = urlConnection.getInputStream();
//                    InputStream is = url.openStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    handler.sendEmptyMessage(0x123);
                    is.close();
                    is = url.openStream();
                    OutputStream os = openFileOutput("crazyit.png", Context.MODE_PRIVATE);
                    byte[] buff = new byte[1024];
                    int hasRead = -1;
                    while((hasRead = is.read(buff))>0){
                        os.write(buff,0,hasRead);
                    }
                    is.close();
                    os.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}