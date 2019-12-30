package com.anshi.farmproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.WeakHandler;
import com.anshi.farmproject.utils.check.SampleMultiplePermissionListener;
import com.anshi.farmproject.utils.gpsutils.GPSUtils;
import com.anshi.farmproject.utils.gpsutils.Gps;
import com.anshi.farmproject.utils.gpsutils.PositionUtil;
import com.anshi.farmproject.utils.notifylistener.INotifyListener;
import com.anshi.farmproject.utils.notifylistener.NotifyListenerMangager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;

public class MainActivity extends BaseActivity implements INotifyListener{
    private TextView mTv;
    private TextView mNetTv;

//    private KProgressHUD commonLoadDialog;
    private WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 22:
                    getLocation();
                    break;

            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       // commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this, "定位中..");
        SampleMultiplePermissionListener multiplePermissionListener = new SampleMultiplePermissionListener(this, new SampleMultiplePermissionListener.PermissionsChecked() {
            @Override
            public void permissionsChecked(boolean check) {
                if (check){
                    new GPSUtils(MainActivity.this);
                    location5seconds();
                }
            }
        });
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).withListener(new CompositeMultiplePermissionsListener(multiplePermissionListener, DialogBuild.getBuild().createPermissionDialog(this,"权限提醒","请给予定位的权限")))
                .check();
        NotifyListenerMangager.getInstance().registerListener(this, Constants.NET_STATE);

    }


    private void location5seconds(){

        weakHandler.post(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 22;
                weakHandler.sendMessage(message);
                weakHandler.postDelayed(this,5000);

            }
        });
    }

    private void getLocation(){
        Location location = GPSUtils.getLocation();
        if (null!=location){
//            if (null!=commonLoadDialog){
//                commonLoadDialog.dismiss();
//            }
            StringBuilder stringBuilder = new StringBuilder();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            stringBuilder.append("GPS坐标 纬度").append(latitude).append("\t"+"经度:").append(longitude).append("\n");
            String addressStr = GPSUtils.getAddressStr();
            Gps gps84_to_gcj02 = PositionUtil.gps84_To_Gcj02(latitude, longitude);
            if (null!=gps84_to_gcj02){
                Gps gps = PositionUtil.gcj02_To_Bd09(gps84_to_gcj02.getWgLat(), gps84_to_gcj02.getWgLon());
                stringBuilder.append("百度地图坐标 纬度:").append(gps.getWgLat()).append("\t"+"经度:").append(gps.getWgLon()).append("\n");
            }
//            if (!TextUtils.isEmpty(addressStr)){
//                stringBuilder.append("地址:").append(addressStr);
//            }
            stringBuilder.append("\n").append("海拔:").append(location.getAltitude());
            mTv.setText(stringBuilder);
            weakHandler.removeCallbacksAndMessages(null);
        }
    }

    private void initView() {
        mTv = findViewById(R.id.tv);
        mNetTv = findViewById(R.id.net_tv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                new GPSUtils(this);
                getLocation();
                break;
        }
    }

    @Override
    public void notifyContext(Object obj) {
        if (obj.toString().equals("连上")){
            mNetTv.setText("网络正常");
        }else {
            mNetTv.setText("网络断开");
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }


}
