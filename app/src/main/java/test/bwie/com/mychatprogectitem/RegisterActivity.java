package test.bwie.com.mychatprogectitem;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import test.bwie.com.mychatprogectitem.base.BaseMvpActivity;
import test.bwie.com.mychatprogectitem.presenter.RegisterSmsPresenter;
import test.bwie.com.mychatprogectitem.utils.MyToast;
import test.bwie.com.mychatprogectitem.view.RegisterSmsView;

import static test.bwie.com.mychatprogectitem.R.id.register_ed_phone;

public class RegisterActivity extends BaseMvpActivity<RegisterSmsView,RegisterSmsPresenter> implements RegisterSmsView {

    @BindView(R.id.register_code)
    EditText code;
    @BindView(R.id.register_code_button)
    Button button_code;
    @BindView(register_ed_phone)
    EditText ed_phone;
    @BindView(R.id.register_ensure_buttons)
    Button ensure_button;
    @BindView(R.id.register_image_back)
    ImageView image_back;
    private EventHandler eventHandler;
    private Unbinder bind;
    private InputMethodManager imm;

    @Override
    public RegisterSmsPresenter initPresenter() {
        return new RegisterSmsPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                }

            }
        };
                // 注册监听器
                SMSSDK.registerEventHandler(eventHandler);
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            }

            @OnClick({R.id.register_code_button, R.id.register_image_back, R.id.register_ensure_buttons, register_ed_phone, R.id.register_code})
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.register_ensure_buttons:
                        presenter.nextStep(ed_phone.getText().toString().trim(), code.getText().toString().trim());
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    case R.id.register_image_back:
                        finish();
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    case R.id.register_code_button:
                        presenter.getVerificationCode(ed_phone.getText().toString().trim());
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    case R.id.register_code:
                        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                        break;
                    case register_ed_phone:
                        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                        break;
                }
            }

            @Override
            public void phoneError(int num) {
                switch (num) {
                    case 1:
                        MyToast.makeText(this, "请输入手机号码", Toast.LENGTH_LONG);
                        break;
                    case 2:
                        MyToast.makeText(this, "手机号码输入错误", Toast.LENGTH_LONG);
                        break;
                }
            }

            @Override
            public void showTimer() {
                button_code.setClickable(false);
                /**
                 * 第一个参数是初始化延时
                 * 第二参数多少秒发送一次
                 * 第三参数时间单位
                 *take 次数
                 * map 控制符
                 */
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .take(30)
                        .map(new Function<Long, Long>() {
                            @Override
                            public Long apply(@NonNull Long aLong) throws Exception {
                                return 29 - aLong;
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull Long aLong) {
                                button_code.setText("还有" + aLong + "s");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                                button_code.setClickable(true);
                                button_code.setText(getText(R.string.verification));
                            }
                        });
            }

            @Override
            public void nextPager() {
                Intent intent = new Intent(this, FourActivity.class);
                intent.putExtra("phone", ed_phone.getText().toString().trim());
                startActivity(intent);
            }

            @Override
            public void SMSError() {
                MyToast.makeText(RegisterActivity.this, "验证码输入错误", Toast.LENGTH_LONG);
            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                SMSSDK.unregisterEventHandler(eventHandler);
                bind.unbind();

            }

}
