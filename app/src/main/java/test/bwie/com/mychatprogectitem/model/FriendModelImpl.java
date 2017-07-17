package test.bwie.com.mychatprogectitem.model;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.mychatprogectitem.base.IApplication;
import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/14.
 * function:
 */
public class FriendModelImpl implements FriendModel{


    @Override
    public void getData(String userId, final FriendModel.FriendModelDataListener listener) {

        Map<String,String> map = new HashMap<String,String>();

    map.put("relationship.friendId",userId );
    String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
    map.put("user.sign",sign);
    RetrofitManager.post(Constants.ADD_FRIEND, map, new BaseObserver() {
        @Override
        public void onSuccess(String result) {
            System.out.println("result:========================="+result);
            FriendBean friendBean = new FriendBean();

            listener.onSuccess(friendBean);
        }
        @Override
        public void onFailed(int code) {
            listener.onFailed(code);
        }
    });

}

}
