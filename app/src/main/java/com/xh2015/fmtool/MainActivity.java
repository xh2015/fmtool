package com.xh2015.fmtool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.tool.scan.ScanActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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
                OkGo.<String>get("http://apis.juhe.cn/lottery/types")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.e(TAG, response.body());
                                mInfo.setText("body:" + response.body()
                                        + "  /n message:" + response.message()
                                        + " /n code:" + response.code());
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
