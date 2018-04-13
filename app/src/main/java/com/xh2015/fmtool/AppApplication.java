package com.xh2015.fmtool;

import android.app.Application;
import android.content.Context;

import com.fm.tool.network.utils.NetWorkUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/4/12 上午11:10
 */
public class AppApplication extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
        //初始化网络请求框架
        NetWorkUtils.initOkhttp(getApplicationContext());
    }

    public static RefWatcher getRefWatcher(Context context) {
        AppApplication application = (AppApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

}
