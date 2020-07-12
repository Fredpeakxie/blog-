package com.example.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.MyApplication;
import com.example.blog.R;
import com.example.fragment.MarkFragment;
import com.example.fragment.MsgFragment;
import com.example.fragment.SetFragment;
import com.example.richeditotandroid.ui.RichTextEditActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MsgFragment msgFragment;
    private MarkFragment markFragment;
    private SetFragment setFragment;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginStateCheck();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//去掉actionbar
        setContentView(R.layout.activity_main);
        setView();
        initFragment();
    }

    /**
     * 界面切换跳转回来时调用
     */
    @Override
    protected void onResume() {
        super.onResume();
        int id = getIntent().getIntExtra("id", 0);
        if(id==2){
            bottomNavigationView.setSelectedItemId(R.id.set);
        }
    }



    /**
     * 用户登录状态检测
     */
    private void loginStateCheck(){
        MyApplication application = (MyApplication) this.getApplication();
        if(application.isLogin() == false){
            Intent it = new Intent(this,LoginActivity.class);
            startActivity(it);
        }
    }

    //初始化fragment和fragment数组
    private void initFragment() {
        msgFragment = new MsgFragment();
        markFragment = new MarkFragment();
        setFragment = new SetFragment();
        fragments = new Fragment[]{msgFragment, markFragment, setFragment};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, msgFragment).show(msgFragment).commit();
        bottomNavigationView = findViewById(R.id.bv_bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.msg: {
                    //这部分的if else 并没有看懂什么原因
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                }
                case R.id.mark: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return true;
                }
                case R.id.set: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return true;
                }
            }
            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        //隐藏上个Fragment 如果添加过了
        if(fragments[index].isAdded()==false){
            transaction.add(R.id.fragment,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    public void setView(){
        Log.d(TAG, "setView: "+Thread.currentThread().getName());
        btnAdd = findViewById(R.id.tool_bar_add);
        btnAdd.setOnClickListener(v->{
            Intent it = new Intent(this, RichTextEditActivity.class);
            startActivity(it);
        });
    }

}
