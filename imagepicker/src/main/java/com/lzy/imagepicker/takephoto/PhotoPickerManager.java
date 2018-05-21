package com.lzy.imagepicker.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.FileUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.util.ResCacheUtils;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:图片选择和拍照
 * Date: 2018/4/3 下午5:32
 */
public class PhotoPickerManager {

    /**
     * 拍照
     *
     * @param activity
     * @param fragment
     * @param requestCode
     */
    public static void takePhoto(Activity activity, Fragment fragment, int requestCode) {
        takePhoto(activity, fragment, null, requestCode);
    }

    public static void takePhoto(Activity activity, Fragment fragment, String water, int requestCode) {
        Intent intent = new Intent(activity, FMImageGridActivity.class);
        intent.putExtra(FMImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        intent.putExtra(FMImageGridActivity.EXTRAS_WATER, water); // 水印
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void takePhoto(Activity activity, int requestCode) {
        takePhoto(activity, "", requestCode);
    }

    public static void takePhoto(Activity activity, String water, int requestCode) {
        Intent intent = new Intent(activity, FMImageGridActivity.class);
        intent.putExtra(FMImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        intent.putExtra(FMImageGridActivity.EXTRAS_WATER, water); // 水印
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 选择图片
     *
     * @param activity
     * @param fragment
     * @param max         最多还可以选几个
     * @param requestCode
     */
    public static void pickerPhoto(Activity activity,
                                   Fragment fragment,
                                   int max,
                                   int requestCode) {
        pickerPhoto(activity, fragment, null, max, requestCode);
    }

    public static void pickerPhoto(Activity activity,
                                   Fragment fragment,
                                   String water,
                                   int max,
                                   int requestCode) {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(max);
        Intent intent = new Intent(activity, FMImageGridActivity.class);
        intent.putExtra(FMImageGridActivity.EXTRAS_WATER, water); // 水印
        /* 如果需要进入选择的时候显示已经选中的图片，
        * 详情请查看ImagePickerActivity
        * */
        // intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void pickerPhoto(Activity activity,
                                   int max,
                                   int requestCode) {
        pickerPhoto(activity, "", max, requestCode);
    }

    public static void pickerPhoto(Activity activity,
                                   String water,
                                   int max,
                                   int requestCode) {
        //打开选择,本次允许选择的数量
        ImagePicker.getInstance().setSelectLimit(max);
        Intent intent = new Intent(activity, FMImageGridActivity.class);
        intent.putExtra(FMImageGridActivity.EXTRAS_WATER, water); // 水印
        /* 如果需要进入选择的时候显示已经选中的图片，
        * 详情请查看ImagePickerActivity
        * */
        // intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void clearImageCache() {
        FileUtils.deleteAllInDir(ResCacheUtils.getImageCacheDir());
    }


}
