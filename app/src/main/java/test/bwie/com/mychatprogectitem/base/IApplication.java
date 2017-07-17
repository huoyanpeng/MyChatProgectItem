package test.bwie.com.mychatprogectitem.base;

import android.app.Application;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import cn.smssdk.SMSSDK;
import test.bwie.com.mychatprogectitem.db.DaoMaster;
import test.bwie.com.mychatprogectitem.db.DaoSession;


/**
 * author: 霍彦朋 (dell) .
 * date: 2017/6/19.
 * function:
 */
public  class IApplication extends Application {

    public static IApplication iApplication;
    public static DaoSession daoSession ;
    @Override
    public void onCreate() {
        super.onCreate();

        iApplication=this;
        SMSSDK.initSDK(this,"1f32f83c1f3a8","fbcc7cc99d04da842111213707a2b241");
        CrashReport.initCrashReport(getApplicationContext(), "2e91dbcf2e", true);
        System.loadLibrary("core");
        LeakCanary.install(this);
        initGreendao();
    }
    public static IApplication getApplication(){
        if(iApplication == null){
            iApplication = getApplication() ;
        }
        return iApplication;
    }
    public void initGreendao(){


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"dliao.db");
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        //   加密
//        DaoMaster master = new DaoMaster(helper.getEncryptedWritableDb("1111"));

        daoSession = master.newSession() ;

    }

}
