package com.anshi.farmproject.view.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.base.BaseApplication;
import com.anshi.farmproject.greendao.UploadLocationEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.check.SampleMultiplePermissionListener;
import com.anshi.farmproject.utils.notifylistener.INotifyListener;
import com.anshi.farmproject.utils.notifylistener.NotifyListenerMangager;
import com.anshi.farmproject.view.location.LocationActivity;
import com.anshi.farmproject.view.login.LoginActivity;
import com.anshi.farmproject.view.map.MapActivity;
import com.anshi.farmproject.view.notupload.NotUploadActivity;
import com.anshi.farmproject.view.query.QueryActivity;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.NetworkUtil;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;

public class MainCateActivity extends BaseActivity implements View.OnClickListener,INotifyListener, MKOfflineMapListener {
    private TextView mNotUploadTv;
    private MKOfflineMap mOffline = null;
    private LocationClient mLocationClient;
    private KProgressHUD mProgressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        createOfflineDialog();
        SampleMultiplePermissionListener multiplePermissionListener = new SampleMultiplePermissionListener(this, new SampleMultiplePermissionListener.PermissionsChecked() {
            @Override
            public void permissionsChecked(boolean check) {
                if (check){
                    if (NetworkUtil.isNetworkAvailable(MainCateActivity.this)&&getVersionCode(MainCateActivity.this)>=10){
                        initLocation();
                    }
                }
            }
        });
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new CompositeMultiplePermissionsListener(multiplePermissionListener, DialogBuild.getBuild().createPermissionDialog(this,"权限提醒","请给予拍照和定位的权限")))
                .check();
        setContentView(R.layout.activity_main_cate);
        Beta.checkUpgrade(false,false);
        NotifyListenerMangager.getInstance().registerListener(this, Constants.NET_STATE);
        initView();
        mProgressDialog = DialogBuild.getBuild().createCommonProgressDialog(this);
    }
    /**
     * 获取当前本地apk的版本
     *
     * @param mContext 上下文
     * @return 版本名称
     */
    private   int getVersionCode(Context mContext) {
        int  versionName =0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    private void checkOffLineMap(){
        ArrayList<MKOLUpdateElement> records2 = mOffline.getAllUpdateInfo();
        if (records2!=null&&records2.size()>0){
            if (TextUtils.isEmpty(SharedPreferenceUtils.getString(mContext,"city"))){
                return;
            }
            List<String> mList = new ArrayList<>();
            for (MKOLUpdateElement record:records2) {
                mList.add(record.cityName);
            }
            if (!mList.contains(SharedPreferenceUtils.getString(mContext,"city"))){
                if (!isFinishing()&&!alertDialog.isShowing()){
                    alertDialog.show();
                }
            }
        }else {
            if (!isFinishing()&&!alertDialog.isShowing()){
                alertDialog.show();
            }
        }
    }
    private AlertDialog alertDialog;
    private void createOfflineDialog(){
         alertDialog = new AlertDialog.Builder(this)
                .setTitle("提醒")
                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startDownLoadOffLine();
                    }
                })
                .setMessage("请先下载离线地图")
                .create();
    }
    private int cityId;
    private void startDownLoadOffLine(){
        ArrayList<MKOLSearchRecord> records = mOffline.searchCity(SharedPreferenceUtils.getString(mContext,"city"));
        if (records != null && records.size() == 1) {
            cityId = records.get(0).cityID;
        }
        mOffline.start(cityId);
    }


    @Override
    protected void onDestroy() {
        /*
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        if (null!=mLocationClient){
            mLocationClient.stop();
        }
        if (null!=alertDialog){
            alertDialog.dismiss();
        }
        super.onDestroy();
    }


    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        findViewById(R.id.back_layout).setVisibility(View.GONE);
        findViewById(R.id.left_tv).setVisibility(View.VISIBLE);
        findViewById(R.id.left_tv).setOnClickListener(this);
        titleTv.setText("");
        TextView outLoginTv = findViewById(R.id.publish_tv);
        outLoginTv.setText("退出登录");
        outLoginTv.setOnClickListener(this);
        outLoginTv.setVisibility(View.VISIBLE);
        mNotUploadTv = findViewById(R.id.data_upload);
        mNotUploadTv.setOnClickListener(this);
    }

    private void findNotUploadData(boolean connect){
        List<UploadLocationEntry> uploadLocationEntries = BaseApplication.getInstances().getDaoSession().getUploadLocationEntryDao().loadAll();
        if (uploadLocationEntries!=null&&uploadLocationEntries.size()>0){
            mNotUploadTv.setVisibility(View.VISIBLE);
        }else {
            mNotUploadTv.setVisibility(View.GONE);
        }

    }

    public void toLocation(View view) {
        if (!SharedPreferenceUtils.getString(this,"type").equals("4")){
            Toast.makeText(mContext, "只有公司层级才能添加采伐数据", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }

    public void toTotal(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void toQuery(View view) {
        Intent intent = new Intent(this, QueryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_upload:
                    Intent intent = new Intent(this, NotUploadActivity.class);
                    startActivity(intent);
                break;
            case R.id.publish_tv:
                createOutLogin();
                break;
            case R.id.left_tv:
                Beta.checkUpgrade(true,false);
                break;
        }
    }
    private void createOutLogin(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提醒")
                .setMessage("退出登录")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!TextUtils.isEmpty(SharedPreferenceUtils.getString(mContext,"dataScope"))){
                            SharedPreferenceUtils.deleteString(mContext,"dataScope");
                            SharedPreferenceUtils.deleteString(mContext,"roleId");
                        }
                        SharedPreferenceUtils.saveBoolean(mContext,"autoLogin",false);
                        Intent intent = new Intent(mContext,LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                })
                .create();
        if (!isFinishing()){
            alertDialog.show();
        }
    }

    @Override
    public void notifyContext(Object obj) {
        if (obj.toString().equals("连上")){
            findNotUploadData(true);
        }else {
            findNotUploadData(false);
        }
    }


    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    if (update.ratio==100){
                        mProgressDialog.setProgress(update.ratio);
                        Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }else {
                        if (!mProgressDialog.isShowing()){
                            mProgressDialog.show();
                        }
                        mProgressDialog.setProgress(update.ratio);
                    }

                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                ArrayList<MKOLSearchRecord> records = mOffline.searchCity(SharedPreferenceUtils.getString(mContext,"city"));
                if (records != null && records.size() == 1) {
                    cityId = records.get(0).cityID;
                }
                mOffline.update(cityId);
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }

    }

    private void initLocation() {
        //定位客户端的设置
        //定位初始化

        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIsNeedAddress(true);
        option.setScanSpan(0);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener  myLocationListener = new MyLocationListener();

        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }
    private int index;

    //自定义的定位监听
    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map

            if (location == null){
                return;
            }
            index++;
            SharedPreferenceUtils.saveString(mContext,"city",location.getCity());
            if (index==1&&!TextUtils.isEmpty(location.getCity())){
                checkOffLineMap();
            }

        }
    }
}
