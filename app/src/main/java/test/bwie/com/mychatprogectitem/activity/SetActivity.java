package test.bwie.com.mychatprogectitem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.mychatprogectitem.R;
import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.network.cookie.CookiesManager;

public class SetActivity extends AppCompatActivity {

    private Unbinder bind;
    @BindView(R.id.set_button_back)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        bind = ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CookiesManager(IApplication.getApplication()).removeAllCookie();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
