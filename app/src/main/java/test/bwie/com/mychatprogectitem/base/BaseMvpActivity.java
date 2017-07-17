package test.bwie.com.mychatprogectitem.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import test.bwie.com.mychatprogectitem.R;


public abstract   class BaseMvpActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {
    public T presenter;
    public abstract T initPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_mvp);
        presenter=initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
