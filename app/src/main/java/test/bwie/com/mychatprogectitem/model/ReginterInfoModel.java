package test.bwie.com.mychatprogectitem.model;

import test.bwie.com.mychatprogectitem.bean.RegisterBean;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/7.
 * function:
 */
public interface ReginterInfoModel {

    public void getData(String phone,String nickname,String sex,String age,String area,String introduce,String password,RegisterInforFragmentDataListener listener);

    public interface RegisterInforFragmentDataListener {


        public void onSuccess(RegisterBean registerBean);

        public void onFailed(int code);

    }

}
