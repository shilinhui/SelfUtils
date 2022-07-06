# SelfUtils
所需的通用工具类
PermissionUtils 使用实例
//请求权限
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
                        
 //注册结果监听          
 @Override
 public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
     super.onRequestPermissionsResult(requestCode, permissions, grantResults);

     PermissionUtils.onRequestPermissionsResult(mActivity,requestCode,permissions,grantResults);
 }
                                  
