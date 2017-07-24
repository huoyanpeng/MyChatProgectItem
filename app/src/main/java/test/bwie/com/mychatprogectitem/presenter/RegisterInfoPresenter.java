package test.bwie.com.mychatprogectitem.presenter;

import android.text.TextUtils;

import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.base.BasePresenter;
import test.bwie.com.mychatprogectitem.model.ReginterInfoModelImpl;
import test.bwie.com.mychatprogectitem.view.RegisInfoView;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public class RegisterInfoPresenter extends BasePresenter<RegisInfoView> {

    private ReginterInfoModelImpl registerInforFragmentModel ;
    public RegisterInfoPresenter(){
        registerInforFragmentModel = new ReginterInfoModelImpl();
    }


    public void vaildInfor(String phone,String nickname,String sex,String age,String area,String introduce,String password) {

        //非空判断

//        if (TextUtils.isEmpty(password)&&TextUtils.isEmpty(sex)&&TextUtils.isEmpty(age)
//                &&TextUtils.isEmpty(nickname)&&TextUtils.isEmpty(area)&&TextUtils.isEmpty(introduce)&&TextUtils.isEmpty(password)){
//
//            return;
//        }

        registerInforFragmentModel.getData(phone, nickname, sex, age, area, introduce, password, new ReginterInfoModelImpl.RegisterInforFragmentDataListener() {
            @Override
            public void onSuccess(RegisterBean registerBean) {

                view.registerSuccess(registerBean);
            }

            @Override
            public void onFailed(int code) {

                view.registerFailed(code);
            }
        });

    }
}
