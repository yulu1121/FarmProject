package com.anshi.farmproject.view.notupload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.base.BaseApplication;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.greendao.UploadLocationEntry;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.view.query.QueryDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NotUploadActivity extends BaseActivity {
    private List<UploadLocationEntry> mList = new ArrayList<>();
    private CommonAdapter<UploadLocationEntry> commonAdapter;
    public static final int UPLOAD_CODE = 321;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_upload);
        initView();
        loadDataFromSql();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case UPLOAD_CODE:
                    loadDataFromSql();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    private RecyclerView mRecyclerView;
    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("未上传列表");
        mRecyclerView = findViewById(R.id.upload_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        commonAdapter = new CommonAdapter<UploadLocationEntry>(this,R.layout.item_detail_query,mList) {
            @Override
            protected void convert(ViewHolder holder, final UploadLocationEntry detailQueryEntry, int position) {
                TextView time = holder.getView(R.id.time_tv);
                TextView company = holder.getView(R.id.company_tv);
                TextView number = holder.getView(R.id.number_tv);
                time.setText(detailQueryEntry.getDealTime());
                company.setText(detailQueryEntry.getWokerName());
                number.setText(detailQueryEntry.getRealNumber());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toNotDetail(detailQueryEntry);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }
    private void toNotDetail(UploadLocationEntry uploadLocationEntry){
        CanLoadEntry canLoadEntry = new CanLoadEntry();
        canLoadEntry.setRealNumber(uploadLocationEntry.getRealNumber());//编号
        canLoadEntry.setUploadNumber(uploadLocationEntry.getUploadNumber());//采伐序号
        if (!TextUtils.isEmpty(uploadLocationEntry.getAddressName())){
            canLoadEntry.setAddressName(uploadLocationEntry.getAddressName());//小地名
        }
        canLoadEntry.setAroundIvPath(uploadLocationEntry.getAroundIvPath());//全景照片
        canLoadEntry.setNumberIvPath(uploadLocationEntry.getNumberIvPath());//编号照片
        canLoadEntry.setDealTime(uploadLocationEntry.getDealTime());//时间
        canLoadEntry.setDealType(uploadLocationEntry.getDealType());
        canLoadEntry.setTeaName(SharedPreferenceUtils.getString(this,"deptName"));
        canLoadEntry.setChainName(SharedPreferenceUtils.getString(this,"userName"));
        canLoadEntry.setZhiwuName(uploadLocationEntry.getZhiwuName());
        canLoadEntry.setZhiwuId(uploadLocationEntry.getZhiwuId());
        canLoadEntry.setDealTypePosition(uploadLocationEntry.getDealTypePosition());
        canLoadEntry.setVillageName(uploadLocationEntry.getVillageName());
        canLoadEntry.setWokerName(uploadLocationEntry.getWokerName());
        canLoadEntry.setVillagePosition(uploadLocationEntry.getVillagePosition());
        canLoadEntry.setGroupNumber(uploadLocationEntry.getGroupNumber());//组
        canLoadEntry.setOwnTown(uploadLocationEntry.getOwnTown());//所属乡镇
        canLoadEntry.setLatitude(uploadLocationEntry.getLatitude());//纬度
        canLoadEntry.setLongtitude(uploadLocationEntry.getLongtitude());//经度
        canLoadEntry.setRadius(uploadLocationEntry.getRadius());//伐地半径
        if (!TextUtils.isEmpty(uploadLocationEntry.getDetailAddress())){
            canLoadEntry.setDetailAddress(uploadLocationEntry.getDetailAddress());//具体地名
        }
        Intent intent = new Intent(mContext,QueryDetailActivity.class);
        intent.putExtra("data",canLoadEntry);
        startActivityForResult(intent,UPLOAD_CODE);
    }

    private void loadDataFromSql(){
        List<UploadLocationEntry> uploadLocationEntries = BaseApplication.getInstances().getDaoSession().getUploadLocationEntryDao().loadAll();
        mList.clear();
        mList.addAll(uploadLocationEntries);
        commonAdapter.notifyDataSetChanged();
    }


}
