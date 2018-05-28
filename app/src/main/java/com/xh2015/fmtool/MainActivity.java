package com.xh2015.fmtool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.fm.tool.network.callback.JsonCallback;
import com.fm.tool.network.model.BaseResponse;
import com.fm.tool.scan.ScanActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInfo = (TextView) findViewById(R.id.info_tv);

        findViewById(R.id.scan_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
            }
        });

        ScanActivity.setOnScanResultListener(new ScanActivity.OnScanResultListener() {
            @Override
            public void success(String result) {
                mInfo.setText("扫描结果:" + result);
                Toast.makeText(MainActivity.this, "扫描结果:" + result, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.network_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<BaseResponse<Logon>>get("http://www.wanandroid.com/tools/mockapi/6092/test1")
                        .execute(new JsonCallback<BaseResponse<Logon>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse<Logon>> response) {
                                LogUtils.e(response.body().data);
                            }
                        });
            }
        });

        findViewById(R.id.photo_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhotoActivity.class));
            }
        });

    }
}
