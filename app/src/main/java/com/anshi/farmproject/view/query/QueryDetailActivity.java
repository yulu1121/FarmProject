package com.anshi.farmproject.view.query;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.base.BaseApplication;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.entry.ImageEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.anshi.farmproject.utils.glide.GlideApp;
import com.anshi.farmproject.utils.notifylistener.NotifyListenerMangager;
import com.anshi.farmproject.view.image.ImageActivity;
import com.baidu.mapapi.NetworkUtil;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class QueryDetailActivity extends BaseActivity implements View.OnClickListener {
    private CanLoadEntry uploadLocationEntry;
    private TextView mNumberTv,mTimeTv,mPersonTv,mUnitTv,mNameTv,mTypeTv,mOwnTownTv,mVillageTv;
    private TextView mGroupTv,mAddressTv,mRadiusTv;
    private ImageView mArroundIv,mMapIv;
    private ImageView mNumberIv;
    private TextView mLonTv,mLatTv,mRealGroupTv;
    private String saveToSdCardOne;
    private String saveToSdCardTwo;
    private boolean hasData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_detail);
        hasData = getIntent().getBooleanExtra("hasData", false);
        uploadLocationEntry = (CanLoadEntry) getIntent().getSerializableExtra("data");
        initView();
        if (null!=uploadLocationEntry){
            completeView();
        }
    }
    private TextView mChangeTv;
    private String mapUrl;
    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("除治详情");
        mChangeTv = findViewById(R.id.publish_tv);
        mChangeTv.setVisibility(View.GONE);
        mChangeTv.setText("切换横屏");
        mChangeTv.setOnClickListener(this);
        mNumberTv = findViewById(R.id.number_tv);
        mTimeTv = findViewById(R.id.time_tv);
        mPersonTv = findViewById(R.id.person_tv);
        mUnitTv = findViewById(R.id.unit_tv);
        mTypeTv = findViewById(R.id.type_tv);
        mOwnTownTv = findViewById(R.id.own_tv);
        mVillageTv = findViewById(R.id.village_tv);
        mNameTv = findViewById(R.id.name_tv);
        mGroupTv = findViewById(R.id.group_tv);
        mAddressTv = findViewById(R.id.address_tv);
        mRadiusTv = findViewById(R.id.radius_tv);
        mArroundIv = findViewById(R.id.around_iv);
        mNumberIv = findViewById(R.id.number_iv);
        mLatTv = findViewById(R.id.lat_tv);
        mMapIv = findViewById(R.id.map_iv);
        mLonTv = findViewById(R.id.lon_tv);
        mRealGroupTv = findViewById(R.id.real_group_tv);
        mArroundIv.setOnClickListener(this);
        mNumberIv.setOnClickListener(this);
        if (hasData){
            findViewById(R.id.commit_btn).setVisibility(View.GONE);
        }else {
            findViewById(R.id.commit_btn).setVisibility(View.VISIBLE);
        }
        if (NetworkUtil.isNetworkAvailable(this)){
            findViewById(R.id.map_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.map_formation_layout).setVisibility(View.VISIBLE);
            if (uploadLocationEntry!=null){
                mapUrl = "http://api.map.baidu.com/staticimage/v2?ak="+ Constants.MAP_AK+"&center="+uploadLocationEntry.getLongtitude()+","+uploadLocationEntry.getLatitude()+"&width=300&height=200&zoom=15&"+"markers="+uploadLocationEntry.getLongtitude()+","+uploadLocationEntry.getLatitude();
                mMapIv.setOnClickListener(this);
                GlideApp.with(mContext).load(mapUrl).centerCrop().into(mMapIv);
            }
        }else {
            findViewById(R.id.map_formation_layout).setVisibility(View.GONE);
            findViewById(R.id.map_layout).setVisibility(View.GONE);
        }
        
    }

    @SuppressLint("SetTextI18n")
    private void completeView(){

        mNumberTv.setText(uploadLocationEntry.getRealNumber());
        mTimeTv.setText(uploadLocationEntry.getDealTime());
        mPersonTv.setText(uploadLocationEntry.getChainName());
        mUnitTv.setText(uploadLocationEntry.getTeaName());
        mTypeTv.setText(uploadLocationEntry.getDealType());
        mOwnTownTv.setText(uploadLocationEntry.getOwnTown());
        mVillageTv.setText(uploadLocationEntry.getVillageName());
        mNameTv.setText(uploadLocationEntry.getZhiwuName());
        mGroupTv.setText(String.valueOf(uploadLocationEntry.getGroupNumber()));
        mAddressTv.setText(uploadLocationEntry.getAddressName());
        mLatTv.setText(String.valueOf(uploadLocationEntry.getLatitude()));
        mLonTv.setText(String.valueOf(uploadLocationEntry.getLongtitude()));
        mRealGroupTv.setText(String.valueOf(uploadLocationEntry.getUploadNumber()));
        mRadiusTv.setText(String.valueOf(uploadLocationEntry.getRadius())+"(厘米)");
        if (hasData){
            GlideApp.with(this).load(Constants.IMAGE_HEADER+uploadLocationEntry.getAroundIvPath()).centerCrop().into(mArroundIv);
            GlideApp.with(this).load(Constants.IMAGE_HEADER+uploadLocationEntry.getNumberIvPath()).centerCrop().into(mNumberIv);
        }else {
            GlideApp.with(this).load(uploadLocationEntry.getAroundIvPath()).centerCrop().into(mArroundIv);
            GlideApp.with(this).load(uploadLocationEntry.getNumberIvPath()).centerCrop().into(mNumberIv);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.publish_tv:
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            {
                mChangeTv.setText("切换横屏");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }


            else if(getRequestedOrientation() ==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            {
                mChangeTv.setText("切换竖屏");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            break;
            case R.id.around_iv:
                if (uploadLocationEntry!=null){
                    Intent intent = new Intent(this, ImageActivity.class);
                    if (hasData){
                        intent.putExtra("isUpload",true);
                    }
                    intent.putExtra("picPath",uploadLocationEntry.getAroundIvPath());
                    startActivity(intent);
                }
                break;
            case R.id.number_iv:
                if (uploadLocationEntry!=null){
                    Intent intent = new Intent(this, ImageActivity.class);
                    if (hasData){
                        intent.putExtra("isUpload",true);
                    }
                    intent.putExtra("picPath",uploadLocationEntry.getNumberIvPath());
                    startActivity(intent);
                }
                break;
            case R.id.map_iv:
                if (!TextUtils.isEmpty(mapUrl)){
                    Intent intent = new Intent(this,ImageActivity.class);
                    intent.putExtra("picPath",mapUrl);
                    startActivity(intent);
                }
                break;
        }
    }

    private void uploadOneIv(String path){
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
                                    saveToSdCardOne = imageEntry.getData().getSavePath();
                                    uploadTwoIv(uploadLocationEntry.getNumberIvPath());
                                }else {
                                    Toast.makeText(mContext, imageEntry.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void uploadTwoIv(String path){
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
                                   saveToSdCardTwo = imageEntry.getData().getSavePath();
                                   uploadFormation();
                                }else {
                                    Toast.makeText(mContext, imageEntry.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void uploadFormation(){

        final KProgressHUD commonLoadDialog  = DialogBuild.getBuild().createCommonLoadDialog(this,"正在上传");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("number",uploadLocationEntry.getRealNumber());
            jsonObject.put("orders",uploadLocationEntry.getUploadNumber());
            jsonObject.put("company",SharedPreferenceUtils.getString(this,"deptName"));
            jsonObject.put("cureTime",uploadLocationEntry.getDealTime());
            jsonObject.put("cureId",uploadLocationEntry.getDealTypePosition());
            jsonObject.put("townId",SharedPreferenceUtils.getInt(this,"townId"));
            jsonObject.put("villageId",uploadLocationEntry.getVillagePosition());
            jsonObject.put("groups",uploadLocationEntry.getGroupNumber());
            jsonObject.put("groundDiameter",uploadLocationEntry.getRadius());
            jsonObject.put("placeName",uploadLocationEntry.getAddressName());
            jsonObject.put("botanyId",uploadLocationEntry.getZhiwuId());
            jsonObject.put("longitude",uploadLocationEntry.getLongtitude());
            jsonObject.put("latitude",uploadLocationEntry.getLatitude());
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
                                        Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                BaseApplication.getInstances().getDaoSession().getUploadLocationEntryDao().deleteByKey(uploadLocationEntry.getUploadNumber());
                                                NotifyListenerMangager.getInstance().nofityContext("成功",Constants.NET_STATE);
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                        },1000);
                                    }else {
                                        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
                        throwable.printStackTrace();
                    }
                });

    }

    public void commit(View view) {
        uploadOneIv(uploadLocationEntry.getAroundIvPath());
    }
}
