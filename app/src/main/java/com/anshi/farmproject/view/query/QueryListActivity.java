package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.entry.CureTimeEntry;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.utils.Constants;
import com.anshi.farmproject.utils.DialogBuild;
import com.anshi.farmproject.utils.SharedPreferenceUtils;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.anshi.farmproject.utils.Utils;
import com.anshi.farmproject.view.query.adapter.QueryAdapter;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class QueryListActivity extends BaseActivity {
    private ExpandableListView mRecyclerView;
    private LinkedHashMap<String,List<DetailQueryEntry.DataBean.FellingListBean>> hashMap = new LinkedHashMap<>();
    //private CommonAdapter<DetailQueryEntry.DataBean> commonAdapter;
    private int id;
    private int pageNo = 1;
    private QueryAdapter queryAdapter;
    private String title;
    private SmartRefreshLayout mRefreshLayout;
    private int total;
    private LinkedHashMap<String,List<Long>> longLinkedHashMap = new LinkedHashMap<>();
    private boolean isLastPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        total = getIntent().getIntExtra("total", 0);
        initView();
        getTimeList();
        addEventListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    
    
    private void getTimeList(){
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载中");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharedPreferenceUtils.getInt(mContext,"userId"));
            jsonObject.put("branchId",id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
        mService.getCureTimeCount(requestBody)
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
                                Gson gson =  new Gson();
                                CureTimeEntry cureTimeEntry = gson.fromJson(string, CureTimeEntry.class);
                                if (cureTimeEntry.getCode()==Constants.SUCCESS_CODE){

                                    if (cureTimeEntry.getData()!=null&&cureTimeEntry.getData().size()>0){
                                        for (int i = 0; i <cureTimeEntry.getData().size() ; i++) {
                                            CureTimeEntry.DataBean dataBean = cureTimeEntry.getData().get(i);
                                            if (longLinkedHashMap.containsKey(getTimeYearMoth(dataBean.getCureTime()))){
                                                List<Long> longs = longLinkedHashMap.get(getTimeYearMoth(dataBean.getCureTime()));
                                                if (longs != null) {
                                                    longs.add((long) dataBean.getAmounts());
                                                }
                                            }else {
                                                List<Long> longList = new ArrayList<>();
                                                longList.add((long) dataBean.getAmounts());
                                                longLinkedHashMap.put(getTimeYearMoth(dataBean.getCureTime()),longList);
                                            }
                                        }
                                        initData(true,longLinkedHashMap);
                                    }
                                }else {
                                    Toast.makeText(mContext, cureTimeEntry.getMsg(), Toast.LENGTH_SHORT).show();
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
    
    
    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("油锯除治详情");
        mRecyclerView = findViewById(R.id.detail_recycler);
        queryAdapter = new QueryAdapter(this,title,hashMap);
        mRecyclerView.setAdapter(queryAdapter);
        mRefreshLayout = findViewById(R.id.smart_refresh);

    }
    private void addEventListener(){
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                 if (isLastPage){
                     refreshLayout.finishLoadMore(true);
                     return;
                 }
                 pageNo++;
                 initData(false,longLinkedHashMap);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    pageNo = 1;
                    initData(true,longLinkedHashMap);

            }
        });
    }

    private String getTimeYearMoth(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String  getYearMonth(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void toNotDetail(DetailQueryEntry.DataBean.FellingListBean uploadLocationEntry){
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
    private void initData(final boolean refresh, final LinkedHashMap<String,List<Long>> listLinkedHashMap){
        if (!isFinishing()){
            commonLoadDialog = DialogBuild.getBuild().createCommonLoadDialog(this,"正在加载中");
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", SharedPreferenceUtils.getInt(mContext,"userId"));
            jsonObject.put("branchId",id);
            jsonObject.put("pageSize",50);
            jsonObject.put("pageNum",pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("xxx",jsonObject.toString());
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
                                    isLastPage = detailQueryEntry.getData().isLastPage();
                                    if (detailQueryEntry.getData().getFellingList()!=null&&detailQueryEntry.getData().getFellingList().size()>0){
                                        if (refresh){
                                            hashMap.clear();
                                            mRefreshLayout.finishRefresh(true);
                                        }else {
                                            mRefreshLayout.finishLoadMore(true);
                                        }
                                        for (int i = 0; i <detailQueryEntry.getData().getFellingList().size(); i++) {
                                            DetailQueryEntry.DataBean.FellingListBean dataBean = detailQueryEntry.getData().getFellingList().get(i);
                                            if (hashMap.containsKey(getYearMonth(dataBean.getCureTime()))){
                                                if (listLinkedHashMap.containsKey(getYearMonth(dataBean.getCureTime()))){
                                                    List<Long> longList = listLinkedHashMap.get(getYearMonth(dataBean.getCureTime()));
                                                    long totalAmouts = 0;
                                                    for (int j = 0; j <longList.size(); j++) {
                                                        totalAmouts+=longList.get(j);
                                                    }
                                                    dataBean.setAmount(totalAmouts);
                                                }
                                                List<DetailQueryEntry.DataBean.FellingListBean> dataBeans = hashMap.get(getYearMonth(dataBean.getCureTime()));
                                                dataBeans.add(dataBean);
                                            }else {
                                                List<DetailQueryEntry.DataBean.FellingListBean> dataBeanList = new ArrayList<>();
                                                if (listLinkedHashMap.containsKey(getYearMonth(dataBean.getCureTime()))){
                                                    List<Long> longList = listLinkedHashMap.get(getYearMonth(dataBean.getCureTime()));
                                                    long totalAmouts = 0;
                                                    for (int j = 0; j <longList.size(); j++) {
                                                        totalAmouts+=longList.get(j);
                                                    }
                                                    dataBean.setAmount(totalAmouts);
                                                }
                                                dataBeanList.add(dataBean);
                                                hashMap.put(getYearMonth(dataBean.getCureTime()),dataBeanList);
                                            }
                                        }
                                        queryAdapter.notifyDataSetChanged();
                                        for(int i = 0; i < queryAdapter.getGroupCount(); i++){
                                            mRecyclerView.expandGroup(i);
                                        }
                                    }
                                }else{
                                    if (refresh){
                                        mRefreshLayout.finishRefresh(false);
                                    }else {
                                        mRefreshLayout.finishLoadMore(false);
                                    }
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
                        if (refresh){
                            mRefreshLayout.finishRefresh(false);
                        }else {
                            mRefreshLayout.finishLoadMore(false);
                        }
                        throwable.printStackTrace();
                    }
                });
    }
}
