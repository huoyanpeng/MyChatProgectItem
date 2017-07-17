package test.bwie.com.mychatprogectitem.view;

import test.bwie.com.mychatprogectitem.bean.RegisterBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public interface RegisInfoView {

    public void registerSuccess(RegisterBean registerBean);
    public void registerFailed(int code);

}
