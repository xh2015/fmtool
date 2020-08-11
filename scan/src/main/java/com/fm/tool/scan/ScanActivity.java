package com.fm.tool.scan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.fm.tool.scan.utils.AppUtils;
import com.fm.tool.scan.utils.StatusBarUtils;
import com.fm.tool.scan.utils.VibrateUtils;
import com.fm.tool.scan.view.FMIconfontView;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


/**
 * Author：gary
 * Email: xuhaozv@163.com
 * description:扫描二维码工具
 * Date: 2018/4/12 上午10:40
 */
public class ScanActivity extends FragmentActivity implements QRCodeView.Delegate, View.OnClickListener {

    private static final String TAG = "ScanActivity";
    private static final int VIBRATE_DURATION = 200;
    private static final int GET_IMAGE_FROM_PHONE = 5002;

    private ZXingView mZXingView;
    private FMIconfontView mBack;
    private FMIconfontView mFlashlight;
    private boolean flashlightStatus;
    private FMIconfontView mPic;//图片选择

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setNoTitle(this);
        setContentView(R.layout.activity_scan);
        StatusBarUtils.setTransparentStatusBar(this);
        AppUtils.syncIsDebug(getApplicationContext());
        //权限初始化
        initPermission();
        //界面控件初始化
        initView();
    }

    private void initPermission() {
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        }
    }

    private void initView() {
        mZXingView = (ZXingView) findViewById(R.id.zxingview);
        mBack = (FMIconfontView) findViewById(R.id.btn_back);
        mFlashlight = (FMIconfontView) findViewById(R.id.btn_flashlight);
        mPic = (FMIconfontView) findViewById(R.id.btn_pic);
        mBack.setOnClickListener(this);
        mFlashlight.setOnClickListener(this);
        mPic.setOnClickListener(this);
        mZXingView.setDelegate(this);
        mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
        mPic.setVisibility(AppUtils.isDebug() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_back) {
            finish();
        } else if (v.getId() == R.id.btn_flashlight) {
            turnFlashlight();
        } else if (v.getId() == R.id.btn_pic) {
            openLocalImage();
        }
    }

    private void openLocalImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
    }

    private void turnFlashlight() {
        if (flashlightStatus) {
            mZXingView.closeFlashlight();
            mFlashlight.setTextColor(getResources().getColor(R.color.wx_topbar_font_color));
            mFlashlight.setText(getResources().getString(R.string.wx_topbar_flashlight_off_icon));
        } else {
            mZXingView.openFlashlight();
            mFlashlight.setTextColor(getResources().getColor(R.color.qrcv_cornerColor));
            mFlashlight.setText(getResources().getString(R.string.wx_topbar_flashlight_on_icon));
        }
        flashlightStatus = !flashlightStatus;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GET_IMAGE_FROM_PHONE) {
            // 照片的原始资源地址
            Uri originalUri = data.getData();
            String realPathFromUri = AppUtils.getRealPathFromUri(getApplicationContext(), originalUri);
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(realPathFromUri);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.v(TAG, "扫描结果:" + result);
        VibrateUtils.vibrateOnce(this, VIBRATE_DURATION);
        if (mListener != null) {
            mListener.success(result);
        }
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {}

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
        finish();
    }

    private static OnScanResultListener mListener;

    public static void setOnScanResultListener(OnScanResultListener listener) {
        mListener = listener;
    }

    public interface OnScanResultListener {
        void success(String result);
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return QRCodeDecoder.syncDecodeQRCode(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (mListener != null) {
                mListener.success(result);
            }
            ScanActivity.this.finish();
        }
    }
}
