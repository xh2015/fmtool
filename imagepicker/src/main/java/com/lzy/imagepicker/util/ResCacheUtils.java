package com.lzy.imagepicker.util;

import android.os.Environment;

import com.blankj.utilcode.util.SDCardUtils;

/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:
 * Date: 2018/4/4 上午11:55
 */
public class ResCacheUtils {
    //资源默认文件夹
    private static final String DEFAULT_CACHE_DIR = "/com.fm.wireless.a/cache/";
    private static final String IMAGE_CACHE_DIR = "image";
    private static final String VIDEO_CACHE_DIR = "video";
    private static final String RADIO_CACHE_DIR = "radio";

    /**
     * 获取资源路径
     *
     * @param path
     * @return
     */
    public static String getResCacheDir(String path) {
        StringBuilder sb;
        if (SDCardUtils.isSDCardEnable()) {
            sb = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            sb = new StringBuilder(Environment.getDataDirectory().getAbsolutePath());
        }
        sb.append(DEFAULT_CACHE_DIR);
        sb.append(path);
        sb.append("/");
        return sb.toString();
    }

    /**
     * 图片缓存地址
     * @return
     */
    public static String getImageCacheDir(){
        return getResCacheDir(IMAGE_CACHE_DIR);
    }

    /**
     * 视频缓存地址
     * @return
     */
    public static String getVideoCacheDir(){
        return getResCacheDir(VIDEO_CACHE_DIR);
    }

    /**
     * 音频缓存地址
     * @return
     */
    public static String getRadioCacheDir(){
        return getResCacheDir(RADIO_CACHE_DIR);
    }
}
