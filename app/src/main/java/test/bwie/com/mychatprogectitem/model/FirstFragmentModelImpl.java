package test.bwie.com.mychatprogectitem.model;




import com.alibaba.fastjson.JSON;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.bwie.com.mychatprogectitem.bean.DataBean;
import test.bwie.com.mychatprogectitem.bean.IndexBean;
import test.bwie.com.mychatprogectitem.core.JNICore;
import test.bwie.com.mychatprogectitem.core.SortUtils;
import test.bwie.com.mychatprogectitem.network.BaseObserver;
import test.bwie.com.mychatprogectitem.network.RetrofitManager;
import test.bwie.com.mychatprogectitem.utils.Constants;
import test.bwie.com.mychatprogectitem.utils.FastJsonTools;
import test.bwie.com.mychatprogectitem.utils.FirstFragmentDaoUtils;


/**
 * author: 霍彦朋 (dell) .
 * date: 2017/7/11.
 * function:
 */
public class FirstFragmentModelImpl implements FirstFragmentModel {

    @Override
    public void getData(final int page, final long currttimer, final DataListener listener) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("user.currenttimer",currttimer+"");
        String sign = JNICore.getSign(SortUtils.getMapResult(map));
        map.put("user.sign", sign);
        RetrofitManager.post(Constants.ALL_USER, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    IndexBean jsonBean  =  JSON.parseObject(result, IndexBean.class);
//                    Gson gson = new Gson();
//                    IndexBean indexBean =   gson.fromJson(result, IndexBean.class);
//                    IndexBean jsonBean = FastJsonTools.createJsonBean(result, IndexBean.class);
                    listener.onSuccess(jsonBean,page,currttimer);
//                    List<DataBean> data = jsonBean.getData();
//                    FirstFragmentDaoUtils.insert(data);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailed(int code) {
                listener.onFailed(code,page);
            }
        });


    }
}
