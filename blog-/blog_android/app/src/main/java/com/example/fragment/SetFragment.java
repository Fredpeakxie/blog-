package com.example.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.MyApplication;
import com.example.activity.LoginActivity;
import com.example.activity.MyArticleActivity;
import com.example.activity.UserDetailActivity;
import com.example.bean.UserDetail;
import com.example.blog.R;
import com.example.nettools.HttpCommunication;
import com.example.nettools.NetThread;
import com.example.utils.UriTransTools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.ContentValues.TAG;

/**
 * 尝试使用MVP架构来实现 SetFragment的实现
 * 此类中不可对userDetail 进行任何的写入操作
 */
public class SetFragment extends Fragment {

    private ImageButton userHeadPortrait;
    private TextView userIntroduction,userNickname,tvArticleNum,tvLikeNum,tvMarkNum;
    private Bitmap bitmap;
    private Button btnEdit,btnExit,btnMyArticle;
    private UserDetail userDetail;

    private static class MyHandler extends Handler {
        private WeakReference<SetFragment> setFragment;
        MyHandler(WeakReference<SetFragment> setFragment){
            this.setFragment = setFragment;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    SetFragment sf = setFragment.get();
                    sf.userDetail = ((MyApplication)sf.getActivity().getApplication()).getUserDetail();
                    sf.bitmap = (Bitmap) msg.obj;
                    UserDetail userDetail = sf.userDetail;
                    sf.userNickname.setText(userDetail.getNickname());
                    sf.userIntroduction.setText(userDetail.getIntroduction());
                    sf.tvArticleNum.setText(Integer.toString(userDetail.getBlogNum()));
                    sf.tvLikeNum.setText(Integer.toString(userDetail.getGetLikeNum()));
                    sf.tvMarkNum.setText(Integer.toString(userDetail.getGetMarkNum()));
                    if(sf.bitmap == null){
                    }else {
                        sf.userHeadPortrait.setImageBitmap(sf.bitmap);
                    }
                    break;
                case 0x02:
                    Toast.makeText(setFragment.get().getContext(),"成功上传图片至服务器",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set,null);
        setView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //此处的userDetail是Application 级别的
        if(userDetail!= null){
            userNickname.setText(userDetail.getNickname());
            userIntroduction.setText(userDetail.getIntroduction());
        }
    }

    private void setView(View view){
        Log.d(TAG, "setView: "+Thread.currentThread().getName());
        userHeadPortrait = view.findViewById(R.id.set_f_img_btn_user_head);
        userHeadPortrait.setOnClickListener(v -> {
            toPicture();
        });
        userNickname = view.findViewById(R.id.set_f_tv_user_nickname);
        userIntroduction = view.findViewById(R.id.set_f_tv_user_introduction);
        tvArticleNum = view.findViewById(R.id.set_f_article_num);
        tvLikeNum = view.findViewById(R.id.set_f_like_num);
        tvMarkNum = view.findViewById(R.id.set_f_mark_num);

        btnEdit = view.findViewById(R.id.set_f_btn_edit);
        btnEdit.setOnClickListener((v)->{
            Intent it = new Intent(getActivity(), UserDetailActivity.class);
            startActivity(it);
        });

        btnExit = view.findViewById(R.id.set_f_btn_exit);
        btnExit.setOnClickListener((v)->{
            MyApplication myApplication = (MyApplication)getActivity().getApplication();
            myApplication.exit();
            Intent it = new Intent(getActivity(), LoginActivity.class);
            startActivity(it);
        });

        btnMyArticle = view.findViewById(R.id.set_f_btn_my_article);
        btnMyArticle.setOnClickListener(v->{
            Intent it = new Intent(getActivity(), MyArticleActivity.class);
            startActivity(it);
        });

        //从网络中获取 一个 可以是一整个UserDetail对象 通过userID
        MyApplication application = (MyApplication)getActivity().getApplication();
        NetThread.GetUserDetail(application,handler);
    }

    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);
        Log.d(TAG, "toPicture: "+"跳转相册成功");
    }

    /**
     * 从相册跳转回来时调用
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断返回码不等于0
        if (requestCode != RESULT_CANCELED){    //RESULT_CANCELED = 0(也可以直接写“if (requestCode != 0 )”)
            if(data!=null){
                Uri uri01 = data.getData();
                userHeadPortrait.setImageURI(uri01);
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getContentResolver().openInputStream(uri01);
                    //Bitmap存在 OOM  Error 后期可修改代码以规避OOM Error
                    //OOM out of Memory
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    userHeadPortrait.setImageBitmap(bitmap);
                    String imgToBase64 = UriTransTools.imgToBase64(bitmap);
                    //检错机制 需要时补充
//                    if (imgToBase64!=null){
//                        HttpCommunication.SendPic(imgToBase64);
//                    } else{
//                        HttpCommunication.SendPic("no picture");
//                    }
                    //启用线程通讯
                    NetThread.setUserHead(userDetail.getUserId(),imgToBase64,handler);
                    //将位图传回 正好 使用bitmap
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    if(inputStream != null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}


//可以采用intent来传递数据 但是觉得使用Application存放高等级变量更合适
//            Bundle bundle = new Bundle();
//            bundle.putString("nickname",userNickname.toString());
//            bundle.putString("introduction",userIntroduction.toString());
//            it.putExtras(bundle);


//虽然确实不明白这些 option 而言确实不知道用处 但是 用了反而解析不出来
//                    BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
//                    onlyBoundsOptions.inJustDecodeBounds = true;
//                    onlyBoundsOptions.inDither = true;//optional
//                    onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, onlyBoundsOptions);