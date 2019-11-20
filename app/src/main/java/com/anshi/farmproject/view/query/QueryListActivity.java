package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class QueryListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<DetailQueryEntry.DataBean> mList = new ArrayList<>();
    private CommonAdapter<DetailQueryEntry.DataBean> commonAdapter;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list);
        id = getIntent().getIntExtra("id", 0);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("油锯除治详情");
        mRecyclerView = findViewById(R.id.detail_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        commonAdapter = new CommonAdapter<DetailQueryEntry.DataBean>(this,R.layout.item_detail_query,mList) {
            @Override
            protected void convert(ViewHolder holder, final DetailQueryEntry.DataBean detailQueryEntry, int position) {
                    TextView time = holder.getView(R.id.time_tv);
                    TextView company = holder.getView(R.id.company_tv);
                    TextView number = holder.getView(R.id.number_tv);
                    time.setText(detailQueryEntry.getCureTime());
                    company.setText(detailQueryEntry.getCompany());
                    number.setText(detailQueryEntry.getNumber());
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

    private void toNotDetail(DetailQueryEntry.DataBean uploadLocationEntry){
        CanLoadEntry canLoadEntry = new CanLoadEntry();
        canLoadEntry.setRealNumber(uploadLocationEntry.getNumber());//编号
        canLoadEntry.setUploadNumber((long) uploadLocationEntry.getOrders());//采伐序号
        canLoadEntry.setAddressName(uploadLocationEntry.getPlaceName());//小地名
        canLoadEntry.setAroundIvPath(uploadLocationEntry.getPanoramaPath());//全景照片
        canLoadEntry.setNumberIvPath(uploadLocationEntry.getNumberPath());//编号照片
        canLoadEntry.setDealTime(uploadLocationEntry.getCureTime());//时间
        canLoadEntry.setDealType(uploadLocationEntry.getCureName());
        canLoadEntry.setZhiwuName(uploadLocationEntry.getBotanyName());
        canLoadEntry.setZhiwuId(uploadLocationEntry.getBotanyId());
        canLoadEntry.setDealTypePosition(uploadLocationEntry.getCureId());
        canLoadEntry.setVillageName(uploadLocationEntry.getVillageName());
        canLoadEntry.setWokerName(uploadLocationEntry.getChainsaw());
        canLoadEntry.setVillagePosition(uploadLocationEntry.getVillageId());
        canLoadEntry.setChainName(uploadLocationEntry.getChainsaw());
        canLoadEntry.setTeaName(uploadLocationEntry.getTeamName());
        canLoadEntry.setGroupNumber(uploadLocationEntry.getGroups());//组
        canLoadEntry.setOwnTown(uploadLocationEntry.getTownName());//所属乡镇
        canLoadEntry.setLatitude(Double.parseDouble(uploadLocationEntry.getLatitude()));//纬度
        canLoadEntry.setLongtitude(Double.parseDouble(uploadLocationEntry.getLongitude()));//经度
        canLoadEntry.setRadius(uploadLocationEntry.getGroundDiameter());//伐地半径
//        if (!TextUtils.isEmpty(uploadLocationEntry.getDetailAddress())){
//            canLoadEntry.setDetailAddress(uploadLocationEntry.getDetailAddress());//具体地名
//        }
        Intent intent = new Intent(mContext,QueryDetailActivity.class);
        intent.putExtra("data",canLoadEntry);
        intent.putExtra("hasData",true);
        startActivity(intent);
    }

    private KProgressHUD commonLoadDialog;
    private void initData(){
        if (!isFinishing()){
            if (null!=commonLoadDialog){
                commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载中");
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("branchId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getFelingList(requestBody)
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
                                DetailQueryEntry detailQueryEntry = gson.fromJson(string, DetailQueryEntry.class);
                                if (detailQueryEntry.getCode()== Constants.SUCCESS_CODE){
                                    if (detailQueryEntry.getData()!=null&&detailQueryEntry.getData().size()>0){
                                        mList.clear();
                                        mList.addAll(detailQueryEntry.getData());
                                        commonAdapter.notifyDataSetChanged();
                                    }
                                }else{
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
                        if (null!=commonLoadDialog){
                            commonLoadDialog.dismiss();
                        }
                        throwable.printStackTrace();
                    }
                });
    }
}
