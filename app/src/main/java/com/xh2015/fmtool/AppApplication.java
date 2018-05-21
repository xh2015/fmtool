package com.xh2015.fmtool;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.fm.tool.network.utils.NetWorkUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.takephoto.GlideImageLoader;
import com.lzy.imagepicker.view.CropImageView;
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
        Utils.init(this);
        mRefWatcher = LeakCanary.install(this);
        //初始化网络请求框架
        NetWorkUtils.initOkhttp(getApplicationContext());
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(6);                          //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    public static RefWatcher getRefWatcher(Context context) {
        AppApplication application = (AppApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

}
