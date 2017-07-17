package test.bwie.com.mychatprogectitem.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.DataBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */
public class FirstFragmentDaoUtils {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void insert(final List<DataBean> list){
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                IApplication.daoSession.getDataBeanDao().insertInTx(list);


            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                });
    }
}