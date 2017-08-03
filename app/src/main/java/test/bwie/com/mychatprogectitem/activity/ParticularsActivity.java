package test.bwie.com.mychatprogectitem.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import test.bwie.com.mychatprogectitem.MainThreeActivity;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.base.BaseMvpActivity;
import test.bwie.com.mychatprogectitem.bean.DataBean;
import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.presenter.FriendPresenter;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.view.FridendView;

public class ParticularsActivity extends BaseMvpActivity<FridendView,FriendPresenter> implements FridendView{

    private Unbinder bind;
    @BindView(R.id.particulars_image_back)
    ImageView image_back;
    @BindView(R.id.particulars_image_me)
    ImageView image_me;
    @BindView(R.id.particulars_linear)
    LinearLayout linearLayouts;
    private DataBean dataBean;
    @BindView(R.id.particulars_text_age)
    TextView age;
    @BindView(R.id.particulars_text_name)
    TextView name;
    @BindView(R.id.particulars_text_native)
    TextView nativie;
    @BindView(R.id.particulars_text_location)
    TextView location;
    @BindView(R.id.button_add_friend)
    Button button_add_friend;
    private int userId;
    @BindView(R.id.particulars_button_greet)
    Button button_greet;
    private boolean falg=false;

    @Override
    public FriendPresenter initPresenter() {
        return new FriendPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);

        Intent intent = getIntent();
        dataBean = (DataBean) intent.getSerializableExtra("dataBean");
        Glide.with(this).load(dataBean.getImagePath()).into(image_me);
        initView();
        String phone = dataBean.getPhone();
        System.out.println("phone===="+phone);
        age.setText(dataBean.getAge());
        name.setText(dataBean.getNickname());
        nativie.setText(dataBean.getArea());
        location.setText(dataBean.getIntroduce());
        userId = dataBean.getUserId();
        RxView.clicks(button_add_friend).throttleFirst(1, TimeUnit.MICROSECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        addFriend();
                    }
                });

    }

    private void initView() {
        //获得一个容器
        LinearLayout linearLayout=new LinearLayout(this);
        //设置容器的布局
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //设置容器与控件间距
        linearLayout.setPadding(10,10,10,10);
        //获得一个图片控件
        ImageView imageView=new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        Glide.with(this).load(dataBean.getImagePath()).into(imageView);
        //设置文本控件
//        TextView textView=new TextView(this);
//        textView.setTextSize(30);
//        textView.setTextColor(Color.RED);
//        textView.setGravity(Gravity.CENTER);
        //向容器添加控件
        linearLayout.addView(imageView);
//        linearLayout.addView(textView);
        linearLayouts.addView(linearLayout);

    }
//,R.id.button_add_friend
    @OnClick({R.id.particulars_image_back,R.id.particulars_image_me,R.id.particulars_button_greet})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.particulars_image_back:
                finish();
                break;
            case R.id.particulars_image_me:
                Intent intent=new Intent(this,PhonoViewActivity.class);
                intent.putExtra("phonoView",dataBean.getImagePath());
                startActivity(intent);
                break;
            case R.id.particulars_button_greet:
                if (falg){
                    Intent intent1=new Intent(this,UserFriendChatActivity.class);
                    int userId = dataBean.getUserId();
                    String nickname = dataBean.getNickname();
                    String imagePath = dataBean.getImagePath();
                    intent1.putExtra("num",1);
                    intent1.putExtra("userId",userId);
                    intent1.putExtra("nickname",nickname);
                    intent1.putExtra("friendImagPath",imagePath);
                    startActivity(intent1);
                }else {
                   MyToast.makeText(this,"请先添加好友",Toast.LENGTH_LONG);
                    return;
                }
                break;
        }
    }

    private void addFriend() {

        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        boolean login1 = login.getBoolean("login", false);
        if (login1){
            presenter.vaildInfor(userId+"");
        }else {
            startActivity(new Intent(this, MainThreeActivity.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void registerSuccess(FriendBean registerBean) {
        MyToast.makeText(this,"添加成功", Toast.LENGTH_LONG);
        falg=true;

    }

    @Override
    public void registerFailed(int code) {
        MyToast.makeText(this,"添加失败", Toast.LENGTH_LONG);
    }
}
