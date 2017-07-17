package test.bwie.com.mychatprogectitem.presenter;

import test.bwie.com.mychatprogectitem.base.BasePresenter;
import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.UserInfoBean;
import test.bwie.com.mychatprogectitem.model.FriendModelImpl;
import test.bwie.com.mychatprogectitem.model.SecondFragmentModelImpl;
import test.bwie.com.mychatprogectitem.view.SecondFragmentView;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/17.
 * function:
 */
public class SecondFragmentPresenter extends BasePresenter<SecondFragmentView> {
    private SecondFragmentModelImpl seconddModel ;
    public SecondFragmentPresenter(){
        seconddModel = new SecondFragmentModelImpl();
    }
    public void vaildInfor() {


        seconddModel.getData(new SecondFragmentModelImpl.SecondModelDataListener() {
            @Override
            public void onSuccess(UserInfoBean userInfoBean) {
                view.registerSuccess(userInfoBean);
            }

            @Override
            public void onFailed() {

                view.registerFailed();
            }
        });

    }

}
