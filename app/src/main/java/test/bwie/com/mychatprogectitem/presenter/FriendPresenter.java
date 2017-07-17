package test.bwie.com.mychatprogectitem.presenter;

import test.bwie.com.mychatprogectitem.base.BasePresenter;
import test.bwie.com.mychatprogectitem.bean.FriendBean;
import test.bwie.com.mychatprogectitem.bean.RegisterBean;
import test.bwie.com.mychatprogectitem.model.FriendModelImpl;
import test.bwie.com.mychatprogectitem.view.FridendView;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/14.
 * function:
 */
public class FriendPresenter extends BasePresenter<FridendView> {
    private FriendModelImpl friendModel ;
    public FriendPresenter(){
        friendModel = new FriendModelImpl();
    }
    public void vaildInfor(String userId) {


        friendModel.getData(userId,new FriendModelImpl.FriendModelDataListener() {
            @Override
            public void onSuccess(FriendBean friendBean) {
                view.registerSuccess(friendBean);
            }

            @Override
            public void onFailed(int code) {

                view.registerFailed(code);
            }
        });

    }

}
