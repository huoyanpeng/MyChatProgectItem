package test.bwie.com.mychatprogectitem.model;

import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/14.
 * function:
 */

public interface FriendModel {
    public void getData(String userId,FriendModel.FriendModelDataListener listener);

    public interface FriendModelDataListener {


        public void onSuccess(FriendBean friendBean);

        public void onFailed(int code);

    }
}
