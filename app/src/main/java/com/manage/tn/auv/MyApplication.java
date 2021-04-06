package com.manage.tn.auv;

import ZtlApi.ZtlManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.danmo.commonapi.AppDatabase;
import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.DataRepository;
import com.facebook.stetho.Stetho;
import com.lxj.xpopup.XPopup;
import com.manage.tn.auv.util.Config;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class MyApplication extends MultiDexApplication {
public static Context sAppContext;
    public static String strMainCellTotal;
    public static String PORT_NAME;
    public static String strSecondaryCellTotal;
    public static String getStrSecondaryCount;
    public static int cell_count;
    public  static  int statue;//0初始状态 1归还 2借  4编辑
    public  static boolean isUSB=true;
    public static  boolean isFirst=false;

    static {
        strMainCellTotal = "12";
        strSecondaryCellTotal = "12";
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        ZtlManager.GetInstance().setContext(getApplicationContext());
        ZtlManager.GetInstance().setBootPackageActivity("com.manage.tn.auv", "com.manage.tn.auv.ui.activity.MainActivity");
        sAppContext=this;
        initBugly();//bugly初始化
        Stetho.initializeWithDefaults(this);
        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));//为弹框框架设置主题
    }
    /*
     * bugly初始化
     * */

    private void initBugly(){
        Bugly.init(getApplicationContext(), Config.appID_Bugly, false);//导入Bugly包
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;//开启自动更新
        Beta.initDelay = 1 * 1000;//自动更新检测延迟时间
    }
/*
* 获取数据库工具
* */
    public AppDatabase getDatabase() {

        return AppDatabase.getInstance(this);
    }
/*
* 获取网络工具
* */
    public CommonApi getCommonApi(){

        return CommonApi.init();
    }
/*
* 数据仓库
* */
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(),getCommonApi());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
