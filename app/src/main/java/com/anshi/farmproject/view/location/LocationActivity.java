package com.anshi.farmproject.view.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseApplication;
import com.anshi.farmproject.entry.DealTypeEntry;
import com.anshi.farmproject.entry.ImageEntry;
import com.anshi.farmproject.entry.VillageEntry;
import com.anshi.farmproject.entry.ZhiWuEntry;
import com.anshi.farmproject.greendao.UploadLocationEntry;
import com.anshi.farmproject.greendao.UploadLocationEntryDao;
import com.anshi.farmproject.net.AppHttpService;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SDCardUtil;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.anshi.farmproject.utils.WeakHandler;
import com.anshi.farmproject.utils.check.SampleMultiplePermissionListener;
import com.anshi.farmproject.utils.gpsutils.GPSUtils;
import com.anshi.farmproject.utils.gpsutils.Gps;
import com.anshi.farmproject.utils.gpsutils.PositionUtil;
import com.anshi.farmproject.utils.pinyin.LanguageConvent;
import com.anshi.farmproject.utils.watermask.WaterMaskUtil;
import com.anshi.farmproject.view.image.ImageActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.NetworkUtil;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.TResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class LocationActivity extends TakePhotoActivity implements View.OnClickListener {
    private View mCommonLayout;
    private ImageView mExpandIv;
    private ImageView mCollapseIv;
    private EditText mAddressEt,mGroupEt,mRadiusEt;
    private TextView mLocationTv;
    private KProgressHUD commonLoadDialog;
    private RelativeLayout mAroundTakePhotoLayout,mNumberTakePhotoLayout;
    private ImageView mAroundIv,mNumberIv;
    private int which;
    private Uri outUri;
    private LocationClient mLocationClient;
    private Spinner mDealTypeSpinner;
    private Spinner mVillageSpinner;
    private Spinner mZhiWuSpinner;
    private TextView mNumberTv,mTimeTv,mOwnTownTv,mWorkerTv;
    private EditText mNumberEt;
    private WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
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
    private TakePhoto takePhoto;
    private AppHttpService mService;
    private List<ZhiWuEntry.DataBean> zhiWuEntryData;
    private List<DealTypeEntry.DataBean> dealTypeEntryData;
    private List<VillageEntry.DataBean> villageEntryData;
    private String secondTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(LocationActivity.this, "定位中..");
        initView();
        SampleMultiplePermissionListener multiplePermissionListener = new SampleMultiplePermissionListener(this, new SampleMultiplePermissionListener.PermissionsChecked() {
            @Override
            public void permissionsChecked(boolean check) {
                if (check){
                    if (NetworkUtil.isNetworkAvailable(LocationActivity.this)){
                         initLocation();
                    }else {
                        new GPSUtils(LocationActivity.this);
                        location5seconds();
                    }

                }
            }
        });

        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION).withListener(new CompositeMultiplePermissionsListener(multiplePermissionListener, DialogBuild.getBuild().createPermissionDialog(this,"权限提醒","请给予定位的权限")))
                .check();
        takePhoto = getTakePhoto();
        addEventListener();
        mService = BaseApplication.getInstances().getAppRetrofit().create(AppHttpService.class);
        getZhiWuData();
        getDealTypeList();
        getVillageData();

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
    public void back(View view){
        finish();
    }
    private String saveToSdCardOne;
    private String saveToSdCardTwo;

    private void initSpinnerData(Spinner spinner, List<String> strings) {
        ArrayAdapter<String> adapterThree = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapterThree);
    }

    @Override
    public void takeSuccess(TResult result) {
        addWaterMask(BitmapFactory.decodeFile(result.getImage().getCompressPath()),which);
    }

    private void addWaterMask(Bitmap sourceBitmap,int which){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("经度:").append(gps.getWgLon()).append("\n");
        stringBuilder.append("纬度:").append(gps.getWgLat()).append("\n");
        if (!TextUtils.isEmpty(mCurrentAddress)){
            stringBuilder.append("地址:").append(mCurrentAddress).append("\n");
        }
        stringBuilder.append("时间:").append(Utils.getSecondTime(new Date()));

        Bitmap waterMaskLeftBottom = WaterMaskUtil.drawTextToLeftBottom(this, sourceBitmap, stringBuilder.toString(), 6,Color.WHITE,5, 40);
        if (which==1){
            mAroundIv.setVisibility(View.VISIBLE);
            saveToSdCardOne = SDCardUtil.saveToSdCard(waterMaskLeftBottom);
            mAroundIv.setImageBitmap(BitmapFactory.decodeFile(saveToSdCardOne));
            uploadImage(saveToSdCardOne);
        }else {
            mNumberIv.setVisibility(View.VISIBLE);
            saveToSdCardTwo = SDCardUtil.saveToSdCard(waterMaskLeftBottom);
            mNumberIv.setImageBitmap(BitmapFactory.decodeFile(saveToSdCardTwo));
            uploadImage(saveToSdCardTwo);
        }
    }
    private String mCurrentAddress ;
    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Gps gps;
    private void getLocation(){
        Location location = GPSUtils.getLocation();
        if (null!=location){
            commonLoadDialog.dismiss();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Gps gps84_to_gcj02 = PositionUtil.gps84_To_Gcj02(latitude, longitude);
            if (null!=gps84_to_gcj02){
                gps = PositionUtil.gcj02_To_Bd09(gps84_to_gcj02.getWgLat(), gps84_to_gcj02.getWgLon());
            }
            mLocationTv.setText(String.format(getString(R.string.location_format),String.valueOf(gps.getWgLon()),String.valueOf(gps.getWgLat())));
            weakHandler.removeCallbacksAndMessages(null);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient!=null&&mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (NetworkUtil.isNetworkAvailable(LocationActivity.this)){
                    initLocation();
                }else {
                    new GPSUtils(LocationActivity.this);
                    location5seconds();
                }
                break;
        }
    }
    private  int mCurrentNumber;
    private void initView() {
        TextView titleTv= findViewById(R.id.title_tv);
        titleTv.setText("提交除治信息");
        mNumberTv = findViewById(R.id.number_tv);
        mNumberEt = findViewById(R.id.number_et);
        mRadiusEt = findViewById(R.id.radius_et);
        mRadiusEt.setText(SharedPreferenceUtils.getFloat(this,Constants.RADIUS_DATA)==0?"":String.valueOf(SharedPreferenceUtils.getFloat(this,Constants.RADIUS_DATA)));
        mZhiWuSpinner = findViewById(R.id.zhiwu_spinner);
        int saveNumber = SharedPreferenceUtils.getInt(this, Constants.DEAL_NUMBER);
        mCurrentNumber = saveNumber+1;
        mNumberEt.setText(String.valueOf(mCurrentNumber));
        mNumberEt.setSelection(String.valueOf(mCurrentNumber).length());
        formatNumber();
        mExpandIv = findViewById(R.id.expand_iv);
        mCollapseIv = findViewById(R.id.collapse_iv);
        mExpandIv.setOnClickListener(this);
        mCollapseIv.setOnClickListener(this);
        mCommonLayout = findViewById(R.id.common_normal_layout);
        mAddressEt= findViewById(R.id.address_et);//具体位置
        if (!TextUtils.isEmpty(SharedPreferenceUtils.getString(this,Constants.PLACE_DATA))){
            mAddressEt.setText(SharedPreferenceUtils.getString(this,Constants.PLACE_DATA));
        }
        mGroupEt = findViewById(R.id.group_et);
        if (SharedPreferenceUtils.getInt(this,Constants.GROUP_DATA)>0){
            mGroupEt.setText(String.valueOf(SharedPreferenceUtils.getInt(this,Constants.GROUP_DATA)));
        }

        mLocationTv = findViewById(R.id.location_tv);//经纬度
        mAroundTakePhotoLayout = findViewById(R.id.around_layout);
        mNumberTakePhotoLayout = findViewById(R.id.number_layout);
        mAroundTakePhotoLayout.setOnClickListener(this);
        mNumberTakePhotoLayout.setOnClickListener(this);
        mTimeTv = findViewById(R.id.time_tv);
        mTimeTv.setText(Utils.getMintuteTime(new Date()));
        secondTime = Utils.getSecondTime(new Date());
        mOwnTownTv = findViewById(R.id.town_tv);
        mOwnTownTv.setText(SharedPreferenceUtils.getString(this,"townName"));
        mWorkerTv = findViewById(R.id.worker_tv);
        String userName = SharedPreferenceUtils.getString(this, "userName");
        mWorkerTv.setText(userName);
        mAroundIv = findViewById(R.id.around_iv);
        mNumberIv = findViewById(R.id.number_iv);
        mAroundIv.setOnClickListener(this);
        mNumberIv.setOnClickListener(this);
        mDealTypeSpinner = findViewById(R.id.deal_spinner);
        mVillageSpinner = findViewById(R.id.village_spinner);
    }
    private String mCurrentZhiWuId;
    private String mCurrentZhiWuName;
    private String mDealTypeId;
    private String mDealName;
    private String mVillageId;
    private String mVillageName;

    private boolean isPhotoUploadSuccess;


    /**
     * 获取除治类型
     */
    private void getDealTypeList(){
        mService.getMyCureList()
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                DealTypeEntry dealTypeEntry = gson.fromJson(string, DealTypeEntry.class);
                                if (dealTypeEntry.getCode()==Constants.SUCCESS_CODE){
                                    if (dealTypeEntry.getData()!=null&&dealTypeEntry.getData().size()>0){
                                        List<String> mList = new ArrayList<>();
                                        dealTypeEntryData = dealTypeEntry.getData();
                                        for (int i = 0; i <dealTypeEntryData.size() ; i++) {
                                            mList.add(dealTypeEntryData.get(i).getCureName());
                                        }
                                        initSpinnerData(mDealTypeSpinner,mList);
                                        mDealTypeSpinner.setSelection(SharedPreferenceUtils.getInt(LocationActivity.this,Constants.DEAL_TYPE_POSITION));
                                    }
                                }else {
                                    Toast.makeText(LocationActivity.this, dealTypeEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        String zhiwuData = SharedPreferenceUtils.getString(LocationActivity.this, Constants.DEAL_TYPE_DATA);
                        String substring = zhiwuData.substring(0, zhiwuData.lastIndexOf(","));
                        List<String> mList = new ArrayList<>();
                        mList.add(substring);
                        initSpinnerData(mDealTypeSpinner,mList);
                        mDealTypeId = zhiwuData.substring(zhiwuData.lastIndexOf(",") + 1, zhiwuData.length());
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 获取村
     */
    private void getVillageData(){
        int townId = SharedPreferenceUtils.getInt(this, "townId");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("deptId",townId);
            jsonObject.put("type",SharedPreferenceUtils.getString(this,"type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getRelationDeptList(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                VillageEntry villageEntry = gson.fromJson(string, VillageEntry.class);
                                if (villageEntry.getCode()==Constants.SUCCESS_CODE){
                                    if (villageEntry.getData()!=null&&villageEntry.getData().size()>0){
                                        List<String> mList = new ArrayList<>();
                                        villageEntryData = villageEntry.getData();
                                        for (int i = 0; i <villageEntryData .size() ; i++) {
                                            mList.add(villageEntryData.get(i).getDeptName());
                                        }
                                        initSpinnerData(mVillageSpinner,mList);
                                        mVillageSpinner.setSelection(SharedPreferenceUtils.getInt(LocationActivity.this,Constants.SAVE_VILLAGE_POSITION));
                                    }
                                }else {
                                    Toast.makeText(LocationActivity.this, villageEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        String zhiwuData = SharedPreferenceUtils.getString(LocationActivity.this, Constants.VILLAGE_DATA);
                        String substring = zhiwuData.substring(0, zhiwuData.lastIndexOf(","));
                        List<String> mList = new ArrayList<>();
                        mList.add(substring);
                        initSpinnerData(mVillageSpinner,mList);
                        mVillageId = zhiwuData.substring(zhiwuData.lastIndexOf(",") + 1, zhiwuData.length());
                        throwable.printStackTrace();
                    }
                });
    }


    private void uploadImage(String path){
        final KProgressHUD commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在上传");
        JSONObject jsonObject = new JSONObject();
        try {
            String fileToBase64 = Utils.fileToBase64(path);
            jsonObject.put("imgData",fileToBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.uploadImg(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                ImageEntry imageEntry = gson.fromJson(string,ImageEntry.class);
                                if (imageEntry.getCode()==Constants.SUCCESS_CODE){
                                    isPhotoUploadSuccess = true;
                                    Toast.makeText(LocationActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                    if (which==1){
                                        saveToSdCardOne = imageEntry.getData().getSavePath();
                                        Log.e("xxx",saveToSdCardOne);
                                    }else {
                                        saveToSdCardTwo = imageEntry.getData().getSavePath();
                                        Log.e("xxx",saveToSdCardTwo);
                                    }
                                }else {
                                    isPhotoUploadSuccess =false;
                                    Toast.makeText(LocationActivity.this, imageEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        throwable.printStackTrace();
                    }
                });

    }


    /**
     * 获取植物类型
     */
    private void getZhiWuData(){
        mService.getBotanyList()
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            if (Utils.isGoodJson(string)){
                                Gson gson = new Gson();
                                ZhiWuEntry zhiWuEntry = gson.fromJson(string, ZhiWuEntry.class);
                                if (zhiWuEntry.getCode()==Constants.SUCCESS_CODE){
                                    if (zhiWuEntry.getData()!=null&&zhiWuEntry.getData().size()>0){
                                        zhiWuEntryData = zhiWuEntry.getData();
                                        List<String> mList = new ArrayList<>();
                                        for (int i = 0; i <zhiWuEntryData.size(); i++) {
                                            mList.add(zhiWuEntry.getData().get(i).getBotanyName());
                                        }
                                        initSpinnerData(mZhiWuSpinner,mList);
                                        mZhiWuSpinner.setSelection(SharedPreferenceUtils.getInt(LocationActivity.this,Constants.ZHIWU_POSITION));
                                    }
                                }else {
                                    Toast.makeText(LocationActivity.this, zhiWuEntry.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        String zhiwuData = SharedPreferenceUtils.getString(LocationActivity.this, Constants.ZHI_WU_DATA);
                        String substring = zhiwuData.substring(0, zhiwuData.lastIndexOf(","));
                        List<String> mList = new ArrayList<>();
                        mList.add(substring);
                        initSpinnerData(mZhiWuSpinner,mList);

                        mCurrentZhiWuId = zhiwuData.substring(zhiwuData.lastIndexOf(",") + 1, zhiwuData.length());
                        throwable.printStackTrace();
                    }
                });
    }
//    number	String	编号
//    orders	Long	序号
//    company	String	除治单位
//    cureTime	Date	除治时间
//    cureId	Long	除治类型id
//    townId	Long	乡镇id
//    villageId	Long	村庄id
//    teamId	Long	除治队id
//    branchId	Long	油锯id
//    groups	Long	组
//    groundDiameter	Double	伐桩地径
//    placeName	String	小地名
//    botanyId	Long	植物id
//    dataSources	String	数据来源
//    longitude	String	经度
//    latitude	String	纬度
//    chainsaw	String	责任油锯
//    panoramaPath	String	全景拍照路径
//    numberPath	String	编号拍照路径
//    userId	Long	用户id
//    deptId	Long	部门id
//    createBy	String	用户账号
    private void uploadFormation(){
        if (!isFinishing()){
            commonLoadDialog  = DialogBuild.getBuild().createCommonLoadDialog(this,"正在上传");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number",mNumberTv.getText().toString());
            jsonObject.put("orders",mCurrentNumber);
            jsonObject.put("company",SharedPreferenceUtils.getString(this,"deptName"));
            jsonObject.put("cureTime",secondTime);
            jsonObject.put("cureId",Long.parseLong(mDealTypeId));
            jsonObject.put("townId",SharedPreferenceUtils.getInt(this,"townId"));
            jsonObject.put("villageId",Long.parseLong(mVillageId));
            jsonObject.put("groups",Long.parseLong(mGroupEt.getText().toString()));
            jsonObject.put("groundDiameter",Double.parseDouble(mRadiusEt.getText().toString()));
            jsonObject.put("placeName",mAddressEt.getText().toString());
            jsonObject.put("botanyId",Long.parseLong(mCurrentZhiWuId));
            jsonObject.put("longitude",String.valueOf(gps.getWgLon()));
            jsonObject.put("latitude",String.valueOf(gps.getWgLat()));
            jsonObject.put("chainsaw",SharedPreferenceUtils.getString(this,"userName"));
            jsonObject.put("panoramaPath",saveToSdCardOne);
            jsonObject.put("numberPath",saveToSdCardTwo);
            jsonObject.put("teamId",SharedPreferenceUtils.getInt(this,"deptId"));
            jsonObject.put("branchId",SharedPreferenceUtils.getInt(this,"userId"));
            jsonObject.put("userId",SharedPreferenceUtils.getInt(this,"userId"));
            jsonObject.put("deptId",SharedPreferenceUtils.getInt(this,"deptId"));
            jsonObject.put("createBy",SharedPreferenceUtils.getString(this,"loginName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.insertFeling(requestBody)
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        try {
                            String string = responseBody.string();
                            Log.e("xxx",string);
                            if (Utils.isGoodJson(string)){
                                try {
                                    JSONObject jsonObject1 = new JSONObject(string);
                                    int code = jsonObject1.getInt("code");
                                    String msg = jsonObject1.getString("msg");
                                    if (code==Constants.SUCCESS_CODE){
                                        SharedPreferenceUtils.saveInt(LocationActivity.this,Constants.DEAL_NUMBER,mCurrentNumber);
                                        Toast.makeText(LocationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        },1000);
                                    }else {
                                        Toast.makeText(LocationActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        saveLocal();
                        throwable.printStackTrace();
                    }
                });

    }



    @SuppressLint("SetTextI18n")
    private void formatNumber(){
        @SuppressLint("DefaultLocale") String format = String.format("%04d", mCurrentNumber );
        String townName = SharedPreferenceUtils.getString(this, "townName");
        String loginName = SharedPreferenceUtils.getString(this, "userName");
        String deptName = SharedPreferenceUtils.getString(this, "deptName");
        StringBuilder pinYinTown = new StringBuilder();
        StringBuilder pinYinLoginName = new StringBuilder();
        StringBuilder pinYinDeptName = new StringBuilder();
        for (int i = 0; i <townName.toCharArray().length ; i++) {
            pinYinTown.append(LanguageConvent.getFirstChar(String.valueOf(townName.toCharArray()[i])));
        }
        for (int i = 0; i <loginName.toCharArray().length ; i++) {
            pinYinLoginName.append(LanguageConvent.getFirstChar(String.valueOf(loginName.toCharArray()[i])));
        }
        for (int i = 0; i <deptName.toCharArray().length ; i++) {
            pinYinDeptName.append(LanguageConvent.getFirstChar(String.valueOf(deptName.toCharArray()[i])));
        }
        if (!TextUtils.isEmpty(pinYinTown.toString())){
            mNumberTv.setText(pinYinTown.toString()+"-"+pinYinDeptName.toString()+"-"+pinYinLoginName.toString()+"-"+format);
        }else {
            mNumberTv.setText(pinYinDeptName.toString()+"-"+pinYinLoginName.toString()+"-"+format);
        }
    }
    private void addEventListener(){
        mDealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferenceUtils.saveInt(LocationActivity.this, Constants.DEAL_TYPE_POSITION,position);
                if (null!=dealTypeEntryData){
                    DealTypeEntry.DataBean dataBean = dealTypeEntryData.get(position);
                    mDealTypeId = String.valueOf(dataBean.getCureId());
                    mDealName = dataBean.getCureName();
                    SharedPreferenceUtils.saveString(LocationActivity.this,Constants.DEAL_TYPE_DATA,dataBean.getCureName()+","+dataBean.getCureId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mZhiWuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferenceUtils.saveInt(LocationActivity.this,Constants.ZHIWU_POSITION,position);
                if (null!=zhiWuEntryData){
                    ZhiWuEntry.DataBean dataBean = zhiWuEntryData.get(position);
                    mCurrentZhiWuId = String.valueOf(dataBean.getBotanyId());
                    mCurrentZhiWuName = dataBean.getBotanyName();
                    SharedPreferenceUtils.saveString(LocationActivity.this,Constants.ZHI_WU_DATA,dataBean.getBotanyName()+","+dataBean.getBotanyId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mVillageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferenceUtils.saveInt(LocationActivity.this,Constants.SAVE_VILLAGE_POSITION,position);
                if (null!=villageEntryData){
                    VillageEntry.DataBean dataBean = villageEntryData.get(position);
                    mVillageId = String.valueOf(dataBean.getDeptId());
                    mVillageName = dataBean.getDeptName();
                    SharedPreferenceUtils.saveString(LocationActivity.this,Constants.VILLAGE_DATA,dataBean.getDeptName()+","+dataBean.getDeptId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)){
                    mCurrentNumber = Integer.parseInt(s.toString());
                    formatNumber();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void selectFile(int which){
        this.which = which;
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (mkdirs){
                Log.d("xxx","创建成功");
            }else {
                Log.d("xxx","文件已存在");
            }
        }
        outUri= Uri.fromFile(file);
        CompressConfig compressConfig=new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);
        takePhoto.onPickFromCapture(outUri);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.expand_iv:
                mCommonLayout.setVisibility(View.VISIBLE);
                mExpandIv.setVisibility(View.GONE);
                mCollapseIv.setVisibility(View.VISIBLE);
                break;
            case R.id.collapse_iv:
                mCommonLayout.setVisibility(View.GONE);
                mExpandIv.setVisibility(View.VISIBLE);
                mCollapseIv.setVisibility(View.GONE);
                break;
            case R.id.around_layout:
                selectFile(1);
                break;
            case R.id.number_layout:
                selectFile(2);
                break;
            case R.id.around_iv:
                Intent intent = new Intent(this, ImageActivity.class);
                intent.putExtra("isUpload",isPhotoUploadSuccess);
                intent.putExtra("picPath",saveToSdCardOne);
                startActivity(intent);
                break;
            case R.id.number_iv:
                Intent intent2 = new Intent(this, ImageActivity.class);
                intent2.putExtra("picPath",saveToSdCardTwo);
                intent2.putExtra("isUpload",isPhotoUploadSuccess);
                startActivity(intent2);
                break;
        }
    }

    public void upLoadFormation(View view) {
        if (TextUtils.isEmpty(mGroupEt.getText())){
            Toast.makeText(this, "请输入组", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mNumberEt.getText())){
            Toast.makeText(this, "请输入序号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mAddressEt.getText())){
            Toast.makeText(this, "请输入小地名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(saveToSdCardOne)){
            Toast.makeText(this, "请选择全景照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(saveToSdCardTwo)){
            Toast.makeText(this, "请选择编号照片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mRadiusEt.getText())){
            Toast.makeText(this, "请输入伐桩地径", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(mNumberEt.getText().toString())<SharedPreferenceUtils.getInt(LocationActivity.this,Constants.DEAL_NUMBER)){
            Toast.makeText(LocationActivity.this, "请输入大于当前编号的数字", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferenceUtils.saveString(this,Constants.PLACE_DATA,mAddressEt.getText().toString());
        SharedPreferenceUtils.saveInt(this,Constants.GROUP_DATA,Integer.parseInt(mGroupEt.getText().toString()));
        SharedPreferenceUtils.saveFloat(this,Constants.RADIUS_DATA,Float.parseFloat(mRadiusEt.getText().toString()));
        uploadFormation();


    }

    private void saveLocal(){
        SharedPreferenceUtils.saveInt(this,Constants.DEAL_NUMBER,mCurrentNumber);
        UploadLocationEntryDao uploadLocationEntryDao = BaseApplication.getInstances().getDaoSession().getUploadLocationEntryDao();
        UploadLocationEntry uploadLocationEntry = new UploadLocationEntry();
        uploadLocationEntry.setRealNumber(mNumberTv.getText().toString());//编号
        uploadLocationEntry.setUploadNumber((long) mCurrentNumber);//采伐序号
        uploadLocationEntry.setAddressName(mAddressEt.getText().toString());//小地名
        uploadLocationEntry.setAroundIvPath(saveToSdCardOne);//全景照片
        uploadLocationEntry.setNumberIvPath(saveToSdCardTwo);//编号照片
        uploadLocationEntry.setDealTime(secondTime);//时间
        uploadLocationEntry.setDealType(mDealName);
        uploadLocationEntry.setDealTypePosition(Integer.parseInt(mDealTypeId));
        uploadLocationEntry.setVillageName(mVillageName);
        uploadLocationEntry.setZhiwuName(mCurrentZhiWuName);
        uploadLocationEntry.setZhiwuId(Integer.parseInt(mCurrentZhiWuId));
        uploadLocationEntry.setWokerName(mWorkerTv.getText().toString());
        uploadLocationEntry.setVillagePosition(Integer.parseInt(mVillageId));//就是ID
        uploadLocationEntry.setGroupNumber(Integer.parseInt(mGroupEt.getText().toString()));//组
        uploadLocationEntry.setOwnTown(mOwnTownTv.getText().toString());//所属乡镇
        uploadLocationEntry.setLatitude(gps.getWgLat());//纬度
        uploadLocationEntry.setLongtitude(gps.getWgLon());//经度
        uploadLocationEntry.setRadius(Double.parseDouble(mRadiusEt.getText().toString()));//伐地半径
        if (!TextUtils.isEmpty(mCurrentAddress)){
            uploadLocationEntry.setDetailAddress(mCurrentAddress);//具体地名
        }
        uploadLocationEntryDao.insert(uploadLocationEntry);
        Toast.makeText(this, "信息已保存本地", Toast.LENGTH_SHORT).show();
    }


    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的location信息给百度map
            commonLoadDialog.dismiss();
            if (location == null){
                return;
            }
            gps = new Gps(location.getLatitude(),location.getLongitude());
            mCurrentAddress = location.getAddrStr();
            mLocationTv.setText(mCurrentAddress);

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
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
//开启地图定位图层
        mLocationClient.start();
    }

}
