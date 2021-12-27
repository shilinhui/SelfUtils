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
                        PermissionUtils.chekPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, new PermissionUtils.IPermissionsResult() {
                            @Override
                            public void onResult(boolean isPass) {
                                Toast.makeText(getApplicationContext(),Manifest.permission.CAMERA + " 权限申请" + (isPass ? "通过" : "失败"),Toast.LENGTH_SHORT).show();
                            }
                        });
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