package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anshi.farmproject.R;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.entry.DetailQueryEntry;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class QueryListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<DetailQueryEntry> mList = new ArrayList<>();
    private CommonAdapter<DetailQueryEntry> commonAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list);
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
        commonAdapter = new CommonAdapter<DetailQueryEntry>(this,R.layout.item_detail_query,mList) {
            @Override
            protected void convert(ViewHolder holder, DetailQueryEntry detailQueryEntry, int position) {
                    TextView time = holder.getView(R.id.time_tv);
                    TextView company = holder.getView(R.id.company_tv);
                    TextView number = holder.getView(R.id.number_tv);
                    time.setText(detailQueryEntry.getTime());
                    company.setText(detailQueryEntry.getCompany());
                    number.setText(detailQueryEntry.getNumber());
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,QueryDetailActivity.class);
                            startActivity(intent);
                        }
                    });
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    private void initData(){
        mList.add(new DetailQueryEntry("2019年11月13日13:46:02","宜昌市萨达打开好","XY-SA-SD-0002"));
        mList.add(new DetailQueryEntry("2019年11月14日13:46:02","宜昌市萨达打开好","XY-SA-SD-0002"));
        mList.add(new DetailQueryEntry("2019年11月15日13:46:02","宜昌市萨达打开好","XY-SA-SD-0002"));
        mList.add(new DetailQueryEntry("2019年11月16日13:46:02","宜昌市萨达打开好","XY-SA-SD-0002"));
        mList.add(new DetailQueryEntry("2019年11月17日13:46:02","宜昌市萨达打开好","XY-SA-SD-0002"));
        commonAdapter.notifyDataSetChanged();
    }
}
