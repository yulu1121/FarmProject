package com.anshi.farmproject.view.query;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.glide.GlideApp;
import com.anshi.farmproject.view.image.ImageActivity;
import com.baidu.mapapi.NetworkUtil;

public class QueryDetailActivity extends BaseActivity implements View.OnClickListener {
    private CanLoadEntry uploadLocationEntry;
    private TextView mNumberTv,mTimeTv,mPersonTv,mUnitTv,mNameTv,mTypeTv,mOwnTownTv,mVillageTv;
    private TextView mGroupTv,mAddressTv,mRadiusTv;
    private ImageView mArroundIv,mMapIv;
    private ImageView mNumberIv;
    private TextView mLonTv,mLatTv,mRealGroupTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_detail);
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
        mChangeTv.setVisibility(View.VISIBLE);
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
        mPersonTv.setText(uploadLocationEntry.getWokerName()==null?"":uploadLocationEntry.getWokerName());
        mUnitTv.setText("林业局");
        mTypeTv.setText(uploadLocationEntry.getDealType());
        mOwnTownTv.setText(uploadLocationEntry.getOwnTown());
        mVillageTv.setText(uploadLocationEntry.getVillageName());
        mNameTv.setText("马尾松");
        mGroupTv.setText(String.valueOf(uploadLocationEntry.getGroupNumber()));
        mAddressTv.setText(uploadLocationEntry.getAddressName());
        mLatTv.setText(String.valueOf(uploadLocationEntry.getLatitude()));
        mLonTv.setText(String.valueOf(uploadLocationEntry.getLongtitude()));
        mRealGroupTv.setText(String.valueOf(uploadLocationEntry.getGroupNumber()));
        mRadiusTv.setText(String.valueOf(uploadLocationEntry.getRadius())+"(厘米)");
        GlideApp.with(this).load(uploadLocationEntry.getAroundIvPath()).centerCrop().into(mArroundIv);
        GlideApp.with(this).load(uploadLocationEntry.getNumberIvPath()).centerCrop().into(mNumberIv);

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
                    intent.putExtra("picPath",uploadLocationEntry.getAroundIvPath());
                    startActivity(intent);
                }
                break;
            case R.id.number_iv:
                if (uploadLocationEntry!=null){
                    Intent intent = new Intent(this, ImageActivity.class);
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
}
