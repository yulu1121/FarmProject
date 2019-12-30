package com.anshi.farmproject.view.map;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.anshi.farmproject.view.area.AreaActivity;
import com.anshi.farmproject.view.area.VillageActivity;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
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
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TestMapActivity extends BaseActivity implements View.OnClickListener,MKOfflineMapListener,DatePickerDialog.OnDateSetListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private TextView mCostTimeTv;
    private String mCostStartTime;
    private DatePickerDialog dpd;
    private String mCostEndTime;
    private boolean firstLoad;
    // 声明json文件变量
    private static final String CUSTOM_FILE_NAME_GRAY = "custom_map_config.json";
    //创建OverlayOptions的集合
    List<OverlayOptions> options = new ArrayList<OverlayOptions>();
    private MKOfflineMap mkOfflineMap;
    public static final int CODE_DATA = 321;
    private int deptId;
    private boolean select =false;
    private EditText mTreeEt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mkOfflineMap=new MKOfflineMap();
        mkOfflineMap.init(this);
        initView();
        initLocation();
    }
    private KProgressHUD progressHUD;
    private void getListTreeInMap(final String starTime, String endTime, int deptId, boolean select){
        if (!isFinishing()){
            progressHUD = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("startTime",starTime);
            jsonObject.put("endTime",endTime);
            jsonObject.put("userId",SharedPreferenceUtils.getInt(mContext,"userId"));
            if (SharedPreferenceUtils.getString(this,"type").equals("4")&&!select){
                jsonObject.put("branchId",SharedPreferenceUtils.getInt(this,"userId"));
            }else {
                jsonObject.put("deptId", deptId);
            }
            jsonObject.put("pageSize",100);
            jsonObject.put("pageNum",1);
            if (!TextUtils.isEmpty(mTreeEt.getText())){
                jsonObject.put("number",mTreeEt.getText().toString());
            }

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
                                    options.clear();
                                    mBaiduMap.clear();
                                    if (detailQueryEntry.getData().getFellingList()!=null&&detailQueryEntry.getData().getFellingList().size()>0){
                                        SharedPreferenceUtils.putList(mContext,"mapList", detailQueryEntry.getData().getFellingList());
                                        //构建Marker图标
                                        BitmapDescriptor bitmap;
                                        for (int i = 0; i <detailQueryEntry.getData().getFellingList().size(); i++) {
                                            DetailQueryEntry.DataBean.FellingListBean dataBean = detailQueryEntry.getData().getFellingList().get(i);
                                            LatLng point1 = new LatLng(Double.parseDouble(detailQueryEntry.getData().getFellingList().get(i).getLatitude()), Double.parseDouble(detailQueryEntry.getData().getFellingList().get(i).getLongitude()));
                                            switch (dataBean.getCureName()) {
                                                case "风倒木":
                                                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.red_dot);
                                                    break;
                                                case "电力采伐":
                                                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.blue_dot);
                                                    break;
                                                case "农民自用材":
                                                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.yellow_dot);
                                                    break;
                                                case "枯死木":
                                                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.gray_dot);
                                                    break;
                                                default:
                                                    bitmap = BitmapDescriptorFactory.fromResource(R.drawable.green_dot);
                                                    break;
                                            }
                                            //创建OverlayOptions属性
                                            Bundle bundle = new Bundle();
                                            bundle.putString("chainsaw",dataBean.getChainsaw());
                                            bundle.putString("number",dataBean.getNumber());
                                            bundle.putString("place",dataBean.getPlaceName());
                                            bundle.putString("time",dataBean.getCureTime());
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
                        List<DetailQueryEntry.DataBean.FellingListBean> mapList = SharedPreferenceUtils.getList(mContext, "mapList");
                        if (null!=mapList&&mapList.size()>0){
                            //构建Marker图标
                            BitmapDescriptor bitmap;
                            for (int i = 0; i <mapList.size(); i++) {
                                DetailQueryEntry.DataBean.FellingListBean dataBean = mapList.get(i);
                                LatLng point1 = new LatLng(Double.parseDouble(mapList.get(i).getLatitude()), Double.parseDouble(mapList.get(i).getLongitude()));
                                switch (dataBean.getCureName()) {
                                    case "风倒木":
                                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.red_dot);
                                        break;
                                    case "电力采伐":
                                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.blue_dot);
                                        break;
                                    case "农民自用材":
                                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.yellow_dot);
                                        break;
                                    case "枯死木":
                                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.gray_dot);
                                        break;
                                    default:
                                        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.green_dot);
                                        break;
                                }
                                //创建OverlayOptions属性
                                Bundle bundle = new Bundle();
                                bundle.putString("chainsaw",dataBean.getChainsaw());
                                bundle.putString("number",dataBean.getNumber());
                                bundle.putString("place",dataBean.getPlaceName());
                                bundle.putString("time",dataBean.getCureTime());
                                OverlayOptions option1 =  new MarkerOptions()
                                        .position(point1)
                                        .extraInfo(bundle)
                                        .icon(bitmap);
                                options.add(option1);
                            }
                            mBaiduMap.addOverlays(options);
                        }

                        throwable.printStackTrace();
                    }
                });
    }


    private TextView mAreaTv;
    private void initView() {
        TextView title = findViewById(R.id.title_tv);
        title.setText("除治信息统计");
        deptId = SharedPreferenceUtils.getInt(mContext,"deptId");
        mAreaTv = findViewById(R.id.area_tv);
        mCostTimeTv = findViewById(R.id.cost_money_history);
        findViewById(R.id.cost_time_iv).setOnClickListener(this);
        mCostStartTime = Utils.getSecondTime(new Date());
        mCostEndTime = Utils.getSecondTime(new Date());
        mCostTimeTv.setText(Utils.getTime(new Date()));
        mMapView = findViewById(R.id.mapview);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        mTreeEt = findViewById(R.id.tree_et);
        mTreeEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mTreeEt.getText())){
                        Toast.makeText(mContext,"请输入内容",Toast.LENGTH_SHORT).show();
                    }else {
                        Utils.hideSoftMethod(mContext);
                        getListTreeInMap(mCostStartTime,mCostEndTime,deptId,select);
                    }
                }
                return false;
            }
        });


        String customStyleFilePath = getCustomStyleFilePath(this, CUSTOM_FILE_NAME_GRAY);
        // 设置个性化地图样式文件的路径和加载方式
        mMapView.setMapCustomStylePath(customStyleFilePath);
         //动态设置个性化地图样式是否生效
        mMapView.setMapCustomStyleEnable(true);
        mBaiduMap = mMapView.getMap();
//        if (NetworkUtil.isNetworkAvailable(mContext)){
//            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//        }
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15.0f).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                LinearLayout mLinearLayout = (LinearLayout) LayoutInflater.from(TestMapActivity.this).inflate(R.layout.baidu_map_infowindow,null);
                TextView mText = mLinearLayout.findViewById(R.id.tv_name);
                TextView phone = mLinearLayout.findViewById(R.id.tv_person);
                TextView textView = mLinearLayout.findViewById(R.id.tv_fuze);
                TextView numberTv = mLinearLayout.findViewById(R.id.tv_number);
                mText.setText(marker.getExtraInfo().getString("chainsaw"));
                numberTv.setText(marker.getExtraInfo().getString("number"));
                phone.setText(marker.getExtraInfo().getString("place"));
                textView.setText(marker.getExtraInfo().getString("time"));
                Button uploadBtn = mLinearLayout.findViewById(R.id.upload_btn);
                uploadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBaiduMap.hideInfoWindow();
                    }
                });
                InfoWindow window = new InfoWindow(mLinearLayout,marker.getPosition(),-47);
                marker.showInfoWindow(window);
                return true;
            }
        });
        Calendar c = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(this,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAutoHighlight(true);
        dpd.setStartTitle("开始时间");
        dpd.setEndTitle("结束时间");
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        mBaiduMap.setMyLocationEnabled(true);
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
                dpd.show(getFragmentManager(),"time");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case CODE_DATA:
                    if (null!=data){
                        String deptName = data.getStringExtra("deptName");
                        mAreaTv.setText(deptName);
                        deptId = data.getIntExtra("deptId", 0);
                        select = true;
                        getListTreeInMap(mCostStartTime,mCostEndTime,deptId, true);
                    }
                    break;
            }
        }
    }

    @Override
    public void onGetOfflineMapState(int i, int i1) {

    }

    public void selectArea(View view) {
        if (SharedPreferenceUtils.getString(this,"type").equals("4")){
            Intent intent = new Intent(mContext, VillageActivity.class);
            intent.putExtra("townId",SharedPreferenceUtils.getInt(mContext,"townId"));
            startActivityForResult(intent,CODE_DATA);
        }else {
            Intent intent = new Intent(mContext, AreaActivity.class);
            startActivityForResult(intent,CODE_DATA);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startTime = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        String endTime = yearEnd+"-"+(monthOfYearEnd+1)+"-"+dayOfMonthEnd;
        if (Utils.isDateOneBigger(startTime,endTime)){
            Toast.makeText(mContext, "开始日期不能大于结束日期", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String realStartTime = Utils.getSecondTime(simpleDateFormat.parse(startTime));
            String realEndTime=  Utils.getSecondTime(simpleDateFormat.parse(endTime));
            String textString = String.format(getString(R.string.time_format),startTime,endTime);
            mCostStartTime = realStartTime;
            mCostEndTime = realEndTime;
            mCostTimeTv.setText(textString);
            getListTreeInMap(mCostStartTime,mCostEndTime,deptId,select);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //自定义的定位监听
    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            if (location == null || mMapView == null){
                return;
            }

            MyLocationData locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.setMapStatus(status);//直接到中间
            if (!firstLoad){
                mCostStartTime = Utils.getSecondTime(new Date());
                getListTreeInMap(mCostStartTime,mCostEndTime,deptId,select);
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
