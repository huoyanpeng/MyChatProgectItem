package test.bwie.com.mychatprogectitem.presenter;

import android.text.TextUtils;

import test.bwie.com.mychatprogectitem.base.BasePresenter;
import test.bwie.com.mychatprogectitem.model.RegisterSmsModel;
import test.bwie.com.mychatprogectitem.model.RegisterSmsModelImpl;
import test.bwie.com.mychatprogectitem.view.RegisterSmsView;
import test.bwie.com.mychatprogectitem.widget.PhoneCheckUtils;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/5.
 * function:
 */
public class RegisterSmsPresenter extends BasePresenter<RegisterSmsView> {

    public RegisterSmsModelImpl registerSmsModel;
    public RegisterSmsPresenter(){
        registerSmsModel=new RegisterSmsModelImpl();
    }

    public void getVerificationCode(String phone){
        if (TextUtils.isEmpty(phone)){
            view.phoneError(1);
            return;
        }
        if (!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(2);
            return;
        }
        registerSmsModel.getVerificationCode(phone);
        view.showTimer();
    }
    public void nextStep(String phone,String sms){

        if(TextUtils.isEmpty(phone)){
            view.phoneError(1);
            return;
        }

        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(2);
            return;
        }

        //判断验证码是否合法  验证码是4为数字 \\d{4} sms 非空
        if (!PhoneCheckUtils.isSMS(sms)){
            view.SMSError();
            return;
        }
        view.nextPager();
    }

}
