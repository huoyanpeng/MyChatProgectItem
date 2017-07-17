package test.bwie.com.mychatprogectitem.model;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.Constants;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public class SecondFragmentModelImpl implements SecondFragmentModel {

    @Override
    public void getData(final SecondFragmentModel.SecondModelDataListener listener) {
        Map<String,String> map = new HashMap<String,String>();

        map.put("user.currenttimer",System.currentTimeMillis()+"" );
        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign",sign);
        RetrofitManager.post(Constants.ADD_FRIEND_LOGIN, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result:========================="+result);
                UserInfoBean userInfoBean = JSON.parseObject(result, UserInfoBean.class);
                listener.onSuccess(userInfoBean);
            }
            @Override
            public void onFailed(int code) {
                listener.onFailed();
            }
        });


    }
}
