package test.bwie.com.mychatprogectitem.view;

import test.bwie.com.mychatprogectitem.bean.FriendBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/10.
 * function:
 */

public interface FridendView {

    public void registerSuccess(FriendBean friendBean);
    public void registerFailed(int code);

}
