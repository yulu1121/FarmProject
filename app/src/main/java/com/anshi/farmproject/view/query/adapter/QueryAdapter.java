package com.anshi.farmproject.view.query.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anshi.farmproject.R;
import com.anshi.farmproject.entry.CanLoadEntry;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.entry.QueryGroupEntry;
import com.anshi.farmproject.view.query.QueryDetailActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by asus on 2019/11/22.
 */

public class QueryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private String title;
    private LinkedHashMap<String,List<DetailQueryEntry.DataBean>> hashMap;
    private final LayoutInflater mInflater;
    public QueryAdapter(Context context,String title,LinkedHashMap<String,List<DetailQueryEntry.DataBean>> hashMap ){
        this.context = context;
        this.hashMap = hashMap;
        this.title = title;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getGroupCount() {
        return  hashMap.keySet().toArray().length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_date,parent,false);
        }
        Object[] objects = hashMap.keySet().toArray();

        TextView tvGroup = convertView.findViewById(R.id.chainw_tv);
        ImageView iv = convertView.findViewById(R.id.icon_iv);
        if (isExpanded){
            iv.setImageResource(R.drawable.pg_expand);
        }else {
            iv.setImageResource(R.drawable.pg_collapse);
        }
        TextView timeTv = convertView.findViewById(R.id.time_tv);
        TextView totalTv = convertView.findViewById(R.id.total_tv);
        tvGroup.setText(title);
        timeTv.setText(objects[groupPosition].toString());
        totalTv.setText(String.valueOf(hashMap.get(objects[groupPosition].toString()).size()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Object[] objects = hashMap.keySet().toArray();
        List<DetailQueryEntry.DataBean> dataBeanList = hashMap.get(objects[groupPosition].toString());
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_child,parent,false);
        }
        RecyclerView recyclerView = convertView.findViewById(R.id.child_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        CommonAdapter<DetailQueryEntry.DataBean> commonAdapter = new CommonAdapter<DetailQueryEntry.DataBean>(context,R.layout.item_detail_query,dataBeanList) {
            @Override
            protected void convert(ViewHolder holder, final DetailQueryEntry.DataBean detailQueryEntry, int position) {
                    TextView time = holder.getView(R.id.time_tv);
                    TextView company = holder.getView(R.id.company_tv);
                    TextView number = holder.getView(R.id.number_tv);
                    time.setText(detailQueryEntry.getCureTime());
                    company.setText(String.valueOf(detailQueryEntry.getOrders()));
                    number.setText(detailQueryEntry.getNumber());
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toNotDetail(detailQueryEntry);
                        }
                    });
            }
        };
        recyclerView.setAdapter(commonAdapter);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
        Intent intent = new Intent(context,QueryDetailActivity.class);
        intent.putExtra("data",canLoadEntry);
        intent.putExtra("hasData",true);
        context.startActivity(intent);
    }

}
