package test.bwie.com.mychatprogectitem;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.horizontalselectedviewlibrary.HorizontalselectedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainTwoActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.main2_login)
    Button button;
    @BindView(R.id.mian2_image)
    ImageView imageView;
    @BindView(R.id.ra_woman)
    RadioButton woman;
    @BindView(R.id.ra_man)
    RadioButton man;
    @BindView(R.id.main2_textView)
    TextView textView;
    private Unbinder bind;
    List<String> strings = new ArrayList<String>();
    private View leftImageView;
    private View rightImageView;
    private HorizontalselectedView hsMain;
//    private Button btMain;
//    private TextView tvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
        initView();
        initdata();
    }
    private void initView() {
        hsMain = (HorizontalselectedView) findViewById(R.id.hd_main);
        leftImageView = findViewById(R.id.iv_left);
        rightImageView = findViewById(R.id.iv_right);
//        btMain = ((Button) findViewById(R.id.bt_main));
//        tvMain = ((TextView) findViewById(R.id.tv_main));


        leftImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);
//        btMain.setOnClickListener(this);
    }

    @OnClick({R.id.mian2_image,R.id.main2_login,R.id.main2_textView})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main2_textView:
                break;
            case R.id.main2_login:
                startActivity(new Intent(MainTwoActivity.this,RegisterActivity.class));
                break;
            case R.id.mian2_image:
                startActivity(new Intent(MainTwoActivity.this,MainThreeActivity.class));
                break;
            case R.id.hd_main:
                initdata();
                break;
            case R.id.iv_left:
                hsMain.setAnLeftOffset();
                break;
            case R.id.iv_right:
                hsMain.setAnRightOffset();
                break;
//            case R.id.bt_main:
//                tvMain.setText("所选文本：" + hsMain.getSelectedString());
//                break;
            default:
                break;
        }
    }

    private void initdata() {

        for (int i = 20; i <=60; i++) {
            strings.add(i + "岁");
        }
        hsMain.setData(strings);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
