package test.bwie.com.mychatprogectitem.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;
import com.mob.MobApplication;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.smssdk.SMSSDK;
import test.bwie.com.mychatprogectitem.db.DaoMaster;
import test.bwie.com.mychatprogectitem.db.DaoSession;
import test.bwie.com.mychatprogectitem.utils.PreferencesUtils;

import static android.content.ContentValues.TAG;


/**
 * author: 霍彦朋 (dell) .
 * date: 2017/6/19.
 * function:
 */
//MultiDexApplication
public  class IApplication extends MultiDexApplication {

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

        // 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        initEM();
        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            }
        });

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
    public void initEM(){
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

//        options.setAutoLogin(false);
//初始化
        EMClient.getInstance().init(this, options);
//        EaseUI.getInstance().init(this, options);

//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    public void emLogin(){
        String username = PreferencesUtils.getValueByKey(this,"uid",0) + "" ;
        String password = PreferencesUtils.getValueByKey(this,"yxpassword","0") ;
        Log.d("emLogin:======","环信关联成功==="+username);
        Log.d("emLogin:======","环信关联成功=="+password);
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("password = onSuccess" );
                Log.d("EMClient:======","环信关联成功");

                try{
                    Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

                    for (int i=0;i<conversations.size();i++){
                        System.out.println("list = " + conversations.get(i));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int i, String s) {
                System.out.println("password onError = " + i + " s " + s);
                Log.e("EMClient:======","环信关联失败");


            }

            @Override
            public void onProgress(int i, String s) {
                System.out.println("password onProgress = " + i);

            }
        });

    }


}
