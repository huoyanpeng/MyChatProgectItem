package test.bwie.com.mychatprogectitem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPickerView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import test.bwie.com.mychatprogectitem.base.BaseMvpActivity;
import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.cipher.Md5Utils;
import test.bwie.com.mychatprogectitem.presenter.RegisterInfoPresenter;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.view.RegisInfoView;

import static android.R.id.edit;


public class FourActivity extends BaseMvpActivity<RegisInfoView,RegisterInfoPresenter> implements RegisInfoView  {

    @BindView(R.id.four_button_ensuer)
    Button ensuer;
    @BindView(R.id.four_ed_age)
    TextView age;
    @BindView(R.id.four_ed_gender)
    TextView gender;
    @BindView(R.id.four_ed_intro)
    EditText intro;
    @BindView(R.id.four_ed_location)
    TextView locations;
    @BindView(R.id.four_ed_nickname)
    EditText nickname;
    @BindView(R.id.four_ed_password)
    EditText password;
    private Unbinder bind;
    private String[] genderStr=new String[]{"男","女"};
    private String phone;
    private String trim;

    @Override
    public RegisterInfoPresenter initPresenter() {
        return new RegisterInfoPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        //与button 点击事件冲突
        RxView.clicks(ensuer).throttleFirst(1, TimeUnit.MICROSECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                                                    System.out.println("========");
                        introData();
                        presenter.vaildInfor(phone,nickname.getText().toString().trim(), gender.getText().toString().trim()
                                , age.getText().toString().trim(), locations.getText().toString().trim()
                                , trim, Md5Utils.getMD5(password.getText().toString().trim()));
                    }
                });

    }
//    ,R.id.four_button_ensuer
    @OnClick({R.id.four_ed_gender,R.id.four_ed_age,R.id.four_ed_location})
    public void onClick(View view){
        switch (view.getId()){
//            case R.id.four_button_ensuer:
//                introData();
//                goData();
//                break;
            case R.id.four_ed_age:
                ageShow();
                break;
            case R.id.four_ed_gender:
                genderShow();
                break;
            case R.id.four_ed_location:
                showLocal();
                break;
        }
    }

    private void goData() {
        presenter.vaildInfor(phone,nickname.getText().toString().trim(), gender.getText().toString().trim()
                , age.getText().toString().trim(), locations.getText().toString().trim()
                , trim, Md5Utils.getMD5(password.getText().toString().trim()));

    }

    private void introData() {
        trim = intro.getText().toString().trim();
    }

    private void showLocal() {
        CityPickerView cityPickerView = new CityPickerView(this);
        cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                Toast.makeText(FourActivity.this,province+"-"+city+"-"+district,Toast.LENGTH_LONG).show();
                locations.setText(province+"-"+city+"-"+district);
            }
        });
        cityPickerView.show();
    }

    private void genderShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择性别");
        builder.setItems(genderStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gender.setText(genderStr[i]);
            }
        });
        builder.show();
    }

    private void ageShow() {

        final String [] ages  =  getResources().getStringArray(R.array.age);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("请选择年龄");
        builder.setItems(ages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                age.setText(ages[which]);
            }
        });
        builder.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {
         //跳到上传形象照页面
        System.out.println("registerBean============:"+registerBean.toString());
        if(registerBean.getResult_code() == 200){
            startActivity(new Intent(FourActivity.this,HeadImageActivity.class));
        }else {
            MyToast.makeText(IApplication.getApplication(),registerBean.getResult_message(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void registerFailed(int code) {
        // 给一个用户友好的提示
        MyToast.makeText(IApplication.getApplication(),code+"", Toast.LENGTH_SHORT);
        System.out.println("code=====================:"+code);
    }
}
