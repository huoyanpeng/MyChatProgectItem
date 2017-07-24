package test.bwie.com.mychatprogectitem.model;

import test.bwie.com.mychatprogectitem.bean.IndexBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */
public interface FirstFragmentModel {
    public void getData(int page,long currttimer,DataListener dataListener);

    public interface DataListener{
        public void onSuccess(IndexBean indexBean, int page,long currttimer);
        public void onFailed(int code,int page);
    }

}
