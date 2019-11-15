package com.anshi.farmproject.view.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.anshi.farmproject.R;
import com.anshi.farmproject.adapter.ExampleListTreeAdapter;
import com.anshi.farmproject.base.BaseActivity;
import com.anshi.farmproject.utils.StatusBarUtils;
import com.niuedu.ListTree;
import com.niuedu.ListTreeAdapter;

public class QueryActivity extends BaseActivity {
    private RecyclerView mListView;
    private ListTree tree = new ListTree();
    //从ListTreeAdapter派生的Adapter
    private ExampleListTreeAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
        initData();
    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_tv);
        titleTv.setText("除治信息查询");
        mListView = findViewById(R.id.tree_recycler);
        mListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mListView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
        StatusBarUtils.setStatusTextColor(true,this);
    }
    private void initData(){
        ExampleListTreeAdapter.ContactInfo contact;
        contact = new ExampleListTreeAdapter.ContactInfo("林业局","数量:3000");
        //创建后台数据：一棵树
        //创建组们，是root node，所有parent为null
        ListTree.TreeNode groupNode1 = tree.addNode(null, contact, R.layout.contacts_group_item);
        //第二层
        contact = new ExampleListTreeAdapter.ContactInfo( "点军街办", "数量:1000");
        ListTree.TreeNode contactNode1 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
        //再添加一个
        contact = new ExampleListTreeAdapter.ContactInfo( "土城乡", "数量:1000");
        ListTree.TreeNode contactNode2 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
        //再添加一个
        contact = new ExampleListTreeAdapter.ContactInfo( "桥边镇", "数量:1000");
        ListTree.TreeNode contactNode3 = tree.addNode(groupNode1, contact, R.layout.contacts_group_item);
        //第三层
        contact = new ExampleListTreeAdapter.ContactInfo("牛扎坪村", "数量:1000");
        ListTree.TreeNode n = tree.addNode(contactNode1, contact, R.layout.contacts_group_item);
        //第4层
        contact = new ExampleListTreeAdapter.ContactInfo("思路除治队", "数量:1000");
        ListTree.TreeNode n1 = tree.addNode(n, contact, R.layout.contacts_group_item);
        //第5层
        contact = new ExampleListTreeAdapter.ContactInfo("油锯1", "数量:500");
        ListTree.TreeNode n2 = tree.addNode(n1, contact, R.layout.contacts_contact_item);
        n2.setShowExpandIcon(false);
        contact = new ExampleListTreeAdapter.ContactInfo("油锯2", "数量:500");
        ListTree.TreeNode n3 = tree.addNode(n1, contact, R.layout.contacts_contact_item);
        n3.setShowExpandIcon(false);

        adapter = new ExampleListTreeAdapter(this, tree, new ListTreeAdapter.ChildClick() {
            @Override
            public void childClick(int position) {
                ListTree.TreeNode node = tree.getNodeByPlaneIndex(position);
                ExampleListTreeAdapter.ContactInfo contactInfo = (ExampleListTreeAdapter.ContactInfo) node.getData();
                Log.e("xxx",contactInfo.getTitle());
                Intent intent = new Intent(mContext, QueryListActivity.class);
                mContext.startActivity(intent);
            }
        });

        mListView.setAdapter(adapter);
    }
}
