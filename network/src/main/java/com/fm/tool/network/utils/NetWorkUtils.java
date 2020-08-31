package com.fm.tool.network.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.fm.tool.network.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:网络工具类
 * Date: 2018/4/13 上午9:47
 */
public class NetWorkUtils {

    /**
     * 初始化网络请求参数
     */
    public static void initOkhttp(Context context) {
        initOkhttp(context, HttpLoggingInterceptor.Level.NONE);
    }

    public static void initOkhttp(Context context, HttpLoggingInterceptor.Level logLevel) {
        initOkhttp(context, OkGo.DEFAULT_MILLISECONDS, logLevel);
    }


    /**
     * @param context             上下文
     * @param timeOutMilliseconds 超时时间 单位为毫秒
     */
    public static void initOkhttp(Context context, long timeOutMilliseconds, HttpLoggingInterceptor.Level logLevel) {
        initOkhttp(context, timeOutMilliseconds, null, logLevel);
    }

    /**
     * @param context     上下文
     * @param sslFileName https 签名文件名 在 assets 文件夹下
     */
    public static void initOkhttp(Context context, String sslFileName) {
        initOkhttp(context, OkGo.DEFAULT_MILLISECONDS, sslFileName, HttpLoggingInterceptor.Level.NONE);
    }

    /**
     * @param context             上下文
     * @param timeOutMilliseconds 超时时间 单位毫秒
     * @param sslFileName         https 签名文件名 在 assets 文件夹下
     */
    public static void initOkhttp(Context context, long timeOutMilliseconds, String sslFileName, HttpLoggingInterceptor.Level logLevel) {
        if (context == null) {
            throw new NullPointerException("context should not null");
        }
        //添加请求头部
        HttpHeaders headers = new HttpHeaders();
        headers.put("Device-Type", "android");
        headers.put("Device-Id", "1234567890");

        //添加请求url追加信息（参数）
        HttpParams params = new HttpParams();
        params.put("app_type", "android");  //param支持中文,直接传,不要自己编码
        params.put("app_version", AppAndDeviceUtils.getAppVersionName(context));

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(logLevel);    //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);

        //超时时间设置，默认60秒
        builder.readTimeout(timeOutMilliseconds, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(timeOutMilliseconds, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(timeOutMilliseconds, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //https相关设置 使用预埋证书，校验服务端证书（自签名证书
        if (!TextUtils.isEmpty(sslFileName)) {
            try {
                HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(context.getApplicationContext().getAssets().open(sslFileName));
                builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OkGo.getInstance().init((Application) context.getApplicationContext())
                .setOkHttpClient(builder.build())
                .setRetryCount(0)
                .addCommonHeaders(headers)
                .addCommonParams(params);
    }
}
