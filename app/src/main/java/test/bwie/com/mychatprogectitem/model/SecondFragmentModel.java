package test.bwie.com.mychatprogectitem.model;

import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public interface SecondFragmentModel {
    public void getData(SecondFragmentModel.SecondModelDataListener listener);

    public interface SecondModelDataListener {


        public void onSuccess(UserInfoBean userInfoBean);

        public void onFailed();

    }

}
