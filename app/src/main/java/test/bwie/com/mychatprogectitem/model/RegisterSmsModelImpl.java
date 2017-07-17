package test.bwie.com.mychatprogectitem.model;


import cn.smssdk.SMSSDK;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/5.
 * function:
 */
public class RegisterSmsModelImpl implements RegisterSmsModel {

    @Override
    public void getVerificationCode(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }
}
