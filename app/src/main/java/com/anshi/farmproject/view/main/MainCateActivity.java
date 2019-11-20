package com.anshi.farmproject.view.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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
import com.anshi.farmproject.view.map.MapActivity;
import com.anshi.farmproject.view.notupload.NotUploadActivity;
import com.anshi.farmproject.view.query.QueryActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;

import java.util.List;

public class MainCateActivity extends BaseActivity implements View.OnClickListener,INotifyListener{
    private TextView mNotUploadTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SampleMultiplePermissionListener multiplePermissionListener = new SampleMultiplePermissionListener(this);
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new CompositeMultiplePermissionsListener(multiplePermissionListener, DialogBuild.getBuild().createPermissionDialog(this,"权限提醒","请给予拍照和定位的权限")))
                .check();
        setContentView(R.layout.activity_main_cate);
        NotifyListenerMangager.getInstance().registerListener(this, Constants.NET_STATE);
        initView();
    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        findViewById(R.id.back_layout).setVisibility(View.GONE);
        titleTv.setText("除治管理");
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
                        SharedPreferenceUtils.clear(mContext);
                        Intent intent = new Intent(mContext,LocationActivity.class);
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
}
