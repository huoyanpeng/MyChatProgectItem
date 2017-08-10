package test.bwie.com.mychatprogectitem.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.android.gms.auth.UserRecoverableNotifiedException;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.base.BaseMvpActivity;
import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.LoginBean;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.cipher.Md5Utils;
import test.bwie.com.mychatprogectitem.cipher.aes.JNCryptorUtils;
import test.bwie.com.mychatprogectitem.cipher.rsa.RsaUtils;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.presenter.LoginPresenter;
import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;
import test.bwie.com.mychatprogectitem.view.FridendView;
import test.bwie.com.mychatprogectitem.widget.KeyBoardHelper;

public class LoginActivity extends BaseMvpActivity<FridendView,LoginPresenter> implements KeyBoardHelper.OnKeyBoardStatusChangeListener{


    @BindView(R.id.login_button)
    Button button;
    @BindView(R.id.login_ed_pass)
    EditText login_pass;
    @BindView(R.id.login_ed_phone)
    EditText login_phone;
    public AMapLocationListener mLocationListener = null;
    private String phone;
    private String pass;
    private Unbinder bind;
    private SharedPreferences login;
    private SharedPreferences.Editor edit;

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this) ;
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);


        bind = ButterKnife.bind(this);
        login = getSharedPreferences("login", MODE_PRIVATE);
        this.edit = login.edit();
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                System.out.println("latitude:" + latitude + ",,,longitude:" + longitude);
            }
        };

    }

    @OnClick(R.id.login_button)
    public void onClick() {
        pass = login_pass.getText().toString().trim();
        phone = login_phone.getText().toString().trim();
        String randomKey = RsaUtils.getStringRandom(16);
        String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);
        String cipherPhone = JNCryptorUtils.getInstance().encryptData(phone,IApplication.getApplication(),randomKey);



        Map map = new HashMap<>();
        map.put("user.phone",cipherPhone);
        map.put("user.password", Md5Utils.getMD5(pass));
        map.put("user.secretkey",rsaRandomKey);
        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign",sign);

        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                int result_code = loginBean.getResult_code();
                if (result_code==200&&loginBean.getData()!=null){
                    MyToast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT);

                    Intent intent=new Intent(LoginActivity.this,MessageActivity.class);


                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",loginBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",loginBean.getData().getUserId());


//                    IApplication.getApplication().emLogin();
                    int userId = loginBean.getData().getUserId();
                    String yxpassword = loginBean.getData().getYxpassword();
                    String imagePath = loginBean.getData().getImagePath();

                    edit.putInt("myuserId",userId);
                    edit.putBoolean("login",true);
                    edit.putString("myImagePath",imagePath);
                    edit.commit();
                    EMClient.getInstance().login(userId+"",yxpassword,new EMCallBack() {
                        //回调
                        @Override public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Log.d("main", "登录聊天服务器成功！"); }
                        @Override public void onProgress(int progress, String status) {

                        } @Override public void onError(int code, String message) {
                            Log.d("main", "登录聊天服务器失败！");
                            Log.d("main", message.toString());
                        }
                    });


                    startActivity(intent);
                    finish();
                }
                if (result_code==303){
                    if (TextUtils.isEmpty(phone)){
                        MyToast.makeText(LoginActivity.this,"账号不能为空", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (TextUtils.isEmpty(pass)) {
                        MyToast.makeText(LoginActivity.this,"密码不能为空", Toast.LENGTH_SHORT);
                        return;
                    }
                    MyToast.makeText(LoginActivity.this,"账号或密码错误", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        finish();
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        PreferencesUtils.addConfigInfo(this,"kh",keyBoardheight);
        System.out.println("keyBoardheight OnKeyBoardPop = " + keyBoardheight);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        System.out.println("keyBoardheight OnKeyBoardClose = " + oldKeyBoardheight);
    }
}
