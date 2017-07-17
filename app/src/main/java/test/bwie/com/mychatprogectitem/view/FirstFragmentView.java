package test.bwie.com.mychatprogectitem.view;

import test.bwie.com.mychatprogectitem.bean.IndexBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */

public interface FirstFragmentView {
    public void success(IndexBean indexBean, int page);
    public void failed(int code,int page);
}
