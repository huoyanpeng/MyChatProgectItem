package test.bwie.com.mychatprogectitem;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.activity.LoginActivity;

public class MainThreeActivity extends AppCompatActivity {

    private Unbinder bind;

    @BindView(R.id.three_button_login)
    Button login;
    @BindView(R.id.three_button_register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_three);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        bind = ButterKnife.bind(this);
    }
    @OnClick({R.id.three_button_login,R.id.three_button_register})
    public  void OnClick(View view){
        switch (view.getId()){
            case R.id.three_button_login:
                startActivity(new Intent(MainThreeActivity.this, LoginActivity.class));
                break;
            case R.id.three_button_register:
                startActivity(new Intent(MainThreeActivity.this,RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
