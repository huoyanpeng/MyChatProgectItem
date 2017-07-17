package test.bwie.com.mychatprogectitem.model;


import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public class ReginterInfoModelImpl implements ReginterInfoModel {



    @Override
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("user.phone",phone);
        map.put("user.nickname",nickname);
        map.put("user.password",password);
        map.put("user.gender",sex);
        map.put("user.area",area);
        map.put("user.age",age);
        map.put("user.introduce",introduce);

        System.out.println("SortUtils.getMapResult(SortUtils.sortString(map)) = " + SortUtils.getMapResult(SortUtils.sortString(map)));
        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);
        Log.e("sss",map.toString());

        RetrofitManager.post(Constants.REGIST_URL, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result:=============="+result.toString());
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);
                if(registerBean.getResult_code() == 200){
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"phone",registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"password",registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",registerBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"nickname",registerBean.getData().getNickname());
                }

                listener.onSuccess(registerBean);
            }
            @Override
            public void onFailed(int code) {
                listener.onFailed(code);
            }
        });


    }
}
