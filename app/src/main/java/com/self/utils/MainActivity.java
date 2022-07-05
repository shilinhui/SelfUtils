package com.self.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.self.myapplication.DisplayUtils;
import com.self.myapplication.NetWorkUtils;
import com.self.myapplication.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;

        mRecyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();

    }

    private void initRecyclerView() {
        List<String> recyclerDta = new ArrayList<>();
        recyclerDta.add("跳转应用详情");
        recyclerDta.add("跳转定位设置");
        recyclerDta.add("请求相机权限");
        recyclerDta.add("获取网络状态");
        recyclerDta.add("获取窗口宽高");
        recyclerDta.add("获取手机宽高");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, recyclerDta);
        mRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                switch (position){
                    case 0:
                        PermissionUtils.startApplicationInformation(mContext);
                        break;
                    case 1:
                        PermissionUtils.startLocationSettings(mContext);
                        break;
                    case 2:
                        PermissionUtils.chekPermissions(mActivity
                                , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                        , Manifest.permission.ACCESS_FINE_LOCATION
                                        , Manifest.permission.CAMERA}
                                , new PermissionUtils.IPermissionsResult() {
                                    @Override
                                    public void onResult(boolean isPass) {
                                        Toast.makeText(getApplicationContext()," 权限申请" + (isPass ? "通过" : "失败"),Toast.LENGTH_SHORT).show();
                                    }
                        });
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"网络是否连接 = " + NetWorkUtils.isNetworkConnected(mContext)
                                + ", 是否是无线 = " + NetWorkUtils.isWifiConnected(mContext)
                                + ", 是否是热点 = " + NetWorkUtils.isNetWorkHotSpot(mContext)
                                + ", 是否是移动 = " + NetWorkUtils.isMobileConnected(mContext)
                                + ", 网络是否可用 = " + NetWorkUtils.isNetWorkAvailable(mContext),Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"当前窗口宽高:"
                                + " 宽 = " + DisplayUtils.getScreenShowWidth(mContext)
                                + " 高 = " + DisplayUtils.getScreenShowHeight(mContext),Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(),"当前手机宽高:"
                                + " 宽 = " + DisplayUtils.getScreenRealWidth(mContext)
                                + " 高 = " + DisplayUtils.getScreenReaHeight(mContext),Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionUtils.onRequestPermissionsResult(mActivity,requestCode,permissions,grantResults);
    }
}