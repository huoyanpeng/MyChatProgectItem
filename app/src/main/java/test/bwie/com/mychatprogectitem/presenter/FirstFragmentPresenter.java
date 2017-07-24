package test.bwie.com.mychatprogectitem.presenter;

import test.bwie.com.mychatprogectitem.base.BasePresenter;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.model.FirstFragmentModel;
import test.bwie.com.mychatprogectitem.model.FirstFragmentModelImpl;
import test.bwie.com.mychatprogectitem.view.FirstFragmentView;

/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */
public class FirstFragmentPresenter extends BasePresenter<FirstFragmentView> {
    private FirstFragmentModel firstFragmentModel ;

    public FirstFragmentPresenter(){
        firstFragmentModel = new FirstFragmentModelImpl();
    }

    public void getData(int page,long currttimer){

        firstFragmentModel.getData(page,currttimer, new FirstFragmentModel.DataListener() {
            @Override
            public void onSuccess(IndexBean indexBean, int page,long currttimer) {
                view.success(indexBean,page,currttimer);
            }

            @Override
            public void onFailed(int code,int page) {
                view.failed(code,page);
            }
        });

    }


}
