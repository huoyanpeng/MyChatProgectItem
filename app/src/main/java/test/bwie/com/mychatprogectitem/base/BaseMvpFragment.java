package test.bwie.com.mychatprogectitem.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.bwie.com.mychatprogectitem.R;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */
public  abstract  class BaseMvpFragment<V,T extends BasePresenter<V>> extends Fragment {
    public T presenter ;

    public abstract T initPresenter();


    public BaseMvpFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_base_mvp, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

}
