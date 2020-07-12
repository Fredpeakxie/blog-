package com.example.richeditotandroid.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MyApplication;
import com.example.activity.MainActivity;
import com.example.blog.R;
import com.example.nettools.NetThread;
import com.example.richeditotandroid.view.ColorPickerView;
import com.example.richeditotandroid.view.RichEditor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RichTextEditActivity extends Activity implements View.OnClickListener {
    private Button btnPublish;
    private EditText etTitle;
    private List<Uri>  uriList;
    private static class MyHandler extends Handler {
        private WeakReference<RichTextEditActivity> rteActivity;
        MyHandler(WeakReference<RichTextEditActivity> rteActivity){
            this.rteActivity = rteActivity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    Toast.makeText(rteActivity.get(),"文章发布成功",Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(rteActivity.get(), MainActivity.class);
                    rteActivity.get().startActivity(it);
                    break;
                case 0x02:
                    Toast.makeText(rteActivity.get(),"文章发布失败 请重新尝试",Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    Toast.makeText(rteActivity.get(),"网络连接异常 请恢复网络后重试",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
    private MyHandler handler = new MyHandler(new WeakReference<>(this));
    /********************View**********************/
    //文本编辑器
    private RichEditor mEditor;
    //加粗按钮
    private ImageView mBold;
    //颜色编辑器
    private TextView mTextColor;
    //显示显示View
    private LinearLayout llColorView;
    //预览按钮
    private TextView mPreView;
    //图片按钮
    private TextView mImage;
    //按序号排列（ol）
    private ImageView mListOL;
    //按序号排列（ul）
    private ImageView mListUL;
    //字体下划线
    private ImageView mLean;
    //字体倾斜
    private ImageView mItalic;
    //字体左对齐
    private ImageView mAlignLeft;
    //字体右对齐
    private ImageView mAlignRight;
    //字体居中对齐
    private ImageView mAlignCenter;
    //字体缩进
    private ImageView mIndent;
    //字体较少缩进
    private ImageView mOutdent;
    //字体索引
    private ImageView mBlockquote;
    //字体中划线
    private ImageView mStrikethrough;
    //字体上标
    private ImageView mSuperscript;
    //字体下标
    private ImageView mSubscript;
    /********************boolean开关**********************/
    //是否加粗
    boolean isClickBold = false;
    //是否正在执行动画
    boolean isAnimating = false;
    //是否按ol排序
    boolean isListOl = false;
    //是否按ul排序
    boolean isListUL = false;
    //是否下划线字体
    boolean isTextLean = false;
    //是否下倾斜字体
    boolean isItalic = false;
    //是否左对齐
    boolean isAlignLeft = false;
    //是否右对齐
    boolean isAlignRight = false;
    //是否中对齐
    boolean isAlignCenter = false;
    //是否缩进
    boolean isIndent = false;
    //是否较少缩进
    boolean isOutdent = false;
    //是否索引
    boolean isBlockquote = false;
    //字体中划线
    boolean isStrikethrough = false;
    //字体上标
    boolean isSuperscript = false;
    //字体下标
    boolean isSubscript = false;
    /********************变量**********************/
    //折叠视图的宽高
    private int mFoldedViewMeasureHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text_edit);

        initView();
        initClickListener();
        setPublish();
    }

    private void setPublish(){
        uriList = new ArrayList<>();
        //
        etTitle = findViewById(R.id.rte_et_title);
        btnPublish = findViewById(R.id.tool_bar_publish);
        btnPublish.setOnClickListener(view ->{
            String html = mEditor.getHtml();
            String title = etTitle.getText().toString();
            int userId = ((MyApplication) getApplication()).getUser().getUserId();
            NetThread.PublishArticle(userId,title,html,uriList,this,handler);
            /*new Thread(()->{
                //重构这部分代码
                //1 从这边 发送 userID 和title 申请数据库获得
                //选择 去除所有的html先
                int articleId = HttpCommunication.ApplyArticleId(userId, title);
                String articleIdS = String.format("%05d", articleId);
                if(articleId != -1){
                    //进行src设置操作 src设置为blogpic/000ArticleId/p001.jpg
                    Document doc = Jsoup.parse(html);
                    Elements imgs = doc.getElementsByTag("img");
                    for (int i = 0; i < imgs.size(); i++){
                        //先使用简单的 文件系统 日后使用 映射来提升查找效率
//                        ../image/JasmineFlower.gif
//                        ../blogpic/boooXXpoX.jpg
                        String picS = String.format("%02d", i+1);
                        String src = "../blogpic/b"+articleIdS+"p"+picS+".jpg";
                        imgs.get(i).attr("src",src);
                    }
                    String newHtml = doc.toString();
                    //对于每张jpg使用base64进行加密 对于每张jpg如何从 html 或程序中读取呢？
                    //1传送 html至服务器
                    boolean b = HttpCommunication.SendHtml(articleId, newHtml);
                    if(b){
                        //2传送每张图片到服务器
                        List<String> pics = new ArrayList<>();
                        for (int i = 0; i < uriList.size(); i++) {
                            Uri uri = uriList.get(i);
                            //使用 工具类 将uri转换成为字符串
                            String pic = UriTransTools.UriTransToBase64String(RichTextEditActivity.this, uri);
                            pics.add(pic);
                        }
                        boolean ok = HttpCommunication.SendArticlePics(articleId, pics);
                        if(ok){
                            handler.sendEmptyMessage(0x01);
                        }else {
                            handler.sendEmptyMessage(0x02);
                        }
                        return;
                        //之后进行 跳转至主页面
                    }else {
                        handler.sendEmptyMessage(0x02);
                        return;
                    }
                }else{
                    handler.sendEmptyMessage(0x02);
                }
            }).start();*/
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        initEditor();
        initMenu();
        initColorPicker();
    }

    /**
     * 初始化文本编辑器
     */
    private void initEditor() {
        mEditor = findViewById(R.id.re_main_editor);
        //mEditor.setEditorHeight(400);
        //输入框显示字体的大小
        mEditor.setEditorFontSize(18);
        //输入框显示字体的颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //输入框背景设置
        mEditor.setEditorBackgroundColor(Color.WHITE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //输入框文本padding
        mEditor.setPadding(10, 10, 10, 10);
        //输入提示文本
        mEditor.setPlaceholder("请输入编辑内容");
        //是否允许输入
        //mEditor.setInputEnabled(false);
        //文本输入框监听事件
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Log.d("mEditor", "html文本：" + text);
            }
        });
    }

    /**
     * 初始化颜色选择器
     */
    private void initColorPicker() {
        ColorPickerView right = findViewById(R.id.cpv_main_color);
        right.setOnColorPickerChangeListener(new ColorPickerView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerView picker, int color) {
                mTextColor.setBackgroundColor(color);
                mEditor.setTextColor(color);
            }

            @Override
            public void onStartTrackingTouch(ColorPickerView picker) {

            }

            @Override
            public void onStopTrackingTouch(ColorPickerView picker) {

            }
        });
    }

    /**
     * 初始化菜单按钮
     */
    private void initMenu() {
        mBold = findViewById(R.id.button_bold);
        mTextColor = findViewById(R.id.button_text_color);
        llColorView = findViewById(R.id.ll_main_color);
        mPreView = findViewById(R.id.tv_main_preview);
        mImage = findViewById(R.id.button_image);
        mListOL = findViewById(R.id.button_list_ol);
        mListUL = findViewById(R.id.button_list_ul);
        mLean = findViewById(R.id.button_underline);
        mItalic = findViewById(R.id.button_italic);
        mAlignLeft = findViewById(R.id.button_align_left);
        mAlignRight = findViewById(R.id.button_align_right);
        mAlignCenter = findViewById(R.id.button_align_center);
        mIndent = findViewById(R.id.button_indent);
        mOutdent = findViewById(R.id.button_outdent);
        mBlockquote = findViewById(R.id.action_blockquote);
        mStrikethrough = findViewById(R.id.action_strikethrough);
        mSuperscript = findViewById(R.id.action_superscript);
        mSubscript = findViewById(R.id.action_subscript);
        getViewMeasureHeight();
    }

    /**
     * 获取控件的高度
     */
    private void getViewMeasureHeight() {
        //获取像素密度
        float mDensity = getResources().getDisplayMetrics().density;
        //获取布局的高度
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        llColorView.measure(w, h);
        int height = llColorView.getMeasuredHeight();
        mFoldedViewMeasureHeight = (int) (mDensity * height + 0.5);
    }

    private void initClickListener() {
        mBold.setOnClickListener(this);
        mTextColor.setOnClickListener(this);
        mPreView.setOnClickListener(this);
        mImage.setOnClickListener(this);
        mListOL.setOnClickListener(this);
        mListUL.setOnClickListener(this);
        mLean.setOnClickListener(this);
        mItalic.setOnClickListener(this);
        mAlignLeft.setOnClickListener(this);
        mAlignRight.setOnClickListener(this);
        mAlignCenter.setOnClickListener(this);
        mIndent.setOnClickListener(this);
        mOutdent.setOnClickListener(this);
        mBlockquote.setOnClickListener(this);
        mStrikethrough.setOnClickListener(this);
        mSuperscript.setOnClickListener(this);
        mSubscript.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_bold:
                if (isClickBold) {
                    mBold.setImageResource(R.mipmap.bold);
                } else {  //加粗
                    mBold.setImageResource(R.mipmap.bold_);
                }
                isClickBold = !isClickBold;
                mEditor.setBold();
                break;
            case R.id.button_text_color:
                //设置字体颜色
                //如果动画正在执行,直接return,相当于点击无效了,不会出现当快速点击时,
                // 动画的执行和ImageButton的图标不一致的情况
                if (isAnimating) return;
                //如果动画没在执行,走到这一步就将isAnimating制为true , 防止这次动画还没有执行完毕的
                //情况下,又要执行一次动画,当动画执行完毕后会将isAnimating制为false,这样下次动画又能执行
                isAnimating = true;

                if (llColorView.getVisibility() == View.GONE) {
                    //打开动画
                    animateOpen(llColorView);
                } else {
                    //关闭动画
                    animateClose(llColorView);
                }
                break;
            case R.id.button_image:
                //插入图片
                //这里的功能需要根据需求实现，通过insertImage传入一个URL或者本地图片路径都可以，这里用户可以自己调用本地相
                //或者拍照获取图片，传图本地图片路径，也可以将本地图片路径上传到服务器（自己的服务器或者免费的七牛服务器），
                //返回在服务端的URL地址，将地址传如即可（我这里传了一张写死的图片URL，如果你插入的图片不现实，请检查你是否添加
                // 网络请求权限<uses-permission android:name="android.permission.INTERNET" />）
//                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
//                        "dachshund");
                toPicture();
                break;
            case R.id.button_list_ol:
                if (isListOl) {
                    mListOL.setImageResource(R.mipmap.list_ol);
                } else {
                    mListOL.setImageResource(R.mipmap.list_ol_);
                }
                isListOl = !isListOl;
                mEditor.setNumbers();
                break;
            case R.id.button_list_ul:
                if (isListUL) {
                    mListUL.setImageResource(R.mipmap.list_ul);
                } else {
                    mListUL.setImageResource(R.mipmap.list_ul_);
                }
                isListUL = !isListUL;
                mEditor.setBullets();
                break;
            case R.id.button_underline:
                if (isTextLean) {
                    mLean.setImageResource(R.mipmap.underline);
                } else {
                    mLean.setImageResource(R.mipmap.underline_);
                }
                isTextLean = !isTextLean;
                mEditor.setUnderline();
                break;
            case R.id.button_italic:
                if (isItalic) {
                    mItalic.setImageResource(R.mipmap.lean);
                } else {
                    mItalic.setImageResource(R.mipmap.lean_);
                }
                isItalic = !isItalic;
                mEditor.setItalic();
                break;
            case R.id.button_align_left:
                if (isAlignLeft) {
                    mAlignLeft.setImageResource(R.mipmap.align_left);
                } else {
                    mAlignLeft.setImageResource(R.mipmap.align_left_);
                }
                isAlignLeft = !isAlignLeft;
                mEditor.setAlignLeft();
                break;
            case R.id.button_align_right:
                if (isAlignRight) {
                    mAlignRight.setImageResource(R.mipmap.align_right);
                } else {
                    mAlignRight.setImageResource(R.mipmap.align_right_);
                }
                isAlignRight = !isAlignRight;
                mEditor.setAlignRight();
                break;
            case R.id.button_align_center:
                if (isAlignCenter) {
                    mAlignCenter.setImageResource(R.mipmap.align_center);
                } else {
                    mAlignCenter.setImageResource(R.mipmap.align_center_);
                }
                isAlignCenter = !isAlignCenter;
                mEditor.setAlignCenter();
                break;
            case R.id.button_indent:
                if (isIndent) {
                    mIndent.setImageResource(R.mipmap.indent);
                } else {
                    mIndent.setImageResource(R.mipmap.indent_);
                }
                isIndent = !isIndent;
                mEditor.setIndent();
                break;
            case R.id.button_outdent:
                if (isOutdent) {
                    mOutdent.setImageResource(R.mipmap.outdent);
                } else {
                    mOutdent.setImageResource(R.mipmap.outdent_);
                }
                isOutdent = !isOutdent;
                mEditor.setOutdent();
                break;
            case R.id.action_blockquote:
                if (isBlockquote) {
                    mBlockquote.setImageResource(R.mipmap.blockquote);
                } else {
                    mBlockquote.setImageResource(R.mipmap.blockquote_);
                }
                isBlockquote = !isBlockquote;
                mEditor.setBlockquote();
                break;
            case R.id.action_strikethrough:
                if (isStrikethrough) {
                    mStrikethrough.setImageResource(R.mipmap.strikethrough);
                } else {
                    mStrikethrough.setImageResource(R.mipmap.strikethrough_);
                }
                isStrikethrough = !isStrikethrough;
                mEditor.setStrikeThrough();
                break;
            case R.id.action_superscript:
                if (isSuperscript) {
                    mSuperscript.setImageResource(R.mipmap.superscript);
                } else {
                    mSuperscript.setImageResource(R.mipmap.superscript_);
                }
                isSuperscript = !isSuperscript;
                mEditor.setSuperscript();
                break;
            case R.id.action_subscript:
                if (isSubscript) {
                    mSubscript.setImageResource(R.mipmap.subscript);
                } else {
                    mSubscript.setImageResource(R.mipmap.subscript_);
                }
                isSubscript = !isSubscript;
                mEditor.setSubscript();
                break;
            case R.id.tv_main_preview:
                Intent intent = new Intent(RichTextEditActivity.this, WebDataActivity.class);
                intent.putExtra("diarys", mEditor.getHtml());
                startActivity(intent);
                break;
        }
    }

    /**
     * 开启动画
     *
     * @param view 开启动画的view
     */
    private void animateOpen(LinearLayout view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mFoldedViewMeasureHeight);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }
        });
        animator.start();
    }

    /**
     * 关闭动画
     *
     * @param view 关闭动画的view
     */
    private void animateClose(final LinearLayout view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                isAnimating = false;
            }
        });
        animator.start();
    }


    /**
     * 创建动画
     *
     * @param view  开启和关闭动画的view
     * @param start view的高度
     * @param end   view的高度
     * @return ValueAnimator对象
     */
    private ValueAnimator createDropAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);
        Log.d(TAG, "toPicture: "+"跳转相册成功");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != RESULT_CANCELED){
            if(data != null){
                Uri picUri = data.getData();
                mEditor.insertImage(picUri.toString(),"dachshund");
                uriList.add(picUri);
                //可以开启新线程将图片传过去先 但是如果用户不想使用这张图片 就得消去 得做触发事件
                //比较麻烦 可以先存储
            }
        }
    }
}