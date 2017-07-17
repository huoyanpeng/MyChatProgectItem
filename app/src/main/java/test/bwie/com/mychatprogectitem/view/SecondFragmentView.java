package test.bwie.com.mychatprogectitem.view;

import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public interface SecondFragmentView {

    public void registerSuccess(UserInfoBean userInfoBean);
    public void registerFailed();

}
