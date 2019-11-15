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
import com.anshi.farmproject.greendao.UploadLocationEntry;
import com.anshi.farmproject.greendao.UploadLocationEntryDao;
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
import com.anshi.farmproject.utils.watermask.WaterMaskUtil;
import com.anshi.farmproject.view.image.ImageActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.NetworkUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.TResult;

import java.io.File;
import java.util.Date;

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

    private void initSpinnerData(Spinner spinner, String[] strings) {
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
        }else {
            mNumberIv.setVisibility(View.VISIBLE);
            saveToSdCardTwo = SDCardUtil.saveToSdCard(waterMaskLeftBottom);
            mNumberIv.setImageBitmap(BitmapFactory.decodeFile(saveToSdCardTwo));
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
        if (mLocationClient.isStarted()){
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
        mGroupEt = findViewById(R.id.group_et);
        mLocationTv = findViewById(R.id.location_tv);//经纬度
        mAroundTakePhotoLayout = findViewById(R.id.around_layout);
        mNumberTakePhotoLayout = findViewById(R.id.number_layout);
        mAroundTakePhotoLayout.setOnClickListener(this);
        mNumberTakePhotoLayout.setOnClickListener(this);
        mTimeTv = findViewById(R.id.time_tv);
        mTimeTv.setText(Utils.getMintuteTime(new Date()));
        mOwnTownTv = findViewById(R.id.town_tv);
        mOwnTownTv.setText("桥边镇");
        mWorkerTv = findViewById(R.id.worker_tv);
        mWorkerTv.setText("油锯1");
        mAroundIv = findViewById(R.id.around_iv);
        mNumberIv = findViewById(R.id.number_iv);
        mAroundIv.setOnClickListener(this);
        mNumberIv.setOnClickListener(this);
        mDealTypeSpinner = findViewById(R.id.deal_spinner);
        initSpinnerData(mDealTypeSpinner,getResources().getStringArray(R.array.typeList));
        int position = SharedPreferenceUtils.getInt(this, Constants.DEAL_TYPE_POSITION);
        mDealTypeSpinner.setSelection(position);
        mVillageSpinner = findViewById(R.id.village_spinner);
        initSpinnerData(mVillageSpinner,getResources().getStringArray(R.array.village_list));
        int villagePosition = SharedPreferenceUtils.getInt(this,Constants.SAVE_VILLAGE_POSITION);
        mVillageSpinner.setSelection(villagePosition);

    }

    @SuppressLint("SetTextI18n")
    private void formatNumber(){
        @SuppressLint("DefaultLocale") String format = String.format("%05d", mCurrentNumber );
        mNumberTv.setText("XY-CC-AS-"+format);
    }
    private void addEventListener(){
        mDealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferenceUtils.saveInt(LocationActivity.this, Constants.DEAL_TYPE_POSITION,position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mVillageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferenceUtils.saveInt(LocationActivity.this,Constants.SAVE_VILLAGE_POSITION,position);
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
                    String number = s.toString();
                    if (Integer.parseInt(number)<SharedPreferenceUtils.getInt(LocationActivity.this,Constants.DEAL_NUMBER)){
                        Toast.makeText(LocationActivity.this, "请输入大于当前编号的数字", Toast.LENGTH_SHORT).show();
                        mNumberEt.setText(String.valueOf(SharedPreferenceUtils.getInt(LocationActivity.this,Constants.DEAL_NUMBER)+1));
                        return;
                    }
                    mCurrentNumber = Integer.parseInt(number);
                    formatNumber();
                }else {
                    mNumberEt.setText(String.valueOf(SharedPreferenceUtils.getInt(LocationActivity.this,Constants.DEAL_NUMBER)+1));
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
                intent.putExtra("picPath",saveToSdCardOne);
                startActivity(intent);
                break;
            case R.id.number_iv:
                Intent intent2 = new Intent(this, ImageActivity.class);
                intent2.putExtra("picPath",saveToSdCardTwo);
                startActivity(intent2);
                break;
        }
    }

    public void upLoadFormation(View view) {
        if (TextUtils.isEmpty(mGroupEt.getText())){
            Toast.makeText(this, "请输入组", Toast.LENGTH_SHORT).show();
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
        SharedPreferenceUtils.saveInt(this,Constants.DEAL_NUMBER,mCurrentNumber);
        UploadLocationEntryDao uploadLocationEntryDao = BaseApplication.getInstances().getDaoSession().getUploadLocationEntryDao();
        UploadLocationEntry uploadLocationEntry = new UploadLocationEntry();
        uploadLocationEntry.setRealNumber(mNumberTv.getText().toString());//编号
        uploadLocationEntry.setUploadNumber((long) mCurrentNumber);//采伐序号
        uploadLocationEntry.setAddressName(mAddressEt.getText().toString());//小地名
        uploadLocationEntry.setAroundIvPath(saveToSdCardOne);//全景照片
        uploadLocationEntry.setNumberIvPath(saveToSdCardTwo);//编号照片
        uploadLocationEntry.setDealTime(mTimeTv.getText().toString());//时间
        int dealPosition = SharedPreferenceUtils.getInt(this, Constants.DEAL_TYPE_POSITION);//处置位置
        String dealType = getResources().getStringArray(R.array.typeList)[dealPosition];//type
        uploadLocationEntry.setDealType(dealType);
        uploadLocationEntry.setDealTypePosition(dealPosition);
        int saveVillagePosition = SharedPreferenceUtils.getInt(this,Constants.SAVE_VILLAGE_POSITION);//村位置
        String village = getResources().getStringArray(R.array.village_list)[saveVillagePosition];
        uploadLocationEntry.setVillageName(village);
        uploadLocationEntry.setWokerName(mWorkerTv.getText().toString());
        uploadLocationEntry.setVillagePosition(saveVillagePosition);
        uploadLocationEntry.setGroupNumber(Integer.parseInt(mGroupEt.getText().toString()));//组
        uploadLocationEntry.setOwnTown(mOwnTownTv.getText().toString());//所属乡镇
        uploadLocationEntry.setLatitude(gps.getWgLat());//纬度
        uploadLocationEntry.setLongtitude(gps.getWgLon());//经度
        uploadLocationEntry.setRadius(Double.parseDouble(mRadiusEt.getText().toString()));//伐地半径
        if (!TextUtils.isEmpty(mCurrentAddress)){
            uploadLocationEntry.setDetailAddress(mCurrentAddress);//具体地名
        }
        uploadLocationEntryDao.insert(uploadLocationEntry);

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
