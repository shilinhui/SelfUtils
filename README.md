# SelfUtils
所需的通用工具类

DisplayUtils： Navbar的设置，屏幕或窗口的宽高获取，px和dp的转换

FileUtils： 文件的处理，如：创建文件，复制文件，文件大小等

NetWorkUtils： 网络状态获取，包括移动。WiFi，热点

PackageUtils： 应用信息处理

PermissionUtils： 权限处理

PermissionUtils 使用示例

请求权限


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
                        
 注册结果监听          
 

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);

       PermissionUtils.onRequestPermissionsResult(mActivity,requestCode,permissions,grantResults);
    }
                                  
