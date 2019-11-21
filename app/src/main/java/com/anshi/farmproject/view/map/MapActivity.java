package com.anshi.farmproject.view.map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MapActivity extends BaseActivity implements View.OnClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private TextView mCostTimeTv;
    private String selectTime;
    private boolean firstLoad;
    // 声明json文件变量
    private static final String CUSTOM_FILE_NAME_GRAY = "custom_map_config.json";
    //创建OverlayOptions的集合
    List<OverlayOptions> options = new ArrayList<OverlayOptions>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initLocation();
    }
    private KProgressHUD progressHUD;
    private void getListTreeInMap(String time){
        if (!isFinishing()){
            progressHUD = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cureTime",time);
           // jsonObject.put("branchId",SharedPreferenceUtils.getInt(this,"userId"));
            jsonObject.put("deptId", SharedPreferenceUtils.getInt(this,"deptId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getFelingList(requestBody)
                .map(new Func1<ResponseBody,ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=progressHUD){
                            progressHUD.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            Log.e("xxx",string);
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                DetailQueryEntry detailQueryEntry = gson.fromJson(string, DetailQueryEntry.class);
                                if (detailQueryEntry.getCode()== Constants.SUCCESS_CODE){
                                    if (detailQueryEntry.getData()!=null&&detailQueryEntry.getData().size()>0){
                                        mBaiduMap.clear();

                                        //构建Marker图标
                                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                                .fromResource(R.drawable.pg_tree);
                                        for (int i = 0; i <detailQueryEntry.getData().size(); i++) {
                                            DetailQueryEntry.DataBean dataBean = detailQueryEntry.getData().get(i);
                                            LatLng point1 = new LatLng(Double.parseDouble(detailQueryEntry.getData().get(i).getLatitude()), Double.parseDouble(detailQueryEntry.getData().get(i).getLongitude()));
                                            //创建OverlayOptions属性
                                            Bundle bundle = new Bundle();
                                            bundle.putString("address","油锯:"+dataBean.getChainsaw()+"\n"+"小地名:"+dataBean.getPlaceName());
                                            OverlayOptions option1 =  new MarkerOptions()
                                                    .position(point1)
                                                    .extraInfo(bundle)
                                                    .icon(bitmap);
                                            options.add(option1);
                                        }
                                        mBaiduMap.addOverlays(options);
                                    }
                                }else {
                                    Toast.makeText(mContext, detailQueryEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=progressHUD){
                            progressHUD.dismiss();
                        }
                        throwable.printStackTrace();
                    }
                });
    }



    private void initView() {
        TextView title = findViewById(R.id.title_tv);
        title.setText("除治信息统计");
        mCostTimeTv = findViewById(R.id.cost_money_history);
        findViewById(R.id.cost_time_iv).setOnClickListener(this);
        mCostTimeTv.setText(Utils.getTime(new Date()));
        mMapView = findViewById(R.id.mapview);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_GRAY);
        // 设置个性化地图样式文件的路径和加载方式
        mMapView.setMapCustomStylePath(customStyleFilePath);
        // 动态设置个性化地图样式是否生效
        mMapView.setMapCustomStyleEnable(true);
        mBaiduMap = mMapView.getMap();
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15.0f).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Button btn = new Button(MapActivity.this);
                btn.setBackgroundColor(Color.WHITE);
                String address = marker.getExtraInfo().getString("address");
                btn.setText(address);
                InfoWindow window = new InfoWindow(btn,marker.getPosition(),-40);
                marker.showInfoWindow(window);
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.clear();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cost_time_iv:
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = Utils.getTime(date);
                        mCostTimeTv.setText(time);
                        selectTime = Utils.getSecondTime(date);
                        getListTreeInMap(selectTime);
                    }
                }).build();
                pvTime.show();
                break;
        }
    }

    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            if (location == null || mMapView == null){
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.setMapStatus(status);//直接到中间
            if (!firstLoad){
                selectTime = Utils.getSecondTime(new Date());
                getListTreeInMap(selectTime);
                firstLoad =true;
            }
        }
    }
    /**
     * 读取json路径
     */
    private String getCustomStyleFilePath(Context context, String customStyleFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String parentPath = null;
        try {
            inputStream = context.getAssets().open("customConfigdir/" + customStyleFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            parentPath = context.getFilesDir().getAbsolutePath();
            File customStyleFile = new File(parentPath + "/" + customStyleFileName);
            if (customStyleFile.exists()) {
                customStyleFile.delete();
            }
            customStyleFile.createNewFile();

            outputStream = new FileOutputStream(customStyleFile);
            outputStream.write(buffer);
        } catch (IOException e) {
            Log.e("CustomMapDemo", "Copy custom style file failed", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("CustomMapDemo", "Close stream failed", e);
                return null;
            }
        }
        return parentPath + "/" + customStyleFileName;
    }
    private void initLocation() {
        //定位客户端的设置
        //定位初始化
        mLocationClient = new LocationClient(this);

//通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(0);

//设置locationClientOption
        mLocationClient.setLocOption(option);

//注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }
}
